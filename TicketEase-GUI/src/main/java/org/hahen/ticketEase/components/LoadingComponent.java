package org.hahen.ticketEase.components;

import javax.swing.*;
import java.awt.*;

public class LoadingComponent extends JPanel {

    private JLabel gifLabel;
    private JPanel overlayPanel;

    public LoadingComponent() {
        // Set layout for the loading screen
        setLayout(new BorderLayout());
        setBackground(new Color(255, 255, 255, 0)); // Transparent background

        // Create an overlay panel with semi-transparent background to dim the screen
        overlayPanel = new JPanel();
        overlayPanel.setBackground(new Color(0, 0, 0, 100)); // Semi-transparent black (dim effect)
        overlayPanel.setLayout(new BorderLayout());
        overlayPanel.setPreferredSize(new Dimension(800, 600));  // Adjust according to your frame size

        // Add the overlay panel to the main panel
        add(overlayPanel, BorderLayout.CENTER);

        // Create a loading GIF label
        gifLabel = new JLabel(new ImageIcon("D:\\private\\hahn\\TicketEase-GUI\\src\\main\\resources\\images\\loading.gif"));  // Replace with your GIF path
        gifLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gifLabel.setVerticalAlignment(SwingConstants.CENTER);

        // Add the loading GIF label on top of the dimmed background
        overlayPanel.add(gifLabel, BorderLayout.CENTER);
    }

    public void startLoading(JFrame frame) {
        // Show the loading screen inside the frame
        frame.setGlassPane(this);  // Add loading to the glass pane (this disables interaction)
        setVisible(true);
        this.repaint();
    }

    public void stopLoading(JFrame frame) {
        // Hide the loading screen
        setVisible(false);
        frame.getGlassPane().setVisible(false);
    }
}

