package yamax.fps;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * FPS (frame per second) 管理監視対象オブジェクトコンテナクラスです。
 */
public class YFPSTargetObjectContainer {
	/** オブジェクトリスト */
	private final List<YFPSTargetObjectInterface> _objectList = Collections.synchronizedList(new ArrayList<>());
	
	/**
	 * YFPSTargetObjectContainer を構築します。
	 */
	public YFPSTargetObjectContainer() {
	}
	
	/**
	 * 保持している監視対象オブジェクトに対して更新通知を行います。
	 */
	public void update() {
		synchronized (_objectList) {
			for (YFPSTargetObjectInterface o : _objectList) {
				o.fpsUpdate();
			}
		}
	}
	
	/**
	 * 保持している監視対象オブジェクトに対して描画通知を行います。
	 * @param g Graphics
	 */
	public void paint(Graphics g) {
		synchronized (_objectList) {
			for (YFPSTargetObjectInterface o : _objectList) {
				o.fpsPaint(g);
			}
		}
	}
	
	/**
	 * 指定インデックス位置に監視対象オブジェクトを追加します。
	 * @param index インデックス
	 * @param o FPS 管理監視対象オブジェクトインターフェイス
	 * @throws IndexOutOfBoundsException インデックスが範囲外の場合 (index < 0 || index > size())
	 */
	public void add(int index, YFPSTargetObjectInterface o) {
		synchronized (_objectList) {
			_objectList.add(index, o);
		}
	}
	
	/**
	 * 監視対象オブジェクトを追加します。
	 * @param o FPS 管理監視対象オブジェクトインターフェイス
	 */
	public void add(YFPSTargetObjectInterface o) {
		synchronized (_objectList) {
			_objectList.add(o);
		}
	}
	
	/**
	 * すべての監視対象オブジェクトをクリアします。
	 */
	public void clear() {
		synchronized (_objectList) {
			_objectList.clear();
		}
	}
	
	/**
	 * 指定された監視対象オブジェクトを除外します。
	 */
	public void remove(YFPSTargetObjectInterface o) {
		synchronized (_objectList) {
			_objectList.remove(o);
		}
	}
	
	/**
	 * 現在保持している要素数を取得します。
	 * @return int 要素数
	 */
	public int size() {
		synchronized (_objectList) {
			return _objectList.size();
		}
	}
}
