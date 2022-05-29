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
	
}
