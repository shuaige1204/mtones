/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package mtons.modules.utils;

import java.util.Date;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

/**
 * @author langhsu
 *
 */
public class FileNameUtils {
    private static String      YYYYMM         = "/yyyy/MMdd/";
    private static String      DDHHMMSS       = "ddHHmmss";

    private static String      YYYYMMDDHHMMSS = "/yyyy/MMdd/ddHHmmss";

    /**
     */
    public static final char[] N36_CHARS      = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
            'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

    /**
     * 
     * yyyyMM 200806
     * 
     * @return
     */
    public static String genPathName() {
        return DateFormatUtils.format(new Date(), YYYYMM);
    }

    /**
     */
    public static String genFileName() {
        return DateFormatUtils.format(new Date(), DDHHMMSS)
               + RandomStringUtils.random(4, N36_CHARS);
    }

    /**
     */
    public static String genFileName(String ext) {
        return genFileName() + "." + ext;
    }

    public static String genPathAndFileName(String ext) {
        return DateFormatUtils.format(new Date(), YYYYMMDDHHMMSS)
               + RandomStringUtils.random(4, N36_CHARS) + "." + ext;
    }

}
