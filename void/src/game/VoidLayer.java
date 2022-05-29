package game;

import base.*;
import javafx.scene.layout.*;
import utils.fx.*;

final class VoidLayer extends Pane implements Updatable {

	private static final double ROTATIONAL_VELOCITY = 6; //degrees per second
	
	private static final VoidLayer INSTANCE = new VoidLayer();

	static VoidLayer get() {
		return INSTANCE;
	}
	
	private final ResizableImage rimage;
	
	private VoidLayer() {
		rimage = new ResizableImage(Images.THE_VOID);
		Nodes.setLayout(rimage,
			VoidScene.CENTER_X - .5 * Images.THE_VOID.getWidth(),
			VoidScene.CENTER_Y - .5 * Images.THE_VOID.getHeight()
		);
		getChildren().add(rimage);
		setMouseTransparent(true);
	}

	@Override
	public void update(long diff) {
		double sec = diff * 1e-9;
		rimage.setRotate(rimage.getRotate() + sec * ROTATIONAL_VELOCITY);
	}
	
}
