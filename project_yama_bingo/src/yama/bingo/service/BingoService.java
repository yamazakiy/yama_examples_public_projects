package yama.bingo.service;

import java.io.File;
import java.util.List;

import yama.bingo.service.control.BingoServiceObserver;
import yama.bingo.service.model.BingoServiceModel;
import yama.bingo.service.view.BingoServiceViewInterface;
import yama.bingo.service.view.types.BingoServiceViewType;
import yama.bingo.service.view.types.cui.BingoServiceUIConsole;
import yama.bingo.service.view.types.gui_mockup.BingoServiceMockupFrame;
import yama.bingo.service.view.types.gui_standard.BingoServiceStandardFrame;
import yamax.sound.AudioManager;

/**
 * ビンゴサービスクラスです。
 */
public final class BingoService {
	/** BingoServiceModel */
	private BingoServiceModel _model;
	/** インスタンス */
	private static final BingoService _instance = new BingoService();
	
	/**
	 * インスタンスを取得します。
	 * @return BingoService インスタンス
	 */
	public static BingoService getInstance() {
		// 
		return _instance;
	}
	
	/**
	 * BingoService を構築します。
	 */
	private BingoService() {
		// 初期化処理
		{
			// 音声ファイルロード
			AudioManager.getInstance().loadClip(BingoServiceConstants.getInstance().getAudioFileName(BingoServiceAudioFileKey.BINGO_RUNNING));
			AudioManager.getInstance().loadClip(BingoServiceConstants.getInstance().getAudioFileName(BingoServiceAudioFileKey.BINGO_STOP));
		}
	}
	
	/**
	 * サービスを開始します。
	 * @param viewType 起動対象ビュータイプ
	 * @param loadFile ビンゴ番号ロード用読み込みファイル - 指定なしの場合は null
	 */
	public synchronized void start(BingoServiceViewType viewType, File loadFile) {
		// 
		if (_model == null) {
			// サービス開始
			_model = new BingoServiceModel(new BingoServiceObserver(createBingoServiceView(viewType)), loadFile);
			_model.start();
		}
	}
	
	/**
	 * サービスを終了します。
	 */
	public synchronized void exit() {
		//
		if (_model != null) {
			_model.stop();
		}
		//
		System.exit(0);
	}
	
	/**
	 * ビンゴをリセットします。
	 */
	public synchronized void resetBingo() {
		// 
		if (_model == null) {
			return;
		}
		// ビンゴリセット
		_model.resetBingo();
	}
	
	/**
	 * ビンゴ番号を選択します。
	 */
	public synchronized void pickBingoNumber() {
		// 
		if (_model == null) {
			return;
		}
		// ビンゴ番号選択
		_model.pickBingoNumber();
	}
	
	/**
	 * ビンゴ番号リスト（複製）の参照を取得します。
	 * @return List<Integer> ビンゴ番号リスト（複製）の参照 - モデルが存在しない場合は null
	 */
	public synchronized List<Integer> getBingoNumberListRef() {
		// 
		if (_model == null) {
			return null;
		}
		// ビンゴ番号リスト（複製）の参照
		return _model.bingoNumberListRef();
	}
	
	/**
	 * BingoServiceViewInterface インスタンスを生成します。
	 * @param type BingoServiceViewType
	 * @return BingoServiceViewInterface BingoServiceViewInterface インスタンス
	 */
	private BingoServiceViewInterface createBingoServiceView(BingoServiceViewType type) {
		// 
		switch (type) {
			case VIEW_TYPE_GUI_MOCKUP:
				// 
				return new BingoServiceMockupFrame();
			case VIEW_TYPE_GUI_STANDARD:
				// 
				return new BingoServiceStandardFrame();
			case VIEW_TYPE_CUI:
			default:
				// 
				return new BingoServiceUIConsole();
		}
	}
}
