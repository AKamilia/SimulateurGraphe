package com.diderot;

import javafx.geometry.Insets;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import static javafx.geometry.Pos.BASELINE_RIGHT;

class Player extends ToolBar {

    Player() {
        final ImageView imageView1 = new ImageView(new Image("/action_1.png", 40, 40, false, false));
        final ImageView imageView2 = new ImageView(new Image("/action_2.png", 40, 40, false, false));
        final ImageView imageView3 = new ImageView(new Image("/action_3.png", 40, 40, false, false));

        final ToggleButton toggle1 = new ToggleButton(null, imageView1);
        final ToggleButton toggle2 = new ToggleButton(null, imageView2);
        final ToggleButton toggle3 = new ToggleButton(null, imageView3);

        HBox hbox = new HBox();
        VBox vbox = new VBox();

        VBox.setMargin(hbox, new Insets(0, 0, 0, 400));
        hbox.getChildren().addAll(toggle1, toggle2, toggle3);
        hbox.setAlignment(BASELINE_RIGHT);

        vbox.getChildren().add(hbox);
        this.getItems().addAll(vbox);

        toggle1.setOnMousePressed((MouseEvent event) -> {
            Main.decrementCurrentGraphViewIndex();
            Main.setGraphViewList();
        });

        toggle2.setOnMousePressed((MouseEvent event) -> {
            Main.clearCount();
            Main.clearList();
            Main.addCurrentGraphView();

            switch (MenuTop.choice)  {
                case 1 : new LongestWay(findFirstShape()); break;
                case 0 : new LongestWayWeight(findFirstShape()); break;
                case 2 : new MinimumWeight(findFirstShape()); break;
            }
        });

        // On incrémente l'état courant et on dessine ce dernier
        toggle3.setOnMousePressed((MouseEvent event) -> {
            Main.incrementCurrentGraphViewIndex();
            Main.setGraphViewList();
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

    CanvasCenter getCanvas() {
        return Main.canvas;
    }
}
