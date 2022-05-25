package base;

import javafx.scene.control.TextField;
import javafx.scene.input.*;

/** <p>A {@link TextField} that, in addition to listening for {@link KeyEvent KeyEvents} like a usual text field,
 * also allows events to be explicitly passed via {@link #handle(KeyEvent)}. The {@link #handle(KeyEvent)} method
 * only allows digits (0-9) to be entered. </p>
 * <p>This class is not in the {@code utils.fx} package because it relies on {@link Void}.</p>*/
public class ExplicitNumberField extends TextField implements KeyListener {

	//Optional TODO - make this support more features (e.g. selecting text, Ctrl+A, Home and End keys, Ctrl+V, etc.)
	@Override
	public void handle(KeyEvent event) {
		if(event.getEventType() == KeyEvent.KEY_PRESSED) {
			final String key = event.getText(), text = getText();
			final int cpos = getCaretPosition();
			if(key.length() == 1 && '0' <= key.charAt(0) && key.charAt(0) <= '9') {
				setText(text + key);
				positionCaret(getText().length());
			}
			else {
				KeyCode code = event.getCode();
				if(code == KeyCode.BACK_SPACE) {
					if(Void.isDown(KeyCode.CONTROL) || Void.isDown(KeyCode.COMMAND)) {
						setText("");
					}
					else if(cpos != 0) {
						setText(text.substring(0, cpos - 1) + text.substring(cpos));
						positionCaret(cpos - 1);
					}
				}
				else if(code == KeyCode.LEFT && cpos != 0) {
					positionCaret(cpos - 1);
				}
				else if(code == KeyCode.RIGHT && cpos != text.length()) {
					positionCaret(cpos + 1);
				}
			}
		}
	}
	
}
