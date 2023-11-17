package yama.cp.element;

import java.awt.Component;
import java.awt.Point;

/**
 * FPS 管理監視対象オブジェクトです。
 * 
 * <pre>
 *   位置情報保持オブジェクトの実装です。
 * </pre>
 */
public class FPSTargetObjectPointsDrawable extends FPSTargetObjectPoints {
	/**
	 * FPSTargetObjectPointsDrawable を構築します。
	 * @param c 描画領域コンポーネント
	 */
	public FPSTargetObjectPointsDrawable(Component c) {
		super(c, 10);
	}
	
	/**
	 * 
	 */
	public void put(Point p1, Point p2) {
		// 
		put(new PointObject(p1, p2));
	}
	
	/**
	 * 
	 */
	private class PointObject extends DefaultPointObject {
		/**  */
		private int _m = 0;
		
		/**
		 * 
		 */
		public PointObject(Point p1, Point p2) {
			super(p1, p2);
		}
		
		/**
		 * 
		 */
		@Override
		public int getP1X() {
			return (int) p1().getX() + getMX();
		}
		
		/**
		 * 
		 */
		@Override
		public int getP1Y() {
			return (int) p1().getY() + getMY();
		}
		
		/**
		 * 
		 */
		@Override
		public int getP2X() {
			return (int) p2().getX() + getMX();
		}
		
		/**
		 * 
		 */
		@Override
		public int getP2Y() {
			return (int) p2().getY() + getMY();
		}
		
		/**
		 * 
		 */
		@Override
		public void update() {
			_m ++;
			// 
			if (_m > 3) {
				_m = 0;
			}
		}
		
		/**
		 * 
		 */
		private int getMX() {
			return _m % 2;
		}
		
		/**
		 * 
		 */
		private int getMY() {
			return (int) (_m / 2);
		}
		
		/**
		 * 
		 */
		@Override
		public PointObjectInterface clonePlain() {
			return new PointObject((Point) p1().clone(), (Point) p2().clone());
		}
	}
}
