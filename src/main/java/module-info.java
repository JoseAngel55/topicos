module com.example.metodosnumericos {
    requires javafx.controls;
    requires javafx.fxml;
    requires exp4j;
    requires java.desktop;


    opens com.example.metodosnumericos to javafx.fxml;
    exports com.example.metodosnumericos;
}