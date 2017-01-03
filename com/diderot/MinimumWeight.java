package com.diderot;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

import static javafx.scene.paint.Color.RED;

class MinimumWeight extends GAlgorithm {
    private List<Graphable> list = new ArrayList<>();

    MinimumWeight(Graphable shape) {
        System.out.println("Le poid le plus faible est " + startEngine(shape));
        clean();
    }

    private int startEngine(Graphable shape) {
        if (getList().contains(shape)) { return 30; }

        getList().add(shape);
        ((Colorable) shape).setColor(Color.PINK);

        // Cette méthode permet de sauvegarder l'état actuel du graphe
        createGView();

        int minimumWeight = shape.getWeight();
        for (int i = 0; i < shape.getLines().size(); i++) {
            GLine currentLine = shape.getLines().get(i);
            currentLine.setColor(RED);

            minimumWeight = Math.min(minimumWeight, startEngine((Graphable) currentLine.getShape_1()));
            minimumWeight = Math.min(minimumWeight, startEngine((Graphable) currentLine.getShape_2()));
        }

        return minimumWeight;
    }

    private List<Graphable> getList() {
        return list;
    }
}
