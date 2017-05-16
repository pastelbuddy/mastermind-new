package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ad on 2017-05-14.
 */
public class SystemPlayer implements Player {

    @Override
    public void makeCommand(GameCommand newCommand) {
        //system never does this
    }

    @Override
    public String getName() {
        return "GIMP";
    }

    /**
     * Generates feedback based on the code and the answer
     *
     * @param guess  - The code that is being guessed
     * @param answer - The solution code
     * @return feedback - The feedback for this round of mastermind
     */
    public Feedback generateFeedback(Code guess, Code answer) {
        int numFeedbackPegs = 0;
        //Gets the magnitude of the intersection of the guess and the answer
        for (CodePegs color : CodePegs.values()) {
            numFeedbackPegs += Math.min(getNumOccur(color, guess.getCodePegs()),
                    getNumOccur(color, answer.getCodePegs()));
        }

        //Calculates the number of black pegs
        int blacks = 0;
        for (int i = 0; i < answer.getCodePegs().size(); i++)
            if (guess.getCodePegs().get(i) == answer.getCodePegs().get(i))
                blacks++;

        //Start creating the feedback
        List<FeedbackPegs> feedbackPegs = new ArrayList<>();

        //Add the black pegs
        for (int i = 0; i < blacks; i++) {
            feedbackPegs.add(FeedbackPegs.BLACK);
        }

        //Add the white pegs
        for (int i = blacks; i < numFeedbackPegs; i++) {
            feedbackPegs.add(FeedbackPegs.WHITE);
        }

        //Add blanks
        for (int i = numFeedbackPegs; i < answer.getCodePegs().size(); i++) {
            feedbackPegs.add(FeedbackPegs.NONE);
        }

        return new Feedback(feedbackPegs, answer.getCodePegs().size());
    }

    private int getNumOccur(CodePegs peg, List<CodePegs> code) {
        int num = 0;
        for (CodePegs p : code) {
            if (p == peg) {
                num++;
            }
        }
        return num;
    }
}
