package com.example.alish;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
/*enterPin.setOnAction(e->{
                   String pin;
                   pin = String.valueOf(vvedipin.getText());

                   try {
                       finalOtpravlyat.writeUTF(pin);
                       finalOtpravlyat.flush();//1
                       window.setScene(new Scene(enterName(),500,720));
                   } catch (Exception ex) {
                       ex.printStackTrace();
                   }

               });

               response =chitat.readUTF();
               if(response.equals("111")) {
                 try {
                    window.setScene(new Scene(question(ind), 500, 720));
                } catch (FileNotFoundException e) {
                   e.printStackTrace();
               }




                public BorderPane prequestion(int ind1){
        BorderPane borderPane = new BorderPane();
        VBox vBox = new VBox();
        Label lbl = new Label(questions.get(ind).getDescription());
        lbl.setFont(Font.font("Montserrat", FontWeight.BOLD, FontPosture.REGULAR, 35));

        //vBox.getChildren().add();
        vBox.getChildren().add(lbl);
        borderPane.setTop(vBox);
        borderPane.setMargin(vBox,new Insets(200,0,0,100));

        return borderPane;
        programming language
java is coffee
java is car
it is animal
The president Kz Republic is Tokaev.
False
A {blank} variable is a constant value that can't be changed
final
JRE stands for Java {blank} Environment.
runtime
Comparable is an example for Java  {blank}
interface
Which one is False about Java Object Class?
it is an abstract class
it has toString method implemented
a new instance of Object can be created
any class in Java is th child of Object
    }*/
public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}