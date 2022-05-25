package game;

import base.*;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import utils.fx.Nodes;

final class DebugLayer extends Pane implements KeyListenerPane {

	private static final DebugLayer INSTANCE = new DebugLayer();
	
	static DebugLayer get() {
		return INSTANCE;
	}
	
	private DebugLayer() {
		Nodes.setPrefSize(this, VoidScene.WIDTH, VoidScene.HEIGHT);
		getChildren().add(DebugMenu.get());
		setPickOnBounds(false);
	}

	/** Allows the {@link DebugLayer} to inspect the given {@link MouseEvent}, which is targetted for the
	 * {@link GameLayer}, and possibly consume it. */
	void inspectEvent(MouseEvent me) {
		if(DebugMenu.get().isVisible() && me.getButton() == MouseButton.PRIMARY) {
			DebugMenu.get().setVisible(false);
			me.consume();
		}
		if(me.getButton() == MouseButton.SECONDARY) {
			DebugMenu.get().display(me);
			me.consume();
		}
	}
	
}
