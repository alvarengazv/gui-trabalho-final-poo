package com.aeroporto.testefx;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class ElementosAviaoController implements Initializable {
    @FXML
    private Label labelAvioesDecolagem;
    @FXML
    private Label labelAvioesAterrissagem;
    @FXML
    private Label labelAterrissagensEmergenciais;
    @FXML
    private Label labelTempoMedioGlobal;
    @FXML
    private Label labelTituloInfo;
    @FXML
    private Label id;
    @FXML
    private Label combustivel;
    @FXML
    private Label passageiroEspecial;
    @FXML
    private Label companhiaAerea;
    @FXML
    private Label tempoEspera;
    @FXML
    private Label numPassageiros;
    @FXML
    private AnchorPane anchorPaneInicial;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void atualizaDados(){
        id.setText(String.valueOf(AeroportoPageController.aeronaveAtual.getId()));
        combustivel.setText(String.valueOf(AeroportoPageController.aeronaveAtual.getCombustivel()));
        passageiroEspecial.setText(String.valueOf(AeroportoPageController.aeronaveAtual.getPassageiroEspecial()));
        companhiaAerea.setText(AeroportoPageController.aeronaveAtual.getCompanhiaAerea());
        tempoEspera.setText(String.valueOf(AeroportoPageController.aeronaveAtual.getTempoEspera()));
        numPassageiros.setText(String.valueOf(AeroportoPageController.aeronaveAtual.getNumPassageiros()));
    }
}
