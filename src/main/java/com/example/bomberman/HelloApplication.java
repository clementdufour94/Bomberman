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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {

    static final int WIDTH = 850;
    static final int HEIGHT = 800;
    Scene sceneMenu;
    Stage primaryStage;
    Group groupGame;
    List<Rectangle> listPoint;
    List<Rectangle> listPoint2;
    List<Rectangle> listPoint3;
    Scene sceneGame;
    Circle bomberman;

    static int bombint = 4;
    Text bombes;

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
        handleGameEvent();

    }


    private Group initializeGroupGame() {
        Group group = new Group();

        Image bombepng = new Image("https://cdn.discordapp.com/attachments/951092669969485864/953590274670616636/Wallpaperkiss_2375844.jpg", false);
        ImageView bombeview = new ImageView(bombepng);
        bombeview.setY(40);
        bombeview.setX(870);
        bombeview.setFitWidth(70);
        bombeview.setFitHeight(70);
        bombeview.setPreserveRatio(true);
        group.getChildren().add(bombeview);


        bombes = new Text(900,45, String.valueOf(bombint));
        bombes.setFill(Color.WHITE);
        bombes.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
        group.getChildren().add(bombes);

        listPoint = new ArrayList<Rectangle>();
        Image wall = new Image("http://images.shoutwiki.com/bomberpedia/3/38/SoftBlock.png", false);
        for(int i = 90; i < HEIGHT; i = i + 80){
        for(int j = 10; j < WIDTH; j = j + 80){
        boolean createOk = true;


              if(createOk){
                  Rectangle point = new Rectangle(j,i,40, 40);
                  point.setFill(new ImagePattern(wall));
                  listPoint.add(point);


              }
          }

        }for (Rectangle p : listPoint) {

          group.getChildren().add(p);
        }

        //Création Mur cassable Height
        listPoint2 = new ArrayList<Rectangle>();
        Image wall2 = new Image("http://images.shoutwiki.com/bomberpedia/thumb/a/af/HardBlock.png/200px-HardBlock.png", false);
        for(int i = 50; i < HEIGHT; i = i + 80){
            for(int j = 10; j < WIDTH; j = j + 80){
                boolean createOk = true;


                if(createOk){
                    Rectangle point = new Rectangle(j,i,40, 40);
                    point.setFill(new ImagePattern(wall2));
                    listPoint2.add(point);

                }
            }

        }for (Rectangle p : listPoint2) {

            group.getChildren().add(p);
        }

        //Création Mur cassable Width
        listPoint3 = new ArrayList<Rectangle>();
        for(int i = 50; i < HEIGHT; i = i + 80){
        for(int j = 50; j < WIDTH; j = j + 80){
        boolean createOk = true;


        if(createOk){
        Rectangle point = new Rectangle(j,i,40, 40);
        point.setFill(new ImagePattern(wall2));
        listPoint3.add(point);

        }
        }

        }for (Rectangle p : listPoint3) {

        group.getChildren().add(p);
        }
        bomberman = new Circle(70,30,13);
        //bomberman = new Rectangle(60, 20, 20,20);
        bomberman.setFill(Color.YELLOW);
        group.getChildren().add(bomberman);



        return group;
    }
    private void handleGameEvent() {
        sceneGame.setOnKeyPressed((KeyEvent event) -> {
            if (event.getText().isEmpty())
                return;
            char keyEntered = event.getText().toUpperCase().charAt(0);

            boolean isMouvOk = true;
            switch (keyEntered){
                case 'Z' :

                    for (Node node : groupGame.getChildren()) {
                        if( node instanceof Rectangle){
                            Rectangle r = ((Rectangle) node);
                            if((bomberman.getCenterX()>= r.getX() && bomberman.getCenterX()<=r.getX()+r.getWidth())){
                                if(bomberman.getCenterY()-bomberman.getRadius() <= r.getY() + r.getHeight() && bomberman.getCenterY()>=r.getY()){
                                    isMouvOk = false;
                                }
                            }
                        }
                    }
                    if(isMouvOk){
                        if (bomberman.getCenterY()- bomberman.getRadius() <= 20) {
                            bomberman.setCenterY(20 + bomberman.getRadius());
                        }


                        bomberman.setCenterY(bomberman.getCenterY() - bomberman.getRadius());
                    }
                    break;
                case 'S' :


                    //isMouvOk = true;
                    for (Node node : groupGame.getChildren()) {
                        if( node instanceof Rectangle){
                            Rectangle r = ((Rectangle) node);
                            if((bomberman.getCenterX()>= r.getX() && bomberman.getCenterX()<=r.getX()+r.getWidth())){
                                if(bomberman.getCenterY() <= r.getY() + r.getHeight() &&
                                        bomberman.getCenterY()+ bomberman.getRadius()>=r.getY()){

                                    isMouvOk = false;
                                }
                            }
                        }
                    }
                    if(isMouvOk) {
                        if (bomberman.getCenterY() >= HEIGHT) {
                            bomberman.setCenterY(HEIGHT - bomberman.getRadius());
                        }

                        bomberman.setCenterY(bomberman.getCenterY() + bomberman.getRadius());
                    }
                    break;
                case 'Q' :


                    //isMouvOk = true;
                    for (Node node : groupGame.getChildren()) {
                        if( node instanceof Rectangle){
                            Rectangle r = ((Rectangle) node);
                            if(bomberman.getCenterY() <= r.getY() + r.getHeight() &&
                                    bomberman.getCenterY()>=r.getY()){
                                if((bomberman.getCenterX()>= r.getX() && bomberman.getCenterX()-bomberman.getRadius()<=r.getX()+r.getWidth())){
                                    isMouvOk = false;
                                }
                            }
                        }
                    }
                    if(isMouvOk) {
                        if (bomberman.getCenterX() <= 25 + bomberman.getRadius()) {
                            bomberman.setCenterX(25 + bomberman.getRadius());
                        }

                        bomberman.setCenterX(bomberman.getCenterX() - bomberman.getRadius());
                    }
                    break;
                case 'D' :


                    //isMouvOk = true;
                    for (Node node : groupGame.getChildren()) {
                        if( node instanceof Rectangle){
                            Rectangle r = ((Rectangle) node);
                            if(bomberman.getCenterY() <= r.getY() + r.getHeight() &&
                                    bomberman.getCenterY()>=r.getY()){
                                if((bomberman.getCenterX()+bomberman.getRadius()>= r.getX() && bomberman.getCenterX()-bomberman.getRadius()<=r.getX())){
                                    isMouvOk = false;
                                }
                            }
                        }
                    }
                    if(isMouvOk) {
                        if (bomberman.getCenterX() >= 840 - bomberman.getRadius()) {
                            bomberman.setCenterX(840 - bomberman.getRadius());
                        }

                        bomberman.setCenterX(bomberman.getCenterX() + bomberman.getRadius());
                    }
                    break;


            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}





