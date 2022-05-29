package game.feeders;

import java.math.BigInteger;

import game.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public abstract sealed class AbstractFeederPane extends StackPane permits FeederPane, InitiationPane {

	protected abstract static class SimpleFeederPaneButton extends Button {
		
		private static final String CSS = "feeder-pane-button", GRAPHIC_CSS = "graphic", COST_CSS = "cost";
		
		final VBox graphic;
		final Label cost;
		
		SimpleFeederPaneButton(String titleText) {
			Label title = new Label(titleText);
			cost = new Label();
			cost.getStyleClass().add(COST_CSS);
			graphic = new VBox(title, cost);
			graphic.getStyleClass().add(GRAPHIC_CSS);
			setGraphic(graphic);
			getStyleClass().add(CSS);
			updateCost();
			setFocusTraversable(false);
		}
		
		abstract BigInteger getCost();
		
		void updateCost() {
			cost.setText(Formatter.format(getCost()));
		}
		
		boolean isAffordable() {
			return Hub.canAfford(getCost());
		}
		
		void update() {
			updateCost();
			setDisable(!isAffordable());
		}
		
	}
	
	private static final String CSS = "feeder-pane", TITLE_BAR_CSS = "title-bar";
	
	protected final VBox vBox;
	protected final HBox titleBar;
	
	AbstractFeederPane() {
		getStyleClass().add(CSS);
		titleBar = new HBox();
		titleBar.getStyleClass().add(TITLE_BAR_CSS);
		vBox = new VBox(titleBar);
		getChildren().add(vBox);
		setVisible(false);
	}

	public abstract void update();
	
}
