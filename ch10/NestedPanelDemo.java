package ch10;

import javax.swing.*;
import java.awt.*;

/**
 * An alternate way to arrange complex UIs. Rather than
 * use more flexible (but complicated) layout managers,
 * you can nest containers each with simpler managers.
 */
public class NestedPanelDemo {
    public static void main( String[] args ) {
        JFrame frame = new JFrame("Nested Panel Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        // Create the text area and go ahead and add it to the center
        JTextArea messageArea = new JTextArea();
        frame.add(messageArea, BorderLayout.CENTER);

        // Create the button container
        //JPanel buttonPanel = new JPanel(new FlowLayout());
        JPanel buttonPanel = new JPanel(new GridLayout(1,0));

        // Create the buttons
        JButton sendButton = new JButton("Send");
        JButton saveButton = new JButton("Save");
        JButton resetButton = new JButton("Reset");
        JButton cancelButton = new JButton("Cancel");

        // Add the buttons to their container
        buttonPanel.add(sendButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(cancelButton);

        // And finally, add the button container to the bottom of the app
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
