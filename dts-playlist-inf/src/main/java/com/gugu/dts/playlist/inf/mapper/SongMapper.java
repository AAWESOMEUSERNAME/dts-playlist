package com.gugu.dts.playlist.inf.mapper;

import com.gugu.dts.playlist.inf.entity.Song;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongMapper {

    @Select({
            "select",
            "id, `name`, `path`, bpm, used_times, library_id",
            "from song",
            "where library_id = #{id,jdbcType=INTEGER}"
    })
    List<Song> selectByLibraryId(Integer id);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    @Delete({
            "delete from song",
            "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    @Insert({
            "insert into song (id, `name`, ",
            "`path`, bpm, used_times, ",
            "library_id)",
            "values (#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, ",
            "#{path,jdbcType=VARCHAR}, #{bpm,jdbcType=FLOAT}, #{usedTimes,jdbcType=INTEGER}, ",
            "#{libraryId,jdbcType=INTEGER})"
    })
    int insert(Song record);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    @InsertProvider(type = SongSqlProvider.class, method = "insertSelective")
    @SelectKey(statement = "SELECT last_insert_rowid()", keyProperty = "id", before = false, resultType = int.class)
    int insertSelective(Song record);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    @Select({
            "select",
            "id, `name`, `path`, bpm, used_times, library_id",
            "from song",
            "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "path", property = "path", jdbcType = JdbcType.VARCHAR),
            @Result(column = "bpm", property = "bpm", jdbcType = JdbcType.FLOAT),
            @Result(column = "used_times", property = "usedTimes", jdbcType = JdbcType.INTEGER),
            @Result(column = "library_id", property = "libraryId", jdbcType = JdbcType.INTEGER)
    })
    Song selectByPrimaryKeyWithLock(Integer id);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    @Select({
            "select",
            "id, `name`, `path`, bpm, used_times, library_id",
            "from song",
            "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "path", property = "path", jdbcType = JdbcType.VARCHAR),
            @Result(column = "bpm", property = "bpm", jdbcType = JdbcType.FLOAT),
            @Result(column = "used_times", property = "usedTimes", jdbcType = JdbcType.INTEGER),
            @Result(column = "library_id", property = "libraryId", jdbcType = JdbcType.INTEGER)
    })
    Song selectByPrimaryKey(Integer id);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    @UpdateProvider(type = SongSqlProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Song record);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    @Update({
            "update song",
            "set `name` = #{name,jdbcType=VARCHAR},",
            "`path` = #{path,jdbcType=VARCHAR},",
            "bpm = #{bpm,jdbcType=FLOAT},",
            "used_times = #{usedTimes,jdbcType=INTEGER},",
            "library_id = #{libraryId,jdbcType=INTEGER}",
            "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Song record);

    @Delete({
            "delete from song",
            "where library_id = #{id,jdbcType=INTEGER}"
    })
    void deleteByLibId(Integer id);
}