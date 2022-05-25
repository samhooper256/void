package game;

import base.*;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import utils.fx.Nodes;

public final class GamePane extends Pane implements UpdatablePane, KeyListener {

	private static final GamePane INSTANCE = new GamePane();
	
	public static GamePane get() {
		return INSTANCE;
	}
	
	private GamePane() {
		Nodes.setPrefSize(this, VoidScene.WIDTH, VoidScene.HEIGHT);
		addEventFilter(MouseEvent.MOUSE_CLICKED, me -> DebugLayer.get().inspectEvent(me));
		getChildren().addAll(GameLayer.get(), DebugLayer.get());
	}
	
	public void setupSave(Save save) {
		GameLayer.get().setupSave(save);
	}
	
	@Override
	public void handle(KeyEvent event) {
		System.out.printf("made it to GamePane: %s%n", event);
		if(DebugMenu.get().isVisible())
			DebugMenu.get().handle(event);
	}
	
}
