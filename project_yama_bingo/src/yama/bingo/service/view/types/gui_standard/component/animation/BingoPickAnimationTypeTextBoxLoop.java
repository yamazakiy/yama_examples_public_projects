package yama.bingo.service.view.types.gui_standard.component.animation;

import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.List;

import yama.bingo.service.BingoService;
import yama.bingo.service.view.types.gui_standard.component.BingoNumberExpression;
import yama.bingo.service.view.types.gui_standard.component.animation.element.BingoNumberCircleObject;

/**
 * ビンゴ抽選アニメーションの実装です。
 * 
 * <pre>
 * テキストボックスループタイプ
 * </pre>
 */
public class BingoPickAnimationTypeTextBoxLoop extends AbstractBingoPickAnimation {
	/** オブジェクト情報 */
	private BingoNumberCircleObject[][] _bingoNumberArrayDiv;
	/** 抽選済み番号リスト */
	private List<Integer> _pickedBingoNumberList;
	
	/**
	 * BingoPickAnimationTypeTextBoxLoop を構築します。
	 * @param c 描画域コンポーネント
	 * @param font フォント
	 */
	public BingoPickAnimationTypeTextBoxLoop(Component c, Font font) {
		super(c, font);
		//
		_bingoNumberArrayDiv = generateBingoNumberArrayDiv();
	}
	
	/**
	 * アニメーション状態を初期化します。
	 */
	@Override
	protected void initialize() {
		_bingoNumberArrayDiv = generateBingoNumberArrayDiv();
		_pickedBingoNumberList = BingoService.getInstance().getBingoNumberListRef();
	}
	
	/**
	 * アニメーションが終了している状態か判断します。
	 * @return boolean 判断結果 - アニメーションが終了している場合は true
	 */
	@Override
	protected boolean isAnimationFinished() {
		// 
		return getUpdateCount() > 420;
	}
	
	/**
	 * アニメーション状態を更新します。
	 */
	@Override
	protected void update() {
		for (int i = 0; i < _bingoNumberArrayDiv.length; i ++) {
			for (int j = 0; j < _bingoNumberArrayDiv[i].length; j ++) {
				BingoNumberCircleObject o = _bingoNumberArrayDiv[i][j];
				// 
				if (o == null) {
					// 
					continue;
				}
				// 半径
				if (getUpdateCount() < 100) {
					o.setRadiusX(getWidth() / 12);
					o.setRadiusY(getHeight() / _bingoNumberArrayDiv.length);
				} else if (getUpdateCount() < 180) {
					o.addRadiusX(4);
				} else if (getUpdateCount() < 280) {
					o.setRadiusX(o.getRadiusX());
				} else {
					o.addRadiusX(-4);
					o.setRadiusX(Math.max(o.getRadiusX(), 2));
				}
				// ラジアン
				double rad = Math.toRadians(getUpdateCount() * 2) - i * (Math.PI * 2 / _bingoNumberArrayDiv.length);
				// 
				double x = o.getRadiusX() * Math.cos(rad);
				double y = o.getRadiusY() * Math.sin(rad);
				// 
				o.setX((int) x);
				o.setY((int) y);
			}
		}
	}
	
	/**
	 * アニメーションを描画します。
	 */
	@Override
	protected void draw(Graphics2D g) {
		for (int i = 0; i < _bingoNumberArrayDiv.length; i ++) {
			for (int j = 0; j < _bingoNumberArrayDiv[i].length; j ++) {
				BingoNumberCircleObject o = _bingoNumberArrayDiv[i][j];
				// 
				if (o == null) {
					// 
					continue;
				}
				// 
				Font font = font();
				FontMetrics fm = getFontMetrics(font);
				// 
				g.setFont(font);
				g.setColor(BingoNumberExpression.getInstance().toBingoNumberColor(o.getNum()));
				// 
				int lgap = 8 + fm.getAscent() * 2;
				int y = j * lgap + ((getHeight() - (lgap * _bingoNumberArrayDiv[i].length)) / 2) + 40;
				// 
				g.drawString(String.valueOf(o.getNum()), cx() + (int) o.getX() - fm.stringWidth(String.valueOf(o.getNum())) / 2, y + (int) o.getY() + fm.getAscent() / 4);
				g.drawOval(cx() + (int) o.getX() - fm.stringWidth(String.valueOf(o.getNum())) / 2 - 4, y + (int) o.getY() + fm.getAscent() / 4 - 4, 8, 8);
				if (_pickedBingoNumberList.contains(o.getNum())) {
					g.fillRect(cx() + (int) o.getX() - fm.stringWidth(String.valueOf(o.getNum())) / 2 - 4, y + (int) o.getY() + fm.getAscent() / 4 + 4, fm.stringWidth(String.valueOf(o.getNum())), fm.getAscent());
				} else {
					g.drawRect(cx() + (int) o.getX() - fm.stringWidth(String.valueOf(o.getNum())) / 2 - 4, y + (int) o.getY() + fm.getAscent() / 4 + 4, fm.stringWidth(String.valueOf(o.getNum())), fm.getAscent());
				}
			}
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
	
	/**
	 * ビンゴ番号情報を配列に分割して生成します。
	 * @return BingoNumberCircleObject[][] 配列に分割したビンゴ番号情報
	 */
	private BingoNumberCircleObject[][] generateBingoNumberArrayDiv() {
		final int DIV_SIZE = 8;
		// 
		int[] source = bingoNumberArrayAsInt();
		// 
		BingoNumberCircleObject[][] array = new BingoNumberCircleObject[DIV_SIZE][(source.length + DIV_SIZE - 1) / DIV_SIZE];
		//
		for (int i = 0; i < array.length; i ++) {
			for (int j = 0; j < array[i].length; j ++) {
				try {
					array[i][j] = new BingoNumberCircleObject(source[i * array[i].length + j]);
				} catch (Exception e) {
					array[i][j] = null;
				}
			}
		}
		//
		return array;
	}
}
