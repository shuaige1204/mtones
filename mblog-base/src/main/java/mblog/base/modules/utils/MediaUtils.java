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

import java.util.Arrays;
import java.util.Iterator;

/**
 * @author langhsu
 *
 */
public class MediaUtils {
	// 文件允许格式
	private static String[] allowFiles = { ".gif", ".png", ".jpg", ".jpeg", ".bmp" };
	
	/**
	 * 文件类型判断
	 * 
	 * @param fileName 文件名
	 * @return boolean
	 */
	public static boolean checkFileType(String fileName) {
		return checkFileType(allowFiles, fileName);
	}
	
	/**
	 * 文件类型判断
	 * 
	 * @param allowFiles 允许的文件名
	 * @param fileName 文件名
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
