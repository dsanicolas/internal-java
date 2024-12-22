package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

import db.DbMockup;
import model.Player;

/**
 * PlayerSelectionView implements the view for selecting players.
 * Provides the interface for selecting two players and starting the game.
 */
public class PlayerSelectionView {

    private String title = "Player Selection"; // Title of the frame
    private JFrame mainFrame; // Main frame for the view
    private DbMockup db; // Database mockup for player operations
    private Player player1; // Player 1 object
    private Player player2; // Player 2 object
    private Player[] players; // Array of players fetched from the database

    /**
     * Constructor for PlayerSelectionView.
     * Initializes the database mockup.
     *
     * @param _db The database mockup object.
     */
    public PlayerSelectionView(DbMockup _db) {
        this.db = _db;
    }

    public void setPlayer1(Player _plyr) {
        this.player1 = _plyr;
    }

    public void setPlayer2(Player _plyr) {
        this.player2 = _plyr;
    }

    private void fillPlayers() {
        List<Player> plyrsList = db.getPlayers(""); // Fetch players from the database
        this.players = new Player[plyrsList.size()];
        plyrsList.toArray(this.players);
    }

    public void render() {
        this.fillPlayers();

        this.destroyMainFrame();

        this.mainFrame = new JFrame(this.title);
        this.mainFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                destroyMainFrame();
            }
        });
        this.mainFrame.setSize(500, 300);
        this.mainFrame.setLocationRelativeTo(null);

        JPanel grid = new JPanel(new GridLayout(2, 1));
        this.mainFrame.add(grid);

        JPanel tpPnl = new JPanel();
        tpPnl.setLayout(new BoxLayout(tpPnl, BoxLayout.Y_AXIS));
        grid.add(tpPnl);

        JButton startBtn = new JButton("START");
        startBtn.setEnabled(false);

        this.createPlayerSelector(true, tpPnl, startBtn);
        this.createPlayerSelector(false, tpPnl, startBtn);

        JPanel bttmPnl = new JPanel();

        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("PlayerSelectionView - Start with player 1 " + player1.dbgMeAsStr() + " and player2 " + player2.dbgMeAsStr());
                // TODO: Handle navigation to game view
                destroyMainFrame();
            }
        });
        bttmPnl.add(startBtn);
        grid.add(bttmPnl);

        this.mainFrame.setVisible(true);
    }

    private void destroyMainFrame() {
        if (this.mainFrame != null) {
            this.mainFrame.setVisible(false);
            this.mainFrame.dispose();
        }
    }

    private void createPlayerSelector(boolean isPlayer1, JPanel pnl, JButton startBtn) {
        JPanel plyrPnl = new JPanel();
        plyrPnl.setLayout(new BoxLayout(plyrPnl, BoxLayout.X_AXIS));

        JLabel plyrTxt = new JLabel(isPlayer1 ? "Player 1" : "Player 2");
        plyrTxt.setAlignmentX(Component.CENTER_ALIGNMENT);
        plyrPnl.add(plyrTxt);

        JComboBox<Player> plyrCmb = new JComboBox<>(new DefaultComboBoxModel<>(this.players));
        plyrCmb.setPreferredSize(new Dimension(200, 25));
        plyrCmb.setMaximumSize(new Dimension(200, 25));
        plyrCmb.setAlignmentX(Component.CENTER_ALIGNMENT);
        plyrPnl.add(plyrCmb);

        KeySelectionRenderer renderer = new KeySelectionRenderer(plyrCmb) {
            @Override
            public String getDisplayValue(Object value) {
                Player plyr = (Player) value;
                return plyr.getNickName();
            }
        };

        plyrCmb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<Player> comboBox = (JComboBox<Player>) e.getSource();
                if (isPlayer1) {
                    player1 = (Player) comboBox.getSelectedItem();
                    System.out.println("PlayerSelectionView - Selected player 1:" + player1.dbgMeAsStr());
                } else {
                    player2 = (Player) comboBox.getSelectedItem();
                    System.out.println("PlayerSelectionView - Selected player 2:" + player2.dbgMeAsStr());
                }

                if (player1 != null && player2 != null && player1.getId() != player2.getId()) {
                    startBtn.setEnabled(true);
                } else {
                    startBtn.setEnabled(false);
                }
            }
        });

        this.setComboBoxToPlayer(plyrCmb, isPlayer1 ? this.player1 : this.player2);

        JButton newPlyrBtn = new JButton("new");
        newPlyrBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton editPlyrBtn = new JButton("edit");
        newPlyrBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        newPlyrBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("PlayerSelectionView - New Player requested for " + (isPlayer1 ? "player1" : "player2"));
                // TODO: Handle navigation to player create view
            }
        });

        editPlyrBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("PlayerSelectionView - Edit Player requested for " + (isPlayer1 ? "player1" : "player2"));
                // TODO: Handle navigation to player edit view
            }
        });
        plyrPnl.add(newPlyrBtn);
        plyrPnl.add(editPlyrBtn);

        pnl.add(plyrPnl);
    }

    private void setComboBoxToPlayer(JComboBox cmbo, Player plyr) {
        if (plyr == null) {
            cmbo.setSelectedIndex(0);
        } else {
            for (int i = 0; i < this.players.length; i++) {
                if (this.players[i].getId() == plyr.getId()) {
                    cmbo.setSelectedIndex(i);
                    break;
                }
            }
        }
    }
}
