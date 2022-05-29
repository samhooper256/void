package game.upgrades;

import game.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import utils.fx.Backgrounds;

public final class UpgradeDisplay extends Pane {

	private static final String CSS = "upgrade-display", HBOX_CSS = "hbox";

	private final HBox hBox;
	private final Button buy;
	private final Label name, cost;
	private final Upgrade upgrade;
	
	public UpgradeDisplay(Upgrade upgrade) {
		this.upgrade = upgrade;
		buy = new Button("Buy");
		name = new Label(upgrade.displayName());
		cost = new Label(); //update will get called before the user ever sees this UpgradeDisplay, filling this in.
		hBox = new HBox(buy, name, cost);
		hBox.getStyleClass().add(HBOX_CSS);
		getChildren().add(hBox);
		getStyleClass().add(CSS);
		setBackground(Backgrounds.of(Color.ORANGE));
	}

	public void update() {
		buy.setDisable(!Hub.save().ascension().canPurchase(upgrade()));
		cost.setText("(" + Formatter.format(Hub.save().ascension().trueCostOf(upgrade())) +")");
	}
	
	public Upgrade upgrade() {
		return upgrade;
	}

}
