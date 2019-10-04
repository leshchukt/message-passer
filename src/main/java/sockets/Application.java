package sockets;

/**
 * Main application to execute technical task and run a message exchange between separate Java processes.
 */
public class Application {
    /**
     * Possible arguments to run application
     */
    private static final String AS_SERVER = "asServer";
    private static final String AS_CLIENT = "asClient";

    /**
     * Runs either server or client player depending on args it receives.
     *
     * @param args string with 2 possible options mentioned before.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("The program was launched incorrectly. Please pass the argument.");
            System.exit(1);
        }

        Player player = null;
        switch (args[0]) {
            case AS_SERVER:
                player = new ServerPlayer();
                break;
            case AS_CLIENT:
                player = new ClientPlayer();
                break;
            default:
                System.err.format("The argument was specified incorrectly. The possible options are: '%s', '%s'",
                                  AS_SERVER, AS_CLIENT);
                System.exit(1);
        }
        player.passMessages();
    }
}
