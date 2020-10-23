package com.gugu.dts.playlist.ui.constants;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chenyiyang
 * @date 2020/10/23
 */
public enum FilterablePropertyEnum {
    BPM("BPM"), TRACK_LENGTH("音乐时长");

    @Getter
    String propName;

    FilterablePropertyEnum(String propName) {
        this.propName = propName;
    }

    public static FilterablePropertyEnum ofProp(String prop) {
        List<FilterablePropertyEnum> result = Arrays.stream(FilterablePropertyEnum.values()).filter(it -> it.propName.equals(prop)).collect(Collectors.toList());
        if (result.size() != 1) {
            throw new RuntimeException("无法找到唯一对应的属性");
        }
        return result.get(0);
    }

}
