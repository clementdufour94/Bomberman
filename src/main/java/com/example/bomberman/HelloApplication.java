package com.example.bomberman;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    static final int WIDTH = 700;
    static final int HEIGHT = 700;
    Scene sceneMenu;
    Stage primaryStage;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        primaryStage = stage;
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT) ;
        Button buttonjouer = new Button("Jouer");
        Button buttonregle = new Button("Règle");
        Button buttonquitter = new Button("Quitter");
        Group menuPrincipal = new Group();

        stage.setTitle("Bomberman!");
        menuPrincipal.setLayoutX(WIDTH/2);
        menuPrincipal.setLayoutY(HEIGHT/2);
        VBox vbox = new VBox(buttonjouer, buttonregle, buttonquitter);
        menuPrincipal.getChildren().add(vbox);
        sceneMenu = new Scene(menuPrincipal, WIDTH, HEIGHT, Color.BLACK);
        stage.setScene(sceneMenu);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}