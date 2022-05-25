package base;

import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

/** Must only be implemented by classes that also subclass {@link Pane}. */
public interface KeyListenerPane extends KeyListener {

	@Override
	default void handle(KeyEvent event) {
		for(Node child : ((Pane) this).getChildren())
			if(child instanceof KeyListener u)
				u.handle(event);
	}
	
}
