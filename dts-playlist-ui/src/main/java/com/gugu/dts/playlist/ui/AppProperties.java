package com.gugu.dts.playlist.ui;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app")
@Getter
@Setter
public class AppProperties {
    String outPutFile = System.getProperty("user.dir") + "/playlist.m3u";
}
