package ch09.game;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

/**
 * Our apple tossing game. This class extends JFrame to create our
 * main application window. We can now demonstrate one apple being
 * tossed. (The ability for the player to aim and throw on demand
 * will be covered in Chapter 10.)
 */
public class AppleToss extends JFrame {

    public static final int FIELD_WIDTH = 800;
    public static final int FIELD_HEIGHT = 500;

    Field field = new Field();
    Physicist player1 = new Physicist();
    ArrayList<Physicist> otherPlayers = new ArrayList<>();

    Random random = new Random();

    public AppleToss() {
        // Create our frame
        super("Apple Toss Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(FIELD_WIDTH,FIELD_HEIGHT + 20);

        // Build the field with our player and some trees
        setupFieldForOnePlayer();
        add(field);
    }

    /**
     * Helper method to return a good x value for a tree so it's not off the left or right edge.
     *
     * @return x value within the bounds of the playing field width
     */
    private int goodX() {
        // at least half the width of the tree plus a few pixels
        int leftMargin = Field.TREE_WIDTH_IN_PIXELS / 2 + 5;
        // now find a random number between a left and right margin
        int rightMargin = FIELD_WIDTH - leftMargin;

        // And return a random number starting at the left margin
        return leftMargin + random.nextInt(rightMargin - leftMargin);
    }

    /**
     * Helper method to return a good y value for a tree so it's not off the top or bottom of the screen.
     *
     * @return y value within the bounds of the playing field height
     */
    private int goodY() {
        // at least half the height of the "leaves" plus a few pixels
        int topMargin = Field.TREE_WIDTH_IN_PIXELS / 2 + 5;
        // a little higher off the bottom
        int bottomMargin = FIELD_HEIGHT - Field.TREE_HEIGHT_IN_PIXELS;

        // And return a random number starting at the top margin but not past the bottom
        return topMargin + random.nextInt(bottomMargin - topMargin);
    }

    /**
     * A helper method to populate a one player field with target trees.
     */
    private void setupFieldForOnePlayer() {
        // place our (new) physicist in the lower left corner
        if (field.physicists.size() == 0) {
            player1.setPosition(Field.PHYSICIST_SIZE_IN_PIXELS, FIELD_HEIGHT - (int) (Field.PHYSICIST_SIZE_IN_PIXELS * 1.5));
            field.physicists.add(player1);
            player1.setField(field);
        }

        // Create some trees for target practice
        for (int i = field.trees.size(); i < 10; i++) {
            Tree t = new Tree();
            t.setPosition(goodX(), goodY());
            // Trees can be close to each other and overlap, but they shouldn't intersect our physicist
            while(player1.isTouching(t)) {
                // We do intersect this tree, so let's try again
                t.setPosition(goodX(), goodY());
                System.err.println("Repositioning an intersecting tree...");
            }
            field.trees.add(t);
        }
    }

    public static void main(String args[]) {
        AppleToss game = new AppleToss();
        game.setVisible(true);
        try {
            game.player1.setAimingAngle(45.0f);
            game.field.repaint();
            Thread.sleep(1000);
            game.field.startTossFromPlayer(game.player1);
        } catch (InterruptedException ie) {
            System.err.println("Interrupted during initial pause before tossing an apple.");
        }
    }
}
