package game;

import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import utils.fx.*;

final class DebugMenu extends VBox {

	private static class DebugItem extends HBox {
		
		static final String CSS = "debug-item";
		
		DebugItem(String text) {
			Pane labelWrap = new Pane(new Label(text));
			HBox.setHgrow(labelWrap, Priority.ALWAYS);
			getStyleClass().add(CSS);
			getChildren().addAll(labelWrap, new ResizableImage(Images.DEBUG_RIGHT_ARROW));
		}
		
	}
	
	private static final double WIDTH = 100;
	private static final DebugMenu INSTANCE = new DebugMenu();
	
	static DebugMenu get() {
		return INSTANCE;
	}
	
	private DebugMenu() {
		Nodes.setAllWidths(this, WIDTH);
		setBackground(Backgrounds.of(Color.rgb(0, 0, 255, .25)));
		getChildren().add(new DebugItem("Bots"));
		setVisible(false);
	}
	
	/** Assumes this is a correct {@link MouseEvent} (i.e. a right click). */
	void display(MouseEvent me) {
		Nodes.setLayout(this, me.getX(), me.getY());
		setVisible(true);
	}
	
}
