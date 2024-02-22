package yamax.swing;

/**
 * コンポーネント規則定義クラスです。
 */
public final class YComponentRegulations {
	/**
	 * YComponentRegulations を構築します。
	 */
	private YComponentRegulations() {
	}
	
	/**
	 * コンポーネント描画アンチエイリアス設定を行うかの定義です。
	 * @return boolean 処理判断 - コンポーネント描画アンチエイリアス設定を行う場合は true
	 */
	public static boolean isUseAntialias() {
		// 
		return true;
	}
	
	/**
	 * 影文字処理を行うかの定義です。
	 * @return boolean 処理判断 - 影文字処理を行う場合は true
	 */
	public static boolean isUseShadowString() {
		// 
		return true;
	}
	
	/**
	 * 採用する影文字処理パターンの定義です。
	 * @return YComponentConstants.ShadowStringPattern 影文字処理パターン
	 */
	public static YComponentConstants.ShadowStringPattern getShadowStringPattern() {
		// 
		return YComponentConstants.ShadowStringPattern.REPETITION_2X2;
	}
}
