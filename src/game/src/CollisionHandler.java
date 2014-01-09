package game.src;

import org.newdawn.slick.geom.Vector2f;

public class CollisionHandler {

	public static final float COR = 0.5f;
	
	
	public static boolean detectParticleParticle(World world, int idx1, int idx2) {
		
		Vector2f x1 = world.getParticle(idx1).getPosition().copy();
		Vector2f v1 = world.getParticle(idx1).getVelocity().copy();
		Vector2f x2 = world.getParticle(idx2).getPosition().copy();
		Vector2f v2 = world.getParticle(idx2).getVelocity().copy();
		
		Vector2f n = x2.sub(x1);
		
		if (n.length() < world.getRadius(idx1) + world.getRadius(idx2)) {
			float relativeVel = v1.sub(v2).dot(n);
			if (relativeVel > 0.0f) return true;
		}
		return false;
		
	}
	
	
	public static boolean detectParticleEdge(World world, int pidx, int eidx) {
		
		Vector2f x1 = world.getParticle(pidx).getPosition().copy();
		Vector2f v1 = world.getParticle(pidx).getVelocity().copy();
		Vector2f x2 = world.getEdge(eidx).getFirstParticle().getPosition().copy();
		Vector2f v2 = world.getEdge(eidx).getFirstParticle().getVelocity().copy();
		Vector2f x3 = world.getEdge(eidx).getSecondParticle().getPosition().copy();
		Vector2f v3 = world.getEdge(eidx).getSecondParticle().getVelocity().copy();
		
		float alpha = (x1.copy().sub(x2)).dot(x3.copy().sub(x2)) / (x3.copy().sub(x2)).dot(x3.copy().sub(x2));
		alpha = Math.min(1.0f, Math.max(0.0f, alpha));
		Vector2f closest = x2.copy().add(x3.copy().sub(x2).scale(alpha));
		Vector2f n = closest.copy().sub(x1);
		
		if (n.length() < world.getRadius(pidx) + world.getEdge(eidx).getRadius()) {
			float relativeVel = v1.copy().sub(v2).sub(v3.copy().sub(v2).scale(alpha)).dot(n);
			if (relativeVel > 0) return true;
		}
		return false;
		
	}
	
	
	public static void respondParticleParticle(World world, int idx1, int idx2) {
		
		Vector2f x1 = world.getParticle(idx1).getPosition().copy();
		Vector2f v1 = world.getParticle(idx1).getVelocity().copy();
		Vector2f x2 = world.getParticle(idx2).getPosition().copy();
		Vector2f v2 = world.getParticle(idx2).getVelocity().copy();
		
		float m1 = world.isFixed(idx1) ? Float.MAX_VALUE : world.getParticle(idx1).getMass();
		float m2 = world.isFixed(idx2) ? Float.MAX_VALUE : world.getParticle(idx2).getMass();
		
		float cFactor = (1.0f + COR) / 2.0f;
		Vector2f nhat = x2.copy().sub(x1).normalise();
		float numerator = 2.0f * cFactor * (v2.copy().sub(v1)).dot(nhat);
		float denom1 = 1.0f + m1 / m2;
		float denom2 = m2 / m1 + 1.0f;
		
		if (!world.isFixed(idx1)) world.setVelocity(idx1, v1.add(nhat.copy().scale(numerator / denom1)));
		if (!world.isFixed(idx2)) world.setVelocity(idx2, v2.sub(nhat.copy().scale(numerator / denom2)));
		
	}
	
	
	public static void respondParticleEdge(World world, int pidx, int eidx) {
		
		int eidx1 = world.getEdge(eidx).getFirstIndex();
		int eidx2 = world.getEdge(eidx).getSecondIndex();
		
		Vector2f x1 = world.getParticle(pidx).getPosition().copy();
		Vector2f v1 = world.getParticle(pidx).getVelocity().copy();
		Vector2f x2 = world.getEdge(eidx).getFirstParticle().getPosition().copy();
		Vector2f v2 = world.getEdge(eidx).getFirstParticle().getVelocity().copy();
		Vector2f x3 = world.getEdge(eidx).getSecondParticle().getPosition().copy();
		Vector2f v3 = world.getEdge(eidx).getSecondParticle().getVelocity().copy();
		
		float alpha = (x1.copy().sub(x2)).dot(x3.copy().sub(x2)) / (x3.copy().sub(x2)).dot(x3.copy().sub(x2));
		alpha = Math.min(1.0f, Math.max(0.0f, alpha));
		Vector2f closest = x2.copy().add(x3.copy().sub(x2).scale(alpha));
		Vector2f nhat = closest.copy().sub(x1).normalise();
		Vector2f vEdge = v2.copy().add(v3.copy().sub(v2).scale(alpha));
		
		float cFactor = (1.0f + COR) / 2.0f;
		float m1 = world.isFixed(pidx) ? Float.MAX_VALUE : world.getParticle(pidx).getMass();
		float m2 = world.isFixed(eidx1) ? Float.MAX_VALUE : world.getParticle(eidx1).getMass();
		float m3 = world.isFixed(eidx2) ? Float.MAX_VALUE : world.getParticle(eidx2).getMass();
		float numerator = 2.0f * cFactor * (vEdge.copy().sub(v1)).dot(nhat);
		float denom1 = 1.0f + (1.0f - alpha) * (1.0f - alpha) * m1 / m2 + alpha * alpha * m1 / m3;
		float denom2 = m2 / m1 + (1.0f - alpha) * (1.0f - alpha) + alpha * alpha * m2 / m3;
		float denom3 = m3 / m1 + (1.0f - alpha) * (1.0f - alpha) * m3 / m2 + alpha * alpha;
		
		if (!world.isFixed(pidx)) world.setVelocity(pidx, v1.add(nhat.copy().scale(numerator / denom1)));
		if (!world.isFixed(eidx1)) world.setVelocity(eidx1, v2.sub(nhat.copy().scale((1.0f - alpha) * (numerator / denom2))));
		if (!world.isFixed(eidx2)) world.setVelocity(eidx2, v3.sub(nhat.copy().scale(alpha * (numerator / denom3))));
		
	}
	
}