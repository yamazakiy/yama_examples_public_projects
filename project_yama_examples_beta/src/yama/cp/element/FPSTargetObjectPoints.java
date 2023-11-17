package yama.cp.element;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import yamax.fps.YFPSTargetObjectInterface;

/**
 * FPS 管理監視対象オブジェクトです。
 * 
 * <pre>
 *   位置情報保持オブジェクトの実装です。
 * </pre>
 */
public abstract class FPSTargetObjectPoints implements YFPSTargetObjectInterface {
	/** 描画領域コンポーネント */
	private final Component _c;
	/**  */
	private List<PointObjectInterface> _list = Collections.synchronizedList(new ArrayList<>());
	/**  */
	private final int _fpsTimes;
	/**  */
	private int _fpsUpdateCount;
	
	/**
	 * FPSTargetObjectPoints を構築します。
	 * @param c 描画領域コンポーネント
	 * @param times
	 */
	public FPSTargetObjectPoints(Component c, int times) {
		// 描画領域コンポーネント
		this._c = c;
		// 
		this._fpsTimes = times;
	}
	
	/**
	 * 
	 */
	public synchronized List<PointObjectInterface> toPointObjectList() {
		synchronized (_list) {
			ArrayList<PointObjectInterface> list = new ArrayList<>();
			// 
			for (PointObjectInterface o : _list) {
				list.add(o.clonePlain());
			}
			// 
			return list;
		}
	}
	
	/**
	 * 
	 */
	public final synchronized void clear() {
		// 
		_list.clear();
	}
	
	/**
	 * 
	 */
	protected final synchronized void put(PointObjectInterface o) {
		// 
		_list.add(o);
	}
	
	/**
	 * FPS 管理からの更新通知です。
	 */
	@Override
	public final void fpsUpdate() {
		_fpsUpdateCount ++;
		// 
		if (_fpsUpdateCount < _fpsTimes) {
			return;
		} else {
			_fpsUpdateCount = 0;
		}
		// 状態更新
		update();
	}
	
	/**
	 * FPS 管理からの描画通知です。
	 * @param g Graphics
	 */
	@Override
	public final void fpsPaint(Graphics g) {
		// 描画
		draw(g);
	}
	
	/**
	 * 
	 */
	protected final int canvasX() {
		return 0;
	}
	
	/**
	 * 
	 */
	protected final int canvasY() {
		return 0;
	}
	
	/**
	 * 
	 */
	protected final int canvasW() {
		// 
		return _c.getWidth();
	}
	
	/**
	 * 
	 */
	protected final int canvasH() {
		// 
		return _c.getHeight();
	}
	
	/**
	 * 状態を更新します。
	 * 
	 * <pre>
	 *   状態を１フレーム進めます。
	 * </pre>
	 */
	private void update() {
		synchronized (_list) {
			for (PointObjectInterface o : _list) {
				o.update();
			}
		}
	}
	
	/**
	 * 現在の状態で描画を行います。
	 */
	private void draw(Graphics g) {
		synchronized (_list) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(10.0F));
			g2.setPaint(Color.BLACK);
			// 
			for (PointObjectInterface o : _list) {
				if (o.isContained(_c)) {
					g2.drawLine((int) o.getP1X(), (int) o.getP1Y(), (int) o.getP2X(), (int) o.getP2Y());
				}
			}
		}
	}
	
	/**
	 * 
	 */
	public interface PointObjectInterface {
		/**
		 * 
		 */
		public int getP1X();
		
		/**
		 * 
		 */
		public int getP1Y();
		
		/**
		 * 
		 */
		public int getP2X();
		
		/**
		 * 
		 */
		public int getP2Y();
		
		/**
		 * 
		 */
		public void update();
		
		/**
		 * 
		 */
		public PointObjectInterface clonePlain();
		
		/**
		 * 
		 */
		public boolean isContained(Component c);
	}
	
	/**
	 * 
	 */
	public abstract class DefaultPointObject implements PointObjectInterface {
		/**  */
		private final Point _p1;
		/**  */
		private final Point _p2;
		
		/**
		 * 
		 */
		public DefaultPointObject(Point p1, Point p2) {
			this._p1 = p1;
			this._p2 = p2;
		}
		
		/**
		 * 
		 */
		protected final Point p1() {
			// 
			return _p1;
		}
		
		/**
		 * 
		 */
		protected final Point p2() {
			// 
			return _p2;
		}
		
		/**
		 * 
		 */
		@Override
		public boolean isContained(Component c) {
			// 
			return c.contains(getP1X(), getP1Y()) && c.contains(getP2X(), getP2Y());
		}
	}
}
