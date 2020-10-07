package com.gugu.dts.playlist.ui.usecase;

import com.gugu.dts.playlist.api.ICommander;
import com.gugu.dts.playlist.api.IQuery;
import com.gugu.dts.playlist.api.object.*;
import com.gugu.dts.playlist.ui.dto.LibRowDTO;
import com.gugu.dts.playlist.ui.parser.IMusicFile;
import com.gugu.dts.playlist.ui.parser.IParser;
import com.gugu.dts.playlist.ui.parser.MusicParserFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author chenyiyang
 * @date 2020/9/29
 */
@Component
public class MusicLibUsecase {


    private MusicParserFactory musicParserFactory;
    private ICommander commander;
    private IQuery query;

    public MusicLibUsecase(MusicParserFactory musicParserFactory, ICommander commander, IQuery query) {
        this.musicParserFactory = musicParserFactory;
        this.commander = commander;
        this.query = query;
    }

    public List<LibRowDTO> load() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return query.listLibrary().stream().map(iMusicLibrary ->
                new LibRowDTO(iMusicLibrary.getId(), format.format(iMusicLibrary.getCreateAt()), iMusicLibrary.getPath(), iMusicLibrary.getName())
        ).collect(Collectors.toList());
//        return Stream.of(
//                new LibRowDTO(1, "time1", "path", "name1"),
//                new LibRowDTO(2, "time2", "path", "name2"),
//                new LibRowDTO(3, "time3", "path", "name3"),
//                new LibRowDTO(4, "time4", "path", "name4")
//        ).collect(Collectors.toList());
    }

    @Transactional
    public void importLib(File root, String name) {
        Stack<File> stack = new Stack<>();
        stack.push(root);

        ArrayList<IMusicFile> songs = new ArrayList<>();
        while (!stack.empty()) {
            File file = stack.pop();
            if (file.isDirectory()) {
                File[] files = root.listFiles();
                if (files == null) {
                    continue;
                }
                Stream.of(files).forEach(stack::push);
            } else {
                IParser parser = musicParserFactory.getParser();
                Optional<IMusicFile> song = parser.parse(file);
                song.ifPresent(songs::add);
            }
        }

        commander.importLibray(new IMusicLibraryDTO() {
            @NotNull
            @Override
            public String getPath() {
                return root.getAbsolutePath();
            }

            @NotNull
            @Override
            public String getName() {
                return name;
            }

            @NotNull
            @Override
            public List<ISongDTO> getSongs() {
                return songs.stream().map(iMusicFile -> new ISongDTO() {
                    @NotNull
                    @Override
                    public String getName() {
                        return iMusicFile.name();
                    }

                    @NotNull
                    @Override
                    public String getPath() {
                        return iMusicFile.path();
                    }

                    @Override
                    public double getBpm() {
                        return iMusicFile.bpm();
                    }
                }).collect(Collectors.toList());
            }
        });
    }

    @Transactional
    public void deleteLib(long currentLibId) {
        commander.deleteLibById(currentLibId);
    }

    public File generatePlayList(long currentLibId, IRuleDTO ruleDTO) {
        IMusicLibrary lib = query.fetchLibraryById(currentLibId);
        if(lib == null){
            throw new RuntimeException(String.format("没有查询到对应Id的musicLib，ID：%s",currentLibId));
        }
        IRule rule = commander.createRule(ruleDTO);
        IPlayList iPlayList = rule.generatePlayList(lib);
        return iPlayList.toFile("D:/default.m3u");
    }
}
