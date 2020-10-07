package com.gugu.dts.playlist.ui.dto;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * @author chenyiyang
 * @date 2020/9/28
 */
public class FilterRowDTO {

    public static final String PROP_MIN = "bpmMin";
    public static final String PROP_MAX = "bpmMax";
    public static final String PROP_NUM = "songNum";

    private SimpleDoubleProperty bpmMin;
    private SimpleDoubleProperty bpmMax;
    private SimpleIntegerProperty songNum;

    public FilterRowDTO() {
    }

    public static FilterRowDTO emptyRow(){
        return new FilterRowDTO(0.0,0.0,0);
    }

    public FilterRowDTO(Double bpmMin, Double bpmMax, Integer songNum) {
        this.bpmMin = new SimpleDoubleProperty(bpmMin);
        this.bpmMax = new SimpleDoubleProperty(bpmMax);
        this.songNum = new SimpleIntegerProperty(songNum);
    }

    public double getBpmMin() {
        return bpmMin.get();
    }

    public void setBpmMin(double bpmMin) {
        this.bpmMin.set(bpmMin);
    }

    public SimpleDoubleProperty bpmMinProperty() {
        return bpmMin;
    }

    public double getBpmMax() {
        return bpmMax.get();
    }

    public void setBpmMax(double bpmMax) {
        this.bpmMax.set(bpmMax);
    }

    public SimpleDoubleProperty bpmMaxProperty() {
        return bpmMax;
    }

    public int getSongNum() {
        return songNum.get();
    }

    public void setSongNum(int songNum) {
        this.songNum.set(songNum);
    }

    public SimpleIntegerProperty songNumProperty() {
        return songNum;
    }
}
