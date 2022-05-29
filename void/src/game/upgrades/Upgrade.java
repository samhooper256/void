package game.upgrades;

import java.math.BigInteger;

import game.Save;
import utils.BigNumbers;

/** Instances of this {@link Upgrade} enum describe upgrades the player can buy. Instances describe the
 * {@link #displayName()}, {@link #baseCost()}, and {@link UpgradeRequirement requirements} of an upgrade. Instances
 * <em>do not</em> describe what the upgrade does. */
public enum Upgrade {
	SANDIER_SAND("Sandier Sand", "Sand balls are twice as meaningful.", BigNumbers.integer(1000));
	
	private final String displayName, description;
	private final BigInteger baseCost;
	private final UpgradeRequirement[] requirements;
	
	/** @param requirements must not be modified after this constructor is called. */
	Upgrade(String displayName, String description, BigInteger baseCost, UpgradeRequirement... requirements) {
		this.displayName = displayName;
		this.description = description;
		this.baseCost = baseCost;
		this.requirements = requirements;
	}

	public String displayName() {
		return displayName;
	}
	
	public String description() {
		return description;
	}

	public BigInteger baseCost() {
		return baseCost;
	}
	
	public boolean isPurchasable(Save save) {
		if(save.ascension().hasUpgrade(this))
			return false;
		if(getTrueCost(save).compareTo(save.mu()) > 0)
			return false;
		for(UpgradeRequirement ur : requirements)
			if(!ur.test(save))
				return false;
		return true;
	}
	
	public BigInteger getTrueCost(Save save) {
		return baseCost; //TODO if anything modifies upgrade costs.
	}
	
}
