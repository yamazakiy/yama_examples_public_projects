package yama.bingo.service.view.types.gui_standard.component;

import yama.bingo.service.view.types.gui_standard.component.border.BingoNumberBorder;
import yamax.lang.YLangUtility;
import yamax.swing.YComponentConstants;
import yamax.swing.YLabel;

/**
 * ビンゴ番号ラベルクラスです。
 */
public class BingoNumberLabel extends YLabel {
	/** BingoNumberBorder */
	private final BingoNumberBorder _border;
	
	/**
	 * BingoNumberLabel を構築します。
	 * @param bw ボーダー幅
	 */
	public BingoNumberLabel(int bw) {
		this(bw, "");
	}
	
	/**
	 * BingoNumberLabel を構築します。
	 * @param bw ボーダー幅
	 * @param text ラベルのテキスト
	 */
	public BingoNumberLabel(int bw, String text) {
		super(text, YComponentConstants.CENTER);
		// 
		setBorder(this._border = new BingoNumberBorder(bw));
	}
	
	/**
	 * ビンゴ番号を設定します。
	 * @param num ビンゴ番号
	 */
	public void setBingoNumber(int num) {
		// 
		setText(String.valueOf(num));
	}
	
	/**
	 * ラベル状態をクリアします。
	 */
	public void clear() {
		// 
		setText("");
	}
	
	/**
	 * テキストを設定します。
	 * 
	 * <pre>
	 *   このクラスの場合、テキストはビンゴ番号を意味します。
	 * </pre>
	 * 
	 * @param s テキスト
	 */
	@Override
	public final void setText(String s) {
		super.setText(s);
		// 
		if (_border != null) {
			_border.setBorderColor(BingoNumberExpression.getInstance().toBingoNumberColor(YLangUtility.toInt(s, 0)));
			// 
			setVisible(!YLangUtility.isEmpty(s));
		}
	}
}
