package ch10;

import javax.swing.*;
import java.awt.*;

/**
 * Demo of the GridLayout manager used to create a
 * dial pad like you might find on a phone.
 */
public class PhoneGridDemo {
    public static void main( String[] args ) {
        JFrame frame = new JFrame("Nested Panel Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(200, 300);

        // Create the phone pad container
        JPanel phonePad = new JPanel(new GridLayout(4,3));

        // Create and add the 12 buttons, top-left to bottom-right
        phonePad.add(new JButton("1"));
        phonePad.add(new JButton("2"));
        phonePad.add(new JButton("3"));

        phonePad.add(new JButton("4"));
        phonePad.add(new JButton("5"));
        phonePad.add(new JButton("6"));

        phonePad.add(new JButton("7"));
        phonePad.add(new JButton("8"));
        phonePad.add(new JButton("9"));

        phonePad.add(new JButton("*"));
        phonePad.add(new JButton("0"));
        phonePad.add(new JButton("#"));

        // And finally, add the pad to the center of the app
        frame.add(phonePad, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
