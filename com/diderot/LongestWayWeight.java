package com.diderot;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

class LongestWayWeight extends GAlgorithm {
    LongestWayWeight(Graphable shape) {
        System.out.println(startEngine(shape, new ArrayList<>()));
        clean();
    }

    private int startEngine(Graphable shape, List<Graphable> visitedNodes) {
        visitedNodes.add(shape);

        ((Colorable) shape).setColor(Color.PINK);
        createGView();

        Graphable nextShape = null;
        int maxWeight = 0;

        for (GLine line : shape.getLines()) {
            if (line.getShape_1() == shape) { nextShape = (Graphable) line.getShape_2(); }
            if (line.getShape_2() == shape) { nextShape = (Graphable) line.getShape_1(); }
            if (visitedNodes.contains(nextShape)) continue;

            System.out.println(nextShape);

            visitedNodes.add(nextShape);
            maxWeight = Math.max(maxWeight, startEngine(nextShape, visitedNodes));
        }

        return maxWeight + shape.getWeight();
    }
}
