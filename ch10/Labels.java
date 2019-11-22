package ch10;

import javax.swing.*;
import java.awt.*;

/**
 * A simple demo of several label options with variations
 * on color, alignment, and icons.
 */
public class Labels {

    // To make this simple example run from inside an IDE like IntelliJ IDEA,
    // set this path to match where you unzipped the book's projects.
    static final String PROJECT_PATH = "/Users/work/LearningJava5e";

    public static void main( String[] args ) {
        JFrame frame = new JFrame( "JLabel Examples" );
        frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize( 300, 300 );

        JLabel basic = new JLabel("Default Label");
        basic.setOpaque(true);
        basic.setBackground(Color.YELLOW);
        JLabel another = new JLabel("Another Label");
        another.setOpaque(true);
        another.setBackground(Color.GREEN);
        JLabel simple = new JLabel("A Simple Label");
        simple.setOpaque(true);
        simple.setBackground(Color.WHITE);
        JLabel standard = new JLabel("A Standard Label");
        standard.setOpaque(true);
        standard.setBackground(Color.ORANGE);
        JLabel centered = new JLabel("Centered Text", JLabel.CENTER);
        centered.setPreferredSize(new Dimension(150, 24));
        centered.setOpaque(true);
        centered.setBackground(Color.WHITE);
        JLabel times = new JLabel("Times Roman");
        times.setOpaque(true);
        times.setBackground(Color.WHITE);
        times.setFont(new Font("TimesRoman", Font.BOLD, 18));
        JLabel styled = new JLabel("<html>Some <b><i>styling</i></b> is also allowed</html>");
        styled.setOpaque(true);
        styled.setBackground(Color.WHITE);
        JLabel icon = new JLabel("Verified", new ImageIcon(PROJECT_PATH + "/ch10/check.png"), JLabel.LEFT);
        icon.setOpaque(true);
        icon.setBackground(Color.WHITE);

        frame.add(basic);
        frame.add(another);
        frame.add(simple);
        frame.add(standard);
        frame.add(centered);
        frame.add(times);
        frame.add(styled);
        frame.add(icon);

        frame.setVisible( true );
    }
}
