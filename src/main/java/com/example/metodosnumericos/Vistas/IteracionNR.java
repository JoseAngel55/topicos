package com.example.metodosnumericos.Vistas;

public class IteracionNR {
    private int iteration;
    private double x;
    private double fx;
    private double error;

    public IteracionNR(int iteration, double x, double fx, double error) {
        this.iteration = iteration;
        this.x = x;
        this.fx = fx;
        this.error = error;
    }

    public int getIteration() {
        return iteration;
    }

    public double getX() {
        return x;
    }

    public double getFx() {
        return fx;
    }

    public double getError() {
        return error;
    }
}

