package org.hahen.ticketEase.components;

import org.hahen.ticketEase.configurations.GlobalVariables;
import org.hahen.ticketEase.pages.DashBoardPage;
import org.hahen.ticketEase.services.TicketService;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;

public class ButtonEditor extends AbstractCellEditor implements TableCellEditor {

    private final JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private final JButton viewButton = new JButton("View");
    private final JButton editButton = new JButton("Edit");
    private final JButton deleteButton = new JButton("Delete");
    private Long ticketId;

    public ButtonEditor(JTable table) {
        panel.add(viewButton);
        panel.add(editButton);
        panel.add(deleteButton);


        viewButton.addActionListener(e -> {
            try {
                GlobalVariables.TICKET_ID = Math.toIntExact(ticketId);
                GlobalVariables.GLOBAL_DashBoard.showTicketDetails();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Edit button action
        editButton.addActionListener(e -> {
            try {
                GlobalVariables.TICKET_ID = Math.toIntExact(ticketId);
                GlobalVariables.GLOBAL_DashBoard.updateTicket();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        // Delete button action
        deleteButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want delete this ticket ?",
                    "Confirm Logout",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (choice == JOptionPane.YES_OPTION) {
                try {
                    if(TicketService.deleteTicketById(ticketId))
                        GlobalVariables.GLOBAL_DashBoard.showTickets();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

            }


        });
    }




    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        ticketId = (Long) table.getValueAt(row, 0);  // Ensure this pulls the correct ticketId
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return "";  // You could return something useful here if needed
    }
}
