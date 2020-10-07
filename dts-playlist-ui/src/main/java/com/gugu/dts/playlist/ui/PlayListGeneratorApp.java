package com.gugu.dts.playlist.ui;

import com.gugu.dts.playlist.ui.utils.AlertUtil;
import com.gugu.dts.playlist.ui.view.MainView;
import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author chenyiyang
 * @date 2020/9/28
 */
@SpringBootApplication
@Slf4j
@EnableConfigurationProperties(AppProperties.class)
public class PlayListGeneratorApp extends AbstractJavaFxApplicationSupport {

    public static void main(String[] args) {
        launch(PlayListGeneratorApp.class, MainView.class,args);
    }

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Thread.currentThread().setUncaughtExceptionHandler((thread, throwable) -> {
            log.error("something went wrong", throwable);
            AlertUtil.exception(throwable);
        });

        stage.setTitle("播放列表生成器");
        stage.setResizable(false);
        super.start(stage);
    }
}
