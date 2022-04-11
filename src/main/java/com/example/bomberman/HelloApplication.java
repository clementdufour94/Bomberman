package com.example.bomberman;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.*;
import java.nio.file.Paths;
import java.util.*;

import static javafx.collections.FXCollections.observableArrayList;


public class HelloApplication extends Application {

    static final int WIDTH = 1080; //40*27 donc 21 blocs de large
    static final int HEIGHT = 800; //40*20 donc 20 bloc de haut
    Scene sceneMenu;

    Stage primaryStage;
    Group groupGame;
    Group groupSettings;

    List<Rectangle> listWall;
    List<Rectangle> listWall2;
    List<ImageView> listBombs;
    List<ImageView> listBagOfCoin;
    Scene sceneGame;
    Scene scenePause;
    Scene sceneSetting;
    List<Circle>listEnnemy;
    Circle bomberman;
    static int bombint = 9;
    static  int heartint =3;
    public int score=0;
    Text bombes;
    Text hearts;
    Text scores;
    Group group;
    Integer timerBomb =0;
    Integer timer = 0;
    boolean gamePaused = true;
    FileWriter fileWriter;
    FileReader fileReader;
    JSONParser jsonParser;
    int restart = 0;

    MediaPlayer mediaPlayer;
    MediaPlayer playerHover;
    MediaPlayer playerExplosion;
    boolean isGameOver;



    Integer secondeUnite = 0;
    Integer secondeDizaine =0;
    Integer minuteUnite =0;
    Integer minuteDizaine=0;
    Integer seconde=0;
    Integer minute=0;
    Text timerSeparator;


    Integer iTimerfix =0;

    ImageView bombeviewgif;
    Timeline tl;
    Timeline explosion;
    Timeline bombe;
    Timeline coin;
    Image bombegif;

    Boolean isAllowedBomb =true;


    Text timertextSeconde;
    Text timertextSecondeDizaine;
    Text timertextMinute;
    Text timertextMinuteDizaine;
    Group groupMenu;
    Group groupPause;

    ImageView explosion1View;
    ImageView explosion2View;
    ImageView explosion3View;
    ImageView explosion4View;
    ImageView explosion5View;
    ImageView explosion6View;
    ImageView explosion0View;
    Integer variable = 0;
    Double scoreFinal;

    ImageView coin1View;
    ImageView coin2View;
    ImageView coin3View;
    ImageView coin4View;
    ImageView coin5View;
    ImageView coin6View;

    FileInputStream inputFace;
    Image imageFace;
    ImagePattern imagePatternFace;

    FileInputStream inputDos;
    Image imageBack ;
    ImagePattern imagePatternBack;

    FileInputStream inputGauche;
    Image imageGauche;
    ImagePattern imagePatternGauche;

    FileInputStream inputDroite;
    Image imageDroite;
    ImagePattern imagePatternDroite;

    Integer variableCoin = 0;
    Reflection reflection;
    ImageView buttonRestartView;
    TextArea nameArea;
    String pseudoPlayer;
    Text highScoreText2;
    Text highScoreText3;
    GaussianBlur gaussianBlur;

