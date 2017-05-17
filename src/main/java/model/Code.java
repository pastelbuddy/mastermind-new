package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Joanna Kanas
 */
public class Code {

    private List<CodePegs> codePegs;
    private int pegSize;

    Code(List<CodePegs> codePegs, int pegSize) {
        if (codePegs.size() != pegSize) {
            throw new IllegalArgumentException();
        }

        if (codePegs.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException();
        }

        this.codePegs = codePegs;
        this.pegSize = pegSize;
    }

    Code(List<String> codePegs) {
        List<CodePegs> guesses = new ArrayList<>();
        codePegs.stream().map(CodePegs::valueOf).forEach(guesses::add);
        this.codePegs = guesses;
    }

    static List<String> makeGuessImageList(List<Code> allGuesses) {
        List<String> guesses = new ArrayList<>();
        allGuesses.stream()
                .map(Code::getCodePegs)
                .map(name -> name + ".png")
                .forEach(guesses::add);

        /*for (Code code : allGuesses) {
            List<CodePegs> currentGuesses = code.getCodePegs();
            for (CodePegs current : currentGuesses) {
                String colorFileName = current.name() + ".png";
                guesses.add(colorFileName);
            }
        }*/
        return guesses;
    }

    List<CodePegs> getCodePegs() {
        return codePegs;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("{");
        for (int i = 0; i < this.pegSize; i++) {
            result.append(codePegs.get(i)).append(", ");
        }
        result = new StringBuilder(result.substring(0, result.length() - 2));
        result.append("}");
        return result.toString();
    }
}
