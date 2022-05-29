package game.feeders;

import java.math.BigInteger;

import game.*;
import javafx.scene.control.Label;

public final class InitiationPane extends AbstractFeederPane {

	private class InitiateButton extends SimpleFeederPaneButton {
		
		InitiateButton() {
			super("Initiate");
			setOnAction(ae -> {
				GameLayer.get().addEOUAction(() -> {
					GameLayer.get().initiate(uFeeder);
				});
			});
		}
		
		@Override
		BigInteger getCost() {
			return initiationCost();
		}
		
	}
	
	private final UninitiatedFeeder uFeeder;
	private final InitiateButton button;
	
	public InitiationPane(UninitiatedFeeder uFeeder) {
		this.uFeeder = uFeeder;
		titleBar.getChildren().add(new Label("???"));
		button = new InitiateButton();
		update();
		vBox.getChildren().addAll(button);
	}

	@Override
	public void update() {
		button.update();
	}

	private BigInteger initiationCost() {
		return Hub.ascension().initiationCost(uFeeder.tag());
	}
	
}
