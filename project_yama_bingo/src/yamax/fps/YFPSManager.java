package yamax.fps;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * FPS (frame per second) 管理クラスです。
 * 
 * <pre>
 *   指定された 1 秒間あたりのフレーム数に応じて、通知を行います。
 * </pre>
 */
public final class YFPSManager implements Runnable {
	/** FPS 計算間隔 単位: ns */
	private static final long MAX_STATS_INTERVAL = 1000000000L;
	
	/** 管理マップ */
	private static final Map<YFPSTargetMonitorInterface, WeakReference<YFPSManager>> _managerMap = Collections.synchronizedMap(new WeakHashMap<>());
	
	/** 期待する FPS */
	private final int FPS;
	/** 1 フレームあたりの時間 */
	private final long PERIOD;
	/** FPS 通知対象 */
	private final YFPSTargetMonitorInterface _monitor;
	/** 処理スレッド */
	private Thread _thread;
	/** 処理中フラグ */
	private volatile boolean _bRunningFlag;
	/** 計算用変数：待機時間 */
	private long _calcInterval = 0L;
	/** 計算用変数：前回計算時時刻 */
	private long _calcTimeLast;
	/** フレームカウンタ */
	private long _calcFrameCount = 0;
	/** 実際の FPS */
	private double _actualFPS = 0.0;
	
	/**
	 * FPS 管理に登録します。
	 * @param monitor YFPSTargetMonitorInterface
	 * @param fps 期待する FPS
	 */
	public static void put(YFPSTargetMonitorInterface monitor, int fps) {
		synchronized (_managerMap) {
			// 
			remove(monitor);
			// 
			_managerMap.put(monitor, new WeakReference<>(new YFPSManager(monitor, fps)));
		}
	}
	
	/**
	 * FPS 管理から除外します。
	 * @param monitor YFPSTargetMonitorInterface
	 */
	public static void remove(YFPSTargetMonitorInterface monitor) {
		synchronized (_managerMap) {
			if (_managerMap.containsKey(monitor)) {
				// 
				YFPSManager manager = _managerMap.get(monitor).get();
				manager.cancel();
				// 
				_managerMap.remove(monitor);
			}
		}
	}
	
	/**
	 * YFPSManager を構築します。
	 * @param monitor YFPSTargetMonitorInterface
	 * @param fps 期待する FPS
	 */
	private YFPSManager(YFPSTargetMonitorInterface monitor, int fps) {
		// 通知対象
		this._monitor = monitor;
		// 期待するFPS
		this.FPS = fps;
		// 1フレームの時間
		this.PERIOD = (long) (1.0 / FPS * MAX_STATS_INTERVAL);
		// スレッド開始
		_thread = new Thread(this);
		_thread.start();
	}
	
	/**
	 * FPS 管理を終了します。
	 */
	private void cancel() {
		// 
		_bRunningFlag = false;
	}
	
	/**
	 * run.
	 */
	@Override
	public void run() {
		long beforeTime, afterTime, timeDiff, sleepTime;
		long overSleepTime = 0L;
		int noDelays = 0;
		// 
		beforeTime = nanoTime();
		_calcTimeLast = beforeTime;
		// 
		_bRunningFlag = true;
		// 
		while (_bRunningFlag) {
			// 
			_monitor.fpsUpdated();
			// 
			afterTime = nanoTime();
			timeDiff = afterTime - beforeTime;
			// 前回のフレームの休止時間誤差も引いておく
			sleepTime = (PERIOD - timeDiff) - overSleepTime;
			// 
			if (sleepTime > 0) {
				// 休止時間がとれる場合
				try {
					Thread.sleep(sleepTime / 1000000L); // nano->ms
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// sleep()の誤差
				overSleepTime = (nanoTime() - afterTime) - sleepTime;
			} else {
				// 状態更新・レンダリングで時間を使い切ってしまい休止時間がとれない場合
				overSleepTime = 0L;
				// 休止なしが16回以上続いたら
				if (++ noDelays >= 16) {
					Thread.yield(); // 他のスレッドを強制実行
					noDelays = 0;
				}
			}
			// 
			beforeTime = nanoTime();
			// FPSを計算
			calcFPS();
		}
	}
	
	/**
	 * FPS 計算を行います。
	 * 
	 * <pre>
	 *   各計算用ローカル変数が更新されます。
	 * </pre>
	 */
	private void calcFPS() {
		_calcFrameCount ++;
		_calcInterval += PERIOD;
		// 1 秒おきに FPS を再計算する
		if (_calcInterval >= MAX_STATS_INTERVAL) {
			long timeNow = nanoTime();
			// 実際の経過時間を測定
			long realElapsedTime = timeNow - _calcTimeLast; // 単位: ns
			// FPS を計算
			// realElapsedTime の単位は ns なので s に変換する
			_actualFPS = ((double) _calcFrameCount / realElapsedTime) * MAX_STATS_INTERVAL;
			_calcFrameCount = 0L;
			_calcInterval = 0L;
			_calcTimeLast = timeNow;
			// 
			_monitor.fpsActualResultUpdated(_actualFPS);
		}
	}
	
	/**
	 * ナノ秒での時刻です。
	 * 
	 * <pre>
	 *   1.5 互換用メソッドです。
	 *   1.4 ではナノ秒は扱い対象外です。
	 * </pre>
	 */
	private long nanoTime() {
		// ナノ秒取得未対応
		return System.currentTimeMillis() * 1000000L;
	}
}
