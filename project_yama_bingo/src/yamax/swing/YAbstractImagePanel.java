package yamax.swing;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.border.Border;

import yamax.swing.border.YImageBorder;
import yamax.swing.util.YComponentUtility;

/**
 * 基本パネルクラスです。
 * 
 * <pre>
 *   追加された子コンポーネントの Graphics は、このパネルの Graphics 設定の影響を受けます。
 * </pre>
 */
public abstract class YAbstractImagePanel extends JPanel {
	/** YImageBorder */
	private final YImageBorder _imageBorder;
	
	/**
	 * デフォルト描画 Graphics を使用するか判断します。
	 * @return boolean 判断結果 - デフォルト描画 Graphics を使用する場合は true
	 */
	public abstract boolean isUseDefaultPaintGraphics();
	
	/**
	 * YAbstractImagePanel を構築します。
	 */
	public YAbstractImagePanel() {
		this((Icon) null);
	}
	
	/**
	 * YAbstractImagePanel を構築します。
	 * @param icon パネル上に表示するアイコンイメージ
	 */
	public YAbstractImagePanel(Icon icon) {
		this(icon, null);
	}
	
	/**
	 * YAbstractImagePanel を構築します。
	 * @param layout LayoutManager
	 */
	public YAbstractImagePanel(LayoutManager layout) {
		this(null, layout);
	}
	
	/**
	 * YAbstractImagePanel を構築します。
	 * @param icon パネル上に表示するアイコンイメージ
	 * @param layout LayoutManager
	 */
	public YAbstractImagePanel(Icon icon, LayoutManager layout) {
		super(layout);
		// 
		super.setBorder(_imageBorder = new YImageBorder(icon));
	}
	
	/**
	 * コンポーネントのボーダを設定します。
	 * @param border Border
	 */
	@Override
	public final void setBorder(Border border) {
		// 
		super.setBorder(BorderFactory.createCompoundBorder(_imageBorder, border));
	}
	
	/**
	 * paint.
	 * @param g Graphics
	 */
	@Override
	public final void paint(Graphics g) {
		{
			if (isUseDefaultPaintGraphics()) {
				YComponentGraphicsManager.getInstance().updateGraphicsForDefaultPainting(g);
			}
			// 
			updateGraphicsForPainting(g);
		}
		{
			// 
			super.paint(g);
			// 
			paintDone(g);
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
	 * アイコンを設定します。
	 * @param icon Icon
	 */
	public void setIcon(Icon icon) {
		_imageBorder.setIcon(icon);
		// 
		repaint();
	}
	
	/**
	 * アイコンを取得します。
	 * @return Icon アイコン
	 */
	public Icon getIcon() {
		// 
		return _imageBorder.getIcon();
	}
	
	/**
	 * validateTree.
	 * @see java.awt.Container#validateTree
	 */
	@Override
	protected final void validateTree() {
		super.validateTree();
		// 
		doLayoutDone();
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
	
	/**
	 * レイアウトマネージャによるレイアウト配置が行われた後に呼び出されます。
	 */
	protected void doLayoutDone() {
	}
	
	/**
	 * 対象全てのアイコンが登録されているか判断します。
	 * 
	 * <pre>
	 *   レイアウト用パネルとして使用する際の、ショートカットメソッドです。
	 * </pre>
	 * 
	 * @param icons アイコン配列
	 * @return boolean 判断結果 - 対象全てのアイコンが登録されている場合は true
	 */
	protected boolean hasAllIcon(Icon[] icons) {
		// 
		return YComponentUtility.hasAllIcon(icons);
	}
}
