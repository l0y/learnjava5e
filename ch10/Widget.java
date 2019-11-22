package ch10;

import javax.swing.*;
import java.awt.*;

/**
 * A helper class aimed at making it easier to try out
 * the many Swing components in jshell. A small frame will
 * show up once the Widget class is imported into jshell
 * and a new instance is created. Be sure to keep a variable
 * for the new object!
 *
 * <pre>
 * jshell> import javax.swing.*
 *
 * jshell> import ch10.Widget
 * 
 * jshell> Widget w = new Widget("My demo widget")
 * w ==> ch10.Widget[frame0,0,23,300x300,layout=java.awt.B ... tPaneCheckingEnabled=true]
 * 
 * jshell> w.add(new JLabel("Hi"))
 * $5 ==> javax.swing.JLabel[,0,0,0x0,invalid,alignmentX=0 ... rticalTextPosition=CENTER]
 * </pre>
 */
public class Widget extends JFrame {

    public Widget() {
        this("jshell GUI Widget");
    }

    public Widget(String title) {
        super(title);
        setLayout(new FlowLayout());
        setSize(300,300);
        setVisible(true);
    }

    @Override
    public Component add(Component comp) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                getContentPane().add(comp);
                getContentPane().doLayout();
            }
        });
        return comp;
    }

    @Override
    public void remove(Component comp) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                getContentPane().remove(comp);
                getContentPane().doLayout();
                getContentPane().repaint();
            }
        });
    }

    public void reset() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                getContentPane().removeAll();
                getContentPane().doLayout();
                getContentPane().repaint();
            }
        });
    }
}
