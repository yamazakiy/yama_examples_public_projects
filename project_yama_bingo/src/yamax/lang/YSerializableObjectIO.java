package yamax.lang;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * シリアライズオブジェクトの入出力クラスです。
 */
public class YSerializableObjectIO<T extends Serializable> {
	/**
	 * YSerializableObjectIO を構築します。
	 */
	public YSerializableObjectIO() {
	}
	
	/**
	 * 対象シリアライズオブジェクトをファイルに出力します。
	 * @param path 出力ファイルパス
	 * @param source 対象シリアライズオブジェクト
	 * @throws IOException 入出力例外
	 */
	public void export(File path, T source) throws IOException {
		try (
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path, false));
		) {
			out.writeObject(source);
			out.flush();
		}
	}
	
	/**
	 * ファイルを読み込み、オブジェクトを復元します。
	 * @param path 出力ファイルパス
	 * @return T 復元したオブジェクト
	 * @throws IOException 入出力例外
	 * @throws ClassNotFoundException 直列化されたオブジェクトのクラスが見つからなかった場合
	 */
	@SuppressWarnings("unchecked")
	public T restore(File path) throws IOException, ClassNotFoundException {
		try (
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
		) {
			return (T) in.readObject();
		}
	}
}
