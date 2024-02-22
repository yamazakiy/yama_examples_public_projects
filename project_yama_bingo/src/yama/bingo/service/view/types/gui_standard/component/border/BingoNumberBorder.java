package yama.bingo.service.view.types.gui_standard.component.border;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

import javax.swing.border.Border;

/**
 * ビンゴ番号ボーダークラスです。
 */
public class BingoNumberBorder implements Border {
	/** ボーダー色 */
	private Color _borderColor;
	/** ボーダー幅 */
	private final int _borderWidth;
	
	/**
	 * BingoNumberBorder を構築します。
	 * @param w ボーダー幅
	 */
	public BingoNumberBorder(int w) {
		this(Color.BLACK, w);
	}
	
	/**
	 * BingoNumberBorder を構築します。
	 * @param c ボーダー色
	 * @param w ボーダー幅
	 */
	public BingoNumberBorder(Color c, int w) {
		this._borderColor = c;
		this._borderWidth = w;
	}
	
	/**
	 * paintBorder.
	 * @param c このボーダーがペイントされるコンポーネント
	 * @param g Graphics
	 * @param x 描画されるボーダーの x 座標
	 * @param y 描画されるボーダーの y 座標
	 * @param width 描画されるボーダーの幅
	 * @param height 描画されるボーダーの高さ
	 */
	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		// 
		if (g instanceof Graphics2D) {
			Graphics2D g2 = (Graphics2D) g;
			// 
			Insets insets = getBorderInsets(c);
			int size = Math.min(width - insets.left - insets.right, height - insets.top - insets.bottom) - _borderWidth;
			// 
			g.setColor(_borderColor);
			// 
			g2.setStroke(new BasicStroke(_borderWidth));
			g2.drawOval((width - size) / 2, (height - size) / 2, size, size);
		}
	}
	
	/**
	 * ボーダーのインセットを取得します。
	 * @param c このボーダーのインセットを適用するコンポーネント
	 * @return Insets ボーダーのインセット
	 */
	@Override
	public Insets getBorderInsets(Component c) {
		// 
		return new Insets(2, 2, 2, 2);
	}
	
	/**
	 * ボーダーが不透過かどうかを判断します。
	 * @return boolean 判断結果 - ボーダーが不透過であれば true
	 */
	@Override
	public boolean isBorderOpaque() {
		// 
		return true;
	}
	
	/**
	 * ボーダー色を設定します。
	 * @param c ボーダー色
	 */
	public void setBorderColor(Color c) {
		// 
		this._borderColor = c;
	}
}
