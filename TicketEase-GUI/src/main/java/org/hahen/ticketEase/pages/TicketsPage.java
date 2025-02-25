package org.hahen.ticketEase.pages;

import org.hahen.ticketEase.components.ButtonEditor;
import org.hahen.ticketEase.components.ButtonRenderer;
import org.hahen.ticketEase.components.PaginationComponent;
import org.hahen.ticketEase.enums.TicketPriority;
import org.hahen.ticketEase.models.SearchTicketFormDto;
import org.hahen.ticketEase.models.TicketDto;
import org.hahen.ticketEase.services.TicketService;
import org.hahen.ticketEase.util.PaginatedResponse;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import static org.hahen.ticketEase.configurations.GlobalVariables.PAGE_SIZE;
import static org.hahen.ticketEase.services.TicketCategoryService.fetchTicketCategoriesNames;

public class TicketsPage {


    private JTable ticketTable;
    private DefaultTableModel tableModel;
    private PaginationComponent paginationComponent;
    private JTextField keyField;
    private JTextField titleField;
    private JComboBox<String> priorityDropdown;
    private JComboBox<String> categoryDropdown;
    private JComboBox<String> statusDropdown;
    private int currentPage = 0;
    private int totalPages = 1;

    public TicketsPage() {}

    public JPanel createPage() throws Exception {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        keyField = createTextField(5);
        searchPanel.add(new JLabel("Key:"));
        searchPanel.add(keyField);

        titleField = createTextField(10);
        searchPanel.add(new JLabel("Title:"));
        searchPanel.add(titleField);

        categoryDropdown = createDropdown(fetchTicketCategoriesNames());
        searchPanel.add(new JLabel("Category:"));
        searchPanel.add(categoryDropdown);

        statusDropdown = createDropdown(new String[]{"", "New", "In progress", "High"});
        searchPanel.add(new JLabel("Status:"));
        searchPanel.add(statusDropdown);

        priorityDropdown = createDropdown(new String[]{"", "Low", "Medium", "High"});
        searchPanel.add(new JLabel("Priority:"));
        searchPanel.add(priorityDropdown);

        JButton searchButton = new JButton("Search");
        styleButton(searchButton);
        searchButton.addActionListener(e -> fetchTickets(currentPage));
        searchPanel.add(searchButton);

        panel.add(searchPanel, BorderLayout.NORTH);

        String[] columns = {"Ticket Id","Ticket Key", "Title", "Priority", "Status", "Actions"};
        tableModel = new DefaultTableModel(columns, 0);
        ticketTable = new JTable(tableModel);
        ticketTable.setRowHeight(40);
        panel.add(new JScrollPane(ticketTable), BorderLayout.CENTER);

        paginationComponent = new PaginationComponent(totalPages, this::fetchTickets);
        panel.add(paginationComponent, BorderLayout.SOUTH);

        fetchTickets(currentPage);
        return panel;
    }

    private JTextField createTextField(int columns) {
        JTextField textField = new JTextField(columns);
        textField.setPreferredSize(new Dimension(120, 30));
        return textField;
    }

    private JComboBox<String> createDropdown(String[] options) {
        JComboBox<String> dropdown = new JComboBox<>(options);
        dropdown.setPreferredSize(new Dimension(120, 30));
        dropdown.setSelectedIndex(0);
        return dropdown;
    }



    private void styleButton(JButton button) {
        button.setPreferredSize(new Dimension(100, 30));
        button.setBackground(new Color(51, 122, 183));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
    }

    private void fetchTickets(int page) {
        try {
            currentPage = page;
            SearchTicketFormDto searchTicketFormDto = new SearchTicketFormDto();

            // Trimmed title and key fields
            searchTicketFormDto.setTitle(titleField.getText().trim());
            searchTicketFormDto.setKey(keyField.getText().trim());

            // Get selected values safely
            String selectedPriority = (String) priorityDropdown.getSelectedItem();
            String selectedStatus = (String) statusDropdown.getSelectedItem();
            String selectedCategory = (String) categoryDropdown.getSelectedItem();

            // Convert text values to Enums only if not null/empty
            if (selectedStatus != null && !selectedStatus.isEmpty()) {
                searchTicketFormDto.setStatus(selectedStatus);
            }

            if (selectedPriority != null && !selectedPriority.isEmpty()) {
                searchTicketFormDto.setPriority(TicketPriority.fromTextToPrio(selectedPriority).toString());
            }

            searchTicketFormDto.setCategory(selectedCategory);

            // Fetch filtered tickets
            PaginatedResponse<TicketDto> paginatedResponse =
                    TicketService.getTicketsWithFilters(currentPage, PAGE_SIZE, searchTicketFormDto);

            // Update UI
            updateTable(paginatedResponse.getContent());

            // Update pagination
            int totalTickets = (int) paginatedResponse.getTotalElements();
            totalPages = (int) Math.ceil((double) totalTickets / PAGE_SIZE);
            paginationComponent.setTotalPages(totalPages);
            paginationComponent.setCurrentPage(currentPage);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error fetching filtered tickets: " + e.getMessage());
        }
    }


    private void updateTable(List<TicketDto> tickets) {
        tableModel.setRowCount(0);
        for (TicketDto ticket : tickets) {
            tableModel.addRow(new Object[]{
                    ticket.getId(),
                    ticket.getKey(),
                    ticket.getTitle(),
                    ticket.getPriority(),
                    ticket.getStatus(),
                    "Actions"
            });
        }
        ticketTable.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        ticketTable.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(ticketTable));
    }
}

