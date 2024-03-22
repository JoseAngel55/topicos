package com.example.metodosnumericos.Vistas;

//import javafx.geometry.Pos;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Functions;
import java.util.function.Function;

public class NewtonRaphson extends Stage {
    private Scene scene;
    private VBox vbox;
    private HBox equationBox, parametersBox, buttonsBox;
    private Label titleLabel, equationLabel, x0Label, epsilonLabel;
    private TextField equationTextField, x0TextField, epsilonTextField;
    private Button graphButton, calculateButton, helpButton;

    public NewtonRaphson() {
        createUI();
        this.setTitle("Método de Newton-Raphson");
        this.setScene(scene);
        this.show();
        this.setWidth(450);
        this.setHeight(350);
    }

    private void createUI() {
        titleLabel = new Label("Método de Newton-Raphson");
        equationLabel = new Label("Ecuación");
        equationTextField = new TextField("");
        equationBox = new HBox(equationLabel, equationTextField);
        equationBox.setAlignment(Pos.CENTER);
        equationBox.setSpacing(20);

        graphButton = new Button("Graficar");
        x0Label = new Label("x0: ");
        x0TextField = new TextField("");
        epsilonLabel = new Label("ep: ");
        epsilonTextField = new TextField("");
        parametersBox = new HBox(graphButton, x0Label, x0TextField, epsilonLabel, epsilonTextField);
        parametersBox.setAlignment(Pos.CENTER);
        parametersBox.setSpacing(20);

        calculateButton = new Button("Calcular");
        calculateButton.setOnAction(event -> calculateNewtonRaphson());
        helpButton = new Button("Ayuda");
        buttonsBox = new HBox(calculateButton, helpButton);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.setSpacing(20);

        vbox = new VBox(titleLabel, equationBox, parametersBox, buttonsBox);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20);
        scene = new Scene(vbox);
    }

    private void calculateNewtonRaphson() {
        String equationString = equationTextField.getText();
        String x0String = x0TextField.getText();
        String epsilonString = epsilonTextField.getText();

        // Parse equation
        ExpressionBuilder builder = new ExpressionBuilder(equationString)
                .variables("x")
                .functions(Functions.getBuiltinFunction("sin"),
                        Functions.getBuiltinFunction("cos"),
                        Functions.getBuiltinFunction("tan"),
                        Functions.getBuiltinFunction("asin"),
                        Functions.getBuiltinFunction("acos"),
                        Functions.getBuiltinFunction("atan"));
        Expression expression = builder.build();

        // Parse parameters
        double x0 = Double.parseDouble(x0String);
        double epsilon = Double.parseDouble(epsilonString);

        // Solve Newton-Raphson method
        NewtonRaphsonSolver solver = new NewtonRaphsonSolver(expression, x0, epsilon);
        //Alert alert = solver.solve();
        //alert.showAndWait();
    }
    private class NewtonRaphsonSolver {
        private Expression expression;
        private double x0;
        private double epsilon;

        public NewtonRaphsonSolver(Expression expression, double x0, double epsilon) {
            this.expression = expression;
            this.x0 = x0;
            this.epsilon = epsilon;
        }

//        public Alert solve() {
//            ObservableList<IteracionNR> iterations = FXCollections.observableArrayList();
//            double x = x0;
//            double error = Double.MAX_VALUE;
//            int iter = 1;
//
//            while (error > epsilon) {
//                double fx = expression.setVariable("x", x).evaluate();
//                double dfx = expression.setVariable("x", x).differentiate("x").evaluate();
//                double xNext = x - fx / dfx;
//                error = Math.abs((xNext - x) / xNext) * 100;
//                iterations.add(new IteracionNR(iter, x, fx, error));
//                x = xNext;
//                iter++;
//            }
/*
            TableView<IteracionNR> tableView = new TableView<>();
            TableColumn<IteracionNR, Integer> iterationCol = new TableColumn<>("Iteración");
            iterationCol.setCellValueFactory(cellData -> cellData.getValue().getIterationProperty().asObject());
            TableColumn<IteracionNR, Double> xCol = new TableColumn<>("x");
            xCol.setCellValueFactory(cellData -> cellData.getValue().getXProperty().asObject());
            TableColumn<IteracionNR, Double> fxCol = new TableColumn<>("f(x)");
            fxCol.setCellValueFactory(cellData -> cellData.getValue().getFxProperty().asObject());
            TableColumn<IteracionNR, Double> errorCol = new TableColumn<>("Error (%)");
            errorCol.setCellValueFactory(cellData -> cellData.getValue().getErrorProperty().asObject());

            tableView.getColumns().addAll(iterationCol, xCol, fxCol, errorCol);
            tableView.setItems(iterations);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Resultado del método de Newton-Raphson");
            alert.setHeaderText(null);
            alert.getDialogPane().setContent(tableView);
            alert.getDialogPane().setPrefSize(800, 600);
            return alert;
        }*/
        }
    }


//    private Scene escena;
//    private VBox vbContenedor, vbSup, vbIzq, vbDer;
//    private HBox hbEc, hbInf, hbXi, hbep, hbDerivada;
//    private Label lblTitle, lblEc, lblXi, lblep, lblDerivada;
//    private TextField txtec, txtXi,  txtep, txtDerivada;
//    private Button btnGraficar, btnCalcular, btnAyuda;
//
//    public NewtonRaphson(){
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
//        txtDerivada =  new TextField("");
//        hbEc = new HBox(lblEc,txtec);
//        hbEc.setSpacing(9);
//        hbDerivada = new HBox(lblDerivada,txtDerivada);
//        vbSup = new VBox(lblTitle,hbEc, hbDerivada);
//        vbSup.setAlignment(Pos.CENTER);
//        vbSup.setSpacing(20);
//
//        btnGraficar = new Button("Graficar");
//        lblXi = new Label("Xi: ");
//        txtXi = new TextField("");
//        hbXi = new HBox(lblXi,txtXi);
//        lblep = new Label("ep: ");
//        txtep = new TextField("");
//        hbep = new HBox(lblep,txtep);
//        vbIzq = new VBox(btnGraficar,hbXi,hbep);
//        vbIzq.setAlignment(Pos.CENTER_LEFT);
//        vbIzq.setSpacing(20);
//
//        btnCalcular = new Button("Calcular");
//        btnAyuda = new Button("Ayuda");
//        vbDer = new VBox(btnCalcular,btnAyuda);
//        vbDer.setAlignment(Pos.CENTER_RIGHT);
//        vbDer.setSpacing(20);
//
//        hbInf = new HBox(vbIzq, vbDer);
//        hbInf.setSpacing(60);
//
//        vbContenedor = new VBox(vbSup,hbInf);
//        vbContenedor.setSpacing(20);
//        escena = new Scene(vbContenedor);
 //   }

