package ch10;

import java.awt.*;
import javax.swing.*;

/**
 * A basic demonstration of the BorderLayout with higlighted
 * areas in different colors.
 */
public class BorderLayoutDemo {
    public static void main( String[] args ) {
        JFrame frame = new JFrame("BorderLayout Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        JLabel northLabel = new JLabel("Top - North", JLabel.CENTER);
        JLabel southLabel = new JLabel("Bottom - South", JLabel.CENTER);
        JLabel eastLabel = new JLabel("Right - East", JLabel.CENTER);
        JLabel westLabel = new JLabel("Left - West", JLabel.CENTER);
        JLabel centerLabel = new JLabel("Center (everything else)", JLabel.CENTER);

        // Color the labels so we can see their boundaries better
        northLabel.setOpaque(true);
        northLabel.setBackground(Color.GREEN);
        southLabel.setOpaque(true);
        southLabel.setBackground(Color.GREEN);
        eastLabel.setOpaque(true);
        eastLabel.setBackground(Color.RED);
        westLabel.setOpaque(true);
        westLabel.setBackground(Color.RED);
        centerLabel.setOpaque(true);
        centerLabel.setBackground(Color.YELLOW);

        frame.add(northLabel, BorderLayout.NORTH);
        frame.add(southLabel, BorderLayout.SOUTH);
        frame.add(eastLabel, BorderLayout.EAST);
        frame.add(westLabel, BorderLayout.WEST);
        frame.add(centerLabel, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
