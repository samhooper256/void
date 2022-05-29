package game.feeders;

import utils.fx.*;

public final class UninitiatedFeeder extends AbstractFeeder {

	private final FeederTag tag;
	
	public UninitiatedFeeder(FeederTag tag) {
		super(new ResizableImage(tag.uninitiatedImage()));
		this.tag = tag;
		Nodes.setLayout(rimage, topLeftX(), topLeftY());
		getChildren().add(rimage);
		setPickOnBounds(false);
	}
	
	@Override
	public FeederTag tag() {
		return tag;
	}
	
}
