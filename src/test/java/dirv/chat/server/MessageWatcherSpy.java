package dirv.chat.server;

public class MessageWatcherSpy extends MessageWatcher {
    public boolean received;

    public MessageWatcherSpy(HangmanBot hangmanBot) {
        super(hangmanBot);
        this.received = false;
    }

    @Override
    public void receiveMessage(String message) {
        received = true;
    }

    public boolean getReceived() {
        return received;
    }
}
