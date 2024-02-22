package yama.bingo.service.view.types.gui_standard.component.animation;

import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import yama.bingo.service.view.types.gui_standard.component.BingoNumberExpression;
import yama.bingo.service.view.types.gui_standard.component.animation.element.BingoNumberObject;

/**
 * ビンゴ抽選アニメーションの実装です。
 * 
 * <pre>
 * 落下タイプ
 * </pre>
 */
public class BingoPickAnimationTypeFalling extends AbstractBingoPickAnimation {
	/** オブジェクト情報（すべて） */
	private final List<BingoNumberObject> _listAll = new ArrayList<>();
	/** オブジェクト情報（描画） */
	private final List<BingoNumberObject> _listDraw = new ArrayList<>();
	
	/**
	 * BingoPickAnimationTypeFalling を構築します。
	 * @param c 描画域コンポーネント
	 * @param font フォント
	 */
	public BingoPickAnimationTypeFalling(Component c, Font font) {
		super(c, font);
	}
	
	/**
	 * アニメーション状態を初期化します。
	 */
	@Override
	protected void initialize() {
		int[] nums = bingoNumberArrayAsInt();
		// 
		_listAll.clear();
		_listDraw.clear();
		// 
		for (int num : nums) {
			_listAll.add(new BingoNumberObject(num));
		}
	}
	
	/**
	 * アニメーションが終了している状態か判断します。
	 * @return boolean 判断結果 - アニメーションが終了している場合は true
	 */
	@Override
	protected boolean isAnimationFinished() {
		// 
		return getUpdateCount() > 340;
	}
	
	/**
	 * アニメーション状態を更新します。
	 */
	@Override
	protected void update() {
		// 
		if (!_listAll.isEmpty()) {
			BingoNumberObject o = _listAll.get((int) (Math.random() * _listAll.size()));
			_listAll.remove(o);
			// 
			o.setX((int) (getWidth() * Math.random()));
			_listDraw.add(o);
		}
		// 
		for (BingoNumberObject o : _listDraw) {
			// x
			o.setXV(2);
			// 
			if (o.getX() < getWidth() / 2) {
				o.addX((int) o.getXV());
			} else {
				o.addX(-1 * (int) o.getXV());
			}
			// y
			if (o.isYFd()) {
				o.addYV(0.8);
				o.addY((int) o.getYV());
			} else {
				o.addYV(-1.2);
				o.addY(-1 * (int) o.getYV());
			}
			// 
			if (o.getYV() <= 0) {
				o.setYFd(true);
			} else if (o.getY() > 720) {
				o.setYFd(false);
			}
		}
	}
	
	/**
	 * アニメーションを描画します。
	 */
	@Override
	protected void draw(Graphics2D g) {
		// 
		for (BingoNumberObject o : _listDraw) {
			// 
			g.setFont(font());
			g.setColor(BingoNumberExpression.getInstance().toBingoNumberColor(o.getNum()));
			// 
			g.drawString(String.valueOf(o.getNum()), getX() + (int) o.getX(), getY() + (int) o.getY());
		}
	}
}
