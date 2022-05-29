package game.upgrades;

import java.util.function.Predicate;

import game.Save;

@FunctionalInterface
public interface UpgradeRequirement extends Predicate<Save> {
	
	/** Returns {@code true} if this {@link UpgradeRequirement} is satisfied on the given {@link Save}. */
	@Override
	boolean test(Save save);
	
}
