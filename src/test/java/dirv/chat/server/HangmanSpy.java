package dirv.chat.server.commands;

import dirv.chat.server.Hangman;

import java.util.ArrayList;

public class HangmanSpy extends Hangman {
    private String guessed;

    public HangmanSpy() {
        super();
    }

    @Override
    public String replyToInput(String input) {
        guessed = input;
        return null;
    }

    public String getReceivedGuess() {
        return guessed;
    }
}
