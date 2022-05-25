package game;

import base.*;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import utils.fx.Nodes;

public final class GamePane extends Pane implements UpdatablePane {

	private static final GamePane INSTANCE = new GamePane();
	
	public static GamePane get() {
		return INSTANCE;
	}
	
	private final Pane debugLayer;
	
	private GamePane() {
		debugLayer = new Pane(DebugMenu.get());
		Nodes.setPrefSize(this, VoidScene.WIDTH, VoidScene.HEIGHT);
		addEventHandler(MouseEvent.MOUSE_CLICKED, me -> {
			if(me.getButton() == MouseButton.SECONDARY)
				DebugMenu.get().display(me);
		});
		getChildren().addAll(GameLayer.get(), debugLayer);
		debugLayer.setPickOnBounds(false);
	}
	
	public void setupSave(Save save) {
		GameLayer.get().setupSave(save);
	}
	
}
