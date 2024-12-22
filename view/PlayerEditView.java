package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import db.DbMockup;
import model.Player;
import navigation.NavigationController;

import view.BaseView;

/**
 * PlayerEditView implements the view for editing a player's details.
 * Provides the interface for renaming a player and saving the changes.
 */
public class PlayerEditView extends BaseView {

    private DbMockup db; // Database mockup for player operations
    private Player plyr; // Player object to be edited
    private boolean isForPlayer1; // Flag to indicate if the player is Player 1

    /**
     * Constructor for PlayerEditView.
     * Initializes the database mockup and the player to be edited.
     *
     * @param _db The database mockup object.
     * @param _plyr The player object to be edited.
     * @param _isForPlayer1 True if editing player 1, false if editing player 2.
     * @param navigationController The navigation controller for managing view transitions.
     */
    public PlayerEditView(DbMockup _db, Player _plyr, boolean _isForPlayer1, NavigationController navigationController) {
        super("Edit Player", navigationController);
        this.db = _db;
        this.plyr = _plyr;
        this.isForPlayer1 = _isForPlayer1;
    }

    /**
     * Render the player editing view.
     * Creates and displays the GUI for editing a player's nickname.
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

        // Prompt for new name
        JLabel prmptText = new JLabel("Enter a new nickname");
        JTextField nmInpt = new JTextField(25); // TextField with a capacity of 25 characters
        tpPnl.add(prmptText);
        tpPnl.add(nmInpt);

        // Bottom panel for buttons
        JPanel bttmPnl = new JPanel();
        bttmPnl.setLayout(new BoxLayout(bttmPnl, BoxLayout.X_AXIS));
        grid.add(bttmPnl);

        // Submit button for saving changes
        JButton sbmtBttn = new JButton("Submit");
        sbmtBttn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nmInpt.getText() == null || nmInpt.getText().trim().length() == 0 || "nickname already exist".equals(nmInpt.getText())) {
                    return;
                }
                System.out.println("PlayerEditView - Player Creation Submitted Name: " + nmInpt.getText());
                try {
                    plyr = db.renamePlayer(plyr, nmInpt.getText());
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

        // Cancel button to abort editing
        JButton cnclBttn = new JButton("Cancel");
        cnclBttn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("PlayerEditView - Player Creation Cancelled");
                navigationController.navigateToPlayerSelectionView();
                destroyMainFrame();
            }
        });

        // Add buttons to the bottom panel
        bttmPnl.add(sbmtBttn);
        bttmPnl.add(cnclBttn);

        // Make the frame visible
        this.mainFrame.pack();
        this.mainFrame.setVisible(true);
    }
}
