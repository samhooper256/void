package game;

import java.util.*;

import base.*;
import game.feeders.*;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import utils.fx.*;

public final class GameLayer extends Pane implements UpdatablePane {

	private static final GameLayer INSTANCE = new GameLayer();
	
	public static GameLayer get() {
		return INSTANCE;
	}
	
	/** Removes happen before adds. */
	private final List<Node> eouRemoves, eouAdds;
	private final List<Runnable> eouActions;
	private final Label mu;
	
	private Save save;
	private Feeder selectedFeeder;
	
	private GameLayer() {
		Nodes.setPrefSize(this, VoidScene.WIDTH, VoidScene.HEIGHT);
		mu = new Label();
		mu.getStyleClass().add("mu");
		getChildren().add(Panes.vBoxBuilder(mu).styleClass("mu-box").allWidths(VoidScene.WIDTH).build());
		eouRemoves = new ArrayList<>();
		eouAdds = new ArrayList<>();
		eouActions = new ArrayList<>();
	}
	
	@Override
	public void update(long diff) {
		UpdatablePane.super.update(diff);
		getChildren().removeAll(eouRemoves);
		getChildren().addAll(eouAdds);
		for(Runnable eouAction : eouActions)
			eouAction.run();
		eouRemoves.clear();
		eouAdds.clear();
		eouActions.clear();
	}
	
	/** The {@link MouseEvent} is assumed to be {@link MouseEvent#MOUSE_CLICKED}. */
	public void feederClicked(Feeder feeder, MouseEvent me) {
		if(feeder == selectedFeeder) {
			feeder.hidePane();
			selectedFeeder = null;
		}
		else {
			if(selectedFeeder != null)
				selectedFeeder.hidePane();
			selectedFeeder = feeder;
			feeder.updateAndShowPane();
		}
	}
	
	/** Assumes the given {@link Projectile} has already been positioned properly. */
	public void addProjectile(Projectile projectile) {
		eouAdds.add(projectile);
	}
	
	/** Identical to {@link #addProjectile(Projectile)}, except it
	 * {@link Projectile#setCenter(double, double) sets the center} of the given {@link Projectile} first. */
	public void addProjectile(Projectile projectile, double centerX, double centerY) {
		projectile.setCenter(centerX, centerY);
		addProjectile(projectile);
	}

	/** Should only be called by {@link Projectile}. Used to notify this {@link GameLayer} that a projectile has reached
	 * the center. */
	void reachedCenter(Projectile p) {
		if(!getChildren().contains(p))
			throw new IllegalArgumentException("The given projectile is not on this GamePane");
		eouRemoves.add(p);
		save.ascension().gain(p.mu());
		updateMU();
	}
	
	public void setupSave(Save save) {
		this.save = save;
		updateMU();
		Ascension a = save.ascension();
		for(FeederData fd : a.getFeederData()) {
			Feeder feeder = new Feeder(fd);
			getChildren().addAll(feeder, feeder.pane());
		}
	}
	
	public void addEOUAction(Runnable action) {
		eouActions.add(action);
	}
	
	public Save save() {
		return save;
	}
	
	/** Causes the {@link FeederPane} of the selected {@link Feeder}, if any, to be updated. */
	public void updateMU() {
		mu.setText(Formatter.format(save.mu()));
		if(selectedFeeder != null)
			selectedFeeder.pane().update();
	}
	
}
