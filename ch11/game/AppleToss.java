package ch11.game;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

/**
 * Our apple tossing game. This class extends JFrame to create our
 * main application window. We're getting closer to our final
 * version of the game which allows two players to compete in a timed
 * trial to see who can clear the trees fastest.
 */
public class AppleToss extends JFrame {
    public static final int SCORE_HEIGHT = 30;
    public static final int CONTROL_WIDTH = 300;
    public static final int CONTROL_HEIGHT = 40;
    public static final int FIELD_WIDTH = 3 * CONTROL_WIDTH;
    public static final int FIELD_HEIGHT = 2 * CONTROL_WIDTH;
    public static final float FORCE_SCALE = 0.7f;

    GridBagLayout gameLayout = new GridBagLayout();
    GridBagConstraints gameConstraints = new GridBagConstraints();
    JPanel gamePane = new JPanel(gameLayout);

    Field field = new Field();
    Physicist player1 = new Physicist();
    ArrayList<Physicist> otherPlayers = new ArrayList<>();

    Random random = new Random();

    public AppleToss() {
        // Create our frame
        super("Apple Toss Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Build the field with our player and some trees
        setupFieldForOnePlayer();

        // Setup the grid we'll use to layout our various components
        gameLayout.columnWidths = new int[] { CONTROL_WIDTH, CONTROL_WIDTH, CONTROL_WIDTH };
        gameLayout.rowHeights = new int[] { SCORE_HEIGHT, FIELD_HEIGHT, CONTROL_HEIGHT, CONTROL_HEIGHT };

        // Now build and add those components at the desired position
        gamePane.add(new JLabel(" Player 1: 0"), buildConstraints(0, 0, 1, 1));
        // gamePane.add(new JLabel(" Player 2: 0"), buildConstraints(0, 1, 1, 1));
        gamePane.add(buildRestartButton(), buildConstraints(0, 2, 1, 1));
        gamePane.add(field, buildConstraints(1, 0, 1, 3));
        gamePane.add(buildAngleControl(), buildConstraints(2, 0, 1, 1));
        gamePane.add(buildForceControl(), buildConstraints(2, 1, 1, 1));
        gamePane.add(buildTossButton(), buildConstraints(2, 2, 2, 1));
        gamePane.add(new JLabel("Angle", JLabel.CENTER), buildConstraints(3, 0, 1, 1));
        gamePane.add(new JLabel("Force", JLabel.CENTER), buildConstraints(3, 1, 1, 1));

		// Setup the networking menu (actions are left to the read to implement)
		setupNetworkMenu();
		
        // replace the frame's content with our game
        setContentPane(gamePane);

        // And set the correct size + a buffer for any OS frame title
        setSize(FIELD_WIDTH,SCORE_HEIGHT + FIELD_HEIGHT + (2 * CONTROL_HEIGHT) + 20);
    }

    private GridBagConstraints buildConstraints(int row, int col, int rowspan, int colspan) {
        gameConstraints.fill = GridBagConstraints.BOTH;
        gameConstraints.gridy = row;
        gameConstraints.gridx = col;
        gameConstraints.gridheight = rowspan;
        gameConstraints.gridwidth = colspan;
        return gameConstraints;
    }

    private JButton buildRestartButton() {
        JButton button = new JButton("Play Again");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setupFieldForOnePlayer();
                field.repaint();
            }
        });
        return button;
    }

    private JSlider buildAngleControl() {
        JSlider slider = new JSlider(0,180);
        slider.setInverted(true);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                player1.setAimingAngle((float)slider.getValue());
                field.repaint();
            }
        });
        return slider;
    }

    private JSlider buildForceControl() {
        JSlider slider = new JSlider();
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                player1.setAimingForce(slider.getValue() * FORCE_SCALE);
            }
        });
        return slider;
    }

    private JButton buildTossButton() {
        JButton button = new JButton("Toss");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                field.startTossFromPlayer(player1);
            }
        });
        return button;
    }

    private void setupNetworkMenu() {
        JMenu netMenu = new JMenu("Multiplayer");

        JMenuItem startItem = new JMenuItem("Start Server");
        startItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(AppleToss.this, "Start Server selected",
					"Network Alert", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        netMenu.add(startItem);

        JMenuItem joinItem = new JMenuItem("Join Game...");
        joinItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(AppleToss.this, "Join Game selected",
					"Network Alert", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        netMenu.add(joinItem);

        JMenuItem quitItem = new JMenuItem("Disconnect");
        quitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(AppleToss.this, "Disconnect selected",
					"Network Alert", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        netMenu.add(quitItem);

        // build a JMenuBar for the application
        JMenuBar mainBar = new JMenuBar();
        mainBar.add(netMenu);
        setJMenuBar(mainBar);
    }

    /**
     * Helper method to return a good x value for a tree so it's not off the left or right edge.
     *
     * @return x value within the bounds of the playing field width
     */
    private int goodX() {
        // at least half the width of the tree plus a few pixels
        int leftMargin = Field.TREE_WIDTH_IN_PIXELS / 2 + 5;
        // now find a random number between a left and right margin
        int rightMargin = FIELD_WIDTH - leftMargin;

        // And return a random number starting at the left margin
        return leftMargin + random.nextInt(rightMargin - leftMargin);
    }

    /**
     * Helper method to return a good y value for a tree so it's not off the top or bottom of the screen.
     *
     * @return y value within the bounds of the playing field height
     */
    private int goodY() {
        // at least half the height of the "leaves" plus a few pixels
        int topMargin = Field.TREE_WIDTH_IN_PIXELS / 2 + 5;
        // a little higher off the bottom
        int bottomMargin = FIELD_HEIGHT - Field.TREE_HEIGHT_IN_PIXELS;

        // And return a random number starting at the top margin but not past the bottom
        return topMargin + random.nextInt(bottomMargin - topMargin);
    }

    /**
     * A helper method to populate a one player field with target trees.
     */
    private void setupFieldForOnePlayer() {
        // place our (new) physicist in the lower left corner
        if (field.physicists.size() == 0) {
            player1.setPosition(Field.PHYSICIST_SIZE_IN_PIXELS, FIELD_HEIGHT - (int) (Field.PHYSICIST_SIZE_IN_PIXELS * 1.5));
            field.physicists.add(player1);
            player1.setField(field);
        }

        // Create some trees for target practice
        for (int i = field.trees.size(); i < 10; i++) {
            Tree t = new Tree();
            t.setPosition(goodX(), goodY());
            // Trees can be close to each other and overlap, but they shouldn't intersect our physicist
            while(player1.isTouching(t)) {
                // We do intersect this tree, so let's try again
                t.setPosition(goodX(), goodY());
                System.err.println("Repositioning an intersecting tree...");
            }
            field.trees.add(t);
        }
    }

    public static void main(String args[]) {
        AppleToss game = new AppleToss();
        game.setVisible(true);
    }
}
