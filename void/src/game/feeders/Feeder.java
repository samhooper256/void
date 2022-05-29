package game.feeders;

import base.Updatable;
import game.*;
import javafx.scene.input.*;
import utils.fx.*;

public final class Feeder extends AbstractFeeder implements Updatable {

	private static final double SIDE_SPACING = 6;
	
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
		addEventHandler(MouseEvent.MOUSE_CLICKED, me -> GameLayer.get().feederClicked(this, me));
	}
	
	public void hidePane() {
		pane().setVisible(false);
	}
	
	public void updateAndShowPane() {
		pane().update();
		pane().setLayoutX(topLeftX() + width() + SIDE_SPACING);
		pane().setLayoutY(topLeftY());
		pane().setVisible(true);
	}
	
	@Override
	FeederTag tag() {
		return data.tag();
	}
	
	public FeederData data() {
		return data;
	}

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