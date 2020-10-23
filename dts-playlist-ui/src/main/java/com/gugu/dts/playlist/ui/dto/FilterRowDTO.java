package com.gugu.dts.playlist.ui.dto;

import javafx.beans.property.SimpleStringProperty;

/**
 * @author chenyiyang
 * @date 2020/10/23
 */
public class FilterRowDTO {
    public static final String PROP_NAME = "propertyName";
    public static final String PROP_MIN = "min";
    public static final String PROP_MAX = "max";

    private SimpleStringProperty propertyName;
    private SimpleStringProperty min;
    private SimpleStringProperty max;

    public FilterRowDTO(String propertyName, String min, String max) {
        this.propertyName = new SimpleStringProperty(propertyName);
        this.min = new SimpleStringProperty(min);
        this.max = new SimpleStringProperty(max);
    }

    public String getPropertyName() {
        return propertyName.get();
    }

    public SimpleStringProperty propertyNameProperty() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName.set(propertyName);
    }

    public String getMin() {
        return min.get();
    }

    public SimpleStringProperty minProperty() {
        return min;
    }

    public void setMin(String min) {
        this.min.set(min);
    }

    public String getMax() {
        return max.get();
    }

    public SimpleStringProperty maxProperty() {
        return max;
    }

    public void setMax(String max) {
        this.max.set(max);
    }
}
