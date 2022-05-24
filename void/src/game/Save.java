package game;

import java.math.BigInteger;
import java.util.*;

/** A game save. */
public final class Save {

	private List<AscensionLog> pastAscensions;
	private Ascension ascension;
	
	public Save() {
		pastAscensions = new ArrayList<>();
		ascension = new Ascension();
	}
	
	/** If {@link #isInAscension() in an ascension}, returns the number of meaning units the player currently has.
	 * @throws IllegalStateException if the player is not in an ascension. */
	public BigInteger mu() {
		if(isInAscension())
			return ascension().mu();
		throw new IllegalStateException("Not currently in an ascension");
	}
	
	public boolean isInAscension() {
		return !isBetweenAscensions();
	}
	
	public boolean isBetweenAscensions() {
		return ascension == null;
	}
	
	/** Return the current {@link Ascension} if {@link #isInAscension() in an ascension}, {@code null} otherwise. */
	public Ascension ascension() {
		return ascension;
	}
	
	List<AscensionLog> pastAscensionsUnmodifiable() {
		return Collections.unmodifiableList(pastAscensions);
	}
	
}
