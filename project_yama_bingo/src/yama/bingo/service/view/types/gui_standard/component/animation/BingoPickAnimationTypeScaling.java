package yama.bingo.service.view.types.gui_standard.component.animation;

import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import yama.bingo.service.view.types.gui_standard.component.BingoNumberExpression;

/**
 * ビンゴ抽選アニメーションの実装です。
 * 
 * <pre>
 * 拡大縮小タイプ
 * </pre>
 */
public class BingoPickAnimationTypeScaling extends AbstractBingoPickAnimation {
	/**
	 * BingoPickAnimationTypeScaling を構築します。
	 * @param c 描画域コンポーネント
	 * @param font フォント
	 */
	public BingoPickAnimationTypeScaling(Component c, Font font) {
		super(c, font);
	}
	
	/**
	 * アニメーション状態を初期化します。
	 */
	@Override
	protected void initialize() {
	}
	
	/**
	 * アニメーションが終了している状態か判断します。
	 * @return boolean 判断結果 - アニメーションが終了している場合は true
	 */
	@Override
	protected boolean isAnimationFinished() {
		// 
		return getUpdateCount() > 200;
	}
	
	/**
	 * アニメーション状態を更新します。
	 */
	@Override
	protected void update() {
	}
	
	/**
	 * アニメーションを描画します。
	 */
	@Override
	protected void draw(Graphics2D g) {
		int[] nums = bingoNumberArrayAsInt();
		int num = nums[(int) (Math.random() * nums.length)];
		// 
		Font font = font().deriveFont((float) getDrawFontSize());
		FontMetrics fm = getFontMetrics(font);
		// 
		g.setFont(font);
		g.setColor(BingoNumberExpression.getInstance().toBingoNumberColor(num));
		// 
		g.drawString(String.valueOf(num), getX() + (getWidth() - fm.stringWidth(String.valueOf(num))) / 2, (int) getY() + fm.getAscent() + (getHeight() - font.getSize()) / 2);
	}
	
	/**
	 * 描画するフォントサイズを計算します。
	 * @return int 描画するフォントサイズ
	 */
	private int getDrawFontSize() {
		// 
		try {
			if (getUpdateCount() < 160) {
				// 
				return Math.max(font().getSize() / getUpdateCount(), 4);
			} else {
				// 
				return Math.max(font().getSize() / (200 - getUpdateCount()), 4);
			}
		} catch (Exception e) {
			// 
			return font().getSize();
		}
	}
}
