package com.gugu.dts.playlist.ui.usecase;

import com.gugu.dts.playlist.api.ICommander;
import com.gugu.dts.playlist.api.IQuery;
import com.gugu.dts.playlist.api.constants.Logic;
import com.gugu.dts.playlist.api.constants.PropertyProvider;
import com.gugu.dts.playlist.api.object.*;
import com.gugu.dts.playlist.ui.AppProperties;
import com.gugu.dts.playlist.ui.constants.FilterablePropertyEnum;
import com.gugu.dts.playlist.ui.dto.*;
import com.gugu.dts.playlist.ui.dto.RuleDTO.FilterGroupDTO;
import com.gugu.dts.playlist.ui.parser.IMusicFile;
import com.gugu.dts.playlist.ui.parser.IParser;
import com.gugu.dts.playlist.ui.parser.MusicParserFactory;
import com.gugu.dts.playlist.ui.utils.AlertUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author chenyiyang
 * @date 2020/9/29
 */
@Component
public class GeneratorUsecase {


    private MusicParserFactory musicParserFactory;
    private ICommander commander;
    private IQuery query;
    private AppProperties appProperties;

    public GeneratorUsecase(MusicParserFactory musicParserFactory, ICommander commander, IQuery query, AppProperties appProperties) {
        this.musicParserFactory = musicParserFactory;
        this.commander = commander;
        this.query = query;
        this.appProperties = appProperties;
    }

    public List<LibRowDTO> loadLib() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return query.listLibrary().stream().map(iMusicLibrary ->
                new LibRowDTO(iMusicLibrary.getId(), format.format(iMusicLibrary.getCreateAt()), iMusicLibrary.getPath(), iMusicLibrary.getName())
        ).collect(Collectors.toList());
    }

    public List<SongsRowDTO> loadLib(int libId) {
        IMusicLibrary library = query.fetchLibraryById(libId);
        if (library == null) {
            AlertUtil.warn("选择的音乐库无效！id：" + libId);
            return Collections.emptyList();
        }
        return library.getSongs().stream().map(iSong ->
                new SongsRowDTO(iSong.getAlbum(), iSong.getBpm(), iSong.getArtist(), iSong.getId(), iSong.getTrackLength(), iSong.getPath(), iSong.getName(), iSong.getUsedTimes())
        ).collect(Collectors.toList());
    }

    @Transactional
    public void importLib(File root, String name) {
        LinkedList<File> list = new LinkedList<>();
        list.addLast(root);

        ArrayList<IMusicFile> songs = new ArrayList<>();
        while (list.size() > 0) {
            File file = list.pollFirst();
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files == null) {
                    continue;
                }
                Stream.of(files).forEach(list::addLast);
            } else if (isSupported(file)) {
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

                    @Nullable
                    @Override
                    public String getArtist() {
                        return iMusicFile.artist();
                    }

                    @Nullable
                    @Override
                    public String getAlbum() {
                        return iMusicFile.album();
                    }

                    @Override
                    public int getTrackLength() {
                        return iMusicFile.trackLength();
                    }
                }).collect(Collectors.toList());
            }
        });
    }

    private boolean isSupported(File file) {
        String fileName = file.getName();
        for (String post : IParser.SUPPORTED) {
            if (fileName.endsWith(post)) {
                return true;
            }
        }
        return false;
    }

    @Transactional
    public void deleteLib(int currentLibId) {
        commander.deleteLibById(currentLibId);
    }

    public File generatePlayList(int currentLibId, List<FilterGroupRowDTO> groups, Double total, boolean fairly) {
        IMusicLibrary lib = query.fetchLibraryById(currentLibId);
        if (lib == null) {
            throw new RuntimeException(String.format("没有查询到对应Id的musicLib，ID：%s", currentLibId));
        }

        List<IFilterGroupDTO> groupDTOs = groups.stream().map(group -> {
            int songNum = group.getSongNum();
            List<IFilter> filters = group.getFilters().stream().map(dto -> {
                PropertyProvider provider = null;
                switch (FilterablePropertyEnum.ofProp(dto.getPropertyName())) {
                    case BPM:
                        provider = PropertyProvider.BPM;
                        break;
                    case TRACK_LENGTH:
                        provider = PropertyProvider.LENGTH;
                }
                return commander.getIntervalFilter(dto.getMinD(), dto.getMaxD(), provider);
            }).collect(Collectors.toList());
            return FilterGroupDTO.builder().filters(filters).logic(Logic.AND).sum(songNum).build();
        }).collect(Collectors.toList());

        RuleDTO ruleDTO = RuleDTO.builder().fairlyMode(fairly).total(total.intValue()).filterGroups(groupDTOs).build();
        IRule rule = commander.createRule(ruleDTO);
        IPlayList iPlayList = rule.generatePlayList(lib);
        commander.updateSongPlayedTimesByOne(iPlayList.getSongs().stream().mapToLong(it -> it.getId().longValue()).toArray());
        return iPlayList.toFile(appProperties.getOutPutFile());
    }

    public void resetPlayTimes(Integer libId) {
        commander.resetLibPlayedTimes(libId);
    }

    public void updateSongPlayedTimes(long id, Integer newValue) {
        commander.updateSongPlayedTimes(id, newValue);
    }

    public List<FilterRowDTO> loadGroups() {
        return query.listFilterGroups();
    }
}
