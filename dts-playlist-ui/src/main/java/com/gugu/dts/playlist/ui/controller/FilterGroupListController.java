package com.gugu.dts.playlist.ui.controller;

import com.gugu.dts.playlist.ui.dto.FilterGroupRowDTO;
import com.gugu.dts.playlist.ui.usecase.GeneratorUsecase;
import com.gugu.dts.playlist.ui.utils.AlertUtil;
import com.gugu.dts.playlist.ui.view.FilterGroupDetailView;
import com.gugu.dts.playlist.ui.view.FilterGroupListView;
import de.felixroske.jfxsupport.FXMLController;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static com.gugu.dts.playlist.ui.controller.FilterGroupListController.FilterGroupListData.CURRENT_GROUP;


/**
 * @author chenyiyang
 * @date 2020/10/30
 */
@FXMLController
public class FilterGroupListController implements Initializable, EventHandler<WindowEvent> {

    private GeneratorUsecase musicLibUsecase;

    @FXML
    private TableView<FilterGroupRowDTO> table_groups;
    @FXML
    private TableColumn<FilterGroupRowDTO, String> col_name;
    @FXML
    private TableColumn<FilterGroupRowDTO, Integer> col_sum;
    @FXML
    private TableColumn<FilterGroupRowDTO, String> col_condition;

    public FilterGroupListController(GeneratorUsecase musicLibUsecase) {
        this.musicLibUsecase = musicLibUsecase;
    }

    @FXML
    void addFilterGroup(MouseEvent event) {
        FilterGroupDetailController.FilterGroupData.clear();
        openDetail();
    }

    @FXML
    void alterFilterGroup(MouseEvent event) {
        if (CURRENT_GROUP == null) {
            AlertUtil.warn("请选择一个过滤器");
        }
        FilterGroupDetailController.FilterGroupData.CURRENT_FILTER_GROUP = CURRENT_GROUP.getId();
        openDetail();
    }

    private void openDetail() {
        Stage stage = MainController.StageContainer.get(FilterGroupDetailView.class.getSimpleName());
        if (stage.isShowing()) {
            AlertUtil.warn("有未提交的新建窗口还没有关闭");
        }
        stage.showAndWait();
        initTableData();
    }

    @FXML
    void deleteFilterGroup(MouseEvent event) {
        if (CURRENT_GROUP == null) {
            AlertUtil.warn("请选择一个过滤器");
        }
        if (AlertUtil.comfirm("确认删除该过滤器么？")) {
            musicLibUsecase.deleteGroup(CURRENT_GROUP.getId());
            initTableData();
        }
    }

    @Override
    public void handle(WindowEvent event) {
        EventType<WindowEvent> eventType = event.getEventType();
        if (eventType.equals(WindowEvent.WINDOW_SHOWN)) {
            initTableData();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCellValueFactory();
        initTableData();
        initTableListener();
    }

    private void initCellValueFactory() {
        col_name.setCellValueFactory(new PropertyValueFactory<>(FilterGroupRowDTO.PROP_NAME));
        col_condition.setCellValueFactory(new PropertyValueFactory<>(FilterGroupRowDTO.PROP_CONDITION));
        col_sum.setCellValueFactory(new PropertyValueFactory<>(FilterGroupRowDTO.PROP_NUM));
    }

    private void initTableData() {
        List<FilterGroupRowDTO> groupRowDTOS = musicLibUsecase.loadGroups();
        table_groups.setItems(FXCollections.observableList(groupRowDTOS));
    }

    private void initTableListener() {
        table_groups.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                CURRENT_GROUP = newValue;
            } else {
                CURRENT_GROUP = null;
            }
        });
        table_groups.setRowFactory(tv -> {
            TableRow<FilterGroupRowDTO> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    FilterGroupRowDTO rowData = row.getItem();
                    commit(rowData);
                }
            });
            return row;
        });
    }

    private void commit(FilterGroupRowDTO dto) {
        MainController.MainViewData.GROUPS.add(dto);
        Stage stage = MainController.StageContainer.get(FilterGroupListView.class.getSimpleName());
        stage.close();
    }

    public static class FilterGroupListData {
        public static FilterGroupRowDTO CURRENT_GROUP;
    }
}
