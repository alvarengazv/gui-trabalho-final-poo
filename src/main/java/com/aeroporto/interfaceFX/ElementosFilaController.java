package com.aeroporto.interfaceFX;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ElementosFilaController implements Initializable {
    @FXML
    private Label qtdAvioes;
    @FXML
    private Label qtdAterrissagensEmergenciais;
    @FXML
    private Label tempoMedioEspera;
    @FXML
    private Label labelTitulo;
    @FXML
    private Label labelInfo2;
    @FXML
    private Pane paneAviao;
    @FXML
    private Pane aterrissagemEmegencial;
    @FXML
    private AnchorPane anchorPaneInicial;
    @FXML
    private ListView<Pane> listViewAvioes;
    public static Map<Integer, Aeronave> map = new HashMap<>();

    public void abrirAviao() {
        AeroportoPageController.voltarParaAviao();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void montaLista(){
        map.clear();
        listViewAvioes.getItems().clear();
        if(AeroportoPageController.filaAtual.getFila().isEmpty()){
            VBox vbox = new VBox();
            vbox.setAlignment(Pos.CENTER);
            vbox.setStyle("-fx-background-color: #c5cecf");
            Label isVazia = new Label("Fila vazia!");
            isVazia.setStyle("-fx-font-size: 16px; -fx-font-family: Merriweather; -fx-font-weight: bold; -fx-text-fill: #596b42");
            vbox.getChildren().add(isVazia);
            listViewAvioes.getItems().add(vbox);
            return;
        }

        for (Aeronave a : AeroportoPageController.filaAtual.getFila()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("pages/aviaoPane.fxml"));
            map.put(a.getId(), a);
            Pane pane;
            try {
                pane = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            AviaoPaneController controller = loader.getController();

            controller.setAeronave(a);

            listViewAvioes.getItems().add(pane);
        }
    }

    public void atualizaDados(){
        if(AeroportoPageController.pistaAtual.getNome().equals("Pista 3"))
            labelTitulo.setStyle("-fx-font-size: 16px; -fx-font-family: Merriweather; -fx-font-weight: bold");
        if(AeroportoPageController.filaAtual.getNome().equals("Fila de Decolagem")){
            labelInfo2.setText("DECOLAGENS REALIZADAS:");
            qtdAterrissagensEmergenciais.setText(String.valueOf(AeroportoPageController.filaAtual.getQtdAeronavesDecolaram()));
        } else {
            labelInfo2.setText("ATERRISSAGENS EMERGENCIAIS:");
            qtdAterrissagensEmergenciais.setText(String.valueOf(AeroportoPageController.filaAtual.getQtdAterrissagensEmergenciais()));
        }
        labelTitulo.setText(AeroportoPageController.filaAtual.getNome().toUpperCase() + ":");
        qtdAvioes.setText(String.valueOf(AeroportoPageController.filaAtual.tamanho()));
        tempoMedioEspera.setText(String.format("%.2f", AeroportoPageController.filaAtual.tempoMedioDeEsperaFila()));
    }
}
