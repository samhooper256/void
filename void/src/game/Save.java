package game;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.*;

/** A game save. */
public final class Save implements Serializable {

	private static final long serialVersionUID = -5226145427742382698L;
	
	private List<AscensionLog> pastAscensions;
	private Ascension ascension;
	
	public Save() {
		pastAscensions = new ArrayList<>();
		ascension = new Ascension();
	}
	
	/** If {@link #isInAscension() in an ascension}, returns the number of meaning units the player currently has.
	 * @throws IllegalStateException if the player is not in an ascension. */
	public BigInteger mu() {
		return ascension().mu();
	}
	
	/** Return the current {@link Ascension} if {@link #isInAscension() in an ascension}. Note that the current
	 * {@link Ascension} changes as soon as the player clicks "Ascend".*/
	public Ascension ascension() {
		return ascension;
	}
	
	List<AscensionLog> pastAscensionsUnmodifiable() {
		return Collections.unmodifiableList(pastAscensions);
	}
	
}
