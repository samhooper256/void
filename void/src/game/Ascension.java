package game;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.*;

import game.feeders.*;

public final class Ascension implements Serializable {

	private static final long serialVersionUID = -8214211348207545572L;
	
	private BigInteger mu;
	private Map<FeederTag, FeederData> feeders;
	
	/** The ascension starts with zero {@link #mu()}.*/
	public Ascension() {
		mu = BigInteger.ZERO;
		feeders = new HashMap<>();
		feeders.put(FeederTag.MUD_SLINGER, new FeederData(FeederTag.MUD_SLINGER, 1));
	}
	
	/** Returns the current number of available (unspent) meaning units in this ascension. */
	public BigInteger mu() {
		return mu;
	}

	/** Assumes {@code gain} is non-negative. Gains the given number of {@link #mu()}. */
	public void gain(BigInteger gain) {
		mu = mu.add(gain);
	}
	
	/** Assumes {@code loss} is non-negative. */
	public void lose(BigInteger loss) {
		if(loss.compareTo(mu()) > 0)
			throw new IllegalArgumentException(
					String.format("Cannot lose %s MU because we only have %s MU", loss, mu()));
		mu = mu.subtract(loss);
	}
	
	public boolean hasFeeder(FeederTag tag) {
		return feeders.containsKey(tag);
	}
	
	/** returns {@code null} if this {@link Ascension} does not {@link #hasFeeder(FeederTag) have} the feeder. */
	public FeederData getFeederData(FeederTag tag) {
		return feeders.get(tag);
	}
	
	public Collection<FeederData> getFeederData() {
		return feeders.values();
	}
	
	public AscensionLog generateLog() {
		return null; //TODO
	}
	
}
