package com.gugu.dts.playlist.ui.parser;

import java.io.File;
import java.util.Optional;

/**
 * @author chenyiyang
 * @date 2020/9/29
 */
public interface IParser {
    Optional<IMusicFile> parse(File file);
}
