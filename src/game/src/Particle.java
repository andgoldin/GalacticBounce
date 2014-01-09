package game.src;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

public class Particle {

	public static final int PARTICLE_WORLD = 0, PARTICLE_BULLET = 1, PARTICLE_ENEMY = 2;
	
	private float mass, radius;
	private Vector2f pos, vel;
	private Color color;
	private boolean isFixed;
	private int particleType, maxHealth, health;
	
	public Particle(int type, float x, float y, float vx, float vy, float m, float rad, boolean fixed) {
		this(type, x, y, vx, vy, m, rad, Utils.randomColor(), fixed);
	}
	
	public Particle(int type, float x, float y, float vx, float vy, float m, float rad, float r, float g, float b, boolean fixed) {
		this(type, x, y, vx, vy, m, rad, new Color(r, g, b), fixed);
	}
	
	public Particle(int type, float x, float y, float vx, float vy, float m, float rad, Color c, boolean fixed) {
		particleType = type;
		pos = new Vector2f(x, y);
		vel = new Vector2f(vx, vy);
		mass = m;
		radius = rad;
		color = new Color(c);
		isFixed = fixed;
		maxHealth = health = 20;
	}
	
	public int getParticleType() { return particleType; }
	public Vector2f getPosition() { return new Vector2f(pos); }
	public float x() { return pos.x; }
	public float y() { return pos.y; }
	public void setPosition(float x, float y) { pos = pos.set(x, y); }
	public void setPosition(Vector2f p) { pos.set(p); }
	public Vector2f getVelocity() { return new Vector2f(vel); }
	public float getVelX() { return vel.x; }
	public float getVelY() { return vel.y; }
	public void setVelocity(float x, float y) { vel = vel.set(x, y); }
	public void setVelocity(Vector2f v) { vel.set(v); }
	public float getMass() { return mass; }
	public void setMass(float m) { mass = m; }
	public float getRadius() { return radius; }
	public void setRadius(float rad) { radius = rad; }
	public Color getColor() { return new Color(color); }
	public void setColor(float r, float g, float b) { color = new Color(r, g, b); }
	public void setColor(Color c) { color = new Color(c); }
	public void setColorRandom() { color = Utils.randomColor(); }
	public boolean isFixed() { return isFixed; }
	public void setFixed(boolean b) { isFixed = b; }
	
	public void setHealth(int h) { health = h; }
	public int getHealth() { return health; }
	public int getMaxHealth() { return maxHealth; }
	
	public String toString() {
		return "[Particle: x = " + pos.x + ", y = " + pos.y + "]";
	}

}
