package view.popUpWindows;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import view.GameWindow;

/** Defeat popup window**/
@SuppressWarnings("serial")
public class DefeatView extends JDialog {
	
	public DefeatView(JFrame parent, boolean modal){
		super(parent, "You lost !", modal);

		init();

		this.setSize(220, 200);
		this.setLocationRelativeTo(parent);
		this.setVisible(true);
	}

	public void init () {

		JPanel globalPanel = new JPanel ();
		globalPanel.setLayout(new BorderLayout());


		JPanel bottomPanel = new JPanel();

		JPanel topPanel = new JPanel ();

		JTextPane text = new JTextPane();
		text.setContentType("text/html");
		text.setText("<html><br><b><em><font size=\"7\" color=\"blue\">DEFEAT !</font></em></b><br><br>" +
				"Would you like to try again ?<br><br></html>"); 
		text.setEditable(false); 
		text.setBackground(null); 
		text.setBorder(null);

		topPanel.add(text);

		JButton againButton = new JButton("Play Again");
		againButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) { 
				((GameWindow)DefeatView.this.getParent()).reset();
				setVisible(false);
			}
		});


		JButton cancelBouton = new JButton("Cancel");
		cancelBouton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}      
		});

		bottomPanel.add(cancelBouton);
		bottomPanel.add(againButton);


		globalPanel.add(topPanel, BorderLayout.CENTER);
		globalPanel.add(bottomPanel, BorderLayout.SOUTH);
		this.setContentPane(globalPanel);
	}

}
