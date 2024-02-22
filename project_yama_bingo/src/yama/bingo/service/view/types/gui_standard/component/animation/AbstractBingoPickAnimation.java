package yama.bingo.service.view.types.gui_standard.component.animation;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.SwingUtilities;

import yama.bingo.service.BingoServiceConstants;
import yamax.swing.YComponentGraphicsManager;

/**
 * ビンゴ抽選アニメーションの基底クラスです。
 */
public abstract class AbstractBingoPickAnimation implements BingoPickAnimationInterface {
	/** 描画域コンポーネント */
	private final Component _c;
	/** デフォルトフォント */
	private final Font _font;
	/** ビンゴ番号一覧（配列形式 - String[]） */
	private final String[] _bingoNumberArrayAsString;
	/** ビンゴ番号一覧（配列形式 - int[]） */
	private final int[] _bingoNumberArrayAsInt;
	/** ビンゴ番号一覧（リスト形式 - List<String>） */
	private final List<String> _bingoNumberListAsString;
	/** ビンゴ番号一覧（リスト形式 - List<Integer>） */
	private final List<Integer> _bingoNumberListAsInteger;
	/** モデル更新カウント */
	private int _updateCount = 0;
	/** アニメーション描画スクリーン背景色 */
	private Color _screenBackground = Color.BLACK;
	/** BingoPickAnimationCallback */
	private BingoPickAnimationCallback _callback = null;
	
	/**
	 * アニメーション状態を初期化します。
	 */
	protected abstract void initialize();
	
	/**
	 * アニメーションが終了している状態か判断します。
	 * @return boolean 判断結果 - アニメーションが終了している場合は true
	 */
	protected abstract boolean isAnimationFinished();
	
	/**
	 * アニメーション状態を更新します。
	 */
	protected abstract void update();
	
	/**
	 * アニメーションを描画します。
	 */
	protected abstract void draw(Graphics2D g);
	
	/**
	 * AbstractBingoPickAnimation を構築します。
	 * @param c 描画域コンポーネント
	 * @param font フォント
	 */
	public AbstractBingoPickAnimation(Component c, Font font) {
		// 
		this._c = c;
		this._font = font;
		// 
		this._bingoNumberArrayAsString = generateBingoNumberArrayAsString();
		this._bingoNumberArrayAsInt = generateBingoNumberArrayAsInt();
		this._bingoNumberListAsString = generateBingoNumberListAsString();
		this._bingoNumberListAsInteger = generateBingoNumberListAsInteger();
	}
	
	/**
	 * アニメーション再生を開始します。
	 * @param callback BingoPickAnimationCallback
	 */
	@Override
	public final void start(BingoPickAnimationCallback callback) {
		// 
		this._callback = callback;
		this._updateCount = 0;
		// 
		initialize();
	}
	
	/**
	 * アニメーション再生をスキップします。
	 * 
	 * <pre>
	 *   notice!
	 *   このメソッドは、外部からのスキップ指示用のメソッドです。
	 *   このクラスを継承したアニメーションクラスからは直接呼び出さないでください。
	 *   FPS 管理側で例外が発生する可能性があります。
	 *   
	 *   アニメーションクラスからアニメーション再生終了を通知したい場合は、このクラスで定義している fireBingoPickAnimationFinished() メソッドを呼び出します。
	 * </pre>
	 * 
	 * @see AbstractBingoPickAnimation#fireBingoPickAnimationFinished()
	 */
	@Override
	public final void skip() {
		// 
		_callback.bingoPickAnimationFinished();
	}
	
	/**
	 * アニメーション描画スクリーン背景色を設定します。
	 * @param c アニメーション描画スクリーン背景色
	 */
	@Override
	public void setScreenBackground(Color c) {
		// 
		this._screenBackground = c;
	}
	
	/**
	 * FPS 管理からの更新通知です。
	 */
	@Override
	public final void fpsUpdate() {
		// アニメーション終了判定
		if (isAnimationFinished()) {
			fireBingoPickAnimationFinished();
			// 
			return;
		}
		// モデル更新カウント
		_updateCount ++;
		// 
		update();
	}
	
	/**
	 * FPS 管理からの描画通知です。
	 * @param g Graphics
	 */
	@Override
	public final void fpsPaint(Graphics g) {
		// 
		if (g instanceof Graphics2D) {
			Graphics2D gg = (Graphics2D) g;
			// 
			YComponentGraphicsManager.getInstance().updateGraphicsForDefaultPainting(gg);
			// 
			{
				final Color defaultColor = gg.getColor();
				final Composite defaultComposite = gg.getComposite();
				gg.setColor(_screenBackground == null ? Color.BLACK : _screenBackground);
				gg.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
				// 
				int gap = 20;
				gg.fillRect(_c.getX() + gap, _c.getY() + gap, _c.getWidth() - gap * 2, _c.getHeight() - gap * 2);
				// 
				gg.setComposite(defaultComposite);
				gg.setColor(defaultColor);
			}
			// 
			draw(gg);
		}
	}
	
