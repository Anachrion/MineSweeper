package view.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;

/** Display a small timer ranging from 0 to 999 **/
@SuppressWarnings("serial")
public class TimerView extends JPanel {

	private int count;
	private Timer timer;
	private DigitView digit1, digit2, digit3;

	public TimerView () {
		super();	
		this.digit1 = new DigitView();
		this.digit2 = new DigitView();
		this.digit3 = new DigitView();


		this.setBorder(BorderFactory.createSoftBevelBorder(BevelBorder.RAISED, new Color (240,240,240), new Color (70,70,70)));
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);


		layout.setHorizontalGroup( layout.createSequentialGroup()
				.addComponent(this.digit3)
				.addComponent(this.digit2)
				.addComponent(this.digit1));

		layout.setVerticalGroup( layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(this.digit3)
						.addComponent(this.digit2)
						.addComponent(this.digit1))
				);

		this.setPreferredSize(new Dimension(60,32));
		this.setMaximumSize(new Dimension(60,32));
		this.setMinimumSize(new Dimension(60,32));

		this.setOpaque(false);
		this.setVisible(true);

		this.timer = new Timer (1000, secondsListener);
		this.start();
	}

	public void start () {
		this.count=0;
		this.timer.start();
	}

	public void stop () {
		this.timer.stop();
	}

	public int getCount () {
		return this.count;
	}


	public void updateDigits () {
		int hundreds, tens, singleNumber;
		hundreds=this.count/100;
		tens = (this.count%100)/10;
		singleNumber = this.count%10;

		digit3.update(hundreds);
		digit2.update(tens);
		digit1.update(singleNumber);
	}

	ActionListener secondsListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			TimerView.this.count++; 
			if (TimerView.this.count == 999) {
				TimerView.this.stop();
			}
			TimerView.this.updateDigits();
		}
	};


}
