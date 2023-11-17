package yamax.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 * コンポーネントファクトリクラスです。
 */
public final class YComponentFactory {
	/**  */
	public static final int DEFAULT_COMPONENT_HEIGHT = 32;
	/**  */
	public static final int DEFAULT_CHAR_WIDTH = 8;
	
	/**
	 * YComponentFactory を構築します。
	 */
	private YComponentFactory() {
	}
	
	/**
	 * レイアウト用パネルを生成します。
	 * @return JPanel レイアウト用パネル
	 */
	public static JPanel createLayoutPanel() {
		// 
		return createLayoutPanel(null);
	}
	
	/**
	 * レイアウト用パネルを生成します。
	 * @param m LayoutManager
	 * @return JPanel レイアウト用パネル
	 */
	public static JPanel createLayoutPanel(LayoutManager m) {
		// 
		return createLayoutPanel(m, null);
	}
	
	/**
	 * レイアウト用パネルを生成します。
	 * @param m LayoutManager
	 * @param b Border
	 * @return JPanel レイアウト用パネル
	 */
	public static JPanel createLayoutPanel(LayoutManager m, Border b) {
		JPanel panel = new JPanel();
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
	
	/**
	 * ボタンを生成します。
	 * @param l ActionListener
	 * @param s タイトル
	 * @return JButton ボタン
	 */
	public static JButton createButton(ActionListener l, String s) {
		JButton button = new JButton(s);
		// 
		button.addActionListener(l);
		button.setPreferredSize(new Dimension(toComponentWidth(s, 12), YComponentFactory.DEFAULT_COMPONENT_HEIGHT));
		// 
		return button;
	}
	
	/**
	 * タイトルラベルを生成します。
	 * @param alignment 水平位置
	 * @param s タイトル
	 * @return JLabel タイトルラベル
	 */
	public static JLabel createTitleLabel(int alignment, String s) {
		JLabel l = new JLabel(s);
		// 
		l.setOpaque(false);
		l.setHorizontalAlignment(alignment);
		l.setPreferredSize(new Dimension(toComponentWidth(s, 2), YComponentFactory.DEFAULT_COMPONENT_HEIGHT));
		// 
		return l;
	}
	
	/**
	 * 値ラベルを生成します。
	 * @param alignment 水平位置
	 * @param s 初期表示
	 * @return JLabel 値ラベル
	 */
	public static JLabel createValueLabel(int alignment, String s) {
		JLabel l = new JLabel(s);
		// 
		l.setOpaque(false);
		l.setHorizontalAlignment(alignment);
		l.setVerticalAlignment(SwingConstants.BOTTOM);
		l.setPreferredSize(new Dimension(toComponentWidth(s, 2), YComponentFactory.DEFAULT_COMPONENT_HEIGHT));
		l.setBorder(BorderFactory.createCompoundBorder(createDefaultLowerLineBorder(), BorderFactory.createEmptyBorder(0, 4, 0, 4)));
		// 
		return l;
	}
	
	/**
	 * デフォルトの下線 Border を生成します。
	 * @return Border デフォルトの下線 Border
	 */
	public static Border createDefaultLowerLineBorder() {
		// 
		// return BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE), BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
		return BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY.brighter()), BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY.darker()));
	}
	
	/**
	 * 指定情報からコンポーネント幅にします。
	 * @param s 文字列
	 * @param margin 左右マージン
	 * @return int コンポーネント幅
	 */
	private static int toComponentWidth(String s, int margin) {
		// 
		return (s == null ? 4 : s.getBytes().length) * YComponentFactory.DEFAULT_CHAR_WIDTH + margin * 2;
	}
}
