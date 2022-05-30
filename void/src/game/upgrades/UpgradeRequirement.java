package game.upgrades;

import java.util.function.Predicate;

import game.Save;

public interface UpgradeRequirement extends Predicate<Save> {
	
	/** Returns {@code true} if this {@link UpgradeRequirement} is satisfied on the given {@link Save}. */
	@Override
	boolean test(Save save);
	
	/** Returns a string describing this {@link UpgradeRequirement} to the user. Recommended language is
	 * "Requires XYZ."*/
	String displayString();
	
}
