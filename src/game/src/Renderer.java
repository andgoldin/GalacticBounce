package game.src;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Renderer {

	public static final Color SHADOW_COLOR = new Color(0.15f, 0.15f, 0.15f);
	public static final float SHADOW_DISTANCE = 2.0f;
	
	public static void setBackgroundColor(Graphics g, Color c) {
		g.setBackground(c);
	}
	
	public static void drawText(Graphics g, String text, float x, float y) {
		g.setColor(Color.white);
		g.drawString(text, x, y);
	}
	
	public static void renderParticle(Graphics g, Particle p) {
		g.setColor(SHADOW_COLOR);
		g.fillOval(p.x() - p.getRadius() + SHADOW_DISTANCE, p.y() - p.getRadius() + SHADOW_DISTANCE, 2.0f * p.getRadius(), 2.0f * p.getRadius());
		
		g.setColor(p.getColor());
		g.fillOval(p.x() - p.getRadius(), p.y() - p.getRadius(), 2.0f * p.getRadius(), 2.0f * p.getRadius());
	}
	
	public static void renderEdge(Graphics g, Edge e) {
		// rotate particles to be level, draw rectangle, rotate back
		g.rotate(e.getFirstParticle().x(), e.getFirstParticle().y(), e.computeAngle());
		g.setColor(SHADOW_COLOR);
		g.fillRect(e.getFirstParticle().x(), e.getFirstParticle().y() - e.getRadius() - SHADOW_DISTANCE, e.length(), 2.0f * e.getRadius() - SHADOW_DISTANCE);
		
		g.setColor(e.getColor());
		g.fillRect(e.getFirstParticle().x(), e.getFirstParticle().y() - e.getRadius(), e.length(), 2.0f * e.getRadius());
		g.rotate(e.getFirstParticle().x(), e.getFirstParticle().y(), -e.computeAngle());
	}
	
	public static void renderParticles(Graphics g, ArrayList<Particle> particles) {
		for (int i = 0; i < particles.size(); i++) {
			renderParticle(g, particles.get(i));
		}
	}
	
	public static void renderEdges(Graphics g, ArrayList<Edge> edges) {
		for (int i = 0; i < edges.size(); i++) {
			renderEdge(g, edges.get(i));
		}
	}
	
	public static void renderPlayer(Graphics g, Player p, float x, float y) {
		g.setColor(Color.orange);
		g.drawLine(p.getPosition().x, p.getPosition().y, x, y);
		g.drawAnimation(p.getAnimation(), p.getPosition().x - p.getWidth() / 2.0f, p.getPosition().y - p.getHeight() / 2.0f);
	}
	
	public static void renderBomb(Graphics g, Color c) {
		g.setColor(c);
		g.fillRect(0, 0, 600, 600);
	}
	
	public static void renderBombMeter(Graphics g, int x, int y, int w, int h, int amountFilled) {
		g.setColor(Color.green);
		g.fillRect(x, y, amountFilled, h);
		g.setColor(Color.black);
		g.drawRect(x, y, w, h);
	}
	
	public static void renderStars(Graphics g, ArrayList<Box> stars) {
		for (int i = 0; i < stars.size(); i++) {
			renderStar(g, stars.get(i));
		}
	}
	
	public static void renderStar(Graphics g, Box star) {
		//g.setColor(SHADOW_COLOR);
		//g.fillRect(star.getPosition().x + SHADOW_DISTANCE, star.getPosition().y + SHADOW_DISTANCE, star.getWidth(), star.getHeight());
		Color temp = star.getColor();
		temp.a = 0.8f;
		g.setColor(temp);
		g.fillRect(star.getPosition().x, star.getPosition().y, star.getWidth(), star.getHeight());
	}
	
	public static void renderStartScreen(Graphics g) {
		g.setColor(new Color(0.1f, 0.1f, 0.1f, 0.8f));
		g.fillRect(0, 0, 600, 600);
		g.setColor(Color.white);
		g.drawString("CLICK TO START", 250, 300);
	}
	
	public static void renderEndScreen(Graphics g, int score) {
		g.setColor(new Color(0.1f, 0.1f, 0.1f, 0.8f));
		g.fillRect(0, 0, 600, 600);
		g.setColor(Color.white);
		g.drawString("TIME UP!!\nYOUR SCORE: " + score + "\n\nPRESS SPACE TO EXIT", 200, 300);
	}
	
	public static void renderPause(Graphics g) {
		g.setColor(new Color(0.1f, 0.1f, 0.1f, 0.8f));
		g.fillRect(0, 0, 600, 600);
		g.setColor(Color.white);
		g.drawString("PAUSED", 250, 300);
	}
	
}
