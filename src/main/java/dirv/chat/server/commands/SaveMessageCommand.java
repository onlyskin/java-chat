package dirv.chat.server.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import dirv.chat.server.MessageRepository;
import dirv.chat.server.MessageWatcher;

public class SaveMessageCommand extends RecognizedCommand {

    private final MessageRepository messageRepository;
    private final List<String> users;
    private final MessageWatcher messageWatcher;

    public SaveMessageCommand(MessageRepository messageRepository, List<String> users, MessageWatcher messageWatcher) {
        super("2");
        this.messageRepository = messageRepository;
        this.users = users;
        this.messageWatcher = messageWatcher;
    }

    @Override
    public void execute(BufferedReader reader, PrintWriter printWriter) throws IOException {
        String user = reader.readLine();
        if (!users.contains(user)) {
            printWriter.println("ERROR");
            return;
        }
        String message = reader.readLine();
        messageRepository.receiveMessage(user, message);
        messageWatcher.receiveMessage(message);
        printWriter.println("OK");
    }
}
