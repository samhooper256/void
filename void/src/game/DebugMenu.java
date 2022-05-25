package game;

import java.util.function.Consumer;

import base.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import utils.Parsing;
import utils.fx.*;

final class DebugMenu extends Pane implements KeyListenerPane {
	
	private static final class DebugVBox extends VBox implements DebugItem {
		
		private static final String CSS = "debug-vbox";
		private static final double MIN_WIDTH = 120;
		
		DebugVBox(DebugItem... items) {
			setMinWidth(MIN_WIDTH);
			getStyleClass().addAll(CSS, DebugItem.CSS);
			setFillWidth(true);
			for(DebugItem item : items)
				getChildren().add(item.asPane());
		}

		@Override
		public void traverseChildren(Consumer<DebugItem> action) {
			for(Node child : getChildren())
				if(child instanceof ParentItem pi)
					pi.traverseChildren(action);
		}
		
	}
	
	private abstract static sealed class SimpleDebugItem extends HBox implements DebugItem {
	
		private static final String CSS = "simple-debug-item";
		private static final double HEIGHT = 28;
		
		SimpleDebugItem(String text) {
			Pane labelWrap = new Pane(new Label(text));
			HBox.setHgrow(labelWrap, Priority.ALWAYS);
			setMinHeight(HEIGHT);
			getChildren().addAll(labelWrap);
			getStyleClass().addAll(CSS, DebugItem.CSS);
		}
		
	}
	
	private static final class LeafItem extends SimpleDebugItem implements DebugItem {

		LeafItem(String text) {
			super(text);
			addEventHandler(MouseEvent.MOUSE_ENTERED, me -> DebugMenu.get().hideChildrenOfTop());
		}

		@Override
		public void traverseChildren(Consumer<DebugItem> action) {
			//nothing - no children
		}
		
	}
	
	private static final class ParentItem extends SimpleDebugItem implements DebugItem, KeyListener {
		
		private final DebugItem child;
		
		ParentItem(String text, DebugItem child) {
			super(text);
			this.child = child;
			getChildren().add(new ResizableImage(Images.DEBUG_RIGHT_ARROW));
			addEventHandler(MouseEvent.MOUSE_ENTERED, me -> {
				DebugMenu.get().hideChildrenOfTop();
				Pane p = child.asPane();
				Nodes.setLayout(p, getLayoutX() + getWidth(), getLayoutY());
				p.setVisible(true);
			});
		}
		
		@Override
		public void traverseChildren(Consumer<DebugItem> action) {
			child.traverseTree(action);
		}

		@Override
		public void handle(KeyEvent event) {
			if(child instanceof KeyListener kl)
				kl.handle(event);
		}
		
	}
	
	private abstract static sealed class CustomDebugItem extends Pane implements DebugItem {
		
		CustomDebugItem() {
			getStyleClass().add(CSS);
		}
		
	}
	
	private static final class SpawnProjectile extends CustomDebugItem implements KeyListener {
		
		private static final double FIELD_WIDTH = 80;
		private static final String HBOX_CSS = "spawn-projectile-hbox";
		
		final HBox hBox;
		final Label muLabel;
		final ExplicitIntegerField field;
		final Button spawnButton;
		
		SpawnProjectile() {
			muLabel = new Label("MU:");
			field = new ExplicitIntegerField();
			field.setMaxWidth(FIELD_WIDTH);
			spawnButton = new Button("Spawn");
			spawnButton.setOnAction(ae -> spawnAction());
			hBox = new HBox(muLabel, field, spawnButton);
			hBox.getStyleClass().add(HBOX_CSS);
			hBox.setMinHeight(SimpleDebugItem.HEIGHT);
			getChildren().add(hBox);
		}

		@Override
		public void traverseChildren(Consumer<DebugItem> action) {
			// nothing
		}

		@Override
		public void handle(KeyEvent event) {
			field.handle(event);
		}
		
		private void spawnAction() {
			String text = field.getText();
			if(Parsing.isInteger(text)) {
				DebugMenu.get().setVisible(false);
				MouseEvent me = DebugMenu.get().cause;
				GameLayer.get().addProjectile(new Projectile(Parsing.parseInteger(text)), me.getX(), me.getY());
			}
		}
		
	}

	private static final String CSS = "debug-menu";
	private static final DebugMenu INSTANCE = new DebugMenu();
	
	static DebugMenu get() {
		return INSTANCE;
	}

	private final DebugVBox topItem;
	/** The most recent {@link MouseEvent} that caused the {@link DebugMenu} to be displayed. */
	private MouseEvent cause;
	
	private DebugMenu() {
		topItem = new DebugVBox(new ParentItem("Spawn Projectile", new SpawnProjectile()), new LeafItem("Leaf"));
		getStyleClass().add(CSS);
		getChildren().addAll(topItem.tree());
		setVisible(false);
	}
	
	/** Assumes this is a correct {@link MouseEvent} (i.e. a right click). */
	void display(MouseEvent me) {
		Nodes.setLayout(this, me.getX(), me.getY());
		this.cause = me;
		hideChildrenOfTop();
		setVisible(true);
	}

	private void hideChildrenOfTop() {
		topItem.traverseChildren(di -> di.asPane().setVisible(false));
	}
	
}
