package game;

import base.*;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import utils.fx.Nodes;

public final class GamePane extends Pane implements UpdatablePane {

	private static final GamePane INSTANCE = new GamePane();
	
	public static GamePane get() {
		return INSTANCE;
	}
	
	private GamePane() {
		Nodes.setPrefSize(this, VoidScene.WIDTH, VoidScene.HEIGHT);
		addEventHandler(MouseEvent.MOUSE_CLICKED, me -> {
			if(me.getButton() == MouseButton.PRIMARY) {
				Circle ball = new Circle(8);
				ball.setLayoutX(me.getX());
				ball.setLayoutY(me.getY());
				getChildren().add(ball);
			}
		});
	}
	
}
