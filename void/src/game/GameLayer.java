package game;

import java.math.BigInteger;
import java.util.*;

import base.*;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import utils.fx.Nodes;

final class GameLayer extends Pane implements UpdatablePane {

	private static final GameLayer INSTANCE = new GameLayer();
	
	static GameLayer get() {
		return INSTANCE;
	}
	
	private final List<Node> eouRemoves;
	private final Label mu;
	
	private Save save;
	
	private GameLayer() {
		Nodes.setPrefSize(this, VoidScene.WIDTH, VoidScene.HEIGHT);
		addEventHandler(MouseEvent.MOUSE_CLICKED, me -> {
			if(me.getButton() == MouseButton.PRIMARY) {
				Projectile ball = new Projectile(BigInteger.ONE);
				ball.setCenter(me.getX(), me.getY());
				getChildren().add(ball);
			}
		});
		mu = new Label();
		mu.getStyleClass().add("mu");
		getChildren().add(mu);
		eouRemoves = new ArrayList<>();
	}
	
	@Override
	public void update(long diff) {
		UpdatablePane.super.update(diff);
		getChildren().removeAll(eouRemoves);
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
		if(save.isInAscension()) {
			updateMU();
		}
		else {
			throw new UnsupportedOperationException("Unfinished"); //TODO
		}
	}
	
	private void updateMU() {
		mu.setText(Formatter.format(save.mu()));
	}
	
}
