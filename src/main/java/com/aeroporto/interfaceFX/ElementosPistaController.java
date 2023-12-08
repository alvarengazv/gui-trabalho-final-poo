package com.aeroporto.interfaceFX;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ElementosPistaController implements Initializable {
    @FXML
    private Label qtdAvioesDecolagem;
    @FXML
    private Label qtdAvioesAterrissagem;
    @FXML
    private Label qtdAterrissagensEmergenciais;
    @FXML
    private Label tempoMedioEspera;
    @FXML
    private Label labelTitulo;
    @FXML
    private AnchorPane anchorPaneInicial;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void abrirFilaAterrissagem1() throws IOException {
        AeroportoPageController.filaAtual = AeroportoPageController.pistaAtual.getFilaAterrissagem1();

        AeroportoPageController.voltarParaFila();
    }

    public void abrirFilaAterrissagem2() throws IOException {
        if(AeroportoPageController.pistaAtual.getNome().equals("Pista 3")){
            alerta();
            return;
        }
            AeroportoPageController.filaAtual = AeroportoPageController.pistaAtual.getFilaAterrissagem2();

        AeroportoPageController.voltarParaFila();
    }

    public void abrirFilaDecolagem() throws IOException {
        AeroportoPageController.filaAtual = AeroportoPageController.pistaAtual.getFilaDecolagem();

        AeroportoPageController.voltarParaFila();
    }

    public void atualizaDados(){
        labelTitulo.setText("INFORMAÇÕES " + AeroportoPageController.pistaAtual.getNome().toUpperCase() + ":");
        qtdAvioesAterrissagem.setText(String.valueOf(AeroportoPageController.pistaAtual.quantidadeAeronavesAterrissagem()));
        qtdAvioesDecolagem.setText(String.valueOf(AeroportoPageController.pistaAtual.quantidadeAeronavesDecolagem()));
        qtdAterrissagensEmergenciais.setText(String.valueOf(AeroportoPageController.pistaAtual.getQtdAterrissagensEmergenciais()));
        tempoMedioEspera.setText(String.format("%.2f", AeroportoPageController.pistaAtual.recalcularTempoMedioEspera()));
    }

    public void alerta(){
        Stage stage = (Stage) anchorPaneInicial.getScene().getWindow();

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(stage);
        alert.setTitle("Alerta");
        alert.setHeaderText("A Pista 3 não possui fila de aterrissagem 2!");
        alert.showAndWait();
    }
}
