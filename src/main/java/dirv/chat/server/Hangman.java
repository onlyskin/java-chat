package dirv.chat.server;

import java.util.ArrayList;
import java.util.List;

public class Hangman {
    private final String answer;
    private final List<String> guesses;

    public Hangman() {
       this.answer = "toaster";
       this.guesses = new ArrayList<String>();
    }

    public String replyToInput(String input) {
        playGuess(input.toLowerCase());
        String output = getProgress();
        if (gameWon()) {
            output = output + " - You won!";
        }
        return output;
    }

    public void playGuess(String guess) {
        if (answer.contains(guess)) {
            guesses.add(guess);
        }
    }

    public String getProgress() {
        String output = "";
        for (int i=0; i<answer.length(); i++) {
            String currentChar = Character.toString(answer.charAt(i));
            if (guesses.contains(currentChar)) {
                output = output + currentChar;
            }
            else {
                output = output + "_";
            }
        }
        return output.toUpperCase();
    }

    public boolean gameWon() {
        return getProgress().toLowerCase().equals(answer.toLowerCase());
    }
}
