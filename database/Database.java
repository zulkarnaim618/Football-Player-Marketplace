package database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Database {
    public List<Player> playerList = new ArrayList();
    public List<Club> clubs = new ArrayList();
    public List<Country> countries = new ArrayList();
    Scanner scanner = new Scanner(System.in);

    public void loadData(String INPUT_FILE_NAME) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        while (true) {
            String line = br.readLine();
            if (line == null) break;
            String[] tokens = line.split(",");
            Player p = new Player(tokens[0],tokens[1],Integer.parseInt(tokens[2]),Double.parseDouble(tokens[3]),tokens[4],tokens[5],Integer.parseInt(tokens[6]),Double.parseDouble(tokens[7]));
            playerList.add(p);
            addToClub(p);
            addToCountry(p);
        }
        br.close();
    }
    private void saveData(String OUTPUT_FILE_NAME) throws Exception{
        BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME));
        for (Player p : playerList) {
            bw.write(p.getName() + "," + p.getCountry() + "," + p.getAge() + "," + p.getHeight() + "," + p.getClub() + "," + p.getPosition() + "," + p.getNumber() + "," + p.getWeeklySalary());
            bw.write("\n");
        }
        bw.close();
    }
    public void functionality() throws Exception {
        loadData("players.txt");
        String option;
        int input;
        while (true) {
            System.out.println();
            System.out.println("Main Menu:");
            System.out.println("(1) Search Players");
            System.out.println("(2) Search Clubs");
            System.out.println("(3) Add Player");
            System.out.println("(4) Exit System");
            System.out.println();
            System.out.print("Select option: ");
            option = scanner.nextLine();
            input = Integer.parseInt(option);
            if (input==1) {
                while (true) {
                    System.out.println();
                    System.out.println("Player Searching Options:");
                    System.out.println("(1) By Player Name");
                    System.out.println("(2) By Club and Country");
                    System.out.println("(3) By Position");
                    System.out.println("(4) By Salary Range");
                    System.out.println("(5) Country-wise player count");
                    System.out.println("(6) Back to Main Menu");
                    System.out.println();
                    System.out.print("Select option: ");
                    option = scanner.nextLine();
                    input = Integer.parseInt(option);
                    if (input==1) {
                        System.out.print("Enter player name: ");
                        String name = scanner.nextLine();
                        Player player = searchByPlayerName(name);
                        if (player == null) System.out.println("No such player with this name");
                        else player.printPlayerInfo();
                    }
                    else if (input==2) {
                        System.out.print("Enter country name: ");
                        String country = scanner.nextLine();
                        System.out.print("Enter club name: ");
                        String club = scanner.nextLine();
                        List<Player> players = searchByClubAndCountry(country,club);
                        if (players.size()==0) System.out.println("No such player with this country and club");
                        else {
                            for (int i=0;i<players.size();i++) {
                                players.get(i).printPlayerInfo();
                            }
                        }
                    }
                    else if (input==3) {
                        System.out.print("Enter player position: ");
                        String position = scanner.nextLine();
                        List<Player> players = searchByPosition(position);
                        if (players.size()==0) System.out.println("No such player with this position");
                        else {
                            for (int i=0;i<players.size();i++) {
                                players.get(i).printPlayerInfo();
                            }
                        }
                    }
                    else if (input==4) {
                        System.out.print("Enter minimum salary: ");
                        String lowRangeString = scanner.nextLine();
                        System.out.print("Enter maximum salary: ");
                        String highRangeString = scanner.nextLine();
                        double lowRange = Double.parseDouble(lowRangeString);
                        double highRange = Double.parseDouble(highRangeString);
                        /*if (lowRange>highRange) {
                            double t = lowRange;
                            lowRange = highRange;
                            highRange = t;
                        }*/
                        List<Player> players = searchBySalaryRange(lowRange,highRange);
                        if (players.size()==0) System.out.println("No such player with this weekly salary range");
                        else {
                            for (int i=0;i<players.size();i++) {
                                players.get(i).printPlayerInfo();
                            }
                        }
                    }
                    else if (input==5) {
                        for (int i=0;i<countries.size();i++) {
                            System.out.println(countries.get(i).getName() + " : " + countries.get(i).getPlayerCount());
                        }
                    }
                    else if (input==6) {
                        break;
                    }
                    else {
                        System.out.println("Invalid option");
                    }
                }

            }
            else if (input==2) {
                while (true) {
                    System.out.println();
                    System.out.println("Club Searching Options:");
                    System.out.println("(1) Player(s) with the maximum salary of a club");
                    System.out.println("(2) Player(s) with the maximum age of a club");
                    System.out.println("(3) Player(s) with the maximum height of a club");
                    System.out.println("(4) Total yearly salary of a club");
                    System.out.println("(5) Back to Main Menu");
                    System.out.println();
                    System.out.print("Select option: ");
                    option = scanner.nextLine();
                    input = Integer.parseInt(option);
                    if (input>=1 && input<=4) {
                        System.out.print("Enter club name: ");
                        String clubName = scanner.nextLine();
                        Club club = searchByClubName(clubName);
                        if (club == null) System.out.println("No such club with this name");
                        else {
                            if (input >= 1 && input <= 3) {
                                List<Player> players = null;
                                if (input == 1) {
                                    players = club.maximumSalaryPlayers();
                                }
                                else if (input == 2) {
                                    players = club.maximumAgePlayers();
                                }
                                else if (input == 3) {
                                    players = club.maximumHeightPlayers();
                                }
                                for (int i=0;i<players.size();i++) {
                                    players.get(i).printPlayerInfo();
                                }
                            }
                            else if (input==4) {
                                double totalYearlySalary= club.totalYearlySalary();
                                //System.out.println("Total yearly salary: " + totalYearlySalary);
                                System.out.printf("Total yearly salary: %.2f\n",totalYearlySalary);
                            }
                        }
                    }
                    else if (input==5) {
                        break;
                    }
                    else {
                        System.out.println("Invalid option");
                    }
                }

            }
            else if (input==3) {
                System.out.print("Enter player name: ");
                String name = scanner.nextLine();
                boolean sameName = false;
                for (int i=0;i<playerList.size();i++) {
                    if (name.equalsIgnoreCase(playerList.get(i).getName())) {
                        sameName = true;
                        break;
                    }
                }
                if (sameName) System.out.println("Player with this name already exists");
                else {
                    System.out.print("Enter player's country name: ");
                    String country = scanner.nextLine();
                    System.out.print("Enter player's age in years: ");
                    String ageString = scanner.nextLine();
                    int age = Integer.parseInt(ageString);
                    System.out.print("Enter player's height in meters: ");
                    String heightString = scanner.nextLine();
                    double height = Double.parseDouble(heightString);
                    System.out.print("Enter player's club name: ");
                    String club = scanner.nextLine();
                    boolean clubLimitReached = false;
                    for (int i=0;i<clubs.size();i++) {
                        if (club.equalsIgnoreCase(clubs.get(i).getName())) {
                            if (clubs.get(i).getPlayerCount() == Club.playerLimit) {
                                clubLimitReached = true;
                                break;
                            }
                        }
                    }
                    if (clubLimitReached) System.out.println("Maximum player limit reached for this club");
                    else {
                        System.out.print("Enter player's position: ");
                        String position = scanner.nextLine();
                        if (!(position.equalsIgnoreCase("Goalkeeper") || position.equalsIgnoreCase("Defender") || position.equalsIgnoreCase("Midfielder") || position.equalsIgnoreCase("Forward"))) {
                            System.out.println("Invalid player position");
                        }
                        else {
                            position = formatPosition(position);
                            System.out.print("Enter player's number: ");
                            String numberString = scanner.nextLine();
                            int number = Integer.parseInt(numberString);
                            boolean sameNumber = false;
                            for (int i=0;i<clubs.size();i++) {
                                if (club.equalsIgnoreCase(clubs.get(i).getName())) {
                                    for (int j=0;j<clubs.get(i).getPlayerCount();j++) {
                                        if (clubs.get(i).getPlayer(j).getNumber() == number) {
                                            sameNumber = true;
                                            break;
                                        }
                                    }
                                    break;
                                }
                            }
                            if (sameNumber) System.out.println("Player with this number already exists in this club");
                            else {
                                System.out.print("Enter player's weekly salary: ");
                                String weeklySalaryString = scanner.nextLine();
                                double weeklySalary = Double.parseDouble(weeklySalaryString);
                                Player player = new Player(name,country,age,height,club,position,number,weeklySalary);
                                playerList.add(player);
                                addToClub(player);
                                addToCountry(player);
                                System.out.println("Player added successfully");
                            }
                        }
                    }
                }
            }
            else if (input==4) {
                break;
            }
            else {
                System.out.println("Invalid option");
            }

        }
        saveData("players.txt");
    }
    public Player searchByPlayerName(String name) {
        for (int i=0;i<playerList.size();i++) {
            if (playerList.get(i).getName().equalsIgnoreCase(name)) {
                return playerList.get(i);
            }
        }
        return null;
    }
    public List<Player> searchByClubAndCountry(String country, String club) {
        List<Player> players = new ArrayList();
        for (int i=0;i<playerList.size();i++) {
            Player player=playerList.get(i);
            if (player.getCountry().equalsIgnoreCase(country)) {
                if (club.equalsIgnoreCase("ANY") || player.getClub().equalsIgnoreCase(club)) {
                    players.add(player);
                }
            }
        }
        return players;
    }
    public List<Player> searchByPosition(String position) {
        List<Player> players = new ArrayList();
        for (int i=0;i<playerList.size();i++) {
            if (playerList.get(i).getPosition().equalsIgnoreCase(position)) {
                players.add(playerList.get(i));
            }
        }
        return players;
    }
    public List<Player> searchBySalaryRange(double lowRange, double highRange) {
        List<Player> players = new ArrayList();
        for (int i=0;i<playerList.size();i++) {
            if (playerList.get(i).getWeeklySalary()>=lowRange && playerList.get(i).getWeeklySalary()<=highRange) {
                players.add(playerList.get(i));
            }
        }
        return players;
    }
    private Club searchByClubName(String clubName) {
        for (int i=0;i<clubs.size();i++) {
            if (clubs.get(i).getName().equalsIgnoreCase(clubName)) {
                return clubs.get(i);
            }
        }
        return null;
    }
    public void addToClub(Player player) {
        boolean clubAdded = false;
        for (int i=0;i<clubs.size();i++) {
            if (player.getClub().equalsIgnoreCase(clubs.get(i).getName())) {
                clubs.get(i).addPlayer(player);
                clubAdded = true;
            }
        }
        if (!clubAdded) {
            clubs.add(new Club(player.getClub()));
            clubs.get(clubs.size()-1).addPlayer(player);
        }
    }
    public void addToCountry(Player player) {
        boolean countryAdded = false;
        for (int i=0;i<countries.size();i++) {
            if (player.getCountry().equalsIgnoreCase(countries.get(i).getName())) {
                countries.get(i).setPlayerCount(countries.get(i).getPlayerCount()+1);
                countryAdded = true;
            }
        }
        if (!countryAdded) {
            countries.add(new Country(player.getCountry()));
            countries.get(countries.size()-1).setPlayerCount(countries.get(countries.size()-1).getPlayerCount()+1);
        }
    }
    String formatPosition(String position) {
        if (position.equalsIgnoreCase("Forward")) position="Forward";
        else if (position.equalsIgnoreCase("Midfielder")) position="Midfielder";
        else if (position.equalsIgnoreCase("Goalkeeper")) position="Goalkeeper";
        else if (position.equalsIgnoreCase("Defender")) position="Defender";
        return position;
    }
    public void removePlayer(Player player) {
        for (int i=0;i<playerList.size();i++) {
            if (player.getName().equalsIgnoreCase(playerList.get(i).getName())) {
                playerList.remove(i);
                break;
            }
        }
        for (int i=0;i<clubs.size();i++) {
            if (player.getClub().equalsIgnoreCase(clubs.get(i).getName())) {
                clubs.get(i).removePlayer(player);
                break;
            }
        }
        for (int i=0;i<countries.size();i++) {
            if (player.getCountry().equalsIgnoreCase(countries.get(i).getName())) {
                countries.get(i).setPlayerCount(countries.get(i).getPlayerCount()-1);
                if (countries.get(i).getPlayerCount() == 0) countries.remove(i);
                break;
            }
        }
    }
    /*
    private String addPlayer (Player player) {
        for (int i=0;i<playerList.size();i++) {
            if (player.getName().equalsIgnoreCase(playerList.get(i).getName())) {
                return "Player with this name already exists";
            }
        }
        for (int i=0;i<clubs.size();i++) {
            if (player.getClub().equalsIgnoreCase(clubs.get(i).getName())) {
                if (clubs.get(i).getPlayerCount() == Club.playerLimit) {
                    return "Maximum player limit reached for this club";
                }
            }
        }
        if (!(player.getPosition().equalsIgnoreCase("Goalkeeper") || player.getPosition().equalsIgnoreCase("Defender") || player.getPosition().equalsIgnoreCase("Midfielder") || player.getPosition().equalsIgnoreCase("Forward"))) {
            return "Invalid player position";
        }

        for (int i=0;i<clubs.size();i++) {
            if (player.getClub().equalsIgnoreCase(clubs.get(i).getName())) {
                for (int j=0;j<clubs.get(i).getPlayerCount();j++) {
                    if (clubs.get(i).getPlayer(j).getNumber() == player.getNumber()) {
                        return "Player with this number already exists in this club";
                    }
                }
            }
        }
        playerList.add(player);
        addToClub(player);
        addToCountry(player);
        return "Player added successfully";
    }
     */
}
