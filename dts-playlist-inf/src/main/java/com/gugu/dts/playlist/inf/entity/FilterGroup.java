package com.gugu.dts.playlist.inf.entity;

/**
 * Table: filter_group
 */
public class FilterGroup {
    /**
     * Column: id
     */
    private Integer id;

    /**
     * Column: name
     */
    private String name;

    /**
     * Column: condition_description
     */
    private String conditionDescription;

    /**
     * Column: condition_json
     */
    private String conditionJson;

    /**
     * Column: sum
     */
    private Integer sum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getConditionDescription() {
        return conditionDescription;
    }

    public void setConditionDescription(String conditionDescription) {
        this.conditionDescription = conditionDescription == null ? null : conditionDescription.trim();
    }

    public String getConditionJson() {
        return conditionJson;
    }

    public void setConditionJson(String conditionJson) {
        this.conditionJson = conditionJson == null ? null : conditionJson.trim();
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }
}