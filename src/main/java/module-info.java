module com.example.bomberman {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires json.simple;


    opens com.example.bomberman to javafx.fxml;
    exports com.example.bomberman;
}