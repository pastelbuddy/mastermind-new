package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Feedback {

    private FeedbackPegs[] feedback;
    private int pegSize;

    public Feedback(FeedbackPegs[] feedback, int pegSize) {
        //validate length of feedback
        if (feedback.length != pegSize)
            throw new IllegalArgumentException();

        //validate that elements in the feedback are assigned
        for (int i = 0; i < pegSize; i++)
            if (feedback[i] == null)
                throw new IllegalArgumentException();

        this.feedback = feedback;
        this.pegSize = pegSize;


    }

    public Feedback(List<String> pegNames, int pegSize) {
        FeedbackPegs[] feedback = new FeedbackPegs[pegSize];

        for (int i = 0; i < pegNames.size(); i++) {
            FeedbackPegs feedbackPeg = FeedbackPegs.valueOf(pegNames.get(i));
            feedback[i] = feedbackPeg;
        }

        this.feedback = feedback;
        this.pegSize = pegSize;

    }

    public FeedbackPegs[] getFeedback() {
        return feedback;
    }

    public String toString() {
        StringBuilder result = new StringBuilder("{");
        for (int i = 0; i < this.pegSize; i++) {
            result.append(feedback[i]).append(", ");
        }

        result = new StringBuilder(result.substring(0, result.length() - 2));
        result.append("}");
        return result.toString();
    }

    public List<String> convertToArray() {
        List<String> feedbackArray = new ArrayList<>();
        for (FeedbackPegs peg : feedback) {
            feedbackArray.add(peg.toString());
        }
        return feedbackArray;
    }

    public static boolean victory(Feedback feedback) {
        if (feedback == null || !feedback.feedback[0].equals(FeedbackPegs.BLACK)) {
            return false;
        } else {
            for (int i = 0; i < feedback.feedback.length - 2; i++) {
                if (!feedback.feedback[i].equals(feedback.feedback[i + 1])) {
                    return false;
                }
            }
        }
        return true;
    }

    public static List<String> makeFeedbackImageNameList(List<Feedback> allFeedback) {
        List<String> feedbackString = new ArrayList<>();
        //iterate the feedback
        for (Feedback anAllFeedback : allFeedback) {
            FeedbackPegs[] currFeedback = anAllFeedback.getFeedback();
            //iterate each guesses' pegs
            for (FeedbackPegs aCurrFeedback : currFeedback) {
                if (Objects.equals(aCurrFeedback.name(), "BLACK")) {
                    String colorFileName = "FEEDBK_BLACK.png";
                    feedbackString.add(colorFileName);
                } else if (Objects.equals(aCurrFeedback.name(), "WHITE")) {
                    String colorFileName = "FEEDBK_WHITE.png";
                    feedbackString.add(colorFileName);
                } else {
                    String colorFileName = "NONE.jpg";
                    feedbackString.add(colorFileName);
                }
            }
        }
        return feedbackString;
    }
}
