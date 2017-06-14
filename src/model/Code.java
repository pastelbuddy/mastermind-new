package model;

import java.util.ArrayList;

public class Code {

    private CodePegs[] code;
    private int pegSize;

    public Code(CodePegs[] code, int pegSize) {

        //validate the length of the code
        if (code.length != pegSize)
            throw new IllegalArgumentException();

        //validate that all elements in the code are assigned
        for (int i = 0; i < pegSize; i++) {
            if (code[i] == null)
                throw new IllegalArgumentException();
        }

        this.code = code;
        this.pegSize = pegSize;
    }

    public Code(ArrayList<String> pegNames, int pegSize) {
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
        for (int i = 0; i < this.pegSize; i++)
            result.append(code[i]).append(", ");
        result = new StringBuilder(result.substring(0, result.length() - 2));
        result.append("}");
        return result.toString();
    }

//	/**
//	 * This function compares two codes. This will be used when the requirements 
//	 * change so that we need to have a computer codemaker.
//	 * 
//	 * @param other - The other code that we are comparing ourselves to
//	 * @return true if the code contain the same pegs
//	 */
//	@Override
//	public boolean equals(Object other) {
//		if(other.getClass() == Code.class) {
//			Code otherCode = (Code)other;
//			for(int i = 0;i < GameController.gamePegSize;i++)
//				if(code[i] != otherCode.getCode()[i])
//					return false;
//			return true;
//		}
//		else {
//			return false;
//		}
//	}

    public static ArrayList<String> makeGuessImageList(ArrayList<Code> allGuesses) {
        ArrayList<String> guessesString = new ArrayList<>();
        //iterate the guesses
        for (Code code : allGuesses) {
            CodePegs[] currGuess = code.getCode();
            //iterate each guesses' pegs
            for (CodePegs currGues : currGuess) {
                String colorFileName = currGues.name() + ".png";
                guessesString.add(colorFileName);
            }
        }
        return guessesString;
    }
}
