package controller;

import model.AbstractModel;

public abstract class AbstractController {

	protected AbstractModel model;
	
	
	public abstract void updateModel (int x, int y, int button);
	
	public abstract void setModel (int x, int y, int mines);
	
	public abstract void resetModel ();
}

