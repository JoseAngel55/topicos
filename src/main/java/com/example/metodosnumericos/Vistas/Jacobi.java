package com.example.metodosnumericos.Vistas;
//
//import javafx.scene.Scene;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//
//public class Jacobi extends Stage {
//    private Scene escena;
//    private Button btnAceptar, btnCalcular, btnAyuda;
//    private TextField[] vectorB, vectorX;
//    private TextField[][] matrizEc;
//    private TextField error;
//    private HBox hbsup;
//
//    public Jacobi() {
            import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

        public class Jacobi extends Stage {

            private TextField variablesField;
            private TextField ecuacionesField;
            private TextArea ecuacionesTextArea;
            private TextField initialGuessField;
            private TextField toleranceField;
            private TextArea resultTextArea;

            public Jacobi() {
                setTitle("Jacobi Method Solver");

                // GridPane layout
                GridPane grid = new GridPane();
                grid.setAlignment(Pos.CENTER);
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(25, 25, 25, 25));

                // Variables label and field
                Label variablesLabel = new Label("Número de variables:   Maestra, lo intentamos pero lo dejamos asi por nuestra salud mental :)");
                variablesField = new TextField();
                grid.add(variablesLabel, 0, 0);
                grid.add(variablesField, 1, 0);

                // Ecuaciones label and field
                Label ecuacionesLabel = new Label("Ecuaciones:");
                ecuacionesField = new TextField();
                grid.add(ecuacionesLabel, 0, 1);
                grid.add(ecuacionesField, 1, 1);

                // Ecuaciones textarea
                ecuacionesTextArea = new TextArea();
                ecuacionesTextArea.setPromptText("Ingrese las ecuaciones aquí");
                ecuacionesTextArea.setPrefRowCount(4);
                grid.add(ecuacionesTextArea, 0, 2, 2, 1);

                // Initial guess label and field
                Label initialGuessLabel = new Label("Estimaciones iniciales:");
                initialGuessField = new TextField();
                grid.add(initialGuessLabel, 0, 3);
                grid.add(initialGuessField, 1, 3);

                // Tolerance label and field
                Label toleranceLabel = new Label("Error permitido:");
                toleranceField = new TextField();
                grid.add(toleranceLabel, 0, 4);
                grid.add(toleranceField, 1, 4);

                // Solve button
                Button solveButton = new Button("Resolver");
                VBox vbox = new VBox(10);
                vbox.getChildren().addAll(solveButton);
                vbox.setAlignment(Pos.CENTER);
                grid.add(vbox, 0, 5, 2, 1);

                // Result textarea
                resultTextArea = new TextArea();
                resultTextArea.setEditable(false);
                resultTextArea.setPrefRowCount(10);
                resultTextArea.setPromptText("Resultados aquí");
                grid.add(resultTextArea, 0, 6, 2, 1);

                // Event handler for solve button
                solveButton.setOnAction(e -> solve());

                Scene scene = new Scene(grid, 600, 400);
                setScene(scene);
                this.show();
            }

            private void solve() {
                int variables = Integer.parseInt(variablesField.getText());
                int ecuaciones = Integer.parseInt(ecuacionesField.getText());
                String[] ecuacionesLines = ecuacionesTextArea.getText().split("\n");
                double[][] coefficients = new double[ecuaciones][variables];
                double[] constants = new double[ecuaciones];
                for (int i = 0; i < ecuaciones; i++) {
                    String[] parts = ecuacionesLines[i].split(" ");
                    for (int j = 0; j < variables; j++) {
                        coefficients[i][j] = Double.parseDouble(parts[j]);
                    }
                    constants[i] = Double.parseDouble(parts[variables]);
                }
                String[] initialGuessParts = initialGuessField.getText().split(" ");
                double[] initialGuess = new double[variables];
                for (int i = 0; i < variables; i++) {
                    initialGuess[i] = Double.parseDouble(initialGuessParts[i]);
                }
                double tolerance = Double.parseDouble(toleranceField.getText());

                double[] solution = jacobi(coefficients, constants, initialGuess, tolerance);

                // Display solution
                StringBuilder result = new StringBuilder("La solución es:\n");
                for (int i = 0; i < solution.length; i++) {
                    result.append("x[").append(i).append("] = ").append(solution[i]).append("\n");
                }
                resultTextArea.setText(result.toString());
            }

            public static double[] jacobi(double[][] coefficients, double[] constants, double[] initialGuess,
                                          double tolerance) {
                int ecuaciones = constants.length;
                int variables = initialGuess.length;
                double[] solution = new double[variables];
                double[] newSolution = new double[variables];
                double[] error = new double[variables];
                double maxError;

                int iteration = 0;
                System.out.println("Iteración " + iteration + ":");
                printSolution(initialGuess);

                // Iterar hasta que se satisfaga la tolerancia
                do {
                    // Calcular nuevas soluciones
                    for (int i = 0; i < variables; i++) {
                        double sum = constants[i];
                        for (int j = 0; j < variables; j++) {
                            if (j != i) {
                                sum -= coefficients[i][j] * initialGuess[j];
                            }
                        }
                        newSolution[i] = sum / coefficients[i][i];
                    }

                    // Calcular el error
                    maxError = 0;

                    for (int i = 0; i < variables; i++) {
                        error[i] = Math.abs(newSolution[i] - initialGuess[i]);
                        if (error[i] > maxError) {
                            maxError = error[i];
                        }
                    }

                    // Mostrar las soluciones de la iteración
                    iteration++;
                    System.out.println("Iteración " + iteration + ":");
                    printSolution(newSolution);

                    // Actualizar las soluciones
                    for (int i = 0; i < variables; i++) {
                        initialGuess[i] = newSolution[i];
                    }

                } while (maxError > tolerance);

                // Devolver la solución
                return initialGuess;
            }

            public static void printSolution(double[] solution) {
                for (int i = 0; i < solution.length; i++) {
                    System.out.println("x[" + i + "] = " + solution[i]);
                }
            }
        }

//        CrearUI();
//        this.setTitle("Jacobi");
//        this.setScene(escena);
//        this.show();
//    }
//
//    private void CrearUI() {
//
//    }


