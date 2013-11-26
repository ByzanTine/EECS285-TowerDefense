package umich.eecs285.towerdefence;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ButtonModel;
import javax.swing.JButton;

public class TowerDefense_Button extends JButton {

	String PressedImage;
	String RolloverImage;
	String defaultImage;
	/**
	 * 
	 * @param PressedImage
	 * @param RolloverImage
	 * @param defaultImage
	 */
	public TowerDefense_Button(String PressedImage, String RolloverImage,
			String defaultImage) {
		super();
		this.PressedImage = PressedImage;
		this.RolloverImage = RolloverImage;
		this.defaultImage = defaultImage;

		setContentAreaFilled(false);
		setBorderPainted(false);

		setForeground(Color.WHITE);
		setFocusable(false);

	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		ButtonModel model = getModel();
		if (model.isPressed()) {
			// GP = new GradientPaint(0, 0, pressedColor, 0, h, pressedColor,
			// true);
			BufferedImage bimg = loadImage(PressedImage);
			g2d.drawImage(bimg, null, 0, 0);
			g2d.dispose();

		}
		if (!model.isEnabled()) {

		} else {

			// setForeground(Color.WHITE);
			if (model.isRollover()) {

				BufferedImage bimg = loadImage(RolloverImage);
				g2d.drawImage(bimg, null, 0, 0);
				g2d.dispose();

			}
		}

		BufferedImage bimg = loadImage(defaultImage);
		g2d.drawImage(bimg, null, 0, 0);
		g2d.dispose();

		super.paintComponent(g);
	}

	private BufferedImage loadImage(String filename) {
		File img = new File(filename);
		BufferedImage bimg = null;
		try {
			bimg = ImageIO.read(img);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bimg;
	}

}