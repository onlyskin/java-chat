package dirv.chat.server.commands;

import dirv.chat.server.HangmanBot;

import static org.junit.Assert.*;

import dirv.chat.server.MessageRepositorySpy;
import org.junit.Test;
import org.junit.Ignore;

public class HangmanBotTest {
    private HangmanSpy hangman = new HangmanSpy();
    private MessageRepositorySpy messageRepository = new MessageRepositorySpy();
    private HangmanBot hangmanBot = new HangmanBot(messageRepository, hangman);

    @Test
    public void itSavesAMessageIfHangmanHandlePresent() throws Exception {
        hangmanBot.receiveMessage("@hangman A");
        assertEquals(1, messageRepository.getMessages().size());
    }

    @Test
    public void noMessageSavedIfHangmanHandleNotPresent() throws Exception {
        hangmanBot.receiveMessage("Not for hangman");
        assertEquals(0, messageRepository.getMessages().size());
    }

    @Test
    public void itCallsHangmanReceiveGuessWithStrippedGuess() throws Exception {
        hangmanBot.receiveMessage("@hangman A");
        assertEquals("A", hangman.getReceivedGuess());
    }
}
