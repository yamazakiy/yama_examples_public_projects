package yamax.lang;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 基本的なユーティリティクラスです。
 */
public final class YLangUtility {
	/**
	 * YLangUtility を構築します。
	 */
	private YLangUtility() {
	}
	
	/**
	 * 数値文字列を int に変換します。
	 * @param s 数値文字列
	 * @param d デフォルト値
	 * @return int 変換結果
	 */
	public static int toInt(String s, int d) {
		try {
			// 
			return Integer.parseInt(s);
		} catch (Exception e) {
			// 
			return d;
		}
	}
	
	/**
	 * 数値文字列を long に変換します。
	 * @param s 数値文字列
	 * @param d デフォルト値
	 * @return long 変換結果
	 */
	public static long toLong(String s, long d) {
		try {
			// 
			return Long.parseLong(s);
		} catch (Exception e) {
			// 
			return d;
		}
	}
	
	/**
	 * 文字列が空か判断します。
	 * @param s 対象文字列
	 * @return boolean 判断結果 - 文字列が null 、または空であれば true
	 */
	public static boolean isEmpty(String s) {
		// 
		if (s == null) {
			// 
			return true;
		}
		// 
		return s.length() == 0;
	}
	
	/**
	 * 文字列配列が空か判断します。
	 * @param s 文字列配列
	 */
	public static boolean isEmpty(String[] s) {
		// 
		if (s == null) {
			// 
			return true;
		}
		// 
		return s.length == 0;
	}
	
	/**
	 * 対象文字列が null かどうか判断し、 null の場合は代替文字列を返却します。
	 * @param s 対象文字列
	 * @param r 代替文字列
	 * @return 対象文字列、対象が null の場合は代替文字列
	 */
	public static String nvl(String s, String r) {
		// 
		if (s == null) {
			return r;
		}
		// 
		return s;
	}
	
	/**
	 * 例外スタックトレースを文字列表現にします。
	 * @param e 例外
	 * @return String 例外スタックトレースの文字列表現
	 */
	public static String toStackTraceString(Throwable e) {
		try (
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw)
		) {
			// 
			e.printStackTrace(pw);
			pw.flush();
			// 
			return sw.toString();
		} catch (Exception ee) {
			ee.printStackTrace();
			// 
			return "";
		}
	}
	
	/**
	 * 指定ミリ秒スリープします。
	 * @param millis スリープミリ秒
	 */
	public static void sleep(long millis) {
		try {
			// 
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
