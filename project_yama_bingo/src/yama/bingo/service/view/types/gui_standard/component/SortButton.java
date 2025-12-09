package yama.bingo.service.view.types.gui_standard.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import yamax.swing.YButton;

/**
 * ソートボタンクラスです。
 */
public class SortButton extends YButton implements ActionListener {
	/**
	 * ソートタイプ
	 */
	public enum SortType {
		/** 抽選順 */
		ORIGINAL("ORIG"),
		/** 昇順 */
		ASC("ASC"),
		/** 降順 */
		DESC("DESC"),
		;
		
		/** ボタンタイトル */
		private final String _title;
		
		/**
		 * SortType を構築します。
		 * @param title ボタンタイトル
		 */
		private SortType(String title) {
			this._title = title;
		}
		
		/**
		 * ボタンタイトルを取得します。
		 * @return String ボタンタイトル
		 */
		public String getTitle() {
			return _title;
		}
	}
	
	/** ソートタイプ */
	private SortType _sortType;
	
	/**
	 * SortButton を構築します。
	 */
	public SortButton() {
		// 
		addActionListener(this);
		// 
		setSortType(SortType.ORIGINAL);
	}
	
	/**
	 * ソートタイプを設定します。
	 * @param type SortType
	 */
	private void setSortType(SortType type) {
		this._sortType = type;
		// 
		setText("Sort - " + _sortType.getTitle());
	}
	
	/**
	 * ソートタイプを取得します。
	 * @return SortType ソートタイプ
	 */
	public SortType getSortType() {
		return _sortType;
	}
	
	/**
	 * actionPerformed.
	 * @param e ActionEvent
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (_sortType) {
			case SortType.ORIGINAL:
				setSortType(SortType.ASC);
				break;
			case SortType.ASC:
				setSortType(SortType.DESC);
				break;
			case SortType.DESC:
			default:
				setSortType(SortType.ORIGINAL);
		}
	}
	
	/**
	 * addActionListener.
	 * @param l ActionListener
	 */
	public void addActionListener(ActionListener l) {
		super.addActionListener(l);
		// 内部のアクションを最初に行うため、自分以外のリスナが登録された場合は自身のリスナを再登録して順番調整
		if (l != this) {
			super.removeActionListener(this);
			super.addActionListener(this);
		}
	}
}
