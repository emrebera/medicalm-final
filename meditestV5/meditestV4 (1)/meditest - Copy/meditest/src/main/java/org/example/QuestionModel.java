package org.example;

import java.util.*;

public class QuestionModel {
    private List<Question> questions;
    private int currentQuestionIndex;
    private Map<String, Integer> typeRatings;
    private Map<String, Integer> typeCounts;

    public QuestionModel() {
        this.questions = createQuestions();
        this.currentQuestionIndex = 0;
        this.typeRatings = new HashMap<>();
        this.typeCounts = new HashMap<>();
    }

    public boolean hasMoreQuestions() {
        return currentQuestionIndex < questions.size();
    }

    public Question getCurrentQuestion() {
        if (hasMoreQuestions()) {
            return questions.get(currentQuestionIndex);
        } else {
            return null;
        }
    }

    public void incrementQuestionIndex() {
        currentQuestionIndex++;
    }

    public void saveRating(String type, int rating) {
        typeRatings.put(type, typeRatings.getOrDefault(type, 0) + rating);
        typeCounts.put(type, typeCounts.getOrDefault(type, 0) + 1);
    }

    public Map<String, Integer> getTypeRatings() {
        return typeRatings;
    }

    public Map<String, Integer> getTypeCounts() {
        return typeCounts;
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public int getTotalQuestions() {
        return questions.size();
    }

    private List<Question> createQuestions() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("How often do you feel nervous, anxious, or on edge? (1 = Never, 5 = Always)", "anxiety"));
        questions.add(new Question("How difficult do you find it to control your worrying? (1 = Not difficult at all, 5 = Extremely difficult)", "anxiety"));
        questions.add(new Question("How often do you experience physical symptoms such as sweating, trembling, or a racing heart when you are anxious? (1 = Never, 5 = Always)", "anxiety"));
        questions.add(new Question("How often do you avoid certain situations or activities because they make you feel anxious? (1 = Never, 5 = Always)", "anxiety"));
        questions.add(new Question("How much does anxiety interfere with your daily activities, work, or social interactions? (1 = Not at all, 5 = Severely)", "anxiety"));
        questions.add(new Question("How often do you have trouble falling asleep? (1 = Never, 5 = Always)", "insomnia"));
        questions.add(new Question("How often do you wake up frequently during the night? (1 = Never, 5 = Always)", "insomnia"));
        questions.add(new Question("How often do you wake up too early and cannot go back to sleep? (1 = Never, 5 = Always)", "insomnia"));
        questions.add(new Question("How often do you feel tired or unrefreshed after sleeping? (1 = Never, 5 = Always)", "insomnia"));
        questions.add(new Question("How much does insomnia interfere with your daily activities, work, or social interactions? (1 = Not at all, 5 = Severely)", "insomnia"));
        questions.add(new Question("How often do you feel down, depressed, or hopeless? (1 = Never, 5 = Always)", "depression"));
        questions.add(new Question("How often do you have little interest or pleasure in doing things? (1 = Never, 5 = Always)", "depression"));
        questions.add(new Question("How often do you feel tired or have little energy? (1 = Never, 5 = Always)", "depression"));
        questions.add(new Question("How often do you feel bad about yourself or that you are a failure? (1 = Never, 5 = Always)", "depression"));
        questions.add(new Question("How much does depression interfere with your daily activities, work, or social interactions? (1 = Not at all, 5 = Severely)", "depression"));
        questions.add(new Question("How often do you feel overwhelmed by your responsibilities? (1 = Never, 5 = Always)", "stress"));
        questions.add(new Question("How often do you find it hard to relax and unwind? (1 = Never, 5 = Always)", "stress"));
        questions.add(new Question("How often do you experience physical symptoms like headaches or stomach issues when stressed? (1 = Never, 5 = Always)", "stress"));
        questions.add(new Question("How often do you feel irritable or angry due to stress? (1 = Never, 5 = Always)", "stress"));
        questions.add(new Question("How much does stress interfere with your daily activities, work, or social interactions? (1 = Not at all, 5 = Severely)", "stress"));
        questions.add(new Question("How often do you experience sudden, intense fear or discomfort that peaks within minutes? (1 = Never, 5 = Always)", "panic disorder"));
        questions.add(new Question("How often do you worry about having more panic attacks? (1 = Never, 5 = Always)", "panic disorder"));
        questions.add(new Question("How often do you avoid places or situations because you fear they might cause a panic attack? (1 = Never, 5 = Always)", "panic disorder"));
        questions.add(new Question("How often do you experience physical symptoms like chest pain, heart palpitations, or dizziness during these episodes? (1 = Never, 5 = Always)", "panic disorder"));
        questions.add(new Question("How much do panic attacks interfere with your daily activities, work, or social interactions? (1 = Not at all, 5 = Severely)", "panic disorder"));
        return questions;
    }
}
