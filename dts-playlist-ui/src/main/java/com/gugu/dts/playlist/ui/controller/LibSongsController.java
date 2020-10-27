package com.gugu.dts.playlist.ui.controller;

import com.gugu.dts.playlist.ui.dto.SongsRowDTO;
import com.gugu.dts.playlist.ui.usecase.GeneratorUsecase;
import com.gugu.dts.playlist.ui.utils.AlertUtil;
import de.felixroske.jfxsupport.FXMLController;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;
import javafx.util.converter.IntegerStringConverter;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static com.gugu.dts.playlist.ui.controller.LibSongsController.LibSongsViewData.libId;

/**
 * @author chenyiyang
 * @date 2020/10/21
 */
@FXMLController
public class LibSongsController implements Initializable, EventHandler<WindowEvent> {

    private GeneratorUsecase musicLibUsecase;

    public LibSongsController(GeneratorUsecase musicLibUsecase) {
        this.musicLibUsecase = musicLibUsecase;
    }

    @FXML
    private TableColumn<SongsRowDTO, String> col_album;

    @FXML
    private TableColumn<SongsRowDTO, Double> col_bpm;

    @FXML
    private TableColumn<SongsRowDTO, String> col_artist;

    @FXML
    private TableColumn<SongsRowDTO, Long> col_id;

    @FXML
    private TableColumn<SongsRowDTO, Integer> col_length;

    @FXML
    private TableColumn<SongsRowDTO, String> col_path;

    @FXML
    private TableColumn<SongsRowDTO, String> col_name;

    @FXML
    private TableColumn<SongsRowDTO, Integer> col_playedTimes;

    @FXML
    private TableView<SongsRowDTO> table_songs;

    @FXML
    void resetPlayedTimes(MouseEvent event) {
        if (AlertUtil.comfirm("确认重置当前音乐库所有音乐的播放次数么（不会影响其他音乐库）？")) {
            musicLibUsecase.resetPlayTimes(libId);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCellValueFactory();
        makeTableEditable();
    }

    private void initTableData() {
        List<SongsRowDTO> rows = musicLibUsecase.loadLib(libId);
        table_songs.setItems(FXCollections.observableList(rows));
    }

    @Override
    public void handle(WindowEvent event) {
        EventType<WindowEvent> eventType = event.getEventType();
        if (eventType.equals(WindowEvent.WINDOW_SHOWN)) {
            initTableData();
        }
    }

    private void makeTableEditable() {
        table_songs.setEditable(true);
        table_songs.getSelectionModel().cellSelectionEnabledProperty().set(true);
        col_playedTimes.setEditable(true);
        col_playedTimes.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        col_playedTimes.setOnEditCommit(onEditPlayedTimes());
        col_playedTimes.setOnEditCancel(onEditPlayedTimes());
    }

    private void initCellValueFactory() {
        col_bpm.setCellValueFactory(new PropertyValueFactory<>(SongsRowDTO.PROP_BPM));
        col_album.setCellValueFactory(new PropertyValueFactory<>(SongsRowDTO.PROP_ALBUM));
        col_artist.setCellValueFactory(new PropertyValueFactory<>(SongsRowDTO.PROP_ARTIST));
        col_id.setCellValueFactory(new PropertyValueFactory<>(SongsRowDTO.PROP_ID));
        col_length.setCellValueFactory(new PropertyValueFactory<>(SongsRowDTO.PROP_LENGTH));
        col_path.setCellValueFactory(new PropertyValueFactory<>(SongsRowDTO.PROP_PATH));
        col_name.setCellValueFactory(new PropertyValueFactory<>(SongsRowDTO.PROP_NAME));
        col_playedTimes.setCellValueFactory(new PropertyValueFactory<>(SongsRowDTO.PROP_PLAYED_TIMES));
    }

    @NotNull
    private EventHandler<TableColumn.CellEditEvent<SongsRowDTO, Integer>> onEditPlayedTimes() {
        return event -> {
            Integer newValue = event.getNewValue();
            Integer oldValue = event.getOldValue();
            if (newValue == null) {
                newValue = oldValue;
            }
            if (newValue < 0) {
                newValue = 0;
            }
            SongsRowDTO songsRowDTO = event.getTableView().getItems().get(
                    event.getTablePosition().getRow());
            if (!newValue.equals(oldValue)) {
                musicLibUsecase.updateSongPlayedTimes(songsRowDTO.getId(), newValue);
            }
            songsRowDTO.setPlayedTimes(newValue);
        };
    }


    public static class LibSongsViewData{
        @NotNull
        public static Integer libId = -1;
    }
}
