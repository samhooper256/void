package game.feeders;

import javafx.scene.image.Image;
import utils.BigNumbers;
import utils.fx.Images;

import static base.VoidScene.CENTER_Y;
import static base.VoidScene.CENTER_X;
import static utils.fx.Images.*;

import java.math.*;
import java.util.List;

public enum FeederTag {
	MUD_SLINGER("Bob", BigInteger.ZERO, BigDecimal.TEN, BigInteger.ONE, (long) 1e9, 700, CENTER_Y - FEEDER.getHeight(),
			FEEDER, MUD_BALL),
	STICK_THROWER("Mosh", BigNumbers.integer(1000), BigDecimal.TEN, BigInteger.ONE, (long) 1e9, 720, 300,
			FEEDER, MUD_BALL);
	
	private static final FeederTag[] VALUES = values();

	/** O(1) */
	public static FeederTag withOrdinal(int ordinal) {
		return VALUES[ordinal];
	}

	/** O(1). Unmodifiable. */
	public static List<FeederTag> listValues() {
		return List.of(VALUES);
	}
	
	private final String displayName;
	private final BigDecimal baseCostAsBigDecimal;
	private final BigInteger baseMU, baseInitiationCost;
	private final long baseRate;
	private final double centerX, centerY;
	private final Image image, projectileImage;
	
	FeederTag(String name, BigInteger baseInitiationCost, BigDecimal baseCostAsBigDecimal, BigInteger baseMU, long baseRate,
			double centerX, double centerY, Image image, Image projectileImage) {
		this.displayName = name;
		this.baseInitiationCost = baseInitiationCost;
		this.baseCostAsBigDecimal = baseCostAsBigDecimal;
		this.baseMU = baseMU;
		this.baseRate = baseRate;
		this.centerX = centerX;
		this.centerY = centerY;
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
	
	public BigInteger baseInitiationCost() {
		return baseInitiationCost;
	}
	
	public BigInteger baseMU() {
		return baseMU;
	}
	
	/** nanoseconds */
	public long baseRate() {
		return baseRate;
	}
	
	/** center x. */
	public double centerX() {
		return centerX;
	}
	
	/** center y */
	public double centerY() {
		return centerY;
	}
	
	public Image image() {
		return image;
	}
	
	public Image uninitiatedImage() {
		return Images.UNINITIATED_FEEDER;
	}
	
	public Image projectileImage() {
		return projectileImage;
	}
	
}
