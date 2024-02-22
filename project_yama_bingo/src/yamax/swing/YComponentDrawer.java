package yamax.swing;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.font.TextMeasurer;
import java.awt.geom.AffineTransform;
import java.text.AttributedString;
import java.util.ArrayList;

import yamax.lang.YLangUtility;

/**
 * コンポーネント描画処理クラスです。
 */
public final class YComponentDrawer {
	/**
	 * YComponentDrawer を構築します。
	 */
	private YComponentDrawer() {
	}
	
	/**
	 * 文字列を描画します。
	 * @param g Graphics
	 * @param s 描画文字列
	 * @param size コンポーネントサイズ
	 * @param inset コンポーネントインセット
	 * @param horizontalAlignment YComponentConstants で定義されている定数 LEFT、CENTER、RIGHT、LEADING、または TRAILING のうちの 1 つ
	 * @param verticalAlignment YComponentConstants で定義されている定数 TOP、CENTER、または BOTTOM のうちの 1 つ
	 * @param linegap 行間
	 * @param translateX X 座標位置調整
	 * @param translateY Y 座標位置調整
	 * @param bEnabled コンポーネント使用可判断
	 * @param bShadow 影文字描画判断
	 */
	public static void drawString(Graphics g, String[] s, Dimension size, Insets inset, int horizontalAlignment, int verticalAlignment, int linegap, int translateX, int translateY, boolean bEnabled, boolean bShadow) {
		if (s == null) {
			// 
			return;
		}
		// 
		if (g instanceof Graphics2D) {
			Graphics2D g2 = (Graphics2D) g;
			TextLayout[] layouts = createTextLayout(g2, s);
			Rectangle area = toBounds(size, toSize(layouts, linegap), inset, horizontalAlignment, verticalAlignment);
			// 
			int y = (int) (area.getY() + area.getHeight());
			// 
			for (int i = layouts.length - 1; i >= 0; i --) {
				int x = 0;
				// 
				switch (horizontalAlignment) {
					case YComponentConstants.RIGHT:
					case YComponentConstants.TRAILING:
						x = (int) (area.getX() + (area.getWidth() - layouts[i].getAdvance()));
						break;
					case YComponentConstants.LEFT:
					case YComponentConstants.LEADING:
						x = (int) (area.getX());
						break;
					case YComponentConstants.CENTER:
					default:
						x = (int) (area.getX() + ((area.getWidth() - layouts[i].getAdvance()) / 2));
						break;
				}
				// 
				drawString(g2, layouts[i], (int) (x + translateX), (int) (y - layouts[i].getDescent() + translateY), bEnabled, bShadow);
				y -= (int) (layouts[i].getAscent() + layouts[i].getDescent() + layouts[i].getLeading() + linegap);
			}
		}
	}
	
	/**
	 * 文字列を描画します。
	 * @param g Graphics2D
	 * @param l TextLayout
	 * @param x x座標
	 * @param y y座標
	 * @param bEnabled コンポーネント使用可判断
	 * @param bShadow 影文字描画判断
	 */
	private static void drawString(Graphics2D g, TextLayout l, int x, int y, boolean bEnabled, boolean bShadow) {
		final Color defaultColor = g.getColor();
		// 
		if (bShadow) {
			if (YComponentRegulations.isUseShadowString()) {
				drawShadowString(g, l, x, y);
			}
		}
		// 
		if (!bEnabled) {
			g.setColor(SystemColor.controlLtHighlight);
			l.draw(g, x + 1, y + 1);
			g.setColor(SystemColor.controlShadow);
		}
		// 
		l.draw(g, x, y);
		g.setColor(defaultColor);
	}
	
	/**
	 * 影文字列を描画します。
	 * @param g Graphics2D
	 * @param l TextLayout
	 * @param x x座標
	 * @param y y座標
	 */
	private static void drawShadowString(Graphics2D g, TextLayout l, int x, int y) {
		// 影文字処理パターン
		switch (YComponentRegulations.getShadowStringPattern()) {
			case REPETITION_4X4: // 繰り返し 4 * 4
				drawShadowStringPatternRepetition4x4(g, l, x, y);
				break;
			case REPETITION_2X2: // 繰り返し 2 * 2
				drawShadowStringPatternRepetition2x2(g, l, x, y);
				break;
			case SCALING: // スケーリング
				drawShadowStringPatternScaling(g, l, x, y);
				break;
			default:
		}
	}
	
