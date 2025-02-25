package org.hahen.ticketEase.pages;

import org.hahen.ticketEase.components.SidebarComponent;
import org.hahen.ticketEase.components.HeaderComponent;
import org.hahen.ticketEase.components.FooterComponent;
import org.hahen.ticketEase.configurations.GlobalVariables;

import javax.swing.*;
import java.awt.*;

public class DashBoardPage {
    private JFrame frame;
    private JPanel contentPanel;
    public CardLayout cardLayout;

    public DashBoardPage() throws Exception {

        frame = new JFrame("IT Support Ticket System");
        frame.setSize(1600, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        SidebarComponent sidebar = new SidebarComponent(this);
        HeaderComponent header = new HeaderComponent();
        FooterComponent footer = new FooterComponent();

        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);

        contentPanel.add(new HomePage().createPage(), "Home");
        contentPanel.add(new TicketsPage().createPage(), "Tickets");
        contentPanel.add(new NewTicketPage().createPage(), "NewTicket");
       // contentPanel.add(new UpdateTicketPage().createPage(), "UpdateTicket");

        GlobalVariables.GLOBAL_DashBoard = this;

        frame.add(header, BorderLayout.NORTH);
        frame.add(sidebar, BorderLayout.WEST);
        frame.add(contentPanel, BorderLayout.CENTER);
        frame.add(footer, BorderLayout.SOUTH);
    }

    public void switchPage(String pageName) throws Exception {
        this.cardLayout = GlobalVariables.GLOBAL_DashBoard.cardLayout;
        cardLayout.show(contentPanel, pageName);
    }

    public void show() {
        frame.setVisible(true);
    }

    public void showTicketDetails() throws Exception {
        this.cardLayout = GlobalVariables.GLOBAL_DashBoard.cardLayout;
        TicketDetailsPage ticketDetailsPage = new TicketDetailsPage();
        contentPanel.add(ticketDetailsPage.createPage(), "TicketDetails");
        cardLayout.show(contentPanel, "TicketDetails");
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public void showTickets() throws Exception {
        this.cardLayout = GlobalVariables.GLOBAL_DashBoard.cardLayout;
        if (!isPageAlreadyAdded("Tickets")) {
            contentPanel.add(new TicketsPage().createPage(), "Tickets");
        }
        cardLayout.show(contentPanel, "Tickets");
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private boolean isPageAlreadyAdded(String pageName) {
        for (Component comp : contentPanel.getComponents()) {
            if (pageName.equals(comp.getName())) {
                return true;
            }
        }
        return false;
    }

    public void updateTicket() throws Exception {
        this.cardLayout = GlobalVariables.GLOBAL_DashBoard.cardLayout;
        if (!isPageAlreadyAdded("UpdateTicket")) {
            contentPanel.add(new UpdateTicketPage().createPage(), "UpdateTicket");
        }
        cardLayout.show(contentPanel, "UpdateTicket");
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}
