package yama.bingo.service.view.types.gui_standard;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BingoServiceStandardFrame 用の色管理クラスです。
 */
public final class BingoServiceStandardFrameColorManager {
	/** 色指定キー */
	public enum ColorKey {
		CONTENT_PANE_BACKGROUND,
		MESSAGE_LABEL_FOREGROUND,
		MESSAGE_LABEL_BACKGROUND,
		PICK_NUMBER_LABEL_FOREGROUND,
		PICK_NUMBER_LABEL_BACKGROUND,
		HISTORY_NUMBER_LABEL_FOREGROUND,
		HISTORY_NUMBER_LABEL_BACKGROUND,
		BUTTON_FOREGROUND,
		BUTTON_BACKGROUND,
		SCREEN_BACKGROUND,
	}
	
	/** 色マップリスト */
	private final List<Map<ColorKey, Color>> _list;
	/** 色マップ管理インデックス */
	private int _index = -1;
	
	/** インスタンス */
	private static final BingoServiceStandardFrameColorManager instance = new BingoServiceStandardFrameColorManager();
	
	/**
	 * インスタンスを取得します。
	 * @return BingoServiceStandardFrameColorManager インスタンス
	 */
	public static BingoServiceStandardFrameColorManager getInstance() {
		// 
		return instance;
	}
	
	/**
	 * BingoServiceStandardFrameColorManager を構築します。
	 */
	private BingoServiceStandardFrameColorManager() {
		// 
		this._list = loadColorMapList();
	}
	
