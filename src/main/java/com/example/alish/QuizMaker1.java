package com.example.alish;

import com.example.alish.Quiz;

import java.io.FileNotFoundException;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

import static com.example.alish.Quiz.loadFromFile;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Collections;

public class QuizMaker1 extends Application {

    private Stage window;
    public int min=0;
    public int sec =0;
    private final double W=900, H=600;
    private ArrayList<Question> questions = new ArrayList<>();
    public int[] nomerRadio = new int[100];
    public String[] otvetyScan = new String[15];
    public String vvod1="zzz";
    private String[] textfilda = new String[100];
    public Label lblTimer = new Label();
    Media media = new Media(new File("src/main/java/com/example/demo7/kahoot_music.wav").toURI().toString());
    String vremya;
    MediaPlayer mediaPlayer = new MediaPlayer(media);
    Timeline timeline = new Timeline();

    public void sss(){
        for(int i=0;i<15;i++){
            otvetyScan[i]= "you don't answer this question";
        }
    }


    public BorderPane SongyQuestion(double surak,double durys) throws FileNotFoundException {

        BorderPane borderPane1 = new BorderPane();
        StackPane stackPane = new StackPane();
        Text t1 = new Text("Your Result:");
        t1.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 25));

        stackPane.getChildren().add(t1);
        stackPane.setAlignment(Pos.CENTER);
        borderPane1.setTop(stackPane);

        Text pr = new Text(Double.toString(durys/surak*100)+"%");
        pr.setFont(Font.font("Times New Roman", FontWeight.MEDIUM, FontPosture.ITALIC, 20));


        Text jay = new Text("Number of correct answers: "+(int)durys+"/"+(int)surak);
        Text jay1 = new Text("Finished in: "+vremya);
        Button b1 = new Button("Show Answer");
        Button b2 = new Button("Close program");

        b1.setTextFill(Color.WHITE);
        b1.setMinWidth(450);
        b1.setMinHeight(55);

        b1.setStyle("-fx-background-color:blue");


        b1.setOnMousePressed(e->{
            int ind=0;
            try{
                window.setScene(new Scene(OtvetyQuestion(ind), W, H));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        b2.setOnMousePressed(e->{
            System.exit(0);
        });

        b2.setTextFill(Color.WHITE);
        b2.setMinWidth(450);
        b2.setMinHeight(55);

        b2.setStyle("-fx-background-color:red");

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setMinWidth(450);

        Image  image = new Image(new FileInputStream("src/main/java/com/example/demo7/konec.jpg"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(500);
        imageView.setFitHeight(300);

        vBox.getChildren().addAll(pr,jay,jay1,b1,b2,imageView);
        vBox.setSpacing(10);

        borderPane1.setBottom(vBox);

        borderPane1.setMargin(vBox, new Insets(0, 10, 10, 10));

        return borderPane1;

    }

    public BorderPane OtvetyQuestion(int ind) throws FileNotFoundException {

        BorderPane borderPane = new BorderPane();
        mediaPlayer.play();

        if (questions.get(ind).getDescription().contains("{blank}")) {

            Image  image = new Image(new FileInputStream("src/main/java/com/example/demo7/fillin.jpg"));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(500);
            imageView.setFitHeight(300);

            HBox hbox1 = new HBox();

            Text textfield1 = new Text();
            textfield1.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 18));

            textfield1.setText(otvetyScan[ind]);


            Text t1 = new Text("Your answer:");
            t1.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 18));
            t1.setX(405);
            t1.setY(420);

            hbox1.getChildren().addAll(t1,textfield1);

            HBox hBox2= new HBox();

            Text textfield2 = new Text();
            textfield2.setText(questions.get(ind).getAnswer());
            textfield2.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 18));


            Text t2 = new Text("Correct answer:");
            t2.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 18));
            t2.setX(405);
            t2.setY(450);

            hBox2.getChildren().addAll(t2,textfield2);

            VBox vBox = new VBox();
            vBox.getChildren().addAll(hbox1,hBox2);


            borderPane.setBottom(vBox);
            borderPane.setTop(surak(ind));
            borderPane.setCenter(imageView);

            borderPane.setMargin(vBox, new Insets(0, 235, 135, 235));

        } else {
            Test test = new Test();

            Button r1 = new Button(questions.get(ind).getOptions(0));
            kahootButton(r1);
            r1.setStyle("-fx-background-color:grey");

            Button r2 = new Button(questions.get(ind).getOptions(1));
            kahootButton(r2);
            r2.setStyle("-fx-background-color:grey");

            Button r3 = new Button(questions.get(ind).getOptions(2));
            kahootButton(r3);
            r3.setStyle("-fx-background-color:grey");

            Button r4 = new Button(questions.get(ind).getOptions(3));
            kahootButton(r4);
            r4.setStyle("-fx-background-color:grey");



            VBox vBox1 = new VBox(3);
            vBox1.getChildren().addAll(r1, r2);


            VBox vBox2 = new VBox(3);
            vBox2.getChildren().addAll(r3, r4);

            HBox hBox = new HBox(3);
            hBox.getChildren().addAll(vBox1, vBox2);

            if(nomerRadio[ind]!=0){
                if(nomerRadio[ind]==1) {
                    r1.setStyle("-fx-background-color:red");
                }else  if(nomerRadio[ind]==2) {
                    r2.setStyle("-fx-background-color:red");
                }else  if(nomerRadio[ind]==3) {
                    r3.setStyle("-fx-background-color:red");
                }else  if(nomerRadio[ind]==4) {
                    r4.setStyle("-fx-background-color:red");
                }
            }
            if(nomerRadio[ind]!=0){
                if(questions.get(ind).getOptions(0)==questions.get(ind).getAnswer()) {
                    r1.setStyle("-fx-background-color:green");
                }else  if(questions.get(ind).getOptions(1)==questions.get(ind).getAnswer()) {
                    r2.setStyle("-fx-background-color:green");
                }else  if(questions.get(ind).getOptions(2)==questions.get(ind).getAnswer()) {
                    r3.setStyle("-fx-background-color:green");
                }else  if(questions.get(ind).getOptions(3)==questions.get(ind).getAnswer()) {
                    r4.setStyle("-fx-background-color:green");
                }
            }


            Image  image = new Image(new FileInputStream("src/main/java/com/example/demo7/test.jpg"));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(500);
            imageView.setFitHeight(300);

            hBox.setAlignment(Pos.CENTER);


            borderPane.setTop(surak(ind));
            borderPane.setBottom(hBox);
            borderPane.setCenter(imageView);


            borderPane.setMargin(hBox, new Insets(10, 0, 0, 0));
        }


        Button btnNext = new Button(">>");
        Button btnPrev = new Button("<<");

        btnNext.setMinWidth(100);
        btnNext.setMinHeight(50);

        btnPrev.setMinWidth(100);
        btnPrev.setMinHeight(50);


        borderPane.setRight(new StackPane(btnNext));
        borderPane.setLeft(new StackPane(btnPrev));

        if(ind == 0 ){
            btnPrev.setVisible(false);
        }
        if(ind == questions.size() -1){
            btnNext.setVisible(false);
            Button btnKonec = new Button("x");

            btnKonec.setMinWidth(100);
            btnKonec.setMinHeight(50);
            borderPane.setRight(new StackPane(btnKonec));

            btnKonec.setOnAction(e ->{
                System.exit(0);
            });

        }

        btnNext.setOnAction(e -> {
            try{
                window.setScene(new Scene(OtvetyQuestion(ind+1), W, H));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }

        });

        btnPrev.setOnAction(e -> {

            try {
                window.setScene(new Scene(OtvetyQuestion(ind-1),W,H));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        return borderPane;
    }
    public BorderPane CurrentQuestion(int ind) throws FileNotFoundException {

        BorderPane borderPane = new BorderPane();
        mediaPlayer.play();


        if (questions.get(ind).getDescription().contains("{blank}")) {

            Image  image = new Image(new FileInputStream("src/main/java/com/example/demo7/fillin.jpg"));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(500);
            imageView.setFitHeight(300);

            TextField textfield = new TextField();
            textfield.setMinHeight(35);
            textfield.setMinWidth(100);

            if(otvetyScan[ind]!=("you don't answer this question")) {
                textfield.setText(otvetyScan[ind]);
            }
            textfield.setOnKeyTyped(e->{
                textfilda[ind]=textfield.getText();
            });


            Text t1 = new Text("Type your answer here:");
            t1.setX(405);
            t1.setY(420);


            borderPane.setBottom(textfield);
            borderPane.setTop(surak(ind));
            borderPane.getChildren().add(t1);
            borderPane.setCenter(imageView);

            borderPane.setMargin(textfield, new Insets(0, 235, 135, 235));

        } else {
            Test test = new Test();

            RadioButton r1 = new RadioButton(questions.get(ind).getOptions(0));
            kahootRadioButton(r1);
            r1.setStyle("-fx-background-color:red");

            RadioButton r2 = new RadioButton(questions.get(ind).getOptions(1));
            kahootRadioButton(r2);
            r2.setStyle("-fx-background-color:blue");

            RadioButton r3 = new RadioButton(questions.get(ind).getOptions(2));
            kahootRadioButton(r3);
            r3.setStyle("-fx-background-color:orange");

            RadioButton r4 = new RadioButton(questions.get(ind).getOptions(3));
            kahootRadioButton(r4);
            r4.setStyle("-fx-background-color:green");

            ToggleGroup group = new ToggleGroup();
            r1.setToggleGroup(group);
            r2.setToggleGroup(group);
            r3.setToggleGroup(group);
            r4.setToggleGroup(group);


            VBox vBox1 = new VBox(3);
            vBox1.getChildren().addAll(r1, r2);


            VBox vBox2 = new VBox(3);
            vBox2.getChildren().addAll(r3, r4);

            HBox hBox = new HBox(3);
            hBox.getChildren().addAll(vBox1, vBox2);

            if(nomerRadio[ind]!=0){
                if(nomerRadio[ind]==1) {
                    r1.setSelected(true);
                }else  if(nomerRadio[ind]==2) {
                    r2.setSelected(true);
                }else  if(nomerRadio[ind]==3) {
                    r3.setSelected(true);
                }else  if(nomerRadio[ind]==4) {
                    r4.setSelected(true);
                }
            }


            r1.setOnAction(e -> {
                if (r1.isSelected()) {
                    otvetyScan[ind]=r1.getText();
                    nomerRadio[ind]=1;
                }
            });
            r2.setOnAction(e -> {
                if (r2.isSelected()) {
                    otvetyScan[ind]=r2.getText();
                    nomerRadio[ind]=2;
                }
            });
            r3.setOnAction(e -> {
                if (r3.isSelected()) {
                    otvetyScan[ind]=r3.getText();
                    nomerRadio[ind]=3;
                }
            });
            r4.setOnAction(e -> {
                if (r4.isSelected()) {
                    otvetyScan[ind]=r4.getText();
                    nomerRadio[ind]=4;
                }
            });


            Image  image = new Image(new FileInputStream("src/main/java/com/example/demo7/kahootgif.gif"));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(500);
            imageView.setFitHeight(300);

            hBox.setAlignment(Pos.CENTER);


            borderPane.setTop(surak(ind));
            borderPane.setBottom(hBox);
            borderPane.setCenter(imageView);

            borderPane.setMargin(hBox, new Insets(10, 0, 0, 0));
        }


        Button btnNext = new Button(">>");
        Button btnPrev = new Button("<<");

        btnNext.setMinWidth(100);
        btnNext.setMinHeight(50);

        btnPrev.setMinWidth(100);
        btnPrev.setMinHeight(50);


        borderPane.setRight(new StackPane(btnNext));
        borderPane.setLeft(new StackPane(btnPrev));

        if(ind == 0 ){
            btnPrev.setVisible(false);
        }
        if(ind == questions.size() -1){

            btnNext.setVisible(false);
            Button btnKonec = new Button("x");

            btnKonec.setMinWidth(100);
            btnKonec.setMinHeight(50);
            borderPane.setRight(new StackPane(btnKonec));

            btnKonec.setOnAction(e -> {

                if(questions.get(ind).getDescription().contains("{blank}")){
                    otvetyScan[ind]=textfilda[ind];
                }
                int durys=0;
                int surak=questions.size();

                vremya = lblTimer.getText();
                for(int i=0;i<questions.size();i++){
                    System.out.println(otvetyScan[i]);
                    System.out.println(questions.get(i).getAnswer());
                }
                for(int i=0;i<questions.size();i++){
                    if(questions.get(ind).getDescription().contains("{blank}")) {
                        if (otvetyScan[i] == null) {
                            otvetyScan[i] = "you don't answer this question";
                        }
                    }
                    if(otvetyScan[i].equals(questions.get(i).getAnswer())){
                        durys++;
                    }

                }
                try {
                    window.setScene(new Scene(SongyQuestion(surak,durys), W, H));
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            });

        }

        btnNext.setOnAction(e -> {
            try {
                if(questions.get(ind).getDescription().contains("{blank}")){

                    otvetyScan[ind]=textfilda[ind];
                }
                window.setScene(new Scene(CurrentQuestion(ind+1), W, H));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }

        });

        btnPrev.setOnAction(e -> {
            if(questions.get(ind).getDescription().contains("{blank}")){

                otvetyScan[ind]=textfilda[ind];
            }
            try {
                window.setScene(new Scene(CurrentQuestion(ind-1),W,H));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        return borderPane;
    }




    public StackPane chooseFile(){

        Quiz quiz = new Quiz();

        Image  image = null;
        try {
            image = new Image(new FileInputStream("src/main/java/com/example/demo7/background.jpg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(W);
        imageView.setFitHeight(H);


        StackPane stackPane = new StackPane();
        Button btnChoose = new Button("Choose a file");
        stackPane.getChildren().addAll(imageView,btnChoose);


        btnChoose.setOnAction(e-> {
            FileChooser fc = new FileChooser();
            fc.setInitialDirectory(new File("src"));
            File files = fc.showOpenDialog(window);

            try {
                questions = (ArrayList<Question>) quiz.loadFromFile(files.getPath());
                sss();
                Collections.shuffle(questions);
                timeline.play();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }



            try {
                window.setScene(new Scene(CurrentQuestion(0),W,H));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });


       /* btnChoose.setOnAction(e-> {

            try {
                window.setScene(new Scene(CurrentQuestion(0),W,H));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });*/

        return stackPane;
    }
    @Override
    public void start(Stage stage) throws IOException {

        Quiz quiz1 = new Quiz();



        lblTimer.setText(timeFormat(sec));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                        event ->
                                lblTimer.setText(timeFormat(++sec))));


        window = stage;
        stage.setScene(new Scene(chooseFile(),W,H));
        stage.setTitle("Project 2");
        stage.show();
    }
    private String timeFormat(int sec){
        min=sec/60;
        sec = sec%60;
        String strMin = min+"";
        if(min<10) strMin = "0"+ strMin;
        String strSec = sec+"";
        if(sec< 10) strSec = "0"+ strSec;
        return strMin + ":"+strSec;
    }

    public VBox surak(int ind){
        StackPane nomer = new StackPane();
        Rectangle rectangle = new Rectangle(40,40);
        rectangle.setArcWidth(20);
        rectangle.setArcHeight(20);
        rectangle.setFill(Color.MEDIUMPURPLE);

        Text text = new Text(Integer.toString(ind+1));
        text.setFont(Font.font("Montserrat", FontWeight.BOLD, FontPosture.REGULAR, 20));
        text.setFill(Color.WHITE);

        nomer.getChildren().addAll(rectangle,text);


        VBox vBox = new VBox();

        HBox hBox = new HBox();
        Label lbl = new Label(questions.get(ind).getDescription().replace("{blank}","______"));
        lbl.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 22));
        lbl.setWrapText(true);

        hBox.getChildren().addAll(nomer,lbl);
        hBox.setSpacing(10);
        hBox.setMinWidth(900);
        hBox.setAlignment(Pos.CENTER);

        lblTimer.setMinWidth(900);
        lblTimer.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 18));
        lblTimer.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(hBox,lblTimer);
        vBox.setSpacing(15);



        return vBox;
    }

    public static void kahootRadioButton(RadioButton r1){
        r1.setTextFill(Color.WHITE);
        r1.setMinWidth(450);
        r1.setMinHeight(75);
        r1.setFont(Font.font("Montserrat", FontWeight.BOLD, FontPosture.REGULAR, 15));
    }

    public static void kahootButton(Button r1){
        r1.setTextFill(Color.WHITE);
        r1.setMinWidth(450);
        r1.setMinHeight(75);
        r1.setFont(Font.font("Montserrat", FontWeight.BOLD, FontPosture.REGULAR, 15));
    }


    public static void main(String[] args) {
        launch();
    }
}
/*
class QuizMaker {
    public static void main(String[] args) throws FileNotFoundException,ArrayIndexOutOfBoundsException {
        Quiz quiz = new Quiz();
        if(args.length!=1){
            System.out.println("Error");
            System.exit(0);
        }
        try {
            if(args[0].equals("QuiztextFIle")) {
                quiz.loadFromFile(args[0]);
            }else{
                throw new InvalidQuizFormatException();
            }
        }catch(InvalidQuizFormatException e){
            System.out.println("InvalidQuizFormatExceptionWrong");
            e.printStackTrace();
            System.exit(0);
        }
        quiz.start();
    }
}*/
