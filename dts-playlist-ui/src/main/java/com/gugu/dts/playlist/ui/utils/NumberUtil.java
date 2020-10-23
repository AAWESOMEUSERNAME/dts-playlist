package com.gugu.dts.playlist.ui.utils;

import org.springframework.util.NumberUtils;

import java.util.AbstractMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author chenyiyang
 * @date 2020/10/23
 */
public class NumberUtil {

    private static final Pattern DURATION = Pattern.compile("^(?<f>\\d+):*(?<s>\\d)*$");
    private static final Pattern NUMBER = Pattern.compile("^\\d+$");

    public static boolean isNotNumber(String s) {
        if (s == null) {
            return false;
        }
        return !NUMBER.matcher(s).find();
    }

    public static boolean isNotFormatedDuration(String s) {
        if (s == null) {
            return false;
        }
        return !DURATION.matcher(s).find();
    }

    public static Map.Entry<Integer, Integer> extractFrom(String str) {
        if (isNotFormatedDuration(str)) {
            throw new RuntimeException("not supported:" + str);
        }
        String f = DURATION.matcher(str).group("f");
        String s = DURATION.matcher(str).group("s");
        if (s == null) {
            return new AbstractMap.SimpleEntry<>(null, NumberUtils.parseNumber(f, Integer.class));
        }
        return new AbstractMap.SimpleEntry<>(NumberUtils.parseNumber(f, Integer.class), NumberUtils.parseNumber(s, Integer.class));
    }
}
