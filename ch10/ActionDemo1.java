package ch10;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A simple, classic demonstration of event handling. Create a frame
 * with a button and a label. As the button is pressed, a counter
 * shown in the label is incremented.
 */
public class ActionDemo1 extends JFrame implements ActionListener {
    int counterValue = 0;
    JLabel counterLabel;

    public ActionDemo1() {
        super( "ActionEvent Counter Demo" );
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        setSize( 300, 180 );

        counterLabel = new JLabel("Count: 0", JLabel.CENTER );
        add(counterLabel);

        JButton incrementer = new JButton("Increment");
        incrementer.addActionListener(this);
        add(incrementer);
    }

    public void actionPerformed(ActionEvent e) {
        counterValue++;
        counterLabel.setText("Count: " + counterValue);
    }

    public static void main( String[] args ) {
        ActionDemo1 demo = new ActionDemo1();
        demo.setVisible(true);
    }
}
