package dirv.chat.server.commands;

import dirv.chat.server.Hangman;
import org.junit.Test;

import static org.junit.Assert.*;

public class HangmanTest {
    private Hangman hangman = new Hangman();

    @Test
    public void itRepliesWithProgressStringForA() {
        String reply = hangman.replyToInput("A");
        assertEquals(reply, "__A____");
    }

    @Test
    public void itRepliesWithProgressStringForO() {
        String reply = hangman.replyToInput("O");
        assertEquals(reply, "_O_____");
    }

    @Test
    public void itRepliesWithProgressStringForT() {
        String reply = hangman.replyToInput("T");
        assertEquals(reply, "T___T__");
    }

    @Test
    public void itRepliesWithProgressStringAfterTwoInputs() {
        hangman.replyToInput("T");
        String reply = hangman.replyToInput("O");
        assertEquals(reply, "TO__T__");
    }

    @Test
    public void itRepliesWithProgressAndWonWhenWon() {
        hangman.replyToInput("T");
        hangman.replyToInput("O");
        hangman.replyToInput("A");
        hangman.replyToInput("S");
        hangman.replyToInput("E");
        String reply = hangman.replyToInput("R");
        assertEquals(reply, "TOASTER - You won!");
    }
}
