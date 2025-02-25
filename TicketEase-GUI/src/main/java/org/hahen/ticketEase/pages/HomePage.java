package org.hahen.ticketEase.pages;

import org.hahen.ticketEase.configurations.GlobalVariables;

import javax.swing.*;
import java.awt.*;

public class HomePage extends JPanel {

    public HomePage() {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        add(createPage(), new GridBagConstraints());
    }

    // Creates the HomePage content
    public JPanel createPage() {
        JPanel panel = new JPanel(new GridBagLayout());  // Center everything inside
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.anchor = GridBagConstraints.CENTER;

        // Title Label
        JLabel titleLabel = new JLabel("Welcome to the IT Support Ticket System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(41, 173, 185));
        panel.add(titleLabel, gbc);

        // Detailed Welcome Text
        JTextArea welcomeText = new JTextArea(
                "Our IT Support Ticket System is designed to help you efficiently manage and resolve IT-related issues.\n\n"
                        + "Create and Track Tickets**: Easily submit new support requests and monitor their progress.\n"
                        + "Prioritized Support**: Urgent issues are handled first to minimize downtime.\n"
                        + "Real-Time Updates**: Stay informed with instant notifications and updates on your requests.\n"
                        + "User-Friendly Interface**: Navigate through categories, view ticket statuses, and interact with support teams effortlessly.\n\n"
                        + "To get started, select a category from the sidebar and create a new support request!**"
        );
        welcomeText.setFont(new Font("Arial", Font.PLAIN, 16));
        welcomeText.setLineWrap(true);
        welcomeText.setWrapStyleWord(true);
        welcomeText.setEditable(false);
        welcomeText.setOpaque(true);  // Set opaque to true to make the background visible
        welcomeText.setBackground(Color.WHITE);  // Optional: Set background color to match the panel
        welcomeText.setPreferredSize(new Dimension(600, 200));  // Ensure a preferred size to display the content
        panel.add(welcomeText, gbc);

        return panel;
    }
}
