package yamax.swing;

import javax.swing.Icon;

import yamax.swing.util.YComponentUtility;

/**
 * 基本的な使用が可能なラベルクラスです。
 */
public class YLabel extends YAbstractImageLabel {
	/** 文字列行間 */
	private int _textLineGap = 0;
	/** 表示テキスト位置調整 x 座標 */
	private int _adjustmentTextPositionX = 0;
	/** 表示テキスト位置調整 y 座標 */
	private int _adjustmentTextPositionY = 0;
	/** デフォルト描画 Graphics 使用フラグ */
	private boolean _useDefaultPaintGraphicsFlag = false;
	/** 影文字使用判断フラグ */
	private boolean _drawShadowStringFlag = false;
	
	/**
	 * YLabel を構築します。
	 */
	public YLabel() {
		this("");
	}
	
	/**
	 * YLabel を構築します。
	 * @param text ラベルのテキスト
	 */
	public YLabel(String text) {
		this(YComponentUtility.toMultilineText(text));
	}
	
	/**
	 * YLabel を構築します。
	 * @param text ラベルのテキスト
	 */
	public YLabel(String[] text) {
		this(text, YComponentConstants.LEADING);
	}
	
	/**
	 * YLabel を構築します。
	 * @param text ラベルのテキスト
	 * @param horizontalAlignment YComponentConstants で定義されている定数 LEFT、CENTER、RIGHT、LEADING、または TRAILING のうちの 1 つ
	 */
	public YLabel(String text, int horizontalAlignment) {
		this(YComponentUtility.toMultilineText(text), horizontalAlignment);
	}
	
	/**
	 * YLabel を構築します。
	 * @param text ラベルのテキスト
	 * @param horizontalAlignment YComponentConstants で定義されている定数 LEFT、CENTER、RIGHT、LEADING、または TRAILING のうちの 1 つ
	 */
	public YLabel(String[] text, int horizontalAlignment) {
		this(text, (Icon) null, horizontalAlignment);
	}
	
	/**
	 * YLabel を構築します。
	 * @param icon ラベル上に表示するアイコンイメージ
	 */
	public YLabel(Icon icon) {
		this(icon, YComponentConstants.LEADING);
	}
	
	/**
	 * YLabel を構築します。
	 * @param icon ラベル上に表示するアイコンイメージ
	 * @param horizontalAlignment YComponentConstants で定義されている定数 LEFT、CENTER、RIGHT、LEADING、または TRAILING のうちの 1 つ
	 */
	public YLabel(Icon icon, int horizontalAlignment) {
		this("", icon, horizontalAlignment);
	}
	
	/**
	 * YLabel を構築します。
	 * @param text ラベルのテキスト
	 * @param icon ラベル上に表示するアイコンイメージ
	 * @param horizontalAlignment YComponentConstants で定義されている定数 LEFT、CENTER、RIGHT、LEADING、または TRAILING のうちの 1 つ
	 */
	public YLabel(String text, Icon icon, int horizontalAlignment) {
		this(YComponentUtility.toMultilineText(text), icon, horizontalAlignment);
	}
	
	/**
	 * YLabel を構築します。
	 * @param text ラベルのテキスト
	 * @param icon ラベル上に表示するアイコンイメージ
	 * @param horizontalAlignment YComponentConstants で定義されている定数 LEFT、CENTER、RIGHT、LEADING、または TRAILING のうちの 1 つ
	 */
	public YLabel(String[] text, Icon icon, int horizontalAlignment) {
		super(text, icon);
		// 
		setHorizontalAlignment(horizontalAlignment);
		setDefaultPaintGraphicsUse(true);
		setDrawShadowStringUse(false);
	}
	
	/**
	 * テキストの行間を設定します。
	 * @param gap 行間
	 */
	public void setTextLineGap(int gap) {
		// 
		this._textLineGap = gap;
	}
	
	/**
	 * テキストの行間を取得します。
	 * @return int 行間
	 */
	@Override
	public int getTextLineGap() {
		// 
		return _textLineGap;
	}
	
	/**
	 * 表示テキスト位置調整 x 座標を設定します。
	 * @param position 表示テキスト位置調整 x 座標
	 */
	public void setAdjustmentTextPositionX(int position) {
		// 
		this._adjustmentTextPositionX = position;
	}
	
	/**
	 * 表示テキスト位置の調整 x 座標です。
	 * @return int 調整 x 座標
	 */
	@Override
	public int adjustedTextPositionX() {
		// 
		return _adjustmentTextPositionX;
	}
	
	/**
	 * 表示テキスト位置調整 y 座標を設定します。
	 * @param position 表示テキスト位置調整 y 座標
	 */
	public void setAdjustmentTextPositionY(int position) {
		// 
		this._adjustmentTextPositionY = position;
	}
	
	/**
	 * 表示テキスト位置の調整 y 座標です。
	 * @return int 調整 y 座標
	 */
	@Override
	public int adjustedTextPositionY() {
		// 
		return _adjustmentTextPositionY;
	}
	
	/**
	 * デフォルト描画 Graphics 使用判断の設定を行います。
	 * @param b 使用判断
	 */
	public void setDefaultPaintGraphicsUse(boolean b) {
		// 
		this._useDefaultPaintGraphicsFlag = b;
	}
	
	/**
	 * デフォルト描画 Graphics を使用するか判断します。
	 * @return boolean 判断結果 - デフォルト描画 Graphics を使用する場合は true
	 */
	@Override
	public boolean isUseDefaultPaintGraphics() {
		// 
		return _useDefaultPaintGraphicsFlag;
	}
	
	/**
	 * 影付き文字を使用するか設定します。
	 * @param b 影付き文字を使用判断
	 */
	public void setDrawShadowStringUse(boolean b) {
		// 
		this._drawShadowStringFlag = b;
	}
	
	/**
	 * 影文字を描画するか判断します。
	 * @return boolean 判断結果 - 影文字を描画する場合は true
	 */
	@Override
	public boolean isDrawShadowString() {
		// 
		return _drawShadowStringFlag;
	}
}
