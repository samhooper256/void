package game;

import base.UpdatablePane;
import javafx.scene.layout.Pane;

final class GameLayer extends Pane implements UpdatablePane {

	private static final GameLayer INSTANCE = new GameLayer();
	
	static GameLayer get() {
		return INSTANCE;
	}
	
}
