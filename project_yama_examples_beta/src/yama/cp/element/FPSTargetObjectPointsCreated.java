package yama.cp.element;

import java.awt.Component;
import java.awt.Point;
import java.util.List;

/**
 * FPS 管理監視対象オブジェクトです。
 * 
 * <pre>
 *   位置情報保持オブジェクトの実装です。
 * </pre>
 */
public class FPSTargetObjectPointsCreated extends FPSTargetObjectPoints {
	/**
	 * FPSTargetObjectPointsCreated を構築します。
	 * @param c 描画領域コンポーネント
	 * @param list PointObject List
	 */
	public FPSTargetObjectPointsCreated(Component c, List<PointObjectInterface> list) {
		super(c, 1);
		// 
		for (PointObjectInterface o : list) {
			put(new Point(o.getP1X(), o.getP1Y()), new Point(o.getP2X(), o.getP2Y()));
		}
	}
	
	/**
	 * 
	 */
	private void put(Point p1, Point p2) {
		// 
		put(new PointObject(p1, p2));
	}
	
	/**
	 * 
	 */
	private class PointObject extends DefaultPointObject {
		/**  */
		private boolean bx = true;
		/**  */
		private boolean by = true;
		/**  */
		private double _ax = 0;
		/**  */
		private double _ay = 0;
		/**  */
		private int _mx = 0;
		/**  */
		private int _my = 0;
		
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
			// x
			if (bx) {
				_ax = 1;
				// _mx += _ax;
			} else {
				_ax = 1;
				// _mx -= _ax;
			}
			if (getP1X() > canvasW() || getP2X() > canvasW()) {
				bx = false;
			} else if (getP1X() < canvasX() || getP2X() < canvasX()) {
				bx = true;
			}
			// y
			if (_ay <= 0) {
				by = true;
			}
			if (by) {
				_ay += 0.8;
				_my += _ay;
			} else {
				_ay -= 1.2;
				_my -= _ay;
			}
			if (getP1Y() > canvasH() || getP2Y() > canvasH()) {
				by = false;
			}
		}
		
		/**
		 * 
		 */
		private int getMX() {
			return _mx;
		}
		
		/**
		 * 
		 */
		private int getMY() {
			return _my;
		}
		
		/**
		 * 
		 */
		@Override
		public PointObjectInterface clonePlain() {
			// 
			return new PointObject((Point) p1().clone(), (Point) p2().clone());
		}
	}
}
