package view.popUpWindows;

import highscores.HighscoreManager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import view.GameWindow;

/** Victory popup window**/
@SuppressWarnings("serial")
public class VictoryView extends JDialog {
	
	private int chrono;
	private String difficulty;
	private JTextField textfield;
	private JPanel centerPanel;
	private boolean saved;
	
	public VictoryView(JFrame parent, boolean modal, int chrono, String difficulty){
		super(parent, "Victory !", modal);
		this.chrono = chrono;
		this.difficulty = difficulty;
		this.saved=false;
		initialize();

		this.setSize(300, 400);
		this.setLocationRelativeTo(parent);
		this.setVisible(true);
	}

	public void initialize () {

		JPanel globalPanel = new JPanel ();
		globalPanel.setLayout(new BorderLayout());


		JPanel bottomPanel = new JPanel();
		JPanel topPanel = new JPanel ();
		
		
		this.textfield = new JTextField ("Enter your name");
		this.textfield.setPreferredSize(new Dimension(100,25));
		this.textfield.setMinimumSize(new Dimension(100,25));
		this.textfield.setMaximumSize(new Dimension(100,25));

		this.textfield.addKeyListener(new KeyListener (){
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					VictoryView.this.saveResult();
					}				
			}
			public void keyPressed(KeyEvent arg0) {
			}
			public void keyReleased(KeyEvent arg0) {
			}
		});

		
		HighscoreManager manager = new HighscoreManager (this.difficulty);
		int rank = manager.ranksAmongstTop(this.chrono);
		centerPanel = HighscoreView.highscorePanel(manager.getHighscoreString(), this.difficulty, 
				rank, this.chrono, this.textfield);
		

		JTextPane text = new JTextPane();
		text.setContentType("text/html");
		text.setText("<html><br><b><em><font size=\"7\" color=\"red\">VICTORY !</font></em></b><br><br>" +
				"Your score : <b>" + this.chrono +" s</b><br><br>Your difficulty : <b>" + this.difficulty +"</b><br><br></html>"); 
		text.setEditable(false); 
		text.setBackground(null); 
		text.setBorder(null);

		topPanel.add(text);


		JButton againButton = new JButton("Play Again");
		againButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) { 
				VictoryView.this.saveResult();
				((GameWindow)VictoryView.this.getParent()).reset();
				setVisible(false);
			}
		});


		JButton cancelBouton = new JButton("Cancel");
		cancelBouton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				VictoryView.this.saveResult();
				setVisible(false);
			}      
		});
		
		bottomPanel.add(cancelBouton);
		bottomPanel.add(againButton);

		globalPanel.add(topPanel, BorderLayout.NORTH);
		globalPanel.add(centerPanel, BorderLayout.CENTER);
		globalPanel.add(bottomPanel, BorderLayout.SOUTH);
		this.setContentPane(globalPanel);
	}
	
	public void saveResult () {
		if (!this.saved) {
			HighscoreManager manager = new HighscoreManager (this.difficulty);
			manager.addScore(this.textfield.getText(), this.chrono);
			this.saved=true;
		}
	}

}
