package com.example.metodosnumericos.Vistas;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Functions;
import java.util.function.Function;
import javafx.scene.control.ScrollPane;

public class ReglaFalsaGUI extends Stage {
    private Scene escena;
    private VBox vbContenedor, vbSup, vbIzq, vbDer;
    private HBox hbEc, hbInf, hba, hbb, hbep;
    private Label lblTitle, lblEc, lbla, lblb, lblep;
    private TextField txtec, txta, txtb, txtep;
    private Button btnGraficar, btnCalcular, btnAyuda;

    public ReglaFalsaGUI(){
        CrearUI();
        this.setTitle("Regla Falsa");
        this.setScene(escena);
        this.show();
        this.setWidth(800);
        this.setHeight(800);
    }

    private void CrearUI() {
        lblTitle = new Label("Regla Falsa");
        lblEc = new Label("Ecuación");
        txtec = new TextField("");
        hbEc = new HBox(lblEc,txtec);
        vbSup = new VBox(lblTitle,hbEc);
        vbSup.setAlignment(Pos.CENTER);
        vbSup.setSpacing(20);

        btnGraficar = new Button("Graficar");
        btnGraficar.setOnAction(event -> graficarFuncion());
        lbla = new Label("a:  ");
        txta = new TextField("");
        hba = new HBox(lbla,txta);
        lblb = new Label("b:  ");
        txtb = new TextField("");
        hbb = new HBox(lblb,txtb);
        lblep = new Label("ep: ");
        txtep = new TextField("");
        hbep = new HBox(lblep,txtep);
        vbIzq = new VBox(btnGraficar,hba,hbb,hbep);
        vbIzq.setAlignment(Pos.CENTER_LEFT);
        vbIzq.setSpacing(20);

        btnCalcular = new Button("Calcular");
        btnCalcular.setOnAction(event -> rf());
        btnAyuda = new Button("Ayuda");
        vbDer = new VBox(btnCalcular,btnAyuda);
        vbDer.setAlignment(Pos.CENTER_RIGHT);
        vbDer.setSpacing(20);

        hbInf = new HBox(vbIzq, vbDer);
        hbInf.setSpacing(60);

        vbContenedor = new VBox(vbSup,hbInf);
        vbContenedor.setSpacing(20);
        escena = new Scene(vbContenedor);
    }

    private void graficarFuncion() {
        String str = txtec.getText();
        ExpressionBuilder builder = new ExpressionBuilder(str)
                .variables("x")
                .functions(Functions.getBuiltinFunction("sin"),
                        Functions.getBuiltinFunction("cos"),
                        Functions.getBuiltinFunction("tan"),
                        Functions.getBuiltinFunction("asin"),
                        Functions.getBuiltinFunction("acos"),
                        Functions.getBuiltinFunction("atan"));
        Expression expression = builder.build();

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("x");
        yAxis.setLabel("f(x)");
        final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

        lineChart.setTitle("Gráfico de la función");

        ObservableList<XYChart.Series<Number, Number>> lineChartData = FXCollections.observableArrayList();
        XYChart.Series<Number, Number> series = new XYChart.Series<>();

        for (double x = -10; x <= 10; x += 0.1) {
            double y = expression.setVariable("x", x).evaluate();
            series.getData().add(new XYChart.Data<>(x, y));
        }

        lineChartData.add(series);
        lineChart.setData(lineChartData);

        Stage stage = new Stage();
        Scene scene = new Scene(lineChart, 500, 600);
        stage.setScene(scene);
        stage.show();
    }

    private void rf(){
        String str = txtec.getText();
        String stra = txta.getText();
        String strb = txtb.getText();
        String strep = txtep.getText();
        ExpressionBuilder builder = new ExpressionBuilder(str)
                .variables("x")
                .functions(Functions.getBuiltinFunction("sin"),
                        Functions.getBuiltinFunction("cos"),
                        Functions.getBuiltinFunction("tan"),
                        Functions.getBuiltinFunction("asin"),
                        Functions.getBuiltinFunction("acos"),
                        Functions.getBuiltinFunction("atan"));
        Expression expression = builder.build();
        Function<Double, Double> funcion = x -> {
            double resultado = expression.setVariable("x", x).evaluate();
            return resultado;
        };
        double a = Double.parseDouble(stra);
        double b = Double.parseDouble(strb);
        double ep = Double.parseDouble(strep);
        int iter = 1;
        ObservableList<IteracionRF> iteraciones = FXCollections.observableArrayList();
        resolverMetodo(funcion, a, b, ep, iteraciones);

        TableView<IteracionRF> tableView = new TableView<>();
        TableColumn<IteracionRF, Integer> iteracionCol = new TableColumn<>("Iteración");
        iteracionCol.setCellValueFactory(cellData -> cellData.getValue().iteracionProperty().asObject());
        TableColumn<IteracionRF, Double> aCol = new TableColumn<>("A");
        aCol.setCellValueFactory(cellData -> cellData.getValue().aProperty().asObject());
        TableColumn<IteracionRF, Double> bCol = new TableColumn<>("B");
        bCol.setCellValueFactory(cellData -> cellData.getValue().bProperty().asObject());
        TableColumn<IteracionRF, Double> xiCol = new TableColumn<>("Xi");
        xiCol.setCellValueFactory(cellData -> cellData.getValue().xiProperty().asObject());
        TableColumn<IteracionRF, Double> fxiCol = new TableColumn<>("F(Xi)");
        fxiCol.setCellValueFactory(cellData -> cellData.getValue().fxiProperty().asObject());
        TableColumn<IteracionRF, Double> errorCol = new TableColumn<>("Error (%)");
        errorCol.setCellValueFactory(cellData -> cellData.getValue().errorProperty().asObject());

        tableView.getColumns().addAll(iteracionCol, aCol, bCol, xiCol, fxiCol, errorCol);
        tableView.setItems(iteraciones);
        tableView.setPrefSize(400,400);

        VBox vbox = new VBox(tableView);
        vbox.setSpacing(10);

        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        hbInf.getChildren().add(scrollPane);

//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("Resultado del método");
//        alert.setHeaderText(null);
//        alert.getDialogPane().setContent(tableView);
//        alert.getDialogPane().setPrefSize(700,700);
//        alert.showAndWait();
//        return alert;
    }

    private double resolverMetodo(Function<Double, Double> f, double a, double b, double ep, ObservableList<IteracionRF> iteraciones) {
        double fa, fb, xi = 0, fxi, xiant = 0;
        double error = Double.MAX_VALUE;
        int iter = 1;
        int maxIter = 100;
        while (iter <= maxIter && error > ep) {
            fa = f.apply(a);
            fb = f.apply(b);
            xi = b - ((fb * (a - b)) / (fa - fb));
            fxi = f.apply(xi);
            error = Math.abs((xi - xiant) / xi) * 100;
            iteraciones.add(new IteracionRF(iter, a, b, xi, fxi, error));
            if (fxi * fa == 0) break;
            else if (fxi * fa < 0) b = xi;
            else if (fxi * fa > 0) a = xi;
            xiant = xi;
            iter++;
        }
        return xi;
    }

}
