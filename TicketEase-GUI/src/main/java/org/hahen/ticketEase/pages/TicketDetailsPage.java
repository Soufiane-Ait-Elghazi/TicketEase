package org.hahen.ticketEase.pages;

import org.hahen.ticketEase.configurations.GlobalVariables;
import org.hahen.ticketEase.models.TicketCommentDto;
import org.hahen.ticketEase.models.TicketDto;
import org.hahen.ticketEase.services.TicketCommentService;
import org.hahen.ticketEase.services.TicketService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TicketDetailsPage {
    private Long ticketId;
    private TicketDto ticketDto;
    private JButton backButton;
    private JButton addCommentButton;
    private JTextArea commentInput;
    private JPanel commentsPanel;

    // Constructor accepts a reference to the dashboard for page switching
    public TicketDetailsPage( ) {
        this.ticketId = GlobalVariables.TICKET_ID;
        this.backButton = new JButton("Back");
        this.addCommentButton = new JButton("Add Comment");
        this.commentInput = new JTextArea(5, 30);  // Set size for the comment input area
        this.commentsPanel = new JPanel(); // Panel for displaying comments
        init();
    }

    private void init() {
        addCommentButton.setToolTipText("Click to add a new comment"); // Tooltip for add comment button
        backButton.setToolTipText("Go back to the previous page"); // Tooltip for back button
    }

    public JPanel createPage() throws Exception {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(20, 20));  // Add spacing between components

        // Fetch ticket details by ID
        ticketDto = TicketService.getTicketById(ticketId);

        // Left panel for ticket details and comments
        JPanel ticketDetailsPanel = new JPanel();
        ticketDetailsPanel.setLayout(new BoxLayout(ticketDetailsPanel, BoxLayout.Y_AXIS));
        ticketDetailsPanel.setBackground(new Color(211, 210, 210));  // Light gray background for ticket details

        ticketDetailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding around the edges

        // Create the labels with customized styles
        ticketDetailsPanel.add(createStyledLabel("Ticket ID:", String.valueOf(ticketDto.getId()), new Color(211, 210, 210, 255), Color.DARK_GRAY));
        ticketDetailsPanel.add(createStyledLabel("Ticket Key:", ticketDto.getKey(), new Color(211, 210, 210), Color.DARK_GRAY));
        ticketDetailsPanel.add(createStyledLabel("Title:", ticketDto.getTitle(), new Color(211, 210, 210), Color.BLACK));
        ticketDetailsPanel.add(createStyledLabel("Description:", ticketDto.getDescription(), new Color(211, 210, 210), Color.BLACK));
        ticketDetailsPanel.add(createStyledLabel("Priority:", ticketDto.getPriority(), new Color(211, 210, 210), Color.BLACK));
        ticketDetailsPanel.add(createStyledLabel("Status:", ticketDto.getStatus(), new Color(211, 210, 210), Color.BLACK));
        ticketDetailsPanel.add(createStyledLabel("Category:", ticketDto.getCategory(), new Color(211, 210, 210), Color.BLACK));

        // Styled for metadata (date, creator, modified by)
        ticketDetailsPanel.add(createStyledLabel("Creation Date:", ticketDto.getCreationDate(), new Color(211, 210, 210), Color.GRAY));
        ticketDetailsPanel.add(createStyledLabel("Last Modified Date:", ticketDto.getLastModifiedDate(), new Color(211, 210, 210), Color.GRAY));
        ticketDetailsPanel.add(createStyledLabel("Created By:", getSafeString(ticketDto.getCreatedBy()), new Color(211, 210, 210), Color.GRAY));
        ticketDetailsPanel.add(createStyledLabel("Modified By:", getSafeString(ticketDto.getModifiedBy()), new Color(211, 210, 210), Color.GRAY));

        // Back Button Styling
        ticketDetailsPanel.add(backButton); // Add the back button at the bottom
        backButton.setBackground(new Color(211, 210, 210));
        backButton.setForeground(Color.WHITE);
        backButton.setPreferredSize(new Dimension(300, 40));
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBorder(BorderFactory.createLineBorder(new Color(210, 210, 211), 2));

        // Add comment field and button below the ticket details (on the same side)
        JPanel commentSectionPanel = new JPanel();
        commentSectionPanel.setLayout(new BoxLayout(commentSectionPanel, BoxLayout.Y_AXIS));
        commentSectionPanel.setBackground(new Color(255, 255, 255));  // White background for the comment section

        // Add the comment input field and the "Add Comment" button
        commentSectionPanel.add(createLabel("Add a Comment:", Color.DARK_GRAY));
        commentSectionPanel.add(new JScrollPane(commentInput));  // Scrollable comment input area
        commentSectionPanel.add(addCommentButton);
        ticketDetailsPanel.add(commentSectionPanel);

        // Right panel for displaying comments (with scroll)
        JPanel commentsPanel = new JPanel();
        commentsPanel.setLayout(new BoxLayout(commentsPanel, BoxLayout.Y_AXIS));
        commentsPanel.setBackground(new Color(255, 255, 255));


        JScrollPane commentsScrollPane = new JScrollPane(commentsPanel);
        commentsScrollPane.setPreferredSize(new Dimension(400, 300));
        commentsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        List<TicketCommentDto> comments = TicketCommentService.getTicketComments(ticketId);
        commentsPanel.add(createLabel("Comments:", Color.DARK_GRAY));
        if (comments != null && !comments.isEmpty()) {
            int counter = 0;
            for (TicketCommentDto comment : comments) {
                JPanel commentPanel = new JPanel();
                commentPanel.setLayout(new BoxLayout(commentPanel, BoxLayout.Y_AXIS));
                commentPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
                commentPanel.setBackground(counter % 2 == 0 ? new Color(245, 245, 245) : new Color(255, 255, 255)); // Alternating background color
                commentPanel.setPreferredSize(new Dimension(400, 100));

                // Add comment content with styled text
                commentPanel.add(createLabel("<html><b>" + getSafeString(comment.getCreatedBy()) + "</b></html>", Color.BLACK));
                commentPanel.add(createLabel("Created on: " + getSafeString(comment.getCreationDate()), new Color(130, 130, 130)));
                commentPanel.add(createLabel("<html>" + comment.getDescription() + "</html>", Color.BLACK));

                commentsPanel.add(commentPanel);
                counter++;
            }
        } else {
            commentsPanel.add(createLabel("No comments available.", Color.GRAY));
        }

        // Back button action
        backButton.addActionListener(e -> {
            try {
                System.out.println("Back button clicked. Going back to Tickets page.");
                GlobalVariables.GLOBAL_DashBoard.showTickets();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Add comment action
        addCommentButton.addActionListener(e -> {
            String newComment = commentInput.getText();
            if (!newComment.trim().isEmpty()) {
                TicketCommentDto newTicketComment = new TicketCommentDto();
                newTicketComment.setDescription(newComment);

                // Add the new comment to the service
                try {
                    TicketCommentService.addNewComment(ticketId, newTicketComment);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error adding the comment.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                commentInput.setText("");  // Clear the input area

                // Refresh comments panel only (not the entire page)
                try {
                    List<TicketCommentDto> updatedComments = TicketCommentService.getTicketComments(ticketId);

                    // Clear existing comments
                    commentsPanel.removeAll();

                    // Display updated comments
                    commentsPanel.add(createLabel("Comments:", Color.DARK_GRAY));
                    int counter = 0;
                    for (TicketCommentDto comment : updatedComments) {
                        JPanel commentPanel = new JPanel();
                        commentPanel.setLayout(new BoxLayout(commentPanel, BoxLayout.Y_AXIS));
                        commentPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
                        commentPanel.setBackground(counter % 2 == 0 ? new Color(245, 245, 245) : new Color(255, 255, 255)); // Alternating background color
                        commentPanel.setPreferredSize(new Dimension(400, 100));

                        commentPanel.add(createLabel("<html><b>" + getSafeString(comment.getCreatedBy()) + "</b></html>", Color.BLACK));
                        commentPanel.add(createLabel("Created on: " + getSafeString(comment.getCreationDate()), new Color(130, 130, 130)));
                        commentPanel.add(createLabel("<html>" + comment.getDescription() + "</html>", Color.BLACK));

                        commentsPanel.add(commentPanel);
                        counter++;
                    }

                    commentsPanel.revalidate();
                    commentsPanel.repaint();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error refreshing the comments.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a comment!", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Adding the panels to the mainPanel (split layout)
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, ticketDetailsPanel, commentsScrollPane);
        splitPane.setDividerLocation(600);  // Set initial divider position
        splitPane.setOneTouchExpandable(true);

        mainPanel.add(splitPane, BorderLayout.CENTER);

        return mainPanel;  // Return the main panel
    }





    private Component createStyledLabel(String s, String title, Color color, Color background) {
        JLabel label = new JLabel("<html><b>" + title + "</b>: " + s + "</html>");
        label.setForeground(color);
        label.setBackground(background);
        label.setOpaque(true);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        return label;
    }



    private JPanel createCommentPanel(TicketCommentDto comment, int counter) {
        JPanel commentPanel = new JPanel();
        commentPanel.setLayout(new BoxLayout(commentPanel, BoxLayout.Y_AXIS));
        commentPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        commentPanel.setBackground(counter % 2 == 0 ? new Color(245, 245, 245) : new Color(255, 255, 255));
        commentPanel.setPreferredSize(new Dimension(400, 100));

        // Add comment content with styled text
        commentPanel.add(createLabel("<html><b>" + getSafeString(comment.getCreatedBy()) + "</b></html>", Color.BLACK));
        commentPanel.add(createLabel("Created on: " + getSafeString(comment.getCreationDate()), new Color(130, 130, 130)));
        commentPanel.add(createLabel("<html>" + comment.getDescription() + "</html>", Color.BLACK));

        return commentPanel;
    }

    // Add a new comment to the service and refresh the comment section
    private void addComment() {
        String newComment = commentInput.getText();
        if (!newComment.trim().isEmpty()) {
            TicketCommentDto newTicketComment = new TicketCommentDto();
            newTicketComment.setDescription(newComment);

            // Add the new comment to the service (make sure the method is working as expected)
            try {
                TicketCommentService.addNewComment(ticketId, newTicketComment);
                commentInput.setText("");  // Clear the input area
                refreshCommentsPanel();   // Refresh comments after adding
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error adding the comment.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a comment!", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Refresh the comments section after adding a new comment
    private void refreshCommentsPanel() {
        try {
            List<TicketCommentDto> updatedComments = TicketCommentService.getTicketComments(ticketId);

            commentsPanel.removeAll();  // Clear the existing comments panel

            commentsPanel.add(createLabel("Comments:", Color.DARK_GRAY));
            int counter = 0;
            for (TicketCommentDto comment : updatedComments) {
                commentsPanel.add(createCommentPanel(comment, counter));
                counter++;
            }

            commentsPanel.revalidate();  // Revalidate layout
            commentsPanel.repaint();  // Repaint the panel to reflect changes
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error refreshing the comments.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Utility method to handle null values safely
    private String getSafeString(String value) {
        return (value == null || value.trim().isEmpty()) ? "Unknown" : value;
    }

    // Utility method to create a JLabel with custom font and color
    private JLabel createLabel(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        label.setForeground(color);
        return label;
    }

    // Navigate back to the previous page
    private void navigateBack() {
        try {
            System.out.println("Back button clicked. Going back to Tickets page.");
            GlobalVariables.GLOBAL_DashBoard.showTickets();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
