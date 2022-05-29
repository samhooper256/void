package utils.fx;

import javafx.scene.control.Label;

public final class Text {

	private Text() {
		
	}
	
	public static Label styledLabel(String styleClass) {
		Label label = new Label();
		label.getStyleClass().add(styleClass);
		return label;
	}
	
	public static Label newLabel(String text, String styleClass) {
		Label label = new Label(text);
		label.getStyleClass().add(styleClass);
		return label;
	}
	
	/** Returns {@code label}*/
	public static Label centerInWidth(Label label, double width) {
		label.layoutXProperty().bind(label.widthProperty().multiply(-.5).add(.5 * width));
		return label;
	}
	
	public static Label centerInHeight(Label label, double height) {
		label.layoutYProperty().bind(label.heightProperty().multiply(-.5).add(.5 * height));
		return label;
	}
	
	public static Label centerIn(Label label, double width, double height) {
		return centerInHeight(centerInWidth(label, width), height);
	}
	
}
