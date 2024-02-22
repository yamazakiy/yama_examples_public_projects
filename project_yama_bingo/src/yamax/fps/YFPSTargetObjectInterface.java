package yamax.fps;

import java.awt.Graphics;

/**
 * FPS (frame per second) 管理監視対象オブジェクトインターフェイスです。
 */
public interface YFPSTargetObjectInterface {
	/**
	 * FPS 管理からの更新通知です。
	 */
	public void fpsUpdate();
	
	/**
	 * FPS 管理からの描画通知です。
	 * @param g Graphics
	 */
	public void fpsPaint(Graphics g);
}
