package com.aeroporto.interfaceFX;

import javafx.animation.*;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;


public class Splash
{
    static Scene splash;
    final private Pane pane;
    final private SequentialTransition seqT;

    public Splash()
    {
        pane = new Pane();
        pane.setStyle("-fx-background-color: #DDDDDD; -fx-background-radius: 80px");

        splash = new Scene(pane);
        splash.setFill(Color.TRANSPARENT);

        seqT = new SequentialTransition();
    }

    public void show()
    {
        String musicFile = "sounds/somAviao.mp3";

        Media sound = new Media(this.getClass().getResource(musicFile).toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setVolume(0.8);
        mediaPlayer.setStartTime(Duration.seconds(8));
        mediaPlayer.play();

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.5), event -> mediaPlayer.setVolume(mediaPlayer.getVolume() - 0.15));

        timeline.getKeyFrames().add(keyFrame);
        timeline.play();

        pane.setMinHeight(70);
        pane.setMaxWidth(400);

        Line line = new Line();
        line.setStroke(Color.valueOf("#899b50"));
        line.setStrokeWidth(7);
        line.setStrokeLineCap(StrokeLineCap.ROUND);
        line.setStartY(35);
        line.setEndY(35);
        line.setStartX(-600);
        line.setEndX(-45);

        Image aviao = new Image(getClass().getResource("icons/aeronaveIcone.png").toString());
        ImageView imageView = new ImageView(aviao);
        imageView.setRotate(45);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);

        TranslateTransition translate = new TranslateTransition();
        translate.setNode(imageView);
        translate.setDuration(Duration.millis(4500));
        translate.setFromX(-50);
        translate.setToX(450);

        translate.setFromY(10);

        TranslateTransition translateLine = new TranslateTransition();
        translateLine.setNode(line);
        translateLine.setDuration(Duration.millis(4600));
        translateLine.setToX(515);

        FadeTransition fade = new FadeTransition();
        fade.setNode(pane);
        fade.setDuration(Duration.millis(800));
        fade.setFromValue(0);
        fade.setToValue(1);

        ParallelTransition pt = new ParallelTransition();
        pt.getChildren().addAll(translate, translateLine, fade);
        pt.setCycleCount(1);

        seqT.getChildren().add(pt);

        pane.getChildren().addAll(line, imageView);

        seqT.play();
    }

    public Scene getSplashScene()
    {
        return splash;
    }

    public SequentialTransition getSequentialTransition()
    {
        return seqT;
    }
}
