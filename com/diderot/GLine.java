package com.diderot;

import javafx.beans.property.DoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.List;

// GLine est une extension d'un Line et possède ainsi toutes les caractéristiques d'un shape Line.
class GLine extends Line implements Graphable {
    private Shape shape_1;
    private Shape shape_2;
    private int weight;
    private Color color;
    private GLabel label;

    GLine(Shape shape_1, Shape shape_2, int weight) {
        this.shape_1 = shape_1;
        this.shape_2 = shape_2;
        this.weight = weight;

        setStrokeWidth(3);
        setStroke(Color.DARKRED);
        setStrokeLineCap(StrokeLineCap.BUTT);

        // On lie ici les coordonnées X et Y du début de la ligne aux
        // coordonnées X et Y de premier shape
        startXProperty().bind(((Graphable) shape_1).getDpX());
        startYProperty().bind(((Graphable) shape_1).getDpY());

        // On lie ici les coordonnées X et Y de la fin de la ligne aux
        // coordonnées X et Y de deuxième shape
        endXProperty().bind(((Graphable) shape_2).getDpX());
        endYProperty().bind(((Graphable) shape_2).getDpY());

        ((Graphable) shape_1).getLines().add(this);
        ((Graphable) shape_2).getLines().add(this);

        this.label = new GLabel(this);

        // On format le label de la ligne si celle-ci est selectionnée
        setOnMouseEntered(mouseEvent -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                getLabel().setTextFill(Color.LIGHTPINK);
                getLabel().setFont(Font.font(null, FontWeight.BOLD, 12));
            }
        });

        setOnMouseExited(mouseEvent -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                getLabel().setTextFill(Color.BLACK);
                getLabel().setFont(Font.font(null, FontWeight.NORMAL, 12));
            }
        });
    }

    Shape getShape_1() { return shape_1; }
    Shape getShape_2() {
        return shape_2;
    }

    @Override
    public double getCentreX() {
        return 0;
    }

    @Override
    public double getCentreY() {
        return 0;
    }

    @Override
    public void setCentreX(double x) { }

    @Override
    public void setCentreY(double y) { }

    @Override
    public DoubleProperty getDpX() {
        return new GLineCenter(this).getCenterX();
    }

    @Override
    public DoubleProperty getDpY() {
        return new GLineCenter(this).getCenterY();
    }

    @Override
    public int getCurrentNumber() {
        return -1;
    }

    @Override
    public void setCurrentNumber(int i) { }

    @Override
    public List<GLine> getLines() {
        return null;
    }

    @Override
    public List<Shape> getConnectedShapes() {
        return null;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public GLabel getLabel() { return label; }

    void setColor(Color color) {
        this.color = color;
    }

    public String toString() {
        return "Line";
    }
}