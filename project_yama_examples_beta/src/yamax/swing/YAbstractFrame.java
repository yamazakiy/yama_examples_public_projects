package yamax.swing;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

/**
 * 基底フレームクラスです。
 */
public abstract class YAbstractFrame extends JFrame {
	// ダブルバッファ（サンプルのため簡易実装）
	private BufferedImage _buffer = null;
	
	/**
	 * デフォルトのフレームタイトル定義です。
	 * @return String デフォルトのフレームタイトル
	 */
	protected abstract String defaultTitle();
	
	/**
	 * デフォルトのフレーム位置定義です。
	 * @return Point デフォルトのフレーム位置
	 */
	protected abstract Point defaultLocation();
	
	/**
	 * デフォルトのフレームサイズ定義です。
	 * @return Dimension デフォルトのフレームサイズ
	 */
	protected abstract Dimension defaultSize();
	
	/**
	 * デフォルトの最前面指定定義です。
	 * @return boolean デフォルトの最前面指定
	 */
	protected abstract boolean defaultAlwaysOnTop();
	
	/**
	 * YAbstractFrame を構築します。
	 */
	public YAbstractFrame() {
		// 
		setTitle(defaultTitle());
		setLocation(defaultLocation());
		setSize(defaultSize());
		setAlwaysOnTop(defaultAlwaysOnTop());
		// 
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setContentPane(createContentPane());
	}
	
	/**
	 * ContentPane を生成します。
	 * @return Container ContentPane
	 */
	protected Container createContentPane() {
		// 
		return YComponentFactory.createLayoutPanel();
	}
	
	/**
	 * paint.
	 * @param g Graphics
	 */
	@Override
	public final void paint(Graphics g) {
		// ダブルバッファ（サンプルのため簡易実装）
		if (_buffer == null || _buffer.getWidth() != getWidth() || _buffer.getHeight() != getHeight()) {
			_buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		}
		// 
		Graphics gg = _buffer.getGraphics();
		// 
		super.paint(gg);
		// 
		paintDone(gg);
		// 
		g.drawImage(_buffer, 0, 0, this);
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
