package yama.bingo;

import java.io.File;

import yama.bingo.service.BingoServiceConstants;
import yama.bingo.service.view.types.BingoServiceViewType;

/**
 * BingoServiceStartup パラメータクラスです。
 */
public final class BingoServiceStartupParameter {
	/** 起動対象ビュータイプ */
	private final BingoServiceViewType _viewType;
	/** ビンゴ番号ロード用読み込みファイル */
	private final File _loadFile;
	
	/**
	 * BingoServiceStartupParameter を生成します。
	 * 
	 * <pre>
	 * 順不同パラメータ
	 * ・パラメータ１ - 起動対象ビュータイプ
	 * ・パラメータ２ - ビンゴ番号ロード用読み込みファイル
	 * </pre>
	 * 
	 * @param args 起動パラメータ
	 * @return BingoServiceStartupParameter 生成パラメータ
	 */
	public static BingoServiceStartupParameter newInstance(String[] args) {
		// 
		return new BingoServiceStartupParameter(toViewType(args), toLoadFile(args));
	}
	
	/**
	 * 起動パラメータから BingoServiceViewType を取得します。
	 * @param args 起動パラメータ
	 * @return BingoServiceViewType BingoServiceViewType
	 */
	private static BingoServiceViewType toViewType(String[] args) {
		// 
		if (args != null) {
			for (String s : args) {
				try {
					// 
					return BingoServiceViewType.valueOf(s);
				} catch (Exception e) {
					// 
					continue;
				}
			}
		}
		// 
		return BingoServiceConstants.getInstance().getDefaultBingoServiceViewType();
	}
	
	/**
	 * 起動パラメータからビンゴ番号ロード用読み込みファイルを取得します。
	 * @param args 起動パラメータ
	 * @return File ビンゴ番号ロード用読み込みファイル - 存在しない場合は null
	 */
	private static File toLoadFile(String[] args) {
		// 
		if (args != null) {
			for (String s : args) {
				try {
					// 
					File f = new File(BingoServiceConstants.getInstance().getTempDirectory(), s);
					// 
					if (f.exists() && f.isFile()) {
						// 
						return f;
					}
				} catch (Exception e) {
					// 
					continue;
				}
			}
		}
		// 
		return null;
	}
	
	/**
	 * BingoServiceStartupParameter を構築します。
	 * @param viewType 起動対象ビュータイプ
	 * @param loadFile ビンゴ番号ロード用読み込みファイル
	 */
	private BingoServiceStartupParameter(BingoServiceViewType viewType, File loadFile) {
		// 
		this._viewType = viewType;
		this._loadFile = loadFile;
	}
	
	/**
	 * 起動対象のビュータイプを取得します。
	 * @return BingoServiceViewType 起動対象のビュータイプ
	 */
	public BingoServiceViewType getViewType() {
		// 
		return _viewType;
	}
	
	/**
	 * ビンゴ番号ロード用読み込みファイルを取得します。
	 * @return File ビンゴ番号ロード用読み込みファイル - 存在しない場合は null
	 */
	public File getLoadFile() {
		// 
		return _loadFile;
	}
}
