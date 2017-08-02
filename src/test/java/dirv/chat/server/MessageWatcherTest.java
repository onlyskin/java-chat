package dirv.chat.server;

import static org.junit.Assert.*;

import org.junit.Test;

public class MessageWatcherTest {

    private HangmanBotSpy hangmanBotSpy = new HangmanBotSpy();
    private final MessageWatcher messageWatcher = new MessageWatcher(hangmanBotSpy);

    @Test
    public void notifiesBotManagersOfMessage() throws Exception {
        messageWatcher.receiveMessage("Pass me on");
        assertTrue(hangmanBotSpy.getReceived());
    }
}
