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
 * カスタムメイド - 増田さん作成 01
 * 
 * 以下、一部を増田さん作成から変更（修正箇所は yama コメント）
 * 
 * 変更点１) 未抽選の番号のみを表示するように実装されていたが、残り番号が少なくなるとアニメーションが崩れるので全番号を表示するように修正（もしかしたら狙ってやっているのかも ごめん！）
 * ・コンストラクタ引数の BingoNumberLabel[] を削除、該当のフィールドを削除（コメント化）
 * ・未抽選の番号に絞っている箇所を、すべての番号を対象とするように修正
 * 
 * 変更点２) 表示位置を調整（解像度によるずれ）
 * ・座標計算部分はそのまま変更せず、draw メソッドの y 座標を調整
 * 
 * </pre>
 */
public class BingoPickAnimationTypeCMMasuda01 extends AbstractBingoPickAnimation {
	//* 描画内容に変化をつけるときに値を変更する
	/**
	 * 表示する数字の最小数
	 */
	private static final int MIN_VISIBLE_NUMS = 5;
	/**
	 * 表示する数字の最大数
	 */
	private static final int MAX_VISIBLE_NUMS = 15;
	/**
	 * 最初に動かしたいか（trueで、縦のズレを収束させる感じになる）
	 */
	private static final boolean MOVE_FIRST = false;
	//* ここまで
	/**
	 * 一番下の描画位置
	 */
	private static final int LAST_Y = 1250;
	/**
	 * ビンゴで使う数字の配列
	 */
	private final int[] bingoNums = bingoNumberArrayAsInt();
	// yama.>
	//	/**
	//	 * 表示された数字を取得するために利用
	//	 */
	//	private final BingoNumberLabel[] _historyNumberLabels;
	// yama.<
	/**
	 * 表示する数字の数
	 */
	private int visibleNums;
	/**
	 * 文字スケール？？小さいと文字が大きくなる(4~5くらいがちょうどいい)
	 */
	private double scale;
	/**
	 * 一番上の描画位置
	 */
	private int baseY;
	/**
	 * 表示する数字の配列
	 */
	private int[] showNums;
	/**
	 * Y軸のポジション
	 */
	private double[] positionY;
	/**
	 * Y軸のずれ
	 */
	private double[] offsetNums;
	
	/**
	 * BingoPickAnimationTypeSimple を構築します。
	 * @param c 描画域コンポーネント
	 * @param font フォント
	 */
	// public BingoPickAnimationTypeCMMasuda01(Component c, Font font, BingoNumberLabel[] bingoNumberLabels) {
	public BingoPickAnimationTypeCMMasuda01(Component c, Font font) {
		super(c, font);
		// yama.>
		// yama.３つ目の引数（BingoNumberLabel[]）を削除、フィールドを削除
		// _historyNumberLabels = bingoNumberLabels;
		// yama.<
	}
	
	/**
	 * アニメーションが終了している状態か判断します。
	 * @return boolean 判断結果 - アニメーションが終了している場合は true
	 */
	@Override
	protected boolean isAnimationFinished() {
		//
		return getUpdateCount() > 130;
	}
	
	/**
	 * アニメーション状態を初期化します。
	 */
	@Override
	protected void initialize() {
		// 表示するものが少ない場合は、最大の配列を調整する
		int randNum = (int) (Math.random() * (MAX_VISIBLE_NUMS - MIN_VISIBLE_NUMS)) + MIN_VISIBLE_NUMS;
		visibleNums = Math.min(bingoNums.length - historySize(), randNum);
		
		// 数字が小さくなったときにも真ん中あたりに表示するための調整
		baseY = (LAST_Y) / (visibleNums + 1) + 200;
		
		// 数が少ないときに文字が小さくならないようにする
		scale = (visibleNums < 5) ? visibleNums : 4d;
		
		// 配列の初期化処理
		showNums = new int[visibleNums];
		positionY = new double[visibleNums];
		offsetNums = new double[visibleNums];
		
		// 比率で、高さや大きさを変更したいので、合計値を取得しておく　→　数学もっと分かればいい方法あるのかも、、、
		double offsetBase = 0;
		for (int i = 0; i < visibleNums; i ++) {
			offsetBase += Math.min(i + 1, visibleNums - i);
		}
		
		// 配列を用意していく
		for (int i = 0; i < visibleNums; i ++) {
			showNums[i] = randNum();
			offsetNums[i] = (Math.min(i + 1, visibleNums - i) / offsetBase) * (LAST_Y - baseY);
			
			// 最初に動きをつけるか
			if (MOVE_FIRST) {
				// 想定通りではないけど、面白いので残しておく
				positionY[i] = baseY + i * offsetNums[i];
			} else {
				// ５つ程度であれば文字が被らないようにoffsetする
				if (i == 0) {
					positionY[i] = baseY;
				} else {
					positionY[i] = positionY[i - 1] + offsetNums[i];
				}
			}
		}
		
	}
	
