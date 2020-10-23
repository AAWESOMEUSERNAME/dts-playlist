package com.gugu.dts.playlist.ui.dto;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author chenyiyang
 * @date 2020/10/21
 */
public class SongsRowDTO {
    
    public static final String PROP_ALBUM = "album";
    public static final String PROP_BPM = "bpm";
    public static final String PROP_ARTIST = "artist";
    public static final String PROP_ID = "id";
    public static final String PROP_LENGTH = "length";
    public static final String PROP_PATH = "path";
    public static final String PROP_NAME = "name";
    public static final String PROP_PLAYED_TIMES = "playedTimes";

    private SimpleStringProperty album;

    private SimpleDoubleProperty bpm;

    private SimpleStringProperty artist;

    private SimpleIntegerProperty id;

    private SimpleIntegerProperty length;

    private SimpleStringProperty path;

    private SimpleStringProperty name;

    private SimpleIntegerProperty playedTimes;

    public SongsRowDTO(String album, Double bpm, String artist, Integer id, Integer length, String path, String name, Integer playedTimes) {
        this.album = new SimpleStringProperty(album);
        this.bpm = new SimpleDoubleProperty(bpm);
        this.artist = new SimpleStringProperty(artist);
        this.id = new SimpleIntegerProperty(id);
        this.length = new SimpleIntegerProperty(length);
        this.path = new SimpleStringProperty(path);
        this.name = new SimpleStringProperty(name);
        this.playedTimes = new SimpleIntegerProperty(playedTimes);
    }

    public String getAlbum() {
        return album.get();
    }

    public SimpleStringProperty albumProperty() {
        return album;
    }

    public void setAlbum(String album) {
        this.album.set(album);
    }

    public double getBpm() {
        return bpm.get();
    }

    public SimpleDoubleProperty bpmProperty() {
        return bpm;
    }

    public void setBpm(double bpm) {
        this.bpm.set(bpm);
    }

    public String getArtist() {
        return artist.get();
    }

    public SimpleStringProperty artistProperty() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist.set(artist);
    }

    public long getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getLength() {
        return length.get();
    }

    public SimpleIntegerProperty lengthProperty() {
        return length;
    }

    public void setLength(int length) {
        this.length.set(length);
    }

    public String getPath() {
        return path.get();
    }

    public SimpleStringProperty pathProperty() {
        return path;
    }

    public void setPath(String path) {
        this.path.set(path);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getPlayedTimes() {
        return playedTimes.get();
    }

    public void setPlayedTimes(int playedTimes) {
        this.playedTimes.set(playedTimes);
    }
}
