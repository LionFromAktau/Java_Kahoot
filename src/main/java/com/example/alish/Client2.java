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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Client2 extends Application {
    String name1;
    String questionDescr;
    String questionAnswer;
    Stage window;
    int razmerVoprosov;
    ArrayList<String> votprosy = new ArrayList<>();
    ArrayList<String> otvety = new ArrayList<>();
    TextField vvediname = new TextField();
    Button enterName = new Button("Enter Name");
    DataInputStream chitat=null;
    DataOutputStream otpravlyat=null;
    BufferedReader scan=null;
    Socket s1=null;
    Timeline timeline = new Timeline();
    Timeline timeline1 = new Timeline();
    public Label lblTimer = new Label();
    int vremya=0;
    int sec=20;
    int ind =0;
    ArrayList<Question> questions = new ArrayList<>();
    Button r1 = new Button("a");
    Button r2 = new Button("c");
    Button r3 = new Button("b");
    Button r4 = new Button("d");
    TextField textFieldFillIn = new TextField();
    Button t1 = new Button("b");
    Button t2 = new Button("a");
    String answersent=null;
    boolean corrincorr=false;
    int bonuses=600;
    boolean nachalo=true;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        timeline1.setCycleCount(Timeline.INDEFINITE);
        timeline1.getKeyFrames().add(
                new KeyFrame(Duration.millis(1000),
                        event ->
                        {
                            bonuses-=50;
                            System.out.println(bonuses);
                        }));



        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(1000),
                        event ->
                        {
                            try {
                                lblTimer.setText(timeFormat(--sec));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }));
        Pane pane = new Pane();
        Image image = null;
        try {
            image = new Image(new FileInputStream("src/main/java/com/example/alish/pin.jpg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ImageView pincartina = new ImageView(image);
        pincartina .setFitWidth(520);
        pincartina .setFitHeight(700);

        TextField vvedipin = new TextField();
        vvedipin.setLayoutX(62);
        vvedipin.setLayoutY(275);
        vvedipin.setMinHeight(50);
        vvedipin.setMinWidth(398);


        Button enterPin = new Button("Enter Pin");
        enterPin.setStyle("-fx-background-color:black");
        enterPin.setTextFill(Color.WHITE);
        enterPin.setLayoutX(62);
        enterPin.setLayoutY(340);
        enterPin.setMinHeight(50);
        enterPin.setMinWidth(398);
        // MediaPlayer mediaPlayer = new MediaPlayer();
        pane.getChildren().addAll(pincartina,vvedipin,enterPin );

        new Thread(()->{
            String name = null;
            InetAddress address= null;
            try {
                address = InetAddress.getLocalHost();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }

            String line=null;



            try {
                s1=new Socket(address, 4445); // You can use static final constant PORT_NUM
                scan= new BufferedReader(new InputStreamReader(System.in));
                chitat=new DataInputStream(s1.getInputStream());
                otpravlyat= new DataOutputStream(s1.getOutputStream());
            }
            catch (IOException e){
                e.printStackTrace();
                System.err.print("IO Exception");
            }

            System.out.println("Client Address : "+address);
            System.out.println("Enter Data to echo Server ( Enter QUIT to end):");

            String response=null;
            try {
                DataOutputStream finalOtpravlyat = otpravlyat;

                enterPin.setOnAction(e->{
                    Quiz quiz = new Quiz();
                    try {
                        questions =  quiz.loadFromFile("src/main/java/com/example/alish/QuiztextFIle");
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    String pin;
                    pin = String.valueOf(vvedipin.getText());

                    if(pin.equals("123654")) {
                        try {
                            finalOtpravlyat.writeUTF(pin);
                            finalOtpravlyat.flush();//1
                            window.setScene(new Scene(enterName(), 500, 720));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            } finally{


            }
        }).start();



        window =primaryStage;
        window.setScene(new Scene(pane,520,700));
        window.show();
    }
    public Pane enterName() throws FileNotFoundException {
        Pane pane = new Pane();
        Image image=null;
        try {
            image = new Image(new FileInputStream("src/main/java/com/example/alish/pin.jpg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ImageView pincartina = new ImageView(image);
        pincartina .setFitWidth(500);
        pincartina .setFitHeight(720);


        vvediname.setLayoutX(59);
        vvediname.setLayoutY(284);
        vvediname.setMinHeight(52);
        vvediname.setMinWidth(391);



        enterName.setStyle("-fx-background-color:black");
        enterName.setTextFill(Color.WHITE);
        enterName.setLayoutX(59);
        enterName.setLayoutY(349);
        enterName.setMinHeight(52);
        enterName.setMinWidth(391);

        // MediaPlayer mediaPlayer = new MediaPlayer();
        pane.getChildren().addAll(pincartina,vvediname,enterName );
        enterName.setOnAction(e->{

            name1 = String.valueOf(vvediname.getText());
            try {
                otpravlyat.writeUTF(name1);
                otpravlyat.flush();//1

                window.setScene(new Scene(promezhutok(), 500, 720));

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });
        return pane;
    }

    public BorderPane zzz(){

        BorderPane borderPane = new BorderPane();

        Rectangle background = new Rectangle(500,720);
        background.setFill(Color.MEDIUMPURPLE);

        borderPane.getChildren().add(background);

        Image image = null;
        try {
            image = new Image(new FileInputStream("src/main/java/com/example/alish/anim04.gif"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        borderPane.setCenter(imageView);

        Label label = new Label("Wait others, genius!");
        label.setTextFill(Color.WHITE);
        label.setFont(Font.font("Montserrat", FontWeight.BOLD, FontPosture.REGULAR, 35));

        borderPane.setBottom(label);

        borderPane.setMargin(label,new Insets(0,0,200,90));

        return borderPane;
    }

    public BorderPane question(int ind) throws FileNotFoundException {

        BorderPane borderPane = new BorderPane();
        //try {
        System.out.println("voshel v question");
        textFieldFillIn.setText("");
        //  mediaPlayer.play();

        System.out.println(questions.size());

        textFieldFillIn.setMinWidth(60);
        textFieldFillIn.setMinHeight(20);

        try {

            //     String surak = questions.get(ind).getDescription();
            if (votprosy.get(ind).contains("{blank}")) {
                textFieldFillIn.setMaxHeight(30);
                textFieldFillIn.setMaxWidth(350);

                VBox vBox = new VBox();
                vBox.getChildren().addAll();


                Image image = new Image(new FileInputStream("src/main/java/com/example/alish/fillin.jpg"));
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(350);
                imageView.setFitHeight(380);

                Label label = new Label();
                label.setText("                                 "+name1);
                label.setFont(Font.font("Montserrat", FontWeight.BOLD, FontPosture.REGULAR, 25));


                vBox.setSpacing(30);
                vBox.getChildren().addAll(imageView,textFieldFillIn);

                borderPane.setTop(label);
                borderPane.setCenter(vBox);
                borderPane.setMargin(vBox,new Insets(80,0,50,100));
            }else if(votprosy.get(ind).contains(".")){
                Label name = new Label("                               "+name1);
                name.setFont(Font.font("Montserrat", FontWeight.BOLD, FontPosture.REGULAR, 28));
                // Label question1 = new Label(ind+1+"/"+razmerVoprosov);
                // question1.setFont(Font.font("Montserrat", FontWeight.BOLD, FontPosture.REGULAR, 24));

                HBox nameQueston = new HBox();
                nameQueston.setSpacing(280);
                nameQueston.getChildren().addAll(name);//question1
                borderPane.setTop(nameQueston);
                borderPane.setMargin(nameQueston, new Insets(5, 0, 0, 0));



                TrueFalse(t1);
                t1.setStyle("-fx-background-color:red");


                TrueFalse(t2);
                t2.setStyle("-fx-background-color:blue");


                HBox hBox = new HBox(3);
                hBox.setSpacing(20);
                hBox.getChildren().addAll(t2, t1);

                borderPane.setBottom(hBox);
                borderPane.setMargin(hBox, new Insets(0, 0, 30, 38));

            }else{
                Label name = new Label("                          "+name1);
                name.setFont(Font.font("Montserrat", FontWeight.BOLD, FontPosture.REGULAR, 24));
                //  Label question1 = new Label(ind+1+"/"+razmerVoprosov);
                // question1.setFont(Font.font("Montserrat", FontWeight.BOLD, FontPosture.REGULAR, 24));

                HBox nameQueston = new HBox();
                nameQueston.setSpacing(280);
                nameQueston.getChildren().addAll(name);
                borderPane.setTop(nameQueston);
                borderPane.setMargin(nameQueston, new Insets(5, 0, 0, 0));


                kahootButton(r1);
                r1.setStyle("-fx-background-color:red");


                kahootButton(r2);
                r2.setStyle("-fx-background-color:blue");


                kahootButton(r3);
                r3.setStyle("-fx-background-color:orange");


                kahootButton(r4);
                r4.setStyle("-fx-background-color:green");


                VBox vBox1 = new VBox(3);
                vBox1.setSpacing(20);
                vBox1.getChildren().addAll(r1, r2);


                VBox vBox2 = new VBox(3);
                vBox2.setSpacing(20);
                vBox2.getChildren().addAll(r3, r4);

                HBox hBox = new HBox(3);
                hBox.setSpacing(20);
                hBox.getChildren().addAll(vBox1, vBox2);

                borderPane.setBottom(hBox);
                borderPane.setMargin(hBox, new Insets(0, 0, 20, 38));

            }
            answersent=null;

            r1.setOnAction(e->{
                answersent ="0";
                vremya=bonuses;
                System.out.println(vremya);
                timeline1.stop();
                bonuses=600;
                window.setScene(new Scene(zzz(),500,720));
            });
            r2.setOnAction(e->{
                answersent ="1";
                vremya=bonuses;
                System.out.println(vremya);
                timeline1.stop();
                bonuses=600;
                window.setScene(new Scene(zzz(),500,720));

            });
            r3.setOnAction(e->{
                answersent ="2";
                vremya=bonuses;
                System.out.println(vremya);
                timeline1.stop();
                bonuses=600;
                window.setScene(new Scene(zzz(),500,720));

            });
            r4.setOnAction(e->{
                answersent ="3";
                vremya=bonuses;
                System.out.println(vremya);
                timeline1.stop();
                bonuses=600;
                window.setScene(new Scene(zzz(),500,720));

            });

            textFieldFillIn.setOnAction(e->{
                vremya=bonuses;
                System.out.println(vremya);
                timeline1.stop();
                bonuses=600;
                answersent=null;
                answersent = (String.valueOf(textFieldFillIn.getText()));
                window.setScene(new Scene(zzz(),500,720));
            });
            t1.setOnAction(e->{
                answersent ="False";
                vremya=bonuses;
                System.out.println(vremya);
                timeline1.stop();
                bonuses=600;
                window.setScene(new Scene(zzz(),500,720));
            });

            t2.setOnAction(e->{
                answersent ="True";
                vremya=bonuses;
                System.out.println(vremya);
                timeline1.stop();
                bonuses=600;
                window.setScene(new Scene(zzz(),500,720));
            });





        }catch (IOException e) {
            e.printStackTrace();
        } finally {


        }
        //}
        //catch (IOException e) {
        //    e.printStackTrace();
        // }

        return borderPane;
    }


    public Pane promezhutok(){
        if(!nachalo){
            timeline.play();
        }
        new Thread(() -> {
            String response = null;

            try
            {
                razmerVoprosov = chitat.readInt();

                for(int i1=0;i1< razmerVoprosov;i1++){
                    response = chitat.readUTF();//vopros
                    votprosy.add(response);
                    System.out.println(response);//otvet
                    response = chitat.readUTF();
                    otvety.add(response);
                    System.out.println(response);
                }
                response = chitat.readUTF();

            } catch(
                    IOException e)

            {
                e.printStackTrace();
            }
            if(response.equals("111"))

            {
                Platform.runLater(()->{
                    window.setScene(new Scene(promezhutok(), 500, 720));
                });

            }
        }).start();
        Pane borderPane = new Pane();
        Image image = null;
        try {
            image = new Image(new FileInputStream("src/main/java/com/example/alish/200.gif"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(750);
        imageView.setFitWidth(500);

        borderPane.getChildren().add(imageView);
        nachalo=false;
        return borderPane;

    }

    public Pane Corr() throws FileNotFoundException {
        Pane pane = new Pane();
        Image image = new Image(new FileInputStream("src/main/java/com/example/alish/Correct.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(500);
        imageView.setFitHeight(720);
        pane.getChildren().add(imageView);
        return pane;
    }

    public Pane InCorr() throws FileNotFoundException {
        Pane pane = new Pane();
        Image image = new Image(new FileInputStream("src/main/java/com/example/alish/InCorrect.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(500);
        imageView.setFitHeight(720);
        pane.getChildren().add(imageView);
        return pane;
    }

    public Pane TimesApp() throws FileNotFoundException {
        Pane pane = new Pane();
        Image image = new Image(new FileInputStream("src/main/java/com/example/alish/timesApp.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(500);
        imageView.setFitHeight(720);
        pane.getChildren().add(imageView);
        return pane;
    }

    public static void kahootButton(Button r1){
        r1.setTextFill(Color.WHITE);
        r1.setMinWidth(210);
        r1.setMinHeight(295);
        r1.setFont(Font.font("Montserrat", FontWeight.BOLD, FontPosture.REGULAR, 15));
    }
    public static void TrueFalse(Button r1){
        r1.setTextFill(Color.WHITE);
        r1.setMinWidth(210);
        r1.setMinHeight(580);
        r1.setFont(Font.font("Montserrat", FontWeight.BOLD, FontPosture.REGULAR, 15));
    }

    public BorderPane loncovka(){

        BorderPane borderPane = new BorderPane();
        Image image = null;
        try {
            image = new Image(new FileInputStream("src/main/java/com/example/oofrmlenie3project/giphy.gif"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(300);
        imageView.setFitHeight(200);

        Rectangle background = new Rectangle();
        background.setFill(Color.INDIGO);
        background.setHeight(700);
        background.setWidth(520);
        Label label = new Label("Thank you for game!");
        Label label1 = new Label("See you next time!");
        label.setTextFill(Color.WHITE);
        label1.setTextFill(Color.WHITE);
        label.setFont(Font.font("Montserrat", FontWeight.BOLD, FontPosture.REGULAR, 45));
        label1.setFont(Font.font("Montserrat", FontWeight.BOLD, FontPosture.REGULAR, 35));
        VBox vbox = new VBox();
        vbox.getChildren().addAll(label,label1,imageView);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(15);
        borderPane.getChildren().add(background);
        borderPane.setCenter(vbox);
        borderPane.setMargin(vbox,new Insets(0,0,70,0));

        return borderPane;
    }

    public String timeFormat(int sec1) throws FileNotFoundException {
        if(sec1==0){
            if(ind!= razmerVoprosov){
                ind++;
                window.setScene(new Scene(promezhutok(),500, 720));
            }else {
                window.setScene(new Scene(loncovka(),500, 720));
                System.out.println("close");
                try {
                    chitat.close();
                    otpravlyat.close();scan.close();s1.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("Connection Closed");

                //    window.setScene(new Scene(OtvetyQuestion(ind + 1), W, H));
            }
            sec=20;
        }else if(sec1==18){
            System.out.println("sssss");
            new Thread(()->{
                try {
                    questionDescr=chitat.readUTF();
                    questionAnswer=chitat.readUTF();
                    System.out.println(questionDescr+"111");
                    System.out.println(questionAnswer+"111");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }else if(sec1==16){
            window.setScene(new Scene(question(ind),500,720));
            timeline1.play();
        }else if(sec1==5){
            corrincorr=false;
            new Thread(()->{
                try {
                    if(answersent==null || answersent=="notanswer" ){
                        answersent="notanswer";
                        timeline1.stop();
                        bonuses=600;
                    }
                    otpravlyat.writeUTF(answersent);//1
                    otpravlyat.flush();
                    otpravlyat.writeInt(vremya);
                    otpravlyat.flush();

                    System.out.println(corrincorr+"final");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

            System.out.println(answersent+"minal");
            try {
                if(answersent==null){
                    System.out.println("asdasdas");
                    window.setScene(new Scene(TimesApp(), 500, 720));
                }else if(answersent.equals(otvety.get(ind))) {
                    System.out.println("Correct");
                    window.setScene(new Scene(Corr(), 500, 720));
                    System.out.println("Yes");
                    //bonuses+300
                }else {
                    System.out.println("InCorrect");
                    window.setScene(new Scene(InCorr(), 500, 720));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            /*try {
                if(( answersent.equals("0") || answersent.equals("1") || answersent.equals("2") || answersent.equals("3") )){
                    int a= (Integer.parseInt(answersent));
                    if( questions.get(ind).getOptions(a).equals(otvety.get(ind))){
                        System.out.println("Trueaaaaa");
                        window.setScene(new Scene(Corr(), 500, 720));
                        System.out.println("Yes");
                        //bonuses+300
                    }else{
                        System.out.println("InCorrect");
                        window.setScene(new Scene(InCorr(), 500, 720));
                    }
                }else if (answersent.equals(otvety.get(ind))) {
                    System.out.println("Correct");
                    window.setScene(new Scene(Corr(), 500, 720));
                    System.out.println("Yes");
                    //bonuses+300
                }else {
                    System.out.println("InCorrect");
                    window.setScene(new Scene(InCorr(), 500, 720));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }*/
        }

        String strSec = sec1+"";

        return strSec;
    }
    public Pane enterPin() throws FileNotFoundException {

        Pane pane = new Pane();
        Image image = new Image(new FileInputStream("src/main/java/com/example/alish/pin.jpg"));
        ImageView pincartina = new ImageView(image);
        pincartina .setFitWidth(500);
        pincartina .setFitHeight(720);

        TextField vvedipin = new TextField();
        vvedipin.setLayoutX(62);
        vvedipin.setLayoutY(275);
        vvedipin.setMinHeight(50);
        vvedipin.setMinWidth(398);


        Button enterPin = new Button("Enter Pin");
        enterPin.setStyle("-fx-background-color:black");
        enterPin.setTextFill(Color.WHITE);
        enterPin.setLayoutX(62);
        enterPin.setLayoutY(340);
        enterPin.setMinHeight(50);
        enterPin.setMinWidth(398);
        // MediaPlayer mediaPlayer = new MediaPlayer();
        pane.getChildren().addAll(pincartina,vvedipin,enterPin );
        return pane;
    }


}



