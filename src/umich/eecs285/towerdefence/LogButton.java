package umich.eecs285.towerdefence;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
//import org.jvnet.substance.SubstanceLookAndFeel;
//import org.jvnet.substance.skin.NebulaSkin;


public class LogButton extends JButton {

	private static boolean REPAINT_SHADOW = true;
	private Color COLOR1 = new Color(255, 255, 255);
	private Color COLOR2 = new Color(115, 201, 29);
	private int outerRoundRectSize = 8;
	private int innerRoundRectSize = 6;
	private Color unselectedLow; // Lowlight for unselected tab
	private Color unselectedHigh; // Highlight for unselected tab
	private Color unselectedMid;
	private Color selectedLow = new Color(204, 67, 0);// Lowlight for selected tab
	private Color selectedHigh = Color.WHITE; // Highlight for selected tab

	public LogButton(String text,Color color1,Color color2) {
		super();
		this.COLOR1 = color1;
		this.COLOR2 = color2;
		setContentAreaFilled(false);
		setText(text);
		setBorderPainted(false);
		unselectedLow = color1;
		unselectedMid =color2;
		unselectedHigh = Color.WHITE;
		setForeground(Color.WHITE);
		setFont(new Font("Thoma", Font.BOLD, 12));
		setFocusable(false);
		setContentAreaFilled(false);
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();

		int h = getHeight();
		int w = getWidth();
        int pct=2;
		float tran = 0.1f + pct * 0.9f;

		GradientPaint GP = new GradientPaint(0, 0, COLOR1, 0, h, COLOR2, true);
		g2d.setPaint(GP);
		ButtonModel model = getModel();
		if (!model.isEnabled()) {
			GP = new GradientPaint(0, 0, COLOR1, 0, h, COLOR2, true);
		} else if (model.isRollover()) {
			GP = new GradientPaint(0, 0, COLOR1, 0, h, COLOR2, true);
		}

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		GradientPaint p1;
		GradientPaint p2;

		//If is pressed ,orange
		if (getModel().isPressed()) {
			p1 = new GradientPaint(0, 0, new Color(0, 0, 0), 0, h - 1,
					new Color(100, 100, 100));
			p2 = new GradientPaint(0, 1, new Color(0, 0, 0, 50), 0, h - 3,
					new Color(255, 255, 255, 100));
		} else {
			p1 = new GradientPaint(0, 0, new Color(100, 100, 100), 0, h - 1,
					new Color(0, 0, 0));
			p2 = new GradientPaint(0, 1, new Color(255, 255, 255, 100), 0,
					h - 3, new Color(0, 0, 0, 50));
		}

		// g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, tran));
		RoundRectangle2D.Float r2d = new RoundRectangle2D.Float(0, 0, w - 1,
				h - 1, outerRoundRectSize, outerRoundRectSize);
		Shape clip = g2d.getClip();
		g2d.clip(r2d);
		g2d.fillRect(0, 0, w, h);
		g2d.setClip(clip);
		g2d.setPaint(p1);
		g2d.drawRoundRect(0, 0, w - 1, h - 1, outerRoundRectSize,
				outerRoundRectSize);
		g2d.setPaint(p2);
		g2d.drawRoundRect(1, 1, w - 3, h - 3, innerRoundRectSize,
				innerRoundRectSize);
		// g2d.dispose();
		_drawRect(g2d, 0, w, unselectedHigh, unselectedMid, unselectedLow);
		g2d.setPaint(null);

		super.paintComponent(g);
	}

	private void _drawRect(Graphics2D g2, int x, int w, Color hiCol,
			Color mdCol, Color loCol) {
		Dimension d = getSize();

		// Divide height into quarters
		int h = (int) d.getHeight();
		int th = h / 4;
		GradientPaint p1;
		GradientPaint p2;
		ButtonModel model = getModel();
		// Paint shaded area
		if (!model.isEnabled()) {
			p1 = new GradientPaint(0, 0, new Color(192, 192, 192), 0, th,
					new Color(192, 192, 192));
			p2 = new GradientPaint(0, 4, new Color(192, 192, 192), 0, h - th
					- 4, new Color(192, 192, 192));
		} else {
			if (model.isRollover()) {
				p1 = new GradientPaint(0, 0, new Color(255, 143, 89), 0, th,
						selectedHigh);
				p2 = new GradientPaint(0, 4, selectedHigh, 0, h - th - 4,
						new Color(255, 143, 89));
			} else {
				p1 = new GradientPaint(0, 0, loCol, 0, th, hiCol);
				p2 = new GradientPaint(0, 4, hiCol, 0, h - th - 4, loCol);
			}
		}
		g2.setPaint(p1);
		g2.fillRoundRect(1, 1, w - 2, th, 6, 6);
		g2.setPaint(p2);
		g2.fillRect(1, th, w - 2, h - th - 1);
		if (model.isPressed()) {
			p1 = new GradientPaint(0, 0, selectedLow, 0, th, selectedHigh);
			p2 = new GradientPaint(0, 4, selectedHigh, 0, h - th - 4,
					selectedLow);
			g2.setPaint(p1);
			g2.fillRoundRect(1, 1, w - 2, th, 6, 6);
			g2.setPaint(p2);
			g2.fillRect(1, th, w - 2, h - th - 1);

		}
	    g2.fillRect(1,th,w-2,h-th-1);
	}

}

	
		
	 
		
		
		
		


