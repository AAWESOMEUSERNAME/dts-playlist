package com.gugu.dts.playlist.ui.parser;

import org.springframework.stereotype.Component;

/**
 * @author chenyiyang
 * @date 2020/9/29
 */
@Component
public class MusicParserFactory {

    private IParser parser;

    public MusicParserFactory(IParser parser) {
        this.parser = parser;
    }

    public IParser getParser() {
        return parser;
    }
}
