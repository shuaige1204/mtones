/*
+--------------------------------------------------------------------------
|   mtons [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package mtons.modules.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Text {

    /**
     */
    public static String formatTime(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    public static Date parseTime(String value, String pattern) {
        try {
            return new SimpleDateFormat(pattern).parse(value);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Generating path
     * @param key unique value
     * @param seed maximum quantity
     * @param deep depth
     * @return string the path
     */
    public static String filePath(long key, long seed, int deep) {
        StringBuilder buf = new StringBuilder(32);
        for (int i = 0; i < deep; i++) {
            buf.append(key % seed).append('/');
            key = key / seed;
        }
        return buf.append(key).toString();
    }

}
