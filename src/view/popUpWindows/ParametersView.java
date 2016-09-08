package view.popUpWindows;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import view.GameWindow;

/** Allow the user to customise the game **/
@SuppressWarnings("serial")
public class ParametersView extends JDialog {

	private JTextField lengthTextField, widthTextField, minesTextField;

	public ParametersView(JFrame parent, boolean modal){
		super(parent, "Parameters", modal);

		initialize();
		this.setSize(220, 200);
		this.setLocationRelativeTo(parent);
		this.setVisible(true);
	}


	public void initialize () {

		JPanel globalPanel = new JPanel ();
		globalPanel.setLayout(new BorderLayout());


		JPanel bottomPanel = new JPanel();

		JPanel topPanel = new JPanel ();
		topPanel.setBorder(BorderFactory.createTitledBorder("Custom game :"));
		GroupLayout layout = new GroupLayout(topPanel);
		topPanel.setLayout(layout);



		JLabel lengthLabel = new JLabel("Length (x) : ");
		this.lengthTextField = new JTextField("0");


		JLabel widthLabel = new JLabel("Width (y) : ");
		this.widthTextField = new JTextField("0");


		JLabel minesLabel = new JLabel("Number of mines : ");
		this.minesTextField = new JTextField("0");


		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) { 
				ParametersView.this.setParameters();
			}
		});


		JButton cancelBouton = new JButton("Cancel");
		cancelBouton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}      
		});

		
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup( layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
						.addComponent(lengthLabel)
						.addComponent(widthLabel)
						.addComponent(minesLabel))
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(lengthTextField, 50, 50, 50)
								.addComponent(widthTextField, 50, 50, 50)
								.addComponent(minesTextField, 50, 50, 50))
				);

		layout.setVerticalGroup( layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(lengthLabel)
						.addComponent(lengthTextField))
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(widthLabel)
								.addComponent(widthTextField))
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(minesLabel)
								.addComponent(minesTextField))
				);


		bottomPanel.add(okButton);
		bottomPanel.add(cancelBouton);


		globalPanel.add(topPanel, BorderLayout.CENTER);
		globalPanel.add(bottomPanel, BorderLayout.SOUTH);
		this.setContentPane(globalPanel);
	}

	/** Check if the given parameters are correct and create a new custom game **/
	public void setParameters () {
		int x,y,mines;
		try {
			x = Integer.parseInt(this.lengthTextField.getText());
			y = Integer.parseInt(this.widthTextField.getText());
			mines = Integer.parseInt(this.minesTextField.getText());
		}
		catch (NumberFormatException e) {
		    JOptionPane.showMessageDialog(this, "Please enter only positive integer numbers !", "Warning", JOptionPane.WARNING_MESSAGE);
		    return;
		}
		if (x>0 && y>0 && mines>0) {
			((GameWindow)this.getParent()).setGame(x, y, mines);
			((GameWindow)this.getParent()).setDifficulty("Custom");
			this.setVisible(false);
		}
	}

}
