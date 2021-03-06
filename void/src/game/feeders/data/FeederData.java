package game.feeders.data;

import java.io.Serializable;
import java.math.*;

import game.*;
import game.feeders.*;

/** {@link FeederData} is serialized when a game {@link Save} is stored. */
public abstract class FeederData implements Serializable {
	
	private static final long serialVersionUID = -6769666422252417179L;
	private static final double
		COST_INCREASE_FACTOR = 1.1,
		BUY_10_MULTIPLIER = (1 - Math.pow(COST_INCREASE_FACTOR, 10)) / (1 - 1.1); //partial sum of geometric series.
	private static final MathContext
		NEAREST_INTEGER = new MathContext(0),
		INTERMEDIATE_CONTEXT = MathContext.DECIMAL128;
	private static final BigDecimal E9 = BigDecimal.valueOf(1_000_000_000);
	
	public static FeederData create(FeederTag tag) {
		return tag.dataFactory().get();
	}
	
	/** Storing the {@link FeederTag#ordinal() ordinal} instead of the {@link FeederTag} itself makes this
	 * {@link FeederData} object cheaper to serialize. */
	private final int tagOrdinal;
	
	private BigInteger level;
	
	/** Starts at level 1. */
	protected FeederData(FeederTag tag) {
		tagOrdinal = tag.ordinal();
		level = BigInteger.ONE;
	}

	public BigInteger level() {
		return level;
	}
	
	public FeederTag tag() {
		return FeederTag.withOrdinal(tagOrdinal);
	}

	/** Returns the {@link Projectile#mu()} of the projectiles produced by this feeder, <em>including</em> any temporary
	 * effects. This method should be overridden as most feeders will have their MU influenced by upgrades, abilities,
	 * etc. */
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
	
	public void levelUp10() {
		BigInteger next10Cost = next10Cost();
		Hub.loseMU(next10Cost);
		level = level.add(BigInteger.TEN);
	}
	
	public BigInteger nextCost() {
		return costOfLevelAfter(level());
	}
	
	public BigInteger next10Cost() {
		return costOf10LevelsAfter(level());
	}
	
	public BigInteger costOfLevelAfter(BigInteger level) {
		return tag().baseCostAsBigDecimal().multiply(
				BigDecimal.valueOf(Math.pow(COST_INCREASE_FACTOR, level.doubleValue() - 1)), NEAREST_INTEGER)
				.toBigInteger();
	}
	
	private BigInteger costOf10LevelsAfter(BigInteger level) {
		BigDecimal nextLevel = tag().baseCostAsBigDecimal().multiply(
				BigDecimal.valueOf(Math.pow(COST_INCREASE_FACTOR, level.doubleValue() - 1)));
		return nextLevel.multiply(BigDecimal.valueOf(BUY_10_MULTIPLIER), NEAREST_INTEGER).toBigInteger();
	}
	
	public BigInteger mupsRounded() {
		return new BigDecimal(mu())
				.divide(new BigDecimal(rate()), INTERMEDIATE_CONTEXT)
				.multiply(E9, NEAREST_INTEGER).toBigInteger();
	}
	
}
