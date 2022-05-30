package game.upgrades;

import base.*;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import utils.Interpolation;
import utils.fx.*;

public final class UpgradesPane extends Pane implements Updatable {

	private enum State {
		ENTERING, EXITING, ON, OFF;
	}
	
	static final double Y = 100, DEST_X = VoidScene.CENTER_X + 180;
	
	private static final String CSS = "upgrades-pane", COLUMN_CSS = "upgrades-column"; //TODO upgrades-column is not used.
	static final double
		WIDTH = VoidScene.WIDTH - DEST_X,
		HEIGHT = VoidScene.HEIGHT - 2 * Y,
		PADDING = 20;
	private static final long TRANSITION_DURATION = (long) 1e9, UPDATE_COOLDOWN = (long) 1e9;
	private static final Interpolation.Interpolator INTERPOLATOR = Interpolation::square;
	private static final UpgradesPane INSTANCE = new UpgradesPane();
	
	public static UpgradesPane get() {
		return INSTANCE;
	}
	
	private long animationTimer, updateTimer;
	private State state;
	private VBox upgradeColumn;
	
	private UpgradesPane() {
		Nodes.setLayout(this, VoidScene.WIDTH, Y);
		Nodes.setAllSizes(this, WIDTH, HEIGHT);
		upgradeColumn = new VBox();
		upgradeColumn.getStyleClass().add(COLUMN_CSS);
		upgradeColumn.setPadding(new Insets(PADDING)); //not set in CSS because the value is needed elsewhere.
		for(Upgrade u : Upgrade.values())
			upgradeColumn.getChildren().add(new UpgradeDisplay(u));
		getChildren().add(upgradeColumn);
		getStyleClass().add(CSS);
		state = State.OFF;
	}

	@Override
	public void update(long diff) {
		if(state == State.ENTERING) {
			animationTimer = Math.min(animationTimer + diff, TRANSITION_DURATION);
			if(animationTimer == TRANSITION_DURATION) {
				animationTimer = 0;
				state = State.ON;
				setLayoutX(DEST_X);
				UpgradesTab.get().setLayoutX(UpgradesTab.DEST_X);
			}
			else {
				double frac = (double) animationTimer / TRANSITION_DURATION;
				setLayoutX(INTERPOLATOR.interpolate(VoidScene.WIDTH, DEST_X, frac));
				UpgradesTab.get().setLayoutX(INTERPOLATOR.interpolate(UpgradesTab.START_X, UpgradesTab.DEST_X, frac));
			}
		}
		else if(state == State.EXITING) {
			animationTimer = Math.min(animationTimer + diff, TRANSITION_DURATION);
			if(animationTimer == TRANSITION_DURATION) {
				animationTimer = 0;
				state = State.OFF;
				setLayoutX(VoidScene.WIDTH);
				UpgradesTab.get().setLayoutX(UpgradesTab.START_X);
			}
			else {
				double frac = (double) animationTimer / TRANSITION_DURATION;
				setLayoutX(INTERPOLATOR.interpolate(DEST_X, VoidScene.WIDTH, frac));
				UpgradesTab.get().setLayoutX(INTERPOLATOR.interpolate(UpgradesTab.DEST_X, UpgradesTab.START_X, frac));
			}
		}
		if(state == State.ENTERING || state == State.ON) {
			updateTimer += diff;
			if(updateTimer >= UPDATE_COOLDOWN)
				updateDisplays(); //sets updateTimer to 0
		}
	}
	
	private void updateDisplays() {
		for(Node n : upgradeColumn.getChildren())
			if(n instanceof UpgradeDisplay ud)
				ud.update();
		updateTimer = 0;
	}
	
	public void updateAndStartEntering() {
		updateDisplays();
		state = State.ENTERING;
		animationTimer = 0;
		setLayoutX(VoidScene.WIDTH);
		UpgradesTab.get().setLayoutX(UpgradesTab.START_X);
	}
	
	public void startExiting() {
		state = State.EXITING;
		animationTimer = 0;
		setLayoutX(DEST_X);
		UpgradesTab.get().setLayoutX(UpgradesTab.DEST_X);
	}
	
	public boolean isInMotion() {
		return state == State.ENTERING || state == State.EXITING;
	}
	
	public boolean isFullyOn() {
		return state == State.ON;
	}
	
}
