package dirv.chat.server;

import dirv.chat.server.commands.HangmanSpy;
import org.junit.Test;

import dirv.chat.Message;
import dirv.chat.SocketStub;
import dirv.chat.server.Server;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static dirv.chat.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServerTest {

	private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    private ServerSocketFactoryStub serverSocketFactory = new ServerSocketFactoryStub();
    private List<String> users = new ArrayList<String>();
    private MessageRepositorySpy messageRepository = new MessageRepositorySpy();
    private MessageWatcherSpy messageWatcher = new MessageWatcherSpy(
            new HangmanBotSpy(messageRepository, new HangmanSpy())
    );

    @Test
    public void listensOnSpecifiedPort() throws IOException {
        startListening(5678);
        assertEquals(5678, serverSocketFactory.getPort());
    }
    
    @Test
    public void acceptsASocket() throws IOException {
        SocketStub client = receiveClientMessage("");
        startListening();
        assertEquals(1, serverSocketFactory.acceptedSockets.size());
        assertEquals(client, serverSocketFactory.acceptedSockets.get(0));
    }
    
    @Test
    public void connectsANewClient() throws IOException {
        receiveClientMessage("1", "Donald");
        startListening();
        assertThat(users, hasItem("Donald"));
    }
    
    @Test
    public void receiveMessages() throws IOException {
        users.add("Donald");
        receiveClientMessage("2", "Donald", "Hello, world!");
        startListening();
        assertEquals(1, messageRepository.getMessages().size());
        assertEquals("Donald", message(0).getUser());
        assertEquals("Hello, world!", message(0).getMessage());
    }
    
    @Test
    public void SendAMessageOnUserRegistration() throws IOException {
        receiveClientMessage("1", "Donald");
        startListening();
        assertEquals(1, messageRepository.getMessages().size());
    }
    
    @Test
    public void doNotAddAUserOnMessageSend() {
        receiveClientMessage("2", "Donald", "Hello, world!");
        startListening();
        assertEquals(0, users.size());
    }
    
    @Test
    public void ignoresUnrecognizedCommands() {
        receiveClientMessage("x");
        startListening();
    }
    
    @Test
    public void sendMessageAcknowledgement() {
        SocketStub client = receiveClientMessage("1", "Donald");
        startListening();
        assertEquals("OK" + LINE_SEPARATOR, client.getOutput());
    }

    @Test
    public void sendMessagesFromTimestamp() {
        addMessage(100, "Donald", "Hello, world!");
        addMessage(200, "Donald", "Hello?");
        SocketStub client = receiveClientMessage("3", "2");
        startListening();
        List<String> expected = Arrays.asList(
                "100", "Donald", "Hello, world!",
                "200", "Donald", "Hello?");
        assertEqualsLines(expected, client.getOutput());
    }
    
    @Test
    public void handlesMultipleClients() {
        receiveClientMessage("1", "Donald");
        receiveClientMessage("2", "Donald", "Hello, world!");
        SocketStub lastClient = receiveClientMessage("3", "0");
        startListening();
        List<String> expected = Arrays.asList(
                "-1", "system", "Donald joined", "-1", "Donald", "Hello, world!");
        assertEqualsLines(expected, lastClient.getOutput());
    }
    
    private void addMessage(long timestamp, String name, String message) {
        messageRepository.add(timestamp, name, message);
    }

    private SocketStub receiveClientMessage(String... message) {
        String allLines = String.join(System.lineSeparator(), message)
                + System.lineSeparator();
        SocketStub client = new SocketStub(allLines);
        serverSocketFactory.addClient(client);
        return client;
    }

    private void startListening() {
        startListening(3000);
    }

    private void startListening(int port) {
        Server server = new Server(serverSocketFactory, users, messageRepository, port, messageWatcher);
        server.run();
    }

    private Message message(int number) {
        return messageRepository.getMessages().get(number);
    }
}
