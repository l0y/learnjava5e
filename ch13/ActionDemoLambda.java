package ch13;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * An update to the ch10.ActionDemo2 class with a lambda expression
 * to help handle action events.
 */
public class ActionDemoLambda {
    public static void main( String[] args ) {
        JFrame frame = new JFrame( "ActionListener & Lambdas Demo" );
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.setSize( 300, 180 );

        JLabel label = new JLabel("Results go here", JLabel.CENTER );

        // We can replace our separate helper class with a simple lambda expression
        ActionListener helper = ae -> label.setText(ae.getActionCommand());

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
