package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

import listeners.GameEndedListener;
import view.board.BoardView;
import view.components.MineCountdownView;
import view.components.TimerView;
import view.popUpWindows.AboutView;
import view.popUpWindows.DefeatView;
import view.popUpWindows.HighscoreView;
import view.popUpWindows.ParametersView;
import view.popUpWindows.VictoryView;
import controller.AbstractController;


/** The main window, arranging and displaying all the other views to the player **/ 
@SuppressWarnings("serial")
public class GameWindow extends JFrame implements GameEndedListener {


	private AbstractController controller;

	private BoardView boardView;
	private TimerView timerView;
	private MineCountdownView countdownView;

	private String difficulty;


	public GameWindow (int x, int y, String difficulty, AbstractController controller){
		super("Minesweeper Game");
		this.controller=controller;
		this.boardView = new BoardView (x, y, controller);
		this.timerView = new TimerView();
		this.countdownView = new MineCountdownView ();
		this.difficulty=difficulty;

		
		String path = System.getProperty("user.dir") + File.separator + "images" + 
				File.separator +"icon" + File.separator + "mine.png";
		try {
			Image img = ImageIO.read(new File (path));
			this.setIconImage(img);
		}catch (IOException e1) {
			System.out.println("/!\\ IO Error. Picture file not found at :" + path + " \nDetails : " + e1.getMessage());
		}
		catch (NullPointerException e2) {
			e2.printStackTrace();
		}

		this.boardView.setOpaque(false);

		JPanel east = new JPanel ();
		east.setOpaque(false);
		JPanel west = new JPanel ();
		west.setOpaque(false);
		JPanel top = new JPanel ();
		top.setOpaque(false);

		ColoredJPanel panel = new ColoredJPanel();
		panel.setLayout(new BorderLayout());
		panel.add(bottomPanelInitialize(), BorderLayout.SOUTH);
		panel.add(top, BorderLayout.NORTH);
		panel.add(this.boardView, BorderLayout.CENTER);
		panel.add(west, BorderLayout.WEST);
		panel.add(east, BorderLayout.EAST);


		this.setContentPane(panel);
		this.setJMenuBar(menuInitialize());
		this.setFocusable(true);
		pack();
		
		this.setSize(600, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	/** Initialize the gameWindow's menu **/
	private JMenuBar menuInitialize () {

		JMenuBar menuBar = new JMenuBar();
		JMenu m1 = new JMenu("Options");
		JMenu m12 = new JMenu("Difficulty");
		JMenu m2 = new JMenu("Help");

		JMenuItem item1 = new JMenuItem("New Game");
		JMenuItem itemD1 = new JMenuItem("Easy");
		JMenuItem itemD2 = new JMenuItem("Medium");
		JMenuItem itemD3 = new JMenuItem("Hard");

		JMenuItem item2 = new JMenuItem("High Scores");
		JMenuItem item3 = new JMenuItem("About");	
		JMenuItem item4 = new JMenuItem("Parameters");
		JMenuItem item5 = new JMenuItem("Exit");


		item1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				GameWindow.this.reset(); 
			}        
		});

		itemD1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				GameWindow.this.setGame(9, 9, 10);
				GameWindow.this.setDifficulty("Easy");
			}        
		});

		itemD2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				GameWindow.this.setGame(16, 16, 40);
				GameWindow.this.setDifficulty("Medium");
			}        
		});

		itemD3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				GameWindow.this.setGame(30, 16, 99);
				GameWindow.this.setDifficulty("Hard");
			}        
		});

		item2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				new HighscoreView ((JFrame)GameWindow.this, false, GameWindow.this.difficulty); 

			}        
		});

		item3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				new AboutView ((JFrame)GameWindow.this, false);               
			}        
		});

		item4.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				new ParametersView ((JFrame)GameWindow.this, false); 
			}
		});

		item5.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}        
		});


		m12.add(itemD1);
		m12.add(itemD2);
		m12.add(itemD3);

		m1.add(item1);
		m1.add(m12);
		m1.add(item2);
		m2.add(item3);
		m1.add(item4);
		m1.add(item5);

		menuBar.add(m1);
		menuBar.add(m2);

		return menuBar;
	}
	
	/** Initialize the bottom components panel **/
	public JPanel bottomPanelInitialize () {
		
		JPanel bottom = new JPanel();
		GroupLayout layout = new GroupLayout(bottom);
		bottom.setLayout(layout);

		ImagePanel p1 = new ImagePanel (System.getProperty("user.dir") + File.separator + "images" + File.separator +"icon" + File.separator +  "clock.png",
				new Dimension (32, 32));

		ImagePanel p2 = new ImagePanel (System.getProperty("user.dir") + File.separator + "images" + File.separator +"icon" + File.separator +  "mine2.png",
				new Dimension (32, 32));


		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup( layout.createSequentialGroup()
				.addComponent(this.timerView)
				.addComponent(p1)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(this.countdownView)
						.addComponent(p2)
				);

		layout.setVerticalGroup( layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(this.timerView)
						.addComponent(p1)
						.addComponent(this.countdownView)
						.addComponent(p2))
				);

		bottom.setOpaque(false);
		
		return bottom;
	}

	public void setGame (int x, int y, int mines){
		this.boardView.set(x, y);
		this.controller.setModel(x, y, mines);
		this.timerView.start();
		this.revalidate();
	}

	public void reset () {
		GameWindow.this.controller.resetModel();
		GameWindow.this.boardView.reset();
		this.timerView.start();
	}


	@Override
	public void won() {
		this.timerView.stop();
		new VictoryView (this, false, this.timerView.getCount(), this.difficulty);
	}

	@Override
	public void lost() {
		this.timerView.stop();
		new DefeatView (this, false);
	}

	public MineCountdownView getCountdownView () {
		return this.countdownView;
	}

	public BoardView getBoardView () {
		return this.boardView;
	}

	public TimerView getTimerView () {
		return this.timerView;
	}

	public AbstractController getController () {
		return this.controller;
	}

	public void setDifficulty (String dif) {
		this.difficulty = dif;
	}

	public String getDifficulty () {
		return this.difficulty;
	}


	/** JPanel component filled with an image **/
	private class ImagePanel extends JPanel {

		private ImageIcon icon;

		public ImagePanel(String path, Dimension dim) {
			super ();

			BufferedImage img = null;

			try {
				img = ImageIO.read(new File(path));
			} catch (IOException e) {
				System.out.println("/!\\ IO Error : Picture file not found at "+path+"\nDetails :"+e.getMessage());
			}
			this.icon = new ImageIcon(img);

			this.setPreferredSize(dim);
			this.setMaximumSize(dim);
			this.setMinimumSize(dim);

			this.setOpaque(false);
			this.setVisible(true);
			this.repaint();
		}

		public void paintComponent (Graphics g) {
			Dimension d = getSize();
			g.drawImage(this.icon.getImage(), 0, 0, d.width, d.height, this);
		}

	}
	
	/** JPanel component whose background color is progressively shaded**/
	private class ColoredJPanel extends JPanel {

		public ColoredJPanel() {
		}

		public void paintComponent (Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			GradientPaint gp = new GradientPaint (0,0, new Color (200,200,230), this.getWidth(), this.getHeight(), new Color (84,84,124), true);
			g2d.setPaint(gp);
			g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

		}

	}

}
