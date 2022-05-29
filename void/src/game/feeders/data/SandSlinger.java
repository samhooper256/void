package game.feeders.data;

import java.math.BigInteger;

import game.Hub;
import game.feeders.FeederTag;
import game.upgrades.Upgrade;

public final class SandSlinger extends FeederData {

	private static final long serialVersionUID = 2117780801281467864L;

	public SandSlinger() {
		super(FeederTag.SAND_SLINGER);
	}
	
	@Override
	public BigInteger mu() {
		BigInteger result = level().multiply(tag().baseMU());
		if(Hub.hasUpgrade(Upgrade.SANDIER_SAND))
			result = result.multiply(BigInteger.TWO);
		return result;
	}
	
	@Override
	public long rate() {
		return tag().baseRate();
	}
	
}
