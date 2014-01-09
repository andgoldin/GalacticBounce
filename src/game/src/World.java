package game.src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

public class World {

	private ArrayList<Particle> particles;
	private ArrayList<Edge> edges;
	private ArrayList<Force> forces;
	
	public World() {
		particles = new ArrayList<Particle>();
		edges = new ArrayList<Edge>();
		forces = new ArrayList<Force>();
	}
	
	public int getNumParticles() {
		return particles.size();
	}
	
	public int getNumEdges() {
		return edges.size();
	}
	
	public ArrayList<Particle> getParticles() {
		return particles;
	}
	
	public Particle getParticle(int particleIndex) {
		return particles.get(particleIndex);
	}
	
	public ArrayList<Edge> getEdges() {
		return edges;
	}
	
	public Edge getEdge(int edgeIndex) {
		return edges.get(edgeIndex);
	}
	
	public void setPosition(int particleIndex, Vector2f pos) {
		particles.get(particleIndex).setPosition(pos);
	}
	
	public void setVelocity(int particleIndex, Vector2f vel) {
		particles.get(particleIndex).setVelocity(vel);
	}
	
	public void setMass(int particleIndex, float mass) {
		particles.get(particleIndex).setMass(mass);
	}
	
	public boolean isFixed(int particleIndex) {
		return particles.get(particleIndex).isFixed();
	}
	
	public float getRadius(int particleIndex) {
		return particles.get(particleIndex).getRadius();
	}
	
	public void setRadius(int particleIndex, float radius) {
		particles.get(particleIndex).setRadius(radius);
	}
	
	public void addParticle(Particle p) {
		particles.add(p);
	}
	
	public void addEdge(Edge e) {
		edges.add(e);
	}
	
	public void addForce(Force f) {
		forces.add(f);
	}
	
	public void removeParticle(int particleIndex) {
		particles.remove(particleIndex);
	}
	
	public void accumulateGradU(Vector2f[] frcs) {
		for (int i = 0; i < forces.size(); i++) {
			forces.get(i).addGradEToTotal(particles, frcs);
		}
	}
	
	public float computeKineticEnergy() {
		float ke = 0;
		for (int i = 0; i < particles.size(); i++) {
			ke += particles.get(i).getMass() * particles.get(i).getVelocity().dot(particles.get(i).getVelocity());
		}
		return ke;
	}
	
	public float computePotentialEnergy() {
		float pe = 0;
		for (int i = 0; i < forces.size(); i++) {
			forces.get(i).addEnergyToTotal(particles, pe);
		}
		return pe;
	}

	public float computeTotalEnergy() {
		return computeKineticEnergy() + computePotentialEnergy();
	}
	
	public void setLevelFromFile(String filename) {
		Scanner read = null;
		try {
			read = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int currentParticle = getNumParticles();
		while (read.hasNextLine()) {
			String line = read.nextLine();
			String[] points = line.split("\\;");
			int numPoints = points.length;
			for (int i = 0; i < numPoints; i++) {
				String[] coords = points[i].split("\\,");
				addParticle(new Particle(Particle.PARTICLE_WORLD, Float.parseFloat(coords[0]), Float.parseFloat(coords[1]), 0, 0, 1, 4, Color.cyan, true));
			}
			for (int i = 0; i < numPoints - 1; i++) {
				addEdge(new Edge(currentParticle + i, currentParticle + i + 1, getParticle(currentParticle + i), getParticle(currentParticle + i + 1), 4, new Color(0.1f, 0.5f, 0.8f)));
			}
			currentParticle += numPoints;
		}
		read.close();
	}
	
}
