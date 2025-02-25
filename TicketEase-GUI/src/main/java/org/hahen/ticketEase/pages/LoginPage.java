package org.hahen.ticketEase.pages;

import net.miginfocom.swing.MigLayout;
import org.hahen.ticketEase.configurations.GlobalVariables;
import org.hahen.ticketEase.models.LoginFormDto;
import org.hahen.ticketEase.models.TokenDto;
import org.hahen.ticketEase.services.AuthenticationService;
import org.hahen.ticketEase.util.JwtTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static org.hahen.ticketEase.enums.Scope.IT_SUPPORT;

public class LoginPage extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel statusLabel;

    public LoginPage() {
        initializeUI();
    }


    private void initializeUI() {
        setTitle("Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen
        setLayout(new MigLayout("wrap 2", "[grow,fill]", "[]10[]10[]10[]"));

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        Font customFont = new Font("Segoe UI", Font.PLAIN, 14);
        UIManager.put("Label.font", customFont);
        UIManager.put("TextField.font", customFont);
        UIManager.put("PasswordField.font", customFont);
        UIManager.put("Button.font", customFont);

        // Username label and field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        add(usernameLabel);

        usernameField = new JTextField(20);
        usernameField.setFont(customFont);
        usernameField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        usernameField.setBackground(Color.WHITE);
        usernameField.setPreferredSize(new Dimension(280, 40));
        usernameField.setCaretColor(Color.DARK_GRAY); // Darker caret for better visibility
        usernameField.setBorder(BorderFactory.createCompoundBorder(
                usernameField.getBorder(),
                BorderFactory.createEmptyBorder(5, 10, 5, 10) // Padding inside the field
        ));
        add(usernameField);


        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setFont(customFont);
        passwordField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        passwordField.setBackground(Color.WHITE);
        passwordField.setPreferredSize(new Dimension(280, 40));
        passwordField.setCaretColor(Color.DARK_GRAY); // Darker caret for better visibility
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                passwordField.getBorder(),
                BorderFactory.createEmptyBorder(5, 10, 5, 10) // Padding inside the field
        ));
        add(passwordField);

        // Login button with enhanced styling
        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(30, 136, 229)); // Blue color
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginButton.setPreferredSize(new Dimension(280, 45)); // Bigger button
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createLineBorder(new Color(30, 136, 229), 2));
        loginButton.setOpaque(true);
        loginButton.setBorderPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Pointer on hover
        loginButton.setFocusPainted(false); // Remove focus outline
        add(loginButton, "span, align center");

        // Status label with initial empty text
        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        statusLabel.setForeground(Color.RED);
        add(statusLabel, "span, align center");

        // Add login button action listener
        loginButton.addActionListener(new LoginActionListener());

        setVisible(true); // Display the frame
    }

    // Action listener for login button click
    private class LoginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            handleLogin();
        }
    }

    // Handle login functionality
    private void handleLogin() {
        new Thread(() -> {
            try {

                LoginFormDto loginFormDto = createLoginForm();
                AuthenticationService.login(loginFormDto);
                SwingUtilities.invokeLater(() -> {
                    statusLabel.setText("Login Successful");
                   GlobalVariables.IT_SUPPORT_USER  = JwtTools.hasItSupportScope(GlobalVariables.getAuthToken());
                });

                dispose();
                new DashBoardPage().show();

            } catch (IOException ex) {
                SwingUtilities.invokeLater(() -> statusLabel.setText("Login Failed"));
                ex.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        }).start();
    }

    // Create LoginFormDto from user input
    private LoginFormDto createLoginForm() {
        LoginFormDto loginFormDto = new LoginFormDto();
         loginFormDto.setUsername(usernameField.getText());
         loginFormDto.setPassword(new String(passwordField.getPassword()));
        return loginFormDto;
    }
}
