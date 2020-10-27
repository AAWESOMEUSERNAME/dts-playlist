package com.gugu.dts.playlist.ui.dto;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author chenyiyang
 * @date 2020/9/29
 */
public class LibRowDTO {

    public static final String PROP_ID = "id";
    public static final String PROP_NAME = "name";
    public static final String PROP_IMPORT_TIME = "importTime";
    public static final String PROP_PATH = "path";

    private SimpleIntegerProperty id;
    private SimpleStringProperty importTime;
    private SimpleStringProperty path;
    private SimpleStringProperty name;

    public LibRowDTO() {
    }

    public LibRowDTO(Integer id, String importTime, String path, String name) {
        this.id = new SimpleIntegerProperty(id);
        this.importTime = new SimpleStringProperty(importTime);
        this.path = new SimpleStringProperty(path);
        this.name = new SimpleStringProperty(name);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public String getImportTime() {
        return importTime.get();
    }

    public void setImportTime(String importTime) {
        this.importTime.set(importTime);
    }

    public SimpleStringProperty importTimeProperty() {
        return importTime;
    }

    public String getPath() {
        return path.get();
    }

    public void setPath(String path) {
        this.path.set(path);
    }

    public SimpleStringProperty pathProperty() {
        return path;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }
}
