package yama.bingo.service.view.types.gui_standard.component.animation.element;

import java.awt.Dimension;

/**
 * ビンゴ番号オブジェクトクラスです。
 * 
 * <pre>
 *   円に関する情報を追加で保持します。
 * </pre>
 */
public class BingoNumberCircleObject extends BingoNumberObject {
	/** 半径 */
	private final Dimension _radius = new Dimension();
	/** ラジアン */
	private double _radian = 0;
	
	/**
	 * BingoNumberCircleObject を構築します。
	 * @param num ビンゴ番号
	 */
	public BingoNumberCircleObject(int num) {
		super(num);
	}
	
	/**
	 * 半径（横軸）を設定します。
	 * @param r 半径（横軸）
	 */
	public void setRadiusX(int r) {
		// 
		_radius.setSize(r, _radius.getHeight());
	}
	
	/**
	 * 半径（縦軸）を設定します。
	 * @param r 半径（縦軸）
	 */
	public void setRadiusY(int r) {
		// 
		_radius.setSize(_radius.getWidth(), r);
	}
	
	/**
	 * ラジアンを設定します。
	 * @param r ラジアン
	 */
	public void setRadian(double r) {
		// 
		_radian = r;
	}
	
	/**
	 * 半径（横軸）に加算します。
	 * @param r 半径（横軸）
	 */
	public void addRadiusX(int r) {
		// 
		setRadiusX(getRadiusX() + r);
	}
	
	/**
	 * 半径（縦軸）に加算します。
	 * @param r 半径（縦軸）
	 */
	public void addRadiusY(int r) {
		// 
		setRadiusY(getRadiusY() + r);
	}
	
	/**
	 * ラジアンに加算します。
	 * @param r ラジアン
	 */
	public void addRadian(double r) {
		// 
		setRadian(getRadian() + r);
	}
	
	/**
	 * 半径（横軸）を取得します。
	 * @return int 半径（横軸）
	 */
	public int getRadiusX() {
		// 
		return (int) _radius.getWidth();
	}
	
	/**
	 * 半径（縦軸）を取得します。
	 * @return int 半径（縦軸）
	 */
	public int getRadiusY() {
		// 
		return (int) _radius.getHeight();
	}
	
	/**
	 * ラジアンを取得します。
	 * @return double ラジアン
	 */
	public double getRadian() {
		// 
		return _radian;
	}
}
