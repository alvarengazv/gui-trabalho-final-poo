package com.aeroporto.testefx;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableListBase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.FileNotFoundException;
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
    private Label id;
    @FXML
    private Label combustivel;
    @FXML
    private Label tempoEspera;
    @FXML
    private Label passageiroEspecial;
    @FXML
    private Label labelTitulo;
    @FXML
    private Pane paneAviao;
    @FXML
    private AnchorPane anchorPaneInicial;
    @FXML
    private ListView<Pane> listViewAvioes;
    public static Map<Integer, Aeronave> map = new HashMap<>();

    public void abrirAviao() throws IOException {
        AeroportoPageController.voltarParaAviao();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        //System.out.println(paneAviao.getChildren());
        //listViewAvioes.setItems(FXCollections.observableArrayList(map.keySet()));
        /*for (Node e: paneAviao.getChildren()) {
            if(e instanceof Label){

            }
        }
        listViewAvioes.getItems().add(paneAviao);*/
    }

    public void montaLista() throws FileNotFoundException {
        //Main.leituraArquivoAeronaves();
        map.clear();
        listViewAvioes.getItems().clear();

        for (Aeronave a : AeroportoPageController.filaAtual.getFila()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("pages/aviaoPane.fxml"));
            map.put(a.getId(), a);
            Pane pane = null;
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
        if(AeroportoPageController.pistaAtual.getNome() == "Pista 3")
            labelTitulo.setStyle("-fx-font-size: 16px; -fx-font-family: Merriweather");
        labelTitulo.setText(AeroportoPageController.filaAtual.getNome().toUpperCase() + ":");
        qtdAvioes.setText(String.valueOf(AeroportoPageController.filaAtual.tamanho()));
        //qtdAterrissagensEmergenciais.setText(String.valueOf(AeroportoPageController.filaAtual.em));
        tempoMedioEspera.setText(String.format("%.2f", AeroportoPageController.filaAtual.tempoMedioDeEsperaFila()));
    }
}
