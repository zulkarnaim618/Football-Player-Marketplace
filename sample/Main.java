package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {

    private Stage stage;
    private homeController controller;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("sample.fxml"));
        Parent root = loader.load();

        controller = loader.getController();
        controller.start();

        stage.setTitle("Football Manager");
        Scene scene = new Scene(root, 1200, 700);
        //scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        //stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.getIcons().add(new Image("img/football.png"));

        stage.setOnCloseRequest(windowEvent -> {
            if (controller.isConnected) {
                try {
                    controller.client.networkUtil.write("disconnect");
                }
                catch (Exception e) {
                    System.out.println(e);
                }
            }
            stage.close();
        });

        stage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
