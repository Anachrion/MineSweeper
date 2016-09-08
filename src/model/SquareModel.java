package model;

public class SquareModel {

	private boolean revealed, mine, flag;
	
	public SquareModel () {
		this.mine = false;
		this.revealed = false;
		this.flag = false;
	}
	
	public boolean getRevealed () {
		return this.revealed;
	}
	
	public void setRevealed (boolean revealed) {
		this.revealed=revealed;
	}
	
	public boolean getMine () {
		return this.mine;
	}
	
	public void setMine (boolean mine) {
		this.mine=mine;
	}
	
	public boolean getFlag () {
		return this.flag;
	}
	
	public void setFlag (boolean flag) {
		this.flag=flag;
	}
	
	
}
