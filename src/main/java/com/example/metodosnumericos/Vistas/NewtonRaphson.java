package com.example.metodosnumericos.Vistas;
//
//import javafx.geometry.Pos;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.function.Function;
public class NewtonRaphson extends Stage {
    private TextField functionField;
    private TextField derivativeField;
    private TextField initialValueField;
    private TextField errorField;
    private TableView<IterationResult> tableView;

    public NewtonRaphson() {
        setTitle("Newton-Raphson Solver");

        BorderPane root = new BorderPane();

        GridPane inputGrid = new GridPane();
        inputGrid.setAlignment(Pos.CENTER);
        inputGrid.setHgap(10);
        inputGrid.setVgap(10);
        inputGrid.setPadding(new Insets(25, 25, 25, 25));

        Label sceneTitle = new Label("Newton-Raphson Solver");
        sceneTitle.setFont(Font.font(20));
        inputGrid.add(sceneTitle, 0, 0, 2, 1);

        Label functionLabel = new Label("Ingrese la función f(x) en términos de x:");
        inputGrid.add(functionLabel, 0, 1);
        functionField = new TextField();
        inputGrid.add(functionField, 1, 1);

        Label derivativeLabel = new Label("Ingrese la derivada de la función f'(x) en términos de x:");
        inputGrid.add(derivativeLabel, 0, 2);
        derivativeField = new TextField();
        inputGrid.add(derivativeField, 1, 2);

        Label initialValueLabel = new Label("Ingrese el valor inicial para x:");
        inputGrid.add(initialValueLabel, 0, 3);
        initialValueField = new TextField();
        inputGrid.add(initialValueField, 1, 3);

        Label errorLabel = new Label("Ingrese el error permitido:");
        inputGrid.add(errorLabel, 0, 4);
        errorField = new TextField();
        inputGrid.add(errorField, 1, 4);

        Button solveButton = new Button("Resolver");
        solveButton.setOnAction(e -> solve());
        inputGrid.add(solveButton, 1, 5);

        tableView = new TableView<>();
        TableColumn<IterationResult, Double> xiColumn = new TableColumn<>("xi");
        xiColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getXi()).asObject());
        TableColumn<IterationResult, Double> fxiColumn = new TableColumn<>("f(xi)");
        fxiColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getFxi()).asObject());
        TableColumn<IterationResult, Double> fpxiColumn = new TableColumn<>("f'(xi)");
        fpxiColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getFpxi()).asObject());
        TableColumn<IterationResult, Double> xnextColumn = new TableColumn<>("xi+1");
        xnextColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getXnext()).asObject());
        TableColumn<IterationResult, Double> errorColumn = new TableColumn<>("Error");
        errorColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getError()).asObject());
        tableView.getColumns().addAll(xiColumn, fxiColumn, fpxiColumn, xnextColumn, errorColumn);
//        TableColumn<IterationResult, Integer> iterationColumn = new TableColumn<>("Iteración");
//        iterationColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIteration()).asObject());
//        TableColumn<IterationResult, Double> xColumn = new TableColumn<>("x");
//        xColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getX()).asObject());
//        tableView.getColumns().addAll(iterationColumn, xColumn);

        root.setCenter(inputGrid);
        root.setBottom(tableView);

        Scene scene = new Scene(root, 600, 400);
        setScene(scene);
        show();
    }

    private void solve() {
        String functionString = functionField.getText();
        String derivativeString = derivativeField.getText();
        double initialValue = Double.parseDouble(initialValueField.getText());
        double error = Double.parseDouble(errorField.getText());

        Function<Double, Double> function = x -> evaluateFunction(functionString, x);
        Function<Double, Double> derivative = x -> evaluateFunction(derivativeString, x);

        ObservableList<IterationResult> iterationResults = FXCollections.observableArrayList();
        double result = findRoot(function, derivative, initialValue, error, iterationResults);

        tableView.setItems(iterationResults);
        tableView.refresh();

        // Show the result label
        Label resultLabel = new Label("La raíz encontrada es: " + result);
        BorderPane.setAlignment(resultLabel, Pos.CENTER);
        ((BorderPane) getScene().getRoot()).setTop(resultLabel);
    }

    private double evaluateFunction(String functionString, double x) {
        // Implementation remains the same
        return parseExpression(functionString.replaceAll("x", Double.toString(x)));
    }
    private double parseExpression(String expression) {
            return new Object() {
                int pos = -1, ch;

                void nextChar() {
                    ch = (++pos < expression.length()) ? expression.charAt(pos) : -1;
                }

                boolean eat(int charToEat) {
                    while (ch == ' ') nextChar();
                    if (ch == charToEat) {
                        nextChar();
                        return true;
                    }
                    return false;
                }

                double parse() {
                    nextChar();
                    double x = parseExpressionLevel1();
                    if (pos < expression.length()) throw new RuntimeException("Caracter inesperado: " + (char) ch);
                    return x;
                }

                double parseExpressionLevel1() {
                    double x = parseExpressionLevel2();
                    for (;;) {
                        if (eat('+')) x += parseExpressionLevel2(); // adición
                        else if (eat('-')) x -= parseExpressionLevel2(); // sustracción
                        else return x;
                    }
                }

                double parseExpressionLevel2() {
                    double x = parseExpressionLevel3();
                    for (;;) {
                        if (eat('*')) x *= parseExpressionLevel3(); // multiplicación
                        else if (eat('/')) x /= parseExpressionLevel3(); // división
                        else return x;
                    }
                }

                double parseExpressionLevel3() {
                    double x = parseExpressionLevel4();
                    for (;;) {
                        if (eat('^')) x = Math.pow(x, parseExpressionLevel4()); // exponente
                        else return x;
                    }
                }

                double parseExpressionLevel4() {
                    if (eat('+')) return parseExpressionLevel4(); // + operador unario
                    if (eat('-')) return -parseExpressionLevel4(); // - operador unario
                    double x;
                    int startPos = this.pos;
                    if (eat('(')) { // paréntesis
                        x = parseExpressionLevel1();
                        eat(')');
                    } else if ((ch >= '0' && ch <= '9') || ch == '.') { // números
                        while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                        x = Double.parseDouble(expression.substring(startPos, this.pos));
                    } else {
                        throw new RuntimeException("Caracter inesperado: " + (char) ch);
                    }
                    return x;
                }
            }.parse();
    }

    private double findRoot(Function<Double, Double> function, Function<Double, Double> derivative, double initialValue, double error, ObservableList<IterationResult> iterationResults) {
        double xi = initialValue;
        double xiNext;
        double fxi;
        double fpxi;
        double errorValue;

        do {
            fxi = function.apply(xi);
            fpxi = derivative.apply(xi);
            xiNext = xi - fxi / fpxi;
            errorValue = Math.abs((xiNext-xi)/xiNext)*100;
            iterationResults.add(new IterationResult(xi, fxi, fpxi, xiNext, errorValue));
            xi = xiNext;
        } while (errorValue > error);

        return xiNext;
    }
    }
