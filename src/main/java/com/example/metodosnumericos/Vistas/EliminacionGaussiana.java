package com.example.metodosnumericos.Vistas;


import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EliminacionGaussiana extends Stage {
    private Scene escena;
    private TextField txtnumEc;
    private Button btnCalcular, btnAyuda, btnAceptar;
    private Label res;
    private TextField[][] txtMatriz;
    private VBox vbContenedor;
    private HBox hbSup;
    private GridPane gridMatriz;

    public EliminacionGaussiana(){
        CrearUI();
        this.setTitle("Eliminacion Gaussiana");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        txtnumEc = new TextField();
        btnCalcular = new Button("Calcular");
        btnAyuda = new Button("Ayuda");
        btnAceptar = new Button("Aceptar");
        hbSup = new HBox(10, txtnumEc, btnCalcular, btnAyuda, btnAceptar);

        vbContenedor = new VBox(10, hbSup);
        vbContenedor.setPadding(new Insets(10));
        escena = new Scene(vbContenedor, 400, 300);

        btnAceptar.setOnAction(e -> crearMatriz());
    }
    private void crearMatriz() {
        int numEc = Integer.parseInt(txtnumEc.getText());
        txtMatriz = new TextField[numEc][numEc + 1];
        gridMatriz = new GridPane();
        gridMatriz.setHgap(10);
        gridMatriz.setVgap(5);
        gridMatriz.setPadding(new Insets(10));

        for (int i = 0; i < numEc; i++) {
            for (int j = 0; j < numEc + 1; j++) {
                TextField textField = new TextField();
                txtMatriz[i][j] = textField;
                gridMatriz.add(textField, j, i);
            }
        }

        vbContenedor.getChildren().add(gridMatriz);
        btnCalcular.setOnAction(e -> realizarEliminacionGaussiana());
        hbSup.getChildren().remove(btnAceptar); // Eliminar el botón Aceptar después de crear la matriz
    }

    private void realizarEliminacionGaussiana() {
        int numEc = Integer.parseInt(txtnumEc.getText());
        double[][] matriz = new double[numEc][numEc + 1];

        // Llenar la matriz desde los TextField
        for (int i = 0; i < numEc; i++) {
            for (int j = 0; j < numEc + 1; j++) {
                matriz[i][j] = Double.parseDouble(txtMatriz[i][j].getText());
            }
        }

        // Aplicar eliminación gaussiana
        for (int k = 0; k < numEc; k++) {
            for (int i = k + 1; i < numEc; i++) {
                double factor = matriz[i][k] / matriz[k][k];
                for (int j = k; j < numEc + 1; j++) {
                    matriz[i][j] -= factor * matriz[k][j];
                }
            }
        }

        // Obtener la solución
        double[] solucion = new double[numEc];
        for (int i = numEc - 1; i >= 0; i--) {
            solucion[i] = matriz[i][numEc];
            for (int j = i + 1; j < numEc; j++) {
                solucion[i] -= matriz[i][j] * solucion[j];
            }
            solucion[i] /= matriz[i][i];
        }

        // Mostrar la solución o realizar alguna acción con ella
        // Por ejemplo, imprimir en la consola
        for (int i = 0; i < numEc; i++) {
            System.out.println("x[" + (i+1) + "] = " + solucion[i]);
            res = new Label("x[" + (i+1) + "] = " + solucion[i]);
            vbContenedor.getChildren().add(res);
        }
        //vbContenedor.getChildren().add(res);
    }

}
