package yama.bingo.service.view.types.gui_standard.component.animation;

import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import yama.bingo.service.view.types.gui_standard.component.BingoNumberExpression;
import yama.bingo.service.view.types.gui_standard.component.animation.element.BingoNumberCircleObject;

/**
 * ビンゴ抽選アニメーションの実装です。
 * 
 * <pre>
 * テキストラインループタイプ
 * </pre>
 */
public class BingoPickAnimationTypeTextLineLoop extends AbstractBingoPickAnimation {
	/** オブジェクト情報 */
	private final List<BingoNumberCircleObject> _list = new ArrayList<>();
	
	/**
	 * BingoPickAnimationTypeTextLineLoop を構築します。
	 * @param c 描画域コンポーネント
	 * @param font フォント
	 */
	public BingoPickAnimationTypeTextLineLoop(Component c, Font font) {
		super(c, font);
	}
	
	/**
	 * アニメーション状態を初期化します。
	 */
	@Override
	protected void initialize() {
		int[] nums = bingoNumberArrayAsInt();
		// 
		_list.clear();
		// 
		for (int num : nums) {
			_list.add(new BingoNumberCircleObject(num));
		}
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
		// 
		for (int i = 0; i < _list.size(); i ++) {
			BingoNumberCircleObject o = _list.get(i);
			// 半径
			double radiusx = getWidth() / 2 - i * 4;
			double radiusy = getHeight() / 8;
			// ラジアン
			double rad = Math.toRadians(getUpdateCount() * 2.5 * 2 - i * 8);
			// 
			double x = radiusx * Math.cos(rad);
			double y = radiusy * Math.sin(rad);
			// 
			o.setX((int) x);
			// 
			if (getUpdateCount() < 228) {
				o.setY((int) y + getUpdateCount() * 4 - i * 8);
			} else {
				if (getUpdateCount() - 228 > i) {
					o.addYV(8);
				}
				o.addY(-1 * (int) o.getYV());
			}
		}
	}
	
	/**
	 * アニメーションを描画します。
	 */
	@Override
	protected void draw(Graphics2D g) {
		// 
		for (BingoNumberCircleObject o : _list) {
			Font font = font().deriveFont((float) (font().getSize() + (int) o.getY() / 20));
			FontMetrics fm = getFontMetrics(font);
			// 
			g.setFont(font);
			g.setColor(BingoNumberExpression.getInstance().toBingoNumberColor(o.getNum()));
			// 
			g.drawString(String.valueOf(o.getNum()), cx() + (int) o.getX() - fm.stringWidth(String.valueOf(o.getNum())) / 2, (int) o.getY() + fm.getAscent() / 4);
		}
	}
	
	/**
	 * 円の中心 x 座標です。
	 * @return int 円の中心 x 座標
	 */
	private int cx() {
		// 
		return getX() + getWidth() / 2;
	}
}
