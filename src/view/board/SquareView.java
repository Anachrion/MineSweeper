package view.board;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

@SuppressWarnings("serial")
public class SquareView extends JPanel {

	private int x, y;
	private ImageIcon icon;
	private boolean revealed;

	public SquareView(int x, int y) {
		this.x = x; 
		this.y = y;
		this.icon = null;
		update (false, 0);
		this.setBorder(BorderFactory.createSoftBevelBorder(BevelBorder.RAISED, Color.WHITE, Color.GRAY));

	}


	/** Update the square background and the image displayed on the square
	 * 0-8 number of adjacent mines
	 * 9 is a mine, 10 is flagged
	 * 11 exploded, 12 flagged mine, 13 empty flagged square (11,12,13 are displayed at the end of the game)**/
	public void update(boolean revealed, int content) {

		this.revealed=revealed;
		String path = System.getProperty("user.dir") + File.separator + "images" + File.separator + "square" 
				+ File.separator + content +".png";

		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(path));
		} catch (IOException e) {
			System.out.println("/!\\ IO Error. Picture file not found at :" + path + " \nDetails : " + e.getMessage());
		}
		this.icon = new ImageIcon(img);

		this.revalidate();
		this.repaint();
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		GradientPaint gp = null;

		if (this.revealed) {
			gp = new GradientPaint (0,0, Color.white, this.getWidth(), this.getHeight(), new Color(225, 225, 235), true);

		}
		else {
			gp = new GradientPaint (0,0, new Color(255, 175, 255), this.getWidth(), this.getHeight(), new Color(153, 50, 153), true);

		}

		g2d.setPaint(gp);
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

		Dimension d = getSize();
		g.drawImage(this.icon.getImage(), 0, 0, d.width, d.height, this);

	}

	public int getx () {
		return this.x;
	}

	public int gety () {
		return this.y;
	}

}