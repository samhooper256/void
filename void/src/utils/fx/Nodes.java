package utils.fx;

import java.util.Objects;

import javafx.scene.Node;
import javafx.scene.layout.Region;

public final class Nodes {

	private Nodes() {
		
	}
	
	public static void setAllSizes(Region r, double width, double height) {
		setAllWidths(r, width);
		setAllHeights(r, height);
	}
	
	public static void setAllWidths(Region r, double width) {
		r.setMinWidth(width);
		r.setPrefWidth(width);
		r.setMaxWidth(width);
	}
	
	public static void setAllHeights(Region r, double height) {
		r.setMinHeight(height);
		r.setPrefHeight(height);
		r.setMaxHeight(height);
	}

	public static void setPrefSize(Region r, double width, double height) {
		r.setPrefWidth(width);
		r.setPrefHeight(height);
	}
	
	public static void setLayout(Node node, double x, double y) {
		node.setLayoutX(x);
		node.setLayoutY(y);
	}
	
	public static boolean isDescendantOf(Node descendant, Node ancestor) {
		Objects.requireNonNull(ancestor);
		while(descendant != null) {
			if(descendant.equals(ancestor))
				return true;
			descendant = descendant.getParent();
		}
		return false;
	}
	
}
