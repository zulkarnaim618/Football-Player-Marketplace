package network;

import database.Player;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.homeController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadThreadClient implements Runnable {
    private Thread thr;
    private NetworkUtil networkUtil;
    private homeController controller;
    private String clientName;
    private String reply;
    private int count;

    public ReadThreadClient(NetworkUtil networkUtil, homeController controller, String clientName) {
        this.clientName = clientName;
        this.controller = controller;
        this.reply = "";
        this.count = 0;
        this.networkUtil = networkUtil;
        this.thr = new Thread(this);
        thr.start();
    }
    public String formatClubName(String clubName) {
        String name = "";
        if (clubName.equalsIgnoreCase("manchester united")) name = "Manchester United";
        else if (clubName.equalsIgnoreCase("manchester city")) name = "Manchester City";
        else if (clubName.equalsIgnoreCase("chelsea")) name = "Chelsea";
        else if (clubName.equalsIgnoreCase("liverpool")) name = "Liverpool";
        else if (clubName.equalsIgnoreCase("arsenal")) name = "Arsenal";
        return name;
    }

    public void run() {
        try {
            while (true) {
                Object o = networkUtil.read();
                if (o instanceof Player) {
                    Player player = (Player) o;
                    if (player.isElligibletoAdd()) {
                        player.setElligibletoAdd(false);
                        player.setClub(formatClubName(clientName));
                        controller.db.playerList.add(player);
                        controller.db.addToClub(player);
                        controller.db.addToCountry(player);
                    }
                    else {
                        count++;
                        reply = "Sell successful";
                        controller.db.removePlayer(player);
                        new Thread(this::showPopup).start();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                controller.sellPlayers();
                            }
                        });
                    }
                }
                else if (o instanceof String) {
                    String msg = (String) o;
                    if (msg.equalsIgnoreCase("disconnect")) {
                        break;
                    }
                    else {
                        count++;
                        if (msg.equalsIgnoreCase("purchase successful")) {
                            reply = "Purchase Successful";
                        }
                        else if (msg.equalsIgnoreCase("purchase failed")) {
                            reply = "Purchase Failed";
                        }
                        new Thread(this::showPopup).start();
                    }
                }
                else if (o instanceof List) {
                    List<Player> buyPlayers = (List<Player>) o;
                    List<Player> buyPlayersModified = new ArrayList<>();
                    for (int i = 0; i < buyPlayers.size(); i++) {
                        if (!buyPlayers.get(i).getClub().equalsIgnoreCase(clientName)) {
                            buyPlayersModified.add(buyPlayers.get(i));
                        }
                    }
                    controller.buyPlayers = buyPlayersModified;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            controller.refreshBuyPlayers();
                        }
                    });
                }
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        finally {
            try {
                networkUtil.closeConnection();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void showPopup() {
        int count = this.count;
        if (controller.popup.isVisible()) {
            controller.popup.setVisible(false);
            try {
                Thread.sleep(200);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controller.popupText.setText(reply);
                controller.popup.setVisible(true);
            }
        });
        try {
            Thread.sleep(2000);
        }
        catch (Exception e) {
            System.out.println(e);
        }
        if (count == this.count) {
            controller.popup.setVisible(false);
        }
    }
}



