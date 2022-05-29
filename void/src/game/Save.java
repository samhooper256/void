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
		ascension = new Ascension(this);
	}
	
	/** Equivalent to {@code ascension().mu()}. */
	public BigInteger mu() {
		return ascension().mu();
	}
	
	/** Return the current {@link Ascension} if {@link #isInAscension() in an ascension}. Note that the current
	 * {@link Ascension} changes as soon as the player clicks "Ascend".*/
	public Ascension ascension() {
		return ascension;
	}
	
	public boolean canAfford(BigInteger cost) {
		return ascension().canAfford(cost);
	}
	
	public 
	List<AscensionLog> pastAscensionsUnmodifiable() {
		return Collections.unmodifiableList(pastAscensions);
	}
	
}
