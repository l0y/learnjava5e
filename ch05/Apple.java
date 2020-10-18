package ch05;

import java.awt.*;

/**
 * Apple
 *
 * This class sums up everything we know about the apples our physicists will be lobbing.
 * We keep the size and weight details. We'll expand this class as we cover more topics.
 */
public class Apple implements GamePiece {
    float mass;
    float diameter = 1.0f;
    int x, y;
    int size;

    // Setup some size constants
    public static final int SMALL = 0;
    public static final int MEDIUM = 1;
    public static final int LARGE = 2;

    // Some helpers for optimizing the draw() method that can be called many, many times
    int centerX, centerY;
    int scaledLength;

    // Boundary helper for optimizing collision detection with physicists and trees
    Rectangle boundingBox;

    // If we bumped into something, keep a reference to that thing around for cleanup and removal
    GamePiece collided;

    /**
     * Create a default, Medium apple
     */
    public Apple() {
        this(MEDIUM);
    }

    /**
     * Create an Apple of the given size
     */
    public Apple(int size) {
        setSize(size);
    }

    /**
     * Sets the size (and dependent properties) of the apple based on the
     * supplied value which must be one of the size constants.
     *
     * @param size one of SMALL, MEDIUM, or LARGE, other values are bounded to SMALL or LARGE
     */
    public void setSize(int size) {
        if (size < SMALL) {
            size = SMALL;
        }
        if (size > LARGE) {
            size = LARGE;
        }
        this.size = size;
        switch (size) {
            case SMALL:
                diameter = 0.9f;
                mass = 0.5f;
                break;
            case MEDIUM:
                diameter = 1.0f;
                mass = 1.0f;
                break;
            case LARGE:
                diameter = 1.1f;
                mass = 1.8f;
                break;
        }
        // fillOval() used below draws an oval bounded by a box, so figure out the length of the sides.
        // Since we want a circle, we simply make our box a square so we only need one length.
        scaledLength = (int)(diameter * Field.APPLE_SIZE_IN_PIXELS + 0.5);
        boundingBox = new Rectangle(x, y, scaledLength, scaledLength);
    }

    public double getDiameter() {
        return diameter;
    }

    @Override
    public void setPosition(int x, int y) {
        // Our position is based on the center of the apple, but it will be drawn from the
        // upper left corner, so figure out the distance of that gap
        int offset = (int)(diameter * Field.APPLE_SIZE_IN_PIXELS / 2);

        this.centerX = x;
        this.centerY = y;
        this.x = x - offset;
        this.y = y - offset;
        boundingBox = new Rectangle(x, y, scaledLength, scaledLength);
    }

    @Override
    public int getPositionX() {
        return centerX;
    }

    @Override
    public int getPositionY() {
        return centerY;
    }

    @Override
    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    @Override
    public void draw(Graphics g) {
        // Make sure our apple will be red, then paint it!
        g.setColor(Color.RED);
        g.fillOval(x, y, scaledLength, scaledLength);
    }

    /**
     * Determine whether or not this apple is touching another piece.
     */
    public boolean isTouching(GamePiece other) {
        double xdiff = x - other.getPositionX();
        double ydiff = y - other.getPositionY();
        double distance = Math.sqrt(xdiff * xdiff + ydiff * ydiff);
        // For now, we can only really collide with other apples.
        // Just cheat a little and assume the other piece is in fact
        // an apple, and an apple with the same diameter. We'll fix
        // this as we fill out the other game pieces in later chapters.
        if (distance < diameter) {
            return true;
        } else {
            return false;
        }
    }

    public void printDetails() {
        System.out.println("  mass: " + mass);
        // Print the exact diameter:
        //System.out.println("  diameter: " + diameter);
        // Or a nice, human-friendly approximate:
        String niceNames[] = getAppleSizes();
        if (diameter < 5.0f) {
            System.out.println(niceNames[SMALL]);
        } else if (diameter < 10.0f) {
            System.out.println(niceNames[MEDIUM]);
        } else {
            System.out.println(niceNames[LARGE]);
        }
        System.out.println("  position: (" + x + ", " + y +")");
    }

    public static String[] getAppleSizes() {
        // Return names for our constants
        // The index of the name should match the value of the constant
        return new String[] { "SMALL", "MEDIUM", "LARGE" };
    }
}
