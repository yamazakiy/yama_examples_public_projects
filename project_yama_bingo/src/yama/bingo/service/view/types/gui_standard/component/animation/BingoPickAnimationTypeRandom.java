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
 * ランダムタイプ
 * </pre>
 */
public class BingoPickAnimationTypeRandom extends AbstractBingoPickAnimation {
	/**
	 * BingoPickAnimationTypeRandom を構築します。
	 * @param c 描画域コンポーネント
	 * @param font フォント
	 */
	public BingoPickAnimationTypeRandom(Component c, Font font) {
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
		return getUpdateCount() > 240;
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
		final int target = 240;
		int[] nums = bingoNumberArrayAsInt();
		// 
		for (int i = 0; i < 4; i ++) {
			int num = nums[(int) (Math.random() * nums.length)];
			// 
			FontMetrics fm = getFontMetrics(font());
			// 
			g.setFont(font());
			g.setColor(BingoNumberExpression.getInstance().toBingoNumberColor(num));
			// 中央位置
			int ax = (getWidth() - fm.stringWidth(String.valueOf(num))) / 2;
			int ay = fm.getAscent();
			// 中央位置から繰り返し単位で四方向に調整
			ax += (i % 2 == 0 ? 1 : -1) * (target - getUpdateCount()) / (double) target * Math.random() * getWidth();
			ay += (i < 2 ? -1 : 1) * (target - getUpdateCount()) / (double) target * Math.random() * getHeight();
			// 
			g.drawString(String.valueOf(num), getX() + ax, getY() + ay);
		}
	}
}
