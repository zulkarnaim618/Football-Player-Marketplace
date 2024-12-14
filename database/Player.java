package database;

import java.io.Serializable;

public class Player implements Serializable {
    private String name;
    private String country;
    private int age;
    private double height;
    private String club;
    private String position;
    private int number;
    private double weeklySalary;
    private boolean inSellQueue;
    private boolean isElligibletoAdd;
    private double sellPrice;
    public Player () {

    }
    public Player(String name, String country, int age, double height, String club, String position, int number, double weeklySalary) {
        this.name = name;
        this.country = country;
        this.age = age;
        this.height = height;
        this.club = club;
        this.position = position;
        this.number = number;
        this.weeklySalary = weeklySalary;
        this.inSellQueue = false;
        this.isElligibletoAdd = false;
    }

    public double getSellPrice() {
        return sellPrice;
    }
    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public boolean isElligibletoAdd() {
        return isElligibletoAdd;
    }
    public void setElligibletoAdd(boolean elligibletoAdd) {
        isElligibletoAdd = elligibletoAdd;
    }

    public boolean isInSellQueue() {
        return inSellQueue;
    }
    public void setInSellQueue(boolean inSellQueue) {
        this.inSellQueue = inSellQueue;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    public double getHeight() {
        return height;
    }
    public void setHeight(double height) {
        this.height = height;
    }

    public String getClub() {
        return club;
    }
    public void setClub(String club) {
        this.club = club;
    }

    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }

    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    public double getWeeklySalary() {
        return weeklySalary;
    }
    public void setWeeklySalary(double weeklySalary) {
        this.weeklySalary = weeklySalary;
    }

    public void printPlayerInfo() {
        System.out.println();
        System.out.println("Name            : " + name);
        System.out.println("Country         : " + country);
        System.out.println("Age in years    : " + age);
        System.out.println("Height in meters: " + height);
        System.out.println("Club            : " + club);
        System.out.println("Position        : " + position);
        System.out.println("Number          : " + number);
        System.out.println("Weekly Salary   : " + weeklySalary);
    }
}
