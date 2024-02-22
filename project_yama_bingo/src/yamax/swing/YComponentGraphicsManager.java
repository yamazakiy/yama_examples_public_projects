package yamax.swing;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * コンポーネントグラフィックス管理クラスです。
 */
public final class YComponentGraphicsManager {
	/** インスタンス */
	private static final YComponentGraphicsManager _instance = new YComponentGraphicsManager();
	
	/**
	 * インスタンスを取得します。
	 * @return YComponentGraphicsManager インスタンス
	 */
	public static YComponentGraphicsManager getInstance() {
		// 
		return _instance;
	}
	
	/**
	 * YComponentGraphicsManager を構築します。
	 */
	private YComponentGraphicsManager() {
	}
	
	/**
	 * 描画 Graphics をデフォルト設定にします。
	 * @param g Graphics
	 */
	public void updateGraphicsForDefaultPainting(Graphics g) {
		if (g instanceof Graphics2D) {
			// アンチエイリアス
			if (YComponentRegulations.isUseAntialias()) {
				((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			}
		}
	}
	
	/**
	 * コンポーネントの描画を行います。
	 * 
	 * <pre>
	 *   このメソッドは、急いで描画を行う必要がある場合にのみ使用されます。
	 * </pre>
	 * 
	 * @param c コンポーネント
	 */
	public void paint(Component c) {
		try {
			c.paint(c.getGraphics());
		} catch (Throwable e) {
			// 
		}
	}
}
