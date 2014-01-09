Welcome to GALACTIC BOUNCE!

Galactic Bounce (working title) is a simple arcade-style game! Many bouncing balls fall into an arena filled with angled walls, and make their way to the bottom of the screen. You play as an orange blinking square that shoots small projectiles. The goal is to destroy as many falling balls as possible within the time limit of 100 seconds by pelting them with your projectiles. You gain points for every ball you destroy, and lose points for every ball that makes it to the bottom of the screen. Every 10 balls you destroy will net you a bomb, which upon use will destroy all of the balls on screen, getting you lots of bonus points.

The controls are as follows:
- W, S, A, D keys: Move the player up, down, left, and right, respectively.
- Mouse: Move the mouse to aim the shooting trajectory, left click to fire. Hold down the left button for rapid fire.
- Spacebar: Use a bomb.

To run the game:
- Make sure you have a Java runtime (6 or later) installed on your computer.
- Simply execute galactic_linux.sh.
- If that does not work, then run the provided .jar file (galactic_bounce.jar).

Custom levels:
- To use your own custom level in the game, open up a text editor and edit the file /res/levels/default.level.
- Each line of text in the level file denotes the coordinates of each endpoint of an obstacle segment. For example, the line...
    430,220;300,190;170,220
  ...places an obstacle with two segments in the stage defined by the three coordinates (430, 220), (300, 190), and (170, 220).
- Once you have your obstacle coordinates set, save the file and re-run the game!




Created by Andrew Goldin, 2013 (andgoldin.github.io)
Using Java and Slick2D (slick.ninjacave.com)