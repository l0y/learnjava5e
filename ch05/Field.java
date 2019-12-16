package ch05;

import javax.swing.*;
import java.awt.*;

/**
 * The playing field for our game. This class will be undergoing quite a few
 * changes as we learn about more of Java's features in coming chapters.
 * For now, we can demonstrate how to work with member variables like a1 and a2
 * as well as how to create new methods like setupApples() and detectCollisions().
 */
public class Field extends JComponent {
    public static final float GRAVITY = 9.8f;  // feet per second per second
    public static final int STEP = 40;   // duration of an animation frame in milliseconds
    public static final int APPLE_SIZE_IN_PIXELS = 30;
    public static final int TREE_WIDTH_IN_PIXELS = 60;
    public static final int TREE_HEIGHT_IN_PIXELS = 2 * TREE_WIDTH_IN_PIXELS;
    public static final int PHYSICIST_SIZE_IN_PIXELS = 75;
    public static final int MAX_TREES = 12;

    Color fieldColor = Color.GRAY;

    Apple a1 = new Apple();
    Apple a2 = new Apple();
	Tree tree = new Tree();
	Physicist physicist;

    public void setupApples() {
		// For now, just play with our apple attributes directly
        a1.diameter = 3.0f;
        a1.mass = 5.0f;
        a1.x = 20;
        a1.y = 40;
        a2.diameter = 8.0f;
        a2.mass = 10.0f;
        a2.x = 70;
        a2.y = 200;
    }
	
	public void setupTree() {
		// Unlike apples, we'll use the setPosition() method from our
		// GamePiece interface to setup our lonely tree
		tree.setPosition(500,200);
	}

    public void setPlayer(Physicist p) {
        physicist = p;
    }
	
    protected void paintComponent(Graphics g) {
        g.setColor(fieldColor);
        g.fillRect(0,0, getWidth(), getHeight());
        tree.draw(g);
        physicist.draw(g);
		a1.draw(g);
		a2.draw(g);
    }

    public void detectCollisions() {
        if (a1.isTouching(a2)) {
            System.out.println("Collision detected!");
        } else {
            System.out.println("Apples are not touching.");
        }
    }
}
