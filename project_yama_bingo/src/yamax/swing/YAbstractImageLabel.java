package yamax.swing;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JLabel;

import yamax.swing.util.YComponentUtility;

/**
 * 基本ラベルクラスです。
 * 
 * <pre>
 *   アイコンは必ず中央に配置され、テキストとアイコンの位置指定はできません。
 * </pre>
 */
public abstract class YAbstractImageLabel extends JLabel {
	/** 表示文字配列 */
	private String[] _text = null;
	/** 描画中フラグ */
	private boolean _paintingFlag = false;
	
	/**
	 * テキストの行間を取得します。
	 * @return int 行間
	 */
	public abstract int getTextLineGap();
	
	/**
	 * デフォルト描画 Graphics を使用するか判断します。
	 * @return boolean 判断結果 - デフォルト描画 Graphics を使用する場合は true
	 */
	public abstract boolean isUseDefaultPaintGraphics();
	
	/**
	 * 表示テキスト位置の調整 x 座標です。
	 * @return int 調整 x 座標
	 */
	public abstract int adjustedTextPositionX();
	
	/**
	 * 表示テキスト位置の調整 y 座標です。
	 * @return int 調整 y 座標
	 */
	public abstract int adjustedTextPositionY();
	
	/**
	 * 影文字を描画するか判断します。
	 * @return boolean 判断結果 - 影文字を描画する場合は true
	 */
	public abstract boolean isDrawShadowString();
	
	/**
	 * YAbstractImageLabel を構築します。
	 */
	public YAbstractImageLabel() {
		this("");
	}
	
	/**
	 * YAbstractImageLabel を構築します。
	 * @param text ラベルのテキスト
	 */
	public YAbstractImageLabel(String text) {
		this(text, null);
	}
	
	/**
	 * YAbstractImageLabel を構築します。
	 * @param text ラベルのテキスト
	 */
	public YAbstractImageLabel(String[] text) {
		this(text, null);
	}
	
	/**
	 * YAbstractImageLabel を構築します。
	 * @param icon ラベル上に表示するアイコンイメージ
	 */
	public YAbstractImageLabel(Icon icon) {
		this("", icon);
	}
	
	/**
	 * YAbstractImageLabel を構築します。
	 * @param text ラベルのテキスト
	 * @param icon ラベル上に表示するアイコンイメージ
	 */
	public YAbstractImageLabel(String text, Icon icon) {
		this(YComponentUtility.toMultilineText(text), icon);
	}
	
	/**
	 * YAbstractImageLabel を構築します。
	 * @param text ラベルのテキスト
	 * @param icon ラベル上に表示するアイコンイメージ
	 */
	public YAbstractImageLabel(String[] text, Icon icon) {
		super(icon);
		// 
		setText(text);
		setHorizontalAlignment(YComponentConstants.LEADING);
	}
	
	/**
	 * 表示文字列を設定します。
	 * @param text 表示文字列
	 */
	@Override
	public void setText(String text) {
		// 
		setText(YComponentUtility.toMultilineText(text));
	}
	
	/**
	 * 表示文字列を設定します。
	 * @param text 表示文字列
	 */
	public void setText(String[] text) {
		this._text = (String[]) (text == null ? new String[] {} : text.clone());
		// 
		repaint();
	}
	
	/**
	 * 表示文字列を取得します。
	 * 
	 * <pre>
	 *   返却文字列に行送り文字列を含みます。
	 * </pre>
	 * 
	 * @return String 表示文字列
	 */
	@Override
	public String getText() {
		// 
		return getText(true);
	}
	
	/**
	 * 表示文字列を取得します。
	 * @param b 行送り文字を含む場合は true
	 * @return String 表示文字列
	 */
	public String getText(boolean b) {
		if (isInnerPainting()) {
			return "";
		} else {
			return YComponentUtility.toSinglelineText(_text, b);
		}
	}
	
	/**
	 * 表示文字列を配列で取得します。
	 * @return String[] 表示文字配列
	 */
	public String[] getMultilineText() {
		// 
		return _text == null ? new String[] {} : (String[]) _text.clone();
	}
	
