package org.hahen.ticketEase.components;

import org.hahen.ticketEase.util.JwtTools;

import javax.swing.*;
import java.awt.*;

public class HeaderComponent extends JPanel {

    public HeaderComponent() {
        setLayout(new BorderLayout());
        setBackground(new Color(41, 128, 185));



        // Extract username from JWT
        String username = JwtTools.getPayloadValue("sub");
        if (username == null || username.equals("Invalid Token") || username.equals("Error Processing Token")) {
            username = "Unknown User";
        }

        // Profile label with username
        JLabel profileLabel = new JLabel(username, JLabel.RIGHT);
        profileLabel.setFont(new Font("Arial", Font.BOLD, 20));
        profileLabel.setForeground(Color.WHITE);

        JPanel profilePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        profilePanel.setBackground(new Color(41, 128, 185));
        profilePanel.add(profileLabel);

        add(profilePanel, BorderLayout.EAST);
    }
}
