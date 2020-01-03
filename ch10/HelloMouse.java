package ch10;

import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * A quick demo of how mouse events work. Clicking around the
 * frame will move the label.
 *
 * Note that "mouse" events are the up, down, and click actions
 * of mouse buttons. If you want to catch the mouse moving or dragging,
 * those are handled by the MouseMotionListener interface.
 */
public class HelloMouse extends JFrame implements MouseListener {
    JLabel label;

    public HelloMouse() {
        super("MouseEvent Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setSize( 300, 100 );

        label = new JLabel("Hello, Mouse!", JLabel.CENTER );
        label.setOpaque(true);
        label.setBackground(Color.YELLOW);
        label.setSize(100,20);
        label.setLocation(100,100);
        add(label);

        getContentPane().addMouseListener(this);
    }

    public void mouseClicked(MouseEvent e) {
        label.setLocation(e.getX(), e.getY());
    }

    public void mousePressed(MouseEvent e) { }
    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }

    public static void main( String[] args ) {
        HelloMouse frame = new HelloMouse();
        frame.setVisible( true );
    }
}
