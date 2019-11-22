package ch08;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

/**
 * The playing field for our game. Now we can setup some constants for
 * other game classes to use and create member variables for our player and some trees.
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
    Random random = new Random();

    // ArrayList covered in Generics chapter
    // synchronizedArrayList covered in Threads chapter
    Physicist physicist;
    List<Tree> trees = Collections.synchronizedList(new ArrayList<>());

    protected void paintComponent(Graphics g) {
        g.setColor(fieldColor);
        g.fillRect(0,0, getWidth(), getHeight());
        for (Tree t : trees) {
            t.draw(g);
        }
        physicist.draw(g);
    }

    public void setPlayer(Physicist p) {
        physicist = p;
    }
	
	/**
	 * Create and add a new tree to the field.
	 *
	 * @param x The X-coordinate for the tree
	 * @param y The Y-coordinate for the tree
	 */
    public void addTree(int x, int y) {
        Tree tree = new Tree();
        tree.setPosition(x,y);
        trees.add(tree);
    }
	
	/**
	 * Add an existing tree to our field. Useful if the position
	 * of the tree has already been set.
	 *
	 * @param t The existing tree to add
	 */
	public void addTree(Tree t) {
		trees.add(t);
	}
}
