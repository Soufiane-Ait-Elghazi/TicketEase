package org.hahen.ticketEase.components;

import org.hahen.ticketEase.pages.DashBoardPage;
import org.hahen.ticketEase.services.AuthenticationService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SidebarComponent extends JPanel {
    private final DashBoardPage dashBoardPage;

    public SidebarComponent(DashBoardPage dashBoardPage) {
        this.dashBoardPage = dashBoardPage;

        // Sidebar layout and styling
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(238, 239, 243));
        setPreferredSize(new Dimension(250, 600));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));


        // Buttons for navigation
        JButton homeButton = createSidebarButton("Home", "Home");
        JButton myTicketsButton = createSidebarButton("Tickets", "Tickets");
        JButton newTicketButton = createSidebarButton("New Ticket", "NewTicket");
        JButton logoutButton = createRoundedButton("Logout");


        add(Box.createRigidArea(new Dimension(0, 20)));
        add(homeButton);
        add(myTicketsButton);
        add(newTicketButton);

        add(Box.createVerticalGlue()); // Push logout button to the bottom
        add(logoutButton);
    }

    // Creates a standard sidebar button
    private JButton createSidebarButton(String text, final String pageName) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setForeground(Color.BLACK);
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(16, 2, 2), 1, true));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(200, 40));
        button.setMaximumSize(new Dimension(200, 40));
        button.setOpaque(true);

        // Button click event
        button.addActionListener(e -> {
            try {
                dashBoardPage.switchPage(pageName,dashBoardPage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(33, 243, 117)); // Blue hover
                button.setForeground(Color.WHITE);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.WHITE); // Default back
                button.setForeground(Color.BLACK);
            }
        });

        return button;
    }


    private JButton createRoundedButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(244, 67, 54)); // Red logout color
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(200, 50, 50), 1, true));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(200, 40));
        button.setMaximumSize(new Dimension(200, 40));
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(211, 47, 47)); // Darker red on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(244, 67, 54)); // Reset color
            }
        });

        // Logout action with confirmation popup
        button.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to log out?",
                    "Confirm Logout",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (choice == JOptionPane.YES_OPTION) {
                try {
                    AuthenticationService.logOut();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                System.exit(0);
            }
        });

        return button;
    }

}
