/*
 * Copyright (C) 2020 Developer Guilliman <developerguilliman@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.developerguilliman.cardEditor;

import java.util.List;

/**
 *
 * @author Developer Guilliman <developerguilliman@gmail.com>
 */
public class Utils {

    private Utils() {

    }

    public static <T> int getExactIndex(List<T> list, T element) {
        int i = 0;

        for (T listItem : list) {
            if (listItem == element) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public static int getBoundIndex(List<?> list, int index) {
        return Math.max(0, Math.min(list.size() - 1, index));
    }

    public static int getBoundIndexPlusOne(List<?> list, int index) {
        return Math.max(0, Math.min(list.size(), index));
    }

    public static String normalize(String str) {
        return (str != null) ? str : "";
    }

    public static String normalizeTrim(String str) {
        return (str != null) ? str.trim() : "";
    }

    public static String longestCommonWords(List<String> list) {
        String[] array = list.toArray(new String[0]);
        int aLen = array.length;
        if (aLen == 0) {
            return "";
        }

        String result = "";
        String s = array[0];
        int sLen = s.length();

        for (int i = 0; i < sLen; i = nextSpace(s, i) + 1) {
            for (int j = nextSpace(s, i); j <= sLen; j = nextSpace(s, j)) {

                String sub = s.substring(i, j);
                int k;
                for (k = 1; k < aLen; k++) {
                    if (!array[k].contains(sub)) {
                        break;
                    }
                }

                if (k == aLen && result.length() < sub.length()) {
                    result = sub;
                }

            }
        }

        return result;
    }

    private static int nextSpace(String s, int i) {
        int sLen = s.length();
        if (i == sLen) {
            return sLen + 1;
        }
        i = s.indexOf(' ', i + 1);
        return i < 0 ? sLen : i;
    }
}
