package com.gugu.dts.playlist.ui.controller;

import com.gugu.dts.playlist.api.object.IFilterDTO;
import com.gugu.dts.playlist.api.object.IRuleDTO;
import com.gugu.dts.playlist.ui.dto.FilterRowDTO;
import com.gugu.dts.playlist.ui.dto.LibRowDTO;
import com.gugu.dts.playlist.ui.usecase.MusicLibUsecase;
import com.gugu.dts.playlist.ui.utils.AlertUtil;
import com.gugu.dts.playlist.ui.view.ChooseLibDirView;
import de.felixroske.jfxsupport.FXMLController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import kotlin.Pair;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport.getStage;


/**
 * @author chenyiyang
 * @date 2020/9/28
 */
@FXMLController
public class MainController implements Initializable {

    private Stage rootStage;
    private Stage chooseDirStage;
    private MusicLibUsecase musicLibUsecase;
    private ChooseLibDirView chooseLibDirView;

    @FXML
    private TextField in_totalNum;
    @FXML
    private Label lab_currentLib;
    private Long currentLibId;

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
    private TableView<FilterRowDTO> table_filter;
    @FXML
    private TableColumn<FilterRowDTO, Double> col_filter_bpmMin;
    @FXML
    private TableColumn<FilterRowDTO, Double> col_filter_bpmMax;
    @FXML
    private TableColumn<FilterRowDTO, Integer> col_filter_songNum;

    public MainController(MusicLibUsecase musicLibUsecase, ChooseLibDirView chooseLibDirView) {
        this.musicLibUsecase = musicLibUsecase;
        this.chooseLibDirView = chooseLibDirView;
    }

    @FXML
    void generatePlaylist(MouseEvent event) {
        ObservableList<FilterRowDTO> filters = table_filter.getItems();

        if (filters.size() == 0) {
            AlertUtil.warn("至少需要添加一个筛选器");
            return;
        }
        if (currentLibId == null) {
            AlertUtil.warn("请选择一个音乐库");
            return;
        }


        List<Pair<Integer, IFilterDTO>> filterDtos = filters.stream().<Pair<Integer, IFilterDTO>>map(row -> new Pair<>(row.getSongNum(), new IFilterDTO() {
            @Override
            public double getStartBpm() {
                return row.getBpmMin();
            }

            @Override
            public double getEndBpm() {
                return row.getBpmMax();
            }
        })).collect(Collectors.toList());
        IRuleDTO ruleDTO = new IRuleDTO() {
            @NotNull
            @Override
            public List<Pair<Integer, IFilterDTO>> getFilters() {
                return filterDtos;
            }

            @Override
            public boolean getRepeatable() {
                return false;
            }

            @Override
            public int getTotalNeeded() {
                return Integer.parseInt(in_totalNum.getText());
            }
        };

        File file = musicLibUsecase.generatePlayList(currentLibId, ruleDTO);
        AlertUtil.success("文件生成成功！路径：" + file.getAbsolutePath());
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
        table_filter.getItems().add(FilterRowDTO.EMPTY_ROW);
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
        makeTableEditable();

        Parent view = chooseLibDirView.getView();
        Scene scene = new Scene(view);
        chooseDirStage = new Stage();
        chooseDirStage.initOwner(rootStage);
        chooseDirStage.setScene(scene);
    }

    private void initTableListener() {
        table_musicLib.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                lab_currentLib.setText(newValue.getName());
                currentLibId = newValue.getId();
            }
        });
    }

    private void initTableData() {
        List<LibRowDTO> rows = musicLibUsecase.load();
        table_musicLib.setItems(FXCollections.observableList(rows));
    }

    private void makeTableEditable() {
        table_filter.getSelectionModel().cellSelectionEnabledProperty().set(true);
        col_filter_bpmMin.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleToStringConverter()));
        col_filter_bpmMin.setOnEditCommit(event -> {
            Double newValue = event.getNewValue();
            if (newValue < 0) {
                AlertUtil.warn("bpm的值应该大于0");
                return;
            }

            (event.getTableView().getItems().get(
                    event.getTablePosition().getRow())
            ).setBpmMin(newValue);
        });
        col_filter_bpmMax.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleToStringConverter()));
        col_filter_bpmMax.setOnEditCommit(event -> {
            Double newValue = event.getNewValue();
            if (newValue < 0) {
                AlertUtil.warn("bpm的值应该大于0");
                return;
            }

            (event.getTableView().getItems().get(
                    event.getTablePosition().getRow())
            ).setBpmMax(newValue);
        });
        col_filter_songNum.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerToStringConverter()));
        col_filter_songNum.setOnEditCommit(event -> {
            Integer newValue = event.getNewValue();
            if (newValue < 0) {
                AlertUtil.warn("歌曲数值应该大于0");
                return;
            }

            (event.getTableView().getItems().get(
                    event.getTablePosition().getRow())
            ).setSongNum(newValue);
        });
    }

    private void initCellValueFactory() {
        col_filter_bpmMin.setCellValueFactory(new PropertyValueFactory<>(FilterRowDTO.PROP_MIN));
        col_filter_bpmMax.setCellValueFactory(new PropertyValueFactory<>(FilterRowDTO.PROP_MAX));
        col_filter_songNum.setCellValueFactory(new PropertyValueFactory<>(FilterRowDTO.PROP_NUM));

        col_musicLib_id.setCellValueFactory(new PropertyValueFactory<>(LibRowDTO.PROP_ID));
        col_musicLib_libName.setCellValueFactory(new PropertyValueFactory<>(LibRowDTO.PROP_NAME));
        col_musicLib_importTime.setCellValueFactory(new PropertyValueFactory<>(LibRowDTO.PROP_IMPORT_TIME));
        col_musicLib_path.setCellValueFactory(new PropertyValueFactory<>(LibRowDTO.PROP_PATH));
    }

}

class DoubleToStringConverter extends StringConverter<Double> {
    @Override
    public String toString(Double object) {
        return object.toString();
    }

    @Override
    public Double fromString(String string) {
        return Double.parseDouble(string);
    }
}

class IntegerToStringConverter extends StringConverter<Integer> {
    @Override
    public String toString(Integer object) {
        return object.toString();
    }

    @Override
    public Integer fromString(String string) {
        return Integer.parseInt(string);
    }
}
