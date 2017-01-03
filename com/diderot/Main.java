package com.diderot;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    static CanvasCenter canvas = new CanvasCenter();

    private static List<GView> graphViewList = new ArrayList<>();

    // Représente l'index du GView courant
    private static int currentGraphViewIndex = 0;

    public void start(Stage primaryStage) throws Exception {
        BorderPane border = new BorderPane();

        border.setPrefSize(1000, 600);
        border.setTop(new MenuTop());
        border.setBottom(new Player());
        border.setLeft(new Tools());
        border.setCenter(canvas);

        Scene scene = new Scene(border, 1000, 600);

        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.setTitle("Editeur de Graphes");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    static void addCurrentGraphView() {
        GView gv = new GView();

        for (int i = 0; i < canvas.getChildren().size(); i++) {
            Node node = getCanvas().getChildren().get(i);

            if (node instanceof GLine) {
                GViewLink gvl = new GViewLink(
                        ((Graphable) ((GLine) node).getShape_1()).getCurrentNumber(),
                        ((Graphable) ((GLine) node).getShape_2()).getCurrentNumber(),
                        ((GLine) node).getShape_1().getClass().toString(),
                        ((GLine) node).getShape_2().getClass().toString(),
                        ((GLine) node).getWeight()
                );

                gv.getLinks().add(gvl);
            }

            if ((node instanceof GCircle) || (node instanceof GRectangle)) {
                GViewNode gvn = new GViewNode(
                        ((Graphable) node).getDpX().get(),
                        ((Graphable) node).getDpY().get(),
                        ((Graphable) node).getWeight(),
                        ((Colorable) node).getColor(),
                        node.getClass().toString()
                );

                gv.getNodes().add(gvn);
            }
        }

        graphViewList.add(gv);
    }

    static void setGraphViewList() {
        clearChildren();
        clearNodesCount();
        GView gv = graphViewList.get(currentGraphViewIndex);

        for (int j = 0; j < gv.getNodes().size(); j++) {
            GViewNode gvn = gv.getNodes().get(j);

            if (gvn.getType().equals("class com.diderot.GCircle")) {
                GCircle shape = new GCircle(gvn.getX(), gvn.getY(), gvn.getWeight());
                shape.setColor(gvn.getColor());
                canvas.getChildren().add(shape);
            }

            if (gvn.getType().equals("class com.diderot.GRectangle")) {
                GRectangle shape = new GRectangle(gvn.getX(), gvn.getY(), gvn.getWeight());
                shape.setColor(gvn.getColor());
                canvas.getChildren().add(shape);
            }
        }

        for (int j = 0; j < gv.getLinks().size(); j++) {
            GViewLink gvl = gv.getLinks().get(j);

            Shape shape_1 = getShapeByIdAndType(gvl.getShape_1_id(), gvl.getShape_1_class());
            Shape shape_2 = getShapeByIdAndType(gvl.getShape_2_id(), gvl.getShape_2_class());

            GLine line = new GLine(shape_1, shape_2, gvl.getWeight());
            canvas.getChildren().add(line);
        }
    }

    private static Shape getShapeByIdAndType(int shape_id, String shape_class) {
        for (int i = 0; i < getCanvas().getChildren().size(); i++) {
            Node node = getCanvas().getChildren().get(i);

            boolean instance_condition_1 = node instanceof GCircle;
            boolean instance_condition_2 = node instanceof GRectangle;

            if (instance_condition_1 || instance_condition_2) {
                boolean condition_1 = node.getClass().toString().equals(shape_class);
                boolean condition_2 = ((Graphable) node).getCurrentNumber() == shape_id;

                if (condition_1 && condition_2) {
                    return (Shape) node;
                }
            }
        }

        return null;
    }

    static void clearChildren() {
        canvas.getChildren().clear();
    }

    static void clearCount() {
        setCurrentGraphViewIndex(0);
        clearNodesCount();
    }

    private static void clearNodesCount() {
        GCircle.setCount(0);
        GRectangle.setCount(0);
    }

    static void clearList() {
        graphViewList.clear();
    }

    private static CanvasCenter getCanvas() {
        return canvas;
    }

    private static List<GView> getGraphViewList() {
        return graphViewList;
    }

    private static void setCurrentGraphViewIndex(int index) {
        System.out.println("Setting index to: " + currentGraphViewIndex);
        currentGraphViewIndex = index;
    }

    static void incrementCurrentGraphViewIndex() {
        if (currentGraphViewIndex + 1 < getGraphViewList().size()) {
            System.out.println("incrementCurrentGraphViewIndex current index: " + currentGraphViewIndex++);
            System.out.println("incrementCurrentGraphViewIndex current index: " + currentGraphViewIndex);
        }
    }

    static void decrementCurrentGraphViewIndex() {
        if (currentGraphViewIndex > 0) {
            System.out.println("decrementCurrentGraphViewIndex current index: " + currentGraphViewIndex--);
            System.out.println("decrementCurrentGraphViewIndex current index: " + currentGraphViewIndex);
        }
    }
}
