package game.src;

import java.util.ArrayList;
import org.newdawn.slick.geom.Vector2f;

public interface Force {

	public void addEnergyToTotal(ArrayList<Particle> particles, float en);
	
	public void addGradEToTotal(ArrayList<Particle> particles, Vector2f[] gradE);
	
	public Force copy();
	
}
