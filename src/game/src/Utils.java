package game.src;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

public class Utils {

	public static Vector2f rotCC90(Vector2f v) {
		return new Vector2f(-v.y, v.x);
	}
	
	public static float cross2D(Vector2f a, Vector2f b) {
		return a.x * b.y - a.y * b.x;
	}
	
	public static Vector2f add(Vector2f a, Vector2f b) {
		
		return null;
	}
	
	public static Color randomColor() {
		return new Color((float) Math.random(), (float) Math.random(), (float) Math.random());
	}
	
}
