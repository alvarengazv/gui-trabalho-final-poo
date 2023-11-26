package com.guilherme.testefx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class Home extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("home-view.fxml"));
            Scene scene = new Scene(root, Color.LIGHTSKYBLUE);
            //scene.getStylesheets().add(getClass().getResource("app.css").toExternalForm());

            String css = this.getClass().getResource("app.css").toExternalForm();

            scene.getStylesheets().add(css);

            Image icone = new Image(getClass().getResource("aeronaveIcone.png").toString());

            stage.getIcons().add(icone);
            stage.setTitle("Aeroporto do CEFET");

            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}