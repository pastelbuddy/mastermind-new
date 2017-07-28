package model;

import java.util.HashSet;
import java.util.Set;

public class Novice implements Difficulty {

    @Override
    public boolean isLegalCode(Code code) {
        CodePegs[] pegs = code.getCode();
        Set<CodePegs> seen = new HashSet<>();

        for (CodePegs peg : pegs) {
            if (seen.contains(peg)) {
                return false;
            }
            seen.add(peg);
        }
        return true;
    }

}
