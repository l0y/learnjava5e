/**
 * Apple
 *
 * This class sums up everything we know about the apples our physicists will be lobbing.
 * We keep the size and weight details and provide a few simple methods for lobbing. We
 * also provide animation helpers for use with the PlayingField class.
 */
package game;

import java.awt.*;

public class Apple implements GamePiece {
    public static final int SMALL  = 0;
    public static final int MEDIUM = 1;
    public static final int LARGE  = 2;

    int size;
    double diameter;
    double mass;
    int centerX, centerY;
    Physicist myPhysicist;

    // In game play, apples can be thrown so track their velocities
    long lastStep;
    float velocityX, velocityY;

    // Some helpers for optimizing the draw() method that can be called many, many times
    int x, y;
    int scaledLength;

    // Boundary helper for optimizing collision detection with physicists and trees
    Rectangle boundingBox;

    // If we bumped into something, keep a reference to that thing around for cleanup and removal
    GamePiece collided;

    /**
     * Create a default, Medium apple
     */
    public Apple(Physicist owner) {
        this(owner, MEDIUM);
    }

    /**
     * Create an Apple of the given size
     */
    public Apple(Physicist owner, int size) {
        myPhysicist = owner;
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
                diameter = 0.9;
                mass = 0.5;
                break;
            case MEDIUM:
                diameter = 1.0;
                mass = 1.0;
                break;
            case LARGE:
                diameter = 1.1;
                mass = 1.8;
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

    @Override
    public boolean isTouching(GamePiece otherPiece) {
        if (this == otherPiece || myPhysicist == otherPiece || collided != null) {
            // By definition we don't collide with ourselves, our physicist, or with more than one other piece
            return false;
        }
        if (otherPiece instanceof Apple) {
            // The other piece is an apple, so we can do a simple distance calculation using
            // the diameters of both apples.
            Apple otherApple = (Apple) otherPiece;
            int v = this.y - otherPiece.getPositionY(); // vertical difference
            int h = this.x - otherPiece.getPositionX(); // horizontal difference
            double distance = Math.sqrt(v * v + h * h);

            double myRadius = diameter * Field.APPLE_SIZE_IN_PIXELS / 2;
            double otherRadius = otherApple.getDiameter() * Field.APPLE_SIZE_IN_PIXELS / 2;
            if (distance < (myRadius + otherRadius)) {
                // Since apples track collisions, we'll update the other apple to keep everyone in sync
                setCollided(otherPiece);
                otherApple.setCollided(this);
                return true;
            }
            return false;
        }
        if (GameUtilities.doBoxesIntersect(boundingBox, otherPiece.getBoundingBox())) {
            setCollided(otherPiece);
            return true;
        }
        return false;
    }

    public GamePiece getCollidedPiece() {
        return collided;
    }

    public void setCollided(GamePiece otherPiece) {
        this.collided = otherPiece;
    }

    public void toss(float angle, float velocity) {
        lastStep = System.currentTimeMillis();
        double radians = angle / 180 * Math.PI;
        velocityX = (float)(velocity * Math.cos(radians) / mass);
        // Start with negative velocity since "up" means smaller values of y
        velocityY = (float)(-velocity * Math.sin(radians) / mass);
    }

    public void step() {
        // Make sure we're moving at all using our lastStep tracker as a sentinel
        if (lastStep > 0) {
            // let's apply our gravity
            long now = System.currentTimeMillis();
            float slice = (now - lastStep) / 1000.0f;
            velocityY = velocityY + (slice * Field.GRAVITY);
            int newX = (int)(centerX + velocityX);
            int newY = (int)(centerY + velocityY);
            setPosition(newX, newY);
        }
    }
}
