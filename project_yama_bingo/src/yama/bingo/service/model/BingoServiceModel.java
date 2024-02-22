package yama.bingo.service.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import yama.bingo.service.BingoServiceConstants;
import yama.bingo.service.control.BingoServiceObserver;
import yamax.lang.YSerializableObjectIO;

/**
 * BingoService モデルクラスです。
 */
public class BingoServiceModel {
	/** BingoServiceObserver */
	private final BingoServiceObserver _observer;
	/** ビンゴ番号選択 - 乱数管理 */
	private final Random _random = new Random();
	/** ビンゴ番号リスト */
	private final ArrayList<Integer> _list = new ArrayList<>();
	/** ビンゴ番号 I/O */
	private final YSerializableObjectIO<ArrayList<Integer>> _io = new YSerializableObjectIO<>();
	/** バックアップファイル */
	private File _backupFile;
	
	/**
	 * BingoServiceModel を構築します。
	 * @param observer BingoServiceObserver
	 * @param file ビンゴ番号ロード用読み込みファイル - 指定なしの場合は null
	 */
	public BingoServiceModel(BingoServiceObserver observer, File file) {
		// 
		this._observer = observer;
		this._backupFile = load(file);
	}
	
	/**
	 * サービスを開始します。
	 */
	public synchronized void start() {
		//
		_observer.start();
	}
	
	/**
	 * サービスを停止します。
	 */
	public synchronized void stop() {
		//
		_observer.stop();
	}
	
	/**
	 * ビンゴをリセットします。
	 */
	public synchronized void resetBingo() {
		// バックアップファイル入れ替え
		this._backupFile = load(null);
		// 更新通知（ビンゴ番号リストリセット）
		_observer.notifyBingoReseted();
	}
	
	/**
	 * ビンゴ番号を選択します。
	 */
	public synchronized void pickBingoNumber() {
		// 基本情報
		final int min = BingoServiceConstants.getInstance().getMinNumber();
		final int max = BingoServiceConstants.getInstance().getMaxNumber();
		final List<Integer> luckyList = BingoServiceConstants.getInstance().getLuckyNumberList();
		final List<Integer> unluckyList = BingoServiceConstants.getInstance().getUnluckyNumberList();
		// 残数判定
		if (_list.size() >= max - min + 1) {
			// 抽選残なし
			_observer.notifyBingoNumberEmpty();
			// 
			return;
		}
		// 
		for (;;) {
			// ビンゴ番号選択
			int num = min + _random.nextInt(max - min + 1);
			// 抽選済みチェック
			if (_list.contains(num)) {
				// 
				continue;
			}
			// ラッキー番号（ラッキー番号は優先的に抽選される）
			if (_list.size() < luckyList.size() && !luckyList.contains(num)) {
				// ラッキー番号がすべて抽選されていない状態でラッキー番号以外が抽選されたら再抽選
				continue;
			}
			// アンラッキー番号（アンラッキー番号は抽選の終盤まで抽選されない）
			if (unluckyList.contains(num) && (max - min + 1) - _list.size() > unluckyList.size()) {
				// アンラッキー番号以外で抽選されていない番号が残っている場合は再抽選
				continue;
			}
			// ビンゴ番号リスト更新
			_list.add(0, num);
			// ビンゴ番号リスト保存
			try {
				_io.export(_backupFile, _list);
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 更新通知（ビンゴ番号選択）
			_observer.notifyBingoNumberPicked(num);
			// 
			break;
		}
	}
	
	/**
	 * ビンゴ番号リスト（複製）の参照です。
	 * @return List<Integer> ビンゴ番号リスト（複製）参照
	 */
	@SuppressWarnings("unchecked")
	public synchronized List<Integer> bingoNumberListRef() {
		// ビンゴ番号リスト（複製）
		return (List<Integer>) _list.clone();
	}
	
	/**
	 * ビンゴ状態をロードします。
	 * @param file ビンゴ番号ロード用読み込みファイル - 指定なしの場合は null
	 * @return File バックアップファイル
	 */
	private File load(File file) {
		// ビンゴ番号リストをリセット
		_list.clear();
		// 
		try {
			// 
			if (file != null) {
				ArrayList<Integer> list = _io.restore(file);
				// 
				_list.addAll(list);
				// 
				return file;
			}
		} catch (Exception e) {
			// 
			e.printStackTrace();
		}
		// 
		return new File(BingoServiceConstants.getInstance().getTempDirectory(), "~temp.bingo." + System.currentTimeMillis() + "~.data");
	}
}
