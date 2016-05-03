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

import java.util.Arrays;
import java.util.Iterator;

/**
 * @author langhsu
 *
 */
public class MediaUtils {
    private static String[] allowFiles = { ".gif", ".png", ".jpg", ".jpeg", ".bmp" };

    /**
     * @return boolean
     */
    public static boolean checkFileType(String fileName) {
        return checkFileType(allowFiles, fileName);
    }

    /**
     * @return boolean
     */
    public static boolean checkFileType(String[] allowFiles, String fileName) {
        Iterator<String> type = Arrays.asList(allowFiles).iterator();
        while (type.hasNext()) {
            String ext = type.next();
            if (fileName.toLowerCase().endsWith(ext)) {
                return true;
            }
        }
        return false;
    }
}
