package utils;

import java.math.BigInteger;

public class BigNumbers {
	
	public static BigInteger integer(long num) {
		return BigInteger.valueOf(num);
	}
	
	public static BigInteger integer(String s) {
		return new BigInteger(s);
	}
	
	public static int digitCount(BigInteger bi) {
		if(isZero(bi))
			return 1;
		else if(isNegative(bi))
			bi = bi.negate();
		double factor = Math.log(2) / Math.log(10);
		int digitCount = (int) (factor * bi.bitLength() + 1);
		if(BigInteger.TEN.pow(digitCount - 1).compareTo(bi) > 0)
			digitCount--;
		return digitCount;
	}

	public static boolean isZero(BigInteger bi) {
		return bi.compareTo(BigInteger.ZERO) == 0;
	}
	
	public static boolean isNegative(BigInteger bi) {
		return bi.compareTo(BigInteger.ZERO) < 0;
	}
	
}
