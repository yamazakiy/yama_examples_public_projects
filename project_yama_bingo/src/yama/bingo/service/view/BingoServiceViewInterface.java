package yama.bingo.service.view;

/**
 * BingoServiceView インターフェイスです。
 */
public interface BingoServiceViewInterface {
	/**
	 * サービス開始の通知です。
	 */
	public void serviceStarted();
	
	/**
	 * サービス停止の通知です。
	 */
	public void serviceStopped();
	
	/**
	 * ビンゴ番号リストリセットの更新通知です。
	 */
	public void bingoReseted();
	
	/**
	 * ビンゴ番号選択の更新通知です。
	 * @param num 選択された番号
	 */
	public void bingoNumberPicked(int num);
	
	/**
	 * 抽選残なしの更新通知です。
	 */
	public void bingoNumberEmptyNotified();
}
