package game.feeders;

import base.Updatable;
import game.*;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import utils.fx.*;

public final class Feeder extends Pane implements Updatable {

	private static final double SIDE_SPACING = 6;
	
	private final FeederData data;
	private final FeederPane pane;
	
	private long untilNext;
	
	public Feeder(FeederData data) {
		this.data = data;
		this.pane = new FeederPane(this); //TODO add the pane to GameLayer
		ResizableImage rimage = new ResizableImage(tag().image());
		Nodes.setLayout(rimage, tag().x(), tag().y());
		getChildren().add(rimage);
		setPickOnBounds(false);
		untilNext = data.rate();
		addEventHandler(MouseEvent.MOUSE_CLICKED, me -> {
			if(me.getButton() == MouseButton.PRIMARY) {
				if(pane().isVisible()) {
					pane().setVisible(false);
				}
				else {
					pane().setLayoutX(tag().x() + width() + SIDE_SPACING);
					pane().setLayoutY(tag().y());
					pane().setVisible(true);
				}
			}
		});
	}

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
		GameLayer.get().addProjectile(new Projectile(data().mu(), tag().projectileImage()), centerX(), centerY());
	}
	
	private double width() {
		return tag().image().getWidth();
	}
	
	private double height() {
		return tag().image().getHeight();
	}
	
	private double centerX() {
		return tag().x() + width() * .5;
	}
	
	private double centerY() {
		return tag().y() + height() * .5;
	}
	
}