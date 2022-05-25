package game;

import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import utils.fx.*;

final class DebugMenu extends VBox {

	private static class DebugItem extends HBox {
		
		final Label label;
		final ResizableImage rimage;
		
		DebugItem(String text) {
			label = new Label(text);
			rimage = new ResizableImage(Images.DEBUG_RIGHT_ARROW);
//			HBox.setHgrow(label, Priority.ALWAYS);
			getChildren().addAll(label, rimage);
		}
		
	}
	
	private static final DebugMenu INSTANCE = new DebugMenu();
	
	static DebugMenu get() {
		return INSTANCE;
	}
	
	private DebugMenu() {
		getChildren().add(new DebugItem("Bots"));
		setVisible(false);
	}
	
	/** Assumes this is a correct {@link MouseEvent} (i.e. a right click). */
	void display(MouseEvent me) {
		Nodes.setLayout(this, me.getX(), me.getY());
		setVisible(true);
	}
	
}
