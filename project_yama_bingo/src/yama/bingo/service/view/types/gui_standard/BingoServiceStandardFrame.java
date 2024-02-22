package yama.bingo.service.view.types.gui_standard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import yama.bingo.service.BingoService;
import yama.bingo.service.BingoServiceAudioFileKey;
import yama.bingo.service.BingoServiceConstants;
import yama.bingo.service.BingoServiceMessageKey;
import yama.bingo.service.view.BingoServiceViewInterface;
import yama.bingo.service.view.types.gui_standard.BingoServiceStandardFrameColorManager.ColorKey;
import yama.bingo.service.view.types.gui_standard.component.BingoNumberLabel;
import yama.bingo.service.view.types.gui_standard.component.animation.BingoPickAnimationCallback;
import yama.bingo.service.view.types.gui_standard.component.animation.BingoPickAnimationInterface;
import yama.bingo.service.view.types.gui_standard.component.animation.BingoPickAnimationTypeBound;
import yama.bingo.service.view.types.gui_standard.component.animation.BingoPickAnimationTypeFalling;
import yama.bingo.service.view.types.gui_standard.component.animation.BingoPickAnimationTypeLoop;
import yama.bingo.service.view.types.gui_standard.component.animation.BingoPickAnimationTypeRandom;
import yama.bingo.service.view.types.gui_standard.component.animation.BingoPickAnimationTypeScaling;
import yama.bingo.service.view.types.gui_standard.component.animation.BingoPickAnimationTypeSimple;
import yama.bingo.service.view.types.gui_standard.component.animation.BingoPickAnimationTypeSwing;
import yama.bingo.service.view.types.gui_standard.component.animation.BingoPickAnimationTypeTextBoxLoop;
import yama.bingo.service.view.types.gui_standard.component.animation.BingoPickAnimationTypeTextLineLoop;
import yama.bingo.service.view.types.gui_standard.component.animation.BingoPickAnimationTypeTextLineScaling;
import yamax.sound.AudioManager;
import yamax.swing.YAbstractFrameFPSTargetMonitor;
import yamax.swing.YButton;
import yamax.swing.YComponentFactory;
import yamax.swing.YLabel;
import yamax.swing.YPanel;

/**
 * BingoService ビューの実装です。
 * 
 * <pre>
 * スタンダード
 * </pre>
 */
public class BingoServiceStandardFrame extends YAbstractFrameFPSTargetMonitor implements BingoServiceViewInterface, BingoPickAnimationCallback, ActionListener {
	/** コマンド 抽選 */
	private static final String COMMAND_PICK = "Pick";
	/** コマンド スキップ */
	private static final String COMMAND_SKIP = "Skip";
	/** コマンド 色モード切替 */
	private static final String COMMAND_COLOR_MODE = "Color Mode";
	/** コマンド リセット */
	private static final String COMMAND_RESET = "Reset";
	/** コマンド 終了 */
	private static final String COMMAND_EXIT = "Exit";
	
	/** メッセージラベル */
	private YLabel _messageLabel;
	/** 抽選番号ラベル */
	private BingoNumberLabel _pickNumberLabel;
	/** ビンゴ番号一覧 */
	private BingoNumberLabel[] _historyNumberLabels;
	/** 番号抽選ボタン */
	private YButton _pickButton;
	/** 色モードボタン */
	private YButton _colorModeButton;
	/** リセットボタン */
	private YButton _resetButton;
	/** 終了ボタン */
	private YButton _exitButton;
	
	/** ビンゴ抽選アニメーションリスト */
	private List<BingoPickAnimationInterface> _bingoPickAnimationList = Collections.emptyList();
	/** 現在のビンゴ抽選アニメーション */
	private BingoPickAnimationInterface _bingoPickAnimationCurrent = null;
	
	/**
	 * BingoServiceStandardFrame を構築します。
	 */
	public BingoServiceStandardFrame() {
		super(60);
		// 
		setComponents(getContentPane());
	}
	
