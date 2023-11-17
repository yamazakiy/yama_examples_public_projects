package yamax.swing;

import java.awt.Color;
import java.awt.Graphics;

import yamax.fps.YFPSManager;
import yamax.fps.YFPSTargetMonitorInterface;
import yamax.fps.YFPSTargetObjectContainer;
import yamax.fps.YFPSTargetObjectInterface;

/**
 * 基底フレームクラスです。
 * 
 * <pre>
 *   FPS (frame per second) 管理通知対象モニタインターフェイスを実装します。
 * </pre>
 */
public abstract class YAbstractFrameFPSTargetMonitor extends YAbstractFrame implements YFPSTargetMonitorInterface {
	/** FPS 管理監視対象オブジェクトコンテナ */
	private final YFPSTargetObjectContainer _fpsTargetObjectContainer = new YFPSTargetObjectContainer();
	/** 実際のFPS値 */
	private double _actualfps = 0;
	/** FPS通知カウント */
	private long _count = 0;
	
	/**
	 * YAbstractFrameFPSTargetMonitor を構築します。
	 * @param fps 期待する FPS
	 */
	public YAbstractFrameFPSTargetMonitor(int fps) {
		// FPS 管理登録
		YFPSManager.put(this, fps);
	}
	
	/**
	 * 指定インデックス位置に監視対象オブジェクトを追加します。
	 * @param index インデックス
	 * @param o FPS 管理監視対象オブジェクトインターフェイス
	 * @throws IndexOutOfBoundsException インデックスが範囲外の場合 (index < 0 || index > size())
	 */
	public void add(int index, YFPSTargetObjectInterface o) {
		// 
		_fpsTargetObjectContainer.add(index, o);
	}
	
	/**
	 * 監視対象オブジェクトを追加します。
	 * @param o FPS 管理監視対象オブジェクトインターフェイス
	 */
	public void add(YFPSTargetObjectInterface o) {
		// 
		_fpsTargetObjectContainer.add(o);
	}
	
	/**
	 * 指定された監視対象オブジェクトを除外します。
	 */
	public void remove(YFPSTargetObjectInterface o) {
		// 
		_fpsTargetObjectContainer.remove(o);
	}
	
	/**
	 * 最新の FPS 実測値です。
	 * @return double FPS 実測値
	 */
	public double actualfps() {
		// 
		return _actualfps;
	}
	
	/**
	 * FPS 管理からの更新通知です。
	 */
	@Override
	public final void fpsUpdated() {
		// FPS 通知カウントアップ
		if (_count >= Long.MAX_VALUE) {
			_count = 0;
		} else {
			_count ++;
		}
		// 監視オブジェクト更新
		_fpsTargetObjectContainer.update();
		// 再描画依頼
		repaint();
	}
	
	/**
	 * 実際の FPS 値が計算更新されたことの通知です。
	 * @param fps 実際の FPS 値
	 */
	@Override
	public final void fpsActualResultUpdated(double fps) {
		// 
		this._actualfps = fps;
	}
	
	/**
	 * paintDone.
	 * @param g Graphics
	 */
	@Override
	protected void paintDone(Graphics g) {
		// paintDone.
		super.paintDone(g);
		// 実際の FPS 値表示
		paintActualFps(g);
		// 監視オブジェクト描画
		_fpsTargetObjectContainer.paint(g);
	}
	
	/**
	 * 実際の FPS 値を描画します。
	 * @param g Graphics
	 */
	private void paintActualFps(Graphics g) {
		// 色反転
		g.setColor(Color.DARK_GRAY);
		// FPS 値描画
		g.drawString("FPS:" + actualfps(), 12, getHeight() - 12);
		// FPS 通知カウント描画
		g.drawString(String.valueOf(_count), getWidth() - getFontMetrics(g.getFont()).stringWidth(String.valueOf(_count)) - 12, getHeight() - 12);
	}
}
