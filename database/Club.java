package database;

import java.util.ArrayList;
import java.util.List;


public class Club {
    public static int playerLimit = 7;
    private String name;
    private List<Player> players;
    public Club () {
        players = new ArrayList<>();
    }
    public Club (String name) {
        players = new ArrayList<>();
        this.name = name;
    }
    public Player getPlayer(int index) {
        return players.get(index);
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public int getPlayerCount() {
        return players.size();
    }

    public void addPlayer (Player player) {
        /*
        if (playerCount == playerLimit) {
            System.out.println("Maximum player limit reached for this club");
            return;
        }
         */
        players.add(player);
    }
    public void removePlayer(Player player) {
        for (int i=0;i<players.size();i++) {
            if (player.getName().equalsIgnoreCase(players.get(i).getName())) {
                players.remove(i);
                break;
            }
        }
    }
    public double totalYearlySalary() {
        double total = 0;
        for (int i=0;i<players.size();i++) {
            total+=players.get(i).getWeeklySalary()*52;
        }
        return total;
    }
    public List<Player> maximumSalaryPlayers() {
        double maxSalary=-1;
        for (int i=0;i< players.size();i++) {
            if (maxSalary<players.get(i).getWeeklySalary()) {
                maxSalary = players.get(i).getWeeklySalary();
            }
        }
        List<Player> playerList = new ArrayList();
        for (int i=0;i<players.size();i++) {
            if (players.get(i).getWeeklySalary()==maxSalary) {
                playerList.add(players.get(i));
            }
        }
        return playerList;
    }
    public List<Player> maximumHeightPlayers() {
        double maxHeight=-1;
        for (int i=0;i<players.size();i++) {
            if (maxHeight<players.get(i).getHeight()) {
                maxHeight = players.get(i).getHeight();
            }
        }
        List<Player> playerList = new ArrayList();
        for (int i=0;i<players.size();i++) {
            if (players.get(i).getHeight()==maxHeight) {
                playerList.add(players.get(i));
            }
        }
        return playerList;
    }
    public List<Player> maximumAgePlayers() {
        int maxAge =-1;
        for (int i=0;i<players.size();i++) {
            if (maxAge<players.get(i).getAge()) {
                maxAge = players.get(i).getAge();
            }
        }
        List<Player> playerList = new ArrayList();
        for (int i=0;i<players.size();i++) {
            if (players.get(i).getAge()==maxAge) {
                playerList.add(players.get(i));
            }
        }
        return playerList;
    }
}
