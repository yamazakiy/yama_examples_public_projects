package yamax.sound;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

/**
 * オーディオ管理クラスです。
 */
public final class AudioManager {
	/** オーディオマップ */
	private final Map<String, Clip> _map = new HashMap<>();
	
	/** インスタンス */
	private static final AudioManager _instance = new AudioManager();
	
	/**
	 * インスタンスを取得します。
	 * @return AudioManager インスタンス
	 */
	public static AudioManager getInstance() {
		// 
		return _instance;
	}
	
	/**
	 * AudioManager を構築します。
	 */
	private AudioManager() {
	}
	
	/**
	 * クリップをロードします。
	 * @param filename ファイル名
	 */
	public synchronized void loadClip(String filename) {
		Clip c = toClip(new File("resources/sound", filename));
		// 
		if (c != null) {
			_map.put(filename, c);
		}
	}
	
	/**
	 * ファイルを読み込み、Clip を生成します。
	 * @param file ファイルパス
	 * @return Clip Clip
	 */
	private Clip toClip(File file) {
		// 
		try (AudioInputStream ais = AudioSystem.getAudioInputStream(file)) {
			// ファイル形式取得
			AudioFormat af = ais.getFormat();
			// データライン情報を構築
			DataLine.Info dataLine = new DataLine.Info(Clip.class, af);
			// ラインを取得
			Clip c = (Clip) AudioSystem.getLine(dataLine);
			// 再生準備
			c.open(ais);
			// 
			return c;
		} catch (Exception e) {
			// 
			e.printStackTrace();
		}
		// 
		return null;
	}
	
	/**
	 * 対象のクリップを再生します。
	 * @param filename ファイル名
	 */
	public synchronized void play(String filename) {
		try {
			// 
			Clip clip = _map.get(filename);
			clip.setFramePosition(0);
			clip.start();
		} catch (Exception e) {
			// 
			e.printStackTrace();
		}
	}
	
	/**
	 * 対象のクリップをループ再生します。
	 * @param filename ファイル名
	 */
	public synchronized void loop(String filename) {
		try {
			// 
			_map.get(filename).loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception e) {
			// 
			e.printStackTrace();
		}
	}
	
	/**
	 * 対象のクリップを一時停止します。
	 * @param filename ファイル名
	 */
	public synchronized void pause(String filename) {
		try {
			// 
			_map.get(filename).stop();
		} catch (Exception e) {
			// 
			e.printStackTrace();
		}
	}
	
	/**
	 * 対象のクリップを停止します。
	 * @param filename ファイル名
	 */
	public synchronized void stop(String filename) {
		try {
			Clip clip = _map.get(filename);
			// 
			clip.stop();
			clip.flush();
			clip.setFramePosition(0);
		} catch (Exception e) {
			// 
			e.printStackTrace();
		}
	}
	
	/**
	 * finalize.
	 */
	@Override
	protected void finalize() {
		try {
			// 
			_map.forEach((key, value) -> value.close());
		} catch (Exception e) {
			// 
			e.printStackTrace();
		}
	}
}