//        double x0 = initialValue;
//        double x1;
//        int iterations = 0;
//
//        do {
//            x1 = x0 - function.apply(x0) / derivative.apply(x0);
//            double relativeError = Math.abs((x1 - x0) / x1) * 100;
//            iterationResults.add(new IterationResult(xi, fxi, fpxi, xiNext, errorValue));
//            //iterationResults.add(new IterationResult(iterations, x1)); // Add iteration result
//            if (relativeError < error) {
//                break;
//            }
//            x0 = x1;
//            iterations++;
//        } while (true);
//
//        return x1;
//    }

     class IterationResult {
        private final double xi;
        private final double fxi;
        private final double fpxi;
        private final double xnext;
        private final double error;

        public IterationResult(double xi, double fxi, double fpxi, double xnext, double error) {
            this.xi = xi;
            this.fxi = fxi;
            this.fpxi = fpxi;
            this.xnext = xnext;
            this.error = error;
        }

        public double getXi() {
            return xi;
        }

        public double getFxi() {
            return fxi;
        }

        public double getFpxi() {
            return fpxi;
        }

        public double getXnext() {
            return xnext;
        }

        public double getError() {
            return error;
        }
    }

//        private final int iteration;
//        private final double x;
//
//        public IterationResult(int iteration, double x) {
//            this.iteration = iteration;
//            this.x = x;
//        }
//
//        public int getIteration() {
//            return iteration;
//        }
//
//        public double getX() {
//            return x;
//        }
//    }
//}
//    private Scene escena;
//    private VBox vbContenedor, vbSup, vbIzq, vbDer;
//    private HBox hbEc, hbInf, hbXi, hbep, hbDerivada;
//    private Label lblTitle, lblEc, lblXi, lblep, lblDerivada;
//    private TextField txtec, txtXi, txtep, txtDerivada;
//    private Button btnGraficar, btnCalcular, btnAyuda;
//
//    public NewtonRaphson() {
//        CrearUI();
//        this.setTitle("Newton-Raphson");
//        this.setScene(escena);
//        this.show();
//        this.setWidth(450);
//        this.setHeight(350);
//    }
//
//    private void CrearUI() {
//        lblTitle = new Label("Newton-Raphson");
//        lblEc = new Label("Ecuación");
//        lblDerivada = new Label("Derivada");
//        txtec = new TextField("");
//        txtDerivada = new TextField("");
//        hbEc = new HBox(lblEc, txtec);
//        hbEc.setSpacing(9);
//        hbDerivada = new HBox(lblDerivada, txtDerivada);
//        vbSup = new VBox(lblTitle, hbEc, hbDerivada);
//        vbSup.setAlignment(Pos.CENTER);
//        vbSup.setSpacing(20);
//
//        btnGraficar = new Button("Graficar");
//        lblXi = new Label("Xi: ");
//        txtXi = new TextField("");
//        hbXi = new HBox(lblXi, txtXi);
//        lblep = new Label("ep: ");
//        txtep = new TextField("");
//        hbep = new HBox(lblep, txtep);
//        vbIzq = new VBox(btnGraficar, hbXi, hbep);
//        vbIzq.setAlignment(Pos.CENTER_LEFT);
//        vbIzq.setSpacing(20);
//
//        btnCalcular = new Button("Calcular");
//        btnAyuda = new Button("Ayuda");
//        vbDer = new VBox(btnCalcular, btnAyuda);
//        vbDer.setAlignment(Pos.CENTER_RIGHT);
//        vbDer.setSpacing(20);
//
//        hbInf = new HBox(vbIzq, vbDer);
//        hbInf.setSpacing(60);
//
//        vbContenedor = new VBox(vbSup, hbInf);
//        vbContenedor.setSpacing(20);
//        escena = new Scene(vbContenedor);
//    }
//}

