package org.hahen.ticketEase.components;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

import static org.hahen.ticketEase.configurations.GlobalVariables.IT_SUPPORT_USER;

public class ButtonRenderer extends JPanel implements TableCellRenderer {
    public ButtonRenderer() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(new JButton("View"));
        if(IT_SUPPORT_USER){
            add(new JButton("Edit"));
        }
        add(new JButton("Delete"));
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return this;
    }
}