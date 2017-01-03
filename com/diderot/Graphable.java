package com.diderot;

import javafx.beans.property.DoubleProperty;
import javafx.scene.shape.Shape;

import java.util.List;

// Cet interface permet d'assurer les méthodes requises pour toutes shapes
interface Graphable {
    double getCentreX();
    double getCentreY();

    void setCentreX(double x);
    void setCentreY(double y);

    DoubleProperty getDpX();
    DoubleProperty getDpY();

    int getCurrentNumber();
    void setCurrentNumber(int i);

    List<GLine> getLines();
    List<Shape> getConnectedShapes();

    int getWeight();
    void setWeight(int w);
    GLabel getLabel();
}
