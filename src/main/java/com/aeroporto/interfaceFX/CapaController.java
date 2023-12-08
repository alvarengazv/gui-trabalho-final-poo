package com.aeroporto.interfaceFX;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CapaController implements Initializable {

    @FXML
    private Pane textos;

    @FXML
    private ImageView imagemFundo;

    @FXML
    private AnchorPane anchorPaneInicial;
    private Stage stage;

    private boolean isMenu = false;
    private double xOffset = 0, yOffset = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        animaTitulo();
        imagemRedonda();
    }

    public void imagemRedonda(){
        Rectangle clip = new Rectangle(
                imagemFundo.getFitWidth(), imagemFundo.getFitHeight()
        );
        clip.setArcWidth(16);
        clip.setArcHeight(16);
        imagemFundo.setClip(clip);

        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        WritableImage image = imagemFundo.snapshot(parameters, null);

        imagemFundo.setImage(image);
    }

    public void fecharPagina(){
        stage = (Stage) anchorPaneInicial.getScene().getWindow();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(stage);
        alert.setTitle("Fechando");
        alert.setHeaderText("Você tem certeza de que deseja sair do programa?");

        if(alert.showAndWait().get() == ButtonType.OK){
            System.out.println("Saiu do programa.");
            System.exit(0);
        }
    }

    public void animaTitulo(){
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(textos);
        translate.setDuration(Duration.millis(1500));
        translate.setToY(-20);

        FadeTransition fade = new FadeTransition();
        fade.setNode(textos);
        fade.setDuration(Duration.millis(2000));
        fade.setFromValue(0);
        fade.setToValue(1);

        ParallelTransition pt = new ParallelTransition();
        pt.getChildren().addAll(fade, translate);

        pt.play();
    }

    public void mudaTitulo() {
        if(!isMenu) {
            isMenu = true;

            TranslateTransition translate = new TranslateTransition();
            translate.setNode(textos);
            translate.setDuration(Duration.millis(1500));
            translate.setToY(0);

            FadeTransition fade = new FadeTransition();
            fade.setNode(textos);
            fade.setDuration(Duration.millis(1500));
            fade.setFromValue(1);
            fade.setToValue(0);

            ParallelTransition pt = new ParallelTransition();
            pt.getChildren().addAll(fade, translate);

            pt.play();


            ScaleTransition scale = new ScaleTransition();
            scale.setNode(imagemFundo);
            scale.setDuration(Duration.millis(1500));
            scale.setByX(2);
            scale.setByY(2.2);

            pt.setOnFinished(e -> {
                  scale.play();

                    scale.setOnFinished(ev -> {
                        try {
                            mudarParaMenu();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    });
            });
        }
    }

    public void mudarParaMenu() throws IOException {
        imagemFundo.setImage(null);
        Parent root = FXMLLoader.load(getClass().getResource("pages/menuInicial.fxml"));
        stage = (Stage) anchorPaneInicial.getScene().getWindow();

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);

        stage.setScene(scene);

        stage.show();

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
            fecharPagina();
        });
    }

    public void minimizarPagina(){
        stage = (Stage) anchorPaneInicial.getScene().getWindow();

        stage.setIconified(true);
    }
}
