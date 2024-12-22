package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;

import java.util.List;
import java.util.Iterator;

import db.DbMockup;
import model.Player;
import model.Game;

/**
 * HomeView implements the view of the Home interface, allowing users to select a game
 * and view the top players.
 */
public class HomeView {

    // Title of the home window
    private String title = "HOME"; // Title of the home window
    private JFrame mainFrame; // Main frame of the home view

    // Dependencies and data models
    private DbMockup db; // Database mockup used for fetching data
    private List<Game> games; // List of available games
    private Player[] topPlayers; // Array of top players

    /**
     * Constructs the HomeView.
     *
     * @param _db the database mockup used for fetching data
     */
    public HomeView(DbMockup _db) {
        this.db = _db;
    }

    /**
     * Fills the list of available games by fetching from the database.
     */
    private void fillGames() { 
        games = db.getGames();
    }

    /**
     * Fills the array of top players by fetching from the database.
     */
    private void fillTopPlayers() { 
        List<Player> playerList = db.getTopPlayers();
        this.topPlayers = new Player[playerList.size()];
        playerList.toArray(this.topPlayers);
    }

    /**
     * Renders the HomeView interface, including game selection buttons and
     * a table displaying the top players.
     */
    public void render() {
        this.fillGames();
        this.fillTopPlayers();

        // We don't want to recreate a frame each time for this view, so destroy any previous one that may have been created
        this.destroyMainFrame();

        // Create the frame
        this.mainFrame = new JFrame(this.title);

        // When clicking the close button, we want to destroy that frame, here it is
        this.mainFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.mainFrame.addWindowListener(new WindowAdapter() { 
            @Override
            public void windowClosing(WindowEvent e) {
                destroyMainFrame();
            }
        });

        // Set frame size
        this.mainFrame.setSize(500, 300);

        // Center the frame on the screen
        this.mainFrame.setLocationRelativeTo(null); 

        // Now create a horizontal grid inside that frame with a left panel and a right one
        JPanel grid = new JPanel(new GridLayout(1, 2));
        this.mainFrame.add(grid);

        // Left panel
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        grid.add(leftPanel);

        JLabel text = new JLabel("Select Your Game \n And \n Start Playing");
        leftPanel.add(text);
        text.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Iterate over games and create buttons for each
        Iterator<Game> gameIterator = games.iterator();
        while (gameIterator.hasNext()) {
            Game game = gameIterator.next();
            JButton button = new JButton(game.getName().toLowerCase());
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("HomeView - Selected Game:" + game.dbgMeAsStr());
                        // TODO: Handle navigation to player selection view
                    }
                });
            leftPanel.add(button);
        }

        // Right panel
        JPanel rightPanel = new JPanel();
        grid.add(rightPanel);

        // Data to be displayed in the JTable
        TableModel dataModel = new AbstractTableModel() {
            public int getColumnCount() {
                return 2;
            }

            public int getRowCount() {
                return topPlayers.length;
            }

            public Object getValueAt(int row, int col) {
                if (col == 0) {
                    return topPlayers[row].getNickName();
                }
                if (col == 1) {
                    return topPlayers[row].getScore();
                }
                return null;
            }
        };

        // Create and add the JTable to the right panel
        JTable scoresTable = new JTable(dataModel);
        rightPanel.add(scoresTable);

        // Make the frame visible
        this.mainFrame.setVisible(true);
    }
  
    private void destroyMainFrame() {
        if (this.mainFrame != null) {
            this.mainFrame.setVisible(false);
            this.mainFrame.dispose();
        }
    }
}
