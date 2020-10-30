package com.gugu.dts.playlist.ui.controller;

import com.gugu.dts.playlist.ui.constants.FilterablePropertyEnum;
import com.gugu.dts.playlist.ui.dto.FilterGroupRowDTO;
import com.gugu.dts.playlist.ui.dto.FilterRowDTO;
import com.gugu.dts.playlist.ui.usecase.GeneratorUsecase;
import com.gugu.dts.playlist.ui.utils.AlertUtil;
import com.gugu.dts.playlist.ui.utils.GeneratorNumberUtils;
import de.felixroske.jfxsupport.FXMLController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static com.gugu.dts.playlist.ui.controller.FilterGroupDetailController.FilterGroupData.CURRENT_FILTER_GROUP;
import static com.gugu.dts.playlist.ui.controller.FilterGroupDetailController.FilterGroupData.GROUP;

/**
 * @author chenyiyang
 * @date 2020/10/23
 */
@FXMLController
public class FilterGroupDetailController implements Initializable, EventHandler<WindowEvent> {
    private GeneratorUsecase usecase;

    public FilterGroupDetailController(GeneratorUsecase usecase) {
        this.usecase = usecase;
    }

    @FXML
    private ChoiceBox<String> cob_property;

    @FXML
    private TextField in_min;
    @FXML
    private TextField in_max;
    @FXML
    private TextField in_sum;
    @FXML
    private TextField in_name;

    @FXML
    private TableView<FilterRowDTO> table_filters;
    @FXML
    private TableColumn<FilterRowDTO, String> col_min;
    @FXML
    private TableColumn<FilterRowDTO, String> col_max;
    @FXML
    private TableColumn<FilterRowDTO, String> col_property;

    @FXML
    void addFilter(MouseEvent event) {
        SingleSelectionModel<String> selectionModel = cob_property.getSelectionModel();
        if (selectionModel.isEmpty()) {
            AlertUtil.warn("请选择需要筛选的属性");
            return;
        }
        String propertyName = selectionModel.getSelectedItem();

        String min = in_min.getText();
        if (StringUtils.isEmpty(min)) {
            AlertUtil.warn("请输入最小值");
            return;
        }
        String max = in_max.getText();
        if (StringUtils.isEmpty(max)) {
            AlertUtil.warn("请输入最大值");
            return;
        }

        boolean result = false;
        switch (FilterablePropertyEnum.ofProp(propertyName)) {
            case BPM:
                result = validateBpm(min, max);
                break;
            case TRACK_LENGTH:
                result = validateLength(min, max);
                break;
        }
        if (!result) {
            return;
        }

        GROUP.addFilter(new FilterRowDTO(propertyName, min, max));
        refresh();
    }

    private boolean validateBpm(String min, String max) {
        if (GeneratorNumberUtils.isNotNumber(min) || GeneratorNumberUtils.isNotNumber(max)) {
            AlertUtil.warn("bpm只能是数字");
            return false;
        }
        Double minD = GeneratorNumberUtils.toNumber(min);
        Double maxD = GeneratorNumberUtils.toNumber(max);
        if (minD.compareTo(maxD) > 0) {
            AlertUtil.warn("最小值不能大于最大值");
            return false;
        }
        return true;
    }

    private boolean validateLength(String min, String max) {
        if (GeneratorNumberUtils.isNotFormatedDuration(min) || GeneratorNumberUtils.isNotFormatedDuration(max)) {
            AlertUtil.warn("无效的长度，请按照 分:秒 或 秒 的格式输入，如 4:30 代表音乐长度4分30秒, 50 代表音乐长度50秒");
            return false;
        }
        Map.Entry<Integer, Integer> minM = GeneratorNumberUtils.extractFrom(min);
        Map.Entry<Integer, Integer> maxM = GeneratorNumberUtils.extractFrom(max);
        if (minM.getValue().compareTo(60) > 0 || maxM.getValue().compareTo(60) > 0) {
            AlertUtil.warn("秒数不应大于60");
            return false;
        }
        return true;
    }

    @FXML
    void deleteFilter(MouseEvent event) {
        FilterRowDTO selectedItem = table_filters.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            AlertUtil.warn("请选择要删除的行");
        }
        GROUP.deleteFilter(selectedItem);
        if (CURRENT_FILTER_GROUP != null) {
            usecase.updateGroup(CURRENT_FILTER_GROUP, GROUP);
        }
        refresh();
    }

    @FXML
    void commit(MouseEvent event) {
        String sumStr = in_sum.getText();
        if (GeneratorNumberUtils.isNotNumber(sumStr) || NumberUtils.parseNumber(sumStr, Integer.class).compareTo(0) <= 0) {
            AlertUtil.warn("请输入有效的数量");
            return;
        }
        int sum = GeneratorNumberUtils.toNumber(sumStr).intValue();

        ObservableList<FilterRowDTO> items = table_filters.getItems();
        if (items.size() == 0) {
            AlertUtil.warn("至少需要添加一个过滤条件");
            return;
        }
        GROUP.setName(in_name.getText());
        GROUP.setSongNum(sum);

        if (CURRENT_FILTER_GROUP == null) {
            usecase.insertGroup(GROUP);
        } else {
            usecase.updateGroup(CURRENT_FILTER_GROUP, GROUP);
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
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
        List<String> items = Arrays.stream(FilterablePropertyEnum.values()).map(FilterablePropertyEnum::getPropName).collect(Collectors.toList());
        cob_property.setItems(FXCollections.observableList(items));

        col_min.setCellValueFactory(new PropertyValueFactory<>(FilterRowDTO.PROP_MIN));
        col_max.setCellValueFactory(new PropertyValueFactory<>(FilterRowDTO.PROP_MAX));
        col_property.setCellValueFactory(new PropertyValueFactory<>(FilterRowDTO.PROP_NAME));

        initTableData();
    }

    private void initTableData() {
        if (CURRENT_FILTER_GROUP != null) {
            GROUP = usecase.loadGroup(CURRENT_FILTER_GROUP).orElseThrow(() -> new RuntimeException("无效的groupId:" + CURRENT_FILTER_GROUP));
        } else {
            GROUP = new FilterGroupRowDTO(null, null, 0);
        }
        refresh();
    }

    private void refresh() {
        in_sum.setText(String.valueOf(GROUP.getSongNum()));
        in_name.setText(GROUP.getName());
        table_filters.setItems(FXCollections.observableList(GROUP.getFilters()));
    }

    public static class FilterGroupData {
        public static Integer CURRENT_FILTER_GROUP;
        public static FilterGroupRowDTO GROUP;

        public static void clear() {
            CURRENT_FILTER_GROUP = null;
            GROUP = new FilterGroupRowDTO(null, null, 0);
        }
    }
}
