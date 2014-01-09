package game.src;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

public class Edge {

	private int i0, i1;
	private Particle p0, p1;
	private float radius;
	private Color color;
	
	public Edge(int i0, int i1, Particle p0, Particle p1, float rad) {
		this(i0, i1, p0, p1, rad, Utils.randomColor());
	}
	
	public Edge(int i0, int i1, Particle p0, Particle p1, float rad, float r, float g, float b) {
		this(i0, i1, p0, p1, rad, new Color(r, g, b));
	}
	
	public Edge(int i0, int i1, Particle p0, Particle p1, float rad, Color c) {
		this.i0 = i0;
		this.i1 = i1;
		this.p0 = p0;
		this.p1 = p1;
		radius = rad;
		color = new Color(c);
	}
	
	public int getFirstIndex() { return i0; }
	public int getSecondIndex() { return i1; }
	public Particle getFirstParticle() { return p0; }
	public Particle getSecondParticle() { return p1; }
	public float getRadius() { return radius; }
	public void setRadius(float rad) {radius = rad; }
	public Color getColor() { return new Color(color); }
	public void setColor(float r, float g, float b) { color = new Color(r, g, b); }
	public void setColor(Color c) { color = new Color(c); }
	
	public float computeAngle() {
		return (float) Math.toDegrees(Math.atan2(p1.y() - p0.y(), p1.x() - p0.x()));
	}
	
	public float length() {
		return (float) Math.sqrt((p0.x() - p1.x()) * (p0.x() - p1.x()) + (p0.y() - p1.y()) * (p0.y() - p1.y()));
	}
	
	public Vector2f getVector() {
		return new Vector2f(p1.x() - p0.x(), p1.y() - p0.y());
	}
	
	public Vector2f getRelativeVelocity() {
		return new Vector2f(p1.getVelX() - p0.getVelX(), p1.getVelY() - p0.getVelY());
	}
	
}