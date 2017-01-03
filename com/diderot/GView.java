package com.diderot;

import java.util.ArrayList;
import java.util.List;

// Une instance de cette classe représente l'état d'un graphe à un moment donnée
// Elle stocke les informations minimales requises pour créer une représentation d'un graphe
class GView {
    private List<GViewNode> nodes;
    private List<GViewLink> links;

    GView() {
        this.nodes = new ArrayList<>();
        this.links = new ArrayList<>();
    }

    List<GViewNode> getNodes() {
        return nodes;
    }
    List<GViewLink> getLinks() {
        return links;
    }
}
