package com.diderot;

import javafx.beans.property.DoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

class GRectangle extends Rectangle implements Graphable, Colorable {
    private int currentNumber;
    private Color color;
    private static int count = 0;
    private ArrayList<GLine> lines = new ArrayList<>();
    private int weight;
    private GLabel label;

    GRectangle(double x, double y, int weight) {
        super(x, y, 20, 20);

        setColor(Color.BLACK);
        setCurrentNumber(count++);
        setWeight(weight);

        this.label = new GLabel(this);
        new Draggable(this);
    }

    static void setCount(int count) {
        GRectangle.count = count;
    }

    public String toString() {
        return "Rectangle " + currentNumber;
    }

    public int getCurrentNumber() {
        return currentNumber;
    }

    @Override
    public List<GLine> getLines() {
        return lines;
    }

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

    public void setColor(Color color) {
        this.color = color;
        setStroke(color);
        setFill(color);
    }

    @Override
    public Color getColor() { return color; }

    @Override
    public double getCentreX() {
        return getX();
    }

    @Override
    public double getCentreY() {
        return getY();
    }

    @Override
    public void setCentreX(double x) {
        setX(x);
    }

    @Override
    public void setCentreY(double y) {
        setY(y);
    }

    @Override
    public DoubleProperty getDpX() { return xProperty(); }

    @Override
    public DoubleProperty getDpY() { return yProperty(); }

    @Override
    public void setCurrentNumber(int currentNumber) { this.currentNumber = currentNumber; }

    public GLabel getLabel() {
        return label;
    }
}
