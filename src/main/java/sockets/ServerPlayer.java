package sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Server side of the player. Answers the messages.
 */
public class ServerPlayer extends Player {
    @Override
    protected void communicate(PrintWriter out, BufferedReader in) throws IOException {
        for (int sentMessageCounter = 0; sentMessageCounter < MAX; sentMessageCounter++) {
            final String receivedMessage = receiveMessage(in);
            sendMessage(receivedMessage + ' ' + sentMessageCounter, out);
        }
    }

    @Override
    protected boolean isServer() {
        return true;
    }

    @Override
    protected String getHost() {
        return null;
    }

    @Override
    protected int getPort() {
        return 5565;
    }
}
