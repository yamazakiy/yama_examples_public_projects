package yama.bingo.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import yama.bingo.service.view.types.BingoServiceViewType;
import yamax.lang.YLangUtility;

/**
 * BingoService 定義クラスです。
 */
public final class BingoServiceConstants {
	/** ビンゴ最小番号 */
	private final int _minNumber;
	/** ビンゴ最大番号 */
	private final int _maxNumber;
	/** ビンゴラッキー番号リスト（不変リスト） */
	private final List<Integer> _luckyNumberList;
	/** ビンゴアンラッキー番号リスト（不変リスト） */
	private final List<Integer> _unluckyNumberList;
	/** メッセージマップ */
	private final Map<String, String> _messageMap;
	/** オーディオファイルマップ */
	private final Map<String, String> _audioFileMap;
	
	/** インスタンス */
	private static final BingoServiceConstants instance = new BingoServiceConstants();
	
	/**
	 * インスタンスを取得します。
	 * @return BingoServiceConstants インスタンス
	 */
	public static BingoServiceConstants getInstance() {
		// 
		return instance;
	}
	
	/**
	 * BingoServiceConstants を構築します。
	 */
	private BingoServiceConstants() {
		Properties p = new Properties();
		// 
		try (
			InputStreamReader reader = new InputStreamReader(new FileInputStream(new File("resources", "bingo_service.properties")), StandardCharsets.UTF_8);
		) {
			p.load(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// ビンゴ最小最大番号
		_minNumber = YLangUtility.toInt(p.getProperty("bingo.min_number"), 1);
		_maxNumber = YLangUtility.toInt(p.getProperty("bingo.max_number"), 75);
		// 
		if (_minNumber > _maxNumber) {
			throw new IllegalArgumentException("ビンゴ最小最大番号の設定が不正です。最小：" + _minNumber + " 最大：" + _maxNumber);
		}
		// ビンゴラッキー番号リスト
		{
			List<Integer> list = readNumbers(p, "bingo.lucky_numbers");
			// 不変リスト
			_luckyNumberList = List.of(list.toArray(new Integer[] {}));
		}
		// ビンゴアンラッキー番号リスト
		{
			List<Integer> list = readNumbers(p, "bingo.unlucky_numbers");
			// 重複除外
			list.removeIf(e -> _luckyNumberList.contains(e));
			// 不変リスト
			_unluckyNumberList = List.of(list.toArray(new Integer[] {}));
		}
		// メッセージ
		{
			_messageMap = new HashMap<>();
			// 
			for (String key : p.stringPropertyNames()) {
				if (key.startsWith("message.")) {
					_messageMap.put(key, p.getProperty(key));
				}
			}
		}
		// オーディオファイル
		{
			_audioFileMap = new HashMap<>();
			// 
			for (String key : p.stringPropertyNames()) {
				if (key.startsWith("audio.")) {
					_audioFileMap.put(key, p.getProperty(key));
				}
			}
		}
	}
	
	/**
	 * プロパティの番号リストを取得します。
	 * @param p プロパティ
	 * @param key プロパティキー
	 * @return List<Integer> 番号リスト
	 */
	private List<Integer> readNumbers(Properties p, String key) {
		List<Integer> list = new ArrayList<>();
		// 
		for (String s : YLangUtility.nvl(p.getProperty(key), "").split(",")) {
			// 
			int num = YLangUtility.toInt(s == null ? s : s.trim(), -1);
			if (list.contains(num)) {
				continue;
			}
			// 
			if (num >= _minNumber && num <= _maxNumber) {
				list.add(num);
			}
		}
		// 
		return list;
	}
	
	/**
	 * デフォルトの BingoServiceViewType です。
	 * @return BingoServiceViewType デフォルトの BingoServiceViewType
	 */
	public BingoServiceViewType getDefaultBingoServiceViewType() {
		// 
		return BingoServiceViewType.VIEW_TYPE_GUI_STANDARD;
	}
	
	/**
	 * サービス名称を取得します。
	 * @return String サービス名称
	 */
	public String getServiceName() {
		// 
		return "Bingo";
	}
	
	/**
	 * ビンゴの最小番号の定義です。
	 * @return int ビンゴの最小番号
	 */
	public int getMinNumber() {
		// 
		return _minNumber;
	}
	
	/**
	 * ビンゴの最大番号の定義です。
	 * @return int ビンゴの最大番号
	 */
	public int getMaxNumber() {
		// 
		return _maxNumber;
	}
	
	/**
	 * ビンゴラッキー番号リストの定義です。
	 * 
	 * <pre>
	 *   このリストは不変リストです。
	 * </pre>
	 * 
	 * @return List<Integer> ビンゴラッキー番号リスト
	 */
	public List<Integer> getLuckyNumberList() {
		// 
		return _luckyNumberList;
	}
	
	/**
	 * ビンゴアンラッキー番号リストの定義です。
	 * 
	 * <pre>
	 *   このリストは不変リストです。
	 * </pre>
	 * 
	 * @return List<Integer> ビンゴアンラッキー番号リスト
	 */
	public List<Integer> getUnluckyNumberList() {
		// 
		return _unluckyNumberList;
	}
	
	/**
	 * メッセージを取得します。
	 * @param key BingoServiceMessageKey
	 * @return String メッセージ
	 */
	public String getMessage(BingoServiceMessageKey key) {
		// 
		return _messageMap.get(key.getKey());
	}
	
	/**
	 * パラメータありメッセージを取得します。
	 * @param key BingoServiceMessageKey
	 * @param param メッセージパラメータ
	 * @return String メッセージ
	 */
	public String getMessage(BingoServiceMessageKey key, Object... param) {
		// 
		return MessageFormat.format(getMessage(key), param);
	}
	
	/**
	 * オーディオファイル名を取得します。
	 * @param key BingoServiceAudioFileKey
	 * @return String オーディオファイル名
	 */
	public String getAudioFileName(BingoServiceAudioFileKey key) {
		// 
		return _audioFileMap.get(key.getKey());
	}
	
	/**
	 * 一時データ格納ディレクトリです。
	 * @return File 一時データ格納ディレクトリ
	 */
	public File getTempDirectory() {
		File dir = new File("temp");
		// 
		if (!dir.exists()) {
			dir.mkdirs();
		}
		// 
		return dir;
	}
}
