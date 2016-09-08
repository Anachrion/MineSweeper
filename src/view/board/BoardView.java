package view.board;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import listeners.SquareListener;
import controller.AbstractController;

@SuppressWarnings("serial")
public class BoardView extends JPanel implements SquareListener {

	private int length;
	private int width;
	private SquareView[][] board;
	private AbstractController controller;


	public BoardView (int x, int y, AbstractController controller){
		this.controller=controller;
		set(x,y);
		this.setVisible(true);
	}

	public void set (int x, int y) {

		this.length = x;
		this.width = y;
		this.removeAll();	

		this.setLayout(new GridLayout(this.width, this.length));			
		this.board = new SquareView[this.length][this.width];

		for (int j=0; j<this.width; j++) {
			for (int i=0; i<this.length; i++) {
				SquareView cell = null;
				cell = new SquareView (i, j);
				cell.addMouseListener(new MouseListener(){

					public void mouseClicked(MouseEvent e) {
						int button = 0;
						if (e.getButton()== MouseEvent.BUTTON1) {
							button = 1;
							if (e.getClickCount() == 2) {
								button = 11;
							}
						}
						else if (e.getButton()== MouseEvent.BUTTON3) {
							button = 2;
						}
						BoardView.this.controller.updateModel ( ((SquareView)e.getSource()).getx(), 
								((SquareView)e.getSource()).gety(), button);
					}

					public void mousePressed(MouseEvent e) {						
					}
					public void mouseReleased(MouseEvent e) {						
					}
					public void mouseEntered(MouseEvent e) {						
					}
					public void mouseExited(MouseEvent e) {						
					}

				});
				this.add(cell);
				this.board[i][j]=cell;
			}

		}
		this.revalidate();
		this.repaint();
		this.setSize(500, 500);
		this.setVisible(true);

	}

	
	public void reset () {
		for (int j=0; j<this.width; j++) {
			for (int i=0; i<this.length; i++) {
				this.board[i][j].update(false, 0);			
			}
		}	
	}

	
	@Override
	public void updateSquare(int x, int y, int status, boolean revealed) {
		this.board[x][y].update(revealed, status);			
	}

}