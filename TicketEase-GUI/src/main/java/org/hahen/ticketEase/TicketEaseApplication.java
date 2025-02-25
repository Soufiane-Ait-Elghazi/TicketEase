package org.hahen.ticketEase;

import org.hahen.ticketEase.pages.LoginPage;

import javax.swing.*;

public class TicketEaseApplication {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginPage::new);
    }
}
