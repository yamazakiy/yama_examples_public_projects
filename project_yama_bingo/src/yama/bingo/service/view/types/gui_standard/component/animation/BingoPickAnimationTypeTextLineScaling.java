package yama.bingo.service.view.types.gui_standard.component.animation;

import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import yama.bingo.service.view.types.gui_standard.component.BingoNumberExpression;
import yama.bingo.service.view.types.gui_standard.component.animation.element.BingoNumberScalingObject;
import yamax.swing.YComponentConstants;
import yamax.swing.YComponentDrawer;

/**
 * ビンゴ抽選アニメーションの実装です。
 * 
 * <pre>
 * テキストライン拡大縮小タイプ
 * </pre>
 */
public class BingoPickAnimationTypeTextLineScaling extends AbstractBingoPickAnimation {
	/** オブジェクト情報（すべて） */
	private final List<BingoNumberScalingObject> _listAll = new ArrayList<>();
	/** オブジェクト情報（描画） */
	private final List<BingoNumberScalingObject> _listDraw = new ArrayList<>();
	
	/**
	 * BingoPickAnimationTypeTextLineScaling を構築します。
	 * @param c 描画域コンポーネント
	 * @param font フォント
	 */
	public BingoPickAnimationTypeTextLineScaling(Component c, Font font) {
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
			_listAll.add(new BingoNumberScalingObject(num));
		}
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
		final int skip = 4;
		// 
		if (!_listAll.isEmpty() && getUpdateCount() % skip == 0) {
			BingoNumberScalingObject o = _listAll.get((int) (Math.random() * _listAll.size()));
			_listAll.remove(o);
			// 
			o.setRatio(0);
			_listDraw.add(o);
		}
		// 
		for (BingoNumberScalingObject o : _listDraw) {
			final double r = 0.05;
			final double max = skip * r * (_listAll.size() + _listDraw.size());
			// 
			o.addRatio(r);
			// 
			if (o.getRatio() > max) {
				o.addRatio(-1 * max);
			}
		}
	}
	
	/**
	 * アニメーションを描画します。
	 */
	@Override
	protected void draw(Graphics2D g) {
		_listDraw.stream().sorted(Comparator.comparing(BingoNumberScalingObject::getRatio, Comparator.naturalOrder())).forEach(o -> {
			if (o.getRatio() < 0 || o.getRatio() > 2.2) {
				return;
			}
			// 
			g.setFont(font());
			g.setColor(BingoNumberExpression.getInstance().toBingoNumberColor(o.getNum()));
			// 
			TextLayout layout = YComponentDrawer.createTextLayout(g, String.valueOf(o.getNum()));
			Rectangle area = YComponentDrawer.toBounds(getSize(), YComponentDrawer.toSize(layout), null, YComponentConstants.CENTER, YComponentConstants.CENTER);
			// 
			final AffineTransform defaultAffineTransform = g.getTransform();
			// 
			double w = area.getWidth() * o.getRatio();
			double h = area.getHeight() * o.getRatio();
			double x = area.getX() + (getWidth() - w) / 2;
			double y = area.getY() + (getHeight() - h) / 2;
			// 
			g.scale(o.getRatio(), o.getRatio());
			g.fill(layout.getOutline(AffineTransform.getTranslateInstance(x, y + h / 2)));
			// 
			g.setTransform(defaultAffineTransform);
		});
	}
}
