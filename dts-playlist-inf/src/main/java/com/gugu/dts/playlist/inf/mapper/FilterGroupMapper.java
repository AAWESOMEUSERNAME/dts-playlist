package com.gugu.dts.playlist.inf.mapper;

import com.gugu.dts.playlist.inf.entity.FilterGroup;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilterGroupMapper {

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    @Delete({"delete from filter_group", "where id = #{id,jdbcType=INTEGER}"})
    int deleteByPrimaryKey(Integer id);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    @Insert({"insert into filter_group (id, `name`, ", "condition_description, condition_json, ", "`sum`, logic, create_at, ", "update_at)", "values (#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, ", "#{conditionDescription,jdbcType=VARCHAR}, #{conditionJson,jdbcType=VARCHAR}, ", "#{sum,jdbcType=INTEGER}, #{logic,jdbcType=INTEGER}, #{createAt,jdbcType=VARCHAR}, ", "#{updateAt,jdbcType=VARCHAR})"})
    int insert(FilterGroup record);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    @InsertProvider(type = FilterGroupSqlProvider.class, method = "insertSelective")
    int insertSelective(FilterGroup record);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    @Select({"select", "id, `name`, condition_description, condition_json, `sum`, logic, create_at, ", "update_at", "from filter_group", "where id = #{id,jdbcType=INTEGER}"})
    @Results({@Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true), @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR), @Result(column = "condition_description", property = "conditionDescription", jdbcType = JdbcType.VARCHAR), @Result(column = "condition_json", property = "conditionJson", jdbcType = JdbcType.VARCHAR), @Result(column = "sum", property = "sum", jdbcType = JdbcType.INTEGER), @Result(column = "logic", property = "logic", jdbcType = JdbcType.INTEGER), @Result(column = "create_at", property = "createAt", jdbcType = JdbcType.VARCHAR), @Result(column = "update_at", property = "updateAt", jdbcType = JdbcType.VARCHAR)})
    FilterGroup selectByPrimaryKey(Integer id);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    @UpdateProvider(type = FilterGroupSqlProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(FilterGroup record);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    @Update({"update filter_group", "set `name` = #{name,jdbcType=VARCHAR},", "condition_description = #{conditionDescription,jdbcType=VARCHAR},", "condition_json = #{conditionJson,jdbcType=VARCHAR},", "`sum` = #{sum,jdbcType=INTEGER},", "logic = #{logic,jdbcType=INTEGER},", "create_at = #{createAt,jdbcType=VARCHAR},", "update_at = #{updateAt,jdbcType=VARCHAR}", "where id = #{id,jdbcType=INTEGER}"})
    int updateByPrimaryKey(FilterGroup record);

    @Select("select * from filter_group")
    @Results({@Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true), @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR), @Result(column = "condition_description", property = "conditionDescription", jdbcType = JdbcType.VARCHAR), @Result(column = "condition_json", property = "conditionJson", jdbcType = JdbcType.VARCHAR), @Result(column = "sum", property = "sum", jdbcType = JdbcType.INTEGER)})
    List<FilterGroup> list();
}