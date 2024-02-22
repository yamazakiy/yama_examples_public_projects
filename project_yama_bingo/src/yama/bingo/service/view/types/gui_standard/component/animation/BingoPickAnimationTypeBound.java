package yama.bingo.service.view.types.gui_standard.component.animation;

import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics2D;

import yama.bingo.service.view.types.gui_standard.component.BingoNumberExpression;

/**
 * ビンゴ抽選アニメーションの実装です。
 * 
 * <pre>
 * バウンドタイプ
 * </pre>
 */
public class BingoPickAnimationTypeBound extends AbstractBingoPickAnimation {
	/** 方向 - x 軸 */
	private boolean _bx = true;
	/** 方向 - y 軸 */
	private boolean _by = true;
	/** 速度 - x 軸 */
	private double _ax = 0;
	/** 速度 - y 軸 */
	private double _ay = 0;
	/** 移動距離 - x 軸 */
	private int _mx = 0;
	/** 移動距離 - y 軸 */
	private int _my = 0;
	
	/**
	 * BingoPickAnimationTypeBound を構築します。
	 * @param c 描画域コンポーネント
	 * @param font フォント
	 */
	public BingoPickAnimationTypeBound(Component c, Font font) {
		super(c, font);
	}
	
	/**
	 * アニメーション状態を初期化します。
	 */
	@Override
	protected void initialize() {
		_bx = true;
		_by = true;
		_ax = 20;
		_ay = 0;
		_mx = 160;
		_my = -200;
	}
	
	/**
	 * アニメーションが終了している状態か判断します。
	 * @return boolean 判断結果 - アニメーションが終了している場合は true
	 */
	@Override
	protected boolean isAnimationFinished() {
		// 
		return getUpdateCount() > 280;
	}
	
	/**
	 * アニメーション状態を更新します。
	 */
	@Override
	protected void update() {
		// x
		_ax *= 0.995;
		// 
		if (_bx) {
			_mx += _ax;
		} else {
			_mx -= _ax;
		}
		// 
		if (_mx > 840) {
			_bx = false;
		} else if (_mx < 0) {
			_bx = true;
		}
		// y
		if (_by) {
			_ay += 0.8;
			_my += _ay;
		} else {
			_ay -= 1.2;
			_my -= _ay;
		}
		// 
		if (_ay <= 0) {
			_by = true;
		} else if (_my > 720) {
			_by = false;
		}
	}
	
	/**
	 * アニメーションを描画します。
	 */
	@Override
	protected void draw(Graphics2D g) {
		int[] nums = bingoNumberArrayAsInt();
		int num = nums[(int) (Math.random() * nums.length)];
		// 
		g.setFont(font());
		g.setColor(BingoNumberExpression.getInstance().toBingoNumberColor(num));
		// 
		g.drawString(String.valueOf(num), getX() + _mx, (int) getY() + _my);
	}
}
