package view.components;



import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import listeners.CountdownListener;



@SuppressWarnings("serial")
/** Display the number of non-flagged mines left **/
public class MineCountdownView extends JPanel  implements CountdownListener {

	private DigitView digit1, digit2;

	public MineCountdownView () {
		super ();
		this.digit1 = new DigitView();
		this.digit2 = new DigitView();

		this.setBorder(BorderFactory.createSoftBevelBorder(BevelBorder.RAISED, new Color (240,240,240), new Color (70,70,70)));
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);



		layout.setHorizontalGroup( layout.createSequentialGroup()
				.addComponent(this.digit2)
				.addComponent(this.digit1));

		layout.setVerticalGroup( layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(this.digit2)
						.addComponent(this.digit1))
				);

		this.setPreferredSize(new Dimension(40,32));
		this.setMaximumSize(new Dimension(40,32));
		this.setMinimumSize(new Dimension(40,32));

		this.setOpaque(false);
		this.setVisible(true);

	}

	@Override
	public void updateCountdown(int cd) {
		int tens, singleNumber;

		if (cd<0){
			tens = 0;
			singleNumber=0;
		}
		else if (cd>100) {
			tens = 9;
			singleNumber=9;
		}
		else {
			tens = cd/10;
			singleNumber = cd%10;
		}
		
		digit2.update(tens);
		digit1.update(singleNumber);
	}

}
