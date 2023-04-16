module com.example.alish {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;


    opens com.example.alish to javafx.fxml;
    exports com.example.alish;
}