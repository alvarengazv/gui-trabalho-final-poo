package com.aeroporto.testefx;

import com.almasb.fxgl.core.Disposable;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AeroportoPageController implements Initializable, Disposable {

    private String svgSol = "M8 11a3 3 0 1 1 0-6 3 3 0 0 1 0 6m0 1a4 4 0 1 0 0-8 4 4 0 0 0 0 8M8 0a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-1 0v-2A.5.5 0 0 1 8 0m0 13a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-1 0v-2A.5.5 0 0 1 8 13m8-5a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1 0-1h2a.5.5 0 0 1 .5.5M3 8a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1 0-1h2A.5.5 0 0 1 3 8m10.657-5.657a.5.5 0 0 1 0 .707l-1.414 1.415a.5.5 0 1 1-.707-.708l1.414-1.414a.5.5 0 0 1 .707 0m-9.193 9.193a.5.5 0 0 1 0 .707L3.05 13.657a.5.5 0 0 1-.707-.707l1.414-1.414a.5.5 0 0 1 .707 0zm9.193 2.121a.5.5 0 0 1-.707 0l-1.414-1.414a.5.5 0 0 1 .707-.707l1.414 1.414a.5.5 0 0 1 0 .707M4.464 4.465a.5.5 0 0 1-.707 0L2.343 3.05a.5.5 0 1 1 .707-.707l1.414 1.414a.5.5 0 0 1 0 .708z";
    private String svgChuva = "M4.158 12.025a.5.5 0 0 1 .316.633l-.5 1.5a.5.5 0 0 1-.948-.316l.5-1.5a.5.5 0 0 1 .632-.317m3 0a.5.5 0 0 1 .316.633l-1 3a.5.5 0 0 1-.948-.316l1-3a.5.5 0 0 1 .632-.317zm3 0a.5.5 0 0 1 .316.633l-.5 1.5a.5.5 0 0 1-.948-.316l.5-1.5a.5.5 0 0 1 .632-.317m3 0a.5.5 0 0 1 .316.633l-1 3a.5.5 0 1 1-.948-.316l1-3a.5.5 0 0 1 .632-.317m.247-6.998a5.001 5.001 0 0 0-9.499-1.004A3.5 3.5 0 1 0 3.5 11H13a3 3 0 0 0 .405-5.973zM8.5 2a4 4 0 0 1 3.976 3.555.5.5 0 0 0 .5.445H13a2 2 0 0 1 0 4H3.5a2.5 2.5 0 1 1 .605-4.926.5.5 0 0 0 .596-.329A4.002 4.002 0 0 1 8.5 2";
    private String svgTempestade = "M2.658 11.026a.5.5 0 0 1 .316.632l-.5 1.5a.5.5 0 1 1-.948-.316l.5-1.5a.5.5 0 0 1 .632-.316zm9.5 0a.5.5 0 0 1 .316.632l-.5 1.5a.5.5 0 1 1-.948-.316l.5-1.5a.5.5 0 0 1 .632-.316zm-7.5 1.5a.5.5 0 0 1 .316.632l-.5 1.5a.5.5 0 1 1-.948-.316l.5-1.5a.5.5 0 0 1 .632-.316zm9.5 0a.5.5 0 0 1 .316.632l-.5 1.5a.5.5 0 1 1-.948-.316l.5-1.5a.5.5 0 0 1 .632-.316zm-.753-8.499a5.001 5.001 0 0 0-9.499-1.004A3.5 3.5 0 1 0 3.5 10H13a3 3 0 0 0 .405-5.973zM8.5 1a4 4 0 0 1 3.976 3.555.5.5 0 0 0 .5.445H13a2 2 0 0 1 0 4H3.5a2.5 2.5 0 1 1 .605-4.926.5.5 0 0 0 .596-.329A4.002 4.002 0 0 1 8.5 1M7.053 11.276A.5.5 0 0 1 7.5 11h1a.5.5 0 0 1 .474.658l-.28.842H9.5a.5.5 0 0 1 .39.812l-2 2.5a.5.5 0 0 1-.875-.433L7.36 14H6.5a.5.5 0 0 1-.447-.724z";
    private String svgNublado = "M16 7.5a2.5 2.5 0 0 1-1.456 2.272 3.513 3.513 0 0 0-.65-.824 1.5 1.5 0 0 0-.789-2.896.5.5 0 0 1-.627-.421 3 3 0 0 0-5.22-1.625 5.587 5.587 0 0 0-1.276.088 4.002 4.002 0 0 1 7.392.91A2.5 2.5 0 0 1 16 7.5 M7 5a4.5 4.5 0 0 1 4.473 4h.027a2.5 2.5 0 0 1 0 5H3a3 3 0 0 1-.247-5.99A4.502 4.502 0 0 1 7 5m3.5 4.5a3.5 3.5 0 0 0-6.89-.873.5.5 0 0 1-.51.375A2 2 0 1 0 3 13h8.5a1.5 1.5 0 1 0-.376-2.953.5.5 0 0 1-.624-.492z";
    private String svgNeve = "M8 16a.5.5 0 0 1-.5-.5v-1.293l-.646.647a.5.5 0 0 1-.707-.708L7.5 12.793V8.866l-3.4 1.963-.496 1.85a.5.5 0 1 1-.966-.26l.237-.882-1.12.646a.5.5 0 0 1-.5-.866l1.12-.646-.884-.237a.5.5 0 1 1 .26-.966l1.848.495L7 8 3.6 6.037l-1.85.495a.5.5 0 0 1-.258-.966l.883-.237-1.12-.646a.5.5 0 1 1 .5-.866l1.12.646-.237-.883a.5.5 0 1 1 .966-.258l.495 1.849L7.5 7.134V3.207L6.147 1.854a.5.5 0 1 1 .707-.708l.646.647V.5a.5.5 0 1 1 1 0v1.293l.647-.647a.5.5 0 1 1 .707.708L8.5 3.207v3.927l3.4-1.963.496-1.85a.5.5 0 1 1 .966.26l-.236.882 1.12-.646a.5.5 0 0 1 .5.866l-1.12.646.883.237a.5.5 0 1 1-.26.966l-1.848-.495L9 8l3.4 1.963 1.849-.495a.5.5 0 0 1 .259.966l-.883.237 1.12.646a.5.5 0 0 1-.5.866l-1.12-.646.236.883a.5.5 0 1 1-.966.258l-.495-1.849-3.4-1.963v3.927l1.353 1.353a.5.5 0 0 1-.707.708l-.647-.647V15.5a.5.5 0 0 1-.5.5z";
    private double xOffset = 0;
    private double yOffset = 0;
    private Map<String, String> svgClimaMap = new HashMap<>();
    public static Map<Integer, FXMLLoader> paginasMap = new HashMap<>();
    public static Aeronave aeronaveAtual;
    public static Pista pistaAtual = new Pista();
    public static FilaDeEspera filaAtual = new FilaDeEspera();

    private Scene scene;
    private Stage stage;

    @FXML
    private AnchorPane anchorPaneInicial;
    @FXML
    private ImageView imagemFundo;

    @FXML
    private SVGPath iconeEscolhido;
    @FXML
    private Label minutosSimuladosLabel;
    @FXML
    private Pane elementosPage;
    @FXML
    private Pane svgAlerta;
    @FXML
    public static Pane paneElementosCentro = new Pane();
    @FXML
    private SVGPath climaSvg;
    @FXML
    private Label climaLabel;

    public static int paginaAtual = 0;
    private boolean isAleatorio = true;

    public static int minutosSimulados = 0;
    public static Aeroporto aeroporto = new Aeroporto();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        minutosSimuladosLabel.setText(String.valueOf(minutosSimulados));
        paneElementosCentro.setLayoutX(1);
        paneElementosCentro.setLayoutY(99);
        paneElementosCentro.setPrefWidth(1280);
        paneElementosCentro.setPrefWidth(510);

        svgClimaMap.put("Sol", svgSol);
        svgClimaMap.put("Chuva", svgChuva);
        svgClimaMap.put("Tempestade", svgTempestade);
        svgClimaMap.put("Neve", svgNeve);
        svgClimaMap.put("Nublado", svgNublado);

        Pane newLoadedPane = null;
        try {
            FXMLLoader loadTest0 = new FXMLLoader(getClass().getResource("pages/informacoesGerais.fxml"));
            FXMLLoader loadTest1 = new FXMLLoader(getClass().getResource("pages/informacoesPista.fxml"));
            FXMLLoader loadTest2 = new FXMLLoader(getClass().getResource("pages/informacoesFila.fxml"));
            FXMLLoader loadTest3 = new FXMLLoader(getClass().getResource("pages/informacoesAviao.fxml"));
            paginasMap.put(0, loadTest0);
            paginasMap.put(1, loadTest1);
            paginasMap.put(2, loadTest2);
            paginasMap.put(3, loadTest3);
            loadTest1.load();
            loadTest2.load();
            loadTest3.load();
            newLoadedPane = paginasMap.get(0).load();
            paginaAtual = 0;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        paneElementosCentro.getChildren().add(newLoadedPane);
        anchorPaneInicial.getChildren().add(paneElementosCentro);
        imagemRedonda();
        mostrarTudo();
    }

    public void simularMinuto(ActionEvent actionEvent) throws IOException {
        minutosSimulados++;
        minutosSimuladosLabel.setText(String.valueOf(minutosSimulados));
        if (isAleatorio) {
            aeroporto.simularMinuto();
        } else {
            aeroporto.simularMinutoArquivo();
        }
        if(aeroporto.getQtdAeronavesEmergencia() >= 3)
            svgAlerta.setVisible(true);
        else
            svgAlerta.setVisible(false);
        voltarParaGerais();
        if(minutosSimulados % 10 == 0)
            mudafundo();
    }

    public void mostrarTudo(){
        FadeTransition fade = new FadeTransition();
        fade.setNode(elementosPage);
        fade.setDuration(Duration.millis(2000));
        fade.setFromValue(0);
        fade.setToValue(1);

        fade.play();
    }

    public void setIcone(String svgPathString){
        iconeEscolhido.setContent(svgPathString);
    }

    public void getAvioesArquivo(File arquivoAeronaves) throws FileNotFoundException {
        isAleatorio = false;
        Main.leituraArquivoAeronaves(arquivoAeronaves);
    }

    public void imagemRedonda(){
        Group group = new Group(imagemFundo);

        Rectangle clip = new Rectangle(
                imagemFundo.getFitWidth(), imagemFundo.getFitHeight()
        );
        clip.setArcWidth(16);
        clip.setArcHeight(16);
        group.setClip(clip);

        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        WritableImage image = group.snapshot(parameters, null);

        anchorPaneInicial.getChildren().add(0, group);
    }

    public void mudafundo(){
        String clima = aeroporto.getClima();

        climaSvg.setContent(svgClimaMap.get(clima));
        climaLabel.setText(clima);

        imagemFundo.setImage(null);

        imagemFundo.setImage(new Image(this.getClass().getResource("images/" + clima + ".gif").toExternalForm()));
    }

    public void fecharPagina(){
        stage = (Stage) anchorPaneInicial.getScene().getWindow();;

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

    public void voltarPagina() throws IOException {
        switch (paginaAtual){
            case 0:{
                voltarParaMenu();
                break;
            }
            case 1:{
                voltarParaGerais();
                break;
            }
            case 2:{
                voltarParaPista();
                break;
            }
            case 3:{
                voltarParaFila();
                break;
            }
        }
    }

    public static void voltarParaGerais() throws IOException {
        ElementosGeraisController controller = paginasMap.get(0).getController();
        controller.atualizaDados();

        paneElementosCentro.getChildren().removeFirst();
        paneElementosCentro.getChildren().add(paginasMap.get(0).getRoot());
        paginaAtual = 0;
    }

    public static void voltarParaPista() throws IOException {
        ElementosPistaController controller = paginasMap.get(1).getController();
        controller.atualizaDados();

        paneElementosCentro.getChildren().removeFirst();
        paneElementosCentro.getChildren().add(paginasMap.get(1).getRoot());
        paginaAtual = 1;
    }

    public static void voltarParaFila() throws IOException {
        ElementosFilaController controller = paginasMap.get(2).getController();
        controller.montaLista();
        controller.atualizaDados();

        paneElementosCentro.getChildren().removeFirst();
        paneElementosCentro.getChildren().add(paginasMap.get(2).getRoot());
        paginaAtual = 2;
    }

    public static void voltarParaAviao() throws IOException {
        ElementosAviaoController controller = paginasMap.get(3).getController();
        controller.atualizaDados();

        paneElementosCentro.getChildren().removeFirst();
        paneElementosCentro.getChildren().add(paginasMap.get(3).getRoot());
        paginaAtual = 3;
    }

    public void voltarParaMenu() throws IOException {
        stage = (Stage) anchorPaneInicial.getScene().getWindow();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(stage);
        alert.setTitle("Voltando");
        alert.setHeaderText("Você tem certeza de que deseja voltar ao menu?");
        alert.setContentText("Todo o seu progresso será perdido.");

        if(alert.showAndWait().get() == ButtonType.OK){
            paneElementosCentro.getChildren().removeFirst();
            Parent root = FXMLLoader.load(getClass().getResource("pages/menuInicial.fxml"));

            reiniciaDados();

            scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);

            stage.setScene(scene);
            
            stage.show();

            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    xOffset = mouseEvent.getSceneX();
                    yOffset = mouseEvent.getSceneY();
                }
            });

            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    stage.setX(mouseEvent.getScreenX() - xOffset);
                    stage.setY(mouseEvent.getScreenY() - yOffset);
                }
            });

            stage.setOnCloseRequest(eventC -> {
                eventC.consume();
                fecharPagina();
            });
        }
    }

    public void alertaEmergencia() {
        stage = (Stage) anchorPaneInicial.getScene().getWindow();;

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(stage);
        alert.setTitle("Alerta");
        alert.setHeaderText("Aeroporto em estado crítico!");
        alert.setContentText("Há " + aeroporto.getQtdAeronavesEmergencia() + " aeronaves em estado de emergência!");

        alert.showAndWait();
    }

    public void reiniciaDados(){
        imagemFundo.setImage(null);
        iconeEscolhido.setContent(null);
        climaSvg.setContent(null);
        climaLabel.setText(null);
        minutosSimuladosLabel.setText(null);
        minutosSimulados = 0;
        paneElementosCentro.getChildren().clear();
        aeroporto = new Aeroporto();
        Aeroporto.reset();
        aeronaveAtual = null;
        pistaAtual = new Pista();
        filaAtual = new FilaDeEspera();
        paginaAtual = 0;
        isAleatorio = true;
        svgClimaMap.clear();
    }

    @Override
    public void dispose() {
        anchorPaneInicial = null;
        imagemFundo = null;
        iconeEscolhido = null;
        minutosSimuladosLabel = null;
        elementosPage = null;
        paneElementosCentro = null;
        climaSvg = null;
        climaLabel = null;
        scene = null;
        stage = null;
        svgAlerta = null;
    }
}
