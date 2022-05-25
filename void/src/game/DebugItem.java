package game;

import java.util.*;
import java.util.function.Consumer;

import javafx.scene.layout.Pane;

/** Each {@code DebugItem} is conceptually the root of a tree of items. The "descendants" of a certain item are all
 * items that can be made to appear through the item. The traversal methods and {@link #tree()}
 * traverse (list) items in no particular order. */
interface DebugItem {
	
	String CSS = "debug-item";

	default Pane asPane() {
		return (Pane) this;
	}
	
	/** Returns an unmodifiable {@link List} containing this {@link DebugItem} and all other items in the subtree
	 * rooted at this item. */
	default List<Pane> tree() {
		ArrayList<Pane> tree = new ArrayList<>();
		traverseTree(di -> tree.add(di.asPane()));
		return Collections.unmodifiableList(tree);
	}
	
	/** Traverses every {@link DebugItem} in the subtree rooted at this item, excluding this item. */
	void traverseChildren(Consumer<DebugItem> action);
	
	/** Traverses every {@link DebugItem} in the subtree rooted at this item, including this item. */
	default void traverseTree(Consumer<DebugItem> action) {
		action.accept(this);
		traverseChildren(action);
	}
	
}
