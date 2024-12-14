package database;
public class Country {
    private String name;
    private int playerCount;
    public Country () {

    }
    public Country (String name) {
        this.name = name;
    }
    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
