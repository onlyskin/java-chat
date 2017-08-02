package dirv.chat.server;

import static org.junit.Assert.*;

import dirv.chat.server.commands.HangmanSpy;
import org.junit.Test;

public class MessageWatcherTest {

    private HangmanBotSpy hangmanBotSpy = new HangmanBotSpy(
            new MessageRepositorySpy(),
            new HangmanSpy()
    );
    private final MessageWatcher messageWatcher = new MessageWatcher(hangmanBotSpy);

    @Test
    public void notifiesBotManagersOfMessage() throws Exception {
        messageWatcher.receiveMessage("Pass me on");
        assertTrue(hangmanBotSpy.getReceived());
    }
}
