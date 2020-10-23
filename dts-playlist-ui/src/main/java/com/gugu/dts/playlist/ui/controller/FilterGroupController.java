package com.gugu.dts.playlist.ui.controller;

import com.gugu.dts.playlist.ui.constants.FilterablePropertyEnum;
import com.gugu.dts.playlist.ui.dto.FilterGroupRowDTO;
import com.gugu.dts.playlist.ui.dto.FilterRowDTO;
import com.gugu.dts.playlist.ui.utils.AlertUtil;
import com.gugu.dts.playlist.ui.utils.NumberUtil;
import de.felixroske.jfxsupport.FXMLController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static com.gugu.dts.playlist.ui.controller.FilterGroupController.FilterGroupData.FILTERS;
import static com.gugu.dts.playlist.ui.controller.FilterGroupController.FilterGroupData.SUM;

/**
 * @author chenyiyang
 * @date 2020/10/23
 */
@FXMLController
public class FilterGroupController implements Initializable {


    @FXML
    private ChoiceBox<String> cob_property;

    @FXML
    private TextField in_min;
    @FXML
    private TextField in_max;
    @FXML
    private TextField in_sum;

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
                result = validateBpm(min) && validateBpm(max);
                break;
            case TRACK_LENGTH:
                result = validateLength(min) && validateLength(max);
                break;
        }
        if (!result) {
            return;
        }

        FILTERS.add(new FilterRowDTO(propertyName, min, max));
        refresh();
    }

    private boolean validateBpm(String bpm) {
        if (NumberUtil.isNotNumber(bpm)) {
            AlertUtil.warn("bpm最小值只能是数字");
            return false;
        }
        return true;
    }

    private boolean validateLength(String length) {
        if (NumberUtil.isNotFormatedDuration(length)) {
            AlertUtil.warn("无效的长度，请按照 mm:ss 或 ss 的格式输入，如 4:30 代表音乐长度4分30秒, 50 代表音乐长度50秒");
            return false;
        }
        Map.Entry<Integer, Integer> minusAndSec = NumberUtil.extractFrom(length);
        if (minusAndSec.getValue().compareTo(60) > 0) {
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
        FILTERS.remove(selectedItem);
        refresh();
    }

    @FXML
    void commit(MouseEvent event) {
        String sum = in_sum.getText();
        if (NumberUtil.isNotNumber(sum) || NumberUtils.parseNumber(sum, Integer.class).compareTo(0) <= 0) {
            AlertUtil.warn("请输入有效的数量");
            return;
        }
        SUM = NumberUtils.parseNumber(sum, Integer.class);

        ObservableList<FilterRowDTO> items = table_filters.getItems();
        if (items.size() == 0) {
            AlertUtil.warn("至少需要添加一个过滤条件");
            return;
        }
        String condition = items.stream().map(it -> String.format("%s: [%s,%s)", it.getPropertyName(), it.getMin(), it.getMax())).reduce((s, s2) -> s + "," + s2).orElse("error");
        FilterGroupRowDTO groupRowDTO = new FilterGroupRowDTO(condition, SUM, items);
        MainController.MainViewData.groups.add(groupRowDTO);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<String> items = Arrays.stream(FilterablePropertyEnum.values()).map(FilterablePropertyEnum::getPropName).collect(Collectors.toList());
        cob_property.setItems(FXCollections.observableList(items));
        in_sum.setText(SUM == null ? "" : SUM.toString());

        col_min.setCellValueFactory(new PropertyValueFactory<>(FilterRowDTO.PROP_MIN));
        col_max.setCellValueFactory(new PropertyValueFactory<>(FilterRowDTO.PROP_MAX));
        col_property.setCellValueFactory(new PropertyValueFactory<>(FilterRowDTO.PROP_NAME));
    }

    private void refresh() {
        table_filters.setItems(FXCollections.observableList(FILTERS));
    }

    public static class FilterGroupData {
        public static List<FilterRowDTO> FILTERS = new ArrayList<>();
        public static Integer SUM = null;

        public static void clear() {
            FILTERS.clear();
            SUM = null;
        }
    }
}
