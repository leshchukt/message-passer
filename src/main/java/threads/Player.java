package threads;

import java.util.concurrent.BlockingQueue;

/**
 * Player is a thread that encapsulates producer-consumer functionality and fulfills the message exchange within one
 * Java process.
 */
public class Player extends Thread {
    /**
     * Amount of messages to send and receive
     */
    public static final int MAX = 10;
    /**
     * Message to initiate the message exchange
     */
    private static final String MESSAGE = "How many messages have you already sent?";

    /**
     * Queue that contains initiator's sent messages
     */
    private final BlockingQueue<String> in;
    /**
     * Queue that contains initiator's received messages
     */
    private final BlockingQueue<String> out;

    /**
     * Name of the player for correct logging
     */
    private String playerName;

    public Player(String playerName,
                  BlockingQueue<String> in,
                  BlockingQueue<String> out) {
        this.playerName = playerName;
        this.in = in;
        this.out = out;
    }

    /**
     * Initiates the message exchange:
     * <ul>
     * <li>sends 10 messages to {@code in} queue in a loop</li>
     * <li>receives 10 answers from {@code out} queue, stops when 10 messages are received</li>
     * </ul>
     *
     * @throws InterruptedException if {@code sendMessage()} or {@code receiveMessage()} throws the exception
     */
    protected void initiate() throws InterruptedException {
        for (int sentMessageCounter = 0; sentMessageCounter < MAX; sentMessageCounter++) {
            sendMessage(Player.MESSAGE, in);
        }

        int receivedMessageCounter = 0;
        do {
            receiveMessage(out);
            receivedMessageCounter++;
        } while (receivedMessageCounter != MAX);
        System.out.println("The stop condition is met.");
    }

    /**
     * Answers the initiator: receives messages and sends back the same messages concatenated with the value of a
     * counter holding the number of messages it already sent.
     *
     * @throws InterruptedException if {@code sendMessage()} or {@code receiveMessage()} throws the exception
     */
    protected void answer() throws InterruptedException {
        int sentMessageCounter = 0;
        while (sentMessageCounter < MAX) {
            final String receivedMessage = receiveMessage(in);
            sendMessage(receivedMessage + ' ' + sentMessageCounter, out);
            sentMessageCounter++;
        }
    }

    /**
     * Sends the message and logs the process
     *
     * @param message for sending
     * @param to      queue where the message should be put
     *
     * @throws InterruptedException if queue is interrupted while waiting
     */
    private void sendMessage(String message, BlockingQueue<String> to) throws InterruptedException {
        System.out.format("--> %s is sending the message...\n", playerName);
        to.put(message);
    }

    /**
     * Receives the message and logs it.
     *
     * @param from queue from where the message should be taken
     *
     * @return received message
     * @throws InterruptedException if queue is interrupted while waiting
     */
    private String receiveMessage(BlockingQueue<String> from) throws InterruptedException {
        final String receivedMessage = from.take();
        System.out.format("<-- %s has received the message: '%s'\n", playerName, receivedMessage);
        return receivedMessage;
    }

}