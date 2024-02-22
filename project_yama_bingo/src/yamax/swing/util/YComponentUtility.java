package yamax.swing.util;

import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.Icon;

import yamax.swing.YComponentConstants;

/**
 * コンポーネント用ユーティリティクラスです。
 */
public final class YComponentUtility {
	/**
	 * YComponentUtility を構築します。
	 */
	private YComponentUtility() {
	}
	
	/**
	 * 対象全てのアイコンが登録されているか判断します。
	 * @param icons アイコン配列
	 * @return boolean 判断結果 - 全てのアイコンが登録されている場合は true
	 */
	public static boolean hasAllIcon(Icon[] icons) {
		// 
		if (icons == null) {
			return false;
		}
		// 
		for (Icon icon : icons) {
			if (icon == null) {
				return false;
			}
		}
		// 
		return true;
	}
	
	/**
	 * 複数行テキストに変換します。
	 * @param s 対象文字列
	 * @return String[] 変換配列
	 */
	public static String[] toMultilineText(String s) {
		return toMultilineText(s, YComponentConstants.LINEFEED);
	}
	
	/**
	 * 単一行テキストに変換します。
	 * @param s 対象文字配列
	 * @param b 行送り文字を含む場合は true
	 * @return String 変換文字列
	 */
	public static String toSinglelineText(String[] s, boolean b) {
		return toSinglelineText(s, b ? YComponentConstants.LINEFEED : "");
	}
	
	/**
	 * 複数行テキストに変換します。
	 * @param s 対象文字列
	 * @param linefeed 行送り文字
	 * @return String[] 変換配列
	 */
	private static String[] toMultilineText(String s, final String linefeed) {
		// 
		if (s == null) {
			return new String[] {};
		}
		// 
		ArrayList<String> list = new ArrayList<>();
		StringTokenizer token = new StringTokenizer(s, linefeed, true);
		// 
		for (boolean wasdelim = true; token.hasMoreTokens();) {
			String temp = token.nextToken();
			// 
			if (temp.equals(linefeed)) {
				if (wasdelim) {
					list.add("");
				}
				if (!token.hasMoreTokens()) {
					list.add("");
				}
				// 
				wasdelim = true;
			} else {
				list.add(temp);
				// 
				wasdelim = false;
			}
		}
		// 
		return list.toArray(new String[0]);
	}
	
	/**
	 * 単一行テキストに変換します。
	 * @param s 対象文字配列
	 * @param linefeed 行送り文字
	 * @return String 変換文字列
	 */
	private static String toSinglelineText(String[] s, final String linefeed) {
		if (s == null) {
			return "";
		}
		// 
		StringBuilder builder = new StringBuilder();
		// 
		for (int i = 0; i < s.length; i ++) {
			if (i != 0) {
				builder.append(linefeed);
			}
			builder.append(s[i]);
		}
		// 
		return builder.toString();
	}
}
