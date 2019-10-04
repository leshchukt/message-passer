package sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Client side of the player. Initiates the message exchange.
 */
public class ClientPlayer extends Player {
    /**
     * Initiates the message exchange:
     * <ul>
     * <li>sends 10 messages to client socket {@code out} stream</li>
     * <li>receives 10 answers from client socket {@code in} stream</li>
     * <li>stops when 10 messages are received</li>
     * </ul>
     *
     * @param out client socket output stream
     * @param in  client socket input stream
     *
     * @throws IOException if {@code receiveMessage()} throws the exception
     */
    @Override
    protected void communicate(PrintWriter out, BufferedReader in) throws IOException {
        for (int sentMessageCounter = 0; sentMessageCounter < MAX; sentMessageCounter++) {
            sendMessage(MESSAGE, out);
            receiveMessage(in);
        }
        System.out.println("The stop condition is met.");
    }

    /**
     * @return {@code false} because it's client's implementation
     */
    @Override
    protected boolean isServer() {
        return false;
    }

    /**
     * @return localhost because it executes on local machine
     */
    @Override
    protected String getHost() {
        return "localhost";
    }

    /**
     * @return the same random port server has
     */
    @Override
    protected int getPort() {
        return 5565;
    }
}
