package threads;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Main application to execute technical task and run a message exchange within one Java process.
 */
public class Application {
    /**
     * Shared message queues for initiator and answerer.
     */
    private BlockingQueue<String> inMessageQueue = new ArrayBlockingQueue<String>(Player.MAX);
    private BlockingQueue<String> outMessageQueue = new ArrayBlockingQueue<String>(Player.MAX);

    public static void main(String[] args) {
        new Application().passMessages();
    }

    /**
     * Runs a message exchange between initiator and answerer.
     */
    private void passMessages() {
        new Player("initiator", inMessageQueue, outMessageQueue) {
            @Override
            public void run() {
                System.out.println("Initiator thread is starting...");
                try {
                    initiate();
                } catch (InterruptedException ignored) {
                }
                System.out.println("Initiator thread is done.");
            }
        }.start();

        new Player("answerer", inMessageQueue, outMessageQueue) {
            @Override
            public void run() {
                System.out.println("Answerer thread is starting...");
                try {
                    answer();
                } catch (InterruptedException ignored) {
                }
                System.out.println("Answerer thread is done.");
            }
        }.start();
    }
}
