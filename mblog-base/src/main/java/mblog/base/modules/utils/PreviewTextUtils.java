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

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.springframework.web.util.HtmlUtils;

/**
 * 截取文章摘要
 * 
 * @author langhsu
 */
public class PreviewTextUtils {

	/**
	 * 提取纯文本
	 * @param html 代码
	 * @return string
	 */
	public static String getText(String html) {
		if (html == null)
			return null;
		return Jsoup.clean(html, Whitelist.none());
	}
	
	/**
	 * 提取纯文本
	 * @param html 代码
	 * @param length 提取文本长度
	 * @return string
	 */
	public static String getText(String html, int length){
		String text = getText(html);
		text = StringUtils.abbreviate(text, length);
		return HtmlUtils.htmlEscape(text);
	}

	/**
	 * 以下标签可以通过 (b, em, i, strong, u. 纯文本)
	 * @param html 代码
	 * @return string
	 */
	public static String getSimpleHtml(String html) {
		if (html == null)
			return null;
		return Jsoup.clean(html, Whitelist.simpleText());
	}

	/**
	 * 获取文章中的img url
	 * @param html 代码
	 * @return string
	 */
	public static String getImgSrc(String html) {
		if (html == null)
			return null;
		Document doc = Jsoup.parseBodyFragment(html);
		Element image = doc.select("img").first();
		return image == null ? null : image.attr("src");
	}
}
