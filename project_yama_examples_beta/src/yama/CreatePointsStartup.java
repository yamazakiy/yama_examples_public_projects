package yama;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import yama.cp.CreatePointsFrame;

/**
 * CreatePoints 起動処理クラスです。
 */
public class CreatePointsStartup {
	/**
	 * static.
	 */
	static {
		String[] laf = new String[] {
			"javax.swing.plaf.nimbus.NimbusLookAndFeel",
			"javax.swing.plaf.metal.MetalLookAndFeel",
			"com.sun.java.swing.plaf.motif.MotifLookAndFeel",
			"com.sun.java.swing.plaf.windows.WindowsLookAndFeel",
			"com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel"
		};
		// 
		for (String s : laf) {
			try {
				UIManager.setLookAndFeel(s);
				// 
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
	 * @param args parameters
	 */
	public static void main(String[] args) {
		// 
		SwingUtilities.invokeLater(() -> createAndShowGUI());
	}
	
	/**
	 * createAndShowGUI.
	 */
	private static void createAndShowGUI() {
		// 
		new CreatePointsFrame().setVisible(true);
	}
}
