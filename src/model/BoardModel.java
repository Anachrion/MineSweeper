package model;

import java.util.Random;

import listeners.CountdownListener;
import listeners.GameEndedListener;
import listeners.SquareListener;


public class BoardModel extends AbstractModel {


	private SquareModel[][] board;
	private boolean boom;
	private boolean ended;
	private int width, length;
	private int mines, flags;

	public BoardModel(int x, int y, int mines){
		super ();
		setModel (x, y, mines);
	}

	@Override
	public void setModel (int x, int y, int mines) {
		this.boom = false;
		this.ended = false;
		this.length = x;
		this.width = y;
		this.flags = 0;
		this.mines = mines;
		this.board = new SquareModel[x][y];
		for (int i = 0; i<x; i++) {
			for (int j = 0; j<y; j++) {
				this.board[i][j]= new SquareModel ();
			}
		}
		randomMining();
	}

	public void resetModel() {
		this.setModel(this.length, this.width, this.mines);
	}

	public void flagSquare (int x, int y){
		if (this.ended || this.board[x][y].getRevealed()) {
			return;
		}

		this.board[x][y].setFlag(!this.board[x][y].getFlag());
		if (this.board[x][y].getFlag()) {
			this.flags++;
		}
		else {
			this.flags--;
		}
		fireSquareChanged(x, y, surroundings(x,y), this.board[x][y].getRevealed());
		fireCountdownChanged();
	}

	/** Reveal this square and (eventually) all squares without any surrounding mines (recursive) **/
	public void revealSquare (int x, int y){
		if (this.ended || this.board[x][y].getRevealed() || this.board[x][y].getFlag()) {
			return;
		}

		this.board[x][y].setRevealed(true);

		if (this.board[x][y].getMine()) {
			fireSquareChanged(x, y, 11, true);
			this.boom = true;
			return;
		}

		int adjacent = surroundings(x,y);

		fireSquareChanged(x, y, adjacent, true);

		if (adjacent==0) {
			for(int i = x-1; i < x+2; i++){
				for(int j = y-1; j < y+2; j++){
					if(i >= 0 && i<this.length && j >= 0 && j<this.width){
						revealSquare(i,j);
					}
				}
			}
		}


	}

	/** Display the mines, the flagged mines and the empty flagged square icons at the end of the game **/
	public void revealBoard () {
		for(int i = 0; i<this.length; i++) {
			for(int j = 0; j<this.width; j++) {
				if (!this.board[i][j].getRevealed()) {
					if (this.board[i][j].getMine()) {
						if (!this.board[i][j].getFlag()) {
							fireSquareChanged(i, j, 9, false);
						}
						else {
							fireSquareChanged(i, j, 12, false);
						}
					}
					else {
						if (this.board[i][j].getFlag()) {
							fireSquareChanged(i, j, 13, false);
						}
					}
				}
			}
		}
	}

	public void randomMining(){
		int n = this.mines;
		if(n < this.length*this.width){
			while(n > 0){
				int i = BoardModel.randomInt(0, this.length-1);
				int j = BoardModel.randomInt(0, this.width-1);
				if(!this.board[i][j].getMine()){
					this.board[i][j].setMine(true);
					n --;
				}
			}
		}
		else{
			System.out.println("Not enough spare square to place all the mines.");
		}
	}



	/** Return the number of mines adjacent to the given square, 
	 * 9 if the square itself is a mine 
	 * 10 if it is flagged
	 **/
	public int surroundings (int x, int y){
		if (this.board[x][y].getFlag()){
			return 10;
		}


		if(this.board[x][y].getMine()){
			return 9;
		}

		int nb = 0;
		for(int i = x-1; i < x+2; i++){
			for(int j = y-1; j < y+2; j++){
				if(i >= 0 && i<this.length && j >= 0 && j<this.width && this.board[i][j].getMine()){
					nb += 1;
				}
			}
		}
		return nb;
	}

	/**  Print the board on the console : 1 for mined squares, else 0 **/
	public void printboard(){
		for(int j = 0; j<this.width; j++){
			for(int i = 0; i<this.length; i++){
				if (this.board[i][j].getMine()) {
					System.out.print(" 1 ");
				}
				else {
					System.out.print(" 0 ");
				}

			}
		}
	}

	public static int randomInt(int min, int max){
		Random rd = new Random();
		int random = rd.nextInt((max - min) + 1) + min;
		return random;
	}

	@Override
	/** Check if the game is over**/
	public void checkGameEnded() {
		if (this.ended){
			return;
		}

		if (this.boom) {
			this.fireGameEnded();
		}

		for(int i = 0; i<this.length; i++) {
			for(int j = 0; j<this.width; j++) {
				if (!this.board[i][j].getMine() && !this.board[i][j].getRevealed()) {
					return;
				}
			}
		}
		this.fireGameEnded();

	}

	/** If the player has flagged a number of adjacent squares accordingly to the number of adjacent mines, 
	 * he can double click on the square to reveal immediately all the non-flagged adjacent squares**/
	public void clearSurroundings (int x, int y) {
		if (this.board[x][y].getRevealed()) {
			int mines = 0;
			int flags = 0;

			for(int i = x-1; i < x+2; i++){
				for(int j = y-1; j < y+2; j++){
					if(i >= 0 && i<this.length && j >= 0 && j<this.width){
						if (this.board[i][j].getMine()) {
							mines++;
						}
						if (this.board[i][j].getFlag()) {
							flags++;
						}					
					}
				}
			}
			if (flags==mines){
				for(int i = x-1; i < x+2; i++){
					for(int j = y-1; j < y+2; j++){
						if(i >= 0 && i<this.length && j >= 0 && j<this.width){
							revealSquare(i, j);
						}
					}
				}
			}

		}
	}


	/** Update the listeners **/

	@Override
	public void fireCountdownChanged() {
		for(CountdownListener listener : getCountdownListener()) {
			listener.updateCountdown(this.mines-this.flags);
		}
	}

	@Override
	
	public void fireSquareChanged(int x, int y, int status, boolean revealed) {
		int st = status;
		if (!revealed) {
			if (!this.ended) {
				if (status!=10) {
					st=0;
				}
			}
			else {
				if (status<9) {
					st=0;	
				}
			}
		}
		for(SquareListener listener : getSquareListener()) {
			listener.updateSquare(x, y, st, revealed);
		}		
	}

	@Override
	public void fireGameEnded() {
		for(GameEndedListener listener : getGameEndedListener()) {
			if (this.boom) {
				listener.lost();
			}
			else {
				listener.won();
			}
		}	
		this.ended=true;
		revealBoard();
	}

	/** Listeners getters setters**/

	public CountdownListener[] getCountdownListener() {
		return listeners.getListeners(CountdownListener.class);
	}
	public void addCountdownListener(CountdownListener listener) {
		listeners.add(CountdownListener.class, listener);
	}
	public void removeCountdownListener(CountdownListener listener) {
		listeners.remove(CountdownListener.class, listener);
	}


	public GameEndedListener[] getGameEndedListener() {
		return listeners.getListeners(GameEndedListener.class);
	}
	public void addGameEndedListener(GameEndedListener listener) {
		listeners.add(GameEndedListener.class, listener);
	}
	public void removeGameEndedListener(GameEndedListener listener) {
		listeners.remove(GameEndedListener.class, listener);
	}


	public SquareListener[] getSquareListener() {
		return listeners.getListeners(SquareListener.class);
	}
	public void addSquareListener(SquareListener listener) {
		listeners.add(SquareListener.class, listener);
	}
	public void removeSquareListener(SquareListener listener) {
		listeners.remove(SquareListener.class, listener);
	}


}
