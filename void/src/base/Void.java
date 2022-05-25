package base;

import javafx.application.Application;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public final class Void extends Application {

	public static final String TITLE = "void";
	
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
	
}
