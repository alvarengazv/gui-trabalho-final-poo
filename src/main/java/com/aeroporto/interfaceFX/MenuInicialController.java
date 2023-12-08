package com.aeroporto.interfaceFX;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuInicialController implements Initializable {
    @FXML
    private ImageView imagemFundo;

    @FXML
    private AnchorPane anchorPaneInicial;

    private Stage stage;
    private Parent root;
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private Label labelEscolha;

    private boolean escolhido = false;
    private boolean isAleatorio = false;

    private File selectedFile;

    @FXML
    private Pane elementosMenu;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label labelProgresso;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imagemRedonda();
        mostrarTudo();
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

    public void mostrarTudo(){
        FadeTransition fade = new FadeTransition();
        fade.setNode(elementosMenu);
        fade.setDuration(Duration.millis(2000));
        fade.setFromValue(0);
        fade.setToValue(1);

        fade.play();
    }

    public void escolherArquivo(){
        stage = (Stage) anchorPaneInicial.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Abrir arquivo de aeronaves");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Arquivos de Texto", "*.txt")
        );

        selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            labelEscolha.setText("Modo de arquivo escolhido: " + selectedFile.getName());
            escolhido = true;
            isAleatorio = false;
        }
    }

    public void apagarTudo(){
        FadeTransition fade1 = new FadeTransition();
        fade1.setNode(elementosMenu);
        fade1.setDuration(Duration.millis(2000));
        fade1.setFromValue(1);
        fade1.setToValue(0);

        progressBar.setVisible(true);
        FadeTransition fade2 = new FadeTransition();
        fade2.setNode(progressBar);
        fade2.setDuration(Duration.millis(2000));
        fade2.setFromValue(0);
        fade2.setToValue(1);

        FadeTransition fade3 = new FadeTransition();
        fade3.setNode(labelProgresso);
        fade3.setDuration(Duration.millis(2000));
        fade3.setFromValue(0);
        fade3.setToValue(1);

        ParallelTransition pt = new ParallelTransition();
        pt.getChildren().addAll(fade1, fade2, fade3);
        pt.play();

        pt.setOnFinished(e -> {
            elementosMenu.setVisible(false);
            mostrarProgresso();
        });
    }

    public void escolherAleatorio(){
        labelEscolha.setText("Modo aleatório escolhido");
        escolhido = true;
        isAleatorio = true;
    }

    public void mostrarProgresso(){
        progressBar.setProgress(0);
        progressBar.setStyle(
                "-fx-border-radius: 25px; " +
                "-fx-background-radius: 25px;" +
                "-fx-accent: #899b51; "
        );

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    int pontos = 200;
                    double progresso = 0;
                    for(int i = 0; i < pontos; i++){
                        Thread.sleep(10);
                        progresso += 0.0051;

                        double finalProgresso = progresso;
                        Platform.runLater(() -> {
                            progressBar.setProgress(finalProgresso);
                            labelProgresso.setText(String.format("%.0f", finalProgresso * 100 > 100 ? 100.00 : (finalProgresso * 100)) + " %");
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("pages/aeroportoPage.fxml"));
                            root = loader.load();

                            AeroportoPageController aeroportoPageController = loader.getController();
                            if(isAleatorio){
                                aeroportoPageController.setIcone("M0 3.5A.5.5 0 0 1 .5 3H1c2.202 0 3.827 1.24 4.874 2.418.49.552.865 1.102 1.126 1.532.26-.43.636-.98 1.126-1.532C9.173 4.24 10.798 3 13 3v1c-1.798 0-3.173 1.01-4.126 2.082A9.624 9.624 0 0 0 7.556 8a9.624 9.624 0 0 0 1.317 1.918C9.828 10.99 11.204 12 13 12v1c-2.202 0-3.827-1.24-4.874-2.418A10.595 10.595 0 0 1 7 9.05c-.26.43-.636.98-1.126 1.532C4.827 11.76 3.202 13 1 13H.5a.5.5 0 0 1 0-1H1c1.798 0 3.173-1.01 4.126-2.082A9.624 9.624 0 0 0 6.444 8a9.624 9.624 0 0 0-1.317-1.918C4.172 5.01 2.796 4 1 4H.5a.5.5 0 0 1-.5-.5 M13 5.466V1.534a.25.25 0 0 1 .41-.192l2.36 1.966c.12.1.12.284 0 .384l-2.36 1.966a.25.25 0 0 1-.41-.192zm0 9v-3.932a.25.25 0 0 1 .41-.192l2.36 1.966c.12.1.12.284 0 .384l-2.36 1.966a.25.25 0 0 1-.41-.192z");
                            } else {
                                aeroportoPageController.setIcone("M.5 9.9a.5.5 0 0 1 .5.5v2.5a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1v-2.5a.5.5 0 0 1 1 0v2.5a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2v-2.5a.5.5 0 0 1 .5-.5 M7.646 1.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1-.708.708L8.5 2.707V11.5a.5.5 0 0 1-1 0V2.707L5.354 4.854a.5.5 0 1 1-.708-.708z");
                                aeroportoPageController.getAvioesArquivo(selectedFile);
                            }
                            mudaPage();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        }).start();
    }

    public void iniciar() {
        if(escolhido){
            apagarTudo();
        } else {
            alerta();
        }
    }

    public void mudaPage() throws IOException {
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

    public void alerta(){
        stage = (Stage) anchorPaneInicial.getScene().getWindow();

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(stage);
        alert.setTitle("Alerta");
        alert.setHeaderText("Selecione uma opção e tente novamente");
        alert.showAndWait();
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

    public void minimizarPagina(){
        stage = (Stage) anchorPaneInicial.getScene().getWindow();

        stage.setIconified(true);
    }
}
