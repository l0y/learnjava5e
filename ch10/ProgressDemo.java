package ch10;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A multithreaded example showing how to safely update Swing components
 * from a background thread. The ProgressPretender class below slowly
 * counts up to 100 and keeps a JLabel updated with the current value.
 */
public class ProgressDemo {
    public static void main( String[] args ) {
        JFrame frame = new JFrame( "SwingUtilities 'invoke' Demo" );
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.setSize( 300, 180 );

        JLabel label = new JLabel("Download Progress Goes Here!", JLabel.CENTER );
        Thread pretender = new Thread(new ProgressPretender(label));

        JButton simpleButton = new JButton("Start");
        simpleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simpleButton.setEnabled(false);
                pretender.start();
            }
        });

        JLabel checkLabel = new JLabel("Can you still type?");
        JTextField checkField = new JTextField(10);

        frame.add(label);
        frame.add(simpleButton);
        frame.add(checkLabel);
        frame.add(checkField);
        frame.setVisible( true );
    }
}

/**
 * Simulated worker that updates a provided JLabel
 * with the work "progress". In this simulation, we just
 * count from 0 to 100 with a one-second delay between
 * each step.
 */
class ProgressPretender implements Runnable {
    JLabel label;
    int progress;

    public ProgressPretender(JLabel label) {
        this.label = label;
        progress = 0;
    }

    public void run() {
        while (progress <= 100) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    label.setText(progress + "%");
                }
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ie) {
                System.err.println("Someone interrupted us. Skipping download.");
                break;
            }
            progress++;
        }
    }
}
