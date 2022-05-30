package game.upgrades;

import game.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import utils.fx.Backgrounds;

public final class UpgradeDisplay extends Pane {

	private static final String CSS = "upgrade-display", OUTER_HBOX_CSS = "outer-hbox", INNER_HBOX = "inner-hbox";

	private final HBox outerHBox, innerHBox;
	private final VBox vBox;
	private final Button buy;
	private final Label name, cost, description;
	private final Upgrade upgrade;
	
	public UpgradeDisplay(Upgrade upgrade) {
		this.upgrade = upgrade;
		buy = new Button("Buy");
		buy.setFocusTraversable(false);
		buy.setOnAction(ae -> {
			if(canPurchase())
				GameLayer.get().buyUpgrade(this);
		});
		name = new Label(upgrade.displayName());
		cost = new Label(); //update will get called before the user ever sees this UpgradeDisplay, filling this in.
		innerHBox = new HBox(name, cost);
		innerHBox.getStyleClass().add(INNER_HBOX);
		description = new Label(upgrade.description());
		vBox = new VBox(innerHBox, description);
		outerHBox = new HBox(buy, vBox);
		outerHBox.getStyleClass().add(OUTER_HBOX_CSS);
		getChildren().add(outerHBox);
		getStyleClass().add(CSS);
		setBackground(Backgrounds.of(Color.ORANGE));
	}

	public void update() {
		buy.setDisable(!canPurchase());
		cost.setText("(" + Formatter.format(Hub.save().ascension().trueCostOf(upgrade())) +")");
	}

	private boolean canPurchase() {
		return Hub.save().ascension().canPurchase(upgrade());
	}
	
	public Upgrade upgrade() {
		return upgrade;
	}

}
