package utils;

import java.math.BigInteger;

public final class Parsing {

	private static final CharPredicate
		IS_ASCII_DIGIT = c -> '0' <= c && c <= '9',
		IS_EXPONENT_INDICATOR = c -> c == 'e' || c == 'E',
		IS_DECIMAL_POINT = c -> c == '.';
	
	private Parsing() {
		
	}
	
	/** The {@code end} index is exclusive. This method does not throw an exception for invalid indices, as follows:
	 * <ul>
	 * <li>if {@code start} is negative, it is treated as zero.</li>
	 * <li>if {@code end} is greater than {@code s.length()}, it is treated as {@code s.length()}.</li>
	 * <li>if {@code start > end}, then the region to be searched is empty. This method vacuously returns {@code true}.
	 * </li>
	 * </ul>
	 * */
	public static boolean containsOnly(String s, CharPredicate condition, int start, int end) {
		for(int i = Math.max(start, 0), max = Math.min(end, s.length()); i < max; i++)
			if(!condition.test(s.charAt(i)))
				return false;
		return true;
	}
	
	public static boolean containsOnly(String s, CharPredicate condition, int start) {
		return containsOnly(s, condition, start, s.length());
	}
	
	public static boolean containsOnly(String s, CharPredicate condition) {
		return containsOnly(s, condition, 0);
	}

	public static boolean containsOnlyDigits(String s, int start, int end) {
		return containsOnly(s, IS_ASCII_DIGIT, start, end);
	}

	public static boolean containsOnlyDigits(String s, int start) {
		return containsOnly(s, IS_ASCII_DIGIT, start);
	}
	
	public static boolean containsOnlyDigits(String s) {
		return containsOnly(s, IS_ASCII_DIGIT);
	}
	
	/** Indices are treated identically to {@link #containsOnly(String, CharPredicate, int, int)}. */
	public static int count(String s, CharPredicate condition, int start, int end) {
		int count = 0;
		for(int i = Math.max(start, 0), max = Math.min(end, s.length()); i < max; i++)
			if(condition.test(s.charAt(i)))
				count++;
		return count;
	}
	
	public static int count(String s, CharPredicate condition, int start) {
		return count(s, condition, start, s.length());
	}
	
	public static int count(String s, CharPredicate condition) {
		return count(s, condition, 0);
	}
	
	public static int countDigits(String s, int start, int end) {
		return count(s, IS_ASCII_DIGIT, start, end);
	}
	
	public static int countDigits(String s, int start) {
		return count(s, IS_ASCII_DIGIT, start);
	}
	
	public static int countDigits(String s) {
		return count(s, IS_ASCII_DIGIT);
	}
	
	public static int indexOf(String s, CharPredicate condition, int start, int end) {
		for(int i = Math.max(start, 0), max = Math.min(end, s.length()); i < max; i++)
			if(condition.test(s.charAt(i)))
				return i;
		return -1;
	}
	
	public static int indexOf(String s, CharPredicate condition, int start) {
		return indexOf(s, condition, start, s.length());
	}
	
	public static int indexOf(String s, CharPredicate condition) {
		return indexOf(s, condition, 0);
	}
	
	/** {@code right} is exclusive; {@code left} is inclusive. Searches the indicated range from right to left. Does
	 * not throw an exception for invalid indices in the same manner as
	 * {@link #containsOnly(String, CharPredicate, int, int)}. */
	public static int lastIndexOf(String s, CharPredicate condition, int left, int right) {
		left = Math.max(0, left);
		for(int i = Math.min(right - 1, s.length() - 1); i >= left; i--)
			if(condition.test(s.charAt(i)))
				return i;
		return -1;
	}
	
	public static int lastIndexOf(String s, CharPredicate condition) {
		return lastIndexOf(s, condition, 0, s.length());
	}
	
	public static boolean contains(String s, char c) {
		return s.indexOf(c) >= 0;
	}
	
	/** Whitespace is not allowed. Accepts strings containing only digits as well as strings that use scientific
	 * notation to specify an integer, e.g. ".3e1", "1.2E9", "123e4", or "6E0". Note that "2.e5" is not allowed; there
	 * must be at least one digit between the decimal point and exponent indicator. Note also that "1.34e1" is not
	 * allowed since it not an integer - for an exponent k, either there must be at most k digits after the decimal
	 * point (but before the exponent indicator) or there must not be a decimal point. Negative exponents are not
	 * allowed, even if they would result in an integer. */
	public static boolean isInteger(String s) {
		if(s.isEmpty())
			return false;
		if(containsOnlyDigits(s))
			return true;
		int ei = indexOf(s, IS_EXPONENT_INDICATOR), lio = lastIndexOf(s, IS_EXPONENT_INDICATOR);
		if(ei == -1 || ei != lio || ei == s.length() - 1 || !containsOnlyDigits(s, ei + 1))
			return false;
		int di = indexOf(s, IS_DECIMAL_POINT);
		if(di == -1)
			return containsOnlyDigits(s, 0, ei);
		lio = lastIndexOf(s, IS_DECIMAL_POINT);
		//The line below does NOT allow the decimal point to be immediately before the exponent indicator; e.g. 2.e10 is
		//not allowed. It DOES, however, allow for zero digits before the decimal point, such as in .5e9
		if(di != lio || di >= ei - 1 || !containsOnlyDigits(s, 0, di) || !containsOnlyDigits(s, di + 1, ei))
			return false;
		//Almost there - we just need to check that we don't have too many digits after the decimal point. For
		//example, 1.2e0 satisfies the conditions above but is not an integer. Unfortunately, this means we have to do
		//some parsing to figure out the value of the exponent.
		return ei - di - 1 <= Integer.parseInt(s.substring(ei + 1));
	}
	
	/** Assumes {@code isInteger(s)}. */
	public static BigInteger parseInteger(String s) {
		int ei = indexOf(s, IS_EXPONENT_INDICATOR);
		if(ei == -1) {
			return new BigInteger(s);
		}
		else {
			int di = indexOf(s, IS_DECIMAL_POINT);
			int exponent = Integer.parseInt(s.substring(ei + 1));
			if(di == -1) {
				return new BigInteger(s.substring(0, ei) + "0".repeat(exponent));
			}
			else {
				String significandWithoutDecimal = s.substring(0, di) + s.substring(di + 1, ei);
				return new BigInteger(significandWithoutDecimal + "0".repeat(exponent - (ei - di - 1)));
			}
		}
	}
	
}
