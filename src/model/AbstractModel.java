package model;

import javax.swing.event.EventListenerList;


public abstract class AbstractModel {

	
	
	/** Methods fired by the listeners **/
	
    protected  EventListenerList listeners = new EventListenerList();
    
    public abstract void fireCountdownChanged ();
    
    public abstract void fireSquareChanged (int x, int y, int status, boolean revealed);
    
    public abstract void fireGameEnded ();
    
    /** Methods used by the controller **/
    
	public abstract void revealSquare(int x, int y);

	public abstract void flagSquare(int x, int y);

	public abstract void checkGameEnded ();
	
	public abstract void clearSurroundings (int x, int y);
	
	public abstract void setModel (int x, int y, int mines);

	public abstract void resetModel();

}
