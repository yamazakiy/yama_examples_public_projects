package yama.bingo.service.view.types.gui_standard.component.animation.element;

/**
 * ビンゴ番号オブジェクトクラスです。
 * 
 * <pre>
 *   拡縮に関する情報を追加で保持します。
 * </pre>
 */
public class BingoNumberScalingObject extends BingoNumberObject {
	/** 比率 */
	private double _ratio = 0;
	
	/**
	 * BingoNumberScalingObject を構築します。
	 * @param num ビンゴ番号
	 */
	public BingoNumberScalingObject(int num) {
		super(num);
	}
	
	/**
	 * 比率を設定します。
	 * @param ratio 比率
	 */
	public void setRatio(double ratio) {
		// 
		this._ratio = ratio;
	}
	
	/**
	 * 比率を加算します。
	 * @param ratio 比率
	 */
	public void addRatio(double ratio) {
		// 
		_ratio += ratio;
	}
	
	/**
	 * 比率を取得します。
	 * @return double 比率
	 */
	public double getRatio() {
		// 
		return _ratio;
	}
}
