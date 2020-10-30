package com.gugu.dts.playlist.ui.controller;

import com.gugu.dts.playlist.ui.usecase.GeneratorUsecase;
import com.gugu.dts.playlist.ui.utils.AlertUtil;
import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.springframework.util.StringUtils;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import static de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport.getStage;

/**
 * @author chenyiyang
 * @date 2020/9/30
 */
@FXMLController
public class ImportLibController implements Initializable {

    private Stage rootStage;
    private GeneratorUsecase musicLibUsecase;
    @FXML
    private Label lab_path;
    @FXML
    private TextField in_libName;
    private File selectedDirectory;

    public ImportLibController(GeneratorUsecase musicLibUsecase) {
        this.musicLibUsecase = musicLibUsecase;
    }

    @FXML
    void chooseDir(MouseEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File newChosen = directoryChooser.showDialog(rootStage);
        selectedDirectory = newChosen == null ? selectedDirectory : newChosen;
        if (this.selectedDirectory != null) {
            lab_path.setText(selectedDirectory.getAbsolutePath());
        }
    }

    @FXML
    void confirm(MouseEvent event) {
        if(selectedDirectory == null){
            AlertUtil.warn("请选择文件夹");
            return;
        }
        if (StringUtils.isEmpty(in_libName.getText())){
            AlertUtil.warn("请为音乐库命名");
            return;
        }
        musicLibUsecase.importLib(selectedDirectory,in_libName.getText());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rootStage = getStage();
    }
}
