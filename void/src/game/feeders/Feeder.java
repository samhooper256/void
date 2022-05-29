package game.feeders;

import base.Updatable;
import game.*;
import utils.fx.*;

public final class Feeder extends AbstractFeeder implements Updatable {

	private final FeederData data;
	private final FeederPane pane;
	
	private long untilNext;
	
	public Feeder(FeederData data) {
		super(new ResizableImage(data.tag().image()));
		this.data = data;
		this.pane = new FeederPane(this);
		Nodes.setLayout(rimage, topLeftX(), topLeftY());
		getChildren().add(rimage);
		setPickOnBounds(false);
		untilNext = data.rate();
	}
	
	@Override
	FeederTag tag() {
		return data.tag();
	}
	
	public FeederData data() {
		return data;
	}

	@Override
	public FeederPane pane() {
		return pane;
	}
	
	@Override
	public void update(long diff) {
		untilNext -= diff;
		while(untilNext <= 0) {
			spawnProjectile();
			untilNext += data().rate();
		}
	}

	private void spawnProjectile() {
		GameLayer.get().addProjectile(new Projectile(data().mu(), tag().projectileImage()), tag().centerX(), tag().centerY());
	}
	
}