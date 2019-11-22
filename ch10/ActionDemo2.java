package ch10;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A simple, classic demonstration of event handling. Create a frame
 * with a button and a label. As the button is pressed, a counter
 * shown in the label is incremented.
 *
 * This second variation uses a separate class to handle the action
 * events rather than implementing the ActionListener interface
 * directly as in ActionDemo1.
 */
public class ActionDemo2 {
    public static void main( String[] args ) {
        JFrame frame = new JFrame( "ActionListener Demo" );
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.setSize( 300, 180 );

        JLabel label = new JLabel("Results go here", JLabel.CENTER );
        ActionCommandHelper helper = new ActionCommandHelper(label);

        JButton simpleButton = new JButton("Button");
        simpleButton.addActionListener(helper);

        JTextField simpleField = new JTextField(10);
        simpleField.addActionListener(helper);

        frame.add(simpleButton);
        frame.add(simpleField);
        frame.add(label);

        frame.setVisible( true );
    }
}

/**
 * Helper class to show the command property of any ActionEvent in a given label.
 */
class ActionCommandHelper implements ActionListener {
    JLabel resultLabel;

    public ActionCommandHelper(JLabel label) {
        resultLabel = label;
    }

    public void actionPerformed(ActionEvent ae) {
        resultLabel.setText(ae.getActionCommand());
    }
}