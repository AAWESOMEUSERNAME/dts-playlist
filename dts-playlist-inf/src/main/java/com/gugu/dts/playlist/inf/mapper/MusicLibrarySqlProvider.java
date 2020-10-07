package com.gugu.dts.playlist.inf.mapper;

import com.gugu.dts.playlist.inf.entity.MusicLibrary;
import org.apache.ibatis.jdbc.SQL;

public class MusicLibrarySqlProvider {

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    public String insertSelective(MusicLibrary record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("music_library");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getName() != null) {
            sql.VALUES("`name`", "#{name,jdbcType=VARCHAR}");
        }
        
        if (record.getPath() != null) {
            sql.VALUES("`path`", "#{path,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateAt() != null) {
            sql.VALUES("create_at", "#{createAt,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    public String updateByPrimaryKeySelective(MusicLibrary record) {
        SQL sql = new SQL();
        sql.UPDATE("music_library");
        
        if (record.getName() != null) {
            sql.SET("`name` = #{name,jdbcType=VARCHAR}");
        }
        
        if (record.getPath() != null) {
            sql.SET("`path` = #{path,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateAt() != null) {
            sql.SET("create_at = #{createAt,jdbcType=VARCHAR}");
        }
        
        sql.WHERE("id = #{id,jdbcType=INTEGER}");
        
        return sql.toString();
    }
}