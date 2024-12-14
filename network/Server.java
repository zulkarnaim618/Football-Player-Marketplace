package network;

import database.Player;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Server {
    private List<Player> playerList = new ArrayList();
    private List<Player> sellPlayers = new ArrayList();
    private List<String> clubNames = new ArrayList();
    private ServerSocket serverSocket;
    private HashMap<String, NetworkUtil> clientMap = new HashMap<>();

    Server() {
        try {
            loadData("players.txt");
        }
        catch (Exception e) {
            System.out.println(e);
        }
        try {
            serverSocket = new ServerSocket(33335);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                NetworkUtil networkUtil = new NetworkUtil(clientSocket);
                String clubName = (String) networkUtil.read();
                boolean clubFound = false;
                for (int i=0;i<clubNames.size();i++) {
                    if (clubName.equalsIgnoreCase(clubNames.get(i))) {
                        clubFound = true;
                        break;
                    }
                }
                if (clubFound) {
                    networkUtil.write("success");
                    List<Player> players = new ArrayList();
                    for (int i=0;i<playerList.size();i++) {
                        if (playerList.get(i).getClub().equalsIgnoreCase(clubName)) players.add(playerList.get(i));
                    }
                    networkUtil.write(players);
                    networkUtil.write(sellPlayers);
                    clientMap.put(clubName, networkUtil);
                    new ReadThreadServer(clientMap, networkUtil, sellPlayers, playerList,clubName);
                }
                else {
                    networkUtil.write("failure");
                    networkUtil.closeConnection();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void loadData(String INPUT_FILE_NAME) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        while (true) {
            String line = br.readLine();
            if (line == null) break;
            String[] tokens = line.split(",");
            Player p = new Player(tokens[0],tokens[1],Integer.parseInt(tokens[2]),Double.parseDouble(tokens[3]),tokens[4],tokens[5],Integer.parseInt(tokens[6]),Double.parseDouble(tokens[7]));
            playerList.add(p);
            addClubName(p);
        }
        br.close();
    }
    public void addClubName(Player player) {
        boolean clubAdded = false;
        for (int i=0;i<clubNames.size();i++) {
            if (player.getClub().equalsIgnoreCase(clubNames.get(i))) {
                clubAdded = true;
                break;
            }
        }
        if (!clubAdded) {
            clubNames.add(player.getClub());
        }
    }

    public static void main(String args[]) {
        Server server = new Server();
    }
}
