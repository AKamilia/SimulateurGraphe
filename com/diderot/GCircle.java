package com.diderot;

import javafx.beans.property.DoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

// GCircle est une extension d'un Circle et possède ainsi toutes les caractéristiques d'un shape Circle.
class GCircle extends Circle implements Graphable, Colorable {
    private static int count = 0;
    private int currentNumber;
    private Color color;
    private List<GLine> lines = new ArrayList<>();
    private int weight;
    private GLabel label;

    GCircle(Double x, Double y, int weight) {
        super(x, y, 10);

        setColor(Color.BLACK);
        setCurrentNumber(count++);
        setWeight(weight);
        this.label = new GLabel(this);
        new Draggable(this);
    }

    public String toString() {
        return "Cercle " + currentNumber;
    }

    public int getCurrentNumber() {
        return currentNumber;
    }

    static void setCount(int count) {
        GCircle.count = count;
    }

    @Override
    public List<GLine> getLines() {
        return lines;
    }

    // Retourne les shapes connectés au shape courant
    @Override
    public List<Shape> getConnectedShapes() {
        List<Shape> list = new ArrayList<>();

        for (int i = 0; i < getLines().size(); i++) {
            Shape shape_1 = getLines().get(i).getShape_1();
            Shape shape_2 = getLines().get(i).getShape_2();

            if (!shape_1.equals(this)) { list.add(shape_1); }
            if (!shape_2.equals(this)) { list.add(shape_2); }
        }

        return list;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public void setWeight(int w) {
        this.weight = w;
    }

    @Override
    public double getCentreX() {
        return getCenterX();
    }

    @Override
    public double getCentreY() {
        return getCenterY();
    }

    @Override
    public void setCentreX(double x) {
        setCenterX(x);
    }

    @Override
    public void setCentreY(double y) {
        setCenterY(y);
    }

    // Retourne la DoubleProperty X du shape courant, pour permettre
    // de suivre les coordonnées lors du mouvement
    @Override
    public DoubleProperty getDpX() {
        return centerXProperty();
    }

    // Retourne la DoubleProperty Y du shape courant, pour permettre
    // de suivre les coordonnées lors du mouvement
    @Override
    public DoubleProperty getDpY() {
        return centerYProperty();
    }

    @Override
    public void setCurrentNumber(int currentNumber) {
        this.currentNumber = currentNumber;
    }

    public void setColor(Color color) {
        this.color = color;
        setFill(color);
    }

    @Override
    public Color getColor() { return color; }
    public GLabel getLabel() {
        return label;
    }
}
