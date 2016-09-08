package view.popUpWindows;

import highscores.HighscoreManager;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/** High Score popup window**/
@SuppressWarnings("serial")
public class HighscoreView extends JDialog {

	private String difficulty;
	
	public HighscoreView(JFrame parent, boolean modal, String difficulty){

		super(parent, "High Score", modal);
		this.difficulty = difficulty;
		
		initialize ();
		
		this.setSize(220, 250);
		this.setLocationRelativeTo(parent);
		this.setVisible(true);
	}


	public void initialize () {
		JPanel globalPanel = new JPanel ();
		globalPanel.setLayout(new BorderLayout());

		JPanel bottomPanel = new JPanel();

		HighscoreManager hms = new HighscoreManager(this.difficulty);
		JPanel topPanel = highscorePanel(hms.getHighscoreString(), this.difficulty, -1, -1, null);
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) { 
				setVisible(false);
			}
		});
		
		bottomPanel.add(okButton);
		globalPanel.add(topPanel, BorderLayout.CENTER);
		globalPanel.add(bottomPanel, BorderLayout.SOUTH);
		this.setContentPane(globalPanel);
	}

	/** Static method returning a JPanel displaying the saved highscore (if any)
	 * if the integer variable "rank" ranks amongst the highscore, 
	 * then player's result (his time) and the provided textfield (asking for the player's name) are inserted
	 * **/
	public static JPanel highscorePanel (String[] hsresults, String difficulty, int rank, int time, JTextField tf) {

		int max = HighscoreManager.MAX_SAVED_SCORES;
		
		JPanel panel = new JPanel ();
		panel.setBorder(BorderFactory.createTitledBorder(" Difficulty Level : " +difficulty+" "));
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);

		JLabel[] ranks = new JLabel[max];
		JLabel[] times = new JLabel[max];
		JComponent[] names = new JComponent[max];

		for (int i = 0; i<max; i++) {
			ranks[i] = new JLabel (Integer.toString(i+1)+") ");
			times[i] = new JLabel (hsresults[i*2]+"s");
			names[i] = new JLabel (hsresults[(i*2)+1]);
		}
		
		if (rank>=0 && rank<=(max-1)) {
			for (int i=4; i > rank; i--) {
				times[i]=times[i-1];
				names[i]=names[i-1];
			}
			times[rank]= new JLabel (Integer.toString(time)+"s");
			names[rank]=tf;
		}

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup( layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
						.addComponent(ranks[0])
						.addComponent(ranks[1])
						.addComponent(ranks[2])
						.addComponent(ranks[3])
						.addComponent(ranks[4]))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
						.addComponent(times[0])
						.addComponent(times[1])
						.addComponent(times[2])
						.addComponent(times[3])
						.addComponent(times[4]))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(names[0])
						.addComponent(names[1])
						.addComponent(names[2])
						.addComponent(names[3])
						.addComponent(names[4]))
				);

		layout.setVerticalGroup( layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(ranks[0])
						.addComponent(times[0])
						.addComponent(names[0]))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(ranks[1])
						.addComponent(times[1])
						.addComponent(names[1]))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(ranks[2])
						.addComponent(times[2])
						.addComponent(names[2]))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(ranks[3])
						.addComponent(times[3])
						.addComponent(names[3]))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(ranks[4])
						.addComponent(times[4])
						.addComponent(names[4]))
				);


		return panel;
	}

}
