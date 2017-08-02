package dirv.chat.server;

public class HangmanBotSpy extends HangmanBot {
    public boolean received;

    public HangmanBotSpy() {
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
