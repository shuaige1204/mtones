/*
+--------------------------------------------------------------------------
|   mtons [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package mblog.base.modules.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 文本工具类
 */
public class Text {

    /**
     * 将日期（时间）格式化成指定格式的字符串
     * @param date 日期(时间)对象
     * @param pattern 格式化模式
     * @return 格式化后的日期字符串
     */
    public static String formatTime(Date date, String pattern){
        return new SimpleDateFormat(pattern).format(date);
    }
    
    /**
     * 将日期（时间）字符串解析成日期时间对象
     * @param value 日期(时间)字符串
     * @param pattern 时间模式
     * @return 返回日期对象，如果解析失败，则返回null
     */
    public static Date parseTime(String value, String pattern){
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
    public static String filePath(long key, long seed, int deep){
        StringBuilder buf = new StringBuilder(32);
        for (int i = 0; i < deep; i++) {
            buf.append(key % seed).append('/');
            key = key / seed;
        }
        return buf.append(key).toString();
    }
    
}
