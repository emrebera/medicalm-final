package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuestionView {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel panelMain;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField newUsernameField;
    private JPasswordField newPasswordField;
    private JLabel loginMessageLabel;
    private JLabel usernameLabel;
    private JLabel questionLabel;
    private JButton loginButton;
    private JButton createButton;
    private JButton nextButton;
    private JButton finishButton;
    private JButton nextDetailButton;
    private JButton finishDetailButton;
    private JButton initialLoginButton;
    private JButton initialCreateUserButton;
    private JRadioButton[] ratingButtons;
    private JTextArea explanationArea;
    private JTextArea suggestionArea;
    private JTextArea meditationArea;
    private JProgressBar progressBar;
    private JButton exitButton; // Added exit button

    private JTextArea anxietyArea;
    private JTextArea stressArea;
    private JTextArea depressionArea;
    private JTextArea panicDisorderArea;
    private JTextArea insomniaArea;

    private static final Color DARK_PURPLE = new Color(48, 25, 52);
    private static final Color SILVER = new Color(192, 192, 192);

    public QuestionView() {
        frame = new JFrame("MediCalm Mental Health Management System");
        cardLayout = new CardLayout();
        panelMain = new JPanel(cardLayout);

        // Initialize components for different cards (screens)
        initializeMainCard();
        initializeLoginCard();
        initializeCreateUserCard();
        initializeQuestionCard();
        initializeDetailsCard();
        initializeResultsCard();

        frame.add(panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    private void initializeMainCard() {
        JPanel mainCard = new JPanel(new BorderLayout());
        mainCard.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainCard.setBackground(DARK_PURPLE); // Dark purple background

        JLabel welcomeLabel = new JLabel("Welcome to MediCalm", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 28));
        welcomeLabel.setForeground(Color.WHITE);

        JTextArea descriptionArea = new JTextArea(
                "MediCalm is a tool designed to help you assess your mental health and provide personalized " +
                        "meditation suggestions. Please log in if you already have an account, or create a new user to get started."
        );
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 16));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEditable(false);
        descriptionArea.setOpaque(false);
        descriptionArea.setForeground(Color.LIGHT_GRAY);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        buttonPanel.setOpaque(false);
        initialLoginButton = new JButton("Login");
        initialCreateUserButton = new JButton("Create a User");

        // Style the buttons
        styleButton(initialLoginButton);
        styleButton(initialCreateUserButton);

        buttonPanel.add(initialLoginButton);
        buttonPanel.add(initialCreateUserButton);

        mainCard.add(welcomeLabel, BorderLayout.NORTH);
        mainCard.add(descriptionArea, BorderLayout.CENTER);
        mainCard.add(buttonPanel, BorderLayout.SOUTH);

        panelMain.add(mainCard, "Main");
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.BLACK);
        button.setBackground(SILVER);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE));
    }

    private void initializeLoginCard() {
        JPanel loginCard = new JPanel(new GridLayout(5, 2, 10, 10));
        loginCard.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        loginCard.setBackground(DARK_PURPLE);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        loginMessageLabel = new JLabel("", SwingConstants.CENTER);
        loginMessageLabel.setForeground(Color.RED);

        // Style the button
        styleButton(loginButton);

        loginCard.add(usernameLabel);
        loginCard.add(usernameField);
        loginCard.add(passwordLabel);
        loginCard.add(passwordField);
        loginCard.add(new JLabel());  // Empty cell
        loginCard.add(loginButton);
        loginCard.add(new JLabel());  // Empty cell
        loginCard.add(loginMessageLabel);

        panelMain.add(loginCard, "Login");
    }

    private void initializeCreateUserCard() {
        JPanel createUserCard = new JPanel(new GridLayout(5, 2, 10, 10));
        createUserCard.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        createUserCard.setBackground(DARK_PURPLE);

        JLabel newUsernameLabel = new JLabel("Username:");
        newUsernameLabel.setForeground(Color.WHITE);
        newUsernameField = new JTextField();
        JLabel newPasswordLabel = new JLabel("Password:");
        newPasswordLabel.setForeground(Color.WHITE);
        newPasswordField = new JPasswordField();
        createButton = new JButton("Create User");

        // Style the button
        styleButton(createButton);

        JLabel createMessageLabel = new JLabel("", SwingConstants.CENTER);
        createMessageLabel.setForeground(Color.RED);

        createUserCard.add(newUsernameLabel);
        createUserCard.add(newUsernameField);
        createUserCard.add(newPasswordLabel);
        createUserCard.add(newPasswordField);
        createUserCard.add(new JLabel());  // Empty cell
        createUserCard.add(createButton);
        createUserCard.add(new JLabel());  // Empty cell
        createUserCard.add(createMessageLabel);

        panelMain.add(createUserCard, "CreateUser");
    }

    private void initializeQuestionCard() {
        JPanel questionCard = new JPanel(new BorderLayout());
        questionCard.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        questionCard.setBackground(DARK_PURPLE);

        usernameLabel = new JLabel();
        usernameLabel.setForeground(Color.WHITE);
        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        questionLabel.setForeground(Color.WHITE);
        JPanel ratingPanel = new JPanel(new FlowLayout());
        ratingPanel.setOpaque(false);

        ratingButtons = new JRadioButton[5];
        ButtonGroup ratingGroup = new ButtonGroup();
        for (int i = 0; i < ratingButtons.length; i++) {
            ratingButtons[i] = new JRadioButton(String.valueOf(i + 1));
            ratingButtons[i].setBackground(DARK_PURPLE);
            ratingButtons[i].setForeground(Color.WHITE);
            ratingGroup.add(ratingButtons[i]);
            ratingPanel.add(ratingButtons[i]);
        }
        nextButton = new JButton("Next");

        // Style the button
        styleButton(nextButton);

        progressBar = new JProgressBar(0, 100); // Initialize with a range from 0 to 100

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(ratingPanel, BorderLayout.CENTER);
        bottomPanel.add(nextButton, BorderLayout.EAST);
        bottomPanel.setOpaque(false);

        questionCard.add(usernameLabel, BorderLayout.NORTH);
        questionCard.add(questionLabel, BorderLayout.CENTER);
        questionCard.add(bottomPanel, BorderLayout.SOUTH);
        questionCard.add(progressBar, BorderLayout.NORTH); // Add the progress bar at the top

        panelMain.add(questionCard, "Questions");
    }

    private void initializeDetailsCard() {
        JPanel detailsCard = new JPanel(new GridLayout(4, 1, 10, 10));
        detailsCard.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        detailsCard.setBackground(DARK_PURPLE);

        JPanel explanationPanel = createSection("Explanation", new Color(224, 255, 255));
        explanationArea = (JTextArea) ((JScrollPane) explanationPanel.getComponent(1)).getViewport().getView();

        JPanel suggestionPanel = createSection("Suggestions", new Color(255, 239, 213));
        suggestionArea = (JTextArea) ((JScrollPane) suggestionPanel.getComponent(1)).getViewport().getView();

        JPanel meditationPanel = createSection("Meditation", new Color(240, 255, 240));
        meditationArea = (JTextArea) ((JScrollPane) meditationPanel.getComponent(1)).getViewport().getView();

        detailsCard.add(explanationPanel);
        detailsCard.add(suggestionPanel);
        detailsCard.add(meditationPanel);

        nextDetailButton = new JButton("Next");
        finishDetailButton = new JButton("Finish");

        // Style the buttons
        styleButton(nextDetailButton);
        styleButton(finishDetailButton);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(nextDetailButton);
        buttonPanel.add(finishDetailButton);
        buttonPanel.setOpaque(false);

        detailsCard.add(buttonPanel);

        panelMain.add(detailsCard, "Details");
    }

    private JPanel createSection(String title, Color bgColor) {
        JPanel sectionPanel = new JPanel(new BorderLayout());
        sectionPanel.setBackground(bgColor);
        sectionPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        sectionPanel.add(titleLabel, BorderLayout.NORTH);
        sectionPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);

        return sectionPanel;
    }

    private void initializeResultsCard() {
        JPanel resultsCard = new JPanel();
        resultsCard.setLayout(new BoxLayout(resultsCard, BoxLayout.Y_AXIS));
        resultsCard.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        resultsCard.setBackground(DARK_PURPLE);

        JPanel anxietyPanel = createResultSection("Anxiety", new Color(255, 182, 193));
        anxietyArea = (JTextArea) ((JScrollPane) anxietyPanel.getComponent(1)).getViewport().getView();
        resultsCard.add(anxietyPanel);

        JPanel stressPanel = createResultSection("Stress", new Color(173, 216, 230));
        stressArea = (JTextArea) ((JScrollPane) stressPanel.getComponent(1)).getViewport().getView();
        resultsCard.add(stressPanel);

        JPanel depressionPanel = createResultSection("Depression", new Color(255, 228, 181));
        depressionArea = (JTextArea) ((JScrollPane) depressionPanel.getComponent(1)).getViewport().getView();
        resultsCard.add(depressionPanel);

        JPanel panicDisorderPanel = createResultSection("Panic Disorder", new Color(144, 238, 144));
        panicDisorderArea = (JTextArea) ((JScrollPane) panicDisorderPanel.getComponent(1)).getViewport().getView();
        resultsCard.add(panicDisorderPanel);

        JPanel insomniaPanel = createResultSection("Insomnia", new Color(221, 160, 221));
        insomniaArea = (JTextArea) ((JScrollPane) insomniaPanel.getComponent(1)).getViewport().getView();
        resultsCard.add(insomniaPanel);

        finishButton = new JButton("Finish");

        // Style the button
        styleButton(finishButton);

        exitButton = new JButton("Exit"); // Add exit button
        styleButton(exitButton);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the application
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(finishButton);
        buttonPanel.add(exitButton); // Add exit button to the panel
        buttonPanel.setOpaque(false);

        resultsCard.add(buttonPanel);

        panelMain.add(resultsCard, "Results");
    }

    private JPanel createResultSection(String title, Color bgColor) {
        JPanel sectionPanel = new JPanel(new BorderLayout());
        sectionPanel.setBackground(bgColor);
        sectionPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        sectionPanel.add(titleLabel, BorderLayout.NORTH);
        sectionPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);

        return sectionPanel;
    }

    // Method to display a success message
    public void displayMessage(String message) {
        JOptionPane.showMessageDialog(frame, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    // Method to display an error message
    public void displayErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(frame, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Method to update the progress bar
    public void updateProgressBar(int current, int total) {
        int progress = (int) ((current / (double) total) * 100);
        progressBar.setValue(progress);
        progressBar.setStringPainted(true); // Show percentage on the progress bar
    }

    // Getters for controller
    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public JPanel getPanelMain() {
        return panelMain;
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JTextField getNewUsernameField() {
        return newUsernameField;
    }

    public JPasswordField getNewPasswordField() {
        return newPasswordField;
    }

    public JLabel getLoginMessageLabel() {
        return loginMessageLabel;
    }

    public JLabel getUsernameLabel() {
        return usernameLabel;
    }

    public JLabel getQuestionLabel() {
        return questionLabel;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getCreateButton() {
        return createButton;
    }

    public JButton getNextButton() {
        return nextButton;
    }

    public JButton getFinishButton() {
        return finishButton;
    }

    public JButton getNextDetailButton() {
        return nextDetailButton;
    }

    public JButton getFinishDetailButton() {
        return finishDetailButton;
    }

    public JButton getInitialLoginButton() {
        return initialLoginButton;
    }

    public JButton getInitialCreateUserButton() {
        return initialCreateUserButton;
    }

    public JRadioButton[] getRatingButtons() {
        return ratingButtons;
    }

    public JTextArea getExplanationArea() {
        return explanationArea;
    }

    public JTextArea getSuggestionArea() {
        return suggestionArea;
    }

    public JTextArea getMeditationArea() {
        return meditationArea;
    }

    public JProgressBar getProgressBar() {
        return progressBar;
    }

    public JTextArea getAnxietyArea() {
        return anxietyArea;
    }

    public JTextArea getStressArea() {
        return stressArea;
    }

    public JTextArea getDepressionArea() {
        return depressionArea;
    }

    public JTextArea getPanicDisorderArea() {
        return panicDisorderArea;
    }

    public JTextArea getInsomniaArea() {
        return insomniaArea;
    }

    // Method to set text in meditationArea based on severity
    public void setMeditationSuggestion(String severity, String suggestion) {
        if (!severity.equals("None")) {
            meditationArea.setText(suggestion);
        } else {
            meditationArea.setText(""); // Clear the suggestion if severity is "None"
        }
    }
}
