package game.src;

import java.util.ArrayList;

import org.newdawn.slick.geom.Vector2f;

public class SpringForce implements Force {

	private Edge edge;
	private float k, l0, b;
	
	public SpringForce(Edge e, float k, float l0) {
		this(e, k, l0, 0.0f);
	}
	
	public SpringForce(Edge e, float k, float l0, float b) {
		edge = e;
		this.k = k;
		this.l0 = l0;
		this.b = b;
	}
	
	public void addEnergyToTotal(ArrayList<Particle> particles, float en) {
		float l = edge.length();
		en += 0.5f * k * (l - l0) * (l - l0);
	}

	public void addGradEToTotal(ArrayList<Particle> particles, Vector2f[] gradE) {
		// elastic component
		Vector2f nhat = edge.getVector().normalise();
		float l = edge.length();
		Vector2f fdamp = nhat;
		nhat = nhat.scale(k * (l - l0));
		Vector2f temp = gradE[edge.getFirstIndex()].copy();
		gradE[edge.getFirstIndex()] = temp.sub(nhat);
		temp = gradE[edge.getSecondIndex()].copy();
		gradE[edge.getSecondIndex()] = temp.add(nhat);
		
		// damping
		fdamp = fdamp.scale(b * fdamp.dot(edge.getRelativeVelocity()));
		temp = gradE[edge.getFirstIndex()].copy();
		gradE[edge.getFirstIndex()] = temp.sub(fdamp);
		temp = gradE[edge.getSecondIndex()].copy();
		gradE[edge.getSecondIndex()] = temp.add(fdamp);
	}

	public Force copy() {
		return new SpringForce(edge, k, l0, b);
	}

}
