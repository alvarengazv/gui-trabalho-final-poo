package com.aeroporto.interfaceFX;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }


    public void abrirPista1()  {
        if(AeroportoPageController.minutosSimulados == 0){
            alerta();
            return;
        }
        AeroportoPageController.pistaAtual = Aeroporto.pista1;

        AeroportoPageController.voltarParaPista();
    }
    public void abrirPista2() {
        if(AeroportoPageController.minutosSimulados == 0){
            alerta();
            return;
        }
        AeroportoPageController.pistaAtual = Aeroporto.pista2;

        AeroportoPageController.voltarParaPista();
    }
    public void abrirPista3() {
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
        Stage stage = (Stage) anchorPaneInicial.getScene().getWindow();

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(stage);
        alert.setTitle("Alerta");
        alert.setHeaderText("Simule pelo menos um minuto!");
        alert.showAndWait();
    }
}