	/**
	 * デフォルトのフレームタイトル定義です。
	 * @return String デフォルトのフレームタイトル
	 */
	@Override
	protected String defaultTitle() {
		// 
		return BingoServiceConstants.getInstance().getMessage(BingoServiceMessageKey.SERVICE_TITLE);
	}
	
	/**
	 * デフォルトのフレーム位置定義です。
	 * @return Point デフォルトのフレーム位置
	 */
	@Override
	protected Point defaultLocation() {
		// 
		return new Point(0, 0);
	}
	
	/**
	 * デフォルトのフレームサイズ定義です。
	 * @return Dimension デフォルトのフレームサイズ
	 */
	@Override
	protected Dimension defaultSize() {
		// 
		return Toolkit.getDefaultToolkit().getScreenSize();
	}
	
	/**
	 * デフォルトの最前面指定定義です。
	 * @return boolean デフォルトの最前面指定
	 */
	@Override
	protected boolean defaultAlwaysOnTop() {
		// 
		return false;
	}
	
	/**
	 * デフォルトのフレーム装飾有効化定義です。
	 * @return boolean デフォルトのフレーム装飾無効化
	 */
	@Override
	protected boolean defaultUndecorated() {
		// 
		return true;
	}
	
	/**
	 * サービス開始の通知です。
	 */
	@Override
	public void serviceStarted() {
		// 
		_messageLabel.setText(BingoServiceConstants.getInstance().getMessage(BingoServiceMessageKey.INFO_SERVICE_STARTED));
		_pickNumberLabel.setText(BingoServiceConstants.getInstance().getMessage(BingoServiceMessageKey.FREE_MARK));
		// 
		for (BingoNumberLabel l : _historyNumberLabels) {
			l.clear();
		}
		// 
		setVisible(true);
	}
	
	/**
	 * サービス停止の通知です。
	 */
	@Override
	public void serviceStopped() {
		// 
		setVisible(false);
	}
	
	/**
	 * ビンゴ番号リストリセットの更新通知です。
	 */
	@Override
	public void bingoReseted() {
		// 
		stopBingoPickAnimation();
		// 
		_messageLabel.setText(BingoServiceConstants.getInstance().getMessage(BingoServiceMessageKey.INFO_SERVICE_RESETED));
		_pickNumberLabel.clear();
		// 
		for (BingoNumberLabel l : _historyNumberLabels) {
			l.clear();
		}
	}
	
	/**
	 * ビンゴ番号選択の更新通知です。
	 * @param num 選択された番号
	 */
	@Override
	public void bingoNumberPicked(int num) {
		// 
		stopBingoPickAnimation();
		// 
		_messageLabel.setText(
			BingoServiceConstants.getInstance().getMessage(
				BingoServiceMessageKey.INFO_BINGONUMBER_PICKED, BingoService.getInstance().getBingoNumberListRef().size()
			)
		);
		_pickNumberLabel.setBingoNumber(num);
		// 
		{
			int index = 0;
			// 
			for (int n : BingoService.getInstance().getBingoNumberListRef()) {
				_historyNumberLabels[index ++].setBingoNumber(n);
			}
		}
	}
	
	/**
	 * 抽選残なしの更新通知です。
	 */
	@Override
	public void bingoNumberEmptyNotified() {
		// 
		stopBingoPickAnimation();
		// 
		_messageLabel.setText(BingoServiceConstants.getInstance().getMessage(BingoServiceMessageKey.INFO_BINGONUMBER_EMPTY));
	}
	
	/**
	 * ContentPane を生成します。
	 * @return Container ContentPane
	 */
	@Override
	protected Container createContentPane() {
		// 
		return YComponentFactory.createLayoutPanel(new BorderLayout(40, 40), BorderFactory.createEmptyBorder(40, 40, 40, 40));
	}
	
