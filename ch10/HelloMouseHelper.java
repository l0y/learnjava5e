package ch10;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

/**
 * A variation on HelloMouse with a separate class implementing
 * the mouse event handler. Note that we have to pass a reference
 * to the label we wish to affect when creating the event
 * helper.
 */
public class HelloMouseHelper {
    public static void main( String[] args ) {
        JFrame frame = new JFrame( "MouseAdapter Demo" );
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize( 300, 300 );

        JLabel label = new JLabel("Hello, Mouse!", JLabel.CENTER );
        label.setOpaque(true);
        label.setBackground(Color.YELLOW);
        label.setSize(100,20);
        label.setLocation(100,100);
        frame.add(label);

        LabelMover mover = new LabelMover(label);
        frame.getContentPane().addMouseListener(mover);
        frame.setVisible( true );
    }
}

/**
 * Helper class to move a label to the position of a mouse click.
 */
class LabelMover extends MouseAdapter {
    JLabel labelToMove;

    public LabelMover(JLabel label) {
        labelToMove = label;
    }

    public void mouseClicked(MouseEvent e) {
        labelToMove.setLocation(e.getX(), e.getY());
    }
}
