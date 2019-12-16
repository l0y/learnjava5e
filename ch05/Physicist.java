package ch05;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.awt.Color.*;

/**
 * Our player class. A physicist can aim where to toss an apple in addition
 * to implementing the basic methods for GamePiece. We can also set a custom
 * color in case you build on the game and allow multiple physicists to be
 * on the screen at the same time.
 */
public class Physicist implements GamePiece {
    Color color;
    int centerX, centerY;
    float aimingAngle;
    float aimingForce;
    Field field;

    // Some helpers for optimizing the draw() method that can be called many, many times
    int x, y;

    // Boundary helpers
    private final int physicistHeight = (int)(1.5 * Field.PHYSICIST_SIZE_IN_PIXELS);
    private Rectangle boundingBox;


    /**
     * Create a default, blue physicist
     */
    public Physicist() {
        this(BLUE);
    }

    /**
     * Create a Physicist of the given color
     */
    public Physicist(Color color) {
        setColor(color);
        aimingAngle = 90.0f;
        aimingForce = 50.0f;
    }

    public void setAimingAngle(Float angle) {
        aimingAngle = angle;
    }

    public void setAimingForce(Float force) {
        if (force < 0) {
            force = 0.0f;
        }
        aimingForce = force;
    }

    /**
     * Sets the color for the physicist.
     *
     * @param color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void setPosition(int x, int y) {
        // Our position is based on the center of our dome,
        // but it will be drawn from the upper left corner.
        // Figure out the distance of that gap
        int offset = (int)(Field.PHYSICIST_SIZE_IN_PIXELS / 2.0f);

        this.centerX = x;
        this.centerY = y;
        this.x = x - offset;
        this.y = y - offset;
        boundingBox = new Rectangle(x, y, Field.PHYSICIST_SIZE_IN_PIXELS, physicistHeight);
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

    /**
     * Sets the active field once our physicist is being displayed.
     *
     * @param field Active game field
     */
    public void setField(Field field) {
        this.field = field;
    }

    @Override
    public void draw(Graphics g) {
        // Make sure our physicist is the right color, then draw the semi-circle and box
        g.setColor(color);
        g.fillArc(x, y, Field.PHYSICIST_SIZE_IN_PIXELS, Field.PHYSICIST_SIZE_IN_PIXELS, 0, 180);
        g.fillRect(x, centerY, Field.PHYSICIST_SIZE_IN_PIXELS, Field.PHYSICIST_SIZE_IN_PIXELS);
    }

    @Override
    public boolean isTouching(GamePiece otherPiece) {
		// We don't really have any collisions to detect yet, so just return "no".
		// As we fill out all of the game pieces, we'll come back to this method
		// and provide a more useful response.
		return false;
    }
}
