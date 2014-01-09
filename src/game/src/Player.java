package game.src;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Vector2f;

public class Player {

	private Vector2f pos, vel;
	private Animation animation;
	
	public Player(float x, float y, float vx, float vy, Animation defaultAnim, boolean pingPong) {
		pos = new Vector2f(x, y);
		vel = new Vector2f(vx, vy);
		animation = defaultAnim;
		animation.setPingPong(pingPong);
	}
	
	public Animation getAnimation() {
		return animation;
	}

	public Vector2f getPosition() {
		return pos.copy();
	}
	
	public void setPosition(Vector2f newPos) {
		pos = new Vector2f(newPos);
	}
	
	public Vector2f getVelocity() {
		return vel.copy();
	}
	
	public void setVelocity(Vector2f newVel) {
		vel = new Vector2f(newVel);
	}
	
	public float getWidth() {
		return animation.getWidth();
	}
	
	public float getHeight() {
		return animation.getHeight();
	}
	
}