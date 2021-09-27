package com.demo.connector.service.sonarqube.dto;

public class Period {
    private int index;
    private String value;
    private boolean bestValue;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isBestValue() {
        return bestValue;
    }

    public void setBestValue(boolean bestValue) {
        this.bestValue = bestValue;
    }
}
