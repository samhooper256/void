package game.upgrades;

import base.VoidScene;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import utils.fx.*;

public final class UpgradesTab extends Pane {

	private static final double WIDTH = 120, HEIGHT = 120,Y = UpgradesPane.Y;
	
	static final double START_X = VoidScene.WIDTH - WIDTH, DEST_X = UpgradesPane.DEST_X - WIDTH;

	static final String CSS = "upgrades-tab";
	private static final UpgradesTab INSTANCE = new UpgradesTab();
	
	public static UpgradesTab get() {
		return INSTANCE;
	}

	private final Label label;
	private final ResizableImage image;
	
	private UpgradesTab() {
		Nodes.setAllSizes(this, WIDTH, HEIGHT);
		Nodes.setLayout(this, START_X, Y);
		label = new Label("Upgrades");
		image = new ResizableImage(Images.UPGRADES_ARROW);
		getChildren().add(Panes.vBoxBuilder(label, image)
				.alignment(Pos.CENTER).fillWidth(false).allSizes(WIDTH, HEIGHT).build());
		getStyleClass().add(CSS);
		setBackground(Backgrounds.of(Color.ORANGE));
		addEventHandler(MouseEvent.MOUSE_CLICKED, me -> {
			if(me.getButton() == MouseButton.PRIMARY && !UpgradesPane.get().isInMotion()) {
				if(UpgradesPane.get().isFullyOn())
					UpgradesPane.get().startExiting();
				else
					UpgradesPane.get().startEntering();
			}
		});
	}
	
}
