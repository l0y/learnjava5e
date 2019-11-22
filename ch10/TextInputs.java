package ch10;

import javax.swing.*;
import java.awt.*;

/**
 * Some example text inputs including a text area embedded in a
 * JScrollPane.
 */
public class TextInputs {
    public static void main( String[] args ) {
        JFrame frame = new JFrame( "JTextField Examples" );
        frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize( 400, 300 );

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(10);
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(24);

        JLabel bodyLabel = new JLabel("Body:");
        JTextArea bodyArea = new JTextArea(10,30);
        bodyArea.setLineWrap(true);
        bodyArea.setWrapStyleWord(true);
        JScrollPane bodyScroller = new JScrollPane(bodyArea);
        bodyScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        bodyScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        frame.add(nameLabel);
        frame.add(nameField);
        frame.add(emailLabel);
        frame.add(emailField);
        frame.add(bodyLabel);
        frame.add(bodyScroller);

        frame.setVisible( true );
    }
}
