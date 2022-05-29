package game.feeders;

import java.math.BigInteger;

import game.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import utils.fx.Backgrounds;

/** Invisible by default. */
public final class FeederPane extends AbstractFeederPane {
	
	private class Level1 extends SimpleFeederPaneButton {
		
		Level1() {
			super("Level Up");
			setOnAction(ae -> {
				GameLayer.get().addEOUAction(() -> {
					feeder.data().levelUp();
					GameLayer.get().updateMU();
				});
			});
		}
		
		@Override
		BigInteger getCost() {
			return feeder().data().nextCost();
		}
		
	}
	
	private class Level10 extends SimpleFeederPaneButton {
		
		Level10() {
			super("Level 10x");
			setOnAction(ae -> {
				GameLayer.get().addEOUAction(() -> {
					feeder.data().levelUp10();
					GameLayer.get().updateMU();
				});
			});
		}
		
		@Override
		BigInteger getCost() {
			return feeder().data().next10Cost();
		}
		
	}

	private static final String LEVEL_BOX_CSS = "level-box", LEVEL_BOX_LAYER_1 = "layer-1";
	
	private final Feeder feeder;
	private final VBox levelBox;
	private final HBox levelBoxLayer1;
	private final Label nameAndLevel, mups, perThrow, delay;
	private final SimpleFeederPaneButton level1, level10;
	
	public FeederPane(Feeder feeder) {
		this.feeder = feeder;
		nameAndLevel = new Label(feeder.tag().displayName() + ", Level " + feeder.data().level());
		mups = new Label();
		perThrow = new Label();
		delay = new Label();
		titleBar.getChildren().addAll(nameAndLevel, mups);
		level1 = new Level1();
		level10 = new Level10();
		levelBoxLayer1 = new HBox(level1, level10);
		levelBoxLayer1.getStyleClass().add(LEVEL_BOX_LAYER_1);
		levelBox = new VBox(levelBoxLayer1);
		levelBox.getStyleClass().add(LEVEL_BOX_CSS);
		update();
		vBox.getChildren().addAll(perThrow, delay, levelBox);
		setBackground(Backgrounds.of(Color.gray(.75)));
	}
	
	Feeder feeder() {
		return feeder;
	}
	
	@Override
	public void update() {
		updateLevel();
		updatemups();
		updatePerThrow();
		updateDelay();
		level1.update();
		level10.update();
	}
	
	private void updateLevel() {
		String text = nameAndLevel.getText();
		int space = text.lastIndexOf(' ');
		nameAndLevel.setText(text.substring(0, space + 1) + feeder.data().level());
	}

	private void updatemups() {
		mups.setText("(" + Formatter.format(feeder().data().mupsRounded()) + " MUPS)");
	}
	
	private void updatePerThrow() {
		perThrow.setText("Per Throw: " + Formatter.format(feeder().data().mu()) + " MU");
	}
	
	private void updateDelay() {
		delay.setText(String.format("Delay: %.2f seconds", feeder().data().rate() * 1e-9));
	}
	
}
