package com.aeroporto.testefx;

import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class AeroportoPageController implements Initializable {
    private double xOffset = 0;
    private double yOffset = 0;

    private Scene scene;
    private Stage stage;

    @FXML
    private AnchorPane anchorPaneInicial;
    @FXML
    private ImageView imagemFundo;

    @FXML
    private SVGPath iconeEscolhido;
    @FXML
    private Label labelEscolhido;
    @FXML
    private Label minutosSimuladosLabel;
    @FXML
    private Pane elementosPage;

    private int minutosSimulados = 0;

    private int i = 1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imagemRedonda();
        mostrarTudo();
    }

    public void simularMinuto(ActionEvent actionEvent){
        minutosSimulados++;
        minutosSimuladosLabel.setText("M i n u t o s   S i m u l a d o s :   " + minutosSimulados);
        mudafundo();
    }

    public void mostrarTudo(){
        FadeTransition fade = new FadeTransition();
        fade.setNode(elementosPage);
        fade.setDuration(Duration.millis(2000));
        fade.setFromValue(0);
        fade.setToValue(1);

        fade.play();
    }

    public void setIcone(String svgPathString){
        iconeEscolhido.setContent(svgPathString);
    }

    public void getAvioesArquivo(File arquivoAeronaves) throws FileNotFoundException {
        labelEscolhido.setText("Arquivo");
        Scanner sc = new Scanner(arquivoAeronaves);
    }

    public void imagemRedonda(){
        Group group = new Group(imagemFundo);

        Rectangle clip = new Rectangle(
                imagemFundo.getFitWidth(), imagemFundo.getFitHeight()
        );
        clip.setArcWidth(16);
        clip.setArcHeight(16);
        group.setClip(clip);

        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        WritableImage image = group.snapshot(parameters, null);

        anchorPaneInicial.getChildren().add(0, group);
    }

    public void mudafundo(){
        String[] imagens = {"Sol", "ChuvaLeve", "Tempestade", "Nublado", "Neve"};

        imagemFundo.setImage(null);

        imagemFundo.setImage(new Image(this.getClass().getResource("images/" + imagens[i] + ".gif").toExternalForm()));

        i++;
        if(i == 5)
            i = 0;
    }

    public void fecharPagina(){
        stage = (Stage) anchorPaneInicial.getScene().getWindow();;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(stage);
        alert.setTitle("Fechando");
        alert.setHeaderText("Você tem certeza de que deseja sair do programa?");

        if(alert.showAndWait().get() == ButtonType.OK){
            System.out.println("Saiu do programa.");
            System.exit(0);
        }
    }

    public void minimizarPagina(){
        stage = (Stage) anchorPaneInicial.getScene().getWindow();

        stage.setIconified(true);
    }

    public void voltarPagina() throws IOException {
        stage = (Stage) anchorPaneInicial.getScene().getWindow();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(stage);
        alert.setTitle("Voltando");
        alert.setHeaderText("Você tem certeza de que deseja voltar ao menu?");
        alert.setContentText("Todo o seu progresso será perdido.");

        if(alert.showAndWait().get() == ButtonType.OK){
            Parent root = FXMLLoader.load(getClass().getResource("pages/menuInicial.fxml"));
            imagemFundo.setImage(null);

            scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);

            stage.setScene(scene);

            stage.show();

            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    xOffset = mouseEvent.getSceneX();
                    yOffset = mouseEvent.getSceneY();
                }
            });

            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    stage.setX(mouseEvent.getScreenX() - xOffset);
                    stage.setY(mouseEvent.getScreenY() - yOffset);
                }
            });

            stage.setOnCloseRequest(eventC -> {
                eventC.consume();
                fecharPagina();
            });
        }
    }
}
