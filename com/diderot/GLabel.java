package com.diderot;

import javafx.scene.control.Label;

import static com.diderot.Main.canvas;

// GLabel est une extension d'un Label et possède ainsi toutes les caractéristiques d'un shape Label.
class GLabel extends Label {
    GLabel(Graphable shape) {
        super("        " + shape.toString() + " (w: " + shape.getWeight() + ")");

        // On lie les coordonnées du label à celui de son Graphable associé
        layoutXProperty().bind(shape.getDpX());
        layoutYProperty().bind(shape.getDpY());

        canvas.getChildren().add(this);
    }
}
