package com.diderot;


import javafx.scene.paint.Color;

class GViewNode {
    private double x, y;
    private int weight;
    private Color color;
    private String type;

    GViewNode(double x, double y, int weight, Color color, String type) {
        this.x = x;
        this.y = y;
        this.weight = weight;
        this.color = color;
        this.type = type;
    }

    double getX() {
        return x;
    }
    double getY() {
        return y;
    }
    int getWeight() {
        return weight;
    }

    Color getColor() {
        return color;
    }
    String getType() {
        return type;
    }
}
