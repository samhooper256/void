package game.feeders;

import javafx.scene.layout.Pane;
import utils.fx.ResizableImage;

abstract sealed class AbstractFeeder extends Pane permits Feeder, UninitiatedFeeder {

	protected final ResizableImage rimage;
	
	AbstractFeeder(ResizableImage rimage) {
		this.rimage = rimage;
	}
	
	abstract FeederTag tag();
	
	protected double width() {
		return tag().image().getWidth();
	}
	
	protected double height() {
		return tag().image().getHeight();
	}
	
	protected double topLeftX() {
		return tag().centerX() - width() * .5;
	}
	
	protected double topLeftY() {
		return tag().centerY() - height() * .5;
	}
	
}
