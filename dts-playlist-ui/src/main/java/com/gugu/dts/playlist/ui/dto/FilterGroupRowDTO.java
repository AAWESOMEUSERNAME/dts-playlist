package com.gugu.dts.playlist.ui.dto;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenyiyang
 * @date 2020/9/28
 */
public class FilterGroupRowDTO {

    public static final String PROP_CONDITION = "condition";
    public static final String PROP_NUM = "songNum";

    private SimpleStringProperty condition;
    private SimpleIntegerProperty songNum;

    @Getter
    @Setter
    private List<FilterRowDTO> filters;

    public FilterGroupRowDTO(String condition, Integer songNum) {
        this(condition, songNum, new ArrayList<>());
    }

    public FilterGroupRowDTO(String condition, Integer songNum, List<FilterRowDTO> filters) {
        this.condition = new SimpleStringProperty(condition);
        this.songNum = new SimpleIntegerProperty(songNum);
        this.filters = filters;
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
