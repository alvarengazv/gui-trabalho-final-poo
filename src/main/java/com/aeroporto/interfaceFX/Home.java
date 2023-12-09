package com.aeroporto.interfaceFX;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;

public class Home extends Application {
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage stage) {
        try {
            Font.loadFont(this.getClass().getResource("fonts/Merriweather-Black.ttf").toExternalForm(), 10);
            Font.loadFont(this.getClass().getResource("fonts/Merriweather-BlackItalic.ttf").toExternalForm(), 10);
            Font.loadFont(this.getClass().getResource("fonts/Merriweather-Bold.ttf").toExternalForm(), 10);
            Font.loadFont(this.getClass().getResource("fonts/Merriweather-BoldItalic.ttf").toExternalForm(), 10);
            Font.loadFont(this.getClass().getResource("fonts/Merriweather-Italic.ttf").toExternalForm(), 10);
            Font.loadFont(this.getClass().getResource("fonts/Merriweather-Light.ttf").toExternalForm(), 10);
            Font.loadFont(this.getClass().getResource("fonts/Merriweather-LightItalic.ttf").toExternalForm(), 10);
            Font.loadFont(this.getClass().getResource("fonts/Merriweather-Regular.ttf").toExternalForm(), 10);
            Splash splash = new Splash();
            splash.show();
            Image icone = new Image(getClass().getResource("icons/aeronaveIcone.png").toString());
            stage.getIcons().add(icone);
            stage.setTitle("Aeroporto do CEFET");
            stage.initStyle(StageStyle.TRANSPARENT);

            stage.setScene(splash.getSplashScene());
            splash.getSequentialTransition().setOnFinished(e -> {
                Timeline timeline = new Timeline();
                KeyFrame key = new KeyFrame(Duration.millis(0),
                        new KeyValue(splash.getSplashScene().getRoot().opacityProperty(), 0));
                timeline.getKeyFrames().add(key);
                timeline.setOnFinished((event) -> {
                    try {

                        String css = this.getClass().getResource("css/app.css").toExternalForm();
                        Parent root = FXMLLoader.load(getClass().getResource("pages/capa.fxml"));

                        Scene scene = new Scene(root);

                        FadeTransition fd = new FadeTransition();
                        fd.setNode(root);
                        fd.setFromValue(0);
                        fd.setToValue(1);
                        fd.setDuration(Duration.millis(1000));
                        fd.play();

                        scene.getStylesheets().add(css);
                        scene.setFill(Color.TRANSPARENT);

                        root.setOnMousePressed(mouseEvent -> {
                            xOffset = mouseEvent.getSceneX();
                            yOffset = mouseEvent.getSceneY();
                        });

                        root.setOnMouseDragged(mouseEvent -> {
                            stage.setX(mouseEvent.getScreenX() - xOffset);
                            stage.setY(mouseEvent.getScreenY() - yOffset);
                        });

                        stage.setOnCloseRequest(eventC -> {
                            eventC.consume();
                            close(stage);
                        });

                        stage.setScene(scene);
                        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
                        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);

                        stage.show();
                    }
                    catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
                timeline.play();
            });

            stage.show();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void close(Stage stage){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(stage.getScene().getWindow());

        alert.setTitle("Fechando");
        alert.setHeaderText("Você tem certeza de que deseja sair do programa?");

        if(alert.showAndWait().get() == ButtonType.OK){
            System.out.println("Saiu do programa.");
            stage.close();
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}