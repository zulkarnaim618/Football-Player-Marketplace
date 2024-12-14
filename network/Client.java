package network;

import database.Database;
import database.Player;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import sample.homeController;

import java.util.ArrayList;
import java.util.List;

public class Client {
    public NetworkUtil networkUtil;
    private homeController controller;
    private String clientName;

    public Client(String serverAddress, int serverPort, String clientName, homeController controller) {
        this.controller = controller;
        this.clientName = clientName;
        try {
            networkUtil = new NetworkUtil(serverAddress, serverPort);
            networkUtil.write(clientName);
            String status = (String) networkUtil.read();
            if (status.equalsIgnoreCase("success")) {
                controller.clubNameText.setText("");
                String clubStyle = controller.getClubStyle(clientName);
                controller.clubImage.setStyle(clubStyle+";-fx-background-size: 200 200");
                String clubName = controller.formatClubName(clientName);
                controller.clubImageLabel.setText(clubName + " Connected");
                controller.loginPane.setVisible(false);
                controller.logoutPane.setVisible(true);
                controller.isConnected = true;
                Event.fireEvent(controller.profileLabel, new MouseEvent(MouseEvent.MOUSE_CLICKED, 0,
                        0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                        true, true, true, true, true, true, null));
                List<Player> players = (List<Player>) networkUtil.read();
                controller.db = new Database();
                for (int i=0;i<players.size();i++) {
                    controller.db.playerList.add(players.get(i));
                    controller.db.addToClub(players.get(i));
                    controller.db.addToCountry(players.get(i));
                }
                List<Player> buyPlayers = (List<Player>) networkUtil.read();
                List<Player> buyPlayersModified = new ArrayList<>();
                for (int i = 0; i < buyPlayers.size(); i++) {
                    if (!buyPlayers.get(i).getClub().equalsIgnoreCase(clientName)) {
                        buyPlayersModified.add(buyPlayers.get(i));
                    }
                }
                controller.buyPlayers = buyPlayersModified;
                controller.refreshBuyPlayers();
                controller.sellPlayers();
                new ReadThreadClient(networkUtil,controller,clientName);
            }
            else if (status.equalsIgnoreCase("failure")) {
                controller.wrongClubLabel.setVisible(true);
                controller.clubNameText.setText("");
                networkUtil.closeConnection();
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }

    }
}


