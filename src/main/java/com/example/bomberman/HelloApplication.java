package com.example.bomberman;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;

import org.json.simple.JSONObject;


public class HelloApplication extends Application {


    static final int WIDTH = 1000;
    static final int HEIGHT = 800;
    static int scoreint = 0;
    List<Circle> listGhost;
    Circle pacman;
    List<Circle> listPoint;
    Text score;
    Button buttonPauseSceneGame;
    Scene sceneGame;
    Group groupGame;
    Stage primaryStage;
    Scene sceneMenu;
    Timeline tl;
    boolean gamePaused = true;
    Button buttonGoToHighScorePage;
    Scene sceneHighScorePage;
    Group groupHighScorePage;
    Button buttonStartMainMenu;
    Button buttonReturnToMenu;
    Text highscores;
    TextArea saisiePseudo;
    String pseudoPlayer;
    JSONObject highScoreData;
    List<Circle> listCerises;

    ArrayList<Map<String, Object>> itemsScore;
    Timer timerCerise = new Timer();

    HashMap<String, Integer> highScores;
    TableView tableHighScore;
    ArrayList<Map<String, Object>> scores;


    boolean pacManVorace = false;

    @Override
    public void start(Stage stage) throws Exception{
        primaryStage = stage;
        primaryStage.setTitle("Pacman");


        //SCENE MENU DEFINITION
        Group groupMenu = new Group();
        buttonStartMainMenu = new Button("Start The GAME!");
        buttonGoToHighScorePage = new Button("Go to highscore!");
        saisiePseudo = new TextArea();
        saisiePseudo.setPrefHeight(100);
        saisiePseudo.setPrefWidth(100);


        groupMenu.setLayoutX(WIDTH/2);
        groupMenu.setLayoutY(HEIGHT/2);
        VBox vbox = new VBox(buttonStartMainMenu, buttonGoToHighScorePage, saisiePseudo);
        groupMenu.getChildren().add(vbox);


        sceneMenu = new Scene(groupMenu, WIDTH, HEIGHT, Color.BLACK);
        primaryStage.setScene(sceneMenu);
        //END SCENE MENU DEFINITION

        //SCENE GAME DEFINITION
        Rectangle rContour = new Rectangle(0,0 ,WIDTH,HEIGHT);
        rContour.setFill(Color.TRANSPARENT);
        rContour.setStroke(Color.PURPLE);
        rContour.setStrokeWidth(10);
        groupGame = initializeGroupGame();

        sceneGame = new Scene(groupGame, WIDTH, HEIGHT, Color.BLACK);

        tl = new Timeline(new KeyFrame(Duration.millis(250), e -> run()));
        tl.setCycleCount(Timeline.INDEFINITE);


        handleGameEvent();

        //HighScorePage
        buttonReturnToMenu = new Button("Return to menu!");
        highscores = new Text();
        highscores.setX(200);
        highscores.setY(200);
        highscores.setFill(Color.WHITE);
        groupHighScorePage = new Group();
        groupHighScorePage.getChildren().add(buttonReturnToMenu);
        groupHighScorePage.getChildren().add(highscores);
        tableHighScore = new TableView();
        scores = new ArrayList<Map<String, Object>>();

        TableColumn<Map, String> col1 = new TableColumn<>("userName");
        col1.setCellValueFactory(new MapValueFactory<>("userName"));

        TableColumn<Map, String> col2 = new TableColumn<>("score");
        col2.setCellValueFactory(new MapValueFactory<>("score"));

        scores.addAll(getInfoFromFile());

        for (Map<String, Object> item:scores) {
            tableHighScore.getItems().addAll(item);
        }

        tableHighScore.getColumns().add(col1);
        tableHighScore.getColumns().add(col2);

        tableHighScore.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

      /*  VBox vBoxTableHighScore = new VBox();
        vBoxTableHighScore.getChildren().add(tableHighScore);
        vBoxTableHighScore.setAlignment(Pos.CENTER);
        */
        tableHighScore.setLayoutX(WIDTH/4);
        tableHighScore.setLayoutY(HEIGHT/4);
        groupHighScorePage.getChildren().add(tableHighScore);

        sceneHighScorePage = new Scene(groupHighScorePage, WIDTH, HEIGHT, Color.BLACK);

        primaryStage.show();

        buttonPauseSceneGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(sceneMenu);
                gamePaused = true;
                tl.pause();
            }
        });

        buttonStartMainMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(sceneGame);
                gamePaused = false;
                tl.play();
            }
        });

        buttonGoToHighScorePage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dataInHashMap();
                primaryStage.setScene(sceneHighScorePage);
                gamePaused = true;
                tl.pause();
            }
        });

        buttonReturnToMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(sceneMenu);
                gamePaused = true;
                tl.pause();
            }
        });
    }

    private void handleGameEvent() {
        sceneGame.setOnKeyPressed((KeyEvent event) -> {
            if (event.getText().isEmpty())
                return;
            char keyEntered = event.getText().toUpperCase().charAt(0);

            boolean isMouvOk = !gamePaused;
            switch (keyEntered){
                case 'Z' :
                    pacman.setRotate(-90);

                    for (Node node : groupGame.getChildren()) {
                        if( node instanceof Rectangle){
                            Rectangle r = ((Rectangle) node);
                            if((pacman.getCenterX()>= r.getX() && pacman.getCenterX()<=r.getX()+r.getWidth())){
                                if(pacman.getCenterY()-pacman.getRadius() <= r.getY() + r.getHeight() && pacman.getCenterY()>=r.getY()){
                                    isMouvOk = false;
                                }
                            }
                        }
                    }
                    if(isMouvOk){
                        if (pacman.getCenterY() <= 0) {
                            pacman.setCenterY(HEIGHT + pacman.getRadius());
                        }

                        isNextPositionAPoint(groupGame, listPoint, pacman, score);
                        pacman.setCenterY(pacman.getCenterY() - pacman.getRadius());
                    }
                    break;
                case 'S' :
                    pacman.setRotate(90);

                    //isMouvOk = true;
                    for (Node node : groupGame.getChildren()) {
                        if( node instanceof Rectangle){
                            Rectangle r = ((Rectangle) node);
                            if((pacman.getCenterX()>= r.getX() && pacman.getCenterX()<=r.getX()+r.getWidth())){
                                if(pacman.getCenterY() <= r.getY() + r.getHeight() &&
                                        pacman.getCenterY()+ pacman.getRadius()>=r.getY()){
                                    System.out.println("Bam");
                                    isMouvOk = false;
                                }
                            }
                        }
                    }
                    if(isMouvOk) {
                        if (pacman.getCenterY() >= HEIGHT) {
                            pacman.setCenterY(0 - pacman.getRadius());
                        }
                        isNextPositionAPoint(groupGame, listPoint, pacman, score);
                        pacman.setCenterY(pacman.getCenterY() + pacman.getRadius());
                    }
                    break;
                case 'Q' :
                    pacman.setRotate(180);

                    //isMouvOk = true;
                    for (Node node : groupGame.getChildren()) {
                        if( node instanceof Rectangle){
                            Rectangle r = ((Rectangle) node);
                            if(pacman.getCenterY() <= r.getY() + r.getHeight() &&
                                    pacman.getCenterY()>=r.getY()){
                                if((pacman.getCenterX()>= r.getX() && pacman.getCenterX()-pacman.getRadius()<=r.getX()+r.getWidth())){
                                    isMouvOk = false;
                                }
                            }
                        }
                    }
                    if(isMouvOk) {
                        if (pacman.getCenterX() <= 0) {
                            pacman.setCenterX(WIDTH + pacman.getRadius());
                        }
                        isNextPositionAPoint(groupGame, listPoint, pacman, score);
                        pacman.setCenterX(pacman.getCenterX() - pacman.getRadius());
                    }
                    break;
                case 'D' :
                    pacman.setRotate(0);

                    //isMouvOk = true;
                    for (Node node : groupGame.getChildren()) {
                        if( node instanceof Rectangle){
                            Rectangle r = ((Rectangle) node);
                            if(pacman.getCenterY() <= r.getY() + r.getHeight() &&
                                    pacman.getCenterY()>=r.getY()){
                                if((pacman.getCenterX()+pacman.getRadius()>= r.getX() && pacman.getCenterX()-pacman.getRadius()<=r.getX())){
                                    isMouvOk = false;
                                }
                            }
                        }
                    }
                    if(isMouvOk) {
                        if (pacman.getCenterX() >= WIDTH) {
                            pacman.setCenterX(0 - pacman.getRadius());
                        }
                        isNextPositionAPoint(groupGame, listPoint, pacman, score);
                        pacman.setCenterX(pacman.getCenterX() + pacman.getRadius());
                    }
                    break;
                case 'X' :
                    primaryStage.setScene(sceneMenu);
                    break;
                case 'P':
                    if(tl.getStatus() == Animation.Status.RUNNING){
                        tl.pause();
                        gamePaused = true;
                    }
                    else if(tl.getStatus() == Animation.Status.PAUSED){
                        tl.play();
                        gamePaused = false;
                    }

            }
        });
    }

    private Group initializeGroupGame() {
        Group group = new Group();
        group.getChildren().add(createObstacleOnScene(100,100, 100, 100));
        group.getChildren().add(createObstacleOnScene(800,100, 100, 100));
        group.getChildren().add(createObstacleOnScene(100,600, 100, 100));
        group.getChildren().add(createObstacleOnScene(800,600, 100, 100));
        group.getChildren().add(createObstacleOnScene(400, 300, 200, 200));

        buttonPauseSceneGame = new Button("Pause The GAME!");
        group.getChildren().add(buttonPauseSceneGame);

        score = new Text(WIDTH - 50,25, String.valueOf(scoreint));
        score.setFill(Color.WHITE);
        group.getChildren().add(score);

        pacman = new Circle(WIDTH/2,50, 25);
        // Image im = new Image("https://upload.wikimedia.org/wikipedia/commons/thumb/4/49/Pacman.svg/1200px-Pacman.svg.png",false);
        Image im = new Image("https://i.gifer.com/origin/64/649852e53b7e4edf15ea1c2f23a61f29_w200.gif",false);
        pacman.setFill(new ImagePattern(im));
        group.getChildren().add(pacman);

        Circle ghost1 = new Circle(700, 700, 25, Color.RED);
        Circle ghost2 = new Circle(700, 700, 25, Color.RED);
        Circle ghost3 = new Circle(700, 700, 25, Color.RED);
        Circle ghost4 = new Circle(700, 700, 25, Color.RED);
        Circle ghost5 = new Circle(700, 700, 25, Color.RED);

        listGhost = new ArrayList<Circle>();
        listGhost.add(ghost1);
        listGhost.add(ghost2);
        listGhost.add(ghost3);
        listGhost.add(ghost4);
        listGhost.add(ghost5);

        group.getChildren().add(ghost1);
        group.getChildren().add(ghost2);
        group.getChildren().add(ghost3);
        group.getChildren().add(ghost4);
        group.getChildren().add(ghost5);

        Circle cerise = new Circle(250, 250, 10, Color.PINK);
        group.getChildren().add(cerise);
        listCerises = new ArrayList<>();
        listCerises.add(cerise);

        listPoint = new ArrayList<Circle>();
       /* for(int i = 50; i < HEIGHT; i = i + 50){
            for(int j = 50; j < WIDTH; j = j + 50){
                boolean createOk = true;

                for (Node node : group.getChildren()) {
                    if (node instanceof Rectangle) {
                        Rectangle r = ((Rectangle) node);
                        if (j > r.getX() -1 && j < r.getX()+r.getWidth()+1){
                            if  ( i>r.getY()-1 && i<r.getY()+r.getHeight()+1){
                                createOk = false;
                            }
                        }
                    }
                }
                if(createOk){
                    Circle point = new Circle(j, i, 10, Color.GAINSBORO);
                    listPoint.add(point);
                }
            }
        }*/

      /*  for (Circle p : listPoint) {
            group.getChildren().add(p);
        }*/
        Circle point = new Circle(50, 50, 10, Color.GAINSBORO);
        listPoint.add(point);
        group.getChildren().add(listPoint.get(0));

        return group;
    }

    private void run() {
        Random r = new Random();
        Circle tempGhostToRemove = null;
        for (Circle ghost:listGhost ) {
            boolean chaseOn = false;
            if((Math.abs(ghost.getCenterX() - pacman.getCenterX()) +
                    Math.abs(ghost.getCenterY() - pacman.getCenterY())) < 500) {
                chaseOn = true;
            }

            if(chaseOn){
                double difX = ghost.getCenterX() - pacman.getCenterX();
                double difY = ghost.getCenterY() - pacman.getCenterY();
                if(Math.abs(difX) < Math.abs(difY)){
                    if(difY>0)  ghost.setCenterY(ghost.getCenterY()-25);
                    else ghost.setCenterY(ghost.getCenterY()+25);
                }
                else {
                    if(difX>0)  ghost.setCenterX(ghost.getCenterX()-25);
                    else ghost.setCenterX(ghost.getCenterX()+25);
                }
            }
            else {
                switch (r.nextInt(4)) {
                    case 0:
                        if (ghost.getCenterX() + 25 < WIDTH) {
                            ghost.setCenterX(ghost.getCenterX() + 25);
                        }
                        break;
                    case 1:
                        if (ghost.getCenterX() - 25 > 0) {
                            ghost.setCenterX(ghost.getCenterX() - 25);
                        }
                        break;
                    case 2:
                        if (ghost.getCenterY() - 25 > 0) {
                            ghost.setCenterY(ghost.getCenterY() - 25);
                        }
                        break;
                    case 3:
                        if (ghost.getCenterY() + 25 < HEIGHT) {
                            ghost.setCenterY(ghost.getCenterY() + 25);
                        }
                        break;
                }
            }
            if(ghost.getCenterX()==pacman.getCenterX() && ghost.getCenterY()==pacman.getCenterY()){

                if(!pacManVorace) {
                    tl.pause();
                    gamePaused = true;
                    highscores.setText(pseudoPlayer + " " + scoreint);
                    addScoreToHighScore();

                    primaryStage.setScene(sceneHighScorePage);

                    System.out.println("Game Over!");
                }
                else{
                    scoreint = scoreint + 10;
                    // groupGame.getChildren().
                    score.setText(String.valueOf(scoreint));
                    groupGame.getChildren().remove(ghost);
                    tempGhostToRemove = ghost;
                }
            }
        }
        if(tempGhostToRemove != null){
            listGhost.remove(tempGhostToRemove);
        }


        if(listPoint.size() == 0){
            System.out.println("Bravo!");
            tl.pause();
            gamePaused = true;
            addScoreToHighScore();
            primaryStage.setScene(sceneHighScorePage);
        }
    }

    private void addScoreToHighScore() {
        pseudoPlayer = saisiePseudo.getText();
        HashMap<String, Object> toAdd = new HashMap<>();
        toAdd.put("userName", pseudoPlayer);
        toAdd.put("score", scoreint);
        scores.add(toAdd);
        tableHighScore.getItems().add(toAdd);


    }

    private void isNextPositionAPoint(Group group, List<Circle> listPoint, Circle pacman, Text score) {
        Circle pointTempToRemove = null;
        for (Circle point : listPoint) {
            if(point.getCenterX() == pacman.getCenterX() && point.getCenterY() == pacman.getCenterY()){
                pointTempToRemove = point;
                group.getChildren().remove(point);
                scoreint++;
                score.setText(String.valueOf(scoreint));
            }
        }
        if(pointTempToRemove!=null) {
            listPoint.remove(pointTempToRemove);
        }
        for (Circle cerise : listCerises) {
            if(cerise.getCenterX() == pacman.getCenterX() && cerise.getCenterY() == pacman.getCenterY()){
                pointTempToRemove = cerise;
                group.getChildren().remove(cerise);
                pacManVorace = true;
                timerCerise.schedule(task, 10000L);
                System.out.println("pacManVorace : " + pacManVorace);
            }
        }
        if(pointTempToRemove!=null) {
            listCerises.remove(pointTempToRemove);
        }
    }
    TimerTask task = new TimerTask() {
        public void run() {
            pacManVorace = false;
            System.out.println("pacManVorace : " + pacManVorace);
        }
    };

    public Rectangle createObstacleOnScene(int x, int y, int width, int heigth){
        Rectangle r = new Rectangle(x, y, width, heigth);
        r.setFill(Color.GREEN);
        return r;
    }

    public boolean isMouvementAllowed(Group group, Circle pacman){
        for (Node node : group.getChildren()) {
            // node.getLayoutY()
        }

        return false;
    }







    /*public void writeHighScoreToFile(){

        JSONObject employeeDetails = new JSONObject();
        employeeDetails.put("firstName", "Lokesh");
        employeeDetails.put("lastName", "Gupta");
        employeeDetails.put("website", "howtodoinjava.com");

        JSONObject employeeObject = new JSONObject();
        employeeObject.put("employee", employeeDetails);

        //Add employees to list
        JSONArray employeeList = new JSONArray();
        employeeList.add(employeeObject);
        employeeList.add(employeeObject2);

        //Write JSON file
        try (FileWriter file = new FileWriter("./highscores.json")) {
            file.write(employeeList.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/

    public ArrayList<Map<String, Object>> getInfoFromFile()  {
      /*  highScoreData = new JSONObject();
        highScoreData.put("userName", "foo");
        highScoreData.put("score", new Integer(100));

        System.out.println(highScoreData);
*/

        Map<String, Object> item1 = new HashMap<>();
        item1.put("userName", "test1");
        item1.put("score", 2000);

        Map<String, Object> item2 = new HashMap<>();
        item2.put("userName", "test2");
        item2.put("score", 10000);

        ArrayList<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
        items.add(item1);
        items.add(item2);

        return items;
    }

    public void dataInHashMap(){
        highScores = new HashMap<String, Integer>();
        highScores.put("Joueur1", 50);
        highScores.put("Joueur2", 40);
        highScores.put("Joueur3", 60);

    }

    public static void main(String[] args) {

        ecritureTest();

        launch(args);
    }

    private static void ecritureTest() {



    }

}
