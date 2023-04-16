/*package com.example.alish;

import javafx.application.Application;
import javafx.stage.Stage;

package com.example.demo7;

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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import static com.example.alish.Quiz.loadFromFile;
//import static com.example.alish.Quiz.questions;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Collections;

public class isxodnik extends Application {


    private Stage window;
    public int min=0;
    int ind=0;
    public int sec =15;
    private final double W=1250, H=750;
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
    public static  ArrayList<String> names = new ArrayList<>();
    public static  ArrayList<Integer> points = new ArrayList<>();
    Socket s=null;

    public void sss(){
        for(int i=0;i<15;i++){
            otvetyScan[i]= "you don't answer this question";
        }
    }


    public BorderPane CurrentQuestion(int ind,Socket s) throws FileNotFoundException {

        BorderPane borderPane = new BorderPane();
        mediaPlayer.play();
        System.out.println("Hello");
        String line=null;
        BufferedReader chitat = null;
        PrintWriter otpravlyat=null;
        Socket ss=s;
        int durys=0;
        int question =5;


        try {
            chitat = new BufferedReader(new InputStreamReader(s.getInputStream()));
            otpravlyat = new PrintWriter(s.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("IO error in server thread");
        }
        String name;
        try {
            Quiz quiz = new Quiz();
            //     questions = (ArrayList<Question>) quiz.loadFromFile("src/main/java/com/example/demo7/QuiztextFIle");
            //prinimaem name
            name = chitat.readLine();

            server1.names.add(name);
            otpravlyat.println(name);
            otpravlyat.flush();

            int i = 0;

            for (int z = 0; z < 5; z++) {
                Button button = new Button("asdasdas");
                borderPane.getChildren().add(button);
                String surak = questions.get(z).getDescription();
                otpravlyat.println(surak);
                System.out.println("vse norm");
                line = chitat.readLine();
                if (line.equals(questions.get(z).getAnswer())) {
                    System.out.println("true");
                    durys++;
                } else {
                    System.out.println("false");
                }
                i++;
            }
            server1.points.add(durys);
            line = durys + "/" + 5 + "";
            otpravlyat.println(line);
            otpravlyat.flush();

        } catch (IOException e) {

            System.out.println("IO Error/ Client " + line + " terminated abruptly");
        } catch (NullPointerException e) {
            System.out.println("Client " + line + " Closed");
        } finally {
            try {
                System.out.println("Connection Closing..");
                if (chitat != null) {
                    chitat.close();
                    System.out.println(" Socket Input Stream Closed");
                }
                if (otpravlyat != null) {
                    otpravlyat.close();
                    System.out.println("Socket Out Closed");
                }
                if (s != null) {
                    s.close();
                    System.out.println("Socket Closed");
                }
            } catch (IOException ie) {
                System.out.println("Socket Close Error");
            } finally {
                System.out.println(server1.names);
                System.out.println(server1.points);
            }
        }
        //end finally




       /* if (questions.get(ind).getDescription().contains("{blank}")) {

            Image  image = new Image(new FileInputStream("src/main/java/com/example/demo7/fillin.jpg"));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(750);
            imageView.setFitHeight(430);



            borderPane.setTop(surak(ind));
            borderPane.setCenter(imageView);

           borderPane.setMargin(surak(ind), new Insets(100, 300, 200,200));

        } else {
            Test test = new Test();

            Button r1 = new Button(questions.get(ind).getOptions(0));
            kahootButton(r1);
            r1.setStyle("-fx-background-color:red");

            Button r2 = new Button(questions.get(ind).getOptions(1));
            kahootButton(r2);
            r2.setStyle("-fx-background-color:blue");

            Button r3 = new Button(questions.get(ind).getOptions(2));
            kahootButton(r3);
            r3.setStyle("-fx-background-color:orange");

            Button r4 = new Button(questions.get(ind).getOptions(3));
            kahootButton(r4);
            r4.setStyle("-fx-background-color:green");


            VBox vBox1 = new VBox(3);
            vBox1.getChildren().addAll(r1, r2);


            VBox vBox2 = new VBox(3);
            vBox2.getChildren().addAll(r3, r4);

            HBox hBox = new HBox(3);
            hBox.getChildren().addAll(vBox1, vBox2);

            hBox.setSpacing(8);
            vBox1.setSpacing(8);
            vBox2.setSpacing(8);

            Image  image = new Image(new FileInputStream("src/main/java/com/example/demo7/kahootgif.gif"));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(700);
            imageView.setFitHeight(350);

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

        StackPane nomer = new StackPane();

        Circle circle= new Circle(60);
        circle.setFill(Color.MEDIUMPURPLE);
        lblTimer.setTextFill(Color.WHITE);

        nomer.getChildren().addAll(circle,lblTimer);

        nomer.setLayoutY(300);
        nomer.setLayoutX(70);

        borderPane.getChildren().add(nomer);

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
                int durys1=0;
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
                            durys1++;
                        }

                }
                try {
                    window.setScene(new Scene(SongyQuestion(surak,durys1), W, H));
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
                window.setScene(new Scene(CurrentQuestion(ind+1,s), W, H));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }

        });

        btnPrev.setOnAction(e -> {
            if(questions.get(ind).getDescription().contains("{blank}")){
                otvetyScan[ind]=textfilda[ind];
            }
            try {
                window.setScene(new Scene(CurrentQuestion(ind-1,s),W,H));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });*/
