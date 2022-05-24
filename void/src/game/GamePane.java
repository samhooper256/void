package game;

import java.math.BigInteger;

import base.*;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import utils.fx.Nodes;

public final class GamePane extends Pane implements UpdatablePane {

	private static final GamePane INSTANCE = new GamePane();
	
	public static GamePane get() {
		return INSTANCE;
	}
	
	private final Label mu;
	
	private GamePane() {
		Nodes.setPrefSize(this, VoidScene.WIDTH, VoidScene.HEIGHT);
//		addEventHandler(MouseEvent.MOUSE_CLICKED, me -> {
//			if(me.getButton() == MouseButton.PRIMARY) {
//				Circle ball = new Circle(8);
//				ball.setLayoutX(me.getX());
//				ball.setLayoutY(me.getY());
//				getChildren().add(ball);
//			}
//		});
		mu = new Label();
		mu.getStyleClass().add("mu");
		getChildren().add(mu);
	}

	public void setupSave(Save save) {
		if(save.isInAscension()) {
			setMU(save.mu());
		}
		else {
			throw new UnsupportedOperationException("Unfinished"); //TODO
		}
	}

	private void setMU(BigInteger mu) {
		this.mu.setText(Formatter.format(mu));
	}
	
}
