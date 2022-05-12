module com.example.taskappv3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.taskappv3 to javafx.fxml;
    exports com.example.taskappv3;
}