package game.upgrades;

import java.math.BigInteger;

import game.*;
import game.feeders.*;
import game.feeders.data.FeederData;

/** Requires that a certain {@link FeederTag feeder} be a certain {@link FeederData#level() level}. */
public final class LevelRequirement implements UpgradeRequirement {

	public static LevelRequirement of(FeederTag tag, BigInteger minLevel) {
		return new LevelRequirement(tag, minLevel);
	}
	
	private final FeederTag tag;
	private final BigInteger minLevel;
	
	private LevelRequirement(FeederTag tag, BigInteger minLevel) {
		this.tag = tag;
		this.minLevel = minLevel;
	}
	
	FeederTag tag() {
		return tag;
	}
	
	BigInteger minLevel() {
		return minLevel;
	}

	@Override
	public boolean test(Save save) {
		if(!save.ascension().hasFeeder(tag()))
			return false;
		return save.ascension().getFeederData(tag()).level().compareTo(minLevel()) >= 0;
	}

	@Override
	public String displayString() {
		return String.format("Requires level %s %s", Formatter.format(minLevel()), tag().displayName());
	}
	
}
