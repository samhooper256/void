package base;

@FunctionalInterface
public interface Updatable {

	/** @param diff time since last update (ns).*/
	void update(long diff);
	
}