	/**
	 * アニメーションの再生が終了したことを呼び出し元に通知します。
	 */
	protected final void fireBingoPickAnimationFinished() {
		// 
		SwingUtilities.invokeLater(() -> skip());
	}
	
	/**
	 * ビンゴ番号一覧（配列形式 - String[]）の参照です。
	 * @return String[] ビンゴ番号一覧（配列形式 - String[]）参照
	 */
	protected String[] bingoNumberArrayAsString() {
		// 
		return _bingoNumberArrayAsString;
	}
	
	/**
	 * ビンゴ番号一覧（配列形式 - int[]）の参照です。
	 * @return int[] ビンゴ番号一覧（配列形式 - int[]）参照
	 */
	protected int[] bingoNumberArrayAsInt() {
		// 
		return _bingoNumberArrayAsInt;
	}
	
	/**
	 * ビンゴ番号一覧（リスト形式 - List<String>）の参照です。
	 * @return List<String> ビンゴ番号一覧（リスト形式 - List<String>）参照
	 */
	protected List<String> bingoNumberListAsString() {
		// 
		return _bingoNumberListAsString;
	}
	
	/**
	 * ビンゴ番号一覧（リスト形式 - List<Integer>）の参照です。
	 * @return List<Integer> ビンゴ番号一覧（リスト形式 - List<Integer>）参照
	 */
	protected List<Integer> bingoNumberListAsInteger() {
		// 
		return _bingoNumberListAsInteger;
	}
	
	/**
	 * モデル更新カウントを取得します。
	 * @return int モデル更新カウント
	 */
	protected int getUpdateCount() {
		// 
		return _updateCount;
	}
	
	/**
	 * 描画域の x 座標を取得します。
	 * return int 描画域の x 座標
	 */
	protected int getX() {
		// 
		return _c.getX();
	}
	
	/**
	 * 描画域の y 座標を取得します。
	 * return int 描画域の y 座標
	 */
	protected int getY() {
		// 
		return _c.getY();
	}
	
	/**
	 * 描画域の幅を取得します。
	 * @return int 描画域の幅
	 */
	protected int getWidth() {
		// 
		return _c.getWidth();
	}
	
	/**
	 * 描画域の高さを取得します。
	 * @return int 描画域の高さ
	 */
	protected int getHeight() {
		// 
		return _c.getHeight();
	}
	
	/**
	 * 描画域の位置を取得します。
	 * @return Point 描画域の位置
	 */
	protected Point getLocation() {
		// 
		return _c.getLocation();
	}
	
	/**
	 * 描画域のサイズを取得します。
	 * @return Dimension 描画域のサイズ
	 */
	protected Dimension getSize() {
		// 
		return _c.getSize();
	}
	
	/**
	 * 描画域の領域を取得します。
	 * @return Rectangle 描画域の領域
	 */
	protected Rectangle getBounds() {
		// 
		return _c.getBounds();
	}
	
	/**
	 * デフォルトフォント参照です。
	 * @return Font デフォルトフォント参照
	 */
	protected Font font() {
		// 
		return _font;
	}
	
	/**
	 * FontMetrics を取得します。
	 * @param f フォント
	 */
	protected FontMetrics getFontMetrics(Font f) {
		// 
		return _c.getFontMetrics(f);
	}
	
	/**
	 * すべてのビンゴ番号一覧を配列形式で生成します。
	 * @return String[] すべてのビンゴ番号一覧
	 */
	private String[] generateBingoNumberArrayAsString() {
		// 
		return generateBingoNumberListAsString().toArray(new String[] {});
	}
	
	/**
	 * すべてのビンゴ番号一覧を配列形式で生成します。
	 * @return int[] すべてのビンゴ番号一覧
	 */
	private int[] generateBingoNumberArrayAsInt() {
		// 要素が保証されているため、要素の null 対処は不要（要素に null が含まれている場合、例外が発生）
		return generateBingoNumberListAsInteger().stream().mapToInt(Integer::intValue).toArray();
	}
	
	/**
	 * すべてのビンゴ番号一覧をリスト形式で生成します。
	 * @return List<String> すべてのビンゴ番号一覧
	 */
	private List<String> generateBingoNumberListAsString() {
		// 
		return generateBingoNumberListAsInteger().stream().map(e -> String.valueOf(e)).collect(Collectors.toList());
	}
	
	/**
	 * すべてのビンゴ番号一覧をリスト形式で生成します。
	 * @return List<Integer> すべてのビンゴ番号一覧
	 */
	private List<Integer> generateBingoNumberListAsInteger() {
		List<Integer> list = new ArrayList<>();
		// 
		for (int i = BingoServiceConstants.getInstance().getMinNumber(); i <= BingoServiceConstants.getInstance().getMaxNumber(); i ++) {
			list.add(i);
		}
		// 
		return list;
	}
}
