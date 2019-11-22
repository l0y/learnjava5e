package ch10;

import javax.swing.*;

/**
 * A simple label placed on a frame.
 */
public class HelloJavaAgain {
    public static void main( String[] args ) {
        JFrame frame = new JFrame( "Hello, Java!" );
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize( 300, 300 );

        JLabel label = new JLabel("Hello, Java!", JLabel.CENTER );
        frame.add(label);

        frame.setVisible( true );
    }
}
