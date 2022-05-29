package game.feeders;

import game.GameLayer;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import utils.fx.ResizableImage;

public abstract sealed class AbstractFeeder extends Pane permits Feeder, UninitiatedFeeder {
	
	private static final double SIDE_SPACING = 6;
	
	protected final ResizableImage rimage;
	
	AbstractFeeder(ResizableImage rimage) {
		this.rimage = rimage;
		addEventHandler(MouseEvent.MOUSE_CLICKED, me -> GameLayer.get().feederClicked(this, me));
	}
	
	abstract FeederTag tag();
	
	protected double width() {
		return rimage.getImage().getWidth();
	}
	
	protected double height() {
		return rimage.getImage().getHeight();
	}
	
	protected double topLeftX() {
		return tag().centerX() - width() * .5;
	}
	
	protected double topLeftY() {
		return tag().centerY() - height() * .5;
	}
	
	public abstract AbstractFeederPane pane();
	
	public void updateAndShowPane() {
		pane().update();
		pane().setLayoutX(topLeftX() + width() + SIDE_SPACING);
		pane().setLayoutY(topLeftY());
		pane().setVisible(true);
	}
	
	public void hidePane() {
		pane().setVisible(false);
	}
	
}
