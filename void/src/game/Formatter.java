package game;

import java.math.BigInteger;
import java.util.Arrays;

import utils.BigNumbers;

public final class Formatter {
	
	private static final int SIGNIFICANT_DIGITS_SHOWN = 4;
	private static final String RADIX_POINT = ".", EXPONENT_INDICATOR = "e";
	
	private static final int[] BREAK_POINT_POWERS = {
			3, 	// 1.000k
			6, 	// 1.000m
			9,  // 1.000b
			12, // 1.000t
			15, // 1.000q
			18, // 1.000Q
			21, // 1.000s
			24, // 1.000S
			27, // 1.000o
			30, // 1.000n
			33, // 1.000d
			36, // 1.000u
			39, // 1.000D
			42, // 1.000T
			45, // 10^45 - no more letters after this point.
	};
	
	private static final BigInteger[] BREAK_POINTS = Arrays.stream(BREAK_POINT_POWERS)
			.mapToObj(i -> new BigInteger("1" + "0".repeat(i)))
			.toArray(BigInteger[]::new);
	
	private static final String[] SUFFIXES = {"", "k", "m", "b", "t", "q", "Q", "s", "S", "o", "n", "d", "u", "D", "T"};
	
	private Formatter() {
		
	}
	
	public static String format(BigInteger num) {
		if(num.compareTo(BREAK_POINTS[0]) < 0)
			return num.toString();
		for(int i = 1; i < BREAK_POINTS.length; i++) {
			BigInteger bp = BREAK_POINTS[i];
			if(num.compareTo(bp) < 0) {
				int dc = BigNumbers.digitCount(num);
				String msd = mostSignificantDigits(num, SIGNIFICANT_DIGITS_SHOWN, dc).toString();
				if(dc % 3 == 1)
					return addRadixPoint(msd, 1) + SUFFIXES[i];
				if(dc % 3 == 2)
					return addRadixPoint(msd, 2) + SUFFIXES[i];
				if(dc % 3 == 0)
					return addRadixPoint(msd, 3) + SUFFIXES[i];
			}
		}
		int dc = BigNumbers.digitCount(num);
		return addRadixPoint(mostSignificantDigits(num, 4, dc).toString(), 1) + EXPONENT_INDICATOR + dc;
	}
	
	/** Assumes {@code msd} has at least two characters and all its characters are digits. */
	private static String addRadixPoint(String msd, int index) {
		return msd.substring(0, index) + RADIX_POINT + msd.substring(index);
	}
	
	/** Returns a non-negative {@link BigInteger} containing at most {@code n} digits. The returned value is
	 * the {@code n} most significant digits (base 10) in the {@code BigInteger} parameter. If {@code bi} has fewer than
	 * {@code n} significant digits, {@code bi} is returned. If {@code bi} is zero, zero is returned. <b>Behavior is
	 * undefined if {@code bi} is negative.</b>*/
	@SuppressWarnings("unused") //contains documentation
	private static BigInteger mostSignificantDigits(BigInteger bi, int n) {
		int dc = BigNumbers.digitCount(bi);
		return mostSignificantDigits(bi, n, dc);
	}

	/** Equivalent to {@link #mostSignificantDigits(BigInteger, int)}, except <b>behavior is undefined if {@code bi} is
	 * negative <em>or</em> {@code bi} does not have exactly {@code dc} digits (base 10).</b>*/
	private static BigInteger mostSignificantDigits(BigInteger bi, int n, int dc) {
		return bi.divide(BigInteger.TEN.pow(dc - n));
	}
	
}
