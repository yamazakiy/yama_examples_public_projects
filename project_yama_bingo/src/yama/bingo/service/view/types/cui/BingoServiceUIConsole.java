package yama.bingo.service.view.types.cui;

import java.util.Scanner;

import yama.bingo.service.BingoService;
import yama.bingo.service.view.BingoServiceViewInterface;

/**
 * BingoService ビューの実装です。
 * 
 * <pre>
 *   コンソール UI
 * </pre>
 */
public class BingoServiceUIConsole implements BingoServiceViewInterface {
	/** コマンド リセット */
	private static final String COMMAND_RESET = "reset";
	/** コマンド 終了 */
	private static final String COMMAND_EXIT = "exit";
	
	/**
	 * BingoServiceUIConsole を構築します。
	 */
	public BingoServiceUIConsole() {
	}
	
	/**
	 * サービス開始の通知です。
	 */
	@Override
	public void serviceStarted() {
		System.out.println("サービス開始");
		// コンソール制御開始
		try (Scanner scan = new Scanner(System.in)) {
			for (;;) {
				// 入力待ちメッセージ
				System.out.println();
				System.out.print("コマンド？ > ");
				// 入力受け取り
				String input = scan.nextLine();
				// 終了判定(大文字小文字は無視して判定)
				if (COMMAND_EXIT.equalsIgnoreCase(input)) {
					BingoService.getInstance().exit();
					// 
					break;
				} else if (COMMAND_RESET.equalsIgnoreCase(input)) {
					BingoService.getInstance().resetBingo();
				} else {
					BingoService.getInstance().pickBingoNumber();
				}
			}
		} finally {
			System.out.println("終了");
		}
	}
	
	/**
	 * サービス停止の通知です。
	 */
	@Override
	public void serviceStopped() {
		System.out.println("サービス終了");
	}
	
	/**
	 * ビンゴ番号リストリセットの更新通知です。
	 */
	@Override
	public void bingoReseted() {
		System.out.println("リセットされました");
	}
	
	/**
	 * ビンゴ番号選択の更新通知です。
	 * @param num 選択された番号
	 */
	@Override
	public void bingoNumberPicked(int num) {
		System.out.println("回数：" + BingoService.getInstance().getBingoNumberListRef().size());
		System.out.println("選択：" + num);
		System.out.println("一覧：" + BingoService.getInstance().getBingoNumberListRef());
	}
	
	/**
	 * 抽選残なしの更新通知です。
	 */
	@Override
	public void bingoNumberEmptyNotified() {
		System.out.println("すべての抽選が終わりました");
	}
}
