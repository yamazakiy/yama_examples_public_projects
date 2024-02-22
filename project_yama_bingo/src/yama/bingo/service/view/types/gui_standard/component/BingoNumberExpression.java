package yama.bingo.service.view.types.gui_standard.component;

import java.awt.Color;

/**
 * ビンゴ番号表現クラスです。
 */
public final class BingoNumberExpression {
	/** インスタンス */
	private static final BingoNumberExpression _instance = new BingoNumberExpression();
	
	/**
	 * インスタンスを取得します。
	 * @return BingoNumberExpression インスタンス
	 */
	public static BingoNumberExpression getInstance() {
		// 
		return _instance;
	}
	
	/**
	 * BingoNumberExpression を構築します。
	 */
	private BingoNumberExpression() {
	}
	
	/**
	 * ビンゴ番号に対応する色に変換します。
	 * @param num ビンゴ番号
	 * @return Color ビンゴ番号に対応する色
	 */
	public Color toBingoNumberColor(int num) {
		// 
		switch (((num - 1) / 5) % 5) {
			case 0:
			default:
				return Color.YELLOW;
			case 1:
				return Color.BLUE;
			case 2:
				return Color.GREEN;
			case 3:
				return Color.ORANGE;
			case 4:
				return Color.RED;
		}
	}
}
