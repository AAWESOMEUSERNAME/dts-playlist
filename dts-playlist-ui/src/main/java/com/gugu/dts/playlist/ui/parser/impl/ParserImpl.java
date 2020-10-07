package com.gugu.dts.playlist.ui.parser.impl;

import com.gugu.dts.playlist.ui.parser.IMusicFile;
import com.gugu.dts.playlist.ui.parser.IParser;
import lombok.extern.slf4j.Slf4j;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Optional;

/**
 * @author chenyiyang
 * @date 2020/9/30
 */
@Slf4j
@Component
public class ParserImpl implements IParser {
    @Override
    public Optional<IMusicFile> parse(File file) {
        try {
            AudioFile f = AudioFileIO.read(file);
            Tag tag = f.getTag();
            String bpm = tag.getFirst(FieldKey.BPM);
            return Optional.of(new IMusicFile() {
                @Override
                public double bpm() {
                    return bpm == null?0.0:Double.parseDouble(bpm);
                }

                @Override
                public String name() {
                    return file.getName();
                }

                @Override
                public String path() {
                    return file.getAbsolutePath();
                }
            });
        } catch (Exception e) {
            log.error(String.format("error parse file %s",file.getAbsolutePath()),e);
            return Optional.empty();
        }
    }
}
