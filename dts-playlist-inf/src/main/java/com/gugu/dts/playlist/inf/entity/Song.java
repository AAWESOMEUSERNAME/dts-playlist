package com.gugu.dts.playlist.inf.entity;

import lombok.Data;

/**
 * Table: song
 */
@Data
public class Song {
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
     * Column: bpm
     */
    private Double bpm;

    /**
     * Column: used_times
     */
    private Integer usedTimes;

    /**
     * Column: library_id
     */
    private Integer libraryId;

    /**
     * Column: track_length
     */
    private Integer trackLength;

    /**
     * Column: artist
     */
    private String artist;

    /**
     * Column: create_at
     */
    private String createAt;

    /**
     * Column: update_at
     */
    private String updateAt;
}