package com.diderot;

class GViewLink {
    private int shape_1_id, shape_2_id, weight;
    private String shape_1_class, shape_2_class;

    GViewLink(int shape_1_id, int shape_2_id, String shape_1_class, String shape_2_class, int weight) {
        this.shape_1_id = shape_1_id;
        this.shape_2_id = shape_2_id;

        this.shape_1_class = shape_1_class;
        this.shape_2_class = shape_2_class;
        this.weight = weight;
    }

    int getShape_1_id() {
        return shape_1_id;
    }
    int getShape_2_id() {
        return shape_2_id;
    }

    String getShape_1_class() {
        return shape_1_class;
    }
    String getShape_2_class() {
        return shape_2_class;
    }

    public int getWeight() { return weight; }
}
