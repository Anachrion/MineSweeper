package view.popUpWindows;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;

@SuppressWarnings("serial")
public class AboutView extends JDialog {

	public AboutView(JFrame parent, boolean modal){
		super(parent, "About", modal);

		JPanel panel = new JPanel ();
		panel.setBorder(BorderFactory.createTitledBorder("Informations :"));

		JTextPane text = new JTextPane();
		text.setContentType("text/html");
		text.setText("<html>Contact : <a href=\"url\">https://github.com/Anachrion</a> " +
				" <br><br> Aurelien G. @2016</html>"); 
		text.setEditable(false); 
		text.setBackground(null); 
		text.setBorder(null);
		
		panel.add(text);
		this.setContentPane(panel);
		this.setSize(300, 300);
		this.setLocationRelativeTo(parent);
		this.setVisible(true);
	}

}
