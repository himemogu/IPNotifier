package org.himemogura.ipnotifier;

/**
 * Created by pri on 2016/02/13.
 */
public class Util {

	public static String getBefoerWord(String html, String word) {
		int pos = html.indexOf(word);
		if (pos < 0) {
			return word;
		}
		return html.substring(0, pos);
	}

	public static String seekWord(String html, String word) {
		int pos = html.indexOf(word);
		if (pos < 0) {
			return word;
		}
		return html.substring(pos + word.length());
	}

}
