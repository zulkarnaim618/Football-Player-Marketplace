package sample;


import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import database.*;
import network.*;

import java.util.ArrayList;
import java.util.List;


public class homeController {
    public Client client;
    @FXML
    public AnchorPane loginPane,logoutPane,popup;
    @FXML
    private AnchorPane leftPane,rightPane,searchContentPane,marketContentPane,playerSearchContentPane,clubSearchContentPane,buyContentPane,sellContentPane;
    @FXML
    private AnchorPane functionRightPane,marketRightPane,playerMenu,byNamePane,byClubandCountryPane,byPositionPane,bySalaryPane,countrywisePlayerCountPane,playerSearchContainer,clubSearchContainer,clubMenu;
    @FXML
    private AnchorPane maximumSalaryPane,maximumAgePane,maximumHeightPane,totalYearlySalaryPane,profilePane,functionPane,marketPane,settingsPane,customThemeContentPane,colorPickerPane;
    @FXML
    private Label playerNameSearchLabel,playerNameResetLabel,playerCountrySearchLabel,playerCountryResetLabel,playerPositionSearchLabel,playerPositionResetLabel,playerSalarySearchLabel,playerSalaryResetLabel;
    @FXML
    public Label profileLabel,theme,customTheme,lightModeLabel,lightModeTextLabel;
    @FXML
    private Label connectImage,connectLabel,disconnectLabel,darkModeLabel,settingsLabel,nameLabel,countryLabel,positionLabel,salaryLabel,darkModeTextLabel;
    @FXML
    public Label wrongClubLabel,clubImageLabel,clubImage,popupText,popupButton;
    @FXML
    private Label totalYearlySalaryValue,profileClubText;
    @FXML
    private VBox byNameBox,byPositionBox,byCountryBox,bySalaryBox,countrywisePlayerCountBox;
    @FXML
    private VBox maximumSalaryBox,maximumAgeBox,maximumHeightBox;
    @FXML
    private VBox buyBox,sellBox;
    @FXML
    private TextField playerNameText,playerPositionText,playerCountryText,minimumSalaryText,maximumSalaryText;
    @FXML
    public TextField clubNameText;

    public Database db = new Database();
    public List<Player> buyPlayers = new ArrayList<>();
    public boolean isConnected;
    @FXML
    private AnchorPane backgroundPane,smallHinge,profileContentPane,settingsRightPane,settingsContentPane,themeContentPane,colorPickerLabelPane;

    private String backgroundColor,leftBarColor,rightBarColor,leftBarClickedColor,rightBarClickedColor,contentBarColor,contentPaneColor,contentBarClickedColor,playerCardColor,playerCardClickedColor,playerCardFontColor;
    private String rightBarFontColor,rightBarFontClickedColor,contentBarFontColor,contentBarFontClickedColor,buttonColor,textFieldColor,textColor;

