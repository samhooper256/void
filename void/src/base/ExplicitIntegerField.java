package base;

import java.util.Set;

import javafx.scene.control.*;
import javafx.scene.input.*;

/** <p>A {@link TextField} that, in addition to listening for {@link KeyEvent KeyEvents} like a usual text field,
 * also allows events to be explicitly passed via {@link #handle(KeyEvent)}. The {@link #handle(KeyEvent)} method
 * only allows digits (0-9) to be entered. </p>
 * <p>This class is not in the {@code utils.fx} package because it relies on {@link Void}.</p>*/
public class ExplicitIntegerField extends TextField implements KeyListener {

	private static final Set<String> LEGAL_KEY_TEXTS =
			Set.of("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ".", "e", "E");
	
	//Optional TODO - make this support more features (e.g. selecting text with arrow keys, Home and End keys, Ctrl+C, Ctrl+V, etc.)
	@Override
	public void handle(KeyEvent event) {
		if(event.getEventType() == KeyEvent.KEY_PRESSED) {
			final String key = event.getText(), text = getText();
			final IndexRange srange = getSelection();
			final int cpos = getCaretPosition(), srs = srange.getStart(), sre = srange.getEnd();
			if(key.length() == 1 && LEGAL_KEY_TEXTS.contains(key)) {
				if(srs != sre)
					setText(text.substring(0, srs) + key + text.substring(sre));
				else
					setText(text.substring(0, cpos) + key + text.substring(cpos));
				positionCaret(cpos + 1);
			}
			else {
				KeyCode code = event.getCode();
				if(code == KeyCode.BACK_SPACE) {
					if(srs != sre) {
						setText(text.substring(0, srs) + text.substring(sre));
						positionCaret(srange.getStart());
					}
					else if(ctrlDown()) {
						setText("");
					}
					else if(cpos != 0) {
						setText(text.substring(0, cpos - 1) + text.substring(cpos));
						positionCaret(cpos - 1);
					}
				}
				else if(code == KeyCode.LEFT && cpos != 0) {
					if(ctrlDown())
						positionCaret(0);
					else
						positionCaret(cpos - 1);
				}
				else if(code == KeyCode.RIGHT && cpos != text.length()) {
					if(ctrlDown())
						positionCaret(text.length());
					else
						positionCaret(cpos + 1);
				}
				else if(code == KeyCode.A && ctrlDown()) {
					selectAll();
				}
				else if(code == KeyCode.HOME) {
					positionCaret(0);
				}
				else if(code == KeyCode.END) {
					positionCaret(text.length());
				}
			}
		}
	}
	
	private static boolean ctrlDown() {
		return Void.isDown(KeyCode.CONTROL) || Void.isDown(KeyCode.COMMAND);
	}
	
}
