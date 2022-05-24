package utils;

import java.math.BigInteger;

public class BigNumbers {

	public static void main(String[] args) {
		System.out.println(BigInteger.ZERO.bitLength());
		System.out.println(BigInteger.ONE.bitLength());
		
		System.out.println();
		
		System.out.println(digitCount(BigInteger.ZERO));
		System.out.println(digitCount(BigInteger.valueOf(-1)));
		System.out.println(digitCount(BigInteger.valueOf(-1000)));
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
