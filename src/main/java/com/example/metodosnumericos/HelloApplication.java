package com.example.metodosnumericos;

import com.example.metodosnumericos.Vistas.Opciones;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private BorderPane bdpPanel;
    private Label lblIntegrantes, lblAngel, lblRobert, lblRafael, lblYahir, lblJAngel, lblTitle;
    private VBox vbContenedor, vbContenedorSup;
    private Button iniciar;

    @Override
    public void start(Stage stage) throws IOException {
        bdpPanel = new BorderPane();
        lblIntegrantes = new Label("Integrantes");
        lblTitle = new Label("Proyecto metodos numericos");
        lblAngel = new Label("- Navarro Flores Jose Angel");
        lblRafael = new Label("- Rafael");
        lblRobert =new Label("- Duran Castillo Roberto Emmanuel");
        lblJAngel = new Label("- Herrera Morales Jose Angel");
        lblYahir =  new Label("- Muñoz Hernandez Eric Yahir");
        iniciar = new Button("Iniciar");
        iniciar.setOnAction(event -> new Opciones());
        vbContenedor = new VBox(lblIntegrantes,lblAngel,lblRobert,lblRafael,lblJAngel,lblYahir,iniciar);
        vbContenedorSup = new VBox(lblTitle);
        vbContenedorSup.setAlignment(Pos.TOP_CENTER);
        vbContenedor.setAlignment(Pos.CENTER);
        bdpPanel.setTop(vbContenedorSup);
        bdpPanel.setCenter(vbContenedor);
        BorderPane.setAlignment(vbContenedor, Pos.CENTER);
        Scene scene = new Scene(bdpPanel);
        //scene.getStylesheets().add(getClass().getResource("/Estilos/main.css").toString());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        stage.setWidth(600);
        stage.setHeight(600);
    }

    public static void main(String[] args) {
        launch();
    }
}