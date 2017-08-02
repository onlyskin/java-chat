package dirv.chat.server;

public class MessageWatcher {
    private final HangmanBot hangmanBot;

    public MessageWatcher(HangmanBot hangmanBot) {
        this.hangmanBot = hangmanBot;
    }

    public void receiveMessage(String message) {
        hangmanBot.receiveMessage(message);
    }
}