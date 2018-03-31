package com.color.card.util;

import android.util.Pair;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * String 的工具方法 提取业务中常用的业务工具集合;
 */
public class StringUtil {
    public static boolean isEmpty(String string) {
        return string == null || string.equals("");
    }

    public static String concatenate(List list, String separator) {
        return concatenate(list, separator, null);
    }

    public static String concatenate(List list, String separator, DecimalFormat decimalFormat) {
        if (list == null || list.size() == 0) return null;
        StringBuilder sb = new StringBuilder();
        for (Object obj :
                list) {
            String format;
            if (decimalFormat == null) {
                format = obj.toString();
            } else {
                format = decimalFormat.format(obj);
            }
            sb.append(format).append(separator);
        }
        String str = sb.toString();
        return str.substring(0, str.length() - 1);
    }

    // Join strings into one with seperator
    public static String join(String seperator, List<String> stringList) {
        StringBuilder sb = new StringBuilder();

        if (stringList == null)
            return sb.toString();

        for (Iterator<String> it = stringList.iterator(); it.hasNext(); ) {
            sb.append(it.next());
            if (it.hasNext())
                sb.append(seperator);
        }

        return sb.toString();
    }

    // Join string array into one with seperator
    private static String join(String seperator, String[] stringArray, int length) {
        StringBuilder sb = new StringBuilder();

        if (stringArray == null)
            return sb.toString();

        int minLength = length <= 0 ? stringArray.length : Math.min(stringArray.length, length);

        for (int i = 0; i < minLength; i++) {
            sb.append(stringArray[i]);
            if (i < minLength - 1)
                sb.append(seperator);
        }

        return sb.toString();
    }

    public static String join(String seperator, String[] stringArray) {
        return join(seperator, stringArray, 0);
    }

    // Split to List of strings
    public static List<String> split(String s, String seperator) {
        List<String> list = new ArrayList<>();

        if (isEmptyString(s))
            return list;

        String[] stringArray = s.split(seperator);
        for (String part : stringArray) {
            if (!StringUtil.isEmptyString(part))
                list.add(part);
        }

        return list;
    }

    // Check whether it is an empty string, including null or empty cases
    public static boolean isEmptyString(String s) {
        return s == null || s.length() == 0;
    }

    // 如果传入的String 类型数据为空则返回“未填写”
    public static String cancelEmptyString(String s) {
        return s == null || s.trim().length() == 0 ? "" : s.trim();
    }

    // Do deepcopy on string list
    public static List<String> deepCopyStringList(List<String> list) {
        List<String> newList = new ArrayList<>();
        for (String s : list)
            newList.add(s);

        return newList;
    }

    // Remove strings that belong to possibleList from originalList.
    //
    // Return a new pair whose 1st element is the list composed by strings in possibleList
    // and 2nd element is the list with left strings
    public static Pair<List<String>, List<String>> seperateStringsInList(List<String> originalList, List<String> possibleList) {
        List<String> existList = new ArrayList<>();
        List<String> leftList = new ArrayList<>();

        for (String s : originalList) {
            if (!possibleList.contains(s))
                leftList.add(s);
            else
                existList.add(s);
        }

        return new Pair<>(existList, leftList);
    }

    // Merge two string lists into one, with duplication between two lists eliminated.

    @SafeVarargs
    public static List<String> mergeStringLists(List<String>... lists) {
        List<String> merged = new ArrayList<>();

        if (lists != null) {
            for (List<String> list : lists) {
                if (list != null) {
                    for (String s : list) {
                        if (!merged.contains(s))
                            merged.add(s);
                    }
                }
            }
        }

        return merged;
    }
}
