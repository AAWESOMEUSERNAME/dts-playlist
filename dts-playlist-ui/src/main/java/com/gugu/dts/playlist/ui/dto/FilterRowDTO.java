package com.gugu.dts.playlist.ui.dto;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import lombok.Data;

/**
 * @author chenyiyang
 * @date 2020/9/28
 */
public class FilterRowDTO {

    public static final String PROP_CONDITION = "condition";
    public static final String PROP_NUM = "songNum";

    private SimpleStringProperty condition;
    private SimpleIntegerProperty songNum;

    public FilterRowDTO() {
    }

    public static FilterRowDTO emptyRow(){
        return new FilterRowDTO("无",0);
    }

    public FilterRowDTO(String condition, Integer songNum) {
        this.condition = new SimpleStringProperty(condition);
        this.songNum = new SimpleIntegerProperty(songNum);
    }

    public String getCondition() {
        return condition.get();
    }

    public SimpleStringProperty conditionProperty() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition.set(condition);
    }

    public int getSongNum() {
        return songNum.get();
    }

    public SimpleIntegerProperty songNumProperty() {
        return songNum;
    }

    public void setSongNum(int songNum) {
        this.songNum.set(songNum);
    }
}
