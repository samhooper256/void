package base;

import java.util.function.LongConsumer;

import javafx.animation.AnimationTimer;

final class Timer extends AnimationTimer {

	private static final Timer INSTANCE = new Timer((LongConsumer) Void::update);
	
	static Timer get() {
		return INSTANCE;
	}
	
	private final LongConsumer updateAction;
	
	private long last = -1;
	
	private Timer(LongConsumer updateAction) {
		super();
		this.updateAction = updateAction;
	}
	
	@Override
	public void handle(long now) {
		long diff = getDiff(now);
		if(diff == 0)
			return;
		if(diff < 0) {
			setLast(now);
			return;
		}
		updateAction.accept(diff);
		setLast(now);
	}
	
	private long getDiff(long now) {
		if(last < 0)
			return -1;
		return now - last;
	}
	
	private void setLast(long last) {
		this.last = last;
	}
	
}