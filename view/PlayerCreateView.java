package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import db.DbInterface;
import model.Player;
import navigation.NavigationController;

import view.BaseView;

/**
 * PlayerCreateView provides an interface for creating a new player.
 * It allows the user to input a nickname and submit the details
 * to create a player or cancel the operation.
 */
public class PlayerCreateView extends BaseView {

    private DbInterface db; // Database mockup for player creation
    private Player plyr; // Player object to be created
    private boolean isForPlayer1; // Flag to indicate if the player is Player 1

    /**
     * Constructs the PlayerCreateView.
     *
     * @param _db the database mockup used for player creation
     * @param navigationController the navigation controller for managing view transitions
     */
    public PlayerCreateView(DbInterface _db, boolean _isForPlayer1, NavigationController navigationController) {
        super("Create Player", navigationController);
        this.db = _db;
        this.isForPlayer1 = _isForPlayer1;
    }

    /**
     * Renders the player creation interface.
     * The interface includes a text input for entering a nickname and buttons
     * for submitting or canceling the operation.
     */
    @Override
    public void render() {
        // Set up the main frame
        this.setupMainFrame();

        // Create a vertical grid inside the frame with a top panel and a bottom one
        JPanel grid = new JPanel(new GridLayout(2, 1));
        this.mainFrame.add(grid);

        // Top panel for nickname input
        JPanel tpPnl = new JPanel();
        tpPnl.setLayout(new BoxLayout(tpPnl, BoxLayout.Y_AXIS));
        grid.add(tpPnl);

        // Prompt for entering a new nickname
        JLabel prmptText = new JLabel("Enter a new nickname");
        prmptText.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField nmInpt = new JTextField(25); // TextField with a capacity of 25 characters
        nmInpt.setMaximumSize(nmInpt.getPreferredSize());
        tpPnl.add(prmptText);
        tpPnl.add(Box.createRigidArea(new Dimension(0, 10))); // Add some space between components
        tpPnl.add(nmInpt);

        // Bottom panel for buttons
        JPanel bttmPnl = new JPanel();
        bttmPnl.setLayout(new BoxLayout(bttmPnl, BoxLayout.X_AXIS));
        grid.add(bttmPnl);

        // Submit button for creating a player
        JButton sbmtBttn = new JButton("Submit");
        sbmtBttn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nmInpt.getText() == null || nmInpt.getText().trim().length() == 0 || "nickname already exist".equals(nmInpt.getText())) {
                    return;
                }
                System.out.println("PlayerCreateView - Player Creation Submitted Name: " + nmInpt.getText());
                try {
                    plyr = db.createPlayer(nmInpt.getText());
                } catch (Exception exception) {
                    nmInpt.setText("nickname already exist");
                    return;
                }
                if (isForPlayer1) {
                    navigationController.navigateToPlayerSelectionViewWithPlayer1(plyr);
                } else {
                    navigationController.navigateToPlayerSelectionViewWithPlayer2(plyr);
                }
                destroyMainFrame();
            }
        });

        // Cancel button to abort player creation
        JButton cnclBttn = new JButton("Cancel");
        cnclBttn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("PlayerCreateView - Player Creation Cancelled");
                navigationController.navigateToPlayerSelectionView();
                destroyMainFrame();
            }
        });

        // Add buttons to the bottom panel
        bttmPnl.add(Box.createHorizontalGlue()); // Add space between buttons
        bttmPnl.add(sbmtBttn);
        bttmPnl.add(Box.createRigidArea(new Dimension(10, 0))); // Add space between buttons
        bttmPnl.add(cnclBttn);
        bttmPnl.add(Box.createHorizontalGlue()); // Add space between buttons

        // Make the frame visible
        this.mainFrame.pack();
        this.mainFrame.setVisible(true);
    }
}
