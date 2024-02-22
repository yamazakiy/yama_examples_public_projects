package yama.bingo.service.view.types.gui_mockup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import yama.bingo.service.BingoService;
import yama.bingo.service.view.BingoServiceViewInterface;
import yamax.swing.YAbstractFrame;
import yamax.swing.YComponentConstants;
import yamax.swing.YComponentFactory;
import yamax.swing.YPanel;

/**
 * BingoService ビューの実装です。
 * 
 * <pre>
 * モックアップ
 * </pre>
 */
public class BingoServiceMockupFrame extends YAbstractFrame implements BingoServiceViewInterface, ActionListener {
	/** メッセージラベル */
	private JLabel _messageLabel;
	/** 抽選番号ラベル */
	private JLabel _numberLabel;
	/** ビンゴ番号一覧テキストエリア */
	private JTextArea _numbersArea;
	/** 番号抽選ボタン */
	private JButton _pickButton;
	/** リセットボタン */
	private JButton _resetButton;
	/** 終了ボタン */
	private JButton _exitButton;
	
	/**
	 * BingoServiceMockupFrame を構築します。
	 */
	public BingoServiceMockupFrame() {
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
		return "ビンゴサービス";
	}
	
	/**
	 * デフォルトのフレーム位置定義です。
	 * @return Point デフォルトのフレーム位置
	 */
	@Override
	protected Point defaultLocation() {
		// 
		return new Point(40, 40);
	}
	
	/**
	 * デフォルトのフレームサイズ定義です。
	 * @return Dimension デフォルトのフレームサイズ
	 */
	@Override
	protected Dimension defaultSize() {
		// 
		return new Dimension(1024, 768);
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
		return false;
	}
	
	/**
	 * サービス開始の通知です。
	 */
	@Override
	public void serviceStarted() {
		// 
		_messageLabel.setText(" Free マスを折ってください");
		// 
		_numberLabel.setText("");
		_numbersArea.setText("");
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
		_messageLabel.setText("リセットされました");
		// 
		_numberLabel.setText("");
		_numbersArea.setText("");
	}
	
	/**
	 * ビンゴ番号選択の更新通知です。
	 * @param num 選択された番号
	 */
	@Override
	public void bingoNumberPicked(int num) {
		// 
		_messageLabel.setText(" " + BingoService.getInstance().getBingoNumberListRef().size() + " 回目の選択です");
		// 
		_numberLabel.setText(String.valueOf(num));
		// 
		StringBuilder builder = new StringBuilder();
		for (int n : BingoService.getInstance().getBingoNumberListRef()) {
			builder.append(n).append(YComponentConstants.LINEFEED);
		}
		_numbersArea.setText(builder.toString());
		_numbersArea.setCaretPosition(0);
	}
	
	/**
	 * 抽選残なしの更新通知です。
	 */
	@Override
	public void bingoNumberEmptyNotified() {
		// 
		_messageLabel.setText("すべての抽選が終わりました");
	}
	
	/**
	 * ContentPane を生成します。
	 * @return Container ContentPane
	 */
	@Override
	protected Container createContentPane() {
		YPanel p = YComponentFactory.createLayoutPanel(new BorderLayout());
		// 
		p.setOpaque(true);
		p.setBackground(Color.WHITE);
		// 
		return p;
	}
	
	/**
	 * コンポーネントを設定します。
	 * @param c Container
	 */
	private void setComponents(Container c) {
		final String fontName = "BIZ UDゴシック";
		// final String fontName = "Meiryo UI";
		{
			c.add(_messageLabel = new JLabel(), BorderLayout.NORTH);
			// 
			_messageLabel.setFont(new Font(fontName, Font.PLAIN, 48));
		}
		{
			c.add(_numberLabel = new JLabel(), BorderLayout.CENTER);
			// 
			_numberLabel.setHorizontalAlignment(JLabel.CENTER);
			_numberLabel.setFont(new Font(fontName, Font.PLAIN, 360));
		}
		{
			JScrollPane pane = new JScrollPane(_numbersArea = new JTextArea(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			// 
			_numbersArea.setEditable(false);
			_numbersArea.setFont(new Font(fontName, Font.PLAIN, 40));
			pane.setPreferredSize(new Dimension(200, -1));
			// 
			c.add(pane, BorderLayout.EAST);
		}
		{
			JPanel p = YComponentFactory.createLayoutPanel(new GridLayout());
			// 
			p.add(_pickButton = new JButton("選択"));
			p.add(_resetButton = new JButton("リセット"));
			p.add(_exitButton = new JButton("終了"));
			// 
			_pickButton.addActionListener(this);
			_resetButton.addActionListener(this);
			_exitButton.addActionListener(this);
			// 
			c.add(p, BorderLayout.SOUTH);
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
			BingoService.getInstance().pickBingoNumber();
		} else if (e.getSource() == _resetButton) {
			// 
			BingoService.getInstance().resetBingo();
		} else if (e.getSource() == _exitButton) {
			// 
			BingoService.getInstance().exit();
		}
	}
}
