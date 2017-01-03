package com.diderot;

import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/*
    Cette classe permet de rendre d'importe quel objet de stype Shape
    déplacable sans à voir à répéter le code dans plusieurs classes.

    Cette classe gère aussi le coloriage et la mise en gras du label associé.
 */
class Draggable {
    private DraggableDelta dragDelta = new DraggableDelta();

    /* Constructeur qui prend en argument un Shape */
    Draggable(Shape shape) {
        shape.setOnMouseReleased(mouseEvent -> shape.getScene().setCursor(Cursor.HAND));

        /*
           A l'entrée de la souris, cette méthode transforme le type de curseur
           et mets la shape en couleur blue. Elle ajuste aussi le format du label.
         */
        shape.setOnMouseEntered(mouseEvent -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                shape.getScene().setCursor(Cursor.HAND);
                shape.setFill(Color.BLUE);

                ((Graphable) shape).getLabel().setTextFill(Color.LIGHTPINK);
                ((Graphable) shape).getLabel().setFont(Font.font(null, FontWeight.BOLD, 12));
            }
        });

        /*
            A la sortie du périmetre du shape, on remet le curseur au format initial
            ainsi que la couleur et le format du label.
         */
        shape.setOnMouseExited(mouseEvent -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                shape.getScene().setCursor(Cursor.DEFAULT);
                ((Colorable) shape).setColor(((Colorable) shape).getColor());

                ((Graphable) shape).getLabel().setTextFill(Color.BLACK);
                ((Graphable) shape).getLabel().setFont(Font.font(null, FontWeight.NORMAL, 12));
            }
        });

        /*
            Une fois la souris cliquée et déplacée, on enregistre la différence
            du mouvement dans un object delta qui ne stoque que le x et le y courant.
         */
        shape.setOnMousePressed(mouseEvent -> {
            dragDelta.x = ((Graphable) shape).getCentreX() - mouseEvent.getX();
            dragDelta.y = ((Graphable) shape).getCentreY() - mouseEvent.getY();
            shape.getScene().setCursor(Cursor.MOVE);
        });

        /*
            Cette méthode reprend les données enregistrés dans le delta, et applique
            la différence à la position courante du shape, créant ainsi un drag and drop.
         */
        shape.setOnMouseDragged(mouseEvent -> {
            double newY = mouseEvent.getY() + dragDelta.y;
            double newX = mouseEvent.getX() + dragDelta.x;

            if (newX > 0 && newX < shape.getScene().getWidth())  ((Graphable) shape).setCentreX(newX);
            if (newY > 0 && newY < shape.getScene().getHeight()) ((Graphable) shape).setCentreY(newY);
        });
    }
}
