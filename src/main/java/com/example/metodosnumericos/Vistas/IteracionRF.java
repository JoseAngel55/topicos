package com.example.metodosnumericos.Vistas;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class IteracionRF {
    private final IntegerProperty iteracion;
    private final DoubleProperty a;
    private final DoubleProperty b;
    private final DoubleProperty xi;
    private final DoubleProperty fxi;
    private final DoubleProperty error;

    public IteracionRF(int iteracion, double a, double b, double xi, double fxi, double error) {
        this.iteracion = new SimpleIntegerProperty(iteracion);
        this.a = new SimpleDoubleProperty(a);
        this.b = new SimpleDoubleProperty(b);
        this.xi = new SimpleDoubleProperty(xi);
        this.fxi = new SimpleDoubleProperty(fxi);
        this.error = new SimpleDoubleProperty(error);
    }

//    public int getIteracion() {
//        return iteracion.get();
//    }

    public IntegerProperty iteracionProperty() {
        return iteracion;
    }

//    public double getA() {
//        return a.get();
//    }

    public DoubleProperty aProperty() {
        return a;
    }

    public double getB() {
        return b.get();
    }

    public DoubleProperty bProperty() {
        return b;
    }

    public double getXi() {
        return xi.get();
    }

    public DoubleProperty xiProperty() {
        return xi;
    }

    public double getFxi() {
        return fxi.get();
    }

    public DoubleProperty fxiProperty() {
        return fxi;
    }

    public double getError() {
        return error.get();
    }

    public DoubleProperty errorProperty() {
        return error;
    }
}
