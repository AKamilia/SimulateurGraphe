package com.diderot;

import javafx.scene.paint.Color;

/*
    Interfance Colorable
    Interface permettant à toute shape de pouvoir set et get une couleur
    Par exemple: Un GCircle peut ainsi avoir une couleur
 */
interface Colorable {
    void setColor(Color color);
    Color getColor();
}
