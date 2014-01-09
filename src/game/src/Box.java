package game.src;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;


public class Box {

	private Vector2f pos, vel;
	private float width, height;
	private Color color;

	public Box(float x, float y, float w, float h, float vx, float vy, Color c) {
		pos = new Vector2f(x, y);
		vel = new Vector2f(vx, vy);
		width = w;
		height = h;
		color = new Color(c);
	}

	public Box(float x, float y, float w, float h, float vx, float vy) {
		this(x, y, w, h, vx, vy, Math.random() < 0.5 ? Color.lightGray : Color.white);
	}

	public Vector2f getVelocity() {
		return vel.copy();
	}
	
	public void setVelocity(Vector2f v) {
		vel = new Vector2f(v);
	}

	public void setVelocity(float x, float y) {
		vel = new Vector2f(x, y);
	}
	
	public Vector2f getPosition() {
		return pos.copy();
	}

	public void setPosition(Vector2f p) {
		pos = new Vector2f(p);
	}
	
	public void setPosition(float x, float y) {
		pos = new Vector2f(x, y);
	}
	
	public Color getColor() {
		return new Color(color);
	}

	public void setColor(float r, float g, float b) {
		color = new Color(r, g, b);
	}

	public void setColor(Color c) {
		color = c;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}

}