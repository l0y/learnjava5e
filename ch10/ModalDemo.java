package ch10;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A demonstration of showing modal dialogs. Pressing
 * the Go button will popup a new window which must be
 * dismissed before interacting with the main application again.
 */
public class ModalDemo extends JFrame implements ActionListener {

    JLabel modalLabel;

    public ModalDemo() {
        super( "Modal Dialog Demo" );
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        setSize( 300, 180 );

        modalLabel = new JLabel("Press 'Go' to show the popup!", JLabel.CENTER );
        add(modalLabel);

        JButton goButton = new JButton("Go");
        goButton.addActionListener(this);
        add(goButton);
    }

    public void actionPerformed(ActionEvent ae) {
        JOptionPane.showMessageDialog(this, "We're going!", "Alert", JOptionPane.INFORMATION_MESSAGE);
        modalLabel.setText("Go pressed! Press again if you like.");
    }

    public static void main(String args[]) {
        ModalDemo demo = new ModalDemo();
        demo.setVisible(true);
    }
}
