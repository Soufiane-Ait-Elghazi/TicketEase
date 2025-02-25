package org.hahen.ticketEase.pages;

import org.hahen.ticketEase.enums.TicketPriority;
import org.hahen.ticketEase.models.TicketDto;
import org.hahen.ticketEase.services.TicketService;

import javax.swing.*;
import java.awt.*;

import static org.hahen.ticketEase.services.TicketCategoryService.fetchTicketCategoriesNames;


public class NewTicketPage {

    private DashBoardPage dashboard;

    // Constructor accepts a reference to the dashboard for page switching
    public NewTicketPage(DashBoardPage dashboard) {
        this.dashboard = dashboard;
    }

    // Creates and returns the new ticket creation panel
    public JPanel createPage() throws Exception {
        JPanel panel = new JPanel(new BorderLayout());

        // Title label at the top
        JLabel titleLabel = new JLabel("Create New Ticket");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));  // Increased font size for title
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(new Color(56, 93, 149));  // Professional color
        panel.add(titleLabel, BorderLayout.NORTH);

        // Form panel in the center with GridBagLayout for flexible layout management
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15); // Increased padding between components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Ticket Title Label and Field
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Ticket Title:"), gbc);
        gbc.gridx = 1;
        JTextField titleField = new JTextField(30);  // Increased width of text field
        titleField.setFont(new Font("Arial", Font.PLAIN, 16));  // Larger text in text field
        formPanel.add(titleField, gbc);

        // Ticket Description Label and Text Area
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        JTextArea descriptionArea = new JTextArea(7, 30);  // Increased height and width
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 16));  // Larger text in textarea
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane descriptionScroll = new JScrollPane(descriptionArea);
        formPanel.add(descriptionScroll, gbc);

        // Ticket Priority Label and Combo Box
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Priority:"), gbc);
        gbc.gridx = 1;
        String[] priorities = {"Low", "Medium", "High"};
        JComboBox<String> priorityCombo = new JComboBox<>(priorities);
        priorityCombo.setFont(new Font("Arial", Font.PLAIN, 16));  // Larger text in combo box
        formPanel.add(priorityCombo, gbc);

        // Fetch Categories and Add to ComboBox
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;

        // Fetch categories dynamically
        JComboBox<String> categoryCombo = new JComboBox<>(fetchTicketCategoriesNames());  // Populate ComboBox with categories
        categoryCombo.setFont(new Font("Arial", Font.PLAIN, 16));  // Larger text in combo box
        formPanel.add(categoryCombo, gbc);

        // Submit Button to handle ticket creation
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        JButton submitButton = new JButton("Submit Ticket");
        submitButton.setBackground(new Color(57, 103, 163)); // Matching color
        submitButton.setForeground(Color.WHITE);  // White text for contrast
        submitButton.setFont(new Font("Arial", Font.BOLD, 18)); // Increased font size for button
        submitButton.setPreferredSize(new Dimension(200, 50)); // Increased button size
        submitButton.addActionListener(e -> {
            // Validate fields before submitting
            if (titleField.getText().isEmpty() || descriptionArea.getText().isEmpty() || categoryCombo.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(panel, "Please fill in all fields.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // Create a new TicketDto object with form data
                TicketDto newTicketDto = new TicketDto();
                newTicketDto.setTitle(titleField.getText());
                newTicketDto.setDescription(descriptionArea.getText());
                newTicketDto.setPriority(TicketPriority.fromTextToPrio(priorityCombo.getSelectedItem().toString()).toString());
                newTicketDto.setCategory(categoryCombo.getSelectedItem().toString());

                // Call TicketService to create the ticket
                try {
                    TicketDto createdTicketDto = TicketService.newTicket(newTicketDto);
                    JOptionPane.showMessageDialog(panel, "Ticket submitted successfully!");
                    // Optionally, switch to the Tickets page after submission:
                    dashboard.showTicketDetails(createdTicketDto.getId(), dashboard); // Example, pass actual ID
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(panel, "Error occurred while submitting the ticket.", "Submission Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        formPanel.add(submitButton, gbc);

        panel.add(formPanel, BorderLayout.CENTER);

        return panel;
    }
}
