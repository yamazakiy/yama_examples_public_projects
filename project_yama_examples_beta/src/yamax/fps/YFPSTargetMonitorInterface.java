package yamax.fps;

/**
 * FPS (frame per second) 管理通知対象モニタインターフェイスです。
 */
public interface YFPSTargetMonitorInterface {
	/**
	 * FPS 管理からの更新通知です。
	 */
	public void fpsUpdated();
	
	/**
	 * 実際の FPS 値が計算更新されたことの通知です。
	 * @param fps 実際の FPS 値
	 */
	public void fpsActualResultUpdated(double fps);
}
