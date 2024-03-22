package com.example.metodosnumericos.Vistas;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.RandomAccessFile;

public class Opciones extends Stage {
    private Scene escena;
    private Label lblTitulo, lblOpciones, lblAbierto, lblCerrado, lblEG, lblJacobi;
    private Button btnreglaF, btnNR, btnEG, btnJacobi;
    private VBox vbContenedor;
    private HBox hbOp,hbRf,hbNR,hbEG,hbJ;

    public Opciones() {
        CrearUI();
        this.setTitle("Metodos Numericos");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        lblTitulo = new Label("Solucion de ecuaciones");
        lblAbierto = new Label("Abierto ---> Regla Falsa: ");
        lblCerrado = new Label("Cerrado ---> Newton-Raphson: ");
        lblOpciones = new Label("Metodos");
        lblEG = new Label("Eliminacion Gaussiana: ");
        btnreglaF = new Button("Iniciar");
        btnreglaF.setOnAction(event -> new ReglaFalsaGUI());
        btnNR = new Button("Iniciar");
        btnNR.setOnAction(event -> new NewtonRaphson());
        btnEG = new Button("Iniciar");
        btnJacobi = new Button("Iniciar");
        lblJacobi = new Label("Jacobi: ");
        hbOp = new HBox(lblOpciones);
        hbOp.setAlignment(Pos.TOP_CENTER);
        hbRf = new HBox(lblAbierto, btnreglaF);
        hbRf.setAlignment(Pos.CENTER);
        hbNR = new HBox(lblCerrado,btnNR);
        hbNR.setAlignment(Pos.CENTER);
        hbEG = new HBox(lblEG,btnEG);
        hbEG.setAlignment(Pos.CENTER);
        hbJ = new HBox(lblJacobi, btnJacobi);
        hbJ.setAlignment(Pos.CENTER);
        vbContenedor = new VBox(hbOp,hbRf,hbNR,hbEG,hbJ);
        //vbContenedor.setAlignment(Pos.CENTER);
        escena = new Scene(vbContenedor, 600, 600);
    }

}
