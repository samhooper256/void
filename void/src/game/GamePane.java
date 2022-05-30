package game;

import base.*;
import javafx.scene.Node;
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
		addEventFilter(MouseEvent.MOUSE_CLICKED, me -> {
			if(me.getTarget() instanceof Node n && !Nodes.isDescendantOf(n, DebugLayer.get()))
				DebugLayer.get().inspectEventTargettedToGameLayer(me);
		});
		getChildren().addAll(GameLayer.get(), DebugLayer.get());
	}
	
	public void setupSave(Save save) {
		GameLayer.get().setupSave(save);
	}
	
	@Override
	public void handle(KeyEvent event) {
		if(DebugMenu.get().isVisible())
			DebugMenu.get().handle(event);
	}
	
}
