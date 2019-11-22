package game;

import java.awt.*;

/**
 * Interface to hold common elements for our apples, trees, and physicists
 * From the README:
 * GamePiece
 *   - methods for positioning on a PlayingField
 *   - methods for Drawing
 *   - methods for detecting a collision with other GamePieces
 */

public interface GamePiece {
    /**
     * Sets the position of the piece on the playing field.
     * (0,0) is the upper left per standard Java drawing methods.
     *
     * @param x
     * @param y
     */
    void setPosition(int x, int y);

    /**
     * Gets the current horizontal position of the piece on the field.
     *
     * @return current X position of the piece
     */
    int getPositionX();

    /**
     * Gets the current vertical position of the piece on the field.
     *
     * @return current Y position of the piece
     */
    int getPositionY();

    /**
     * Gets the bounding box of this piece.
     *
     * @return a Rectangle encompassing the visual elements of the piece
     */
    Rectangle getBoundingBox();

    /**
     * Draws the piece inside the given graphics context.
     * Do not assume anything about the state of the context.
     *
     * @param g
     */
    void draw(Graphics g);

    /**
     * Detect a collision with another piece on the field.
     * By definition, a piece does NOT touch itself (i.e. it won't collide with itself).
     *
     * @param otherPiece
     * @return
     */
    boolean isTouching(GamePiece otherPiece);
}
