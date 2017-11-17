package org.academiadecodigo.enuminatti.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by codecadet on 07/11/17.
 */
public class Client extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/view/battlefield.fxml"));
        primaryStage.setTitle("BeerBattle");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void stop() {

    }
}
