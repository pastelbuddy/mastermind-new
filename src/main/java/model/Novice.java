package model;

import java.util.HashSet;
import java.util.List;

/**
 * Created by Joanna Kanas
 */
public class Novice implements Difficulty {

    @Override
    public boolean isLegalCode(Code code) {
        List<CodePegs> codePegs = code.getCodePegs();
        HashSet<CodePegs> seen = new HashSet<>();

        for (CodePegs codePeg : codePegs) {
            if (seen.contains(codePeg)) {
                return false;
            }
            seen.add(codePeg);
        }
        
        return true;
    }
}
