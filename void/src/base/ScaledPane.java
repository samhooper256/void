package base;

import java.util.Collections;

import game.*;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import utils.fx.*;

public final class ScaledPane extends Pane implements UpdatablePane {

	private static final ScaledPane INSTANCE = new ScaledPane();
	
	public static ScaledPane get() {
		return INSTANCE;
	}
	
	private final Scale scale;
	private final ChangeListener<? super Number> parentSizeListener;
	
	private ScaledPane() {
		scale = new Scale();
		parentSizeListener = (_1, _2, _3) -> {
			if(getParent() instanceof Region r) {
				double width = r.getWidth(), height = r.getHeight();
				scale.setX(width / VoidScene.WIDTH);
				scale.setY(height / VoidScene.HEIGHT);
			}
		};
		getTransforms().add(scale);
		setBackground(Backgrounds.of(Color.PINK));
		Nodes.setPrefSize(this, VoidScene.WIDTH, VoidScene.HEIGHT);
	}
	
	void linkWithParent() {
		if(!(getParent() instanceof Region r))
			throw new IllegalStateException("Parent is not a Region");
		r.widthProperty().addListener(parentSizeListener);
		r.heightProperty().addListener(parentSizeListener);
	}
	
	public void showGame() {
		setChildren(GamePane.get());
		GamePane.get().setupSave(new Save()); //TODO move this elsewhere.
	}

	private void setChildren(Node... children) {
		getChildren().clear();
		Collections.addAll(getChildren(), children);
	}
	
}