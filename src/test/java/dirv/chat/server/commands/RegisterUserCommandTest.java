package dirv.chat.server.commands;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dirv.chat.Message;
import dirv.chat.server.MessageRepositorySpy;
import org.junit.Test;

import dirv.chat.server.commands.Command;
import dirv.chat.server.commands.RegisterUserCommand;

public class RegisterUserCommandTest extends CommandTest {

	private static final String LINE_SEPARATOR = System.getProperty("line.separator");

	private final List<String> users = new ArrayList<String>();
    private MessageRepositorySpy messageRepository = new MessageRepositorySpy();

    @Test
    public void addsNewName() throws IOException {
        executeCommand("Donald\n");
        Message message = messageRepository.getMessages().get(0);
        assertThat(users, hasItem("Donald"));
        assertEquals(message.getUser(), "system");
//        assertEquals(message.getMessage(), "Donald joined");
    }

    @Test
    public void acknowledgesAdd() throws IOException {
        String output = executeCommand("Donald\n");
        assertEquals("OK" + LINE_SEPARATOR, output.toString());
    }
    
    @Test
    public void doesNotAddIfNoNameGiven() throws IOException {
        String output = executeCommand("");
        assertEquals(0, users.size());
        assertEquals("ERROR" + LINE_SEPARATOR, output.toString());
    }
    
    @Test
    public void doesNotAddIfNameIsBlank() throws IOException {
        String output = executeCommand("\n");
        assertEquals(0, users.size());
        assertEquals("ERROR" + LINE_SEPARATOR, output.toString());
    }
    
    @Test
    public void doesNotAddAlreadyRegisteredNames() throws IOException {
        executeCommand("Donald\n");
        String output = executeCommand("Donald\n");
        assertEquals(1, users.size());
        assertEquals("ERROR" + LINE_SEPARATOR, output.toString());
    }
    
    @Test
    public void trimsNames() throws IOException {
        executeCommand(" Donald \n");
        assertThat(users, hasItem("Donald"));
    }
    
    protected Command command() {
        return new RegisterUserCommand(messageRepository, users);
    }
}
