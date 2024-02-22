package yama.bingo.service.control;

import yama.bingo.service.view.BingoServiceViewInterface;

/**
 * BingoService オブザーバクラスです。
 */
public class BingoServiceObserver {
	/** BingoServiceViewInterface */
	private final BingoServiceViewInterface _view;
	
	/**
	 * BingoServiceObserver を構築します。
	 * @param view BingoServiceViewInterface
	 */
	public BingoServiceObserver(BingoServiceViewInterface view) {
		// 
		this._view = view;
	}
	
	/**
	 * サービスの開始を通知します。
	 */
	public void start() {
		// 
		_view.serviceStarted();
	}
	
	/**
	 * サービスの停止を通知します。
	 */
	public void stop() {
		// 
		_view.serviceStopped();
	}
	
	/**
	 * 更新通知
	 * 
	 * <pre>
	 * ビンゴ番号リストリセット
	 * </pre>
	 */
	public void notifyBingoReseted() {
		// 
		_view.bingoReseted();
	}
	
	/**
	 * 更新通知
	 * 
	 * <pre>
	 * ビンゴ番号選択
	 * </pre>
	 * 
	 * @param num 選択された番号
	 */
	public void notifyBingoNumberPicked(int num) {
		// 
		_view.bingoNumberPicked(num);
	}
	
	/**
	 * 更新通知
	 * 
	 * <pre>
	 * 抽選残なし
	 * </pre>
	 */
	public void notifyBingoNumberEmpty() {
		// 
		_view.bingoNumberEmptyNotified();
	}
}
