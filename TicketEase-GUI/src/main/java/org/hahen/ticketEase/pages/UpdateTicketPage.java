package org.hahen.ticketEase.pages;

import org.hahen.ticketEase.configurations.GlobalVariables;
import org.hahen.ticketEase.models.TicketDto;
import org.hahen.ticketEase.services.TicketService;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

import static org.hahen.ticketEase.configurations.GlobalVariables.TICKET_ID;
import static org.hahen.ticketEase.services.TicketCategoryService.fetchTicketCategoriesNames;

public class UpdateTicketPage {
    private Long ticketId;
    private TicketDto ticketDto;
    private boolean isEditable = true ;

    public UpdateTicketPage() throws Exception {
        this.ticketId = (long) TICKET_ID;
        this.ticketDto = TicketService.getTicketById(ticketId);
    }

    public JPanel createPage() throws Exception {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("Update Ticket");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(new Color(56, 93, 149));
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Ticket Title
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Ticket Title:"), gbc);
        gbc.gridx = 1;
        JTextField titleField = new JTextField(ticketDto.getTitle(), 30);
        titleField.setFont(new Font("Arial", Font.PLAIN, 16));
        titleField.setEnabled(isEditable); // Désactive si l'utilisateur n'a pas les droits
        formPanel.add(titleField, gbc);

        // Ticket Description
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        JTextArea descriptionArea = new JTextArea(ticketDto.getDescription(), 7, 30);
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 16));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEnabled(isEditable);
        JScrollPane descriptionScroll = new JScrollPane(descriptionArea);
        formPanel.add(descriptionScroll, gbc);

        // Priority Combo Box
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Priority:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> priorityCombo = new JComboBox<>(new String[]{"LOW", "MEDIUM", "HIGHT"});
        priorityCombo.setSelectedItem(ticketDto.getPriority());
        priorityCombo.setFont(new Font("Arial", Font.PLAIN, 16));
        priorityCombo.setEnabled(isEditable);
        formPanel.add(priorityCombo, gbc);

        // Category Combo Box
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> categoryCombo = new JComboBox<>(fetchTicketCategoriesNames());
        categoryCombo.setSelectedItem(ticketDto.getCategory());
        categoryCombo.setFont(new Font("Arial", Font.PLAIN, 16));
        categoryCombo.setEnabled(isEditable);
        formPanel.add(categoryCombo, gbc);

        // Status Combo Box
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"NEW", "IN_PROGRESS", "RESOLVED"});
        statusCombo.setSelectedItem(ticketDto.getStatus());
        statusCombo.setFont(new Font("Arial", Font.PLAIN, 16));
        statusCombo.setEnabled(isEditable);
        formPanel.add(statusCombo, gbc);

        // Update Button
        gbc.gridx = 1; gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        JButton updateButton = new JButton("Update Ticket");
        updateButton.setBackground(new Color(57, 103, 163));
        updateButton.setForeground(Color.WHITE);
        updateButton.setFont(new Font("Arial", Font.BOLD, 18));
        updateButton.setPreferredSize(new Dimension(200, 50));
        updateButton.setEnabled(isEditable); // Désactive le bouton si pas d'accès

        updateButton.addActionListener(e -> {
            if (titleField.getText().isEmpty() || descriptionArea.getText().isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Please fill in all fields.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            } else {
                ticketDto.setTitle(titleField.getText());
                ticketDto.setDescription(descriptionArea.getText());
                ticketDto.setPriority(priorityCombo.getSelectedItem().toString());
                ticketDto.setCategory(categoryCombo.getSelectedItem().toString());
                ticketDto.setStatus(statusCombo.getSelectedItem().toString());

                try {
                    TicketService.updateTicket(ticketId, ticketDto);
                    JOptionPane.showMessageDialog(panel, "Ticket updated successfully!");
                    GlobalVariables.GLOBAL_DashBoard.showTickets();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(panel, "Error updating ticket.", "Update Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        formPanel.add(updateButton, gbc);
        panel.add(formPanel, BorderLayout.CENTER);

        return panel;
    }
}
