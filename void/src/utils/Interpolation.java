package utils;

public final class Interpolation {

	public static interface Interpolator {
		
		double interpolate(double start, double end, double frac);
		
	}
	
	private Interpolation() {
		
	}
	
	public static double lerp(double start, double end, double frac) {
		return start + (end - start) * frac;
	}
	
	public static double square(double start, double end, double frac) {
		return lerp(start, end, frac * frac);
	}
	
	public static double sqrt(double start, double end, double frac) {
		return lerp(start, end, Math.sqrt(frac));
	}
	
}
