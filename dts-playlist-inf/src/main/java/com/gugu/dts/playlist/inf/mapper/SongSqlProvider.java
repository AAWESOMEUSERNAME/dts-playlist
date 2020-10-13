package com.gugu.dts.playlist.inf.mapper;

import com.gugu.dts.playlist.inf.entity.Song;
import org.apache.ibatis.jdbc.SQL;

public class SongSqlProvider {

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    public String insertSelective(Song record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("song");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getName() != null) {
            sql.VALUES("`name`", "#{name,jdbcType=VARCHAR}");
        }
        
        if (record.getPath() != null) {
            sql.VALUES("`path`", "#{path,jdbcType=VARCHAR}");
        }
        
        if (record.getBpm() != null) {
            sql.VALUES("bpm", "#{bpm,jdbcType=FLOAT}");
        }
        
        if (record.getUsedTimes() != null) {
            sql.VALUES("used_times", "#{usedTimes,jdbcType=INTEGER}");
        }
        
        if (record.getLibraryId() != null) {
            sql.VALUES("library_id", "#{libraryId,jdbcType=INTEGER}");
        }
        
        if (record.getTrackLength() != null) {
            sql.VALUES("track_length", "#{trackLength,jdbcType=INTEGER}");
        }
        
        return sql.toString();
    }

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    public String updateByPrimaryKeySelective(Song record) {
        SQL sql = new SQL();
        sql.UPDATE("song");
        
        if (record.getName() != null) {
            sql.SET("`name` = #{name,jdbcType=VARCHAR}");
        }
        
        if (record.getPath() != null) {
            sql.SET("`path` = #{path,jdbcType=VARCHAR}");
        }
        
        if (record.getBpm() != null) {
            sql.SET("bpm = #{bpm,jdbcType=FLOAT}");
        }
        
        if (record.getUsedTimes() != null) {
            sql.SET("used_times = #{usedTimes,jdbcType=INTEGER}");
        }
        
        if (record.getLibraryId() != null) {
            sql.SET("library_id = #{libraryId,jdbcType=INTEGER}");
        }
        
        if (record.getTrackLength() != null) {
            sql.SET("track_length = #{trackLength,jdbcType=INTEGER}");
        }
        
        sql.WHERE("id = #{id,jdbcType=INTEGER}");
        
        return sql.toString();
    }
}