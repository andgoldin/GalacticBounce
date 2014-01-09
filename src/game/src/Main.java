package game.src;

import java.util.ArrayList;
import java.util.Arrays;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Vector2f;

public class Main extends BasicGame {

	public static int WIDTH = 600, HEIGHT = 600;

	private World world;
	private Player player;
	private Image cursor;
	private ArrayList<Box> stars;
	private int bulletCounter, bulletRate, enemyCounter, enemyRate, starCounter, starRate;
	private int time, timeDist, score, bulletKills, killsPerBomb;
	
	private Color bombColor;
	private Color bombCurrent;
	private int bombCount;
	private boolean bombActivated;

	private boolean gameStart, paused, gameEnd;
	
	private String levelFile;

	public Main(String title, String level) {
		super(title);
		levelFile = level;
	}


	public void init(GameContainer gc) throws SlickException {
		Soundbank.loadAllSounds();
		world = new World();

		world.addParticle(new Particle(Particle.PARTICLE_WORLD, -10, -500, 0, 0, 1, 4, Color.cyan, true));
		world.addParticle(new Particle(Particle.PARTICLE_WORLD, WIDTH +10, -500, 0, 0, 1, 4, Color.cyan, true));
		world.addParticle(new Particle(Particle.PARTICLE_WORLD, WIDTH - 20, HEIGHT - 80, 0, 0, 1, 4, Color.cyan, true));
		world.addParticle(new Particle(Particle.PARTICLE_WORLD, 20, HEIGHT - 80, 0, 0, 1, 4, Color.cyan, true));
		world.addParticle(new Particle(Particle.PARTICLE_WORLD, 200, HEIGHT - 50, 0, 0, 1, 4, Color.cyan, true));
		world.addParticle(new Particle(Particle.PARTICLE_WORLD, 400, HEIGHT - 50, 0, 0, 1, 4, Color.cyan, true));
		world.addEdge(new Edge(0, 3, world.getParticle(0), world.getParticle(3), 4, Color.blue));
		world.addEdge(new Edge(1, 2, world.getParticle(1), world.getParticle(2), 4, Color.blue));
		world.addEdge(new Edge(3, 4, world.getParticle(3), world.getParticle(4), 4, Color.blue));
		world.addEdge(new Edge(2, 5, world.getParticle(2), world.getParticle(5), 4, Color.blue));
		
		world.setLevelFromFile(levelFile);

		world.addForce(new SimpleGravityForce(new Vector2f(0, 500)));
		player = new Player(300, HEIGHT - 50, 0, 0, new Animation(new SpriteSheet("res/img/dude.png", 32, 32), 200), true);
		cursor = new SpriteSheet("res/img/reticle.png", 16, 16).getSubImage(0, 0, 16, 16);
		gc.setMouseCursor(cursor, cursor.getWidth() / 2, cursor.getHeight() / 2);

		stars = new ArrayList<Box>();
		for (int i = 0; i < 100; i++) {
			Vector2f rPos = new Vector2f((float) Math.random() * WIDTH, (float) Math.random() * HEIGHT);
			Vector2f rVel = new Vector2f((float) (Math.random() * 500) + 100, 0);
			stars.add(new Box(rPos.x, rPos.y, 3, 3, rVel.x, rVel.y));
		}
		
		bulletRate = 100;
		bulletCounter = 0;
		enemyRate = 1000;
		enemyCounter = 0;
		bulletKills = 0;
		killsPerBomb = 10;
		
		starRate = 50;
		starCounter = 0;
		
		bombColor = new Color(1.0f, 1.0f, 1.0f, 1.0f);
		bombCurrent = new Color(bombColor);
		bombCount = 2;
		bombActivated = false;

		time = 0;
		timeDist = time;
		score = 0;
		
		gameStart = true;
		paused = false;
		gameEnd = false;
		
		gc.setMusicVolume(0.2f);
		Soundbank.playMusic("music");
	}