	/**
	 * 影文字処理パターン繰り返し 4*4 で影文字列を描画します。
	 * @param g Graphics2D
	 * @param l TextLayout
	 * @param x x座標
	 * @param y y座標
	 */
	private static void drawShadowStringPatternRepetition4x4(Graphics2D g, TextLayout l, int x, int y) {
		final Color defaultColor = g.getColor();
		g.setColor(Color.black);
		// 
		final Composite defaultComposite = g.getComposite();
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.05f));
		// 
		for (int i = 0; i < 4; i ++) {
			for (int j = 0; j < 4; j ++) {
				g.fill(l.getOutline(AffineTransform.getTranslateInstance(x + i, y + j + 2)));
			}
		}
		// 
		g.setComposite(defaultComposite);
		g.setColor(defaultColor);
	}
	
	/**
	 * 影文字処理パターン繰り返し 2*2 で影文字列を描画します。
	 * @param g Graphics2D
	 * @param l TextLayout
	 * @param x x座標
	 * @param y y座標
	 */
	private static void drawShadowStringPatternRepetition2x2(Graphics2D g, TextLayout l, int x, int y) {
		// 
		final Color defaultColor = g.getColor();
		g.setColor(Color.black);
		// 
		final Composite defaultComposite = g.getComposite();
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
		// 
		for (int i = 0; i < 4; i += 2) {
			for (int j = 0; j < 4; j += 2) {
				g.fill(l.getOutline(AffineTransform.getTranslateInstance(x + i + 1, y + j + 2)));
			}
		}
		// 
		g.setComposite(defaultComposite);
		g.setColor(defaultColor);
	}
	
	/**
	 * 影文字処理パターンスケーリングで影文字列を描画します。
	 * @param g Graphics2D
	 * @param l TextLayout
	 * @param x x座標
	 * @param y y座標
	 */
	private static void drawShadowStringPatternScaling(Graphics2D g, TextLayout l, int x, int y) {
		// 
		final Color defaultColor = g.getColor();
		g.setColor(Color.black);
		// 
		final Composite defaultComposite = g.getComposite();
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
		// 
		final AffineTransform defaultAffineTransform = g.getTransform();
		g.scale(1.0f, 1.1f);
		g.fill(l.getOutline(AffineTransform.getTranslateInstance(x + 2, y)));
		// 
		g.setTransform(defaultAffineTransform);
		g.setComposite(defaultComposite);
		g.setColor(defaultColor);
	}
	
	/**
	 * TextLayout[] を作成します。
	 * @param g Graphics2D
	 * @param ss String[]
	 * @return TextLayout[] TextLayout[]
	 */
	private static TextLayout[] createTextLayout(Graphics2D g, String[] ss) {
		if (ss == null) {
			return new TextLayout[] {};
		}
		// 
		ArrayList<TextLayout> list = new ArrayList<>();
		// 
		for (String s : ss) {
			list.add(createTextLayout(g, s));
		}
		// 
		return (TextLayout[]) list.toArray(new TextLayout[] {});
	}
	
	/**
	 * TextLayout を作成します。
	 * @param g Graphics2D
	 * @param s String
	 * @return TextLayout TextLayout
	 */
	public static TextLayout createTextLayout(Graphics2D g, String s) {
		if (YLangUtility.isEmpty(s)) {
			s = YComponentConstants.LINEFEED;
		}
		// 
		AttributedString as = new AttributedString(s);
		as.addAttribute(TextAttribute.FONT, g.getFont());
		// as.addAttribute(TextAttribute.FOREGROUND, g.getColor());
		// as.addAttribute(TextAttribute.BACKGROUND, g.getBackground());
		// as.addAttribute(TextAttribute.BACKGROUND, null);
		return new TextMeasurer(as.getIterator(), g.getFontRenderContext()).getLayout(0, s.length());
	}
	
	/**
	 * 指定要素から導かれるサイズにします。
	 * @param l TextLayout
	 * @return Dimension サイズ
	 */
	public static Dimension toSize(TextLayout l) {
		// 
		return toSize(new TextLayout[] {l}, 0);
	}
	
	/**
	 * 指定要素から導かれるサイズにします。
	 * @param l TextLayout[]
	 * @param gap 行間
	 * @return Dimension サイズ
	 */
	public static Dimension toSize(TextLayout[] l, int gap) {
		if (l == null) {
			// 
			return new Dimension(0, 0);
		} else {
			// 
			float height = 0;
			float width = 0;
			// 
			for (int i = 0; i < l.length; i ++) {
				width = Math.max(width, l[i].getAdvance());
				height += l[i].getAscent() + l[i].getDescent();
				if (i < l.length - 1) {
					height += l[i].getLeading();
				}
				if (i != 0) {
					height += gap;
				}
			}
			// 
			return new Dimension((int) width, (int) height);
		}
	}
	
	/**
	 * 指定要素から導かれる領域にします。
	 * @param size コンポーネントサイズ
	 * @param textSize 描画テキストサイズ
	 * @param inset コンポーネントインセット
	 * @param horizontalAlignment YComponentConstants で定義されている定数 LEFT、CENTER、RIGHT、LEADING、または TRAILING のうちの 1 つ
	 * @param verticalAlignment YComponentConstants で定義されている定数 TOP、CENTER、または BOTTOM のうちの 1 つ
	 * @return Rectangle 領域
	 */
	public static Rectangle toBounds(Dimension size, Dimension textSize, Insets inset, int horizontalAlignment, int verticalAlignment) {
		if (textSize == null) {
			// 
			return new Rectangle(0, 0, 0, 0);
		} else if (size == null) {
			// 
			return new Rectangle(0, 0, (int) textSize.getWidth(), (int) textSize.getHeight());
		} else {
			// 
			int insetTop = (int) (inset == null ? 0 : inset.top);
			int insetBottom = (int) (inset == null ? 0 : inset.bottom);
			int insetLeft = (int) (inset == null ? 0 : inset.left);
			int insetRight = (int) (inset == null ? 0 : inset.right);
			// 
			int x = 0;
			// 
			switch (horizontalAlignment) {
				case YComponentConstants.LEFT:
				case YComponentConstants.LEADING:
					x = insetLeft;
					break;
				case YComponentConstants.RIGHT:
				case YComponentConstants.TRAILING:
					x = (int) (size.getWidth() - textSize.getWidth() - insetRight);
					break;
				case YComponentConstants.CENTER:
				default:
					x = (int) ((size.getWidth() - textSize.getWidth()) / 2);
					break;
			}
			// 
			int y = 0;
			// 
			switch (verticalAlignment) {
				case YComponentConstants.TOP:
					y = insetTop;
					break;
				case YComponentConstants.BOTTOM:
					y = (int) (size.getHeight() - textSize.getHeight() - insetBottom);
					break;
				case YComponentConstants.CENTER:
				default:
					y = (int) ((size.getHeight() - textSize.getHeight()) / 2);
					break;
			}
			// 
			return new Rectangle(x, y, (int) textSize.getWidth(), (int) textSize.getHeight());
		}
	}
}