    public void lightTheme() {
        backgroundColor = "white";
        leftBarColor = "#000044";
        leftBarClickedColor = "white";
        rightBarColor = "white";
        rightBarClickedColor = "#000044";
        rightBarFontColor = "#000044";
        rightBarFontClickedColor = "white";
        contentPaneColor = "white";
        contentBarColor = "white";
        contentBarClickedColor = "green";
        contentBarFontColor = "green";
        contentBarFontClickedColor = "white";
        playerCardColor = "white";
        playerCardFontColor = "black";
        playerCardClickedColor = "#000044";
        buttonColor = "green";
        textFieldColor = "grey";
        textColor = "black";
        setNodeStyle("theme");
    }
    public void darkTheme() {
        backgroundColor = "#1a1a1a";
        leftBarColor = "#000044";
        leftBarClickedColor = "#303233";
        rightBarColor = "#303233";
        rightBarClickedColor = "#000044";
        rightBarFontColor = "white";
        rightBarFontClickedColor = "white";
        contentPaneColor = "#2b2b2b";
        contentBarColor = "#303233";
        contentBarClickedColor = "#4f40c8";
        contentBarFontColor = "white";
        contentBarFontClickedColor = "white";
        playerCardColor = "white";
        playerCardFontColor = "black";
        playerCardClickedColor = "#000044";
        buttonColor = "green";
        textFieldColor = "grey";
        textColor = "white";
        setNodeStyle("theme");
    }
    public void customTheme() {
        darkModeLabel.setVisible(true);
        lightModeLabel.setVisible(true);
        ObservableList<Node> colorPickers = colorPickerPane.getChildren();
        String[] colors = new String[17];
        for (int i=0;i<colorPickers.size();i++) {
            colors[i] = processColor(((ColorPicker)colorPickers.get(i)).getValue().toString());
        }
        backgroundColor = colors[0];
        leftBarColor = colors[1];
        leftBarClickedColor = colors[2];
        rightBarColor = colors[2];
        rightBarClickedColor = colors[3];
        rightBarFontColor = colors[4];
        rightBarFontClickedColor = colors[5];
        contentBarColor = colors[6];
        contentBarClickedColor = colors[7];
        contentBarFontColor = colors[8];
        contentBarFontClickedColor = colors[9];
        contentPaneColor = colors[10];
        playerCardColor = colors[11];
        playerCardClickedColor = colors[12];
        playerCardFontColor = colors[13];
        buttonColor = colors[14];
        textFieldColor = colors[15];
        textColor = colors[16];
        setNodeStyle("custom theme");
    }
    public String processColor(String color) {
        return "#".concat(color.substring(2,8));
    }
    public void initializeColorPickers() {
        ObservableList<Node> colorPickers = colorPickerPane.getChildren();
        String[] colors = new String[17];
        colors[0] = backgroundColor;
        colors[1] = leftBarColor;
        colors[2] = rightBarColor;
        colors[3] = rightBarClickedColor;
        colors[4] = rightBarFontColor;
        colors[5] = rightBarFontClickedColor;
        colors[6] = contentBarColor;
        colors[7] = contentBarClickedColor;
        colors[8] = contentBarFontColor;
        colors[9] = contentBarFontClickedColor;
        colors[10] = contentPaneColor;
        colors[11] = playerCardColor;
        colors[12] = playerCardClickedColor;
        colors[13] = playerCardFontColor;
        colors[14] = buttonColor;
        colors[15] = textFieldColor;
        colors[16] = textColor;
        for (int i=0;i<colorPickers.size();i++) {
            ((ColorPicker)colorPickers.get(i)).setValue(Color.web(colors[i]));
            ((ColorPicker)colorPickers.get(i)).setStyle("-fx-background-color: " + colors[i] + ";-fx-border-radius: 10;-fx-background-radius:10;-fx-border-color: " + textColor);

        }
    }
    public void setNodeStyle(String mode) {
        popup.setStyle("-fx-background-radius: 10;-fx-effect: dropshadow(gaussian,black,5,.1,0,0);-fx-background-color: " + contentBarColor);
        popupText.setStyle("-fx-background-color: transparent;-fx-text-fill: " + textColor);
        popupButton.setStyle("-fx-background-color: transparent;-fx-background-radius:8;-fx-text-fill: " + textColor);
        initializeColorPickers();
        allButtonHover();
        ObservableList<Node> colorPickerLabels = colorPickerLabelPane.getChildren();
        for (int i=0;i<colorPickerLabels.size();i++) {
            ((Label)colorPickerLabels.get(i)).setStyle("-fx-background-color: transparent;-fx-text-fill: " + textColor);
        }
        nameLabel.setStyle("-fx-background-color: transparent;-fx-text-fill: " + textColor);
        salaryLabel.setStyle("-fx-background-color: transparent;-fx-text-fill: " + textColor);
        positionLabel.setStyle("-fx-background-color: transparent;-fx-text-fill: " + textColor);
        countryLabel.setStyle("-fx-background-color: transparent;-fx-text-fill: " + textColor);
        darkModeTextLabel.setStyle("-fx-background-color: transparent;-fx-text-fill: " + textColor);
        lightModeTextLabel.setStyle("-fx-background-color: transparent;-fx-text-fill: " + textColor);
        totalYearlySalaryValue.setStyle("-fx-text-fill: " + textColor);
        profileClubText.setStyle("-fx-text-fill: " + textColor);
        clubImageLabel.setStyle("-fx-text-fill: " + textColor);
        clubNameText.setStyle("-fx-background-color: transparent;-fx-border-color: " + textFieldColor + ";-fx-border-width: 0 0 1 0;-fx-text-fill: " + textColor);
        playerNameText.setStyle("-fx-background-color: transparent;-fx-border-color: " + textFieldColor + ";-fx-border-width: 0 0 1 0;-fx-text-fill: " + textColor);
        playerCountryText.setStyle("-fx-background-color: transparent;-fx-border-color: " + textFieldColor + ";-fx-border-width: 0 0 1 0;-fx-text-fill: " + textColor);
        playerPositionText.setStyle("-fx-background-color: transparent;-fx-border-color: " + textFieldColor + ";-fx-border-width: 0 0 1 0;-fx-text-fill: " + textColor);
        minimumSalaryText.setStyle("-fx-background-color: transparent;-fx-border-color: " + textFieldColor + ";-fx-border-width: 0 0 1 0;-fx-text-fill: " + textColor);
        maximumSalaryText.setStyle("-fx-background-color: transparent;-fx-border-color: " + textFieldColor + ";-fx-border-width: 0 0 1 0;-fx-text-fill: " + textColor);
        backgroundPane.setStyle("-fx-background-color:" + backgroundColor);
        leftPane.setStyle("-fx-background-radius: 15;-fx-background-color:" + leftBarColor);
        smallHinge.setStyle("-fx-background-color: " + rightBarColor);
        functionRightPane.setStyle("-fx-background-color: " + rightBarColor + ";-fx-background-radius: 20;-fx-border-radius: 20;-fx-border-width: 2;-fx-border-color: " + leftBarColor);
        marketRightPane.setStyle("-fx-background-color: " + rightBarColor + ";-fx-background-radius: 20;-fx-border-radius: 20;-fx-border-width: 2;-fx-border-color: " + leftBarColor);
        settingsRightPane.setStyle("-fx-background-color: " + rightBarColor + ";-fx-background-radius: 20;-fx-border-radius: 20;-fx-border-width: 2;-fx-border-color: " + leftBarColor);
        searchContentPane.setStyle("-fx-background-color: " + contentPaneColor + ";-fx-background-radius: 20;-fx-border-radius: 20;-fx-border-width: 2;-fx-border-color: " + leftBarColor);
        marketContentPane.setStyle("-fx-background-color: " + contentPaneColor + ";-fx-background-radius: 20;-fx-border-radius: 20;-fx-border-width: 2;-fx-border-color: " + leftBarColor);
        settingsContentPane.setStyle("-fx-background-color: " + contentPaneColor + ";-fx-background-radius: 20;-fx-border-radius: 20;-fx-border-width: 2;-fx-border-color: " + leftBarColor);
        profileContentPane.setStyle("-fx-background-color: " + rightBarColor + ";-fx-background-radius: 20;-fx-border-radius: 20;-fx-border-width: 2;-fx-border-color: " + leftBarColor);
        playerSearchContentPane.setStyle("-fx-background-color: " + backgroundColor + ";-fx-background-radius: 20");
        clubSearchContentPane.setStyle("-fx-background-color: " + backgroundColor + ";-fx-background-radius: 20");
        themeContentPane.setStyle("-fx-background-color: " + contentPaneColor + ";-fx-background-radius: 20");
        customThemeContentPane.setStyle("-fx-background-color: " + contentPaneColor + ";-fx-background-radius: 20");
        buyContentPane.setStyle("-fx-background-color: " + contentPaneColor + ";-fx-background-radius: 20");
        sellContentPane.setStyle("-fx-background-color: " + contentPaneColor + ";-fx-background-radius: 20");
        playerMenu.setStyle("-fx-background-color: " + contentBarColor + ";-fx-background-radius: 20 20 0 0;-fx-border-radius: 20 20 0 0;-fx-border-width: 0 0 2 0;-fx-border-color: " + leftBarColor);
        clubMenu.setStyle("-fx-background-color: " + contentBarColor + ";-fx-background-radius: 20 20 0 0;-fx-border-radius: 20 20 0 0;-fx-border-width: 0 0 2 0;-fx-border-color: " + leftBarColor);
        playerSearchContainer.setStyle("-fx-background-color: " + contentPaneColor + ";-fx-background-radius: 0 0 20 20");
        clubSearchContainer.setStyle("-fx-background-color: " + contentPaneColor + ";-fx-background-radius: 0 0 20 20");
        byNameBox.setStyle("-fx-background-color: " + contentPaneColor);
        byCountryBox.setStyle("-fx-background-color: " + contentPaneColor);
        byPositionBox.setStyle("-fx-background-color: " + contentPaneColor);
        bySalaryBox.setStyle("-fx-background-color: " + contentPaneColor);
        countrywisePlayerCountBox.setStyle("-fx-background-color: " + contentPaneColor);
        maximumHeightBox.setStyle("-fx-background-color: " + contentPaneColor);
        maximumAgeBox.setStyle("-fx-background-color: " + contentPaneColor);
        maximumSalaryBox.setStyle("-fx-background-color: " + contentPaneColor);
        totalYearlySalaryPane.setStyle("-fx-background-radius: 0 0 20 20;-fx-background-color: " + contentPaneColor);
        buyBox.setStyle("-fx-background-color: " + contentPaneColor);
        sellBox.setStyle("-fx-background-color: " + contentPaneColor);
        initializeLabels(functionRightPane.getChildren(),false);
        initializeLabels(marketRightPane.getChildren(),false);
        initializeLabels(settingsRightPane.getChildren(),false);
        initializeLabels(playerMenu.getChildren(),true);
        initializeLabels(clubMenu.getChildren(),true);
        Event.fireEvent(settingsLabel, new MouseEvent(MouseEvent.MOUSE_CLICKED, 0,
                0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                true, true, true, true, true, true, null));
        if (mode.equalsIgnoreCase("theme")) Event.fireEvent(theme, new MouseEvent(MouseEvent.MOUSE_CLICKED, 0,
                0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                true, true, true, true, true, true, null));
        else if (mode.equalsIgnoreCase("custom theme")) Event.fireEvent(customTheme, new MouseEvent(MouseEvent.MOUSE_CLICKED, 0,
                0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                true, true, true, true, true, true, null));
    }
    public void initializeLabels(ObservableList<Node> labels, boolean menu) {
        for (int i=0;i<labels.size();i++) {
            if (menu) labels.get(i).setStyle("-fx-background-color: " + contentBarColor + ";-fx-background-radius:20;-fx-text-fill: " + contentBarFontColor + ";-fx-alignment: center;-fx-border-radius: 20;-fx-border-color:" + contentBarClickedColor+";-fx-border-width: 2");
            else labels.get(i).setStyle("-fx-background-color: "+rightBarColor+";-fx-text-fill: "+ rightBarFontColor +";-fx-alignment: center");
        }
    }
    public void connect() {
        wrongClubLabel.setVisible(false);
        String serverAddress = "127.0.0.1";
        int serverPort = 33335;
        String clubName = clubNameText.getText();
        client = new Client(serverAddress, serverPort, clubName,this);
    }
    public void disconnect() {
        try {
            client.networkUtil.write("disconnect");
        }
        catch (Exception e) {
            System.out.println(e);
        }
        logoutPane.setVisible(false);
        loginPane.setVisible(true);
        isConnected = false;
        Event.fireEvent(profileLabel, new MouseEvent(MouseEvent.MOUSE_CLICKED, 0,
                0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                true, true, true, true, true, true, null));
        db = new Database();
        buyPlayers = new ArrayList<>();
    }
    public void refreshBuyPlayers() {
        buyBox.getChildren().removeAll(buyBox.getChildren());
        addList(buyPlayers, buyBox, true);
    }
    public void sellPlayers() {
        sellBox.getChildren().removeAll(sellBox.getChildren());
        List<Player> playerList = new ArrayList<>();
        for (int i = 0; i < db.playerList.size(); i++) playerList.add(db.playerList.get(i));
        addList(playerList, sellBox, false);
    }
    public void sell(Player player) {
        try {
            client.networkUtil.write(player);
        }
        catch (Exception e) {
            System.out.println(e);
        }
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
    public void buyPlayer(Player player) {
        try {
            client.networkUtil.write(player);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    public void createPlayerCard(boolean buy, AnchorPane card, Player player, double top, double bottom, double left, double right) {
        card.setStyle("-fx-background-color: " + playerCardColor + ";-fx-background-radius: 25;-fx-effect: dropshadow(gaussian,black,10,.1,2,2)");
        AnchorPane.setTopAnchor(card,top);
        AnchorPane.setBottomAnchor(card,bottom);
        AnchorPane.setLeftAnchor(card,left);
        AnchorPane.setRightAnchor(card,right);
        Label img = new Label();
        String playerStyle = getPlayerStyle(player.getName());
        img.setStyle(playerStyle + ";-fx-background-size: 203 220;-fx-background-radius: 25 25 0 0");
        AnchorPane.setTopAnchor(img,0.0);
        AnchorPane.setBottomAnchor(img,70.0);
        AnchorPane.setLeftAnchor(img,0.0);
        AnchorPane.setRightAnchor(img,0.0);
        card.getChildren().add(img);
        Label clubImg = new Label();
        String clubStyle = getClubStyle(player.getClub());
        clubImg.setStyle(clubStyle + ";-fx-background-size: 30 30");
        AnchorPane.setTopAnchor(clubImg,10.0);
        AnchorPane.setBottomAnchor(clubImg,250.0);
        AnchorPane.setLeftAnchor(clubImg,163.0);
        AnchorPane.setRightAnchor(clubImg,10.0);
        card.getChildren().add(clubImg);
        Label number = new Label(""+player.getNumber());
        AnchorPane.setTopAnchor(number,5.0);
        AnchorPane.setBottomAnchor(number,255.0);
        AnchorPane.setLeftAnchor(number,10.0);
        AnchorPane.setRightAnchor(number,160.0);
        number.setStyle("-fx-background-color: transparent;-fx-text-fill: " + playerCardFontColor + ";-fx-font-size:20");
        card.getChildren().add(number);
        Label name = new Label(player.getName());
        AnchorPane.setTopAnchor(name,220.0);
        AnchorPane.setBottomAnchor(name,35.0);
        AnchorPane.setLeftAnchor(name,0.0);
        AnchorPane.setRightAnchor(name,0.0);
        name.setStyle("-fx-background-color: transparent;-fx-text-fill: " + playerCardFontColor + ";-fx-font-size:20;-fx-label-padding: 0 0 0 10");
        card.getChildren().add(name);
        Label position = new Label();
        if (buy) position.setText("Price: "+player.getSellPrice());
        else position.setText(player.getPosition());
        AnchorPane.setTopAnchor(position,250.0);
        AnchorPane.setBottomAnchor(position,10.0);
        AnchorPane.setLeftAnchor(position,10.0);
        AnchorPane.setRightAnchor(position,60.0);
        position.setStyle("-fx-background-color: transparent;-fx-text-fill: " + playerCardFontColor + ";-fx-font-size:15");
        card.getChildren().add(position);
        Label country = new Label();
        AnchorPane.setTopAnchor(country,255.0);
        AnchorPane.setBottomAnchor(country,15.0);
        AnchorPane.setLeftAnchor(country,150.0);
        AnchorPane.setRightAnchor(country,20.0);
        String style = getCountryStyle(player.getCountry());
        country.setStyle(style+";-fx-border-width: .1;-fx-border-color: black;-fx-background-size:38 21");
        card.getChildren().add(country);
    }
    public String getCountryStyle(String country) {
        String style = "";
        if (country.equalsIgnoreCase("spain")) style="-fx-background-image: url(img/flags/spain.png)";
        else if (country.equalsIgnoreCase("belgium")) style="-fx-background-image: url(img/flags/belgium.png)";
        else if (country.equalsIgnoreCase("brazil")) style="-fx-background-image: url(img/flags/brazil.png)";
        else if (country.equalsIgnoreCase("egypt")) style="-fx-background-image: url(img/flags/egypt.png)";
        else if (country.equalsIgnoreCase("england")) style="-fx-background-image: url(img/flags/england.png)";
        else if (country.equalsIgnoreCase("france")) style="-fx-background-image: url(img/flags/france.png)";
        else if (country.equalsIgnoreCase("gabon")) style="-fx-background-image: url(img/flags/gabon.png)";
        else if (country.equalsIgnoreCase("germany")) style="-fx-background-image: url(img/flags/germany.png)";
        else if (country.equalsIgnoreCase("ghana")) style="-fx-background-image: url(img/flags/ghana.png)";
        else if (country.equalsIgnoreCase("italy")) style="-fx-background-image: url(img/flags/italy.png)";
        else if (country.equalsIgnoreCase("netherlands")) style="-fx-background-image: url(img/flags/netherlands.png)";
        else if (country.equalsIgnoreCase("portugal")) style="-fx-background-image: url(img/flags/portugal.png)";
        else if (country.equalsIgnoreCase("senegal")) style="-fx-background-image: url(img/flags/senegal.png)";
        else if (country.equalsIgnoreCase("switzerland")) style="-fx-background-image: url(img/flags/switzerland.png)";
        else if (country.equalsIgnoreCase("uruguay")) style="-fx-background-image: url(img/flags/uruguay.png)";
        return style;
    }
    public String getClubStyle(String club) {
        String style = "";
        if (club.equalsIgnoreCase("manchester united")) style="-fx-background-image: url(img/clubs/manchesterunited.png)";
        else if (club.equalsIgnoreCase("manchester city")) style="-fx-background-image: url(img/clubs/manchestercity.png)";
        else if (club.equalsIgnoreCase("chelsea")) style="-fx-background-image: url(img/clubs/chelsea.png)";
        else if (club.equalsIgnoreCase("liverpool")) style="-fx-background-image: url(img/clubs/liverpool.png)";
        else if (club.equalsIgnoreCase("arsenal")) style="-fx-background-image: url(img/clubs/arsenal.png)";
        return style;
    }
    public String getPlayerStyle(String name) {
        String style = "";
        if (name.equalsIgnoreCase("edinson cavani")) style="-fx-background-image: url(img/players/edinson.png)";
        else if (name.equalsIgnoreCase("david de gea")) style="-fx-background-image: url(img/players/david.png)";
        else if (name.equalsIgnoreCase("paul pogba")) style="-fx-background-image: url(img/players/pogba.png)";
        else if (name.equalsIgnoreCase("bruno fernandes")) style="-fx-background-image: url(img/players/bruno.png)";
        else if (name.equalsIgnoreCase("harry maguire")) style="-fx-background-image: url(img/players/harry.png)";
        else if (name.equalsIgnoreCase("ederson")) style="-fx-background-image: url(img/players/ederson.png)";
        else if (name.equalsIgnoreCase("ruben dias")) style="-fx-background-image: url(img/players/ruben.png)";
        else if (name.equalsIgnoreCase("kevin de bruyne")) style="-fx-background-image: url(img/players/kevin.png)";
        else if (name.equalsIgnoreCase("ilkay gundogan")) style="-fx-background-image: url(img/players/ilkay.png)";
        else if (name.equalsIgnoreCase("raheem sterling")) style="-fx-background-image: url(img/players/raheem.png)";
        else if (name.equalsIgnoreCase("edouard mendy")) style="-fx-background-image: url(img/players/edouard.png)";
        else if (name.equalsIgnoreCase("cesar azpilicueta")) style="-fx-background-image: url(img/players/cesar.png)";
        else if (name.equalsIgnoreCase("n'golo kante")) style="-fx-background-image: url(img/players/ngolo.png)";
        else if (name.equalsIgnoreCase("jorginho")) style="-fx-background-image: url(img/players/jorginho.png)";
        else if (name.equalsIgnoreCase("timo werner")) style="-fx-background-image: url(img/players/timo.png)";
        else if (name.equalsIgnoreCase("alisson")) style="-fx-background-image: url(img/players/alisson.png)";
        else if (name.equalsIgnoreCase("virgil van dijk")) style="-fx-background-image: url(img/players/virgil.png)";
        else if (name.equalsIgnoreCase("jordan henderson")) style="-fx-background-image: url(img/players/jordan.png)";
        else if (name.equalsIgnoreCase("thiago alcantaram")) style="-fx-background-image: url(img/players/thiago.png)";
        else if (name.equalsIgnoreCase("mohamed salah")) style="-fx-background-image: url(img/players/mohamed.png)";
        else if (name.equalsIgnoreCase("bernd leno")) style="-fx-background-image: url(img/players/bernd.png)";
        else if (name.equalsIgnoreCase("hector bellerin")) style="-fx-background-image: url(img/players/hector.png)";
        else if (name.equalsIgnoreCase("granit xhaka")) style="-fx-background-image: url(img/players/granit.png)";
        else if (name.equalsIgnoreCase("thomas partey")) style="-fx-background-image: url(img/players/thomas.png)";
        else if (name.equalsIgnoreCase("pierre-emerick aubameyang")) style="-fx-background-image: url(img/players/pierre.png)";
        return style;
    }
    public Label createField(String value,double top, double bottom) {
        Label label = new Label(value);
        AnchorPane.setTopAnchor(label,top);
        AnchorPane.setBottomAnchor(label,bottom);
        AnchorPane.setLeftAnchor(label,0.0);
        AnchorPane.setRightAnchor(label,0.0);
        label.setStyle("-fx-background-color: transparent;-fx-text-fill: " + playerCardFontColor + ";-fx-font-size:16;-fx-label-padding: 0 0 0 10");
        return label;
    }
    public void mouseHover(AnchorPane card,Player player) {
        card.getChildren().removeAll(card.getChildren());
        Label name = createField("Name: "+player.getName(),25.0+30.0*0,265.0-30.0*1);
        card.getChildren().add(name);
        Label country = createField("Country: "+player.getCountry(),25.0+30.0*1,265.0-30.0*2);
        card.getChildren().add(country);
        Label age = createField("Age: "+player.getAge(),25.0+30.0*2,265.0-30.0*3);
        card.getChildren().add(age);
        Label height = createField("Height: "+player.getHeight(),25.0+30.0*3,265.0-30.0*4);
        card.getChildren().add(height);
        Label club = createField("Club: "+player.getClub(),25.0+30.0*4,265.0-30.0*5);
        card.getChildren().add(club);
        Label position = createField("Position: "+player.getPosition(),25.0+30.0*5,265.0-30.0*6);
        card.getChildren().add(position);
        Label number = createField("Number: "+player.getNumber(),25.0+30.0*6,265.0-30.0*7);
        card.getChildren().add(number);
        Label salary = createField("Salary: "+player.getWeeklySalary(),25.0+30.0*7,265.0-30.0*8);
        card.getChildren().add(salary);
    }
    public void playerNameReset() {
        ObservableList<Node> nodes = byNameBox.getChildren();
        playerNameText.setText("");
        int size = nodes.size();
        for (int i=1;i<size;i++) {
            byNameBox.getChildren().remove(1);
        }
    }
    public void playerPositionReset() {
        ObservableList<Node> nodes = byPositionBox.getChildren();
        playerPositionText.setText("");
        int size = nodes.size();
        for (int i=1;i<size;i++) {
            byPositionBox.getChildren().remove(1);
        }
    }
    public void playerCountryReset() {
        ObservableList<Node> nodes = byCountryBox.getChildren();
        playerCountryText.setText("");
        int size = nodes.size();
        for (int i=1;i<size;i++) {
            byCountryBox.getChildren().remove(1);
        }
    }
    public void playerSalaryReset() {
        ObservableList<Node> nodes = bySalaryBox.getChildren();
        minimumSalaryText.setText("");
        maximumSalaryText.setText("");
        int size = nodes.size();
        for (int i=1;i<size;i++) {
            bySalaryBox.getChildren().remove(1);
        }
    }
    public void playerSalarySearch() {
        ObservableList<Node> nodes = bySalaryBox.getChildren();
        int size = nodes.size();
        for (int i=1;i<size;i++) {
            bySalaryBox.getChildren().remove(1);
        }
        double minSalary,maxSalary;
        try {
            maxSalary = Double.parseDouble(maximumSalaryText.getText());
        }
        catch (Exception e) {
            maxSalary = 0;
        }
        try {
            minSalary = Double.parseDouble(minimumSalaryText.getText());
        }
        catch(Exception e) {
            minSalary = maxSalary+1;
        }
        List<Player> playerList = db.searchBySalaryRange(minSalary,maxSalary);
        if (playerList.size()>0) addList(playerList,bySalaryBox,false);
        else {
            AnchorPane pane = new AnchorPane();
            pane.setPrefHeight(200);
            pane.setMinHeight(200);
            pane.setStyle("-fx-background-color: transparent");
            Label status = new Label("No such player in this weekly salary range");
            status.setStyle("-fx-background-color: transparent;-fx-font-size: 20;-fx-alignment: center;-fx-text-fill: " + textColor);
            AnchorPane.setTopAnchor(status,20.0);
            AnchorPane.setBottomAnchor(status,20.0);
            AnchorPane.setLeftAnchor(status,200.0);
            AnchorPane.setRightAnchor(status,200.0);
            pane.getChildren().add(status);
            bySalaryBox.getChildren().add(pane);
        }
    }
    public void playerCountrySearch() {
        ObservableList<Node> nodes = byCountryBox.getChildren();
        int size = nodes.size();
        for (int i=1;i<size;i++) {
            byCountryBox.getChildren().remove(1);
        }
        List<Player> playerList = db.searchByClubAndCountry(playerCountryText.getText(),"any");
        if (playerList.size()>0) addList(playerList,byCountryBox,false);
        else {
            AnchorPane pane = new AnchorPane();
            pane.setPrefHeight(200);
            pane.setMinHeight(200);
            pane.setStyle("-fx-background-color: transparent");
            Label status = new Label("No such player with this country");
            status.setStyle("-fx-background-color: transparent;-fx-font-size: 20;-fx-alignment: center;-fx-text-fill: " + textColor);
            AnchorPane.setTopAnchor(status,20.0);
            AnchorPane.setBottomAnchor(status,20.0);
            AnchorPane.setLeftAnchor(status,200.0);
            AnchorPane.setRightAnchor(status,200.0);
            pane.getChildren().add(status);
            byCountryBox.getChildren().add(pane);
        }
    }
    public void playerPositionSearch() {
        ObservableList<Node> nodes = byPositionBox.getChildren();
        int size = nodes.size();
        for (int i=1;i<size;i++) {
            byPositionBox.getChildren().remove(1);
        }
        List<Player> playerList = db.searchByPosition(playerPositionText.getText());
        if (playerList.size()>0) addList(playerList,byPositionBox,false);
        else {
            AnchorPane pane = new AnchorPane();
            pane.setPrefHeight(200);
            pane.setMinHeight(200);
            pane.setStyle("-fx-background-color: transparent");
            Label status = new Label("No such player with this position");
            status.setStyle("-fx-background-color: transparent;-fx-font-size: 20;-fx-alignment: center;-fx-text-fill: " + textColor);
            AnchorPane.setTopAnchor(status,20.0);
            AnchorPane.setBottomAnchor(status,20.0);
            AnchorPane.setLeftAnchor(status,200.0);
            AnchorPane.setRightAnchor(status,200.0);
            pane.getChildren().add(status);
            byPositionBox.getChildren().add(pane);
        }
    }
    public void playerNameSearch() {
        ObservableList<Node> nodes = byNameBox.getChildren();
        int size = nodes.size();
        for (int i=1;i<size;i++) {
            byNameBox.getChildren().remove(1);
        }
        Player player = db.searchByPlayerName(playerNameText.getText());
        List<Player> playerList = new ArrayList();
        if (player==null) {
            AnchorPane pane = new AnchorPane();
            pane.setPrefHeight(200);
            pane.setMinHeight(200);
            pane.setStyle("-fx-background-color: transparent");
            Label status = new Label("No such player with this name");
            status.setStyle("-fx-background-color: transparent;-fx-font-size: 20;-fx-alignment: center;-fx-text-fill: " + textColor);
            AnchorPane.setTopAnchor(status,20.0);
            AnchorPane.setBottomAnchor(status,20.0);
            AnchorPane.setLeftAnchor(status,200.0);
            AnchorPane.setRightAnchor(status,200.0);
            pane.getChildren().add(status);
            byNameBox.getChildren().add(pane);
        }
        else {
            playerList.add(player);
            addList(playerList,byNameBox,false);
        }
    }
    void sellCard(AnchorPane card, AnchorPane playerCard, Player player) {
        playerCard.setVisible(false);
        card.getChildren().removeAll(card.getChildren());
        card.setOnMouseExited(mouseEvent -> {});
        TextField option3 = new TextField();
        option3.setPromptText("Enter price");
        option3.setStyle("-fx-background-color: transparent;-fx-font-size: 16;-fx-border-width: 0 0 1 0;-fx-border-color: " + playerCardColor + ";-fx-text-fill: " + playerCardColor);
        AnchorPane.setTopAnchor(option3,20.0+50.0);
        AnchorPane.setBottomAnchor(option3,320.0-50.0);
        AnchorPane.setLeftAnchor(option3,20.0);
        AnchorPane.setRightAnchor(option3,20.0);
        card.getChildren().add(option3);
        Label option = new Label("Go back");
        option.setStyle("-fx-background-color: " + playerCardColor + ";-fx-background-radius:10;-fx-text-fill: " + playerCardClickedColor + ";-fx-font-size:16;-fx-label-padding: 0 0 0 0;-fx-alignment: center");
        AnchorPane.setTopAnchor(option,320.0);
        AnchorPane.setBottomAnchor(option,20.0);
        AnchorPane.setLeftAnchor(option,20.0);
        AnchorPane.setRightAnchor(option,20.0);
        card.getChildren().add(option);
        option.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                card.getChildren().removeAll(card.getChildren());
                playerCard.setVisible(true);
                card.setStyle("-fx-background-color: transparent");
                card.setOnMouseExited(mouseEvent1 -> {
                    card.getChildren().removeAll(card.getChildren());
                    card.setStyle("-fx-background-color: transparent");
                });
            }
        });
        Label option1 = new Label("Sell");
        option1.setStyle("-fx-background-color: " + playerCardColor + ";-fx-background-radius:10;-fx-text-fill: " + playerCardClickedColor + ";-fx-font-size:16;-fx-label-padding: 0 0 0 0;-fx-alignment: center");
        AnchorPane.setTopAnchor(option1,320.0-60.0);
        AnchorPane.setBottomAnchor(option1,20.0+60.0);
        AnchorPane.setLeftAnchor(option1,20.0);
        AnchorPane.setRightAnchor(option1,20.0);
        card.getChildren().add(option1);
        option1.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                player.setInSellQueue(true);
                double sellPrice;
                try {
                    sellPrice = Double.parseDouble(option3.getText());
                } catch (Exception e) {
                    sellPrice = 0;
                }
                player.setSellPrice(sellPrice);
                sell(player);
                card.getChildren().removeAll(card.getChildren());
                playerCard.setVisible(true);
                card.setStyle("-fx-background-color: transparent");
                card.setOnMouseExited(mouseEvent1 -> {
                    card.getChildren().removeAll(card.getChildren());
                    card.setStyle("-fx-background-color: transparent");
                });
            }
        });
        //Label option2 = new Label("Sell price");
        //option2.setStyle("-fx-background-color: transparent;-fx-text-fill: " + playerCardColor + ";-fx-font-size:20;-fx-label-padding: 0 0 0 0;-fx-alignment: center");
        //AnchorPane.setTopAnchor(option2,20.0);
        //AnchorPane.setBottomAnchor(option2,320.0);
        //AnchorPane.setLeftAnchor(option2,20.0);
        //AnchorPane.setRightAnchor(option2,20.0);
        //card.getChildren().add(option2);
    }
    void networkOption(AnchorPane card, AnchorPane playerCard, Player player, boolean buy) {
        card.setStyle("-fx-background-color: " + playerCardClickedColor + ";-fx-background-radius: 25");
        Label option = new Label();
        if (buy) option.setText("Buy");
        else {
            if (player.isInSellQueue()) option.setText("Sell in progess");
            else option.setText("Sell");
        }
        option.setStyle("-fx-background-color: " + playerCardColor + ";-fx-background-radius:10;-fx-text-fill: " + playerCardClickedColor + ";-fx-font-size:16;-fx-label-padding: 0 0 0 0;-fx-alignment: center");
        AnchorPane.setTopAnchor(option,320.0);
        AnchorPane.setBottomAnchor(option,20.0);
        AnchorPane.setLeftAnchor(option,20.0);
        AnchorPane.setRightAnchor(option,20.0);
        card.getChildren().add(option);
        option.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                if (buy) {
                    buyPlayer(player);
                } else {
                    if (!player.isInSellQueue()) {
                        sellCard(card, playerCard, player);
                        //System.out.println("request for sell");
                    }
                }
            }
        });
    }
    public void addCountrywisePlayerCount(List<Country> countries,VBox box) {
        for (int i=0;i<countries.size();i++) {
            AnchorPane pane = new AnchorPane();
            pane.setPrefHeight(50);
            pane.setMinHeight(50);
            pane.setStyle("-fx-background-color: transparent");
            Label country = new Label(countries.get(i).getName());
            country.setStyle("-fx-background-color: transparent;-fx-font-size: 20;-fx-text-fill: " + textColor);
            AnchorPane.setTopAnchor(country,0.0);
            AnchorPane.setBottomAnchor(country,0.0);
            AnchorPane.setLeftAnchor(country,300.0);
            AnchorPane.setRightAnchor(country,400.0);
            pane.getChildren().add(country);
            Label countryCount = new Label(""+countries.get(i).getPlayerCount());
            countryCount.setStyle("-fx-background-color: transparent;-fx-font-size: 20;-fx-text-fill: " + textColor);
            AnchorPane.setTopAnchor(countryCount,0.0);
            AnchorPane.setBottomAnchor(countryCount,0.0);
            AnchorPane.setLeftAnchor(countryCount,500.0);
            AnchorPane.setRightAnchor(countryCount,250.0);
            pane.getChildren().add(countryCount);
            box.getChildren().add(pane);
        }
    }
    public void addList(List<Player> playerList,VBox box, boolean buy) {
        double top=30;
        double bottom=80;
        double cardwidth = 294;
        for (int i=0;i<playerList.size();i+=3) {
            AnchorPane pane = new AnchorPane();
            pane.setPrefHeight(400);
            pane.setMinHeight(400);
            pane.setStyle("-fx-background-color: transparent");
            for (int j=0;j<3;j++) {
                if (i+j<playerList.size()) {
                    AnchorPane cardNet = new AnchorPane();
                    AnchorPane card1 = new AnchorPane();
                    pane.getChildren().add(cardNet);
                    AnchorPane.setTopAnchor(cardNet,top-20);
                    AnchorPane.setBottomAnchor(cardNet,bottom-70);
                    AnchorPane.setLeftAnchor(cardNet,(72 / 3.0) * (3 - j) + (cardwidth * j)-20);
                    AnchorPane.setRightAnchor(cardNet,cardwidth * (2 - j) + (72 / 3.0) * (j + 1)-20);
                    createPlayerCard(buy,card1, playerList.get(i+j), top, bottom, (72 / 3.0) * (3 - j) + cardwidth * j, cardwidth * (2 - j) + (72 / 3.0) * (j + 1));
                    pane.getChildren().add(card1);
                    int finalI = i;
                    int finalJ = j;
                    card1.setOnMouseEntered(mouseEvent -> {
                        //System.out.println("prev " + playerList.size() + " " + (finalI+finalJ));
                        mouseHover(card1, playerList.get(finalI+finalJ));
                        //System.out.println("afte " + playerList.size() + " " + (finalI+finalJ));
                        networkOption(cardNet,card1,playerList.get(finalI+finalJ),buy);//,top,bottom-40,(72 / 3.0) * (3 - finalJ) + cardwidth * finalJ, cardwidth * (2 - finalJ) + (72 / 3.0) * (finalJ + 1));
                    });
                    card1.setOnMouseExited(mouseEvent -> {
                        card1.getChildren().removeAll(card1.getChildren());
                        createPlayerCard(buy,card1, playerList.get(finalI+finalJ), top, bottom, (72 / 3.0) * (3 - finalJ) + cardwidth * finalJ, cardwidth * (2 - finalJ) + (72 / 3.0) * (finalJ + 1));
                    });
                    cardNet.setOnMouseExited(mouseEvent -> {
                        cardNet.getChildren().removeAll(cardNet.getChildren());
                        cardNet.setStyle("-fx-background-color: transparent");
                    });
                }
            }
            box.getChildren().add(pane);
        }
    }
    public void onLabelClick(AnchorPane pane, ObservableList<Node> panes) {
        for (int i=0;i<panes.size();i++) {
            if (pane == (AnchorPane) panes.get(i)) {
                pane.setVisible(true);
            }
            else {
                ((AnchorPane) panes.get(i)).setVisible(false);
            }
        }
    }
    public String connectImageStyle() {
        return "-fx-background-image: url(img/club.png);-fx-background-size: 376 376";
    }
    public void buttonHover(Label label) {
        label.setStyle("-fx-background-color: transparent;-fx-background-radius:10;-fx-alignment: center;-fx-border-color: " + buttonColor + ";-fx-border-radius: 10;-fx-text-fill: " + buttonColor);
        label.setOnMouseEntered(mouseEvent -> {
            label.setStyle("-fx-background-color: " + buttonColor + ";-fx-background-radius: 10;-fx-alignment: center;-fx-text-fill: white;");
        });
        label.setOnMouseExited(mouseEvent -> {
            label.setStyle("-fx-background-color: transparent;-fx-background-radius:10;-fx-alignment: center;-fx-border-color: " + buttonColor + ";-fx-border-radius: 10;-fx-text-fill: " + buttonColor);
        });
    }
    public void allButtonHover() {
        buttonHover(connectLabel);
        buttonHover(disconnectLabel);
        buttonHover(darkModeLabel);
        buttonHover(lightModeLabel);
        buttonHover(playerNameSearchLabel);
        buttonHover(playerNameResetLabel);
        buttonHover(playerCountrySearchLabel);
        buttonHover(playerCountryResetLabel);
        buttonHover(playerPositionSearchLabel);
        buttonHover(playerPositionResetLabel);
        buttonHover(playerSalarySearchLabel);
        buttonHover(playerSalaryResetLabel);
    }
    public void buttonStyleLoad() {
        connectImage.setStyle(connectImageStyle());
        allButtonHover();
        popupButton.setOnMouseEntered(mouseEvent -> {
            popupButton.setStyle("-fx-background-color: red;-fx-background-radius:8;-fx-text-fill: white");
        });
        popupButton.setOnMouseExited(mouseEvent -> {
            popupButton.setStyle("-fx-background-color: transparent;-fx-background-radius:8;-fx-text-fill: " + textColor);
        });
        popupButton.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                popup.setVisible(false);
            }
        });
        connectLabel.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) connect();
        });
        disconnectLabel.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) disconnect();
        });
        logoutPane.setVisible(false);
        loginPane.setVisible(true);
        darkModeLabel.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                darkModeLabel.setVisible(false);
                lightModeLabel.setVisible(true);
                darkTheme();
            }
        });
        lightModeLabel.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                lightModeLabel.setVisible(false);
                darkModeLabel.setVisible(true);
                lightTheme();
            }
        });
        playerNameSearchLabel.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                playerNameSearch();
            }
        });
        playerNameResetLabel.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                playerNameReset();
            }
        });
        playerCountrySearchLabel.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                playerCountrySearch();
            }
        });
        playerCountryResetLabel.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                playerCountryReset();
            }
        });
        playerPositionSearchLabel.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                playerPositionSearch();
            }
        });
        playerPositionResetLabel.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                playerPositionReset();
            }
        });
        playerSalarySearchLabel.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                playerSalarySearch();
            }
        });
        playerSalaryResetLabel.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                playerSalaryReset();
            }
        });
        initializeColorPickersListerner();
    }
    public void initializeColorPickersListerner() {
        ObservableList<Node> colorPickers = colorPickerPane.getChildren();
        for (int i=0;i<colorPickers.size();i++) {
            ((ColorPicker)colorPickers.get(i)).getStyleClass().add("button");
            ((ColorPicker)colorPickers.get(i)).setOnAction(actionEvent -> {
                customTheme();
            });
        }
    }
    public void start() {
        wrongClubLabel.setText("You have entered an unregistered club name");
        wrongClubLabel.setStyle("-fx-background-color: transparent;-fx-text-fill: red;-fx-label-padding: 0 0 0 8");
        wrongClubLabel.setVisible(false);
        isConnected = false;
        lightTheme();
        lightModeLabel.setVisible(false);
        buttonStyleLoad();
        ObservableList<Node> labels = leftPane.getChildren();
        for (int i=0;i< labels.size();i++) {
            if (labels.get(i) instanceof AnchorPane) {
                ((AnchorPane)labels.get(i)).setStyle("-fx-background-color: white");
                continue;
            }
            else if (((Label)labels.get(i)).getId().equalsIgnoreCase("topLabel") || ((Label)labels.get(i)).getId().equalsIgnoreCase("bottomLabel")) {
                ((Label)labels.get(i)).setStyle("-fx-background-color:" + leftBarColor +";-fx-background-radius: 25 25 25 25");
                continue;
            }
            setLeftBarStyle((Label)labels.get(i),labels,i);
        }
        ObservableList<Node> functionlabels = functionRightPane.getChildren();
        ObservableList<Node> panes = searchContentPane.getChildren();
        AnchorPane pane = null ;
        for (int i=0;i<functionlabels.size();i++) {
            if (((Label)functionlabels.get(i)).getId().equalsIgnoreCase("searchPlayers")) pane = playerSearchContentPane;
            else if (((Label)functionlabels.get(i)).getId().equalsIgnoreCase("searchClubs")) pane = clubSearchContentPane;
            setRightBarStyle(functionlabels,(Label)functionlabels.get(i),panes,pane);
        }
        ObservableList<Node> marketlabels = marketRightPane.getChildren();
        panes = marketContentPane.getChildren();
        pane =null;
        for (int i=0;i<marketlabels.size();i++) {
            if (((Label)marketlabels.get(i)).getId().equalsIgnoreCase("buy")) pane = buyContentPane;
            else if (((Label)marketlabels.get(i)).getId().equalsIgnoreCase("sell")) pane = sellContentPane;
            setRightBarStyle(marketlabels,(Label)marketlabels.get(i),panes,pane);
        }
        ObservableList<Node> settingslabels = settingsRightPane.getChildren();
        panes = settingsContentPane.getChildren();
        pane =null;
        for (int i=0;i<settingslabels.size();i++) {
            if (((Label)settingslabels.get(i)).getId().equalsIgnoreCase("theme")) pane = themeContentPane;
            else if (((Label)settingslabels.get(i)).getId().equalsIgnoreCase("customTheme")) pane = customThemeContentPane;
            setRightBarStyle(settingslabels,(Label)settingslabels.get(i),panes,pane);
        }
        ObservableList<Node> playerMenuLabels = playerMenu.getChildren();
        panes = playerSearchContainer.getChildren();
        pane = null;
        for (int i=0;i<playerMenuLabels.size();i++) {
            if (((Label)playerMenuLabels.get(i)).getId().equalsIgnoreCase("byName")) pane = byNamePane;
            else if (((Label)playerMenuLabels.get(i)).getId().equalsIgnoreCase("byClubandCountry")) pane = byClubandCountryPane;
            else if (((Label)playerMenuLabels.get(i)).getId().equalsIgnoreCase("byPosition")) pane = byPositionPane;
            else if (((Label)playerMenuLabels.get(i)).getId().equalsIgnoreCase("bySalary")) pane = bySalaryPane;
            else if (((Label)playerMenuLabels.get(i)).getId().equalsIgnoreCase("countrywisePlayerCount")) pane = countrywisePlayerCountPane;
            setMenuLabelStyle(playerMenuLabels,(Label)playerMenuLabels.get(i),panes,pane);
        }
        ObservableList<Node> clubMenuLabels = clubMenu.getChildren();
        panes = clubSearchContainer.getChildren();
        pane = null;
        for (int i=0;i<clubMenuLabels.size();i++) {
            if (((Label)clubMenuLabels.get(i)).getId().equalsIgnoreCase("maximumSalary")) pane = maximumSalaryPane;
            else if (((Label)clubMenuLabels.get(i)).getId().equalsIgnoreCase("maximumAge")) pane = maximumAgePane;
            else if (((Label)clubMenuLabels.get(i)).getId().equalsIgnoreCase("maximumHeight")) pane = maximumHeightPane;
            else if (((Label)clubMenuLabels.get(i)).getId().equalsIgnoreCase("totalYearlySalary")) pane = totalYearlySalaryPane;
            setMenuLabelStyle(clubMenuLabels,(Label)clubMenuLabels.get(i),panes,pane);
        }
        Event.fireEvent(profileLabel, new MouseEvent(MouseEvent.MOUSE_CLICKED, 0,
                0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                true, true, true, true, true, true, null));
    }
    public void setMenuLabelStyle(ObservableList<Node> labels, Label label,ObservableList<Node> panes, AnchorPane pane) {
        label.setStyle("-fx-background-color: " + contentBarColor + ";-fx-background-radius:20;-fx-text-fill: " + contentBarFontColor + ";-fx-alignment: center;-fx-border-radius: 20;-fx-border-color:" + contentBarClickedColor+";-fx-border-width: 2");
        label.setOnMouseEntered(mouseEvent -> {
            label.setStyle("-fx-background-color: " + contentBarClickedColor + ";-fx-background-radius:20;-fx-text-fill: " + contentBarFontClickedColor + ";-fx-alignment: center");
        });
        label.setOnMouseExited(mouseEvent -> {
            label.setStyle("-fx-background-color: " + contentBarColor + ";-fx-background-radius:20;-fx-text-fill: " + contentBarFontColor + ";-fx-alignment: center;-fx-border-radius: 20;-fx-border-color:" + contentBarClickedColor+";-fx-border-width: 2");
        });
        label.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                label.setStyle("-fx-background-color: " + contentBarClickedColor + ";-fx-background-radius:20;-fx-text-fill: " + contentBarFontClickedColor + ";-fx-alignment: center");
                onLabelClick(pane, panes);
                if (label.getId().equalsIgnoreCase("maximumSalary")) {
                    maximumSalaryBox.getChildren().removeAll(maximumSalaryBox.getChildren());
                    List<Player> playerList = new ArrayList<>();
                    for (int i=0;i<db.clubs.size();i++) {
                        playerList = db.clubs.get(0).maximumSalaryPlayers();
                    }
                    addList(playerList, maximumSalaryBox, false);
                } else if (label.getId().equalsIgnoreCase("maximumAge")) {
                    maximumAgeBox.getChildren().removeAll(maximumAgeBox.getChildren());
                    List<Player> playerList = new ArrayList<>();
                    for (int i=0;i<db.clubs.size();i++) {
                        playerList = db.clubs.get(0).maximumAgePlayers();
                    }
                    addList(playerList, maximumAgeBox, false);
                } else if (label.getId().equalsIgnoreCase("maximumHeight")) {
                    maximumHeightBox.getChildren().removeAll(maximumHeightBox.getChildren());
                    List<Player> playerList = new ArrayList<>();
                    for (int i=0;i<db.clubs.size();i++) {
                        playerList = db.clubs.get(0).maximumHeightPlayers();
                    }
                    addList(playerList, maximumHeightBox, false);
                } else if (label.getId().equalsIgnoreCase("totalYearlySalary")) {
                    double totalYearlySalary = 0;
                    for (int i=0;i<db.clubs.size();i++) {
                        totalYearlySalary = db.clubs.get(0).totalYearlySalary();
                    }
                    totalYearlySalaryValue.setText(String.format("Total yearly salary %.2f", totalYearlySalary));
                } else if (label.getId().equalsIgnoreCase("countrywisePlayerCount")) {
                    ObservableList<Node> nodes = countrywisePlayerCountBox.getChildren();
                    int size = nodes.size();
                    for (int i = 1; i < size; i++) {
                        countrywisePlayerCountBox.getChildren().remove(1);
                    }
                    List<Country> countries = db.countries;
                    addCountrywisePlayerCount(countries, countrywisePlayerCountBox);
                }
                for (int i = 0; i < labels.size(); i++) {
                    if (label == (Label) labels.get(i)) label.setOnMouseExited(mouseEvent1 -> {
                    });
                    else {
                        Label label1 = (Label) labels.get(i);
                        label1.setStyle("-fx-background-color: " + contentBarColor + ";-fx-background-radius:20;-fx-text-fill: " + contentBarFontColor + ";-fx-alignment: center;-fx-border-radius: 20;-fx-border-color:" + contentBarClickedColor + ";-fx-border-width: 2");
                        label1.setOnMouseExited(mouseEvent1 -> {
                            label1.setStyle("-fx-background-color: " + contentBarColor + ";-fx-background-radius:20;-fx-text-fill: " + contentBarFontColor + ";-fx-alignment: center;-fx-border-radius: 20;-fx-border-color:" + contentBarClickedColor + ";-fx-border-width: 2");
                        });
                    }
                }
            }
        });
    }
    public void setRightBarStyle(ObservableList<Node> labels, Label label,ObservableList<Node> panes, AnchorPane pane) {
        label.setStyle("-fx-background-color: "+rightBarColor+";-fx-text-fill: "+ rightBarFontColor +";-fx-alignment: center");
        label.setOnMouseEntered(mouseEvent -> {
            label.setStyle("-fx-background-color: "+rightBarClickedColor+";-fx-text-fill: "+ rightBarFontClickedColor+";-fx-alignment: center");
        });
        label.setOnMouseExited(mouseEvent -> {
            label.setStyle("-fx-background-color: "+rightBarColor+";-fx-text-fill: "+ rightBarFontColor+";-fx-alignment: center");
        });
        label.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                label.setStyle("-fx-background-color: " + rightBarClickedColor + ";-fx-text-fill: " + rightBarFontClickedColor + ";-fx-alignment: center");

                if (label.getId().equalsIgnoreCase("buy")) {
                    refreshBuyPlayers();
                }
                else if (label.getId().equalsIgnoreCase("sell")) {
                    sellPlayers();
                }
                onLabelClick(pane, panes);
                for (int i = 0; i < labels.size(); i++) {
                    if (label == (Label) labels.get(i)) label.setOnMouseExited(mouseEvent1 -> {
                    });
                    else {
                        Label label1 = (Label) labels.get(i);
                        label1.setStyle("-fx-background-color: " + rightBarColor + ";-fx-text-fill: " + rightBarFontColor + ";-fx-alignment: center");
                        label1.setOnMouseExited(mouseEvent1 -> {
                            label1.setStyle("-fx-background-color: " + rightBarColor + ";-fx-text-fill: " + rightBarFontColor + ";-fx-alignment: center");
                        });
                    }
                }
            }
        });
    }
    public void setLeftBarStyle(Label label, ObservableList<Node> labels, int j) {
        String[] style = getStyle(label);
        label.setStyle("-fx-background-color: "+leftBarColor+";-fx-background-radius: 25 25 25 25;"+style[0]);
        label.setOnMouseEntered(mouseEvent -> {
            String[] style1 = getStyle(label);
            label.setStyle("-fx-background-color: "+leftBarClickedColor+";-fx-background-radius: 25 25 25 25;"+ style1[1]);
            label.getTooltip().setShowDelay(Duration.seconds(.2));
        });
        label.setOnMouseExited(mouseEvent -> {
            String[] style1 = getStyle(label);
            label.setStyle("-fx-background-color: "+leftBarColor+";-fx-background-radius: 25 25 25 25;"+style1[0]);
        });
        label.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                String[] style1 = getStyle(label);
                label.setStyle("-fx-background-color:" + leftBarClickedColor + ";-fx-background-radius: 25 25 25 25;" + style1[1]);
                //curveLabel((Label) labels.get(j - 1), true, leftPaneColor);
                //curveLabel((Label) labels.get(j + 1), false, leftPaneColor);
                ObservableList<Node> panes = rightPane.getChildren();
                AnchorPane pane = null;
                if (label.getId().equalsIgnoreCase("profilelabel")) pane = profilePane;
                else if (label.getId().equalsIgnoreCase("functionlabel")) pane = functionPane;
                else if (label.getId().equalsIgnoreCase("marketlabel")) pane = marketPane;
                else if (label.getId().equalsIgnoreCase("settingslabel")) pane = settingsPane;
                onLabelClick(pane, panes);
                for (int i = 0; i < labels.size(); i++) {
                    if (labels.get(i) instanceof AnchorPane) {
                        AnchorPane anchorPane = (AnchorPane) labels.get(i);
                        //anchorPane.setStyle("-fx-background-color: " + rightBarColor);
                        double bottomAnchor = 0, topAnchor = 0;
                        if (j == 2) {
                            topAnchor = 190;
                            bottomAnchor = 370;
                        } else if (j == 3) {
                            topAnchor = 250;
                            bottomAnchor = 310;
                        } else if (j == 4) {
                            topAnchor = 310;
                            bottomAnchor = 250;
                        } else if (j == 5) {
                            topAnchor = 370;
                            bottomAnchor = 190;
                        }
                        leftPane.setTopAnchor(anchorPane, topAnchor);
                        leftPane.setBottomAnchor(anchorPane, bottomAnchor);
                        continue;
                    }
                    else if (((Label) labels.get(i)).getId().equalsIgnoreCase("topLabel") || ((Label) labels.get(i)).getId().equalsIgnoreCase("bottomLabel")) {
                        ((Label)labels.get(i)).setStyle("-fx-background-color:" + leftBarColor +";-fx-background-radius: 25 25 25 25");
                    }
                    else if (label == (Label) labels.get(i)) label.setOnMouseExited(mouseEvent1 -> { });
                    else {
                        Label label1 = (Label) labels.get(i);
                        String[] newStyle = getStyle(label1);
                        label1.setStyle("-fx-background-color: " + leftBarColor + ";-fx-background-radius: 25 25 25 25;" + newStyle[0]);
                        int finalI = i;
                        label1.setOnMouseExited(mouseEvent1 -> {
                            label1.setStyle("-fx-background-color:" + leftBarColor + ";-fx-background-radius: 25 25 25 25;" + newStyle[0]);
                        });
                    }
                }
            }
        });
    }
    public String[] getStyle(Label label) {
        String[] style= new String[2];
        if (label.getId().equalsIgnoreCase("profileLabel")) {
            if (isConnected) {
                style[0] = "-fx-background-image: url(img/profile.png);-fx-background-size: 70 70";
                style[1] = "-fx-background-image: url(img/profileclick.png);-fx-background-size: 70 70";
            }
            else {
                style[0] = "-fx-background-image: url(img/profiledisconnect.png);-fx-background-size: 70 70";
                style[1] = "-fx-background-image: url(img/profiledisconnectclick.png);-fx-background-size: 70 70";
            }
        }
        else if (label.getId().equalsIgnoreCase("functionLabel")) {
            style[0] = "-fx-background-image: url(img/function.png);-fx-background-size: 70 70";
            style[1] = "-fx-background-image: url(img/functionclick.png);-fx-background-size: 70 70";
        }
        else if (label.getId().equalsIgnoreCase("marketLabel")) {
            style[0] = "-fx-background-image: url(img/market.png);-fx-background-size: 70 70";
            style[1] = "-fx-background-image: url(img/marketclick.png);-fx-background-size: 70 70";
        }
        else if (label.getId().equalsIgnoreCase("settingsLabel")) {
            style[0] = "-fx-background-image: url(img/settings.png);-fx-background-size: 70 70";
            style[1] = "-fx-background-image: url(img/settingsclick.png);-fx-background-size: 70 70";
        }
        return style;
    }
}