	public void update(GameContainer gc, int delta) throws SlickException {

		// clearnup from last frame, dead particles
		for (int i = world.getNumParticles() - 1; i >= 0; i--) {
			if ((world.getParticle(i).getParticleType() == Particle.PARTICLE_BULLET
					|| world.getParticle(i).getParticleType() == Particle.PARTICLE_ENEMY)
					&& world.getParticle(i).getHealth() <= 0) {
				world.removeParticle(i);
			}
		}
		
		// user input
		Input in = gc.getInput();
		if (in.isKeyPressed(Input.KEY_ESCAPE) || (gameEnd && in.isKeyPressed(Input.KEY_SPACE))) {
			gc.exit();
			System.exit(0);
		}
		
		if (gameStart) {
			if (in.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				gameStart = false;
				Soundbank.playSound("gamestart");
			}
			return;
		}
		
		gameEnd = timeDist == 100;
		if (gameEnd) return;
		

		if (!paused) {
			
			if (in.isKeyPressed(Input.KEY_R)) {
				player.getAnimation().stop();
				Soundbank.pauseSound("music");
				paused = true;
			}
			
			if (bombActivated) {
				bombCurrent.a -= 0.03f / (float) delta;
				if (bombCurrent.a <= 0.0f) {
					bombActivated = false;
				}
			}
			
			if (in.isKeyPressed(Input.KEY_SPACE) && bombCount > 0) {
				bombCurrent = new Color(bombColor);
				bombActivated = true;
				bombCount--;
				for (int i = 0; i < world.getNumParticles(); i++) {
					if (world.getParticle(i).getParticleType() == Particle.PARTICLE_ENEMY) {
						world.getParticle(i).setHealth(0);
						score += 100;
					}
				}
				Soundbank.playSound("bomb");
			}
			
			if (in.isKeyDown(Input.KEY_W) && player.getPosition().y > player.getHeight() / 2) {
				player.setVelocity(new Vector2f(player.getVelocity().x, -500));
			}
			else if (in.isKeyDown(Input.KEY_S) && player.getPosition().y < HEIGHT - player.getHeight() / 2) {
				player.setVelocity(new Vector2f(player.getVelocity().x, 500));
			}
			else {
				player.setVelocity(new Vector2f(player.getVelocity().x, 0));
			}
			if (in.isKeyDown(Input.KEY_A) && player.getPosition().x > player.getWidth() / 2) {
				player.setVelocity(new Vector2f(-500, player.getVelocity().y));
			}
			else if (in.isKeyDown(Input.KEY_D) && player.getPosition().x < WIDTH - player.getWidth() / 2) {
				player.setVelocity(new Vector2f(500, player.getVelocity().y));
			}
			else {
				player.setVelocity(new Vector2f(0, player.getVelocity().y));
			}
			if (bulletCounter >= bulletRate && in.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				float bulletRad = 3, bulletSpeed = 800;
				float bulletX = player.getPosition().x;
				float bulletY = player.getPosition().y;
				float dx = in.getMouseX() - bulletX;
				float dy = in.getMouseY() - bulletY;
				Particle bullet = new Particle(Particle.PARTICLE_BULLET, bulletX, bulletY, 0, 0, 10, bulletRad, Color.yellow, false);
				bullet.setVelocity(new Vector2f(dx, dy).normalise().scale(bulletSpeed));
				world.addParticle(bullet);
				bulletCounter = 0;
				Soundbank.playSound("shootbullet");
			}
			else {
				bulletCounter += delta;
			}
			
			if (enemyCounter >= enemyRate) {
				float enemyRad = (float) (Math.random() * 20) + 10;
				float enemyMass = (float) (Math.random() * 50) + 10;
				float enemyX = (float) (Math.random() * (WIDTH - 100)) + 50;
				Particle enemy = new Particle(Particle.PARTICLE_ENEMY, enemyX, -400, 0, 0, enemyMass, enemyRad, Color.red, false);
				world.addParticle(enemy);
				enemyCounter = 0;
			}
			else {
				enemyCounter += delta;
			}

			// detect particle collisions
			for (int i = 0; i < world.getNumParticles(); i++) {
				for (int j = i + 1; j < world.getNumParticles(); j++) {
					if (j != i && CollisionHandler.detectParticleParticle(world, i, j)) {
						if (world.getParticle(i).getParticleType() == Particle.PARTICLE_BULLET
								&& world.getParticle(j).getParticleType() == Particle.PARTICLE_ENEMY) {
							world.getParticle(j).setHealth(world.getParticle(j).getHealth() - 1);
							world.getParticle(j).setColor(world.getParticle(j).getColor().r - 1.0f / world.getParticle(j).getMaxHealth(),
									world.getParticle(j).getColor().g, world.getParticle(j).getColor().b);
							if (world.getParticle(j).getHealth() == 0) {
								score += 100;
								bulletKills++;
								if (bulletKills % killsPerBomb == 0) {
									bulletKills = 0;
									bombCount++;
									Soundbank.playSound("bombget");
								}
							}
							Soundbank.playSound("bounce");
						}
						else if (world.getParticle(j).getParticleType() == Particle.PARTICLE_BULLET
								&& world.getParticle(i).getParticleType() == Particle.PARTICLE_ENEMY) {
							world.getParticle(i).setHealth(world.getParticle(i).getHealth() - 1);
							world.getParticle(i).setColor(world.getParticle(i).getColor().r - 1.0f / world.getParticle(i).getMaxHealth(),
									world.getParticle(i).getColor().g, world.getParticle(i).getColor().b);
							if (world.getParticle(i).getHealth() == 0) {
								score += 100;
								bulletKills++;
								if (bulletKills % killsPerBomb == 0) {
									bulletKills = 0;
									bombCount++;
									Soundbank.playSound("bombget");
								}
							}
							Soundbank.playSound("bounce");
						}
						CollisionHandler.respondParticleParticle(world, i, j);
					}
				}
				for (int j = 0; j < world.getNumEdges(); j++) {
					if (i != world.getEdge(j).getFirstIndex() && i != world.getEdge(j).getSecondIndex()
							&& CollisionHandler.detectParticleEdge(world, i, j)) {
						CollisionHandler.respondParticleEdge(world, i, j);
					}
				}
			}

			// particles that fall past the bottom are dead
			for (int i = 0; i < world.getNumParticles(); i++) {
				if ((world.getParticle(i).getParticleType() == Particle.PARTICLE_BULLET
						|| world.getParticle(i).getParticleType() == Particle.PARTICLE_ENEMY)
						&& world.getParticle(i).y() > HEIGHT) {
					world.getParticle(i).setHealth(0);
					if (world.getParticle(i).getParticleType() == Particle.PARTICLE_ENEMY) {
						score = score > 50 ? score - 50 : 0;
					}
				}
			}

			// timestep using symplectic euler
			float dt = delta / 1000.0f;
			Vector2f[] f = new Vector2f[world.getNumParticles()];
			Arrays.fill(f, new Vector2f(0, 0));
			world.accumulateGradU(f);
			for (int i = 0; i < f.length; i++) f[i] = f[i].scale(-1.0f);
			for (int i = 0; i < world.getNumParticles(); i++) {
				if (world.isFixed(i)) f[i] = f[i].set(0, 0);
				f[i] = f[i].scale(1.0f / world.getParticle(i).getMass());
				world.setVelocity(i, world.getParticle(i).getVelocity().add(f[i].scale(dt)));
				world.setPosition(i, world.getParticle(i).getPosition().add(world.getParticle(i).getVelocity().scale(dt)));
			}

			// update player
			player.setPosition(player.getPosition().add(player.getVelocity().scale(dt)));
			
			// update stars
			if (starCounter >= starRate) {
				Vector2f rPos = new Vector2f(-10, (float) Math.random() * HEIGHT);
				Vector2f rVel = new Vector2f((float) (Math.random() * 500) + 100, 0);
				stars.add(new Box(rPos.x, rPos.y, 3, 3, rVel.x, rVel.y));
				starCounter = 0;
			}
			else {
				starCounter += delta;
			}
			for (int i = 0; i < stars.size(); i++) {
				stars.get(i).setPosition(stars.get(i).getPosition().x + stars.get(i).getVelocity().x * dt, stars.get(i).getPosition().y);
				if (stars.get(i).getPosition().x > 610) stars.remove(i);
			}

			//System.out.println("num particles: " + world.getNumParticles());
			time += delta;
			if (time >= 1000) {
				timeDist++;
				time = 0;
			}
		}
		else {
			if (in.isKeyPressed(Input.KEY_R)) {
				paused = false;
				player.getAnimation().start();
				Soundbank.playMusic("music");
			}
		}

	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		Renderer.setBackgroundColor(g, Color.darkGray);
		Renderer.renderStars(g, stars);
		Renderer.renderEdges(g, world.getEdges());
		Renderer.renderParticles(g, world.getParticles());
		Renderer.renderPlayer(g, player, gc.getInput().getMouseX(), gc.getInput().getMouseY());
		if (bombActivated) Renderer.renderBomb(g, bombCurrent);
		Renderer.drawText(g, "TIME   " + timeDist, 10, HEIGHT - 40);
		Renderer.drawText(g, "SCORE  " + score, 10, HEIGHT - 25);
		Renderer.drawText(g, "BOMB   " + bombCount, 500, HEIGHT - 40);
		Renderer.renderBombMeter(g, 490, HEIGHT - 20, 100, 15, (int) (100 * bulletKills / 10));
		if (gameStart) Renderer.renderStartScreen(g);
		if (paused) Renderer.renderPause(g);
		if (gameEnd) Renderer.renderEndScreen(g, score);
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new Main("GALACTIC BOUNCE", args.length == 0 ? "res/levels/default.level" : args[0]));
		app.setDisplayMode(WIDTH, HEIGHT, false);
		//app.setTargetFrameRate(120);
		app.start();
	}


}