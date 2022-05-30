package game.abilities;

import base.VoidScene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import utils.fx.*;

public final class AbilityBar extends Pane {

	private static final String CSS = "ability-bar", NOT_UNLOCKED_YET_CSS = "not-unlocked-yet";
	private static final double HEIGHT = 120, Y = VoidScene.HEIGHT - HEIGHT;
	private static final AbilityBar INSTANCE = new AbilityBar();
	
	public static AbilityBar get() {
		return INSTANCE;
	}
	
	private final Label notUnlockedLabel;
	
	private AbilityBar() {
		Nodes.setAllSizes(this, VoidScene.WIDTH, HEIGHT);
		setLayoutY(Y);
		getStyleClass().add(CSS);
		notUnlockedLabel = Text.centerIn(new Label("Abilities have not been unlocked yet."), VoidScene.WIDTH, HEIGHT);
		notUnlockedLabel.getStyleClass().add(NOT_UNLOCKED_YET_CSS);
		getChildren().add(notUnlockedLabel);
	}
	
}
