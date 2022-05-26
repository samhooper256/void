package game.feeders;

import java.io.Serializable;
import java.math.*;

import game.*;

/** {@link FeederData} is serialized when a game {@link Save} is stored. */
public final class FeederData implements Serializable {
	
	private static final long serialVersionUID = -6769666422252417179L;
	private static final double COST_INCREASE_FACTOR = 1.1;
	private static final MathContext NEAREST_INTEGER = new MathContext(0);
	private static final BigDecimal E9 = BigDecimal.valueOf(1_000_000_000);
	
	/** Storing the {@link FeederTag#ordinal() ordinal} instead of the {@link FeederTag} itself makes this
	 * {@link FeederData} object cheaper to serialize. */
	private final int tagOrdinal;
	
	private BigInteger level;
	
	/** {@link #level()} is {@code 0}. */
	public FeederData(FeederTag tag) {
		this(tag, 0);
	}
	
	public FeederData(FeederTag tag, long startingLevel) {
		tagOrdinal = tag.ordinal();
		level = BigInteger.valueOf(startingLevel);
	}

	public BigInteger level() {
		return level;
	}
	
	public FeederTag tag() {
		return FeederTag.withOrdinal(tagOrdinal);
	}

	/** Returns the {@link Projectile#mu()} of the projectiles produced by this feeder, <em>including</em> any temporary
	 * effects. */
	public BigInteger mu() {
		return level().multiply(tag().baseMU());
	}
	
	/** Returns the rate in ns/throw at which this {@link Feeder} throws projectiles, <em>including</em> any temporary
	 * effects. */
	public long rate() {
		return tag().baseRate();
	}
	
	public void levelUp() {
		BigInteger nextCost = nextCost();
		Hub.loseMU(nextCost);
		level = level.add(BigInteger.ONE);
	}
	
	public BigInteger nextCost() {
		return costOfLevelAfter(level());
	}
	
	public BigInteger costOfLevelAfter(BigInteger level) {
		return tag().baseCostAsBigDecimal().multiply(
				BigDecimal.valueOf(Math.pow(COST_INCREASE_FACTOR, level.doubleValue() - 1)), NEAREST_INTEGER)
				.toBigInteger();
	}
	
	public BigInteger mupsRounded() {
		return new BigDecimal(mu()).divide(new BigDecimal(rate())).multiply(E9, NEAREST_INTEGER).toBigInteger();
	}
	
}
