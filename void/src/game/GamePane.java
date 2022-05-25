package game;

import java.math.BigInteger;
import java.util.*;

import base.*;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import utils.fx.Nodes;

public final class GamePane extends Pane implements UpdatablePane {

	private static final GamePane INSTANCE = new GamePane();
	
	public static GamePane get() {
		return INSTANCE;
	}
	
	private final Pane debugLayer;
	private final Label mu;
	private final List<Node> eouRemoves;
	
	private Save save;
	
	private GamePane() {
		debugLayer = new Pane(DebugMenu.get());
		Nodes.setPrefSize(this, VoidScene.WIDTH, VoidScene.HEIGHT);
		addEventHandler(MouseEvent.MOUSE_CLICKED, me -> {
			if(me.getButton() == MouseButton.PRIMARY) {
				Projectile ball = new Projectile(BigInteger.ONE);
				ball.setCenter(me.getX(), me.getY());
				GameLayer.get().getChildren().add(ball);
			}
			else if(me.getButton() == MouseButton.SECONDARY) {
				DebugMenu.get().display(me);
			}
		});
		eouRemoves = new ArrayList<>();
		mu = new Label();
		mu.getStyleClass().add("mu");
		GameLayer.get().getChildren().add(mu);
		getChildren().addAll(GameLayer.get(), debugLayer);
		debugLayer.setPickOnBounds(false);
	}
	
	@Override
	public void update(long diff) {
		UpdatablePane.super.update(diff);
		getChildren().removeAll(eouRemoves);
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
	
	void reachedCenter(Projectile p) {
		if(!getChildren().contains(p))
			throw new IllegalArgumentException("The given projectile is not on this GamePane");
		eouRemoves.add(p);
		save.ascension().gain(p.mu());
		updateMU();
	}
	
	private void updateMU() {
		mu.setText(Formatter.format(save.mu()));
	}
	
}
