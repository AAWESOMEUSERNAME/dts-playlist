package com.gugu.dts.playlist.ui.parser.impl;

import com.gugu.dts.playlist.ui.parser.IMusicFile;
import com.gugu.dts.playlist.ui.parser.IParser;
import lombok.extern.slf4j.Slf4j;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.TagField;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * @author chenyiyang
 * @date 2020/9/30
 */
@Slf4j(topic = "app")
@Component
public class ParserImpl implements IParser {
    @Override
    public Optional<IMusicFile> parse(File file) {
        try {
            log.debug("parsing file: {}", file.getName());
            AudioFile f = AudioFileIO.read(file);
            Tag tag = f.getTag();
            List<TagField> artists = tag.getFields(FieldKey.ARTIST);
            String album = tag.getFirst(FieldKey.ALBUM);
            int trackLength = f.getAudioHeader().getTrackLength();
            String bpm = tag.getFirst(FieldKey.BPM);
            log.debug("parsing file BPM: {}", bpm);
            if (StringUtils.isEmpty(bpm)) {
                log.warn("--------- file BPM is empty! ---------");
            }
            return Optional.of(new IMusicFile() {
                @Override
                public double bpm() {
                    return StringUtils.isEmpty(bpm) ? 0.0 : Double.parseDouble(bpm);
                }

                @Override
                public String name() {
                    return file.getName();
                }

                @Override
                public String path() {
                    return file.getAbsolutePath();
                }

                @Override
                public String artist() {
                    return artists.stream().map(TagField::toString).reduce("", (s, s2) -> s + s2);
                }
                @Override
                public String album() {
                    return album;
                }

                @Override
                public int trackLength() {
                    return trackLength;
                }
            });
        } catch (Exception e) {
            log.error(String.format("error parse file %s", file.getAbsolutePath()), e);
            return Optional.empty();
        }
    }


//    public static void main(String[] args) throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException {
//        File file = new File("D:\\Data\\music\\09-05. Hop Skip And Jump.mp3");
//        AudioFile f = AudioFileIO.read(file);
//        int trackLength = f.getAudioHeader().getTrackLength();
//        System.out.println("length : " + trackLength);
//        System.out.println("BPM: " + f.getTag().getFirst(FieldKey.BPM));
//        System.out.println("artist:" + f.getTag().getFields(FieldKey.ARTIST));
//        System.out.println("artists: " + f.getTag().getFields(FieldKey.ARTISTS));
//        Iterator<TagField> fields = f.getTag().getFields();
//        for (Iterator<TagField> it = fields; it.hasNext(); ) {
//            TagField field = it.next();
//            System.out.println("id: " + field.getId() + " value:" + field);
//        }
//    }
}
