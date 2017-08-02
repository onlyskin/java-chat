package dirv.chat.server;

public class HangmanBotSpy extends HangmanBot {
    public boolean received;

    public HangmanBotSpy(MessageRepository messageRepository, Hangman hangman) {
        super(messageRepository, hangman);
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
