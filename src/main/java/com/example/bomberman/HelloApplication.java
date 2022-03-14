package com.example.bomberman;

import javafx.animation.Animation;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    static final int WIDTH = 700;
    static final int HEIGHT = 700;
    Scene sceneMenu;
    Stage primaryStage;
    Group groupGame;
    Scene sceneGame;
    Circle bomberman;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        primaryStage = stage;
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT) ;
        Button buttonJouer = new Button("Jouer");
        Button buttonRegle = new Button("Règle");
        Button buttonQuitter = new Button("Quitter");
        Group menuPrincipal = new Group();

        stage.setTitle("Bomberman!");
        menuPrincipal.setLayoutX(WIDTH/2);
        menuPrincipal.setLayoutY(HEIGHT/2);
        VBox vbox = new VBox(buttonJouer, buttonRegle, buttonQuitter);
        menuPrincipal.getChildren().add(vbox);
        sceneMenu = new Scene(menuPrincipal, WIDTH, HEIGHT, Color.BLACK);
        stage.setScene(sceneMenu);
        stage.show();
        groupGame = initializeGroupGame();
        sceneGame = new Scene(groupGame, WIDTH, HEIGHT, Color.BLACK);
        buttonJouer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(sceneGame);




            }
        });

    }


    private Group initializeGroupGame() {
        Group group = new Group();
        bomberman = new Circle(50, 50, 15);
        bomberman.setFill(Color.YELLOW);

        group.getChildren().add(bomberman);


        return group;
    }

    public static void main(String[] args) {
        launch();
    }
}