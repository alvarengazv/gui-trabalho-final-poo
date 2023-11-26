package com.example.trabalhofinalpoo;

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

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root1 = FXMLLoader.load(HelloApplication.class.getResource("hello-view.fxml"));
        Group root = new Group();
        Scene scene = new Scene(root1, Color.LIGHTSKYBLUE);

        Image icone = new Image(HelloApplication.class.getResource("aeronaveIcone.png").toString());

        stage.getIcons().add(icone);
        stage.setTitle("Aeroporto do CEFET");
//        stage.setFullScreen(true);
//        stage.setResizable(false);
        stage.setHeight(720);
        stage.setWidth(1280);

        Text texto = new Text();
        texto.setText("BOM DIA");
        texto.setY(50);
        texto.setX(50);

        root.getChildren().add(texto);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}