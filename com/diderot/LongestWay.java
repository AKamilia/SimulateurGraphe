package com.diderot;

import java.util.ArrayList;
import static javafx.scene.paint.Color.YELLOW;

class LongestWay extends GAlgorithm {
    LongestWay(Graphable shape) {
        System.out.println(startEngine(shape, new ArrayList<>()));
        clean();
    }

    private ArrayList<Object> startEngine(Graphable shape, ArrayList<Object> visitedNodes) {
        visitedNodes.add(shape);
        Graphable nextShape = null;
        ((Colorable) shape).setColor(YELLOW);

        createGView();

        for (GLine line : shape.getLines()) {
            if (line.getShape_1() == shape) { nextShape = (Graphable) line.getShape_2(); }
            if (line.getShape_2() == shape) { nextShape = (Graphable) line.getShape_1(); }
            if (visitedNodes.contains(nextShape)) continue;

            visitedNodes.add(nextShape);
            startEngine(nextShape, visitedNodes);
        }

        return visitedNodes;
    }
}
