package game;

import java.math.BigInteger;

public final class Ascension {

	private BigInteger mu;
	
	/** The ascension starts with zero {@link #mu()}.*/
	public Ascension() {
		mu = BigInteger.ZERO;
	}
	
	/** Returns the current number of available (unspent) meaning units in this ascension. */
	public BigInteger mu() {
		return mu;
	}

	/** Gains the given number of {@link #mu()}. */
	public void gain(BigInteger gain) {
		mu = mu.add(gain);
	}
	
	public AscensionLog generateLog() {
		return null; //TODO
	}
	
}
