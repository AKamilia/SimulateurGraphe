package com.diderot;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

// Cette classe permet de calculer le centre d'une Gline
// pour pouvoir permettre au label de suivre la ligne lors du déplacement
class GLineCenter {
    private DoubleProperty centerX = new SimpleDoubleProperty();
    private DoubleProperty centerY = new SimpleDoubleProperty();

    GLineCenter(GLine line) {
        calculateCenter(line);
        line.boundsInLocalProperty().addListener((observable, oldValue, newValue) -> calculateCenter(line));
    }

    private void calculateCenter(GLine line) {
        centerX.setValue((line.getStartX() + line.getEndX()) / 2.0);
        centerY.setValue((line.getStartY() + line.getEndY()) / 2.0);
    }

    DoubleProperty getCenterX() { return centerX; }
    DoubleProperty getCenterY() { return centerY; }
}
