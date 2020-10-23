package com.gugu.dts.playlist.ui.controller;

import com.gugu.dts.playlist.ui.dto.FilterGroupRowDTO;
import com.gugu.dts.playlist.ui.dto.LibRowDTO;
import com.gugu.dts.playlist.ui.usecase.MusicLibUsecase;
import com.gugu.dts.playlist.ui.utils.AlertUtil;
import com.gugu.dts.playlist.ui.view.ChooseLibDirView;
import com.gugu.dts.playlist.ui.view.FilterGroupView;
import com.gugu.dts.playlist.ui.view.LibSongsView;
import de.felixroske.jfxsupport.FXMLController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import static com.gugu.dts.playlist.ui.controller.MainController.MainViewData.currentLibId;
import static com.gugu.dts.playlist.ui.controller.MainController.MainViewData.groups;
import static de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport.getStage;


/**
 * @author chenyiyang
 * @date 2020/9/28
 */
@FXMLController
public class MainController implements Initializable {

    private Stage rootStage;
    private Stage chooseDirStage;
    private Stage libSongsStage;
    private Stage filterGroupStage;

    private LibSongsController libSongsController;
    private MusicLibUsecase musicLibUsecase;
    private ChooseLibDirView chooseLibDirView;
    private LibSongsView playlistSongsView;
    private FilterGroupView filterGroupView;

    @FXML
    private TextField in_totalNum;
    @FXML
    private Label lab_currentLib;
    @FXML
    private CheckBox cb_fairly;

    @FXML
    private TableView<LibRowDTO> table_musicLib;
    @FXML
    private TableColumn<LibRowDTO, Long> col_musicLib_id;
    @FXML
    private TableColumn<LibRowDTO, Date> col_musicLib_importTime;
    @FXML
    private TableColumn<LibRowDTO, String> col_musicLib_path;
    @FXML
    private TableColumn<LibRowDTO, String> col_musicLib_libName;

    @FXML
    private TableView<FilterGroupRowDTO> table_filter;
    @FXML
    private TableColumn<FilterGroupRowDTO, Integer> col_filter_songNum;
    @FXML
    private TableColumn<FilterGroupRowDTO, String> col_filter_condition;

    public MainController(LibSongsController libSongsController, MusicLibUsecase musicLibUsecase, ChooseLibDirView chooseLibDirView, LibSongsView playlistSongsView, FilterGroupView filterGroupView) {
        this.libSongsController = libSongsController;
        this.musicLibUsecase = musicLibUsecase;
        this.chooseLibDirView = chooseLibDirView;
        this.playlistSongsView = playlistSongsView;
        this.filterGroupView = filterGroupView;
    }

    @FXML
    void showMusicList(MouseEvent event) {
        if (currentLibId == null) {
            AlertUtil.warn("请选择一个音乐库");
        }
        LibSongsController.LibSongsViewData.libId = currentLibId;
        if (libSongsStage.isShowing()) {
            libSongsStage.close();
        }
        libSongsStage.showAndWait();
    }

    @FXML
    void generatePlaylist(MouseEvent event) {
        ObservableList<FilterGroupRowDTO> filters = table_filter.getItems();

        if (filters.size() == 0) {
            AlertUtil.warn("至少需要添加一个筛选器");
            return;
        }
        if (currentLibId == null) {
            AlertUtil.warn("请选择一个音乐库");
            return;
        }
        cb_fairly.isSelected();
        // todo
//        File file = musicLibUsecase.generatePlayList(currentLibId, filters);
//        AlertUtil.success("文件生成成功！路径：" + file.getAbsolutePath());
        AlertUtil.success("文件生成成功！路径：");
    }

    @FXML
    void deleteLib(MouseEvent event) {
        boolean comfirm = AlertUtil.comfirm("确认删除这个音乐库么？");
        if (comfirm) {
            musicLibUsecase.deleteLib(currentLibId);
            initTableData();
        }
    }

    @FXML
    void addFilter(MouseEvent event) {
        if (filterGroupStage.isShowing()) {
            AlertUtil.warn("上一次新建还没有提交或关闭");
            return;
        }
        FilterGroupController.FilterGroupData.clear();
        filterGroupStage.showAndWait();
        refreshFilterGroups();
    }

    @FXML
    void deleteFilter(MouseEvent event) {
        refreshFilterGroups();
    }

    @FXML
    void importLib(MouseEvent event) {
        if (chooseDirStage.isShowing()) {
            return;
        }
        chooseDirStage.showAndWait();
        initTableData();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rootStage = getStage();
        initCellValueFactory();
        initTableData();
        initTableListener();

        chooseDirStage = new Stage();
        chooseDirStage.initOwner(rootStage);
        chooseDirStage.setScene(new Scene(chooseLibDirView.getView()));

        libSongsStage = new Stage();
        libSongsStage.initOwner(rootStage);
        libSongsStage.setScene(new Scene(playlistSongsView.getView()));
        libSongsStage.setOnShown(libSongsController);

        filterGroupStage = new Stage();
        filterGroupStage.initOwner(rootStage);
        filterGroupStage.setScene(new Scene(filterGroupView.getView()));
    }

    private void initTableListener() {
        table_musicLib.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                lab_currentLib.setText(newValue.getName());
                currentLibId = newValue.getId();
            } else {
                lab_currentLib.setText("未选择");
                currentLibId = null;
            }
        });
    }

    private void initTableData() {
        List<LibRowDTO> rows = musicLibUsecase.load();
        table_musicLib.setItems(FXCollections.observableList(rows));
    }

    private void initCellValueFactory() {
        col_filter_condition.setCellValueFactory(new PropertyValueFactory<>(FilterGroupRowDTO.PROP_CONDITION));
        col_filter_songNum.setCellValueFactory(new PropertyValueFactory<>(FilterGroupRowDTO.PROP_NUM));

        col_musicLib_id.setCellValueFactory(new PropertyValueFactory<>(LibRowDTO.PROP_ID));
        col_musicLib_libName.setCellValueFactory(new PropertyValueFactory<>(LibRowDTO.PROP_NAME));
        col_musicLib_importTime.setCellValueFactory(new PropertyValueFactory<>(LibRowDTO.PROP_IMPORT_TIME));
        col_musicLib_path.setCellValueFactory(new PropertyValueFactory<>(LibRowDTO.PROP_PATH));
    }

    public void refreshFilterGroups() {
        table_filter.setItems(FXCollections.observableList(groups));
    }

    public static class MainViewData {
        public static Long currentLibId;
        public static List<FilterGroupRowDTO> groups = new ArrayList<>();
    }
}