	/**
	 * ビンゴ抽選アニメーションを開始します。
	 */
	private synchronized void playBingoPickAnimation() {
		// 
		if (isActiveBingoPickAnimation()) {
			return;
		}
		// 
		_pickButton.setText(COMMAND_SKIP);
		_pickNumberLabel.clear();
		// 
		_bingoPickAnimationCurrent = _bingoPickAnimationList.get(
			BingoService.getInstance().getBingoNumberListRef().size() < 5 ? 0 : (int) (Math.random() * _bingoPickAnimationList.size())
		);
		_bingoPickAnimationCurrent.start(this);
		// 
		add(_bingoPickAnimationCurrent);
		// 
		AudioManager.getInstance().loop(BingoServiceConstants.getInstance().getAudioFileName(BingoServiceAudioFileKey.BINGO_RUNNING));
	}
	
	/**
	 * ビンゴ抽選アニメーションをスキップします。
	 */
	private synchronized void skipBingoPickAnimation() {
		// 
		if (!isActiveBingoPickAnimation()) {
			return;
		}
		// 
		_bingoPickAnimationCurrent.skip();
	}
	
	/**
	 * ビンゴ抽選アニメーションを停止します。
	 */
	private synchronized void stopBingoPickAnimation() {
		// 
		if (!isActiveBingoPickAnimation()) {
			return;
		}
		// 
		AudioManager.getInstance().pause(BingoServiceConstants.getInstance().getAudioFileName(BingoServiceAudioFileKey.BINGO_RUNNING));
		// AudioManager.getInstance().stop(BingoServiceConstants.getInstance().getAudioFileName(BingoServiceAudioFileKey.BINGO_RUNNING));
		// 
		remove(_bingoPickAnimationCurrent);
		// 
		_bingoPickAnimationCurrent = null;
	}
	
	/**
	 * ビンゴ抽選アニメーションの再生が終了したことの通知です。
	 */
	@Override
	public synchronized void bingoPickAnimationFinished() {
		// 
		if (!isActiveBingoPickAnimation()) {
			return;
		}
		// 
		BingoService.getInstance().pickBingoNumber();
		// 
		AudioManager.getInstance().play(BingoServiceConstants.getInstance().getAudioFileName(BingoServiceAudioFileKey.BINGO_STOP));
		// 
		_pickButton.setText(COMMAND_PICK);
	}
	
	/**
	 * ビンゴ抽選アニメーションがアクティブか判断します。
	 * @return boolean 判断結果 - ビンゴ抽選アニメーションがアクティブであれば true
	 */
	private synchronized boolean isActiveBingoPickAnimation() {
		// 
		return _bingoPickAnimationCurrent != null;
	}
	
