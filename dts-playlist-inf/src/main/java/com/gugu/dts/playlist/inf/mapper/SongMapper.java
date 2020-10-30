package com.gugu.dts.playlist.inf.mapper;

import com.gugu.dts.playlist.inf.entity.Song;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongMapper {

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    @Delete({"delete from song", "where id = #{id,jdbcType=INTEGER}"})
    int deleteByPrimaryKey(Integer id);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    @Insert({"insert into song (id, `name`, ", "`path`, bpm, used_times, ", "library_id, track_length, ", "artist, create_at, ", "update_at, album)", "values (#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, ", "#{path,jdbcType=VARCHAR}, #{bpm,jdbcType=FLOAT}, #{usedTimes,jdbcType=INTEGER}, ", "#{libraryId,jdbcType=INTEGER}, #{trackLength,jdbcType=INTEGER}, ", "#{artist,jdbcType=VARCHAR}, #{createAt,jdbcType=VARCHAR}, ", "#{updateAt,jdbcType=VARCHAR}, #{album,jdbcType=VARCHAR})"})
    int insert(Song record);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    @InsertProvider(type = SongSqlProvider.class, method = "insertSelective")
    int insertSelective(Song record);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    @Select({"select", "id, `name`, `path`, bpm, used_times, library_id, track_length, artist, create_at, ", "update_at, album", "from song", "where id = #{id,jdbcType=INTEGER}"})
    @Results({@Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true), @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR), @Result(column = "path", property = "path", jdbcType = JdbcType.VARCHAR), @Result(column = "bpm", property = "bpm", jdbcType = JdbcType.FLOAT), @Result(column = "used_times", property = "usedTimes", jdbcType = JdbcType.INTEGER), @Result(column = "library_id", property = "libraryId", jdbcType = JdbcType.INTEGER), @Result(column = "track_length", property = "trackLength", jdbcType = JdbcType.INTEGER), @Result(column = "artist", property = "artist", jdbcType = JdbcType.VARCHAR), @Result(column = "create_at", property = "createAt", jdbcType = JdbcType.VARCHAR), @Result(column = "update_at", property = "updateAt", jdbcType = JdbcType.VARCHAR), @Result(column = "album", property = "album", jdbcType = JdbcType.VARCHAR)})
    Song selectByPrimaryKey(Integer id);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    @UpdateProvider(type = SongSqlProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Song record);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    @Update({"update song", "set `name` = #{name,jdbcType=VARCHAR},", "`path` = #{path,jdbcType=VARCHAR},", "bpm = #{bpm,jdbcType=FLOAT},", "used_times = #{usedTimes,jdbcType=INTEGER},", "library_id = #{libraryId,jdbcType=INTEGER},", "track_length = #{trackLength,jdbcType=INTEGER},", "artist = #{artist,jdbcType=VARCHAR},", "create_at = #{createAt,jdbcType=VARCHAR},", "update_at = #{updateAt,jdbcType=VARCHAR},", "album = #{album,jdbcType=VARCHAR}", "where id = #{id,jdbcType=INTEGER}"})
    int updateByPrimaryKey(Song record);

    @Select({"select", "id, `name`, `path`,`artist`,`album`, bpm, used_times, library_id, track_length", "from song", "where library_id = #{id,jdbcType=INTEGER}"})
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "path", property = "path", jdbcType = JdbcType.VARCHAR),
            @Result(column = "album", property = "album", jdbcType = JdbcType.VARCHAR),
            @Result(column = "artist", property = "artist", jdbcType = JdbcType.VARCHAR),
            @Result(column = "bpm", property = "bpm", jdbcType = JdbcType.FLOAT),
            @Result(column = "used_times", property = "usedTimes", jdbcType = JdbcType.INTEGER),
            @Result(column = "library_id", property = "libraryId", jdbcType = JdbcType.INTEGER),
            @Result(column = "track_length", property = "trackLength", jdbcType = JdbcType.INTEGER)
    })
    List<Song> selectByLibraryId(Integer id);

    @Delete({"delete from song", "where library_id = #{id,jdbcType=INTEGER}"})
    void deleteByLibId(Integer id);

    @Update({"update song", "set `used_times` = 0", "where library_id = #{libId,jdbcType=INTEGER}"})
    void resetPlayedTimes(Integer libId);

    @Update({"update song", "set `used_times` = #{usedTimes,jdbcType=INTEGER}", "where `id` = #{id,jdbcType=INTEGER}"})
    void updatePlayedTimes(@Param("id") int id, @Param("usedTimes") int usedTimes);

    @Update({"<script> update song", "set `used_times` = `used_times` +1", "where `id` in ",
            "<foreach collection='ids' item = 'item' open = '(' separator = ',' close = ')'>",
            "#{item}",
            "</foreach> </script>"
    })
    void updatePlayedTimesByOne(@Param("ids") @NotNull int[] ids);
}