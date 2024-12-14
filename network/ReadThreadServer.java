package network;

import database.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReadThreadServer implements Runnable {
    private Thread thr;
    private NetworkUtil networkUtil;
    private HashMap<String, NetworkUtil> clientMap;
    private List<Player> sellPlayers;
    private List<Player> playerList;
    private String clubName;


    public ReadThreadServer(HashMap<String, NetworkUtil> map, NetworkUtil networkUtil, List<Player> sellPlayers, List<Player> playerList, String clubName) {
        this.clientMap = map;
        this.clubName = clubName;
        this.networkUtil = networkUtil;
        this.sellPlayers = sellPlayers;
        this.playerList = playerList;
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
                    if (player.isInSellQueue()) {
                        player.setInSellQueue(false);
                        sellPlayers.add(player);
                        for (int i=0;i<playerList.size();i++) {
                            if (player.getName().equalsIgnoreCase(playerList.get(i).getName())) {
                                playerList.get(i).setInSellQueue(true);
                                break;
                            }
                        }
                    }
                    else {
                        NetworkUtil nu = null;
                        for (HashMap.Entry<String, NetworkUtil> i: clientMap.entrySet()) {
                            if (player.getClub().equalsIgnoreCase(i.getKey())) {
                                nu = i.getValue();
                                break;
                            }
                        }
                        boolean successfulBuy = false;
                        //synchronization
                        synchronized (sellPlayers) {
                            for (int i = 0; i < sellPlayers.size(); i++) {
                                if (player.getName().equalsIgnoreCase(sellPlayers.get(i).getName())) {
                                    //nu.write(player);
                                    sellPlayers.remove(i);
                                    successfulBuy = true;
                                    break;
                                }
                            }
                            //Thread.sleep(5000);
                        }
                        if (successfulBuy) {
                            if (nu != null) nu.write(player);
                            for (int i=0;i<playerList.size();i++) {
                                if (player.getName().equalsIgnoreCase(playerList.get(i).getName())) {
                                    playerList.get(i).setClub(formatClubName(clubName));
                                    playerList.get(i).setInSellQueue(false);
                                    break;
                                }
                            }
                            networkUtil.write("purchase successful");
                            player.setElligibletoAdd(true);
                            networkUtil.write(player);
                        }
                        else networkUtil.write("purchase failed");
                    }
                    for (HashMap.Entry<String, NetworkUtil> i: clientMap.entrySet()) {
                        NetworkUtil nu = i.getValue();
                        nu.write(sellPlayers);
                    }
                }
                else if (o instanceof String) {
                    String msg = (String) o;
                    if (msg.equalsIgnoreCase("disconnect")) {
                        networkUtil.write("disconnect");
                        break;
                    }
                }
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        finally {
            try {
                clientMap.remove(clubName);
                networkUtil.closeConnection();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



