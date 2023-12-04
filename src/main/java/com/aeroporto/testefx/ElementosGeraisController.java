package com.aeroporto.testefx;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;

public class ElementosGeraisController {
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
    private Label labelInfo1;
    @FXML
    private Label labelInfo2;
    @FXML
    private Label labelInfo3;
    @FXML
    private Label labelInfo4;
    @FXML
    private Pane infoPistas;
    @FXML
    private AnchorPane anchorPaneInicial;

    public void abrirPista1() throws IOException {
        AeroportoPageController.voltarParaPista();
    }
    public void abrirPista2(){}
    public void abrirPista3(){}
}
