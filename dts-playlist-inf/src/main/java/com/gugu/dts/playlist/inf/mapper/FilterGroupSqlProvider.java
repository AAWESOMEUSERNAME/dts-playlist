package com.gugu.dts.playlist.inf.mapper;

import com.gugu.dts.playlist.inf.entity.FilterGroup;
import org.apache.ibatis.jdbc.SQL;

public class FilterGroupSqlProvider {

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    public String insertSelective(FilterGroup record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("filter_group");

        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }

        if (record.getName() != null) {
            sql.VALUES("`name`", "#{name,jdbcType=VARCHAR}");
        }

        if (record.getConditionDescription() != null) {
            sql.VALUES("condition_description", "#{conditionDescription,jdbcType=VARCHAR}");
        }

        if (record.getConditionJson() != null) {
            sql.VALUES("condition_json", "#{conditionJson,jdbcType=VARCHAR}");
        }

        if (record.getSum() != null) {
            sql.VALUES("`sum`", "#{sum,jdbcType=INTEGER}");
        }

        return sql.toString();
    }

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    public String updateByPrimaryKeySelective(FilterGroup record) {
        SQL sql = new SQL();
        sql.UPDATE("filter_group");

        if (record.getName() != null) {
            sql.SET("`name` = #{name,jdbcType=VARCHAR}");
        }

        if (record.getConditionDescription() != null) {
            sql.SET("condition_description = #{conditionDescription,jdbcType=VARCHAR}");
        }

        if (record.getConditionJson() != null) {
            sql.SET("condition_json = #{conditionJson,jdbcType=VARCHAR}");
        }

        if (record.getSum() != null) {
            sql.SET("`sum` = #{sum,jdbcType=INTEGER}");
        }

        sql.WHERE("id = #{id,jdbcType=INTEGER}");

        return sql.toString();
    }
}