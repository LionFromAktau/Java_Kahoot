package com.example.alish;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class HelloApplication extends Application {
       int ind=0;
       Quiz quiz = new Quiz();
       ArrayList<Question>  questions;

    {
        try {
            questions = quiz.loadFromFile("src/main/java/com/example/alish/QuiztextFIle");
            Zagruzka zagruzka = new Zagruzka();
            zagruzka.setZagruzka(questions);
          //  System.out.println(zagruzka.getZagruzka());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

       ArrayList<String> names = new ArrayList<>();
       ArrayList<Integer> points = new ArrayList<>();

       ArrayList<DataOutputStream> output = new ArrayList<>();
       ArrayList<DataInputStream> input = new ArrayList<>();
       Stage window;
       Timeline timeline = new Timeline();
       public Label lblTimer = new Label();
       public Label lblTimer15 = new Label();

       Media media = new Media(new File("src/main/java/com/example/alish/kahoot_music.wav").toURI().toString());
       MediaPlayer mediaPlayer = new MediaPlayer(media);

    Media media1 = new Media(new File("src/main/java/com/example/alish/baraban.mp3").toURI().toString());
    MediaPlayer mediaPlayer1 = new MediaPlayer(media1);

       int sec=20;
       int sec15=16;
       String line;
       DataInputStream is=null;
       DataOutputStream os=null;
       int h=0;
       ArrayList<String> mass = new ArrayList<>();
       boolean corincorr = true;



    @Override
    public void start(Stage stage) throws IOException {
        Quiz quiz = new Quiz();
        //System.out.println(questions);

        Rectangle rectangle = new Rectangle(1250,750);
        rectangle.setFill(Color.INDIGO);

        Label label1  = new Label("Welcome to Kahoot!");
        label1.setTextFill(Color.WHITE);
        label1.setFont(Font.font("Montserrat", FontWeight.BOLD, FontPosture.REGULAR, 58));

        Label label = new Label("PIN: 123654");
        label.setTextFill(Color.WHITE);
        label.setFont(Font.font("Montserrat", FontWeight.BOLD, FontPosture.REGULAR, 58));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                        event ->
                        {
                            try {
                                lblTimer.setText(timeFormat(--sec));
                                lblTimer15.setText(String.valueOf((--sec15)));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                        }));
        window=stage;
        BorderPane borderPane = new BorderPane();
        borderPane.getChildren().add(rectangle);
        Text textArea = new Text();
        textArea.setFill(Color.WHITE);
        textArea.setFont(Font.font("Montserrat", FontWeight.BOLD, FontPosture.REGULAR, 48));

        int z=0;
        Button start = new Button("Start");
        start.setStyle("-fx-background-color:black");
        start.setTextFill(Color.WHITE);
        start.setMinWidth(380);
        start.setMinHeight(70);
        start.setFont(Font.font("Montserrat", FontWeight.BOLD, FontPosture.REGULAR, 21));



        start.setOnAction(e-> {
                    for(int i=0;i<names.size();i++){
                        points.add(0);
                    }
                  /*  for(int g=0;g< questions.size();g++){
                        if(!questions.get(g).getDescription().contains("{blank}") || !questions.get(g).getDescription().contains(".")){
                            for(int a){

                            }
                        }
                    }*/
                    try{

                            for (int i = 0; i < input.size(); i++) {
                                output.get(i).writeInt(questions.size());

                                for(int i1=0;i1< questions.size();i1++){
                                    output.get(i).writeUTF(String.valueOf(questions.get(i1).getDescription()));
                                    output.get(i).flush();
                                    if(questions.get(i1).getDescription().contains("?")){
                                        for(int j=0;j<4;j++){
                                            if(questions.get(i1).getOptions(j).equals(questions.get(i1).getAnswer())){
                                                output.get(i).writeUTF(String.valueOf(j));
                                                output.get(i).flush();
                                                break;
                                            }
                                        }
                                    }else {
                                        output.get(i).writeUTF(String.valueOf(questions.get(i1).getAnswer()));
                                        output.get(i).flush();
                                    }
                                }
                                output.get(i).writeUTF("111");
                                output.get(i).flush();

                            }
                        window.setScene(new Scene(prequestion(), 1250, 750));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
        );

     new Thread(() -> {
            // can also use static final PORT_NUM , when defined
            try {
                ServerSocket ss2 = new ServerSocket(4445);
                while(true) {

                    Socket s = ss2.accept();


                     is = new DataInputStream(s.getInputStream());
                     os = new DataOutputStream(s.getOutputStream());
                     String pin = is.readUTF();//1



                    //name
                    line = is.readUTF();
                    textArea.setText(textArea.getText() + line + " ");
                    names.add(line);

                    input.add(is);
                    output.add(os);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }).start();

        VBox vbox = new VBox();
        vbox.getChildren().addAll(label1,label);
        vbox.setAlignment(Pos.CENTER);
        borderPane.setTop(vbox);
        borderPane.setCenter(textArea);
        borderPane.setBottom(start);
        borderPane.setMargin(start, new Insets(0,90,150,465));
        borderPane.setMargin(label,new Insets(50,0,0,540));
      //  System.out.println("bastaldy");


        window.setScene(new Scene(borderPane, 1250, 750));
        stage.show();
    }


     public BorderPane prequestion(){
        timeline.play();
        BorderPane pane = new BorderPane();
        Label label = new Label();
        if(questions.get(ind).getDescription().contains("{blank}")){
            label.setText(questions.get(ind).getDescription().replace("{blank}","______"));
        }else{
            label.setText(questions.get(ind).getDescription());
        }
        label.setFont(Font.font("Montserrat", FontWeight.BOLD, FontPosture.REGULAR, 30));
        pane.setCenter(label);
        return pane;
     }


    public BorderPane question(int ind) throws FileNotFoundException {
        mediaPlayer.play();
        sec15=12;
        BorderPane borderPane = new BorderPane();

        StackPane stackPane = new StackPane();

        Circle circle = new Circle(55);
        circle.setFill(Color.MEDIUMPURPLE);

        lblTimer15.setTextFill(Color.WHITE);
        lblTimer15.setFont(Font.font("Montserrat", FontWeight.BOLD, FontPosture.REGULAR, 18));
        stackPane.getChildren().addAll(circle,lblTimer15);
        borderPane.setLeft(stackPane);
        try {
           // System.out.println("voshel v question");
      //mediaPlayer.play();

        int durys=0;
        int question =5;
          try {

             if(questions.get(ind).getDescription().contains("{blank}")){

             Image image = new Image(new FileInputStream("src/main/java/com/example/alish/fillin.jpg"));
             ImageView imageView = new ImageView(image);
             imageView.setFitWidth(750);
             imageView.setFitHeight(430);

             borderPane.setTop(surak(ind));
             borderPane.setCenter(imageView);

            }else if(questions.get(ind).getDescription().contains(".")){

                 Image image = null;
                 try {
                     image = new Image(new FileInputStream("src/main/java/com/example/alish/background.jpg"));
                 } catch (FileNotFoundException e) {
                     e.printStackTrace();
                 }
                 ImageView imageView = new ImageView(image);
                 imageView.setFitWidth(700);
                 imageView.setFitHeight(350);

                 Button t1 = new Button("False");
                 TrueFalse(t1);
                 t1.setStyle("-fx-background-color:red");

                 Button t2 = new Button("True");
                 TrueFalse(t2);
                 t2.setStyle("-fx-background-color:blue");


                 HBox hBox = new HBox(3);
                 hBox.setSpacing(20);
                 hBox.getChildren().addAll(t2, t1);

                 borderPane.setCenter(imageView);
                 borderPane.setTop(surak(ind));
                 borderPane.setBottom(hBox);

                 borderPane.setMargin(hBox, new Insets(0, 0, 30, 38));

             }else{
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

                 Image  image = new Image(new FileInputStream("src/main/java/com/example/alish/kahootgif.gif"));
                 ImageView imageView = new ImageView(image);
                 imageView.setFitWidth(700);
                 imageView.setFitHeight(350);

                 hBox.setAlignment(Pos.CENTER);

                 borderPane.setTop(surak(ind));
                 borderPane.setBottom(hBox);
                 borderPane.setCenter(imageView);

                 borderPane.setMargin(hBox, new Insets(10, 0, 0, 0));
             }
         borderPane.setMargin(stackPane,new Insets(0,0,25,25));

     }catch (IOException e) {
         e.printStackTrace();
     } finally {
     }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return borderPane;
    }
    public  void sorting(){
        // Outer loop
        for (int i = 0; i < points.size(); i++) {
            // Inner nested loop pointing 1 index ahead
            for (int j = i + 1; j < points.size(); j++) {
                // Checking elements
                int temp = 0;
                String temp1 =null;
                DataOutputStream temp3=null;
                DataInputStream temp4 =null;

                if (points.get(j) < points.get(i)) {
                    // Swapping
                    temp = points.get(i);
                    points.set(i,points.get(j));
                    points.set(j,temp);

                    temp1 = names.get(i);
                    names.set(i,names.get(j));
                    names.set(j,temp1);

                    temp3 = output.get(i);
                    output.set(i,output.get(j));
                    output.set(j,temp3);

                    temp4 = input.get(i);
                    input.set(i,input.get(j));
                    input.set(j,temp4);


                }
            }
            // Printing sorted array elements
        }

    }

    public Pane OtvetyQuestion(){
     // System.out.println(names);
     // System.out.println(points);

        names= sort(points,names);


        //System.out.println(names);
        //System.out.println(points);

        //sorting


        Pane pane = new Pane();
        Image image = null;
        try {
            image = new Image(new FileInputStream("src/main/java/com/example/alish/poslednee.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(1250);
        imageView.setFitHeight(750);
        pane.getChildren().add(imageView);
        for(int i=0;i< names.size();i++){
            Label lbl1 = new Label(names.get(i));
            lbl1.setTextFill(Color.WHITE);
            lbl1.setFont(Font.font("Montserrat", FontWeight.BOLD, FontPosture.REGULAR, 35));
            if(i==0){
                lbl1.setLayoutY(300);
                lbl1.setLayoutX(601);
                pane.getChildren().add(lbl1);
            }else if(i==1){
                lbl1.setLayoutY(360);
                lbl1.setLayoutX(379);
                pane.getChildren().add(lbl1);
            } else if (i==2) {
                lbl1.setLayoutY(420);
                lbl1.setLayoutX(797);
                pane.getChildren().add(lbl1);
                break;
            }
           //lbl3
            }



        return pane;
    }

    public BorderPane corr(){
        BorderPane pane = new BorderPane();
        Rectangle backgrounds = new Rectangle();
        backgrounds.setFill(Color.INDIGO);
        backgrounds.setHeight(750);
        backgrounds.setWidth(1250);
        VBox vBox = new VBox();

        Label label = new Label("The best of the best:");
        label.setTextFill(Color.WHITE);
        label.setFont(Font.font("Montserrat", FontWeight.BOLD, FontPosture.REGULAR, 40));
        vBox.getChildren().add(label);

        for(int i=0;i< names.size();i++){
            if(i==3){
                break;
            }
            StackPane stackPane = new StackPane();

            Label label1 = new Label();

            label1.setText(String.valueOf(points.get(i)));
            label1.setTextFill(Color.WHITE);
            label1.setFont(Font.font("Montserrat", FontWeight.BOLD, FontPosture.REGULAR, 40));


            Rectangle rectangle = new Rectangle();
            rectangle.setFill(Color.BLACK);
            rectangle.setArcHeight(40);
            rectangle.setArcWidth(40);
            rectangle.setOpacity(0.1);
            rectangle.setHeight(80);
            rectangle.setWidth(250);

            stackPane.getChildren().addAll(rectangle,label1);

            Label lable = new Label();
            lable.setText(names.get(i));
            lable.setTextFill(Color.WHITE);
            lable.setFont(Font.font("Montserrat", FontWeight.BOLD, FontPosture.REGULAR, 40));

            HBox hbox = new HBox();
            hbox.getChildren().addAll(lable,stackPane);
            hbox.setSpacing(40);
            hbox.setAlignment(Pos.CENTER);

            vBox.getChildren().addAll(hbox);
        }
        vBox.setSpacing(25);

        Button button = new Button("ddd");


        pane.getChildren().add(backgrounds);

        vBox.setAlignment(Pos.CENTER);
        pane.setCenter(vBox);
        return pane;
    }
    public static void TrueFalse(Button r1){
        r1.setTextFill(Color.WHITE);
        r1.setMinWidth(580);
        r1.setMinHeight(175);
        r1.setFont(Font.font("Montserrat", FontWeight.BOLD, FontPosture.REGULAR, 15));
    }


    public String timeFormat(int sec1) throws FileNotFoundException {

        if(sec1==0){

            mediaPlayer1.stop();
            if(ind<questions.size()){
                ind++;
            }
           if(ind!=questions.size()){
                try {
                    window.setScene(new Scene(prequestion(),1250, 750));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else{
               //System.out.println("Konec");
                window.setScene(new Scene(OtvetyQuestion(), 1250, 750));
                timeline.stop();
            }
           sec=20;

        }else if(sec1==18){
            //System.out.println("sssss");
            new Thread(()->{

                for(int i=0;i<input.size();i++) {
                    try {
                        //System.out.println("sssss222222222");
                        output.get(i).writeUTF(questions.get(ind).getDescription());
                        output.get(i).writeUTF(questions.get(ind).getAnswer());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }else if(sec1==16){
            window.setScene(new Scene(question(ind), 1250, 750));
        }else if(sec1==5){
            new Thread(()->{
                for (int i=0;i<input.size();i++) {
                    corincorr=false;
                    String answer = null;//1
                    int bonuse=0;
                    try {
                        answer = input.get(i).readUTF();
                        bonuse=input.get(i).readInt();
                        //1
                        //System.out.println(bonuse);
                        //System.out.println(answer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(( answer.equals("0") || answer.equals("1") || answer.equals("2") || answer.equals("3") )){
                        int a= (Integer.parseInt(answer));

                       if( questions.get(ind).getOptions(a).equals(questions.get(ind).getAnswer())){
                           //System.out.println("Trueaaaaa");
                           corincorr=true;
                           int z=points.get(i)+300+bonuse;
                           points.set(i,z);
                           //bonuses+300
                       }else{
                           //System.out.println("False");
                       }
                    }else if (answer.equals(questions.get(ind).getAnswer())) {
                        corincorr = true;
                        //System.out.println("Yes");
                        //bonuses+300
                        int z=points.get(i)+300+bonuse;
                        points.set(i,z);
                    }
                  //  try {
                       // System.out.println(corincorr+"final");
                       // output.get(i).writeBoolean(corincorr);
                       //output.get(i).flush();
                  //  } catch (IOException e) {
                  //      e.printStackTrace();
                  //  }
                }
            }).start();
        }else if(sec1==4){
            names= sort1(points,names,output,input);
            mediaPlayer.pause();

            mediaPlayer1.play();
            window.setScene(new Scene(corr(),1250,750));
        }
        String strSec = sec1+"";

        return strSec;
    }






       /* else {
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
                    window.setScene(new Scene(SngyQuestion(surak,durys1), W, H));
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



    public static void kahootButton(Button r1){
        r1.setTextFill(Color.WHITE);
        r1.setMinWidth(650);
        r1.setMinHeight(95);
        r1.setFont(Font.font("Montserrat", FontWeight.BOLD, FontPosture.REGULAR, 20));
    }
    public static String[] shuffle(String[] mass){
        String temp="";
        for(int i=0;i<mass.length;i++){
            int s = (int)(Math.random()*mass.length);
            temp=mass[i];
            mass[i]=mass[s];
            mass[s]=temp;
        }
        return mass;
    }

    public ArrayList<String> sort1(ArrayList<Integer> points, ArrayList<String> names,ArrayList<DataOutputStream> output,ArrayList<DataInputStream> input) {
        boolean check = true;
        while(check) {
            check = false;
            for(int i = 0; i < points.size() - 1; i++) {
                if(points.get(i) < points.get(i + 1)) {
                    String temp1 = names.get(i + 1);
                    names.set(i + 1, names.get(i));
                    names.set(i, temp1);

                    int temp = points.get(i + 1);
                    points.set(i + 1, points.get(i));
                    points.set(i, temp);


                    DataOutputStream temp3 = output.get(i + 1);
                    output.set(i + 1,output.get(i));
                    output.set(i,temp3);

                    DataInputStream temp4 = input.get(i + 1);
                    input.set(i + 1,input.get(i));
                    input.set(i,temp4);
                    check = true;
                }
            }
        }
        return names;
    }

    public ArrayList<String> sort(ArrayList<Integer> points, ArrayList<String> names) {
        boolean check = true;
        while(check) {
            check = false;
            for(int i = 0; i < points.size() - 1; i++) {
                if(points.get(i) < points.get(i + 1)) {
                    String temp1 = names.get(i + 1);
                    names.set(i + 1, names.get(i));
                    names.set(i, temp1);

                    int temp = points.get(i + 1);
                    points.set(i + 1, points.get(i));
                    points.set(i, temp);

                    check = true;

                }
            }
        }
        return names;
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

       // lblTimer.setMinWidth(900);
        //lblTimer.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 18));
        //lblTimer.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(hBox);//dobav lbltimer
        vBox.setSpacing(15);



        return vBox;
    }




    public static void main(String[] args) {
        launch();
    }

}

class Zagruzka{
    ArrayList<Question> questionzzz = new ArrayList<>();
    public  Zagruzka(){

    }
    public void setZagruzka(ArrayList<Question> questionqq){
        questionzzz = questionqq;
    }
    public ArrayList<Question> getZagruzka(){
       return questionzzz;
    }


}


