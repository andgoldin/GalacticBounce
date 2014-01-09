package game.src;

import java.util.ArrayList;

import org.newdawn.slick.geom.Vector2f;

public class SimpleGravityForce implements Force {

	private Vector2f gravity;
	
	public SimpleGravityForce(Vector2f gravity) {
		this.gravity = new Vector2f(gravity);
	}
	
	public void addEnergyToTotal(ArrayList<Particle> particles, float en) {
		for (int i = 0; i < particles.size(); i++) {
			en -= particles.get(i).getMass() * gravity.dot(particles.get(i).getPosition());
		}
	}

	public void addGradEToTotal(ArrayList<Particle> particles, Vector2f[] gradE) {
		for (int i = 0; i < gradE.length; i++) {
			Vector2f gTemp = gravity.copy(), gETemp = gradE[i].copy();
			gradE[i] = gETemp.sub(gTemp.scale(particles.get(i).getMass()));
		}
	}

	public Force copy() {
		return new SimpleGravityForce(gravity);
	}

}
