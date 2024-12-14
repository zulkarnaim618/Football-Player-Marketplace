module myjfx {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    opens sample to javafx.graphics, javafx.fxml;
    opens img;
    opens img.clubs;
    opens img.flags;
    opens img.players;
}