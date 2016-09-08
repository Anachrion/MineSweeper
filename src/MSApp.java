import model.BoardModel;
import view.GameWindow;
import controller.MSController;


public class MSApp {

	public static void main(String[] args) {

		
		BoardModel model = new BoardModel (16, 16, 40);
		
		MSController controller = new MSController (model);
		
		GameWindow view = new GameWindow(16, 16, "Medium", controller);
		
		model.addCountdownListener(view.getCountdownView());
		model.addGameEndedListener(view);
		model.addSquareListener(view.getBoardView());

	}
}
