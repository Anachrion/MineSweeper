package listeners;

import java.util.EventListener;

public interface GameEndedListener extends EventListener {
	
	public void won ();
	
	public void lost ();

}
