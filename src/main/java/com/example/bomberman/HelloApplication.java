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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class HelloApplication extends Application {

   static final int WIDTH = 1080;
    static final int HEIGHT = 800;
    Scene sceneMenu;
    Scene sceneChoosePlayer;
    Stage primaryStage;
    Group groupGame;
    List<Rectangle> listPoint;
    List<Rectangle> listWall2;
    List<Rectangle> listWall3;
    Scene sceneGame;
    List<Circle>listEnnemy;
    Circle bomberman;
    static int bombint = 4;
    Text bombes;
    Group group;
    Integer timerBomb = 0;
    Integer timer = 0;

    Integer secondeUnite = 0;
    Integer secondeDizaine =0;
    Integer minuteUnite =0;
    Integer minuteDizaine=0;
    Integer seconde=0;
    Integer minute=0;
    Text timerSeparator;

    ImageView bombeviewgif;
    Timeline tl;
    Image bombegif;
    Boolean isAllowedBomb =true;
    Integer truc = 0;
    String couleur = "";
    FileInputStream input;
    Text timertextSeconde;
    Text timertextSecondeDizaine;
    Text timertextMinute;
    Text timertextMinuteDizaine;




    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        primaryStage = stage;
        Scene scene = new Scene(fxmlLoader.load(), 850, HEIGHT) ;
        Button buttonJouer = new Button("Jouer");
        Button buttonRegle = new Button("Règle");
        Button buttonQuitter = new Button("Quitter");
        Group menuPrincipal = new Group();


        tl = new Timeline(new KeyFrame(Duration.millis(250), e -> run()));
        tl.setCycleCount(Timeline.INDEFINITE);


        timertextSeconde =new Text();
        timertextSecondeDizaine = new Text();

        timertextMinute =new Text();
        timertextMinuteDizaine = new Text();

        timertextSeconde.setX(1000);
        timertextSeconde.setY(400);

        timertextSecondeDizaine.setX(980);
        timertextSecondeDizaine.setY(400);

         timerSeparator = new Text(":");
         timerSeparator.setX(965);
         timerSeparator.setY(400);

        timertextMinute.setX(940);
        timertextMinute.setY(400);

        timertextMinuteDizaine.setX(920);
        timertextMinuteDizaine.setY(400);






        stage.setTitle("Bomberman!");
        menuPrincipal.setLayoutX(850/2);
        menuPrincipal.setLayoutY(HEIGHT/2);
        VBox vbox = new VBox(buttonJouer, buttonRegle, buttonQuitter);
        menuPrincipal.getChildren().add(vbox);
        sceneMenu = new Scene(menuPrincipal, WIDTH, HEIGHT, Color.GRAY);

        stage.setScene(sceneMenu);
        stage.show();
        groupGame = initializeGroupGame();
        sceneGame = new Scene(groupGame, WIDTH, HEIGHT, Color.GRAY);
        //Ici


        FileInputStream inputBlanc  = new FileInputStream("src/Images/Bomberman/bombermanBlanc.png");
        Image skinBlanc = new Image(inputBlanc,340,340,true,false);
        ImageView skinBlancview = new ImageView(skinBlanc);

        FileInputStream inputBleu  = new FileInputStream("src/Images/Bomberman/bombermanBleu.png");
        Image skinBleu = new Image(inputBleu,340,340,true,false);
        ImageView skinBleuview = new ImageView(skinBleu);

        FileInputStream inputJaune  = new FileInputStream("src/Images/Bomberman/bombermanJaune.png");
        Image skinJaune = new Image(inputJaune,340,340,true,false);
        ImageView skinJauneview = new ImageView(skinJaune);

        FileInputStream inputNoir  = new FileInputStream("src/Images/Bomberman/bombermanNoir.png");
        Image skinNoir = new Image(inputNoir,340,340,true,false);
        ImageView skinNoirview = new ImageView(skinNoir);

        FileInputStream inputRouge  = new FileInputStream("src/Images/Bomberman/BombermanRouge.png");
        Image skinRouge = new Image(inputRouge,340,340,true,false);
        ImageView skinRougeview = new ImageView(skinRouge);

        FileInputStream inputVert  = new FileInputStream("src/Images/Bomberman/bombermanVert.png");
        Image skinVert = new Image(inputVert,340,340,true,false);
        ImageView skinVertview = new ImageView(skinVert);

        HBox vboxChoose = new HBox(skinBlancview, skinBleuview, skinJauneview, skinNoirview, skinRougeview,skinVertview);
        vboxChoose.setPrefSize(10,10);
        Group groupChoose = new Group();
        groupChoose.getChildren().add(vboxChoose);

        sceneChoosePlayer = new Scene(groupChoose,WIDTH,HEIGHT,Color.GRAY);

        skinBlancview.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                System.out.println("Perso blanc choisie");

                couleur = "blanc";
                primaryStage.setScene(sceneGame);
                tl.play();
                event.consume();
            }
        });
        skinBleuview.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                System.out.println("Perso bleu choisie");

                couleur = "bleu";
                primaryStage.setScene(sceneGame);
                tl.play();
                event.consume();
            }
        });
        skinJauneview.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                System.out.println("Perso jaune choisie");

                couleur = "jaune";
                primaryStage.setScene(sceneGame);
                tl.play();
                event.consume();
            }
        });
        skinNoirview.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                System.out.println("Perso noir choisie");

                couleur = "noir";
                primaryStage.setScene(sceneGame);
                tl.play();
                event.consume();
            }
        });
        skinRougeview.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                System.out.println("Perso rouge choisie");

                couleur = "rouge";
                primaryStage.setScene(sceneGame);
                tl.play();
                event.consume();
            }
        });
        skinVertview.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                System.out.println("Perso Vert choisie");

                couleur = "vert";
                primaryStage.setScene(sceneGame);
                tl.play();
                event.consume();
            }
        });



        buttonJouer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //primaryStage.setScene(sceneGame);
                //tl.play();
                primaryStage.setScene(sceneChoosePlayer);



            }
        });
        handleGameEvent();

    }

    private void run(){



        Random r = new Random();
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
                    if(difY>0) ennemy.setCenterY(ennemy.getCenterY()-12);
                    else ennemy.setCenterY(ennemy.getCenterY()+12);
                }
                else {
                    if (difX>0) ennemy.setCenterX(ennemy.getCenterX()-12);
                    else ennemy.setCenterX(ennemy.getCenterX()+12);
                }
            }
            else{
                switch (r.nextInt(4)){
                    case 0:
                        if(ennemy.getCenterX() +12 < 850){
                            ennemy.setCenterX(ennemy.getCenterX()+12);
                        }
                        break;
                    case 1:
                        if (ennemy.getCenterX() - 12 > 0) {
                            ennemy.setCenterX(ennemy.getCenterX() - 12);
                        }
                        break;
                    case 2:
                        if (ennemy.getCenterY() - 12 > 0) {
                            ennemy.setCenterY(ennemy.getCenterY() - 12);
                        }
                        break;
                    case 3:
                        if (ennemy.getCenterY() + 12 < HEIGHT) {
                            ennemy.setCenterY(ennemy.getCenterY() + 12);
                        }
                        break;

                }
            }
            if(ennemy.getCenterX()==bomberman.getCenterX() & ennemy.getCenterY()==bomberman.getCenterY()){
                tl.pause();
                System.out.println("Game Over");
            }
        }

        //la on ou va mettre les ennemies et les déplacments

        timerBomb = timerBomb +1;
        timer = timer +1;
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

        if(truc +11==timerBomb & truc >11){

            System.out.println("Boum");
            isNextPositionAWall(groupGame, listPoint, listWall2, listWall3,bombeviewgif);
            group.getChildren().remove(bombeviewgif);



            isAllowedBomb =true;



        }


    }
    private void Timer(){
        if(secondeUnite==9){
            secondeDizaine+=1;
            secondeUnite=0;
        }
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

        if (seconde==60){
            minute+=1;
            seconde=0;
        }

    }

    private void handleGameEvent() {

        sceneGame.setOnKeyPressed((KeyEvent event) -> {
            if (event.getText().isEmpty())
                return;
            char keyEntered = event.getText().toUpperCase().charAt(0);

            boolean isMouvOk = true;
            if(isAllowedBomb=true){
                switch(keyEntered){
                    case 'B':

                        truc = timerBomb;
                        if (bombint > 0) {
                            group.getChildren().add(bombeviewgif);
                            bombeviewgif.setY(bomberman.getCenterY() - bomberman.getRadius());
                            bombeviewgif.setX(bomberman.getCenterX() - bomberman.getRadius());


                        }
                        isAllowedBomb =false;


                        //new Timeline(new KeyFrame(
                        //Duration.seconds(3),
                        //ae->{


                        //isNextPositionAWall(groupGame, listPoint,listPoint2,listPoint3,bombeviewgif);
                        //group.getChildren().remove(bombeviewgif);
                        //System.out.println("hey");


                        //})).play();





                        break;

                }
            }
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
                        if (bomberman.getCenterY()- bomberman.getRadius() <= 12) {
                            bomberman.setCenterY(18 + bomberman.getRadius());
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
                        if (bomberman.getCenterX() <= 22 + bomberman.getRadius()) {
                            bomberman.setCenterX(22 + bomberman.getRadius());
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






            }
        });


    }


    private Group initializeGroupGame() throws FileNotFoundException {
        group = new Group();


        //Affichage de la bombe pour savoir combien de bombes il nous reste
        Image bombepng = new Image("https://cdn.discordapp.com/attachments/951092669969485864/953590274670616636/Wallpaperkiss_2375844.jpg", false);
        ImageView bombeview = new ImageView(bombepng);
        bombeview.setY(40);
        bombeview.setX(870);
        bombeview.setFitWidth(70);
        bombeview.setFitHeight(70);
        bombeview.setPreserveRatio(true);
        group.getChildren().add(bombeview);
        //Affichage du nombre de bombe restant
        bombes = new Text(900,45, String.valueOf(bombint));
        bombes.setFill(Color.WHITE);
        bombes.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
        group.getChildren().add(bombes);





        //Affichage de la bombe sur le terrain
        bombegif = new Image("https://www.informatiquegifs.com/explosion/gifs-explosion-8.gif",false);
        bombeviewgif = new ImageView(bombegif);
        bombeviewgif.setFitWidth(40);
        bombeviewgif.setFitHeight(40);
        bombeviewgif.setPreserveRatio(true);

        //Affichage des ennemies
        //Circle ennemy1 = new Circle(94,90,15,Color.RED);
        Circle ennemy2 = new Circle(700,700,15,Color.RED);
        Circle ennemy3 = new Circle(700,700,15,Color.RED);
        Circle ennemy4 = new Circle(700,700,15,Color.RED);
        listEnnemy = new ArrayList<Circle>();
        //listEnnemy.add(ennemy1);
        listEnnemy.add(ennemy2);
        listEnnemy.add(ennemy3);
        listEnnemy.add(ennemy4);

        //group.getChildren().add(ennemy1);
        group.getChildren().add(ennemy2);
        group.getChildren().add(ennemy3);

        group.getChildren().add(ennemy4);

        //Il faut que la position x et y des ennemies soient un multiple de 70+12 pour x et 30 +12 pour y exemple(82,42) ou (94,54


            input  = new FileInputStream("src/Images/Bomberman/bombermanBlanc.png");




        switch (couleur){
        case "blanc":

            input  = new FileInputStream("src/Images/Bomberman/bombermanBlanc.png");


        break;
        case "bleu":
            input  = new FileInputStream("src/Images/Bomberman/bombermanBleu.png");


        break;
        case "jaune":
            input  = new FileInputStream("src/Images/Bomberman/bombermanJaune.png");

        break;
        case "noir":
            input  = new FileInputStream("src/Images/Bomberman/bombermanNoir.png");

        break;
        case "rouge":
            input  = new FileInputStream("src/Images/Bomberman/bombermanRouge.png");

        break;
        case "vert":
            input  = new FileInputStream("src/Images/Bomberman/bombermanVert.png");

        break;
        }



        //Afficage Bomberman
        bomberman = new Circle(70,30,12);

        Image bombermanSkin = new Image(input);
        bomberman.setFill(new ImagePattern(bombermanSkin));
        group.getChildren().add(bomberman);





        //Création du mur pas cassable
        listPoint = new ArrayList<Rectangle>();
        Image wall = new Image("http://images.shoutwiki.com/bomberpedia/3/38/SoftBlock.png", false);
        for(int i = 90; i < HEIGHT; i = i + 80){
            for(int j = 10; j < 850; j = j + 80){
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
        listWall2 = new ArrayList<Rectangle>();
        Image wall2 = new Image("http://images.shoutwiki.com/bomberpedia/thumb/a/af/HardBlock.png/200px-HardBlock.png", false);
        for(int i = 50; i < HEIGHT; i = i + 80){
            for(int j = 10; j < 850; j = j + 80){
                boolean createOk = true;


                if(createOk){
                    Rectangle point = new Rectangle(j,i,40, 40);
                    point.setFill(new ImagePattern(wall2));
                    listWall2.add(point);


                }
            }

        }for (Rectangle p : listWall2) {

            group.getChildren().add(p);
        }

        //Création Mur cassable Width
        listWall3 = new ArrayList<Rectangle>();
        for(int i = 50; i < HEIGHT; i = i + 80){
            for(int j = 50; j < 850; j = j + 80){
                boolean createOk = true;


                if(createOk){
                    Rectangle point = new Rectangle(j,i,40, 40);
                    point.setFill(new ImagePattern(wall2));
                    listWall3.add(point);

                }
            }

        }for (Rectangle p : listWall3) {

            group.getChildren().add(p);
        }






        return group;
    }

    private void isNextPositionAWall(Group group, List<Rectangle> listPoint, List<Rectangle> listWall2, List<Rectangle> listPoint3, ImageView bombeviewgif){
        Rectangle wallTempToRemove =null;
        for(Rectangle wall : listWall2){
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
                listWall2.remove(wallTempToRemove);
            }



        }

    }



    public static void main(String[] args) {
        launch();
    }
}