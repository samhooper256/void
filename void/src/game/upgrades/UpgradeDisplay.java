package game.upgrades;

import game.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import utils.fx.*;

public final class UpgradeDisplay extends Pane {

	private static class RequirementLabel extends Label {
		
		private static final Color SATISFIED_FILL = Color.BLACK, UNSATISFIED_FILL = Color.RED;
		
		final UpgradeRequirement requirement;
		
		RequirementLabel(UpgradeRequirement requirement) {
			super(requirement.displayString());
			this.requirement = requirement;
		}

		void update() {
			if(Hub.save().ascension().meets(requirement))
				setTextFill(SATISFIED_FILL);
			else
				setTextFill(UNSATISFIED_FILL);
		}
		
	}
	
	private static final String CSS = "upgrade-display", OUTER_HBOX_CSS = "outer-hbox", INNER_HBOX = "inner-hbox";

	private final HBox outerHBox, innerHBox;
	private final VBox vBox, requirements;
	private final Button buy;
	private final Label name, cost, description;
	private final Upgrade upgrade;
	
	public UpgradeDisplay(Upgrade upgrade) {
		this.upgrade = upgrade;
		Nodes.setAllWidths(this, UpgradesPane.WIDTH - UpgradesPane.PADDING * 2); 
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
		requirements = new VBox();
		for(UpgradeRequirement ur : upgrade().requirements())
			requirements.getChildren().add(new RequirementLabel(ur));
		vBox = new VBox(innerHBox, description, requirements);
		outerHBox = new HBox(buy, vBox);
		outerHBox.getStyleClass().add(OUTER_HBOX_CSS);
		getChildren().add(outerHBox);
		getStyleClass().add(CSS);
	}

	public void update() {
		buy.setDisable(!canPurchase());
		cost.setText("(" + Formatter.format(Hub.save().ascension().trueCostOf(upgrade())) +")");
		for(Node n : requirements.getChildren())
			((RequirementLabel) n).update();
	}

	private boolean canPurchase() {
		return Hub.save().ascension().canPurchase(upgrade());
	}
	
	public Upgrade upgrade() {
		return upgrade;
	}

}
