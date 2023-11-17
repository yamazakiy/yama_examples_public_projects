package yama.cp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import yama.cp.element.FPSTargetObjectPoints.PointObjectInterface;
import yama.cp.element.FPSTargetObjectPointsCreated;
import yama.cp.element.FPSTargetObjectPointsDrawable;
import yamax.swing.YAbstractFrameFPSTargetMonitor;
import yamax.swing.YComponentFactory;

/**
 * CreatePointsFrame の実装です。
 */
public class CreatePointsFrame extends YAbstractFrameFPSTargetMonitor {
	/** FPSTargetObjectPointsDrawable */
	private FPSTargetObjectPointsDrawable _drawable = new FPSTargetObjectPointsDrawable(this);
	/** UIリスナ */
	private InnerUIAdapter _uiListener = new InnerUIAdapter();
	/** アクションボタン */
	private JButton _button = YComponentFactory.createButton(new InnerActionAdapter(), InnerActionAdapter.COMMAND_IMMEDIATE);
	
	/**
	 * CreatePointsFrame を構築します。
	 */
	public CreatePointsFrame() {
		super(60);
		// 
		setComponents(getContentPane());
		// 
		addMouseListener(_uiListener);
		addMouseMotionListener(_uiListener);
		// 
		add(_drawable);
	}
	
	/**
	 * デフォルトのフレームタイトル定義です。
	 * @return String デフォルトのフレームタイトル
	 */
	@Override
	protected String defaultTitle() {
		// 
		return "Demo";
	}
	
	/**
	 * デフォルトのフレーム位置定義です。
	 * @return Point デフォルトのフレーム位置
	 */
	@Override
	protected Point defaultLocation() {
		// 
		return new Point(20, 20);
	}
	
	/**
	 * デフォルトのフレームサイズ定義です。
	 * @return Dimension デフォルトのフレームサイズ
	 */
	@Override
	protected Dimension defaultSize() {
		// return Toolkit.getDefaultToolkit().getScreenSize();
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
	 * ContentPane を生成します。
	 * @return Container ContentPane
	 */
	@Override
	protected Container createContentPane() {
		JPanel p = YComponentFactory.createLayoutPanel(new BorderLayout());
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
		// 
		c.add(_button, BorderLayout.NORTH);
	}
	
	/**
	 * 
	 */
	private void create(List<PointObjectInterface> list) {
		// 
		add(new FPSTargetObjectPointsCreated(this, list));
	}
	
	/**
	 * 
	 */
	private boolean isCommandTypeImmediate() {
		// 
		return _button.getText().equals(InnerActionAdapter.COMMAND_IMMEDIATE);
	}
	
	/**
	 * InnerUIAdapter.
	 */
	private class InnerUIAdapter implements MouseInputListener {
		/**  */
		private Point _p = new Point(-1, -1);
		
		/**
		 * 
		 */
		@Override
		public void mouseDragged(MouseEvent e) {
			draw(e.getPoint());
			// 
			reset(e.getPoint());
		}
		
		/**
		 * 
		 */
		@Override
		public void mousePressed(MouseEvent e) {
			reset(e.getPoint());
			// 
			draw(e.getPoint());
		}
		
		/**
		 * 
		 */
		@Override
		public void mouseMoved(MouseEvent e) {
		}
		
		/**
		 * 
		 */
		@Override
		public void mouseExited(MouseEvent e) {
		}
		
		/**
		 * 
		 */
		@Override
		public void mouseEntered(MouseEvent e) {
		}
		
		/**
		 * 
		 */
		@Override
		public void mouseReleased(MouseEvent e) {
			if (!isCommandTypeImmediate()) {
				return;
			}
			// 
			action();
		}
		
		/**
		 * 
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
		}
		
		/**
		 * 
		 */
		private void action() {
			create(_drawable.toPointObjectList());
			// 
			_drawable.clear();
		}
		
		/**
		 * 
		 */
		private void reset(Point p) {
			// 
			this._p = p;
		}
		
		/**
		 * 
		 */
		private void draw(Point p) {
			// 
			_drawable.put(_p, p);
		}
	}
	
	/**
	 * InnerActionAdapter.
	 */
	private class InnerActionAdapter implements ActionListener {
		/**  */
		public static final String COMMAND_IMMEDIATE = "immediate";
		/**  */
		public static final String COMMAND_RELEASE = "release";
		
		/**
		 * 
		 */
		public InnerActionAdapter() {
		}
		
		/**
		 * actionPerformed.
		 * @param e ActionEvent
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (_button.getText().equals(COMMAND_IMMEDIATE)) {
				_button.setText(COMMAND_RELEASE);
			} else {
				_button.setText(COMMAND_IMMEDIATE);
				// 
				_uiListener.action();
			}
		}
	}
}
