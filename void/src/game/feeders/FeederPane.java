package game.feeders;

import game.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import utils.fx.Backgrounds;

/** Invisible by default. */
public final class FeederPane extends StackPane {

	private class Level1 extends Button {
		
		final VBox graphic;
		final Label cost;
		
		Level1() {
			Label title = new Label("Level Up");
			cost = new Label();
			updateCost();
			graphic = new VBox(title, cost);
			setGraphic(graphic);
			setOnAction(ae -> {
				GameLayer.get().addEOUAction(() -> {
					feeder.data().levelUp();
					GameLayer.get().updateMU();
				});
			});
		}
		
		void update() {
			updateCost();
			setDisable(!isAffordable());
		}
		
		private void updateCost() {
			cost.setText(feeder().data().nextCost().toString());
		}
		
		private boolean isAffordable() {
			return feeder().data().nextCost().compareTo(Hub.mu()) <= 0;
		}
		
	}
	
	private static final String CSS = "feeder-pane", NAME_AND_LEVEL_CSS = "name-and-level";
	
	private final Feeder feeder;
	private final VBox vBox;
	private final Label nameAndLevel, mups;
	private final Level1 level1;
	
	public FeederPane(Feeder feeder) {
		this.feeder = feeder;
		nameAndLevel = new Label(feeder.tag().displayName() + ", Level " + feeder.data().level());
		nameAndLevel.getStyleClass().add(NAME_AND_LEVEL_CSS);
		mups = new Label();
		updatemups();
		level1 = new Level1();
		vBox = new VBox(nameAndLevel, mups, level1);
		getChildren().add(vBox);
		getStyleClass().add(CSS);
		setVisible(false);
		setBackground(Backgrounds.of(Color.gray(.75)));
	}
	
	Feeder feeder() {
		return feeder;
	}
	
	public void update() {
		updateLevel();
		updatemups();
		level1.update();
	}
	
	private void updateLevel() {
		String text = nameAndLevel.getText();
		int space = text.lastIndexOf(' ');
		nameAndLevel.setText(text.substring(0, space + 1) + feeder.data().level());
	}

	private void updatemups() {
		mups.setText("MUPS: " + Formatter.format(feeder().data().mupsRounded()));
	}
	
}