	/**
	 * アニメーション状態を更新します。
	 */
	@Override
	protected void update() {
		// 配列の最後がLAST_Yを超えていれば、中身を入れ替える
		while (positionY[visibleNums > 1 ? visibleNums - 1 : 0] == LAST_Y) {
			replaceArray();
		}
	}
	
	/**
	 * アニメーションを描画します。
	 */
	@Override
	protected void draw(Graphics2D g) {
		// yama.>
		// 描画位置調整
		int adjustY = (getY() + getHeight() - LAST_Y) / 2;
		// yama.<
		for (int i = 0; i < visibleNums; i ++) {
			// 数字に合わせて色を付ける
			g.setColor(BingoNumberExpression.getInstance().toBingoNumberColor(showNums[i]));
			
			// フォント設定
			float fontSize = (int) (offsetNums[i] * (visibleNums / scale));
			Font font = font().deriveFont(fontSize);
			FontMetrics fm = getFontMetrics(font);
			g.setFont(font);
			
			// 真ん中に数字を描画
			// yama.>
			// g.drawString(String.valueOf(showNums[i]), (getWidth() - fm.stringWidth(String.valueOf(showNums[i]))) / 2, (int) positionY[i]);
			g.drawString(String.valueOf(showNums[i]), (getWidth() - fm.stringWidth(String.valueOf(showNums[i]))) / 2, (int) positionY[i] + adjustY);
			// yama.<
			
			// 次のフォントの大きさに合わせてOffsetする
			// 数字が1つのときはバウンドするように見える
			if (visibleNums < 2) {
				if (positionY[i] + 100 < LAST_Y) {
					positionY[i] += 100;
				} else {
					positionY[i] = LAST_Y;
				}
			} else {
				if (i < visibleNums - 1) {
					positionY[i] += offsetNums[i + 1];
				} else {
					positionY[i] = LAST_Y;
				}
			}
		}
	}
	
	/**
	 * 配列を置き換えて更新する
	 */
	private void replaceArray() {
		int showTmp;
		double posiTmp;
		for (int i = visibleNums - 1; i > 0; i --) {
			showTmp = showNums[i - 1];
			posiTmp = positionY[i - 1];
			showNums[i - 1] = showNums[i];
			positionY[i - 1] = positionY[i];
			showNums[i] = showTmp;
			positionY[i] = posiTmp;
		}
		showNums[0] = randNum();
		positionY[0] = baseY;
	}
	
	/**
	 * まだ出ていないランダムな数字を取得する
	 *
	 * @return ランダムな数字
	 */
	private int randNum() {
		// yama.>
		//		int num = bingoNums[(int) (Math.random() * bingoNums.length)];
		//		if (Arrays.stream(_historyNumberLabels).filter(n -> n.getText().equals(Integer.toString(num))).toList().isEmpty()) {
		//			return num;
		//		} else {
		//			return randNum();
		//		}
		// 
		return bingoNums[(int) (Math.random() * bingoNums.length)];
		// yama.<
	}
	
	/**
	 * 履歴の数を取得する
	 *
	 * @return 履歴の数
	 */
	private int historySize() {
		// yama.>
		//		return Arrays.stream(_historyNumberLabels).filter(n -> !n.getText().isEmpty()).toList().size();
		return 0;
		// yama.<
	}
}
