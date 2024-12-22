package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import navigation.NavigationController;

/**
 * BaseView provides common functionalities for all views.
 * It handles the creation and destruction of the main frame and common setup tasks.
 */
public abstract class BaseView {

    protected String title; // Title of the frame
    protected JFrame mainFrame; // Main frame for the view
    protected NavigationController navigationController; // Controller for managing view transitions

    /**
     * Constructs the BaseView with the given title and navigation controller.
     *
     * @param title the title of the frame
     * @param navigationController the navigation controller for managing view transitions
     */
    public BaseView(String title, NavigationController navigationController) {
        this.title = title;
        this.navigationController = navigationController;
    }

    /**
     * Renders the view.
     * This method should be overridden by subclasses to provide specific rendering logic.
     */
    public abstract void render();

    /**
     * Sets up the main frame with common properties.
     */
    protected void setupMainFrame() {
        // Destroy any previous frame
        this.destroyMainFrame();

        // Create the frame
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
    }

    /**
     * Destroys the main frame if it exists.
     */
    protected void destroyMainFrame() {
        if (this.mainFrame != null) {
            this.mainFrame.setVisible(false);
            this.mainFrame.dispose();
        }
    }
}
