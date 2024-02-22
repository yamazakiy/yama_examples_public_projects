package yama.bingo;

import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import yama.bingo.service.BingoService;
import yamax.swing.YComponentConstants;

/**
 * BingoService 起動クラスです。
 */
public class BingoServiceStartup {
	/**
	 * static.
	 */
	static {
		List<String> laf = YComponentConstants.getLookAndFeelClassNames();
		// 
		for (String s : laf) {
			try {
				UIManager.setLookAndFeel(s);
				// 最初に成功した LookAndFeel を採用
				break;
			} catch (ClassNotFoundException e) {
			} catch (InstantiationException e) {
			} catch (IllegalAccessException e) {
			} catch (UnsupportedLookAndFeelException e) {
			}
		}
	}
	
	/**
	 * main.
	 * @param args 起動パラメータ
	 * @see yama.bingo.BingoServiceStartupParameter#newInstance(String[])
	 */
	public static void main(String[] args) {
		// 
		SwingUtilities.invokeLater(() -> start(args));
	}
	
	/**
	 * 起動処理です。
	 * @param params 起動パラメータ
	 */
	private static void start(String[] params) {
		BingoServiceStartupParameter p = BingoServiceStartupParameter.newInstance(params);
		// 
		BingoService.getInstance().start(p.getViewType(), p.getLoadFile());
	}
}