    Timeline invincible;
    Boolean isInvincible = false;
    public int[][] tiles;
    public int[][] map1 ={
            {1,0,0,6,0,2,2,2,2,2,0,2,0,1,2,0,0,2,0,0,1},
    {0,0,0,0,0,2,1,1,1,2,0,2,5,0,2,0,0,2,0,5,1},
    {0,0,0,0,0,2,2,2,1,2,2,1,0,0,2,0,0,2,0,0,1},
    {0,0,0,0,1,2,0,2,1,2,2,1,0,1,1,0,1,2,0,3,1},
    {0,0,0,1,1,2,0,0,1,1,1,1,1,1,2,1,0,2,1,1,1},
    {0,0,1,1,2,2,0,0,0,0,2,1,2,2,2,0,0,2,1,1,1},
    {0,1,1,2,2,0,0,0,0,0,2,1,2,0,0,0,0,2,0,0,1},
    {2,1,2,2,0,0,0,0,0,0,2,2,2,0,0,0,0,2,0,0,1},
    {2,1,2,1,0,0,0,0,0,0,0,0,0,0,0,0,0,2,5,0,1},
    {2,2,2,1,0,0,0,0,1,1,0,0,0,0,0,0,0,2,0,0,1},
    {0,1,2,1,0,1,0,0,1,1,0,0,0,0,0,0,0,2,0,0,1},
    {0,1,2,0,1,1,1,2,1,1,2,2,2,2,2,2,2,2,2,2,1},
    {1,1,2,0,0,1,2,2,2,2,2,2,2,2,2,1,1,1,1,2,1},
    {4,1,2,0,0,0,2,1,2,0,0,0,0,0,2,1,2,4,1,2,1},
    {2,1,1,0,0,0,0,0,2,0,0,3,0,0,2,1,2,0,1,2,1},
    {2,2,1,0,5,0,1,1,2,0,0,0,0,2,2,1,2,0,1,2,1},
    {0,2,2,1,0,0,1,1,2,0,5,0,0,2,1,1,2,5,1,2,1},
    {0,2,2,1,1,1,1,1,2,0,0,0,0,2,1,1,2,0,1,2,1},
    {0,2,2,2,2,2,2,2,2,0,0,0,0,2,2,2,1,0,1,2,1},
    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,1,0,1}
};
    public int[][] map2 = {
            {1,1,1,0,0,2,0,0,1,0,0,4,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,2,0,0,1,0,5,0,5,0,0,0,0,0,0,0,1},
            { 1,4,0,0,0,2,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,5,0,0,0,2,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1},
            { 1,3,0,0,0,2,0,1,1,1,1,1,1,1,1,2,0,0,0,0,1},
            {1,0,0,0,2,0,0,1,0,0,0,0,0,0,1,2,0,0,0,0,1},
            { 1,2,0,0,2,0,0,1,0,0,0,0,0,0,1,2,0,0,2,2,1},
            {1,1,2,0,2,5,2,1,0,0,0,0,0,0,1,2,0,2,0,0,1},
            {0,0,1,0,2,2,0,1,0,0,6,0,0,0,1,2,2,0,0,3,1},
            {0,0,0,1,2,2,3,1,0,0,0,0,0,0,1,2,0,0,0,0,1},
            {0,0,0,0,1,2,0,1,0,0,0,0,0,0,1,2,0,0,0,0,1},
            {2,2,0,2,0,1,2,1,1,2,2,2,1,1,1,2,2,2,2,2,1},
            {0,0,2,0,0,2,0,0,2,2,2,2,2,0,0,0,2,2,2,2,1},
            {5,4,2,2,2,2,0,0,2,0,0,3,2,0,0,0,0,2,2,2,1},
            {0,0,2,0,0,2,0,0,2,0,0,0,2,0,0,0,0,0,2,2,1},
            {2,2,0,2,0,2,0,0,2,0,5,0,2,0,0,0,0,0,2,0,1},
            {0,0,0,0,0,1,0,0,2,0,0,0,2,0,0,0,0,0,0,0,1},
            { 0,0,0,0,1,0,0,0,2,0,0,0,2,0,0,0,2,0,0,5,1},
            {0,0,0,1,0,0,0,0,2,0,0,0,2,0,0,2,0,2,0,0,1},
            {1,1,1,0,0,0,0,0,2,0,0,0,2,2,2,0,0,0,2,2,1}
    };


    @Override
    public void start(Stage stage)  {

        try{
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
            primaryStage = stage;
            Scene scene = new Scene(fxmlLoader.load(), 850, HEIGHT) ;



            stage.setTitle("Bomberman!");

            groupGame = initializeGroupGame();
            sceneGame = new Scene(groupGame, WIDTH, HEIGHT, Color.GRAY);

            //Ici

            if(restart==0){
                buttonMenu();
                sceneMenu = new Scene(groupMenu, WIDTH, HEIGHT, Color.GRAY);


                stage.setScene(sceneMenu);
                stage.show();

            }else if(restart==1){
                primaryStage.setScene(sceneGame);

                gamePaused = false;
                tl.play();
                coin.play();
                restart=0;
                primaryStage.show();
            }

            FileInputStream inputExplosion1 = new FileInputStream("src/Images/Bombes/Explosions/explosion_1.png");
            Image explosion1 = new Image(inputExplosion1,340,340,true,false);
            explosion1View = new ImageView(explosion1);
            explosion1View.setX(-350);
            explosion1View.setY(-100);
            explosion1View.setEffect(reflection);



            FileInputStream inputExplosion2 = new FileInputStream("src/Images/Bombes/Explosions/explosion_2.png");
            Image explosion2 = new Image(inputExplosion2,340,340,true,false);
            explosion2View = new ImageView(explosion2);
            explosion2View.setX(-350);
            explosion2View.setY(-100);
            explosion2View.setEffect(reflection);


            FileInputStream inputExplosion3 = new FileInputStream("src/Images/Bombes/Explosions/explosion_3.png");
            Image explosion3 = new Image(inputExplosion3,340,340,true,false);
            explosion3View = new ImageView(explosion3);
            explosion3View.setX(-350);
            explosion3View.setY(-100);
            explosion3View.setEffect(reflection);


            FileInputStream inputExplosion4 = new FileInputStream("src/Images/Bombes/Explosions/explosion_4.png");
            Image explosion4 = new Image(inputExplosion4,340,340,true,false);
            explosion4View = new ImageView(explosion4);
            explosion4View.setX(-350);
            explosion4View.setY(-100);
            explosion4View.setEffect(reflection);


            FileInputStream inputExplosion5 = new FileInputStream("src/Images/Bombes/Explosions/explosion_5.png");
            Image explosion5 = new Image(inputExplosion5,340,340,true,false);
            explosion5View = new ImageView(explosion5);
            explosion5View.setX(-350);
            explosion5View.setY(-100);
            explosion5View.setEffect(reflection);


            FileInputStream inputExplosion6 = new FileInputStream("src/Images/Bombes/Explosions/explosion_6.png");
            Image explosion6 = new Image(inputExplosion6,340,340,true,false);
            explosion6View = new ImageView(explosion6);
            explosion6View.setX(-350);
            explosion6View.setY(-100);
            explosion6View.setEffect(reflection);

            FileInputStream inputExplosion0 = new FileInputStream("src/Images/Bombes/bombesprite.png");
            Image explosion0 = new Image(inputExplosion0,340,340,true,false);
            explosion0View = new ImageView(explosion0);
            explosion0View.setX(-350);
            explosion0View.setY(-100);
            explosion0View.setEffect(reflection);


            FileInputStream inputCoin1 = new FileInputStream("src/Images/Coins/star coin rotate 1.png");
            Image coin1 = new Image(inputCoin1,70,70,true,false);
            coin1View = new ImageView(coin1);
            coin1View.setX(880);
            coin1View.setY(300);
            coin1View.setEffect(reflection);

            FileInputStream inputCoin2 = new FileInputStream("src/Images/Coins/star coin rotate 2.png");
            Image coin2 = new Image(inputCoin2,70,70,true,false);
            coin2View = new ImageView(coin2);
            coin2View.setX(880);
            coin2View.setY(300);
            coin2View.setEffect(reflection);

            FileInputStream inputCoin3 = new FileInputStream("src/Images/Coins/star coin rotate 3.png");
            Image coin3 = new Image(inputCoin3,70,70,true,false);
            coin3View = new ImageView(coin3);
            coin3View.setX(880);
            coin3View.setY(300);
            coin3View.setEffect(reflection);

            FileInputStream inputCoin4 = new FileInputStream("src/Images/Coins/star coin rotate 4.png");
            Image coin4 = new Image(inputCoin4,70,70,true,false);
            coin4View = new ImageView(coin4);
            coin4View.setX(880);
            coin4View.setY(300);
            coin4View.setEffect(reflection);

            FileInputStream inputCoin5 = new FileInputStream("src/Images/Coins/star coin rotate 5.png");
            Image coin5 = new Image(inputCoin5,70,70,true,false);
            coin5View = new ImageView(coin5);
            coin5View.setX(880);
            coin5View.setY(300);
            coin5View.setEffect(reflection);

            FileInputStream inputCoin6 = new FileInputStream("src/Images/Coins/star coin rotate 6.png");
            Image coin6 = new Image(inputCoin6,70,70,true,false);
            coin6View = new ImageView(coin6);
            coin6View.setX(880);
            coin6View.setY(300);
            coin6View.setEffect(reflection);




            buttonPause();


            handleGameEvent();

        }catch (IOException e){
            System.out.println("erreur" + e);

        }



    }

    public void isNextABomb(Group group, List<ImageView> listBombs, Circle bomberman){

        ImageView bombTempToRemove = null;

        for(ImageView bombe : listBombs){
            if(bomberman.getCenterX()+bomberman.getRadius()==bombe.getX() && bomberman.getCenterY()+bomberman.getRadius()==bombe.getY()
            || bomberman.getCenterX()+bomberman.getRadius()+40>=bombe.getX() && bombe.getX()>=bomberman.getCenterX()-bomberman.getRadius()-40
            && bomberman.getCenterY()+bomberman.getRadius()+40>=bombe.getY() && bombe.getY()>=bomberman.getCenterY()-bomberman.getRadius()-40)
            {
                bombTempToRemove = bombe;
                group.getChildren().remove(bombe);
                bombint +=7;


            }
            if(bombTempToRemove!=null){
                listBombs.remove(bombTempToRemove);
            }
        }


    }
    public void isNextACoin(Group group, List<ImageView> listBagOfCoin, Circle bomberman){

        ImageView bagTempToRemove = null;

        for(ImageView coin : listBagOfCoin){
            if(bomberman.getCenterX()+bomberman.getRadius()==coin.getX() && bomberman.getCenterY()+bomberman.getRadius()==coin.getY()
                    || bomberman.getCenterX()+bomberman.getRadius()+40>=coin.getX() && coin.getX()>=bomberman.getCenterX()-bomberman.getRadius()-40
                    && bomberman.getCenterY()+bomberman.getRadius()+40>=coin.getY() && coin.getY()>=bomberman.getCenterY()-bomberman.getRadius()-40)
            {
                bagTempToRemove = coin;
                group.getChildren().remove(coin);
                score +=10;


            }
            if(bagTempToRemove!=null){
                listBagOfCoin.remove(bagTempToRemove);
            }
        }


    }


    public void highScore(){


        Group highscoreGroup = new Group();
        Scene highscoreScene = new Scene(highscoreGroup,WIDTH, HEIGHT, Color.GRAY);
        Text highScoreText = new Text("Meilleur Score :");
        highScoreText.setTextAlignment(TextAlignment.CENTER);
        highScoreText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));



        try{

            fileReader = new FileReader("highscore.json");
            ImageView buttonBackView = new ImageView(new Image((new FileInputStream("src/Images/Button/button_back.png")),150,150,true,false));



            BufferedReader br = new BufferedReader(fileReader);

            if(br.readLine() == null){ // Si le fichier Json est vide alors on le remplis
                JSONObject obj = new JSONObject();
                fileWriter = new FileWriter("highscore.json");
                obj.put("nom",pseudoPlayer);
                obj.put("score",String.valueOf(scoreFinal.intValue()));
                fileWriter.write(obj.toString());
                fileWriter.flush();
                fileWriter.close();
            }else{ // Sinon on le lis et on récupère le meilleur score actuel
                System.out.println("Fichier remplis");
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("highscore.json"));
                String value = (String) jsonObject.get("score");
                String valueName = (String) jsonObject.get("nom");
                System.out.println(value);

                if(scoreFinal.intValue()>=Double.parseDouble(value)){ // Si le score actuel est meilleur que le meilleur score affiché alors on le remplis
                 System.out.println("Nouveau meilleur score");
                    JSONObject obj = new JSONObject();
                    fileWriter = new FileWriter("highscore.json");
                    obj.put("nom",pseudoPlayer);
                    obj.put("score",String.valueOf(scoreFinal.intValue()));
                    fileWriter.write(obj.toString());
                    fileWriter.flush();
                    fileWriter.close();

                    highScoreText2 = new Text("nom :" + " " + pseudoPlayer);
                    highScoreText3 = new Text("score :" + String.valueOf(scoreFinal.intValue()));
                    highScoreText2.setFont(Font.font("verdana",  FontPosture.REGULAR, 40));
                    highScoreText3.setFont(Font.font("verdana",  FontPosture.REGULAR, 40));

                 }else if(Double.parseDouble(value)>scoreFinal.intValue()){ // Sinon

                 System.out.println("Dommage vous n'avez pas réussi à battre le meilleur score");
                 highScoreText2 = new Text("nom:"+" " + valueName);
                 highScoreText3 = new Text("score :" + String.valueOf(value));
                 highScoreText2.setFont(Font.font("verdana",  FontPosture.REGULAR, 40));
                 highScoreText3.setFont(Font.font("verdana",  FontPosture.REGULAR, 40));
                 }





            }
            buttonBackView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {

                    primaryStage.setScene(sceneMenu);

                    explosion.play();
                }
            });
            buttonBackView.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {

                    musicHover();
                }
            });
            VBox vboxHightScore = new VBox(highScoreText,highScoreText2,highScoreText3);
            vboxHightScore.setLayoutX(400);
            vboxHightScore.setLayoutY(200);
            vboxHightScore.setSpacing(5);
            vboxHightScore.setAlignment(Pos.BASELINE_CENTER);


            highscoreGroup.getChildren().add(vboxHightScore);
            highscoreGroup.getChildren().add(buttonBackView);


            primaryStage.setScene(highscoreScene);


            //DEBUT TEST
            //JSONObject obj = new JSONObject(file.toString());
            //String jsonString ="{\"name\":\"Alice\",\"age\": 20}";

            //String n = obj.getString("name");
            //int a = obj.getInt("age");
            //System.out.println(n + " " + a );


            //Pour écrire dans un fihier txt avec du json
            //JSONObject jo = new JSONObject();
            //jo.put("Score", 11);
            //jo.put("Name", saisiePseudo.getText());

            //JSONObject test = new JSONObject();
            //test.put("Score", 12);
            //test.put("Name", saisiePseudo.getText());

            //JSONArray ja = new JSONArray();
            //ja.put(test);
            //ja.put(jo);
            //FIN TEST
        }catch(IOException e){

        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    public void isAllEnemiesDied(List<Circle> listEnnemy){

        if(listEnnemy.size()==0){




            Win();


        }

    }

    public void music(){
        String s ="screen.mp3";
        Media h = new Media(Paths.get(s).toUri().toString());
        mediaPlayer = new MediaPlayer(h);

        mediaPlayer.play();

    }
    public void musicHover(){
        String s = "rollover.mp3";
        Media h = new Media(Paths.get(s).toUri().toString());
        playerHover = new MediaPlayer(h);
        playerHover.setCycleCount(1);
        playerHover.play();
    }

    public void musicExplosion(){
        String s = "explosion.mp3";
        Media h = new Media(Paths.get(s).toUri().toString());
        playerExplosion = new MediaPlayer(h);
        playerExplosion.setCycleCount(1);
        playerExplosion.setVolume(100);
        playerExplosion.play();
    }

    private Group buttonSetting() throws FileNotFoundException{

        groupSettings = new Group();
        FileInputStream inputLeaderBoard = new FileInputStream("src/Images/Button/button_leaderboard.png");
        Image buttonLeaderBoard = new Image(inputLeaderBoard,340,340,true,false);
        ImageView buttonLeaderBoardView = new ImageView(buttonLeaderBoard);

        FileInputStream innputBack = new FileInputStream("src/Images/Button/button_back.png");
        Image buttonBack = new Image(innputBack,150,150,true,false);
        ImageView buttonBackView = new ImageView(buttonBack);


        Text rules = new Text("" +
                "Le but du jeu est de tuer tous les ennemis le plus rapidement possible. Pour cela vous \n" +
                "commencez la partie avec 9 bombes et 3 vies. Chaque fois que vous vous faites toucher\n" +
                "par un ennemi vous perdez une vie et vous êtes invincibles pendant 4 secondes. Vous \n" +
                "pouvez récupérer sur la map des bombes (chaque bombe récupérée vous donne 9 bombes) \n" +
                "et des pièces (chaque sac vous donne 10 pièces). \n \n" +
                "Chaque fois que vous tuez un monstre vous gagnez 5 pièces et dès que vous cassez un \n " +
                "mur vous en gagnez 3. Les murs cassables sont ceux en briques, vous pouvez poser une \n" +
                "bombe seulement toutes les 4 secondes.  Pour vous déplacer utiliser Z,Q,S,D, \n" +
                "pour poser une bombe B et pour mettre en pause P. A la fin de la partie votre \n" +
                "score est calculé sur la base de votre score par rapport au temps multiplié par 0.001.");
        rules.setFont(Font.font("verdana", FontPosture.REGULAR, 23));
        rules.setTextAlignment(TextAlignment.CENTER);
        rules.setY(350);
        VBox vboxSettings = new VBox(buttonLeaderBoardView);
        vboxSettings.setLayoutX(400);
        vboxSettings.setLayoutY(200);
        vboxSettings.setSpacing(10);
        groupSettings.getChildren().add(rules);
        groupSettings.getChildren().add(vboxSettings);
        groupSettings.getChildren().add(buttonBackView);

        buttonBackView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

               primaryStage.setScene(sceneMenu);
               explosion.play();
            }
        });
        buttonBackView.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                musicHover();
            }
        });
        buttonLeaderBoardView.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                musicHover();
            }
        });
        buttonLeaderBoardView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                getHighScore();



            }
        });


        return groupSettings;
    }
    public void getHighScore(){

        Group getHighScoreGroup = new Group();
        Scene getHighScoreScene = new Scene(getHighScoreGroup, WIDTH,HEIGHT,Color.GRAY);
        VBox vboxGetHighScore = new VBox();
        Text highScoreText = new Text();





        try{

            FileInputStream innputBack = new FileInputStream("src/Images/Button/button_back.png");
            Image buttonBack = new Image(innputBack,150,150,true,false);
            ImageView buttonBackView = new ImageView(buttonBack);




            fileReader = new FileReader("highscore.json");
            BufferedReader br = new BufferedReader(fileReader);
            if(br.readLine() == null){
                highScoreText = new Text("Il y a actuellement aucune donnée enregistré ");
                highScoreText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));


                vboxGetHighScore = new VBox(highScoreText);
            }else{
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("highscore.json"));
                String value = (String) jsonObject.get("score");
                String valueName = (String) jsonObject.get("nom");
                highScoreText = new Text("Meilleur Score : ");
                Text highScoreNameText = new Text(" nom : " + valueName + "\n" + "score : " + value);
                highScoreNameText.setFont(Font.font("verdana", FontPosture.REGULAR, 40));
                highScoreText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
                vboxGetHighScore = new VBox(highScoreText,highScoreNameText);

            }
            buttonBackView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    if(isGameOver=true){
                        explosion.play();
                        primaryStage.setScene(sceneMenu);



                        isGameOver=false;
                    }else{
                        primaryStage.setScene(sceneSetting);

                    }




                }
            });
            buttonBackView.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {

                    musicHover();
                }
            });
            vboxGetHighScore.setLayoutX(400);
            vboxGetHighScore.setLayoutY(200);
            vboxGetHighScore.setSpacing(5);
            getHighScoreGroup.getChildren().add(vboxGetHighScore);
            getHighScoreGroup.getChildren().add(buttonBackView);
            primaryStage.setScene(getHighScoreScene);


        }catch (IOException e){

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private Scene buttonPause() throws FileNotFoundException{
        groupPause = new Group();

        FileInputStream inputResume = new FileInputStream("src/Images/Button/Button_Resume.png");
        Image buttonResume = new Image(inputResume,340,340,true,false);
        ImageView buttonResumeView = new ImageView(buttonResume);

        FileInputStream inputRestart = new FileInputStream("src/Images/Button/Button_Restart.png");
        Image buttonRestart = new Image(inputRestart,340,340,true,false);
        ImageView buttonRestartView = new ImageView(buttonRestart);

        FileInputStream inputExit = new FileInputStream("src/Images/Button/button_exit.png");
        Image buttonExit = new Image(inputExit,340,340,true,false);
        ImageView buttonExitView = new ImageView(buttonExit);
        buttonExitView.setEffect(reflection);

        VBox vboxButtonPause = new VBox(buttonResumeView,buttonRestartView,buttonExitView);
        vboxButtonPause.setPrefSize(10,10);
        groupPause.getChildren().add(vboxButtonPause);
        groupPause.setLayoutX(370);
        groupPause.setLayoutY(200);

        scenePause = new Scene(groupPause, WIDTH, HEIGHT, Color.GRAY);
        buttonResumeView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                primaryStage.setScene(sceneGame);

                tl.play();

                gamePaused = false;

            }
        });



        buttonExitView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {



                Platform.exit();
            }
        });

        buttonRestartView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                try {
                    restart=1;


                    start(primaryStage);
                    primaryStage.setScene(sceneGame);

                    groupGame = initializeGroupGame();

                }catch (IOException e){
                    System.out.println("erreur");

                }




            }
        });




        return scenePause;
    }


    private Group buttonMenu() throws FileNotFoundException{

        groupMenu = new Group();

        nameArea = new TextArea();
        nameArea.setPrefWidth(100);
        nameArea.setPrefHeight(50);





        explosion.play();
        FileInputStream inputPlay = new FileInputStream("src/Images/Button/button_play.png");
        Image buttonPlay = new Image(inputPlay,340,340,true,false);
        ImageView buttonPlayView = new ImageView(buttonPlay);



        FileInputStream inputSetting = new FileInputStream("src/Images/Button/button_settings.png");
        Image buttonSetting = new Image(inputSetting,340,340,true,false);
        ImageView buttonSettingView = new ImageView(buttonSetting);


        FileInputStream inputExit = new FileInputStream("src/Images/Button/button_exit.png");
        Image buttonExit = new Image(inputExit,340,340,true,false);
        ImageView buttonExitView = new ImageView(buttonExit);
        buttonExitView.setEffect(reflection);

        VBox vboxButton = new VBox(nameArea,buttonPlayView,buttonSettingView,buttonExitView);
        vboxButton.setPrefSize(10,10);
        groupMenu.getChildren().add(vboxButton);
        groupMenu.setLayoutX(370);
        groupMenu.setLayoutY(200);


        buttonPlayView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {


                explosion.stop();
                primaryStage.setScene(sceneGame);
                gamePaused = false;
                tl.play();
                coin.play();
                event.consume();
            }
        });
        buttonSettingView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                explosion.stop();

                try{
                    buttonSetting();
                    sceneSetting = new Scene(groupSettings, WIDTH,HEIGHT,Color.GRAY);
                    primaryStage.setScene(sceneSetting);


                    event.consume();

                }catch (IOException e){
                    System.out.println("eereru" + e);


                }






            }
        });

        buttonExitView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                Platform.exit();
            }
        });
        buttonExitView.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                musicHover();
            }
        });
        buttonPlayView.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                musicHover();
            }
        });
        buttonSettingView.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                musicHover();
            }
        });


        return groupMenu;
    }
    private void gameOver(){
        tl.stop();
        isGameOver =true;
        Group gameOverGroup = new Group();
        Scene gameOverScene = new Scene(gameOverGroup,WIDTH,HEIGHT,Color.GRAY);
        ImageView gameOverView = new ImageView();
        Text text = new Text("Appuyez n'importe où sur l'écran pour afficher le meilleur score");

        text.setFont(Font.font("verdana", FontPosture.REGULAR, 20));

        try{
             gameOverView = new ImageView(new Image(new FileInputStream("src/Images/Misc/GameOver.png")));

            gameOverGroup.getChildren().add(gameOverView);
            gameOverScene.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    getHighScore();
                }
            });


        }catch (IOException e){

        }
        VBox gameOverVbox = new VBox(gameOverView);
        gameOverVbox.setLayoutX(25);
        gameOverVbox.setLayoutY(200);
        gameOverVbox.setSpacing(50);
        gameOverGroup.getChildren().add(gameOverVbox);
        text.setX(250);
        text.setY(500);
        gameOverGroup.getChildren().add(text);
        primaryStage.setScene(gameOverScene);


    }
    private  void Win(){
        tl.stop();
        scoreFinal = score/(seconde*0.001);

        pseudoPlayer = nameArea.getText();
        Group winGroup = new Group();
        Scene winScene = new Scene(winGroup,WIDTH,HEIGHT,Color.GRAY);
        ImageView winView = new ImageView();
        Text text = new Text("Bien joué " +pseudoPlayer + " Votre score est "+scoreFinal.intValue() + "\n" + "\n" +"Appuyez n'importe où sur l'écran pour afficher le meilleur score");
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFont(Font.font("verdana", FontPosture.REGULAR, 20));

        try{

            winView = new ImageView(new Image(new FileInputStream("src/Images/Misc/YouWin.png"),500,500,true,false));
            winView.setX(275);


            winGroup.getChildren().add(winView);
            winScene.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    highScore();
                }
            });


        }catch (IOException e){

        }


        text.setX(250);
        text.setY(500);
        winGroup.getChildren().add(text);
        primaryStage.setScene(winScene);

    }
    private void runCoin(){

        if (variableCoin==6){

            groupGame.getChildren().remove(coin6View);
            variableCoin =0;


        }
        if (variableCoin==5){
            groupGame.getChildren().remove(coin5View);
            groupGame.getChildren().add(coin6View);

        }
        if (variableCoin==4){
            groupGame.getChildren().remove(coin4View);
            groupGame.getChildren().add(coin5View);

        }
        if (variableCoin==3){
            groupGame.getChildren().remove(coin3View);
            groupGame.getChildren().add(coin4View);

        }
        if (variableCoin==2){
            groupGame.getChildren().remove(coin2View);
            groupGame.getChildren().add(coin3View);


        }
        if (variableCoin==1){
            groupGame.getChildren().remove(coin1View);
            groupGame.getChildren().add(coin2View);

        }
        if(variableCoin==0){
            groupGame.getChildren().add(coin1View);

        }
        variableCoin+=1;

    }
    private void runexplosion(){
        System.out.println(variable);


        if (variable==9){
            groupMenu.getChildren().remove(explosion6View);
            variable=0;
        }
        if (variable==8){
            groupMenu.getChildren().remove(explosion5View);
            groupMenu.getChildren().add(explosion6View);

        }
        if (variable==7){
            groupMenu.getChildren().remove(explosion4View);
            groupMenu.getChildren().add(explosion5View);

        }
        if (variable==6){
            groupMenu.getChildren().remove(explosion3View);
            groupMenu.getChildren().add(explosion4View);

        }
        if (variable==5){
            groupMenu.getChildren().remove(explosion2View);
            groupMenu.getChildren().add(explosion3View);

        }
        if (variable==4){
            groupMenu.getChildren().remove(explosion1View);
            groupMenu.getChildren().add(explosion2View);

        }
        if (variable==3){
            groupMenu.getChildren().remove(explosion0View);
            groupMenu.getChildren().add(explosion1View);

        }
        if(variable==0){
            groupMenu.getChildren().add(explosion0View);
        }
        variable+=1;

    }
    private void runbombe(){

            musicExplosion();
            group.getChildren().remove(bombeviewgif);
            isNextPositionAWall(groupGame, listWall2,bombeviewgif);
            isEnnemyInExplosion(groupGame,listEnnemy,bombeviewgif);

            isAllowedBomb =true;

    }
    private void runInvincible(){

        isInvincible =false;
        bomberman.setEffect(null);


    }

    private void isEnnemyInExplosion(Group group, List<Circle> listEnnemy, ImageView bombeviewgif){

        Circle ennemyTempToRemove = null;
        for (Circle ennemy : listEnnemy){
            if(ennemy.getCenterX() == bombeviewgif.getX() && ennemy.getCenterY() == bombeviewgif.getY()
            || bombeviewgif.getX()+50>= ennemy.getCenterX() && ennemy.getCenterX()>= bombeviewgif.getX()-50
                    && bombeviewgif.getY()+50>= ennemy.getCenterY() && ennemy.getCenterY()>=bombeviewgif.getY()-50
            ){
                ennemyTempToRemove = ennemy;
                group.getChildren().remove(ennemy);
                score +=5;
                System.out.println(listEnnemy.size());
            }
            if(ennemyTempToRemove!=null){
                listEnnemy.remove(ennemyTempToRemove);
            }
        }


    }




    private void run(){

        Random r = new Random();
        group.getChildren().remove(bombes);
        group.getChildren().remove(scores);
        group.getChildren().remove(hearts);

        isNextABomb(groupGame,listBombs,bomberman);
        isNextACoin(groupGame,listBagOfCoin,bomberman);
        isAllEnemiesDied(listEnnemy);




        scores = new Text();
        scores.setText(String.valueOf(score));
        scores.setX(960);
        scores.setY(350);
        scores.setFill(Color.WHITE);
        scores.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 70));
        group.getChildren().add(scores);

        hearts =new Text();
        hearts.setX(960);
        hearts.setY(250);
        hearts.setText(String.valueOf(heartint));
        hearts.setFill(Color.WHITE);
        hearts.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 70));
        group.getChildren().add(hearts);



        bombes = new Text();
        bombes.setText(String.valueOf(bombint));
        bombes.setX(960);
        bombes.setY(160);

        bombes.setFill(Color.WHITE);
        bombes.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 70));
        group.getChildren().add(bombes);

        Circle tempennemyToRemove =null;
        for(Circle ennemy:listEnnemy){
            boolean chasseOn = false;
            if((Math.abs(ennemy.getCenterX() - bomberman.getCenterX()) +
                    Math.abs(ennemy.getCenterY() - bomberman.getCenterY())) < 500){
                chasseOn = true;
            }

            if(chasseOn){
                double difX = ennemy.getCenterX() - bomberman.getCenterX();
                double difY = ennemy.getCenterY() - bomberman.getCenterY();
                if(Math.abs(difX) < Math.abs(difY)){
                    if(difY>0) ennemy.setCenterY(ennemy.getCenterY()-5);
                    else ennemy.setCenterY(ennemy.getCenterY()+5);
                }
                else {
                    if (difX>0) ennemy.setCenterX(ennemy.getCenterX()-5);
                    else ennemy.setCenterX(ennemy.getCenterX()+5);
                }
            }
            else{
                switch (r.nextInt(4)){
                    case 0:
                        if(ennemy.getCenterX() +5 < 850){
                            ennemy.setCenterX(ennemy.getCenterX()+5);
                        }
                        break;
                    case 1:
                        if (ennemy.getCenterX() - 5 > 0) {
                            ennemy.setCenterX(ennemy.getCenterX() - 5);
                        }
                        break;
                    case 2:
                        if (ennemy.getCenterY() - 5 > 0) {
                            ennemy.setCenterY(ennemy.getCenterY() - 5);
                        }
                        break;
                    case 3:
                        if (ennemy.getCenterY() + 5 < HEIGHT) {
                            ennemy.setCenterY(ennemy.getCenterY() + 5);
                        }
                        break;

                }
            }


            if(isInvincible==false){

                if(ennemy.getCenterX()==bomberman.getCenterX() & ennemy.getCenterY()==bomberman.getCenterY()){
                    heartint -=1;
                    bomberman.setEffect(gaussianBlur);
                    isInvincible =true;

                    invincible.play();
                    if(heartint==0){
                        gameOver();

                    }

                }

            }

        }

        //la on ou va mettre les ennemies et les déplacments


        timer = timer +1;
        iTimerfix +=1;
        Timer();
        System.out.println(seconde);


        //Affichage du timer

        if(timertextSeconde != null && timertextSecondeDizaine!=null && timertextMinute!=null && timertextMinuteDizaine!=null){
            group.getChildren().remove(timertextSeconde);
            group.getChildren().remove(timertextSecondeDizaine);
            group.getChildren().remove(timertextMinute);
            group.getChildren().remove(timertextMinuteDizaine);
            group.getChildren().remove(timerSeparator);

        }

        timertextSeconde.setText(String.valueOf(secondeUnite));
        timertextSecondeDizaine.setText(String.valueOf(secondeDizaine));

        timertextMinute.setText(String.valueOf(minuteUnite));
        timertextMinuteDizaine.setText(String.valueOf(minuteDizaine));


        timertextSeconde.setFont(Font.font(null,FontWeight.BOLD,36));
        timertextSeconde.setFill(Color.RED);
        group.getChildren().add(timertextSeconde);

        timertextSecondeDizaine.setFont(Font.font(null,FontWeight.BOLD,36));
        timertextSecondeDizaine.setFill(Color.RED);
        group.getChildren().add(timertextSecondeDizaine);

        timertextMinute.setFont(Font.font(null,FontWeight.BOLD,36));
        timertextMinute.setFill(Color.RED);
        group.getChildren().add(timertextMinute);

        timertextMinuteDizaine.setFont(Font.font(null,FontWeight.BOLD,36));
        timertextMinuteDizaine.setFill(Color.RED);
        group.getChildren().add(timertextMinuteDizaine);

        timerSeparator.setFont(Font.font(null,FontWeight.BOLD,36));
        timerSeparator.setFill(Color.RED);
        group.getChildren().add(timerSeparator);





    }
    private void Timer(){




        if(secondeDizaine==6){
            minuteUnite+=1;
            secondeDizaine=0;
        }
        if (minuteUnite == 9) {
            minuteDizaine+=1;
            minuteUnite=0;
        }
        if (timer ==4){
            seconde +=1;
            if(secondeUnite!=9){
                secondeUnite+=1;
            }

            timer=0;
        }
        if(iTimerfix ==40){
            secondeDizaine+=1;
            secondeUnite=0;
            iTimerfix =0;

        }

    }

    private void handleGameEvent() {

        sceneGame.setOnKeyPressed((KeyEvent event) -> {
            if (event.getText().isEmpty())
                return;
            char keyEntered = event.getText().toUpperCase().charAt(0);

            boolean isMouvOk = !gamePaused;

            boolean canFront = false;
            boolean canback = true;
            boolean canLeft = true;
            boolean canRight = true;
            if(isAllowedBomb=true){
                switch(keyEntered){
                    case 'B':

                        timerBomb =seconde;
                        if (bombint > 0) {
                            timerBomb =seconde;
                            bombe.play();
                            group.getChildren().add(bombeviewgif);
                            bombeviewgif.setY(bomberman.getCenterY() - bomberman.getRadius());
                            bombeviewgif.setX(bomberman.getCenterX() - bomberman.getRadius());
                            bombint -=1;

                        }
                        isAllowedBomb =false;
                        break;
                }
            }
            switch (keyEntered){
                case 'Z' :



                    if(canback = true){
                        bomberman.setFill(imagePatternBack);
                        System.out.println("entré dans canback1");
                        canFront =true;
                        canLeft =true;
                        canRight =true;

                        canback = false;



                    }









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
                        if (bomberman.getCenterY()- bomberman.getRadius() <= 12) {
                            bomberman.setCenterY(18 + bomberman.getRadius());
                        }


                        bomberman.setCenterY(bomberman.getCenterY() - 10);
                    }
                    break;
                case 'S' :
                    if(canFront =true){

                        bomberman.setFill(imagePatternFace);
                        canFront =false;
                        canback = true;
                        canLeft=true;
                        canRight =true;

                    }



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

                        bomberman.setCenterY(bomberman.getCenterY() + 10);
                    }
                    break;
                case 'Q' :

                    if(canLeft = true){

                        bomberman.setFill(imagePatternGauche);
                        canFront = true;
                        canback = true;
                        canLeft=false;
                        canRight =true;
                    }



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
                        if (bomberman.getCenterX() <= 22 + bomberman.getRadius()) {
                            bomberman.setCenterX(22 + bomberman.getRadius());
                        }

                        bomberman.setCenterX(bomberman.getCenterX() - 10);
                    }
                    break;
                case 'D' :
                    if(canRight = true){
                        bomberman.setFill(imagePatternDroite);

                        canFront = true;
                        canback = true;
                        canLeft=true;
                        canRight =false;
                    }



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

                        bomberman.setCenterX(bomberman.getCenterX() + 10);
                    }
                    break;
                case'P':
                    if(tl.getStatus() == Animation.Status.RUNNING){

                        primaryStage.setScene(scenePause);
                        tl.pause();
                        gamePaused = true;

                    }
                    else if(tl.getStatus() == Animation.Status.PAUSED){
                        primaryStage.setScene(sceneMenu);
                        tl.play();
                        gamePaused = false;
                    }

                    break;






            }
        });


    }



    public Group initializeGroupGame() throws FileNotFoundException {
        group = new Group();

        reflection = new Reflection();
        reflection.setFraction(1);
        gaussianBlur = new GaussianBlur();
        music();

        tl = new Timeline(new KeyFrame(Duration.millis(250), e -> run()));
        explosion = new Timeline(new KeyFrame(Duration.millis(180),e->runexplosion()));
        coin = new Timeline(new KeyFrame(Duration.millis(150),e->runCoin()));
        coin.setCycleCount(Timeline.INDEFINITE);
        explosion.setCycleCount(Timeline.INDEFINITE);

        tl.setCycleCount(Timeline.INDEFINITE);
        bombe = new Timeline(new KeyFrame(Duration.seconds(1),e->runbombe()));
        bombe.setCycleCount(1);

        invincible = new Timeline(new KeyFrame(Duration.seconds(4),e->runInvincible()));
        invincible.setCycleCount(1);


        timertextSeconde =new Text();
        timertextSecondeDizaine = new Text();

        inputDos = new FileInputStream("src/Images/Bomberman/bomberman_dos.png");
        imageBack = new Image(inputDos);
        imagePatternBack = new ImagePattern(imageBack);

        inputGauche = new FileInputStream("src/Images/Bomberman/bomberman_gauche.png");
        imageGauche = new Image(inputGauche);
        imagePatternGauche = new ImagePattern(imageGauche);

        inputDroite = new FileInputStream("src/Images/Bomberman/bomberman_droite.png");
        imageDroite = new Image(inputDroite);
        imagePatternDroite = new ImagePattern(imageDroite);


        timertextMinute =new Text();
        timertextMinuteDizaine = new Text();

        timertextSeconde.setX(1000);
        timertextSeconde.setY(45);

        timertextSecondeDizaine.setX(980);
        timertextSecondeDizaine.setY(45);

        timerSeparator = new Text(":");
        timerSeparator.setX(965);
        timerSeparator.setY(45);

        timertextMinute.setX(940);
        timertextMinute.setY(45);

        timertextMinuteDizaine.setX(920);
        timertextMinuteDizaine.setY(45);


        //Affichage de la bombe pour savoir combien de bombes il nous reste
        FileInputStream bombe = new FileInputStream("src/Images/Bombes/bombesprite.png");
        Image bombepng = new Image(bombe);
        ImageView bombeview = new ImageView(bombepng);
        bombeview.setY(70);
        bombeview.setX(850);
        bombeview.setFitWidth(120);
        bombeview.setFitHeight(120);
        bombeview.setPreserveRatio(true);

        group.getChildren().add(bombeview);

        //Affichage des coeurs pour savoir combien de vie il nous reste
        FileInputStream heart = new FileInputStream("src/Images/Heart/heart.png");
        Image heartpng = new Image(heart);
        ImageView heartview = new ImageView(heartpng);
        heartview.setY(200);
        heartview.setX(870);
        heartview.setFitHeight(70);
        heartview.setFitWidth(70);
        heartview.setPreserveRatio(true);
        group.getChildren().add(heartview);


        //Affichage de la bombe sur le terrain
        bombegif = new Image("https://www.informatiquegifs.com/explosion/gifs-explosion-8.gif",false);
        bombeviewgif = new ImageView(bombegif);
        bombeviewgif.setFitWidth(40);
        bombeviewgif.setFitHeight(40);
        bombeviewgif.setPreserveRatio(true);

        //Création du mur pas cassable
        listWall = new ArrayList<Rectangle>();
        FileInputStream inputWall_Unbreakable = new FileInputStream("src/Images/wall/wall_unbreakable.png");
        Image wall = new Image(inputWall_Unbreakable);

        listWall2 = new ArrayList<Rectangle>();
        FileInputStream inputWall_Breakable = new FileInputStream("src/Images/wall/wall_breakable.png");
        Image wall2 = new Image(inputWall_Breakable);

        listBombs = new ArrayList<ImageView>();
        FileInputStream inputBombs = new FileInputStream("src/Images/Bombes/bombe_obtenable.png");
        Image bombeImage = new Image(inputBombs);

        listBagOfCoin = new ArrayList<ImageView>();
        FileInputStream inputBagOfCoins = new FileInputStream("src/Images/Coins/bag_coin.png");
        Image coinImage = new Image(inputBagOfCoins);

        listEnnemy = new ArrayList<Circle>();

        FileInputStream ennemyInput1 = new FileInputStream("src/Images/ennemy/enemy1.png");
        Image ennemy1Skin = new Image(ennemyInput1);

        FileInputStream ennemyInput2 = new FileInputStream("src/Images/ennemy/enemy2.png");
        Image ennemy2Skin = new Image(ennemyInput2);

        FileInputStream ennemyInput3 = new FileInputStream("src/Images/ennemy/enemy3.png");
        Image ennemy3Skin = new Image(ennemyInput3);

        FileInputStream ennemyInput4 = new FileInputStream("src/Images/ennemy/enemy4.png");
        Image ennemy4Skin = new Image(ennemyInput4);

        FileInputStream ennemyInput5 = new FileInputStream("src/Images/ennemy/enemy5.png");
        Image ennemy5Skin = new Image(ennemyInput5);

        FileInputStream ennemyInput6 = new FileInputStream("src/Images/ennemy/enemy6.png");
        Image ennemy6Skin = new Image(ennemyInput6);

        Random randomSkin = new Random();
        Random randomMap = new Random();
        switch(randomMap.nextInt(2)){
            case 0:
                tiles =map1;
                break;
            case 1:
                tiles=map2;
                break;
        }





        for(int x=0; x< tiles.length;x++){
            for(int y=0; y< tiles[x].length;y++){
                if(tiles[x][y]==1){//Correspond au briques pas casable
                    Rectangle point = new Rectangle(y*40,x*40,40, 40);
                    point.setFill(new ImagePattern(wall));
                    listWall.add(point);

                }else if(tiles[x][y]==2){ // Correspond au briques cassable
                    Rectangle point = new Rectangle(y*40,x*40,40, 40);
                    point.setFill(new ImagePattern(wall2));
                    listWall2.add(point);

                }else if(tiles[x][y]==3){ // Correspond au Bombe que le joueur peut récupérer
                    ImageView bombesView = new ImageView(bombeImage);
                    bombesView.setX(y*40);
                    bombesView.setY(x*40);
                    bombesView.setFitWidth(40);
                    bombesView.setFitHeight(40);
                    bombesView.setPreserveRatio(true);
                    listBombs.add(bombesView);
                }else if(tiles[x][y]==4){// Correspond au sac de pièce que le joueur peut récupérer
                    ImageView coinView= new ImageView(coinImage);
                    coinView.setX(y*40);
                    coinView.setY(x*40);
                    coinView.setFitWidth(40);
                    coinView.setFitHeight(40);
                    coinView.setPreserveRatio(true);
                    listBagOfCoin.add(coinView);
                }else if(tiles[x][y]==5){//Correspond à la position des ennemies

                    Circle ennemy = new Circle(y*40,x*40,15);
                    switch (randomSkin.nextInt(6)){
                        case 0:
                            ennemy.setFill(new ImagePattern(ennemy1Skin));
                            break;
                        case 1:
                            ennemy.setFill(new ImagePattern(ennemy2Skin));
                            break;
                        case 2:
                            ennemy.setFill(new ImagePattern(ennemy3Skin));
                            break;
                        case 3:
                            ennemy.setFill(new ImagePattern(ennemy4Skin));
                            break;
                        case 4:
                            ennemy.setFill(new ImagePattern(ennemy5Skin));
                            break;
                        case 5:
                            ennemy.setFill(new ImagePattern(ennemy6Skin));
                            break;

                    }

                    listEnnemy.add(ennemy);

                }else if(tiles[x][y]==6) {//Correspond à la position du joueur
                    bomberman = new Circle(y*40,x*40,15);
                    inputFace = new FileInputStream("src/Images/Bomberman/bomberman_face.png");
                    imageFace = new Image(inputFace);
                    imagePatternFace = new ImagePattern(imageFace);
                    bomberman.setFill(imagePatternFace);


                    group.getChildren().add(bomberman);
                }
            }
        }
        for (Rectangle p : listWall) {

            group.getChildren().add(p);
        }
        for (Rectangle p : listWall2) {

            group.getChildren().add(p);
        }
        for (ImageView p : listBombs){
            group.getChildren().add(p);
        }
        for (ImageView p : listBagOfCoin){
            group.getChildren().add(p);
        }
        for (Circle p :listEnnemy){
            group.getChildren().add(p);
        }



        return group;
    }

    private void isNextPositionAWall(Group group,  List<Rectangle> listWall2, ImageView bombeviewgif){

        Rectangle wallTempToRemove2 =null;


            for(Rectangle wall : listWall2){

                if(wall.getX() == bombeviewgif.getX() && wall.getY() == bombeviewgif.getY()
                        || bombeviewgif.getX()+50>=wall.getX() && wall.getX()>= bombeviewgif.getX()-50
                        && bombeviewgif.getY()+50>=wall.getY() && wall.getY()>= bombeviewgif.getY()-50
                ){
                    wallTempToRemove2 = wall;
                    group.getChildren().remove(wall);
                    score +=3;
                }
                if(wallTempToRemove2!=null){
                    listWall2.remove(wallTempToRemove2);
                }



            }





    }



    public static void main(String[] args) {

        launch();
    }
}