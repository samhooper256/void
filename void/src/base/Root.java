package base;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import utils.fx.Backgrounds;

/** The {@link Scene#getRoot() root} of the {@link VoidScene}. */
final class Root extends Pane implements UpdatablePane {

	private static final Root INSTANCE = new Root();
	
	static Root get() {
		return INSTANCE;
	}
	
	private Root() {
		getChildren().add(ScaledPane.get());
		ScaledPane.get().linkWithParent();
		setBackground(Backgrounds.of(Color.rgb(0, 255, 0, 0.25)));
	}
	
}
