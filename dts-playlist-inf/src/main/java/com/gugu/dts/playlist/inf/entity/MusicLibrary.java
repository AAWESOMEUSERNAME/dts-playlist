package com.gugu.dts.playlist.inf.entity;

import lombok.Data;

/**
 * Table: music_library
 */
@Data
public class MusicLibrary {
    /**
     * Column: id
     */
    private Integer id;

    /**
     * Column: name
     */
    private String name;

    /**
     * Column: path
     */
    private String path;

    /**
     * Column: create_at
     */
    private String createAt;

    /**
     * Column: update_at
     */
    private String updateAt;
}