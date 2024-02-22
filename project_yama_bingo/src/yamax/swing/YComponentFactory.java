package yamax.swing;

import java.awt.LayoutManager;

import javax.swing.border.Border;

/**
 * コンポーネントファクトリクラスです。
 */
public final class YComponentFactory {
	/**
	 * YComponentFactory を構築します。
	 */
	private YComponentFactory() {
	}
	
	/**
	 * レイアウト用パネルを生成します。
	 * @return YPanel レイアウト用パネル
	 */
	public static YPanel createLayoutPanel() {
		// 
		return createLayoutPanel(null);
	}
	
	/**
	 * レイアウト用パネルを生成します。
	 * @param m LayoutManager
	 * @return YPanel レイアウト用パネル
	 */
	public static YPanel createLayoutPanel(LayoutManager m) {
		// 
		return createLayoutPanel(m, null);
	}
	
	/**
	 * レイアウト用パネルを生成します。
	 * @param m LayoutManager
	 * @param b Border
	 * @return YPanel レイアウト用パネル
	 */
	public static YPanel createLayoutPanel(LayoutManager m, Border b) {
		YPanel panel = new YPanel();
		//
		panel.setOpaque(false);
		// 
		if (m != null) {
			panel.setLayout(m);
		}
		// 
		if (b != null) {
			panel.setBorder(b);
		}
		//
		return panel;
	}
}
