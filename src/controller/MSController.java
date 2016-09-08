package controller;

import model.AbstractModel;

public class MSController extends AbstractController {

	public MSController (AbstractModel model) {
		this.model = model;
	}

	@Override
	public void updateModel(int x, int y, int button) {
		/* button values : 2 = right click / 1 = left click / 11 = double left click*/
		if (button == 2) {
			this.model.flagSquare(x, y);
		}
		else if (button == 1) {
			this.model.revealSquare(x, y);

		}
		else if (button == 11) {
			this.model.clearSurroundings(x, y);
		}
		
		this.model.checkGameEnded();
	}

	@Override
	public void setModel(int x, int y, int mines) {
		this.model.setModel(x, y, mines);
	}

	@Override
	public void resetModel() {
		this.model.resetModel();	
	}

}
