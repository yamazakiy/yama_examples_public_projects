package yama.bingo.service;

/**
 * BingoService メッセージキー定義です。
 */
public enum BingoServiceMessageKey {
	/** メッセージキー サービスタイトル */
	SERVICE_TITLE("message.service_title"),
	/** メッセージキー フリーマーク */
	FREE_MARK("message.free_mark"),
	/** メッセージキー 情報 - サービス開始通知 */
	INFO_SERVICE_STARTED("message.info_service_started"),
	/** メッセージキー 情報 - リセット通知 */
	INFO_SERVICE_RESETED("message.info_service_reseted"),
	/** メッセージキー 情報 - ビンゴ抽選通知 */
	INFO_BINGONUMBER_PICKED("message.info_bingonumber_picked"),
	/** メッセージキー 情報 - ビンゴ抽選完了通知 */
	INFO_BINGONUMBER_EMPTY("message.info_bingonumber_empty"),
	;
	
	/** プロパティキー */
	private final String key;
	
	/**
	 * BingoServiceMessageKey を構築します。
	 * @param key プロパティキー
	 */
	private BingoServiceMessageKey(String key) {
		this.key = key;
	}
	
	/**
	 * プロパティキーを取得します。
	 * @return String プロパティキー
	 */
	public String getKey() {
		// 
		return key;
	}
}
