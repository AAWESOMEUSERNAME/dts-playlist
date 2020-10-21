package com.gugu.dts.playlist.ui.controller;

import com.gugu.dts.playlist.ui.dto.LibRowDTO;
import com.gugu.dts.playlist.ui.dto.SongsRowDTO;
import com.gugu.dts.playlist.ui.usecase.MusicLibUsecase;
import de.felixroske.jfxsupport.FXMLController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static com.gugu.dts.playlist.ui.controller.LibSongsController.LibSongsViewData.*;

/**
 * @author chenyiyang
 * @date 2020/10/21
 */
@FXMLController
public class LibSongsController implements Initializable {

    private MusicLibUsecase musicLibUsecase;

    public LibSongsController(MusicLibUsecase musicLibUsecase) {
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
    private TableView<SongsRowDTO> table_songs;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<SongsRowDTO> rows = musicLibUsecase.load(libId);
        table_songs.setItems(FXCollections.observableList(rows));
    }

    public static class LibSongsViewData{
        @NotNull public static Long libId = -1L;
    }
}
