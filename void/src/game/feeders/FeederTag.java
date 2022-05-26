package game.feeders;

import javafx.scene.image.Image;

import static base.VoidScene.CENTER_Y;
import static base.VoidScene.CENTER_X;
import static utils.fx.Images.*;

import java.math.*;

public enum FeederTag {
	MUD_SLINGER("Bob", BigDecimal.TEN, BigInteger.ZERO, BigInteger.ONE, (long) 1e9, 700, CENTER_Y - FEEDER.getHeight(),
			FEEDER, MUD_BALL);
	
	private static final FeederTag[] VALUES = values();

	/** O(1) */
	public static FeederTag withOrdinal(int ordinal) {
		return VALUES[ordinal];
	}

	private final String displayName;
	private final BigDecimal baseCostAsBigDecimal;
	private final BigInteger baseMU, initiationCost;
	private final long baseRate;
	private final double x, y;
	private final Image image, projectileImage;
	
	FeederTag(String name, BigDecimal baseCostAsBigDecimal, BigInteger initiationCost, BigInteger baseMU, long baseRate,
			double x, double y, Image image,
			Image projectileImage) {
		this.displayName = name;
		this.baseCostAsBigDecimal = baseCostAsBigDecimal;
		this.initiationCost = initiationCost;
		this.baseMU = baseMU;
		this.baseRate = baseRate;
		this.x = x;
		this.y = y;
		this.image = image;
		this.projectileImage = projectileImage;
	}
	
	public String displayName() {
		return displayName;
	}
	
	/** The base cost of upgrading from level 1 to level 2. */
	public BigDecimal baseCostAsBigDecimal() {
		return baseCostAsBigDecimal;
	}
	
	public BigInteger initiationCost() {
		return initiationCost;
	}
	
	public BigInteger baseMU() {
		return baseMU;
	}
	
	/** nanoseconds */
	public long baseRate() {
		return baseRate;
	}
	
	public double x() {
		return x;
	}
	
	public double y() {
		return y;
	}
	
	public Image image() {
		return image;
	}
	
	public Image projectileImage() {
		return projectileImage;
	}
	
}
