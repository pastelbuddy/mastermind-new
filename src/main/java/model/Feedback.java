package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Joanna Kanas
 */
public class Feedback {

    private List<FeedbackPegs> feedbackPegs;
    private List<String> feedbackArray;
    private int pegSize;

    Feedback(List<FeedbackPegs> feedbackPegs, int pegSize) {
        if (feedbackPegs.size() != pegSize) {
            throw new IllegalArgumentException();
        }

        if (feedbackPegs.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException();
        }

        this.feedbackPegs = feedbackPegs;
        this.pegSize = pegSize;
    }

    Feedback(List<String> pegNames) {
        List<FeedbackPegs> feedback = new ArrayList<>();
        pegNames.stream().map(FeedbackPegs::valueOf).forEach(feedback::add);
        this.feedbackPegs = feedback;
    }

    public static boolean victory(Feedback feedback) {
        if (feedback == null || Objects.equals(FeedbackPegs.BLACK, feedback.feedbackPegs.get(0))) {
            return false;
        } else {
            for (int i = 0; i < feedback.feedbackPegs.size() - 2; i++) {
                if (!feedback.feedbackPegs.get(i).equals(feedback.feedbackPegs.get(i + 1))) {
                    return false;
                }
            }
        }
        return true;
    }

    static List<String> makeFeedbackImageNameList(List<Feedback> allFeedback) {
        ArrayList<String> feedbackString = new ArrayList<>();

        for (Feedback feedback : allFeedback) {
            List<FeedbackPegs> currentFeedback = feedback.getFeedbackPegs();

            for (FeedbackPegs feedbackPegs : currentFeedback) {
                if (Objects.equals(feedbackPegs.name(), "BLACK")) {
                    String colorFileName = "FEEDBACK_BLACK.png";
                    feedbackString.add(colorFileName);
                } else if (Objects.equals(feedbackPegs.name(), "WHITE")) {
                    String colorFileName = "FEEDBACK_WHITE.png";
                    feedbackString.add(colorFileName);
                } else {
                    String colorFileName = "NONE.jpg";
                    feedbackString.add(colorFileName);
                }
            }
        }
        return feedbackString;
    }

    public List<FeedbackPegs> getFeedbackPegs() {
        return feedbackPegs;
    }

    public String toString() {
        StringBuilder result = new StringBuilder("{");
        for (int i = 0; i < this.pegSize; i++) {
            result.append(feedbackPegs.get(i)).append(", ");
        }
        result = new StringBuilder(result.substring(0, result.length() - 2));
        result.append("}");
        return result.toString();
    }

    List<String> convertToArray() {
        feedbackArray = new ArrayList<>();
        feedbackPegs.forEach(peg -> feedbackArray.add(peg.toString()));
        return feedbackArray;
    }
}