	/**
	 * コンポーネントを設定します。
	 * @param c Container
	 */
	private void setComponents(Container c) {
		final String fontNameJa = "BIZ UDゴシック";
		final String fontNameAN = "Comic Sans MS";
		{
			YLabel label = new YLabel();
			label.setFont(new Font(fontNameJa, Font.PLAIN, 48));
			// 
			c.add(_messageLabel = label, BorderLayout.NORTH);
		}
		{
			BingoNumberLabel label = new BingoNumberLabel(28);
			label.setFont(new Font(fontNameAN, Font.PLAIN, 480));
			// 
			c.add(_pickNumberLabel = label, BorderLayout.CENTER);
		}
		{
			_historyNumberLabels = new BingoNumberLabel[BingoServiceConstants.getInstance().getMaxNumber() - BingoServiceConstants.getInstance().getMinNumber() + 1];
			// 
			final int cols = 10;
			final int rows = (_historyNumberLabels.length + cols - 1) / cols;
			// 
			YPanel p = YComponentFactory.createLayoutPanel(new GridLayout(rows, cols));
			// 
			for (int i = 0; i < _historyNumberLabels.length; i ++) {
				BingoNumberLabel l = new BingoNumberLabel(6);
				l.setFont(new Font(fontNameAN, Font.PLAIN, 40));
				// 
				p.add(_historyNumberLabels[i] = l);
			}
			// 
			p.setPreferredSize(new Dimension(800, -1));
			c.add(p, BorderLayout.EAST);
		}
		{
			YButton[] buttons = new YButton[] {
				_pickButton = new YButton(COMMAND_PICK), _colorModeButton = new YButton(COMMAND_COLOR_MODE), _resetButton = new YButton(COMMAND_RESET), _exitButton = new YButton(COMMAND_EXIT)
			};
			// 
			JPanel p = YComponentFactory.createLayoutPanel(new GridLayout(1, buttons.length));
			// 
			for (YButton b : buttons) {
				b.setFont(new Font(fontNameAN, Font.PLAIN, 48));
				b.setFocusable(false);
				b.setDrawShadowStringUse(true);
				b.addActionListener(this);
				p.add(b);
			}
			// 
			p.setPreferredSize(new Dimension(-1, 120));
			c.add(p, BorderLayout.SOUTH);
		}
		{
			// アニメーション
			_bingoPickAnimationList = new ArrayList<>();
			_bingoPickAnimationList.add(new BingoPickAnimationTypeSimple(_pickNumberLabel, new Font(fontNameAN, Font.PLAIN, 480)));
			_bingoPickAnimationList.add(new BingoPickAnimationTypeScaling(_pickNumberLabel, new Font(fontNameAN, Font.PLAIN, 480)));
			_bingoPickAnimationList.add(new BingoPickAnimationTypeBound(_pickNumberLabel, new Font(fontNameAN, Font.PLAIN, 480)));
			_bingoPickAnimationList.add(new BingoPickAnimationTypeRandom(_pickNumberLabel, new Font(fontNameAN, Font.PLAIN, 480)));
			_bingoPickAnimationList.add(new BingoPickAnimationTypeTextLineScaling(_pickNumberLabel, new Font(fontNameAN, Font.PLAIN, 480)));
			_bingoPickAnimationList.add(new BingoPickAnimationTypeFalling(_pickNumberLabel, new Font(fontNameAN, Font.PLAIN, 40)));
			_bingoPickAnimationList.add(new BingoPickAnimationTypeLoop(_pickNumberLabel, new Font(fontNameAN, Font.PLAIN, 480)));
			_bingoPickAnimationList.add(new BingoPickAnimationTypeTextLineLoop(_pickNumberLabel, new Font(fontNameAN, Font.PLAIN, 40)));
			_bingoPickAnimationList.add(new BingoPickAnimationTypeSwing(_pickNumberLabel, new Font(fontNameAN, Font.PLAIN, 40)));
			_bingoPickAnimationList.add(new BingoPickAnimationTypeTextBoxLoop(_pickNumberLabel, new Font(fontNameAN, Font.PLAIN, 40)));
		}
		{
			// 初期色モード
			resetColorMode();
		}
	}
	
	/**
	 * 色モードを初期設定にします。
	 */
	private void resetColorMode() {
		BingoServiceStandardFrameColorManager.getInstance().reset();
		// 
		changeColorMode();
	}
	
