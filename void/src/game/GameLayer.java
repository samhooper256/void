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
	private static final double MU_TEXT_Y = 80;
	
	public static GameLayer get() {
		return INSTANCE;
	}
	
	/** Removes happen before adds. */
	private final List<Node> eouRemoves, eouAdds;
	private final List<Runnable> eouActions;
	private final Label muDisplay, muText;
	
	private Save save;
	private AbstractFeeder selectedFeeder;
	
	private GameLayer() {
		Nodes.setPrefSize(this, VoidScene.WIDTH, VoidScene.HEIGHT);
		muDisplay = Text.styledLabel("mu-display");
		muText = Text.newLabel("Meaning Units", "mu-text");
		muDisplay.layoutXProperty().bind(muDisplay.widthProperty().multiply(-.5).add(VoidScene.CENTER_X));
		muText.layoutXProperty().bind(muText.widthProperty().multiply(-.5).add(VoidScene.CENTER_X));
		muText.setLayoutY(MU_TEXT_Y);
		getChildren().addAll(VoidLayer.get(), muDisplay, muText);
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
	public void feederClicked(AbstractFeeder feeder, MouseEvent me) {
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
	
	public void initiate(UninitiatedFeeder uFeeder) {
		getChildren().removeAll(uFeeder, uFeeder.pane());
		FeederTag tag = uFeeder.tag();
		Hub.ascension().initiate(tag);
		Feeder feeder = new Feeder(Hub.ascension().getFeederData(tag));
		getChildren().addAll(feeder, feeder.pane());
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
		for(FeederTag tag : FeederTag.listValues()) {
			if(!save.ascension().hasFeeder(tag)) {
				UninitiatedFeeder uf = new UninitiatedFeeder(tag);
				getChildren().addAll(uf, uf.pane());
			}
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
		muDisplay.setText(Formatter.format(save.mu()));
		if(selectedFeeder != null)
			selectedFeeder.pane().update();
	}
	
}
