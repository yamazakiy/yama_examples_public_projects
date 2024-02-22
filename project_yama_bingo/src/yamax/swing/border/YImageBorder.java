package yamax.swing.border;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.Icon;
import javax.swing.border.AbstractBorder;

/**
 * イメージボーダクラスです。
 */
public class YImageBorder extends AbstractBorder {
	/** イメージアイコン */
	private Icon _icon = null;
	
	/**
	 * YImageBorder を構築します。
	 * @param icon イメージアイコン
	 */
	public YImageBorder(Icon icon) {
		setIcon(icon);
	}
	
	/**
	 * 指定されたコンポーネントのボーダを、指定された位置およびサイズでペイントします。
	 * @param c このボーダがペイントされるコンポーネント
	 * @param g ペイントのグラフィックス
	 * @param x ペイントされたボーダの x 座標
	 * @param y ペイントされたボーダの y 座標
	 * @param width ペイントされたボーダの幅
	 * @param height ペイントされたボーダの高さ
	 */
	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		if (hasIcon()) {
			getIcon().paintIcon(c, g, x, y);
		}
	}
	
	/**
	 * ボーダのインセットの値を返します。
	 * @param c このボーダのインセットの値を適用するコンポーネント
	 * @return Insets ボーダのインセットの値
	 */
	@Override
	public Insets getBorderInsets(Component c) {
		return new Insets(0, 0, 0, 0);
	}
	
	/**
	 * ボーダが不透明かどうかを返します。
	 * @return boolean 判断結果 - ボーダが不透明の場合は true
	 */
	@Override
	public boolean isBorderOpaque() {
		if (hasIcon()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * アイコンが設定されているか判断します。
	 * @return boolean 判断結果 - アイコンが設定されている場合は true
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
		this._icon = icon;
	}
	
	/**
	 * アイコンを取得します。
	 * @return Icon アイコン
	 */
	public Icon getIcon() {
		return _icon;
	}
}
