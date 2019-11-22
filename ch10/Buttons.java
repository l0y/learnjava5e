package ch10;

import javax.swing.*;
import java.awt.*;

/**
 * A very simple button placed on a frame. This button is
 * not connected to any listener so it will "press" when
 * clicked but action is taken in response.
 */
public class Buttons {
    public static void main( String[] args ) {
        JFrame frame = new JFrame( "JButton Examples" );
        frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize( 300, 200 );

        JButton basic = new JButton("Try me!");
        frame.add(basic);

        frame.setVisible( true );
    }
}