	/**
	 * 色マップリストをロードします。
	 * @return List<Map<ColorKey, Color>> 色マップリスト
	 */
	private List<Map<ColorKey, Color>> loadColorMapList() {
		List<Map<ColorKey, Color>> list = new ArrayList<>();
		// 黒背景
		{
			Map<ColorKey, Color> map = new HashMap<>();
			// 
			map.put(ColorKey.CONTENT_PANE_BACKGROUND, Color.BLACK);
			map.put(ColorKey.MESSAGE_LABEL_FOREGROUND, Color.WHITE);
			map.put(ColorKey.MESSAGE_LABEL_BACKGROUND, Color.BLACK);
			map.put(ColorKey.PICK_NUMBER_LABEL_FOREGROUND, Color.WHITE);
			map.put(ColorKey.PICK_NUMBER_LABEL_BACKGROUND, Color.BLACK);
			map.put(ColorKey.HISTORY_NUMBER_LABEL_FOREGROUND, Color.WHITE);
			map.put(ColorKey.HISTORY_NUMBER_LABEL_BACKGROUND, Color.BLACK);
			map.put(ColorKey.BUTTON_FOREGROUND, Color.WHITE);
			map.put(ColorKey.BUTTON_BACKGROUND, Color.BLACK);
			map.put(ColorKey.SCREEN_BACKGROUND, Color.BLACK);
			// 
			list.add(map);
		}
		// 白背景
		{
			Map<ColorKey, Color> map = new HashMap<>();
			// 
			map.put(ColorKey.CONTENT_PANE_BACKGROUND, Color.WHITE);
			map.put(ColorKey.MESSAGE_LABEL_FOREGROUND, Color.BLACK);
			map.put(ColorKey.MESSAGE_LABEL_BACKGROUND, Color.WHITE);
			map.put(ColorKey.PICK_NUMBER_LABEL_FOREGROUND, Color.BLACK);
			map.put(ColorKey.PICK_NUMBER_LABEL_BACKGROUND, Color.WHITE);
			map.put(ColorKey.HISTORY_NUMBER_LABEL_FOREGROUND, Color.BLACK);
			map.put(ColorKey.HISTORY_NUMBER_LABEL_BACKGROUND, Color.WHITE);
			map.put(ColorKey.BUTTON_FOREGROUND, Color.BLACK);
			map.put(ColorKey.BUTTON_BACKGROUND, Color.WHITE);
			map.put(ColorKey.SCREEN_BACKGROUND, Color.WHITE);
			// 
			list.add(map);
		}
		// 暖色
		{
			Map<ColorKey, Color> map = new HashMap<>();
			// 
			map.put(ColorKey.CONTENT_PANE_BACKGROUND, new Color(255, 224, 155));
			map.put(ColorKey.MESSAGE_LABEL_FOREGROUND, Color.BLACK);
			map.put(ColorKey.MESSAGE_LABEL_BACKGROUND, new Color(255, 224, 155));
			map.put(ColorKey.PICK_NUMBER_LABEL_FOREGROUND, new Color(68, 68, 68));
			map.put(ColorKey.PICK_NUMBER_LABEL_BACKGROUND, new Color(255, 224, 155));
			map.put(ColorKey.HISTORY_NUMBER_LABEL_FOREGROUND, new Color(68, 68, 68));
			map.put(ColorKey.HISTORY_NUMBER_LABEL_BACKGROUND, new Color(255, 224, 155));
			map.put(ColorKey.BUTTON_FOREGROUND, Color.WHITE);
			map.put(ColorKey.BUTTON_BACKGROUND, new Color(255, 177, 0));
			map.put(ColorKey.SCREEN_BACKGROUND, new Color(255, 224, 155));
			// 
			list.add(map);
		}
		// グレー
		{
			Map<ColorKey, Color> map = new HashMap<>();
			// 
			map.put(ColorKey.CONTENT_PANE_BACKGROUND, Color.DARK_GRAY);
			map.put(ColorKey.MESSAGE_LABEL_FOREGROUND, Color.WHITE);
			map.put(ColorKey.MESSAGE_LABEL_BACKGROUND, Color.DARK_GRAY);
			map.put(ColorKey.PICK_NUMBER_LABEL_FOREGROUND, Color.WHITE);
			map.put(ColorKey.PICK_NUMBER_LABEL_BACKGROUND, Color.DARK_GRAY);
			map.put(ColorKey.HISTORY_NUMBER_LABEL_FOREGROUND, Color.WHITE);
			map.put(ColorKey.HISTORY_NUMBER_LABEL_BACKGROUND, Color.DARK_GRAY);
			map.put(ColorKey.BUTTON_FOREGROUND, Color.WHITE);
			map.put(ColorKey.BUTTON_BACKGROUND, Color.DARK_GRAY);
			map.put(ColorKey.SCREEN_BACKGROUND, Color.DARK_GRAY);
			// 
			list.add(map);
		}
		// 
		{
			Map<ColorKey, Color> map = new HashMap<>();
			// 
			map.put(ColorKey.CONTENT_PANE_BACKGROUND, new Color(63, 61, 86));
			map.put(ColorKey.MESSAGE_LABEL_FOREGROUND, new Color(245, 223, 77));
			map.put(ColorKey.MESSAGE_LABEL_BACKGROUND, new Color(63, 61, 86));
			map.put(ColorKey.PICK_NUMBER_LABEL_FOREGROUND, new Color(245, 223, 77));
			map.put(ColorKey.PICK_NUMBER_LABEL_BACKGROUND, new Color(63, 61, 86));
			map.put(ColorKey.HISTORY_NUMBER_LABEL_FOREGROUND, new Color(245, 223, 77));
			map.put(ColorKey.HISTORY_NUMBER_LABEL_BACKGROUND, new Color(63, 61, 86));
			map.put(ColorKey.BUTTON_FOREGROUND, new Color(245, 223, 77));
			map.put(ColorKey.BUTTON_BACKGROUND, new Color(63, 61, 86));
			map.put(ColorKey.SCREEN_BACKGROUND, new Color(63, 61, 86));
			// 
			list.add(map);
		}
		// グレー
		{
			Map<ColorKey, Color> map = new HashMap<>();
			// 
			map.put(ColorKey.CONTENT_PANE_BACKGROUND, new Color(255, 240, 106));
			map.put(ColorKey.MESSAGE_LABEL_FOREGROUND, new Color(29, 32, 138));
			map.put(ColorKey.MESSAGE_LABEL_BACKGROUND, new Color(255, 240, 106));
			map.put(ColorKey.PICK_NUMBER_LABEL_FOREGROUND, new Color(29, 32, 138));
			map.put(ColorKey.PICK_NUMBER_LABEL_BACKGROUND, new Color(255, 240, 106));
			map.put(ColorKey.HISTORY_NUMBER_LABEL_FOREGROUND, new Color(29, 32, 138));
			map.put(ColorKey.HISTORY_NUMBER_LABEL_BACKGROUND, new Color(255, 240, 106));
			map.put(ColorKey.BUTTON_FOREGROUND, new Color(29, 32, 138));
			map.put(ColorKey.BUTTON_BACKGROUND, new Color(255, 240, 106));
			map.put(ColorKey.SCREEN_BACKGROUND, new Color(255, 240, 106));
			// 
			list.add(map);
		}
		// 
		return list;
	}
	
	/**
	 * 色マップ管理インデックスを初期化します。
	 */
	public void reset() {
		// 
		this._index = -1;
	}
	
	/**
	 * 次の色マップを取得します。
	 * 
	 * <pre>
	 *   このメソッドを呼び出すと、色マップ管理インデックスがひとつ進みます。
	 * </pre>
	 * 
	 * @return Map<ColorKey, Color> 次の色マップ
	 */
	public Map<ColorKey, Color> next() {
		try {
			// 
			return _list.get(++ _index);
		} catch (Exception e) {
			// 
			return _list.get(_index = 0);
		}
	}
}
