package com.aeroporto.interfaceFX;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.SVGPath;

import java.net.URL;
import java.util.ResourceBundle;

public class ElementosAviaoController implements Initializable {
    @FXML
    private Label id;
    @FXML
    private Label combustivel;
    @FXML
    private SVGPath passageiroEspecial;
    @FXML
    private ImageView companhiaAerea;
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
        String svgX = "M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708";
        String svgV = "M10.97 4.97a.75.75 0 0 1 1.07 1.05l-3.99 4.99a.75.75 0 0 1-1.08.02L4.324 8.384a.75.75 0 1 1 1.06-1.06l2.094 2.093 3.473-4.425a.267.267 0 0 1 .02-.022z";
        String companhiaA = AeroportoPageController.aeronaveAtual.getCompanhiaAerea();
        Image imagem = new Image(getClass().getResource("images/" + companhiaA + ".png").toExternalForm());

        id.setText(String.valueOf(AeroportoPageController.aeronaveAtual.getId()));
        combustivel.setText(String.valueOf(AeroportoPageController.aeronaveAtual.getCombustivel()));
        passageiroEspecial.setContent(AeroportoPageController.aeronaveAtual.getPassageiroEspecial() ? svgV : svgX);
        companhiaAerea.setImage(imagem);
        tempoEspera.setText(String.valueOf(AeroportoPageController.aeronaveAtual.getTempoEspera()));
        numPassageiros.setText(String.valueOf(AeroportoPageController.aeronaveAtual.getNumPassageiros()));
    }
}
