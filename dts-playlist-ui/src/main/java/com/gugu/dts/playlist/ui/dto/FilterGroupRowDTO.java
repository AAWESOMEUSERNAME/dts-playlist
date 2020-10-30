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

    public static final String PROP_NAME = "name";
    public static final String PROP_CONDITION = "condition";
    public static final String PROP_NUM = "songNum";

    private SimpleStringProperty name;
    private SimpleStringProperty condition;
    private SimpleIntegerProperty songNum;


    @Getter
    @Setter
    private Integer id;
    @Getter
    @Setter
    private List<FilterRowDTO> filters;

    public FilterGroupRowDTO(String name, String condition, Integer songNum) {
        this(name, condition, songNum, new ArrayList<>());
    }

    public FilterGroupRowDTO(String name, String condition, Integer songNum, List<FilterRowDTO> filters) {
        this.name = new SimpleStringProperty(name);
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

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void addFilter(FilterRowDTO filter) {
        filters.add(filter);
        initCondition();
    }

    public void deleteFilter(FilterRowDTO filter) {
        filters.remove(filter);
        initCondition();
    }

    private void initCondition() {
        this.condition = new SimpleStringProperty(filters.stream()
                .map(it -> String.format("%s: [%s,%s)", it.getPropertyName(), it.getMin(), it.getMax()))
                .reduce((s, s2) -> s + "," + s2)
                .orElse("error"));
    }
}
