package game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.awt.Color.*;

public class Physicist implements GamePiece, ActionListener {
    Color color;
    int centerX, centerY;
    Apple aimingApple;
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
        getNewApple();
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

    /**
     * Take the current apple away from the physicist. Returns the apple for
     * use in animating on the field. Note that if there is no current apple being
     * aimed, null is returned.
     *
     * @return the current apple (if any) being aimed
     */
    public Apple takeApple() {
        Apple myApple = aimingApple;
        aimingApple = null;
        return myApple;
    }

    /**
     * Get a new apple ready if we need one. Do not discard an existing apple.
     */
    public void getNewApple() {
        // Don't drop the current apple if we already have one!
        if (aimingApple == null) {
            aimingApple = new Apple(this);
        }
    }

    @Override
    public void draw(Graphics g) {
        // Make sure our physicist is the right color, then draw the semi-circle and box
        g.setColor(color);
        g.fillArc(x, y, Field.PHYSICIST_SIZE_IN_PIXELS, Field.PHYSICIST_SIZE_IN_PIXELS, 0, 180);
        g.fillRect(x, centerY, Field.PHYSICIST_SIZE_IN_PIXELS, Field.PHYSICIST_SIZE_IN_PIXELS);

        // Do we have an apple to draw as well?
        if (aimingApple != null) {
            // Yes. Position the center of the apple on the edge of our semi-circle.
            // Use the current aimingAngle to determine where on the edge.
            double angleInRadians = Math.toRadians(aimingAngle);
            double radius = Field.PHYSICIST_SIZE_IN_PIXELS / 2.0;
            int aimingX = centerX + (int)(Math.cos(angleInRadians) * radius);
            int aimingY = centerY - (int)(Math.sin(angleInRadians) * radius);
            aimingApple.setPosition(aimingX, aimingY);
            aimingApple.draw(g);
        }
    }

    @Override
    public boolean isTouching(GamePiece otherPiece) {
        if (this == otherPiece) {
            // By definition we don't collide with ourselves
            return false;
        }
        return GameUtilities.doBoxesIntersect(boundingBox, otherPiece.getBoundingBox());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("New Apple")) {
            getNewApple();
            if (field != null) {
                field.repaint();
            }
        }
    }
}
