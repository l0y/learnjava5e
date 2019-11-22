package ch02;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * An upgraded graphical application with interactivity!
 */
public class HelloJava2 {
	public static void main( String[] args ) {
    	JFrame frame = new JFrame( "HelloJava2" );
		frame.add( new HelloComponent2("Hello, Java!") );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.setSize( 300, 300 );
		frame.setVisible( true );
	}
}

/*
 * Inheritence (the "extends" keyword below) and interfaces
 * (the "implements MouseMotionListener" portion) are covered in
 * Chapter 5.
 */
class HelloComponent2 extends JComponent implements MouseMotionListener {
	String theMessage;
	int messageX = 125, messageY = 95; // Coordinates of the message

	/**
	 * Create a new component that can draw its message at an arbitrary position.
	 * That position can be changed by dragging the mouse; we attach a listener
	 * to pick up those drag events.
	 */
	public HelloComponent2( String message ) {
    	theMessage = message;
    	addMouseMotionListener(this);
	}

	public void paintComponent( Graphics g ) {
    	g.drawString( theMessage, messageX, messageY );
	}

	public void mouseDragged(MouseEvent e) {
    	// Save the mouse coordinates and paint the message.
    	messageX = e.getX();
    	messageY = e.getY();
    	repaint();
	}

  	public void mouseMoved(MouseEvent e) { 
		// Ignore simple movements
	}
}
