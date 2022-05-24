package base;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

/** Must only be implemented by classes that also subclass {@link Pane}. */
public interface UpdatablePane extends Updatable {

	@Override
	default void update(long diff) {
		for(Node child : ((Pane) this).getChildren())
			if(child instanceof Updatable u)
				u.update(diff);
	}
	
}
