package game;

import java.math.BigInteger;

import game.upgrades.Upgrade;

public final class Hub {

	private Hub() {
		
	}
	
	/** Assumes the player is in game (not in the main menu). Returns the current {@link Save}. */
	public static Save save() {
		return GameLayer.get().save();
	}

	public static Ascension ascension() {
		return save().ascension();
	}
	
	public static BigInteger mu() {
		return save().mu();
	}

	public static boolean canAfford(BigInteger cost) {
		return save().canAfford(cost);
	}
	
	public static void loseMU(BigInteger mu) {
		ascension().lose(mu);
	}
	
	public static boolean hasUpgrade(Upgrade upgrade) {
		return ascension().hasUpgrade(upgrade);
	}
	
}
