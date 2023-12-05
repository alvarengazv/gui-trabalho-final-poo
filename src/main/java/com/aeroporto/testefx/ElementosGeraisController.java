package com.aeroporto.testefx;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ElementosGeraisController implements Initializable {
    @FXML
    private Label qtdAvioesDecolagem;
    @FXML
    private Label qtdAvioesAterrissagem;
    @FXML
    private Label qtdAterrissagensEmergenciais;
    @FXML
    private Label tempoMedioGlobal;
    @FXML
    private AnchorPane anchorPaneInicial;
    private Pista pista;
    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }


    public void abrirPista1() throws IOException {
        if(AeroportoPageController.minutosSimulados == 0){
            alerta();
            return;
        }
        AeroportoPageController.pistaAtual = Aeroporto.pista1;

        AeroportoPageController.voltarParaPista();
    }
    public void abrirPista2() throws IOException {
        if(AeroportoPageController.minutosSimulados == 0){
            alerta();
            return;
        }
        AeroportoPageController.pistaAtual = Aeroporto.pista2;

        AeroportoPageController.voltarParaPista();
    }
    public void abrirPista3() throws IOException {
        if(AeroportoPageController.minutosSimulados == 0){
            alerta();
            return;
        }
        AeroportoPageController.pistaAtual = Aeroporto.pista3;

        AeroportoPageController.voltarParaPista();
    }

    public void atualizaDados(){
        qtdAterrissagensEmergenciais.setText(String.valueOf(AeroportoPageController.aeroporto.getQtdAterrissagemEmergencial()));
        qtdAvioesDecolagem.setText(String.valueOf(AeroportoPageController.aeroporto.calcularAeronavesEmEsperaDecolagem()));
        qtdAvioesAterrissagem.setText(String.valueOf(AeroportoPageController.aeroporto.calcularAeronavesEmEsperaAterrissagem()));
        tempoMedioGlobal.setText(String.format("%.2f", AeroportoPageController.aeroporto.tempoMedioTotal()));
    }

    public void alerta(){
        stage = (Stage) anchorPaneInicial.getScene().getWindow();;

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(stage);
        alert.setTitle("Alerta");
        alert.setHeaderText("Simule pelo menos um minuto!");
        alert.showAndWait();
    }
}