	/**
	 * 描画中か判断します。
	 * @return boolean 判断結果 - 描画中の場合は true
	 */
	private boolean isInnerPainting() {
		if (_paintingFlag == true) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * paint.
	 * @param g Graphics
	 */
	@Override
	public final void paint(Graphics g) {
		try {
			_paintingFlag = true;
			{
				if (isUseDefaultPaintGraphics()) {
					YComponentGraphicsManager.getInstance().updateGraphicsForDefaultPainting(g);
				}
				updateGraphicsForPainting(g);
			}
			// 
			super.paint(g);
			paintDone(g);
		} finally {
			_paintingFlag = false;
		}
	}
	
	/**
	 * paintComponent.
	 * @param g Graphics
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// 
		if (_text != null) {
			YComponentDrawer.drawString(g, _text, getSize(), getInsets(), getHorizontalAlignment(), getVerticalAlignment(), getTextLineGap(), adjustedTextPositionX(), adjustedTextPositionY(), isEnabled(), isDrawShadowString());
		}
	}
	
	/**
	 * 推奨サイズを取得します。
	 * 
	 * <pre>
	 *   アイコンが設定されている場合は、アイコンサイズを推奨サイズとします。
	 * </pre>
	 * 
	 * @return Dimension 推奨サイズ
	 * @see javax.swing.JComponent#getPreferredSize()
	 */
	@Override
	public Dimension getPreferredSize() {
		if (hasIcon()) {
			return new Dimension(getIcon().getIconWidth(), getIcon().getIconHeight());
		} else {
			return super.getPreferredSize();
		}
	}
	
	/**
	 * このコンポーネントがフォーカスを受け取れるかどうかを識別します。
	 * @return boolean 判断結果
	 * @see javax.swing.JComponent#isFocusTraversable()
	 */
	@Override
	public boolean isFocusTraversable() {
		// 
		return false;
	}
	
	/**
	 * 受信側コンポーネントが requestFocus を呼び出してフォーカスを取得できるかどうかを返します。
	 * @return boolean 判断結果
	 * @see javax.swing.JComponent#isRequestFocusEnabled()
	 */
	@Override
	public boolean isRequestFocusEnabled() {
		// 
		return false;
	}
	
	/**
	 * ラベルテキストのイメージに対する水平位置を返します。
	 * @return int 水平位置 YComponentConstants.CENTER 固定
	 * @see javax.swing.JLabel#getHorizontalTextPosition()
	 */
	@Override
	public int getHorizontalTextPosition() {
		// 
		return YComponentConstants.CENTER;
	}
	
	/**
	 * ラベルテキストのイメージに対する水平位置のかわりに、ラベルの内容の X 軸に沿った配置方法を設定します。
	 * @param alignment 水平位置
	 * @see javax.swing.JLabel#setHorizontalAlignment(int)
	 */
	@Override
	public void setHorizontalTextPosition(int alignment) {
		// 
		setHorizontalAlignment(alignment);
	}
	
	/**
	 * ラベルテキストのイメージに対する垂直位置を返します。
	 * @return int 垂直位置 YComponentConstants.CENTER 固定
	 * @see javax.swing.JLabel#getVerticalTextPosition()
	 */
	@Override
	public int getVerticalTextPosition() {
		// 
		return YComponentConstants.CENTER;
	}
	
	/**
	 * ラベルテキストのイメージに対する垂直位置のかわりに、ラベルの内容の Y 軸に沿った配置方法を設定します。
	 * @param alignment 垂直位置
	 * @see javax.swing.JLabel#setVerticalAlignment(int)
	 */
	@Override
	public void setVerticalTextPosition(int alignment) {
		// 
		setVerticalAlignment(alignment);
	}
	
	/**
	 * このコンポーネントが完全に不透明な場合に true を返します。
	 * @return boolean 不透過判断
	 * @see javax.swing.JComponent#isOpaque()
	 */
	@Override
	public boolean isOpaque() {
		if (hasIcon()) {
			return false;
		} else {
			return super.isOpaque();
		}
	}
	
	/**
	 * アイコンが登録されているか判断します。
	 * @return boolean 判断結果 - アイコンが登録されている場合は true
	 */
	public boolean hasIcon() {
		if (getIcon() == null) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * 個別で描画用 Graphics に設定を行いたい場合はオーバーライドして下さい。
	 * 
	 * <pre>
	 *   paint(Graphics) メソッドより呼び出されます。
	 * </pre>
	 * 
	 * @param g Graphics
	 */
	protected void updateGraphicsForPainting(Graphics g) {
	}
	
	/**
	 * paint(Graphics) メソッドの最後に呼び出されます。
	 * 
	 * <pre>
	 *   このメソッドは、paint(Graphics) メソッドを final 宣言しているため用意されました。
	 * </pre>
	 * 
	 * @param g Graphics
	 */
	protected void paintDone(Graphics g) {
	}
}
