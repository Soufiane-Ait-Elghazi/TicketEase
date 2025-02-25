package org.hahen.ticketEase.components;


import javax.swing.*;
import java.awt.*;

public class FooterComponent extends JPanel {

    public FooterComponent() {
        // Set layout for the footer
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setBackground(new Color(52, 152, 219));  // Footer background color

        // Add footer content
        JLabel footerLabel = new JLabel("© 2025 IT Support Ticket System. All rights reserved.");
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        footerLabel.setForeground(Color.WHITE);

        add(footerLabel);
    }
}

