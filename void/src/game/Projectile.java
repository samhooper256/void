package game;

import java.math.BigInteger;

import base.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import utils.RNG;
import utils.fx.*;

public class Projectile extends StackPane implements Updatable {

	private static final double ROTATIONAL_VELOCITY = 45; //degrees per second
	private static final double DEFAULT_VELOCITY = 200; //pixels per second
	private static final double ACCELERATION = 100; //pixels per second per second
	
	private final ResizableImage rimage;
	
	private final double width, height;
	private final BigInteger mu;
	
	private double velocity;
	
	public Projectile(BigInteger mu) {
		this(mu, Images.SAND_BALL);
	}
	
	public Projectile(BigInteger mu, Image image) {
		this(mu, image, DEFAULT_VELOCITY);
	}
	
	public Projectile(BigInteger mu, Image image, double velocity) {
		this.mu = mu;
		rimage = new ResizableImage(image);
		width = image.getWidth();
		height = image.getHeight();
		this.velocity = velocity;
		getChildren().add(rimage);
		setMouseTransparent(true);
		setRotate(RNG.doubleExclusive(360));
	}
	
	@Override
	public void update(long diff) {
		//translational motion:
		double angrad = Math.atan2(VoidScene.CENTER_Y - getCenterY(), VoidScene.CENTER_X - getCenterX());
		double sec = diff * 1e-9;
		velocity += ACCELERATION * sec;
		double xdist = Math.cos(angrad) * velocity() * sec, ydist = Math.sin(angrad) * velocity() * sec;
		if(Math.abs(xdist) > Math.abs(VoidScene.CENTER_X - getCenterX()))
			GameLayer.get().reachedCenter(this);
		else
			setCenter(getCenterX() + xdist, getCenterY() + ydist);
		//rotational motion:
		setRotate(getRotate() + ROTATIONAL_VELOCITY * sec);
	}
	
	public void setCenterX(double x) {
		setLayoutX(x - width * .5);
	}
	
	public void setCenterY(double y) {
		setLayoutY(y - height * .5);
	}
	
	public void setCenter(double x, double y) {
		setCenterX(x);
		setCenterY(y);
	}
	
	public double getCenterX() {
		return getLayoutX() + width * .5;
	}
	
	public double getCenterY() {
		return getLayoutY() + height * .5;
	}

	public double velocity() {
		return velocity;
	}
	
	public BigInteger mu() {
		return mu;
	}
	
}
