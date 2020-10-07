package com.gugu.dts.playlist.inf.mapper;

import com.gugu.dts.playlist.inf.entity.MusicLibrary;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicLibraryMapper {

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    @Delete({ "delete from music_library", "where id = #{id,jdbcType=INTEGER}" })
    int deleteByPrimaryKey(Integer id);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    @Insert({ "insert into music_library (id, `name`, ", "`path`, create_at)", "values (#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, ", "#{path,jdbcType=VARCHAR}, #{createAt,jdbcType=VARCHAR})" })
    int insert(MusicLibrary record);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    @InsertProvider(type = MusicLibrarySqlProvider.class, method = "insertSelective")
    @SelectKey(statement = "SELECT last_insert_rowid()", keyProperty = "id", before = false, resultType = int.class)
    int insertSelective(MusicLibrary record);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    @Select({ "select", "id, `name`, `path`, create_at", "from music_library", "where id = #{id,jdbcType=INTEGER}" })
    @Results({ @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true), @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR), @Result(column = "path", property = "path", jdbcType = JdbcType.VARCHAR), @Result(column = "create_at", property = "createAt", jdbcType = JdbcType.VARCHAR) })
    MusicLibrary selectByPrimaryKey(Integer id);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    @UpdateProvider(type = MusicLibrarySqlProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(MusicLibrary record);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    @Update({ "update music_library", "set `name` = #{name,jdbcType=VARCHAR},", "`path` = #{path,jdbcType=VARCHAR},", "create_at = #{createAt,jdbcType=VARCHAR}", "where id = #{id,jdbcType=INTEGER}" })
    int updateByPrimaryKey(MusicLibrary record);

    @Select({ "select", "id, `name`, `path`, create_at", "from music_library" })
    @Results({ @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true), @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR), @Result(column = "path", property = "path", jdbcType = JdbcType.VARCHAR), @Result(column = "create_at", property = "createAt", jdbcType = JdbcType.VARCHAR) })
    List<MusicLibrary> list();

    @Select({ "select", "id, `name`, `path`, create_at", "from music_library", "where name = #{name,jdbcType=VARCHAR}" })
    @Results({ @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true), @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR), @Result(column = "path", property = "path", jdbcType = JdbcType.VARCHAR), @Result(column = "create_at", property = "createAt", jdbcType = JdbcType.VARCHAR) })
    MusicLibrary selectByName(String name);
}