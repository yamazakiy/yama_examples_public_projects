package yamax.swing;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingConstants;

/**
 * コンポーネント定義クラスです。
 */
public final class YComponentConstants implements SwingConstants {
	/** 影文字処理パターン */
	public enum ShadowStringPattern {
		/** 繰り返し 4 * 4 */
		REPETITION_4X4,
		/** 繰り返し 2 * 2 */
		REPETITION_2X2,
		/** スケーリング */
		SCALING,
	};
	
	/** スクロールパネル スクロールポリシー */
	public enum ScrollablePanelScrollPolicy {
		/** スクロールなし */
		NONE,
		/** スクロール垂直 */
		VERTICAL,
		/** スクロール水平 */
		HORIZONTAL,
		/** スクロール垂直水平 */
		BOTH,
	};
	
	/** スクロールバー方向 */
	public enum ScrollbarAlignment {
		/** 水平 */
		HORIZONTAL,
		/** 垂直 */
		VERTICAL,
	};
	
	/** 複数行コンポーネント 行送り文字 */
	public static final String LINEFEED = "\n";
	
	/**
	 * YComponentConstants を構築します。
	 */
	private YComponentConstants() {
	}
	
	/**
	 * LookAndFeel 名称リストを取得します。
	 * 
	 * <pre>
	 *   採用候補の一覧が、優先順で登録されています。
	 * </pre>
	 * 
	 * @return List<String> LookAndFeel 名称リスト
	 */
	public static List<String> getLookAndFeelClassNames() {
		List<String> list = new ArrayList<>();
		//
		list.add("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		list.add("javax.swing.plaf.metal.MetalLookAndFeel");
		list.add("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		list.add("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		list.add("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
		//
		return list;
	}
	
	/**
	 * デフォルトのコンポーネント高さを取得します。
	 * @return int デフォルトのコンポーネント高さ
	 */
	public static int getDefaultComponentHeight() {
		// 現在未使用
		return 32;
	}
	
	/**
	 * デフォルトの文字幅を取得します。
	 * @return int デフォルトの文字幅
	 */
	public static int getDefaultCharWidth() {
		// 現在未使用
		return 8;
	}
}
