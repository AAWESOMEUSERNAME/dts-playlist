package com.gugu.dts.playlist.ui.utils;

import org.springframework.util.NumberUtils;

import java.util.AbstractMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author chenyiyang
 * @date 2020/10/23
 */
public class GeneratorNumberUtils {

    private static final Pattern DURATION = Pattern.compile("^(?<f>\\d+):?(?<s>\\d)*$");
    private static final Pattern NUMBER = Pattern.compile("^\\d+$");

    public static boolean isNotNumber(String s) {
        if (s == null) {
            return false;
        }
        return !NUMBER.matcher(s).find();
    }

    public static boolean isFormatedDuration(String s) {
        return !isNotFormatedDuration(s);
    }

    public static boolean isNotFormatedDuration(String s) {
        if (s == null) {
            return false;
        }
        s = s.replaceAll("：", ":");
        return !DURATION.matcher(s).find();
    }

    public static Map.Entry<Integer, Integer> extractFrom(String str) {
        str = str.replaceAll("：", ":");
        Matcher matcher = DURATION.matcher(str);
        if (!matcher.find()) {
            throw new RuntimeException("not supported:" + str);
        }
        String f = matcher.group("f");
        String s = matcher.group("s");
        if (s == null) {
            return new AbstractMap.SimpleEntry<>(null, NumberUtils.parseNumber(f, Integer.class));
        }
        return new AbstractMap.SimpleEntry<>(NumberUtils.parseNumber(f, Integer.class), NumberUtils.parseNumber(s, Integer.class));
    }

    public static Double toNumber(String str) {
        Matcher numMatcher = NUMBER.matcher(str);
        if (numMatcher.find()) {
            return NumberUtils.parseNumber(str, Double.class);
        }
        Matcher durMatcher = DURATION.matcher(str);
        if (durMatcher.find()) {
            return NumberUtils.parseNumber(durMatcher.group("f"), Double.class) * 60 +
                    NumberUtils.parseNumber(durMatcher.group("s"), Double.class);
        }
        throw new RuntimeException("not supported String format");
    }
}
