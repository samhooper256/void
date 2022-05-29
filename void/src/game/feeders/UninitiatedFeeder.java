package game.feeders;

import utils.fx.*;

public final class UninitiatedFeeder extends AbstractFeeder {

	private final FeederTag tag;
	private final InitiationPane pane;
	
	public UninitiatedFeeder(FeederTag tag) {
		super(new ResizableImage(tag.uninitiatedImage()));
		this.tag = tag;
		Nodes.setLayout(rimage, topLeftX(), topLeftY());
		getChildren().add(rimage);
		pane = new InitiationPane(this);
		setPickOnBounds(false);
	}
	
	@Override
	public FeederTag tag() {
		return tag;
	}

	@Override
	public InitiationPane pane() {
		return pane;
	}
	
}
