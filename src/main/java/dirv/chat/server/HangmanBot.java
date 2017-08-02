package dirv.chat.server;

import dirv.chat.server.Hangman;

public class HangmanBot {
    private MessageRepository messageRepository;
    private Hangman hangman;

    public HangmanBot(MessageRepository messageRepository, Hangman hangman) {
        this.messageRepository = messageRepository;
        this.hangman = hangman;
    }

    public void receiveMessage(String message) {
        if (message.startsWith("@hangman")) {
            String guess = getGuess(message);
            String reply = hangman.replyToInput(guess);
            messageRepository.receiveMessage("hangman", reply);
        }
    }

    private String getGuess(String message) {
        return Character.toString(message.charAt(message.length() - 1));
    }
}