/*
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
                Socket s=null;
                ServerSocket ss2=null;
                System.out.println("Server Listening......");
                try{
                    ss2 = new ServerSocket(4445); // can also use static final PORT_NUM , when defined
                }
                catch(IOException ex){
                    ex.printStackTrace();
                    System.out.println("Server error");

                }

                for(int i=0;i<2;i++){
                    try{
                        s= ss2.accept();
                        System.out.println("connection Established");
                        try {
                            window.setScene(new Scene(CurrentQuestion(ind,s),W,H));
                        } catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                        }
                        System.out.println(names);
                        System.out.println(points);
                    }

                    catch(Exception ex){
                        ex.printStackTrace();
                        System.out.println("Connection Error");

                    }
                }

                sss();
                Collections.shuffle(questions);
                timeline.play();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }




        });

        return stackPane;
    }
    @Override
    public void start(Stage stage) throws IOException {
        Quiz quiz1 = new Quiz();
        /*lblTimer.setText(timeFormat(sec));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                        event ->
                        {
                            try {
                                lblTimer.setText(timeFormat(--sec));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }));
*/
/*
        window = stage;
        stage.setScene(new Scene(chooseFile(),W,H));
        stage.setTitle("Project 2");
        stage.show();
    }
























    private String timeFormat(int sec1) throws FileNotFoundException {
        if(sec1==0){
            if(ind!= questions.size()-1) {
                window.setScene(new Scene(CurrentQuestion(ind + 1,s), W, H));
            }else{
                window.setScene(new Scene(OtvetyQuestion(ind + 1), W, H));
            }
            sec=5;
            ind++;
        }
        String strSec = sec1+"";

        return strSec;
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
        lbl.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 30));
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









    public VBox time15sec(int ind){



        VBox vBox = new VBox();

        HBox hBox = new HBox();
        Label lbl = new Label(questions.get(ind).getDescription().replace("{blank}","______"));
        lbl.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 22));
        lbl.setWrapText(true);

        hBox.getChildren().addAll(lbl);
        hBox.setSpacing(10);'

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
        r1.setMinWidth(650);
        r1.setMinHeight(95);
        r1.setFont(Font.font("Montserrat", FontWeight.BOLD, FontPosture.REGULAR, 20));
    }

    public BorderPane OtvetyQuestion(int ind) throws FileNotFoundException {

        BorderPane borderPane = new BorderPane();
        mediaPlayer.play();

        if (questions.get(ind).getDescription().contains("{blank}")) {

            Image  image = new Image(new FileInputStream("src/main/java/com/example/demo7/fillin.jpg"));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(600);
            imageView.setFitHeight(400);

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




    public static void main(String[] args) {
        launch();
    }
}*/
/*public class HelloApplication extends Application {
    private Stage window;
    private final double W=500, H=400;
    private ArrayList<Question> questions = new ArrayList<>();

   public BorderPane CurrentQuestion(int ind){

        Button redButton = kahootButton("Red", "red");
        Button orangeButton = kahootButton("Orange", "orange");

        VBox vBox1 = new VBox(3);
        vBox1.getChildren().addAll(redButton, orangeButton);

        Button blueButton = kahootButton("Blue", "blue");
        Button greenButton = kahootButton("Green", "green");
        VBox vBox2 = new VBox(3);
        vBox2.getChildren().addAll(blueButton, greenButton);

        HBox hBox = new HBox(3);
        hBox.getChildren().addAll(vBox1, vBox2);
        hBox.setMaxWidth(500);
        hBox.setMaxHeight(100);

        Label txtLabel = new Label("Question description");
        Font font = Font.font("Times New Roman", FontWeight.BOLD,
                FontPosture.ITALIC, 18);
        txtLabel.setFont(font);
        txtLabel.setMinWidth(500);
        txtLabel.setAlignment(Pos.CENTER);

        BorderPane mainPane = new BorderPane();
        hBox.setAlignment(Pos.CENTER);
        mainPane.setTop(txtLabel);
        mainPane.setBottom(hBox);
        BorderPane.setMargin(hBox, new Insets(0, 0, 10, 0));
        primaryStage.setScene(new Scene(mainPane, 500, 400));
        primaryStage.setTitle("JavaFX");
        primaryStage.show();
    }
}     BorderPane borderPane = new BorderPane();

        Label lbl = new Label(questions.get(ind).getDescription());
        lbl.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 22));
        lbl.setWrapText(true);

        Button btnNext = new Button(">>");
        Button btnPrev = new Button("<<");

        btnNext.setMinWidth(100);
        btnNext.setMinHeight(50);

        btnPrev.setMinWidth(100);
        btnPrev.setMinHeight(50);

        borderPane.setCenter(lbl);
        borderPane.setRight(new StackPane(btnNext));
        borderPane.setLeft(new StackPane(btnPrev));

        if(ind == 0 ){
            btnPrev.setVisible(false);
        }
        if(ind == questions.size() -1){
            btnNext.setVisible(false);
        }
        btnNext.setOnAction(e -> {
           window.setScene(new Scene(CurrentQuestion(ind+1),W,H));
        });
        btnPrev.setOnAction(e -> {
            window.setScene(new Scene(CurrentQuestion(ind-1),W,H));
        });
      return borderPane;
    }

    public StackPane chooseFile(){
        StackPane stackPane = new StackPane();
        Quiz quiz = new Quiz();

       Button btnChoose = new Button("Choose a file");
        stackPane.getChildren().addAll(btnChoose);

        btnChoose.setOnAction(e-> {
            FileChooser fc = new FileChooser();
            File file = fc.showOpenDialog(window);



            questions = quiz.zzz();
        window.setScene(new Scene(CurrentQuestion(0),W,H));
        });

     return stackPane;
    }

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane borderPane = new BorderPane();
        Button v1 = new Button("adasdasd");
        borderPane.setCenter(v1);
        int ind=0;
        StackPane stackPane = new StackPane();
        Quiz quiz = new Quiz();
        loadFromFile("src/main/java/com/example/project2/QuiztextFIle");
        questions = quiz.zzz();



        Label lbl = new Label(questions.get(ind).getDescription());
        lbl.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 22));
        lbl.setWrapText(true);

        Button btnNext = new Button(">>");
        Button btnPrev = new Button("<<");

        btnNext.setMinWidth(100);
        btnNext.setMinHeight(50);

        btnPrev.setMinWidth(100);
        btnPrev.setMinHeight(50);

        borderPane.setCenter(lbl);
        borderPane.setRight(new StackPane(btnNext));
        borderPane.setLeft(new StackPane(btnPrev));

        if(ind == 0 ){
            btnPrev.setVisible(false);
        }
        if(ind == questions.size() -1){
            btnNext.setVisible(false);
        }
        btnNext.setOnAction(e -> {
            window.setScene(new Scene(CurrentQuestion(ind+1),W,H));
        });
        btnPrev.setOnAction(e -> {
            window.setScene(new Scene(CurrentQuestion(ind-1),W,H));
        });

       Scene  scene = new Scene(borderPane,W,H);
       window.setScene(scene);
       window.setTitle("Project 2");
       window.show();

    }

    public static void main(String[] args) {
        launch();
    }
}



public class HelloApplication extends Application {
     private Stage window;
     private final double W=500, H=400;
    private ArrayList<Question> questions = new ArrayList<>();

    public BorderPane CurrentQuestion(int ind){

       Button redButton = kahootButton("Red", "red");
        Button orangeButton = kahootButton("Orange", "orange");

        VBox vBox1 = new VBox(3);
        vBox1.getChildren().addAll(redButton, orangeButton);

        Button blueButton = kahootButton("Blue", "blue");
        Button greenButton = kahootButton("Green", "green");
        VBox vBox2 = new VBox(3);
        vBox2.getChildren().addAll(blueButton, greenButton);

        HBox hBox = new HBox(3);
        hBox.getChildren().addAll(vBox1, vBox2);
        hBox.setMaxWidth(500);
        hBox.setMaxHeight(100);

        Label txtLabel = new Label("Question description");
        Font font = Font.font("Times New Roman", FontWeight.BOLD,
                FontPosture.ITALIC, 18);
        txtLabel.setFont(font);
        txtLabel.setMinWidth(500);
        txtLabel.setAlignment(Pos.CENTER);

        BorderPane mainPane = new BorderPane();
        hBox.setAlignment(Pos.CENTER);
        mainPane.setTop(txtLabel);
        mainPane.setBottom(hBox);
        BorderPane.setMargin(hBox, new Insets(0, 0, 10, 0));
        primaryStage.setScene(new Scene(mainPane, 500, 400));
        primaryStage.setTitle("JavaFX");
        primaryStage.show();
    }
}

        BorderPane borderPane = new BorderPane();

        Label lbl = new Label(questions.get(ind).getDescription());
        lbl.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 22));
        lbl.setWrapText(true);

        Button btnNext = new Button(">>");
        Button btnPrev = new Button("<<");

        btnNext.setMinWidth(100);
        btnNext.setMinHeight(50);

        btnPrev.setMinWidth(100);
        btnPrev.setMinHeight(50);

        borderPane.setCenter(lbl);
        borderPane.setRight(new StackPane(btnNext));
        borderPane.setLeft(new StackPane(btnPrev));

        if(ind == 0 ){
            btnPrev.setVisible(false);
        }
        if(ind == questions.size() -1){
            btnNext.setVisible(false);
        }
        btnNext.setOnAction(e -> {
           window.setScene(new Scene(CurrentQuestion(ind+1),W,H));
        });
        btnPrev.setOnAction(e -> {
            window.setScene(new Scene(CurrentQuestion(ind-1),W,H));
        });
      return borderPane;
    }

    public StackPane chooseFile(){
        StackPane stackPane = new StackPane();
        Quiz quiz = new Quiz();

       Button btnChoose = new Button("Choose a file");
        stackPane.getChildren().addAll(btnChoose);

        btnChoose.setOnAction(e-> {
            FileChooser fc = new FileChooser();
            File file = fc.showOpenDialog(window);



            questions = quiz.zzz();
        window.setScene(new Scene(CurrentQuestion(0),W,H));
        });

     return stackPane;
    }

    @Override
    public void start(Stage stage) throws Exception {
       window = stage;
       window.setScene(new Scene(chooseFile(),W,H));
       window.setTitle("Project 2");
       window.show();

    }

    public static void main(String[] args) {
        launch();
    }
}*/
