package game;

import java.math.BigInteger;

import base.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import utils.fx.*;

class Projectile extends StackPane implements Updatable {

	private static final double DEFAULT_VELOCITY = 100; //pixels per second
	
	private final ResizableImage rimage;
	
	private final double width, height, velocity;
	private final BigInteger mu;
	
	public Projectile(BigInteger mu) {
		this(mu, Images.MUD_BALL);
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
	}
	
	@Override
	public void update(long diff) {
		double angrad = Math.atan2(VoidScene.CENTER_Y - getCenterY(), VoidScene.CENTER_X - getCenterX());
		double sec = diff * 1e-9;
		double xdist = Math.cos(angrad) * velocity() * sec, ydist = Math.sin(angrad) * velocity() * sec;
		if(Math.abs(xdist) > Math.abs(VoidScene.CENTER_X - getCenterX()))
			GamePane.get().reachedCenter(this);
		else
			setCenter(getCenterX() + xdist, getCenterY() + ydist);
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
