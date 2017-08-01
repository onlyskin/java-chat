package dirv.chat.server.commands;

import dirv.chat.Message;
import dirv.chat.server.MessageRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class RegisterUserCommand extends RecognizedCommand {

    private final MessageRepository messageRepository;
    private final List<String> users;

    public RegisterUserCommand(MessageRepository messageRepository, List<String> users) {
        super("1");
        this.messageRepository = messageRepository;
        this.users = users;
    }

    @Override
    public void execute(BufferedReader reader, PrintWriter printWriter) throws IOException {
        String user = reader.readLine();

        boolean added = attemptAdd(user);
        if(added) {
            messageRepository.receiveMessage("system", user + " joined");
            printWriter.println("OK");
        } else {
            printWriter.println("ERROR");
        }
    }
    
    private boolean attemptAdd(String user) {
        if (user == null) return false;
        user = user.trim();
        if (user.isEmpty()) return false;
        if (users.contains(user)) return false;
        users.add(user);
        return true;
    }

}
