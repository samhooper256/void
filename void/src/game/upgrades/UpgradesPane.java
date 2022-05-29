package game.upgrades;

import base.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import utils.Interpolation;
import utils.fx.*;

public final class UpgradesPane extends Pane implements Updatable {

	private enum State {
		ENTERING, EXITING, ON, OFF;
	}
	
	static final double Y = 100, DEST_X = VoidScene.CENTER_X + 180;
	
	private static final double WIDTH = .5 * VoidScene.WIDTH, HEIGHT = VoidScene.HEIGHT - 2 * Y;
	private static final long TRANSITION_DURATION = (long) 1e9;
	private static final Interpolation.Interpolator INTERPOLATOR = Interpolation::square;
	private static final UpgradesPane INSTANCE = new UpgradesPane();
	
	public static UpgradesPane get() {
		return INSTANCE;
	}
	
	private long time;
	private State state;
	
	private UpgradesPane() {
		Nodes.setLayout(this, VoidScene.WIDTH, Y);
		Nodes.setAllSizes(this, WIDTH, HEIGHT);
		setBackground(Backgrounds.of(Color.BROWN));
		state = State.OFF;
	}

	@Override
	public void update(long diff) {
		if(state == State.ENTERING) {
			time = Math.min(time + diff, TRANSITION_DURATION);
			if(time == TRANSITION_DURATION) {
				time = 0;
				state = State.ON;
				setLayoutX(DEST_X);
				UpgradesTab.get().setLayoutX(UpgradesTab.DEST_X);
			}
			else {
				double frac = (double) time / TRANSITION_DURATION;
				setLayoutX(INTERPOLATOR.interpolate(VoidScene.WIDTH, DEST_X, frac));
				UpgradesTab.get().setLayoutX(INTERPOLATOR.interpolate(UpgradesTab.START_X, UpgradesTab.DEST_X, frac));
			}
		}
		else if(state == State.EXITING) {
			time = Math.min(time + diff, TRANSITION_DURATION);
			if(time == TRANSITION_DURATION) {
				time = 0;
				state = State.OFF;
				setLayoutX(VoidScene.WIDTH);
				UpgradesTab.get().setLayoutX(UpgradesTab.START_X);
			}
			else {
				double frac = (double) time / TRANSITION_DURATION;
				setLayoutX(INTERPOLATOR.interpolate(DEST_X, VoidScene.WIDTH, frac));
				UpgradesTab.get().setLayoutX(INTERPOLATOR.interpolate(UpgradesTab.DEST_X, UpgradesTab.START_X, frac));
			}
		}
	}
	
	public void startEntering() {
		state = State.ENTERING;
		time = 0;
		setLayoutX(VoidScene.WIDTH);
		UpgradesTab.get().setLayoutX(UpgradesTab.START_X);
	}
	
	public void startExiting() {
		state = State.EXITING;
		time = 0;
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
