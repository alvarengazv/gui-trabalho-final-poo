package com.aeroporto.testefx;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
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
    private Label labelAvioesDecolagem;
    @FXML
    private Label labelAvioesAterrissagem;
    @FXML
    private Label labelAterrissagensEmergenciais;
    @FXML
    private Label labelTempoMedioGlobal;
    @FXML
    private Label id;
    @FXML
    private Label combustivel;
    @FXML
    private Label tempoEspera;
    @FXML
    private Label passageiroEspecial;
    @FXML
    private Label labelInfo4;
    @FXML
    private Pane paneAviao;
    @FXML
    private AnchorPane anchorPaneInicial;
    @FXML
    private ListView<Pane> listViewAvioes;
    static Map<Integer, Aeronave> map = new HashMap<>();

    public void abrirAviao() throws IOException {
        AeroportoPageController.voltarParaAviao();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            montaLista();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        //System.out.println(paneAviao.getChildren());
        //listViewAvioes.setItems(FXCollections.observableArrayList(map.keySet()));
        /*for (Node e: paneAviao.getChildren()) {
            if(e instanceof Label){

            }
        }
        listViewAvioes.getItems().add(paneAviao);*/
    }

    public void montaLista() throws FileNotFoundException {
        Main.leituraArquivoAeronaves();

        for (Aeronave a : Main.filaAeronavesAterrissagemArquivo) {
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
}
