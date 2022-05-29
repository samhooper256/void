package utils.fx;

import java.util.*;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

public final class Panes {

	private Panes() {
		
	}
	
	public static final class VBoxBuilder {
		
		private final List<Node> children;
		
		private String styleClass;
		private double minWidth, prefWidth, maxWidth;
		
		private VBoxBuilder(Node... children) {
			if(children.length == 0) {
				this.children = new ArrayList<>();
			}
			else {
				this.children = new ArrayList<>(children.length);
				Collections.addAll(this.children, children);
			}
			minWidth = prefWidth = maxWidth = Double.NaN;
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
			return vBox;
		}
		
	}
	
	public static VBoxBuilder vBoxBuilder(Node... children) {
		return new VBoxBuilder(children);
	}
	
}
