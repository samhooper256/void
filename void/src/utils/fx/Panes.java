package utils.fx;

import java.util.*;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public final class Panes {

	private Panes() {
		
	}
	
	public static final class VBoxBuilder {
		
		private final List<Node> children;
		
		private String styleClass;
		private double minWidth, prefWidth, maxWidth, minHeight, prefHeight, maxHeight;
		private Pos alignment;
		private boolean fillWidth;
		
		private VBoxBuilder(Node... children) {
			if(children.length == 0) {
				this.children = new ArrayList<>();
			}
			else {
				this.children = new ArrayList<>(children.length);
				Collections.addAll(this.children, children);
			}
			minWidth = prefWidth = maxWidth = minHeight = prefHeight = maxHeight = Double.NaN;
		}
		
		public VBoxBuilder styleClass(String styleClass) {
			this.styleClass = styleClass;
			return this;
		}
		
		public VBoxBuilder minWidth(double minWidth) {
			this.minWidth = minWidth;
			return this;
		}
		
		public VBoxBuilder prefWidth(double prefWidth) {
			this.prefWidth = prefWidth;
			return this;
		}
		
		public VBoxBuilder maxWidth(double maxWidth) {
			this.maxWidth = maxWidth;
			return this;
		}
		
		public VBoxBuilder allWidths(double width) {
			this.minWidth = width;
			this.prefWidth = width;
			this.maxWidth = width;
			return this;
		}
		
		public VBoxBuilder minHeight(double minHeight) {
			this.minHeight = minHeight;
			return this;
		}
		
		public VBoxBuilder prefHeight(double prefHeight) {
			this.prefHeight = prefHeight;
			return this;
		}
		
		public VBoxBuilder maxHeight(double maxHeight) {
			this.maxHeight = maxHeight;
			return this;
		}
		
		public VBoxBuilder allHeights(double height) {
			this.minHeight = height;
			this.prefHeight = height;
			this.maxHeight = height;
			return this;
		}
		
		public VBoxBuilder allSizes(double width, double height) {
			allWidths(width);
			return allHeights(height);
		}
		
		public VBoxBuilder alignment(Pos alignment) {
			this.alignment = alignment;
			return this;
		}
		
		public VBoxBuilder fillWidth(boolean fillWidth) {
			this.fillWidth = fillWidth;
			return this;
		}
		
		public VBox build() {
			VBox vBox = new VBox();
			vBox.getChildren().addAll(children);
			if(styleClass != null)
				vBox.getStyleClass().add(styleClass);
			if(!Double.isNaN(minWidth))
				vBox.setMinWidth(minWidth);
			if(!Double.isNaN(prefWidth))
				vBox.setPrefWidth(prefWidth);
			if(!Double.isNaN(maxWidth))
				vBox.setMaxWidth(maxWidth);
			if(!Double.isNaN(minHeight))
				vBox.setMinHeight(minHeight);
			if(!Double.isNaN(prefHeight))
				vBox.setPrefHeight(prefHeight);
			if(!Double.isNaN(maxHeight))
				vBox.setMaxHeight(maxHeight);
			if(alignment != null)
				vBox.setAlignment(alignment);
			if(!fillWidth)
				vBox.setFillWidth(false);
			return vBox;
		}
		
	}
	
	public static VBoxBuilder vBoxBuilder(Node... children) {
		return new VBoxBuilder(children);
	}
	
}
