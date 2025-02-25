package org.hahen.ticketEase.pages;

import org.hahen.ticketEase.components.SidebarComponent;
import org.hahen.ticketEase.components.HeaderComponent;
import org.hahen.ticketEase.components.FooterComponent;

import javax.swing.*;
import java.awt.*;

public class DashBoardPage {
    private JFrame frame;
    private JPanel contentPanel;
    private CardLayout cardLayout;

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

        contentPanel.add(new HomePage(this).createPage(), "Home");
        contentPanel.add(new TicketsPage(this).createPage(), "Tickets");
        contentPanel.add(new NewTicketPage(this).createPage(), "NewTicket");

        frame.add(header, BorderLayout.NORTH);
        frame.add(sidebar, BorderLayout.WEST);
        frame.add(contentPanel, BorderLayout.CENTER);
        frame.add(footer, BorderLayout.SOUTH);
    }

    public void switchPage(String pageName, DashBoardPage dashBoardPage) throws Exception {
        this.cardLayout = dashBoardPage.cardLayout;
        cardLayout.show(contentPanel, pageName);
    }

    public void show() {
        frame.setVisible(true);
    }

    public void showTicketDetails(Long ticketId,DashBoardPage dashBoardPage) throws Exception {
        this.cardLayout = dashBoardPage.cardLayout ;
        TicketDetailsPage ticketDetailsPage = new TicketDetailsPage(ticketId, this);
        JPanel ticketPanel = ticketDetailsPage.createPage();
        contentPanel.removeAll();
        contentPanel.add(ticketPanel, "Ticket Details");
        cardLayout.show(contentPanel, "Ticket Details");

       /* contentPanel.add(new TicketDetailsPage(ticketId,this).createPage(), "TicketDetails");
        switchPage("Tickets",this);**/
    }

    public void showTickets(DashBoardPage dashBoardPage) throws Exception {
        this.cardLayout = dashBoardPage.cardLayout ;
        contentPanel.add(new TicketsPage(this).createPage(), "Tickets");
        switchPage("Tickets",this);
    }
}
