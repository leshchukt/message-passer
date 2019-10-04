package sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Main class that creates socket - the endpoint at localhost:5565 of a two-way message exchange.
 */
public abstract class Player {
    /**
     * Amount of messages to send and receive
     */
    public static final int MAX = 10;
    /**
     * Message to initiate the message exchange
     */
    protected static final String MESSAGE = "How many messages have you already sent?";

    /**
     * Creates socket for either server or client depending on {@code isServer()} return value. Wraps socket output
     * stream to {@code PrintWriter} and input stream to {@code BufferedReader}. Calls {@code communicate()} method.
     * Stops program in case of exceptions.
     */
    public void passMessages() {
        try (
            ServerSocket serverSocket = isServer() ?
                new ServerSocket(getPort()) :
                null;
            Socket clientSocket = isServer() ?
                serverSocket.accept() :
                new Socket(getHost(), getPort());
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {
            communicate(out, in);
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + getHost());
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port " +
                                   getPort() + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Sends the message and logs the process.
     *
     * @param message for sending
     * @param out     writer where the message is printed
     */
    protected void sendMessage(String message, PrintWriter out) {
        System.out.format("--> %s is sending the message...\n", getPlayerName());
        out.println(message);
    }

    /**
     * Receives the message and logs it.
     *
     * @param in reader from where the message should be read
     *
     * @return received message
     * @throws IOException if an I/O error occurs in {@code readLine()}
     */
    protected String receiveMessage(BufferedReader in) throws IOException {
        final String receivedMessage = in.readLine();
        System.out.format("<-- %s has received the message: '%s'\n", getPlayerName(), receivedMessage);
        return receivedMessage;
    }

    /**
     * Provides main message exchange body. Should be implemented on both server's and client's side.
     *
     * @param out writer where the message is printed
     * @param in  reader from where the message should be read
     *
     * @throws IOException if an I/O error occurs while reading the message
     */
    protected abstract void communicate(PrintWriter out, BufferedReader in) throws IOException;

    /**
     * @return {@code true} if it is a server, {@code false} otherwise
     */
    protected abstract boolean isServer();

    protected abstract String getHost();

    protected abstract int getPort();

    /**
     * @return the player's name depending on {@code isServer()} return value
     */
    private String getPlayerName() {
        return isServer() ? "answerer" : "initiator";
    }
}
