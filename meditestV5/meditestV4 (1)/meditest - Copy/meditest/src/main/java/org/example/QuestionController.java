package org.example;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class QuestionController {
    private QuestionModel model;
    private QuestionView view;
    private int currentRating;
    private String currentDetail;
    private Map<String, String> userDatabase;  // Simulated user database

    public QuestionController(QuestionModel model, QuestionView view) {
        this.model = model;
        this.view = view;
        this.userDatabase = new HashMap<>();

        initialize();

        view.getLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = view.getUsernameField().getText();
                String password = new String(view.getPasswordField().getPassword());

                if (authenticate(username, password)) {
                    view.getCardLayout().show(view.getPanelMain(), "Questions");
                    view.getUsernameLabel().setText("User: " + username);  // Set the username on successful login
                    showNextQuestion();
                } else {
                    view.displayErrorMessage("Invalid username or password");
                }
            }
        });

        view.getCreateButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newUsername = view.getNewUsernameField().getText();
                String newPassword = new String(view.getNewPasswordField().getPassword());

                if (createUser(newUsername, newPassword)) {
                    view.getCardLayout().show(view.getPanelMain(), "Login");
                    view.getUsernameField().setText(newUsername);
                    view.getPasswordField().setText(newPassword);
                    view.displayMessage("User created successfully. Please login.");
                } else {
                    view.displayErrorMessage("Username already exists. Please choose another.");
                }
            }
        });

        for (int i = 0; i < view.getRatingButtons().length; i++) {
            final int rating = i + 1;
            view.getRatingButtons()[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    currentRating = rating;
                    view.getNextButton().setEnabled(true); // Enable the Next button
                }
            });
        }

        view.getNextButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveRating();
                if (model.hasMoreQuestions()) {
                    showNextQuestion();
                } else {
                    showDetails("Explanation");
                    view.getCardLayout().show(view.getPanelMain(), "Details");
                }
            }
        });

        view.getFinishButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayResults();
                try {
                    exportResultsToFile();
                } catch (IOException ex) {
                    view.displayErrorMessage("Error exporting results to file.");
                    ex.printStackTrace();
                }
            }
        });

        view.getNextDetailButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentDetail.equals("Explanation")) {
                    showDetails("Suggestions");
                } else if (currentDetail.equals("Suggestions")) {
                    showDetails("Meditation");
                }
            }
        });

        view.getFinishDetailButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.getCardLayout().show(view.getPanelMain(), "Results");
            }
        });

        view.getInitialLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.getCardLayout().show(view.getPanelMain(), "Login");
            }
        });

        view.getInitialCreateUserButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.getCardLayout().show(view.getPanelMain(), "CreateUser");
            }
        });

        view.getNextButton().setEnabled(false);
    }

    private void initialize() {
        // Prepopulate with a default user for testing
        userDatabase.put("user", "password");
    }

    private boolean authenticate(String username, String password) {
        // Check if the user exists in the simulated user database and if the password matches
        return userDatabase.containsKey(username) && userDatabase.get(username).equals(password);
    }

    private boolean createUser(String username, String password) {
        // Check if the username already exists
        if (userDatabase.containsKey(username)) {
            return false;  // User already exists
        }
        // Create the new user
        userDatabase.put(username, password);
        return true;
    }

    private void saveRating() {
        if (model.hasMoreQuestions()) {
            String currentType = model.getCurrentQuestion().getType();
            model.saveRating(currentType, currentRating);
            System.out.println("Saved rating: " + currentRating + " for type: " + currentType);
            model.incrementQuestionIndex(); // Move to the next question
        }
    }

    private void showNextQuestion() {
        if (model.hasMoreQuestions()) {
            Question question = model.getCurrentQuestion();
            view.getQuestionLabel().setText(question.getText());
            view.getNextButton().setEnabled(false); // Disable the Next button until a rating is selected
            view.updateProgressBar(model.getCurrentQuestionIndex() + 1, model.getTotalQuestions()); // update the progress bar
            System.out.println("Showing next question. Current index: " + model.getCurrentQuestionIndex());
        } else {
            System.out.println("No more questions. Displaying details.");
            showDetails("Explanation");
            view.getCardLayout().show(view.getPanelMain(), "Details");
        }
    }

    private void displayResults() {
        StringBuilder anxietyResults = new StringBuilder();
        StringBuilder stressResults = new StringBuilder();
        StringBuilder depressionResults = new StringBuilder();
        StringBuilder panicDisorderResults = new StringBuilder();
        StringBuilder insomniaResults = new StringBuilder();

        for (String type : model.getTypeRatings().keySet()) {
            int totalRating = model.getTypeRatings().get(type);
            int count = model.getTypeCounts().get(type);
            double averageRating = totalRating / (double) count;
            StringBuilder result = new StringBuilder();
            result.append(String.format("%s: Total = %d, Count = %d, Average = %.2f\n", type, totalRating, count, averageRating));
            result.append(String.format("%s Severity: %s\n", type, getSeverity(totalRating)));
            result.append(String.format("Explanation: %s\n", getExplanation(type)));
            result.append(String.format("Suggestion: %s\n\n", getSuggestion(type, totalRating)));

            switch (type) {
                case "anxiety":
                    anxietyResults.append(result.toString());
                    break;
                case "stress":
                    stressResults.append(result.toString());
                    break;
                case "depression":
                    depressionResults.append(result.toString());
                    break;
                case "panic disorder":
                    panicDisorderResults.append(result.toString());
                    break;
                case "insomnia":
                    insomniaResults.append(result.toString());
                    break;
            }
        }

        view.getAnxietyArea().setText(anxietyResults.toString());
        view.getStressArea().setText(stressResults.toString());
        view.getDepressionArea().setText(depressionResults.toString());
        view.getPanicDisorderArea().setText(panicDisorderResults.toString());
        view.getInsomniaArea().setText(insomniaResults.toString());
    }

    private String getSeverity(int totalRating) {
        if (totalRating >= 5 && totalRating <= 10) {
            return "None";
        } else if (totalRating >= 11 && totalRating <= 15) {
            return "Mild";
        } else if (totalRating >= 16 && totalRating <= 20) {
            return "Moderate";
        } else if (totalRating >= 21 && totalRating <= 25) {
            return "Severe";
        } else {
            return "";
        }
    }

    private void showDetails(String detailType) {
        StringBuilder details = new StringBuilder();
        if (detailType.equals("Explanation")) {
            for (String type : model.getTypeRatings().keySet()) {
                details.append(String.format("%s Explanation:\n%s\n\n", type, getExplanation(type)));
            }
            currentDetail = "Explanation";
            view.getExplanationArea().setText(details.toString());
        } else if (detailType.equals("Suggestions")) {
            for (String type : model.getTypeRatings().keySet()) {
                details.append(String.format("%s Suggestions:\n%s\n\n", type, getSuggestion(type, model.getTypeRatings().get(type))));
            }
            currentDetail = "Suggestions";
            view.getSuggestionArea().setText(details.toString());
        } else if (detailType.equals("Meditation")) {
            for (String type : model.getTypeRatings().keySet()) {
                details.append(String.format("%s Meditation:\n%s\n\n", type, getMeditationSuggestion(type)));
            }
            currentDetail = "Meditation";
            view.getMeditationArea().setText(details.toString());
        }
    }

    private String getExplanation(String type) {
        switch (type) {
            case "anxiety":
                if( (model.getTypeRatings().get(type)) <= 10){
                    return "No Explanation is necessary";
                }else {
                    return "Anxiety disorders involve excessive worry or fear that can interfere with daily activities. Severe anxiety often manifests through constant worry, physical symptoms like a racing heart, sweating, trembling, and muscle tension.";
                }
            case "insomnia":
                if( (model.getTypeRatings().get(type)) <= 10){
                    return "No Explanation is necessary";
                }else {
                    return "Insomnia is characterized by difficulty falling asleep, staying asleep, or waking up too early. Moderate insomnia can affect daytime functioning, leading to fatigue, irritability, and difficulty concentrating.";
                }
            case "depression":
                if( (model.getTypeRatings().get(type)) <= 10){
                    return "No Explanation is necessary";
                }else {
                    return "Depression involves persistent feelings of sadness, hopelessness, and a loss of interest in activities once enjoyed. Moderate depression can significantly impact daily functioning, causing difficulties in maintaining relationships.";
                }
            case "stress":
                if( (model.getTypeRatings().get(type)) <= 10){
                    return "No Explanation is necessary";
                }else {
                    return "Severe stress can lead to significant physical and emotional health problems. It often manifests through feelings of being overwhelmed, chronic irritability, and physical symptoms like headaches, stomach problems, and muscle tension.";
                }
            case "panic disorder":
                if( (model.getTypeRatings().get(type)) <= 10){
                    return "No Explanation is necessary";
                }else {
                    return "Panic disorder involves recurrent panic attacks characterized by sudden, intense fear and physical symptoms such as chest pain, heart palpitations, shortness of breath, dizziness, and nausea.";
                }
            default:
                return "";
        }
    }

    private String getSuggestion(String type, int totalRating) {
        if (totalRating >= 5 && totalRating <= 10) {
            return "No Suggestion is necessary";
        } else if (totalRating >= 11 && totalRating <= 15) {
            return getMildSuggestion(type);
        } else if (totalRating >= 16 && totalRating <= 20) {
            return getModerateSuggestion(type);
        } else if (totalRating >= 21 && totalRating <= 25) {
            return getSevereSuggestion(type);
        } else {
            return "";
        }
    }

    private String getMildSuggestion(String type) {
        switch (type) {
            case "anxiety":
                return "Consider practicing mindfulness and relaxation techniques.";
            case "insomnia":
                return "Suggest maintaining a regular sleep schedule and reducing screen time before bed.";
            case "depression":
                return "Encourage engaging in enjoyable activities and practicing gratitude.";
            case "stress":
                return "Recommend stress management techniques such as yoga or meditation.";
            case "panic disorder":
                return "Suggest learning and practicing relaxation techniques and breathing exercises.";
            default:
                return "";
        }
    }

    private String getModerateSuggestion(String type) {
        switch (type) {
            case "anxiety":
                return "Suggest regular exercise and speaking with a counselor.";
            case "insomnia":
                return "Recommend cognitive behavioral therapy for insomnia (CBT-I) and consulting a doctor.";
            case "depression":
                return "Suggest talking to a therapist and possibly joining a support group.";
            case "stress":
                return "Advise time management strategies and possibly seeing a counselor.";
            case "panic disorder":
                return "Recommend therapy, such as cognitive-behavioral therapy (CBT), to address panic symptoms.";
            default:
                return "";
        }
    }

    private String getSevereSuggestion(String type) {
        switch (type) {
            case "anxiety":
                return "Recommend consulting a mental health professional and possibly medication.";
            case "insomnia":
                return "Strongly advise seeing a sleep specialist and discussing potential medication.";
            case "depression":
                return "Urge consulting a psychiatrist for comprehensive treatment including medication.";
            case "stress":
                return "Suggest a thorough evaluation by a mental health professional for tailored interventions.";
            case "panic disorder":
                return "Strongly advise consulting a mental health professional for a comprehensive treatment plan, possibly including medication.";
            default:
                return "";
        }
    }

    private String getMeditationSuggestion(String type) {
        switch (type) {
            case "anxiety":
                if( (model.getTypeRatings().get(type)) <= 10){
                    return "No Meditation is necessary";
                }else {
                    return "Mindfulness Meditation:\n1. Find a quiet place and sit comfortably.\n2. Close your eyes and take a few deep breaths.\n3. Focus on your breath, noticing the sensation of the air entering and leaving your body.\n4. When your mind wanders, gently bring your focus back to your breath.\n5. Continue for 10-20 minutes.";
                }
            case "insomnia":
                if( (model.getTypeRatings().get(type)) <= 10){
                    return "No Meditation is necessary";
                }else {
                    return "Guided Sleep Meditation:\n1. Lie down in a comfortable position.\n2. Play a guided sleep meditation audio.\n3. Follow the instructions, focusing on relaxing your body and mind.\n4. Let go of any worries or thoughts, allowing yourself to drift into sleep.";
                }
            case "depression":
                if( (model.getTypeRatings().get(type)) <= 10){
                    return "No Meditation is necessary";
                }else {
                    return "Loving-Kindness Meditation:\n1. Sit comfortably and close your eyes.\n2. Take a few deep breaths and relax.\n3. Repeat phrases like 'May I be happy, may I be healthy, may I be safe, may I live with ease.'\n4. Gradually extend these wishes to others: loved ones, friends, acquaintances, and all beings.\n5. Continue for 10-20 minutes.";
                }
            case "stress":
                if( (model.getTypeRatings().get(type)) <= 10){
                    return "No Meditation is necessary";
                }else {
                    return "Body Scan Meditation:\n1. Lie down or sit in a comfortable position.\n2. Close your eyes and take a few deep breaths.\n3. Focus on each part of your body, starting from your toes and moving up to your head.\n4. Notice any tension or discomfort and try to release it.\n5. Continue for 10-20 minutes.";
                }
            case "panic disorder":
                if( (model.getTypeRatings().get(type)) <= 10){
                    return "No Meditation is necessary";
                }else {
                    return "Breathing Meditation:\n1. Sit comfortably and close your eyes.\n2. Take a few deep breaths, inhaling through your nose and exhaling through your mouth.\n3. Focus on your breath, counting to four as you inhale and to four as you exhale.\n4. If your mind wanders, gently bring your focus back to your breath.\n5. Continue for 10-20 minutes.";
                }
            default:
                return "";
        }
    }

    private void exportResultsToFile() throws IOException {
        FileWriter writer = new FileWriter("results.txt");
        writer.write(view.getAnxietyArea().getText());
        writer.write(view.getStressArea().getText());
        writer.write(view.getDepressionArea().getText());
        writer.write(view.getPanicDisorderArea().getText());
        writer.write(view.getInsomniaArea().getText());
        writer.close();
        System.out.println("Results exported to results.txt");
    }
}
