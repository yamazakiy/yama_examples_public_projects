package yama.bingo.service.view.types.gui_standard.component.animation;

import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;

import yama.bingo.service.view.types.gui_standard.component.BingoNumberExpression;

/**
 * ビンゴ抽選アニメーションの実装です。
 * 
 * <pre>
 * ループタイプ
 * </pre>
 */
public class BingoPickAnimationTypeLoop extends AbstractBingoPickAnimation {
	/** 座標 */
	private final Point _xy = new Point();
	
	/**
	 * BingoPickAnimationTypeLoop を構築します。
	 * @param c 描画域コンポーネント
	 * @param font フォント
	 */
	public BingoPickAnimationTypeLoop(Component c, Font font) {
		super(c, font);
	}
	
	/**
	 * アニメーション状態を初期化します。
	 */
	@Override
	protected void initialize() {
		_xy.setLocation(0, 0);
	}
	
	/**
	 * アニメーションが終了している状態か判断します。
	 * @return boolean 判断結果 - アニメーションが終了している場合は true
	 */
	@Override
	protected boolean isAnimationFinished() {
		// 
		return getUpdateCount() > 320;
	}
	
	/**
	 * アニメーション状態を更新します。
	 */
	@Override
	protected void update() {
		// 半径
		double radiusx = getWidth() / 2 * 80d / getUpdateCount();
		double radiusy = getHeight() / 2 * 80d / getUpdateCount();
		// ラジアン
		double rad = Math.toRadians(getUpdateCount() * 2.5);
		// 
		double x = radiusx * Math.cos(rad);
		double y = radiusy * Math.sin(rad);
		// 
		_xy.setLocation(x, y);
	}
	
	/**
	 * アニメーションを描画します。
	 */
	@Override
	protected void draw(Graphics2D g) {
		int[] nums = bingoNumberArrayAsInt();
		int num = nums[(int) (Math.random() * nums.length)];
		// 
		Font font = font().deriveFont((float) (font().getSize() + (int) _xy.getY()));
		FontMetrics fm = getFontMetrics(font);
		// 
		g.setFont(font);
		g.setColor(BingoNumberExpression.getInstance().toBingoNumberColor(num));
		// 
		g.drawString(String.valueOf(num), cx() + (int) _xy.getX() - fm.stringWidth(String.valueOf(num)) / 2, cy() + (int) _xy.getY() + fm.getAscent() / 4);
	}
	
	/**
	 * 円の中心 x 座標です。
	 * @return int 円の中心 x 座標
	 */
	private int cx() {
		// 
		return getX() + getWidth() / 2;
	}
	
	/**
	 * 円の中心 y 座標です。
	 * @return int 円の中心 y 座標
	 */
	private int cy() {
		// 
		return getY() + getHeight() / 2;
	}
}
