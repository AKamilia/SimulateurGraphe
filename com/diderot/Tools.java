package com.diderot;

import javafx.scene.Node;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class Tools extends VBox {
    private final ToggleGroup toggle = new ToggleGroup();
	private final ToggleButton toggle1 = new ToggleButton();
    private final ToggleButton toggle2 = new ToggleButton();
    private final ToggleButton toggle3 = new ToggleButton();

    Tools() {
        toggle1.setGraphic(new ImageView(new Image("shape_1.png", 25, 25, false, false)));
        toggle2.setGraphic(new ImageView(new Image("shape_2.png", 25, 25, false, false)));
        toggle3.setGraphic(new ImageView(new Image("shape_3.png", 25, 25, false, false)));

        toggle1.setToggleGroup(toggle);
        toggle2.setToggleGroup(toggle);
        toggle3.setToggleGroup(toggle);
     

        this.setAlignment(javafx.geometry.Pos.TOP_LEFT);
    
        this.getChildren().addAll(toggle1,toggle2,toggle3);
       
        getCanvas().setOnMouseReleased((MouseEvent event) -> {
            double x = event.getX();
            double y = event.getY();

            if (getToggle().getSelectedToggle() != null) {
                if (getToggle().getSelectedToggle().equals(toggle1)) {
                    int random = 1 + (int) (Math.random() * 25);
                    getCanvas().getChildren().add(new GCircle(x, y, random));
                } else if (getToggle().getSelectedToggle().equals(toggle2)) {
                    int random = 1 + (int) (Math.random() * 25);
                    getCanvas().getChildren().add(new GRectangle(x, y, random));
                } else if (getToggle().getSelectedToggle().equals(toggle3)) {
                    List<String> choices = new ArrayList<>();

                    for (int i = 0; i < getCanvas().getChildren().size(); i++) {
                        Node child_1 = getCanvas().getChildren().get(i);

                        for (int j = 0; j < getCanvas().getChildren().size(); j++) {
                            if (i == j) continue;

                            if (getCanvas().getChildren().get(i) instanceof GLine) continue;
                            if (getCanvas().getChildren().get(j) instanceof GLine) continue;
                            if (getCanvas().getChildren().get(i) instanceof GLabel) continue;
                            if (getCanvas().getChildren().get(j) instanceof GLabel) continue;

                            Node child_2 = getCanvas().getChildren().get(j);
                            choices.add(child_1.toString() + " --> " + child_2.toString());
                        }
                    }

                    ChoiceDialog<String> dialog = new ChoiceDialog<>(null, choices);

                    dialog.setTitle("Formulaire");
                    dialog.setHeaderText("Utilser le formulaire suivant pour associer deux Noeuds.");
                    dialog.setContentText("Choisissez une option:");

                    Optional<String> result = dialog.showAndWait();

                    if (result.isPresent()){
                        String choice_1 = result.get().split(" --> ")[0];
                        String choice_2 = result.get().split(" --> ")[1];

                        Shape selectedShape_1 = (Shape) findShapeByName(choice_1);
                        Shape selectedShape_2 = (Shape) findShapeByName(choice_2);

                        GLine line = new GLine(selectedShape_1, selectedShape_2, 1);
                        getCanvas().getChildren().add(line);
                    }
                }
		
                getToggle().selectToggle(null);
            }
        });
    }

    private Graphable findFirstShape() {
        for (int i = 0; i < getCanvas().getChildren().size(); i++) {
            if (getCanvas().getChildren().get(i) instanceof GLabel) continue;
            if (getCanvas().getChildren().get(i) instanceof GLine)  continue;

            return (Graphable) getCanvas().getChildren().get(i);
        }

        return null;
    }

    private Node findShapeByName(String choice) {
        for (int i = 0; i < getCanvas().getChildren().size(); i++) {
            if (getCanvas().getChildren().get(i) instanceof GLabel) continue;

            Shape shape = (Shape) getCanvas().getChildren().get(i);
            if (shape.toString().equals(choice)) {
                return shape;
            }
        }

        return null;
    }

    private CanvasCenter getCanvas() { return Main.canvas; }
    private ToggleGroup getToggle() { return toggle; }
}
