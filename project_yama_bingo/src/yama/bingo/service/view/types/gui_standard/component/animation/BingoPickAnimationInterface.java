package yama.bingo.service.view.types.gui_standard.component.animation;

import java.awt.Color;

import yamax.fps.YFPSTargetObjectInterface;

/**
 * ビンゴ抽選アニメーションインターフェイスです。
 */
public interface BingoPickAnimationInterface extends YFPSTargetObjectInterface {
	/**
	 * アニメーション再生を開始します。
	 * @param callback BingoPickAnimationCallback
	 */
	public void start(BingoPickAnimationCallback callback);
	
	/**
	 * アニメーション再生をスキップします。
	 */
	public void skip();
	
	/**
	 * アニメーション描画スクリーン背景色を設定します。
	 * @param c アニメーション描画スクリーン背景色
	 */
	public void setScreenBackground(Color c);
}
