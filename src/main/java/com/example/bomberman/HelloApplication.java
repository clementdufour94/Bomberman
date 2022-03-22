package com.example.bomberman;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;


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
    static int heartint=3;
    Text bombes;
    Group group;


    ImageView bombeviewgif;
    ImageView heartview;
    Timeline tl;
    Timer timer;
    Text heart;


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        primaryStage = stage;
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT) ;
        Button buttonJouer = new Button("Jouer");
        Button buttonRegle = new Button("Règle");
        Button buttonQuitter = new Button("Quitter");
        Group menuPrincipal = new Group();


        tl = new Timeline(new KeyFrame(Duration.millis(250), e -> run()));
        tl.setCycleCount(Timeline.INDEFINITE);

        stage.setTitle("Bomberman!");
        menuPrincipal.setLayoutX(WIDTH/2);
        menuPrincipal.setLayoutY(HEIGHT/2);
        VBox vbox = new VBox(buttonJouer, buttonRegle, buttonQuitter);
        menuPrincipal.getChildren().add(vbox);
        sceneMenu = new Scene(menuPrincipal, WIDTH, HEIGHT, Color.GRAY);
        stage.setScene(sceneMenu);
        stage.show();
        groupGame = initializeGroupGame();
        sceneGame = new Scene(groupGame, WIDTH, HEIGHT, Color.GRAY);
        buttonJouer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(sceneGame);
                tl.play();




            }
        });
        handleGameEvent();

    }

    private void run(){

        //la on ou va mettre les ennemies et les déplacments


    }


    private Group initializeGroupGame() {
        group = new Group();

        Image bombepng = new Image("https://img2.freepng.fr/20171217/e0e/bomb-png-5a371a4e3e3042.0391952015135606542547.jpg", false);
        Image heartpng = new Image("https://e7.pngegg.com/pngimages/935/758/png-clipart-minecraft-video-game-health-game-result-text-rectangle.png", false);

        ImageView bombeview = new ImageView(bombepng);
        bombeview.setY(60);
        bombeview.setX(880);
        bombeview.setFitWidth(70);
        bombeview.setFitHeight(70);
        bombeview.setPreserveRatio(true);
        group.getChildren().add(bombeview);


        ImageView heartview = new ImageView(heartpng);
        heartview.setY(220);
        heartview.setX(880);
        heartview.setFitWidth(70);
        heartview.setFitHeight(70);
        heartview.setPreserveRatio(true);
        group.getChildren().add(heartview);

        Image bombegif = new Image("https://www.informatiquegifs.com/explosion/gifs-explosion-8.gif",false);
        bombeviewgif = new ImageView(bombegif);
        bombeviewgif.setFitWidth(40);
        bombeviewgif.setFitHeight(40);
        bombeviewgif.setPreserveRatio(true);


        bombes = new Text(900,45, String.valueOf(bombint));
        bombes.setFill(Color.WHITE);
        bombes.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
        group.getChildren().add(bombes);


        heart = new Text(900, 200, String.valueOf(heartint));
        heart.setFill(Color.WHITE);
        heart.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
        group.getChildren().add(heart);

        timer = new Timer();

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

                case 'B':
                    new Timeline(new KeyFrame(
                            Duration.seconds(4),
                            ae->{


                                isNextPositionAWall(groupGame, listPoint,listPoint2,listPoint3,bombeviewgif);
                                group.getChildren().remove(bombeviewgif);



                            })).play();


                    if(bombint >0){
                        group.getChildren().add(bombeviewgif);
                        bombeviewgif.setY(bomberman.getCenterY()-bomberman.getRadius());
                        bombeviewgif.setX(bomberman.getCenterX()-bomberman.getRadius());



                    }


                    break;


            }
        });


    }
    private void isNextPositionAWall(Group group, List<Rectangle> listPoint, List<Rectangle> listPoint2, List<Rectangle> listPoint3, ImageView bombeviewgif){
        Rectangle wallTempToRemove =null;
        for(Rectangle wall : listPoint2){
            if(wall.getX()  == bombeviewgif.getX() && wall.getY() == bombeviewgif.getY()
                    || wall.getX() >= bombeviewgif.getX()+10 && wall.getY() >= bombeviewgif.getY()
                    || wall.getX() >= bombeviewgif.getX() && wall.getY() >= bombeviewgif.getY()+10
                    || wall.getX() >= bombeviewgif.getX()+10 && wall.getY() >= bombeviewgif.getY()+10
                    || wall.getX() >= bombeviewgif.getX()-10 && wall.getY() >= bombeviewgif.getY()
                    || wall.getX() >= bombeviewgif.getX() && wall.getY() >= bombeviewgif.getY()-10
                    ||wall.getX() >= bombeviewgif.getX()-10 && wall.getY()>=bombeviewgif.getY()-10
            ){
                wallTempToRemove = wall;
                group.getChildren().remove(wall);
            }
            if(wallTempToRemove!=null){
                listPoint2.remove(wallTempToRemove);
            }



        }

    }



    public static void main(String[] args) {
        launch();
    }
}