package com.example.dahae.myandroiice.Adapter;

public class PlanItem {

    String name = null;
    boolean selected = false;

    public PlanItem(String name, String checkBox, boolean selected) {
        super();
        this.name = name;
        this.selected = selected;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}