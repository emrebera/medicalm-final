package org.example;

import javax.swing.SwingUtilities;

public class MentalHealthApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                QuestionModel model = new QuestionModel();
                QuestionView view = new QuestionView();
                new QuestionController(model, view);
            }
        });
    }
}
