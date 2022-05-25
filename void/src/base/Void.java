package base;

import java.util.EnumSet;

import javafx.application.Application;
import javafx.scene.input.*;
import javafx.stage.Stage;

public final class Void extends Application {

	public static final String TITLE = "void";
	
	private static final EnumSet<KeyCode> KEYS_DOWN = EnumSet.noneOf(KeyCode.class);
	
	private static Stage stage;

	public static void startGame(String... args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		Void.stage = stage;
		stage.setScene(VoidScene.get());
		stage.setTitle(TITLE);
		ScaledPane.get().showGame();
		stage.show();
		stage.addEventFilter(KeyEvent.ANY, ke -> {
			if(ke.getEventType() == KeyEvent.KEY_PRESSED)
				KEYS_DOWN.add(ke.getCode());
			else if(ke.getEventType() == KeyEvent.KEY_RELEASED)
				KEYS_DOWN.remove(ke.getCode());
			VoidScene.get().handle(ke);
			ke.consume();
		});
		Timer.get().start();
	}

	/** Returns {@code null} if {@link #start(Stage)} has not been called. */
	public static Stage stage() {
		return stage;
	}
	
	static void update(long diff) {
		VoidScene.get().update(diff);
	}

	public static boolean isDown(KeyCode code) {
		return KEYS_DOWN.contains(code);
	}
	
}
