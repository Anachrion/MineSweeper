package view.components;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DigitView extends JPanel {

	private ImageIcon icon;

	public DigitView () {
		this.icon = null;
		update (0);
	}

	public void update(int content) {

		String path = System.getProperty("user.dir") + File.separator + "images" 
				+ File.separator +"digits" + File.separator + content +".png";

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

		Dimension d = getSize();
		g.drawImage(this.icon.getImage(), 0, 0, d.width, d.height, this);
	}
}
