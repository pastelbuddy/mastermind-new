/**
 * Novice.java
 *
 * @author Jeremy
 */

package model;

import java.util.HashSet;

public class Novice implements Difficulty {

    @Override
    public boolean isLegalCode(Code code) {
        CodePegs pegs[] = code.getCode();
        HashSet<CodePegs> seen = new HashSet<>();

        for (CodePegs peg : pegs) {
            if (seen.contains(peg))
                return false;
            seen.add(peg);
        }
        return true;
    }

}
