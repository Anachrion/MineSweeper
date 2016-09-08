package listeners;

import java.util.EventListener;

public interface CountdownListener extends EventListener {
	
	public void updateCountdown (int cd);

}
