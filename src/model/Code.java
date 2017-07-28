package model;

import java.util.ArrayList;
import java.util.List;

public class Code {

    private CodePegs[] code;
    private int pegSize;

    public Code(CodePegs[] code, int pegSize) {
        //validate the length of the code
        if (code.length != pegSize)
            throw new IllegalArgumentException();

        //validate that all elements in the code are assigned
        for (int i = 0; i < pegSize; i++) {
            if (code[i] == null) {
                throw new IllegalArgumentException();
            }
        }

        this.code = code;
        this.pegSize = pegSize;
    }

    public Code(List<String> pegNames, int pegSize) {
        CodePegs[] guess = new CodePegs[pegSize];

        for (int i = 0; i < pegNames.size(); i++) {
            CodePegs feedback = CodePegs.valueOf(pegNames.get(i));
            guess[i] = feedback;
        }
        this.code = guess;
        this.pegSize = pegSize;
    }

    public CodePegs[] getCode() {
        return code;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("{");
        for (int i = 0; i < this.pegSize; i++) {
            result.append(code[i]).append(", ");
        }
        result = new StringBuilder(result.substring(0, result.length() - 2));
        result.append("}");
        return result.toString();
    }

    public static List<String> makeGuessImageList(List<Code> allGuesses) {
        List<String> guessesString = new ArrayList<>();
        //iterate the guesses
        for (Code code : allGuesses) {
            CodePegs[] currGuess = code.getCode();
            //iterate each guesses' pegs
            for (CodePegs current : currGuess) {
                String colorFileName = current.name() + ".png";
                guessesString.add(colorFileName);
            }
        }
        return guessesString;
    }
}
