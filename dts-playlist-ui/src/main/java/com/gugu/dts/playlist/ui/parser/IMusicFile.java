package com.gugu.dts.playlist.ui.parser;

/**
 * @author chenyiyang
 * @date 2020/9/30
 */
public interface IMusicFile {
    double bpm();
    String name();
    String path();
    String artist();
    int trackLength();
    String album();
}
