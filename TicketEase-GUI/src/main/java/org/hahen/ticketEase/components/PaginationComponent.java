package org.hahen.ticketEase.components;

import javax.swing.*;
import java.awt.*;

public class PaginationComponent extends JPanel {
    private final JButton firstButton, prevButton, nextButton, lastButton;
    private final JLabel pageInfoLabel;
    private int currentPage;
    private int totalPages;
    private final PaginationListener paginationListener;

    public PaginationComponent(int totalPages, PaginationListener listener) {
        this.totalPages = Math.max(totalPages, 1); // Assure au moins 1 page
        this.paginationListener = listener;
        this.currentPage = 0;
        setLayout(new FlowLayout());

        firstButton = createButton("<< First", () -> goToPage(0));
        prevButton = createButton("< Prev", () -> goToPage(currentPage - 1));
        pageInfoLabel = new JLabel();
        nextButton = createButton("Next >", () -> goToPage(currentPage + 1));
        lastButton = createButton("Last >>", () -> goToPage(totalPages - 1));

        add(firstButton);
        add(prevButton);
        add(pageInfoLabel);
        add(nextButton);
        add(lastButton);

        updateDisplay();
    }

    private JButton createButton(String text, Runnable action) {
        JButton button = new JButton(text);
        button.addActionListener(e -> action.run());
        return button;
    }

    private void goToPage(int page) {
        if (page < 0 || page >= totalPages || page == currentPage) return;
        currentPage = page;
        updateDisplay();
        paginationListener.onPageChanged(currentPage);
    }

    private void updateDisplay() {
        pageInfoLabel.setText("Page " + (currentPage + 1) + " of " + totalPages);

        firstButton.setEnabled(currentPage > 0);
        prevButton.setEnabled(currentPage > 0);
        nextButton.setEnabled(currentPage < totalPages - 1);
        lastButton.setEnabled(currentPage < totalPages - 1);
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = Math.max(totalPages, 1);
        if (currentPage >= totalPages) {
            currentPage = totalPages - 1;
        }
        updateDisplay();
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public interface PaginationListener {
        void onPageChanged(int newPage);
    }
}
