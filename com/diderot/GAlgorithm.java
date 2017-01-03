package com.diderot;

// Cette classe mère permet aux classes enfants
// de posséder les memes méthodes et éviter les répétitions
class GAlgorithm {
    void clean() {
        Main.clearChildren();
        Main.setGraphViewList();
    }

    void createGView() {
        Main.addCurrentGraphView();
    }
}