	/**
	 * 色モードを切り替えます。
	 */
	private void changeColorMode() {
		Map<ColorKey, Color> map = BingoServiceStandardFrameColorManager.getInstance().next();
		// 
		{
			// 
			YPanel p = (YPanel) getContentPane();
			p.setBackground(map.get(BingoServiceStandardFrameColorManager.ColorKey.CONTENT_PANE_BACKGROUND));
			p.setOpaque(true);
		}
		{
			// 
			_messageLabel.setForeground(map.get(BingoServiceStandardFrameColorManager.ColorKey.MESSAGE_LABEL_FOREGROUND));
			_messageLabel.setBackground(map.get(BingoServiceStandardFrameColorManager.ColorKey.MESSAGE_LABEL_BACKGROUND));
			_messageLabel.setOpaque(true);
			// 
			_pickNumberLabel.setForeground(map.get(BingoServiceStandardFrameColorManager.ColorKey.PICK_NUMBER_LABEL_FOREGROUND));
			_pickNumberLabel.setBackground(map.get(BingoServiceStandardFrameColorManager.ColorKey.PICK_NUMBER_LABEL_BACKGROUND));
			_pickNumberLabel.setOpaque(true);
			// 
			for (BingoNumberLabel l : _historyNumberLabels) {
				l.setForeground(map.get(BingoServiceStandardFrameColorManager.ColorKey.HISTORY_NUMBER_LABEL_FOREGROUND));
				l.setBackground(map.get(BingoServiceStandardFrameColorManager.ColorKey.HISTORY_NUMBER_LABEL_BACKGROUND));
				l.setOpaque(true);
			}
			// 
			_pickButton.setForeground(map.get(BingoServiceStandardFrameColorManager.ColorKey.BUTTON_FOREGROUND));
			_pickButton.setBackground(map.get(BingoServiceStandardFrameColorManager.ColorKey.BUTTON_BACKGROUND));
			_colorModeButton.setForeground(map.get(BingoServiceStandardFrameColorManager.ColorKey.BUTTON_FOREGROUND));
			_colorModeButton.setBackground(map.get(BingoServiceStandardFrameColorManager.ColorKey.BUTTON_BACKGROUND));
			_resetButton.setForeground(map.get(BingoServiceStandardFrameColorManager.ColorKey.BUTTON_FOREGROUND));
			_resetButton.setBackground(map.get(BingoServiceStandardFrameColorManager.ColorKey.BUTTON_BACKGROUND));
			_exitButton.setForeground(map.get(BingoServiceStandardFrameColorManager.ColorKey.BUTTON_FOREGROUND));
			_exitButton.setBackground(map.get(BingoServiceStandardFrameColorManager.ColorKey.BUTTON_BACKGROUND));
		}
		{
			// 
			_bingoPickAnimationList.forEach(e -> e.setScreenBackground(map.get(BingoServiceStandardFrameColorManager.ColorKey.SCREEN_BACKGROUND)));
		}
	}
	
	/**
	 * actionPerformed.
	 * @param e ActionEvent
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == _pickButton) {
			// 
			pickButtonActionPerformed();
		} else if (e.getSource() == _colorModeButton) {
			// 
			colorModeButtonActionPerformed();
		} else if (e.getSource() == _resetButton) {
			// 
			resetButtonActionPerformed();
		} else if (e.getSource() == _exitButton) {
			// 
			exitButtonActionPerformed();
		}
	}
	
	/**
	 * 番号抽選ボタンの処理です。
	 */
	private void pickButtonActionPerformed() {
		if (_pickButton.getText().equals(COMMAND_PICK)) {
			// ビンゴ抽選アニメーション開始
			playBingoPickAnimation();
		} else {
			// ビンゴ抽選アニメーションスキップ
			skipBingoPickAnimation();
		}
	}
	
	/**
	 * 色モードボタンの処理です。
	 */
	private void colorModeButtonActionPerformed() {
		// 色モード変更
		changeColorMode();
	}
	
	/**
	 * リセットボタンの処理です。
	 */
	private void resetButtonActionPerformed() {
		// 
		if (!_pickButton.getText().equals(COMMAND_PICK)) {
			// 
			return;
		}
		// リセット確認
		if (JOptionPane.showConfirmDialog(this, _resetButton.getText() + " ?", _resetButton.getText(), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) != JOptionPane.YES_OPTION) {
			// 
			return;
		}
		// リセット
		BingoService.getInstance().resetBingo();
	}
	
	/**
	 * 終了ボタンの処理です。
	 */
	private void exitButtonActionPerformed() {
		// 終了確認
		if (JOptionPane.showConfirmDialog(this, _exitButton.getText() + " ?", _exitButton.getText(), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) != JOptionPane.YES_OPTION) {
			// 
			return;
		}
		// 終了
		BingoService.getInstance().exit();
	}
}
