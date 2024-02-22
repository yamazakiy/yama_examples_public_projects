package yama.bingo.service.view.types.gui_standard.component.animation.element;

import java.awt.Point;

/**
 * ビンゴ番号オブジェクトクラスです。
 * 
 * <pre>
 *   ビンゴ番号や位置情報などを保持し、主に描画用の情報を管理します。
 * </pre>
 */
public class BingoNumberObject {
	/** ビンゴ番号 */
	private final int _num;
	/** モーション情報 - x */
	private final MotionInfo _x = new MotionInfo();
	/** モーション情報 - y */
	private final MotionInfo _y = new MotionInfo();
	/** 位置 */
	private final Point _p = new Point(0, 0);
	
	/**
	 * BingoNumberObject を構築します。
	 * @param num ビンゴ番号
	 */
	public BingoNumberObject(int num) {
		// 
		this._num = num;
	}
	
	/**
	 * [モーション情報 - x] 順方向を設定します。
	 * @param fd [モーション情報 - x] 順方向の場合は true
	 */
	public void setXFd(boolean fd) {
		// 
		_x.setFd(fd);
	}
	
	/**
	 * [モーション情報 - x] 速度を設定します。
	 * @param v [モーション情報 - x] 速度
	 */
	public void setXV(double v) {
		// 
		_x.setV(v);
	}
	
	/**
	 * [モーション情報 - y] 順方向を設定します。
	 * @param fd [モーション情報 - y] 順方向の場合は true
	 */
	public void setYFd(boolean fd) {
		// 
		_y.setFd(fd);
	}
	
	/**
	 * [モーション情報 - y] 速度を設定します。
	 * @param v [モーション情報 - y] 速度
	 */
	public void setYV(double v) {
		// 
		_y.setV(v);
	}
	
	/**
	 * x 座標を設定します。
	 * @param x x 座標
	 */
	public void setX(int x) {
		// 
		_p.setLocation(x, _p.getY());
	}
	
	/**
	 * y 座標を設定します。
	 * @param y y 座標
	 */
	public void setY(int y) {
		// 
		_p.setLocation(_p.getX(), y);
	}
	
	/**
	 * [モーション情報 - x] 速度に加算します。
	 * @param v [モーション情報 - x] 速度
	 */
	public void addXV(double v) {
		// 
		setXV(_x.getV() + v);
	}
	
	/**
	 * [モーション情報 - y] 速度に加算します。
	 * @param v [モーション情報 - y] 速度
	 */
	public void addYV(double v) {
		// 
		setYV(_y.getV() + v);
	}
	
	/**
	 * x 座標に加算します。
	 * @param x x 座標
	 */
	public void addX(int x) {
		// 
		setX((int) _p.getX() + x);
	}
	
	/**
	 * y 座標に加算します。
	 * @param y y 座標
	 */
	public void addY(int y) {
		// 
		setY((int) _p.getY() + y);
	}
	
	/**
	 * ビンゴ番号を取得します。
	 * @return int ビンゴ番号
	 */
	public int getNum() {
		// 
		return _num;
	}
	
	/**
	 * [モーション情報 - x] 順方向か判断します。
	 * @return boolean 判断結果 - [モーション情報 - x] 順方向であれば true
	 */
	public boolean isXFd() {
		// 
		return _x.isFd();
	}
	
	/**
	 * [モーション情報 - x] 速度を取得します。
	 * @return double [モーション情報 - x] 速度
	 */
	public double getXV() {
		// 
		return _x.getV();
	}
	
	/**
	 * [モーション情報 - y] 順方向か判断します。
	 * @return boolean 判断結果 - [モーション情報 - y] 順方向であれば true
	 */
	public boolean isYFd() {
		// 
		return _y.isFd();
	}
	
	/**
	 * [モーション情報 - y] 速度を取得します。
	 * @return double [モーション情報 - y] 速度
	 */
	public double getYV() {
		// 
		return _y.getV();
	}
	
	/**
	 * x 座標を取得します。
	 * @return int x 座標
	 */
	public int getX() {
		// 
		return (int) _p.getX();
	}
	
	/**
	 * y 座標を取得します。
	 * @return int y 座標
	 */
	public int getY() {
		// 
		return (int) _p.getY();
	}
	
	/**
	 * モーション情報クラス
	 */
	private class MotionInfo {
		/** 順方向 */
		private boolean _fd = true;
		/** 速度 */
		private double _v = 0;
		
		/**
		 * 順方向を設定します。
		 * @param fd 順方向の場合は true
		 */
		public void setFd(boolean fd) {
			this._fd = fd;
		}
		
		/**
		 * 速度を設定します。
		 * @param v 速度
		 */
		public void setV(double v) {
			this._v = v;
		}
		
		/**
		 * 順方向か判断します。
		 * @return boolean 判断結果 - 順方向であれば true
		 */
		public boolean isFd() {
			return _fd;
		}
		
		/**
		 * 速度を取得します。
		 * @return double 速度
		 */
		public double getV() {
			return _v;
		}
	}
}
