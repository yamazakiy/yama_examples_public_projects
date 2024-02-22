package yamax.swing;

import java.awt.LayoutManager;

import javax.swing.Icon;

/**
 * 基本的な使用が可能なパネルクラスです。
 */
public class YPanel extends YAbstractImagePanel {
	/** デフォルト描画 Graphics 使用フラグ */
	private boolean _useDefaultPaintGraphicsFlag = false;
	
	/**
	 * YPanel を構築します。
	 */
	public YPanel() {
		this((Icon) null);
	}
	
	/**
	 * YPanel を構築します。
	 * @param icon パネル上に表示するアイコンイメージ
	 */
	public YPanel(Icon icon) {
		this(icon, (LayoutManager) null);
	}
	
	/**
	 * YPanel を構築します。
	 * @param layout LayoutManager
	 */
	public YPanel(LayoutManager layout) {
		this((Icon) null, layout);
	}
	
	/**
	 * YPanel を構築します。
	 * @param icon パネル上に表示するアイコンイメージ
	 * @param layout LayoutManager
	 */
	public YPanel(Icon icon, LayoutManager layout) {
		super(icon, layout);
		// 
		setDefaultPaintGraphicsUse(false);
		setOpaque(false);
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
}
