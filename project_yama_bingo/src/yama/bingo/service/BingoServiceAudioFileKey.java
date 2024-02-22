package yama.bingo.service;

/**
 * BingoService オーディオファイルキー定義です。
 */
public enum BingoServiceAudioFileKey {
	/** オーディオファイルキー ビンゴ抽選中 */
	BINGO_RUNNING("audio.bingo_running"),
	/** オーディオファイルキー ビンゴ抽選ストップ */
	BINGO_STOP("audio.bingo_stop"),
	;
	
	/** プロパティキー */
	private final String key;
	
	/**
	 * BingoServiceAudioFileKey を構築します。
	 * @param key プロパティキー
	 */
	private BingoServiceAudioFileKey(String key) {
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
