package game.feeders;

import java.math.BigInteger;

import game.*;

public final class FeederUtils {

	private FeederUtils() {
		
	}
	
	/** Returns the true cost of initiating the given feeder in the current {@link Ascension} of the given
	 * {@link Save}. This is not necessarily the same as {@link FeederTag#baseInitiationCost()}. */
	public static BigInteger trueInitiationCost(FeederTag tag, Save save) {
		return tag.baseInitiationCost();
	}
	
}
