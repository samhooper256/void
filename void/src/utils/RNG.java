package utils;

import java.util.Random;

public final class RNG {

	private RNG() {
		
	}

	private static final Random SOURCE = new Random();
	
	/** Returns a random {@code double} value from {@code 0.0} (inclusive) to {@code upperBound} (exclusive). Assumes
	 * {@code upperBound} > 0.0.*/
	public static double doubleExclusive(double upperBound) {
		return SOURCE.nextDouble() * upperBound;
	}
	
}
