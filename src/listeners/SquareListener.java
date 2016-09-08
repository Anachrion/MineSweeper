package listeners;

import java.util.EventListener;

public interface SquareListener extends EventListener {
	
	public void updateSquare (int x, int y, int status, boolean revealed);

}
