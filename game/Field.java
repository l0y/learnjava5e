package game;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

public class Field extends JComponent implements ActionListener {
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
    int myScore = 0;
    String[] scores = new String[3];
    JLabel[] scoreLabels = new JLabel[3];
    List<Apple> apples = Collections.synchronizedList(new ArrayList<>());
    List<Tree> trees = Collections.synchronizedList(new ArrayList<>());

    boolean animating = false;
    Thread animationThread;
    Timer animationTimer;

    protected void paintComponent(Graphics g) {
        g.setColor(fieldColor);
        g.fillRect(0,0, getWidth(), getHeight());
        physicist.draw(g);
        for (Tree t : trees) {
            t.draw(g);
        }
        for (Apple a : apples) {
            a.draw(g);
        }
    }

    public void setPlayer(Physicist p) {
        physicist = p;
    }

    public void actionPerformed(ActionEvent event) {
        if (animating && event.getActionCommand().equals("repaint")) {
            for (Apple a : apples) {
                a.step();
                detectCollisions(a);
            }
            repaint();
            cullFallenApples();
        }
    }

    /**
     * Toss an apple from the given physicist using that physicist's aim and force.
     * Make sure the field is in the animating state.
     *
     * @param physicist the player whose apple should be tossed
     */
    public void startTossFromPlayer(Physicist physicist) {
        if (!animating) {
            System.out.println("Starting animation!");
            animating = true;
            startAnimation();
        }
        if (animating) {
            // Check to make sure we have an apple to toss
            if (physicist.aimingApple != null) {
                Apple apple = physicist.takeApple();
                apple.toss(physicist.aimingAngle, physicist.aimingForce);
                apples.add(apple);
                Timer appleLoader = new Timer(800, physicist);
                appleLoader.setActionCommand("New Apple");
                appleLoader.setRepeats(false);
                appleLoader.start();
            }
        }
    }

    void cullFallenApples() {
        Iterator<Apple> iterator = apples.iterator();
        while (iterator.hasNext()) {
            Apple a = iterator.next();
            if (a.getCollidedPiece() != null) {
                GamePiece otherPiece = a.getCollidedPiece();
                if (otherPiece instanceof Physicist) {
                    hitPhysicist((Physicist) otherPiece);
                } else if (otherPiece instanceof Tree) {
                    hitTree((Tree) otherPiece);
                }
                // Remove ourselves. If the other piece we hit was an apple, leave it alone.
                // It will be removed when the iterator comes to it.
                iterator.remove();
            } else if (a.getPositionY() > 600) {
                System.out.println("Culling apple");
                iterator.remove();
            }
        }
        if (apples.size() <= 0) {
            animating = false;
            if (animationTimer != null && animationTimer.isRunning()) {
                animationTimer.stop();
            }
        }
    }

    void detectCollisions(Apple apple) {
        // Check for other apples
        for (Apple a : apples) {
            if (apple.isTouching(a)) {
                System.out.println("Touching another apple!");
                return;
            }
        }
        // Check our physicist
        if (apple.isTouching(physicist)) {
            System.out.println("Touching a physicist!");
            return;
        }

        // Check for trees
        for (Tree t : trees) {
            if (apple.isTouching(t)) {
                System.out.println("Touching a tree!");
                return;
            }
        }
    }

    void hitPhysicist(Physicist physicist) {
        // do any scoring or notifications here
    }

    void hitTree(Tree tree) {
        // do any scoring or notifications here
        myScore += 10;
        trees.remove(tree);
        setScore(1, String.valueOf(myScore));
    }

    void startAnimation() {
        // Animator myAnimator = new Animator();
        // animationThread = new Thread(myAnimator);
        // animationThread.start();
        if (animationTimer == null) {
            animationTimer = new Timer(STEP, this);
            animationTimer.setActionCommand("repaint");
            animationTimer.setRepeats(true);
            animationTimer.start();
        } else if (!animationTimer.isRunning()) {
            animationTimer.restart();
        }
    }

    public String getScore(int playerNumber) {
        return scores[playerNumber];
    }

    public void setScore(int playerNumber, String score) {
        scores[playerNumber] = score;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                String newScore = " Player " + playerNumber + ": " + score;
                scoreLabels[playerNumber].setText(newScore);
            }
        });
    }

    public String getWinner() {
        int score2 = -1;
        try {
            score2 = Integer.parseInt(scores[2]);
        } catch (NumberFormatException nfe) {
            System.err.println("Couldn't parse the other player's score: " + scores[2]);
        }
        if (myScore == score2) {
            return "It's a tie!";
        } else if (myScore > score2) {
            return "You won!";
        } else {
            return "They won.";
        }
    }

    /**
     * Helper method to return a good x value for a tree so it's not off the left or right edge.
     *
     * @return x value within the bounds of the playing field width
     */
    private int goodX() {
        // at least half the width of the tree plus a few pixels
        int leftMargin = TREE_WIDTH_IN_PIXELS / 2 + 5;
        // now find a random number between a left and right margin
        int rightMargin = AppleToss.FIELD_WIDTH - leftMargin;

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
        int topMargin = TREE_WIDTH_IN_PIXELS / 2 + 5;
        // a little higher off the bottom
        int bottomMargin = AppleToss.FIELD_HEIGHT - TREE_HEIGHT_IN_PIXELS;

        // And return a random number starting at the top margin but not past the bottom
        return topMargin + random.nextInt(bottomMargin - topMargin);
    }

    public void setupNewGame() {
        // Clear out any old trees
        trees.clear();

        // Now create some trees for target practice
        for (int i = trees.size(); i < Field.MAX_TREES; i++) {
            Tree t = new Tree();
            t.setPosition(goodX(), goodY());
            // Trees can be close to each other and overlap, but they shouldn't intersect our physicist
            while(physicist.isTouching(t)) {
                // We do intersect this tree, so let's try again
                t.setPosition(goodX(), goodY());
                System.err.println("Repositioning an intersecting tree...");
            }
            trees.add(t);
        }
        repaint();
    }

    class Animator implements Runnable {
        public void run() {
            while (animating) {
                System.out.println("Stepping " + apples.size() + " apples");
                for (Apple a : apples) {
                    a.step();
                    detectCollisions(a);
                }
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        Field.this.repaint();
                    }
                });
                cullFallenApples();
                try {
                    Thread.sleep((int)(STEP * 1000));
                } catch (InterruptedException ie) {
                    System.err.println("Animation interrupted");
                    animating = false;
                }
            }
        }
    }
}
