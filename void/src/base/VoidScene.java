package base;

import javafx.scene.*;

/** The single {@link Scene} of the {@value Void#TITLE}. */
public final class VoidScene extends Scene implements Updatable {

	public static final double WIDTH = 1920, HEIGHT = 1080, CENTER_X = WIDTH * .5, CENTER_Y = HEIGHT * .5;
	
	private static final VoidScene INSTANCE = new VoidScene();
	
	public static VoidScene get() {
		return INSTANCE;
	}
	
	private VoidScene() {
		super(Root.get(), WIDTH * .5, HEIGHT * .5);
		getStylesheets().add(Main.class.getResource(Main.RESOURCES_PREFIX + "style.css").toExternalForm());
	}

	@Override
	public void update(long diff) {
		Root.get().update(diff);
	}
	
}
