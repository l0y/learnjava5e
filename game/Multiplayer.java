package game;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Multiplayer {
    private Field gameField;
    private int gamePort;
    private volatile boolean keepListening;
    private volatile boolean keepPlaying;
    private volatile boolean startNewGame;
    private volatile boolean disconnecting;
    private Server server;
    private Thread serverThread;
    private Thread clientThread;

    public Multiplayer(Field field) {
        this(field,8677);
    }

    public Multiplayer(Field field, int port) {
        gameField = field;
        gamePort = port;
    }

    public void startServer() {
        keepListening = true;
        keepPlaying = false;
        startNewGame = true;
        disconnecting = false;
        server = new Server();
        serverThread = new Thread(server);
        serverThread.start();
        gameField.setScore(2, "waiting...");
    }

    public void joinGame(String otherServer) {
        clientThread = new Thread(new Client(otherServer));
        clientThread.start();
    }

    public void startGame() {
        startNewGame = true;
    }

    public void disconnect() {
        disconnecting = true;
        keepListening = false;
        // Are we in the middle of a game and regularly checking these flags?
        // If not, just close the server socket to interrupt the blocking accept() method.
        if (server != null && keepPlaying == false) {
            server.stopListening();
        }
        keepPlaying = false;
    }

    class Server implements Runnable {
        ServerSocket listener;

        public void run() {
            Socket socket = null;
            try {
                listener = new ServerSocket(gamePort);
                while (keepListening) {
                    socket = listener.accept();  // wait for connection

                    InputStream in = socket.getInputStream();
                    BufferedReader reader = new BufferedReader( new InputStreamReader(in) );
                    OutputStream out = socket.getOutputStream();
                    PrintWriter writer = new PrintWriter(out, true);

                    while (startNewGame) {
                        // Create a new game, but assume this will be the last
                        writer.println("NEW_GAME");
                        startNewGame = false;

                        // If the client agrees, Send over the location of the trees
                        String response = reader.readLine();
                        if (response != null && response.equals("OK")) {
                            gameField.setupNewGame();
                            for (Tree tree : gameField.trees) {
                                writer.println("TREE " + tree.getPositionX() + " " + tree.getPositionY());
                            }
                        } else {
                            System.err.println("Unexpected start response: " + response);
                            System.err.println("Skipping game and waiting again.");
                            keepPlaying = false;
                            break;
                        }

                        // Start the action!
                        writer.println("START");
                        response = reader.readLine();
                        keepPlaying = response.equals("OK");

                        while (keepPlaying) {
                            try {
                                if (gameField.trees.size() > 0) {
                                    writer.print("SCORE ");
                                } else {
                                    writer.print("END ");
                                    keepPlaying = false;
                                }
                                writer.println(gameField.getScore(1));
                                response = reader.readLine();
                                if (response == null) {
                                    keepPlaying = false;
                                    disconnecting = true;
                                } else {
                                    String parts[] = response.split(" ");
                                    switch (parts[0]) {
                                        case "END":
                                            keepPlaying = false;
                                        case "SCORE":
                                            gameField.setScore(2, parts[1]);
                                            break;
                                        case "DISCONNECT":
                                            disconnecting = true;
                                            keepPlaying = false;
                                            break;
                                        default:
                                            System.err.println("Warning. Unexpected command: " + parts[0] + ". Ignoring.");
                                    }
                                }
                                Thread.sleep(500);
                            } catch(InterruptedException e) {
                                System.err.println("Interrupted while polling. Ignoring.");
                            }
                        }

                        // If we're not disconnecting, ask about playing again with the same player
                        if (!disconnecting) {
                            String message = gameField.getWinner() + " Would you like to ask them to play again?";
                            int myPlayAgain = JOptionPane.showConfirmDialog(gameField, message, "Play Again?", JOptionPane.YES_NO_OPTION);

                            if (myPlayAgain == JOptionPane.YES_OPTION) {
                                // If they haven't disconnected, ask if they want to play again
                                writer.println("PLAY_AGAIN");
                                String playAgain = reader.readLine();
                                if (playAgain != null) {
                                    switch (playAgain) {
                                        case "YES":
                                            startNewGame = true;
                                            break;
                                        case "DISCONNECT":
                                            keepPlaying = false;
                                            startNewGame = false;
                                            disconnecting = true;
                                            break;
                                        default:
                                            System.err.println("Warning. Unexpected response: " + playAgain + ". Not playing again.");
                                    }
                                }
                            }
                        }
                    }

                    if (socket.isConnected()) {
                        // say goodbye
                        writer.println("DISCONNECT");
                        socket.close();
                    }
                }
            } catch (SocketException se) {
                System.err.println("Disconnecting. Closing down server.");
                keepListening = false;
            } catch (IOException ioe) {
                System.err.println("Networking error. Closing down server.");
                ioe.printStackTrace();
                keepListening = false;
            } catch (Exception e) {
                System.err.println("Other exception occurred. Closing down server.");
                e.printStackTrace();
                keepListening = false;
            } finally {
                try {
                    if (socket != null && !socket.isClosed()) {
                        socket.close();
                    }
                } catch (IOException closingException) {
                    System.err.println("Error closing client socket: " + closingException.getMessage());
                }
            }
        }

        public void stopListening() {
            if (listener != null && !listener.isClosed()) {
                try {
                    listener.close();
                } catch (IOException ioe) {
                    System.err.println("Error disconnecting listener: " + ioe.getMessage());
                }
            }
        }
    }

    class Client implements Runnable {
        String gameHost;
        boolean startNewGame;

        public Client(String host) {
            gameHost = host;
            keepPlaying = false;
            startNewGame = false;
        }

        public void run() {
            try (Socket socket = new Socket(gameHost, gamePort)) {

                InputStream in = socket.getInputStream();
                BufferedReader reader = new BufferedReader( new InputStreamReader( in ) );
                OutputStream out = socket.getOutputStream();
                PrintWriter writer = new PrintWriter( out, true );

                // Assume the first game will start...
                startNewGame = true;
                while (startNewGame) {
                    // ... but only the first
                    startNewGame = false;

                    // We expect to see the NEW_GAME command first
                    String response = reader.readLine();

                    // If we don't see that command, bail
                    if (response == null || !response.equals("NEW_GAME")) {
                        System.err.println("Unexpected initial command: " + response);
                        System.err.println("Disconnecting");
                        writer.println("DISCONNECT");
                        return;
                    }
                    // Yay! We're going to play a game. Acknowledge this command
                    writer.println("OK");
                    // And now gather the trees and setup our field
                    gameField.trees.clear();
                    response = reader.readLine();
                    while (response.startsWith("TREE")) {
                        String[] parts = response.split(" ");
                        int x = Integer.parseInt(parts[1]);
                        int y = Integer.parseInt(parts[2]);
                        Tree tree = new Tree();
                        tree.setPosition(x, y);
                        gameField.trees.add(tree);
                        response = reader.readLine();
                    }
                    if (!response.equals("START")) {
                        // Hmm, we should have ended the list of trees with a START, but didn't. Bail out.
                        System.err.println("Unexpected start to the game: " + response);
                        System.err.println("Disconnecting");
                        writer.println("DISCONNECT");
                        return;
                    } else {
                        // Yay again! We're starting a game. Acknowledge this command
                        writer.println("OK");
                        keepPlaying = true;
                        gameField.repaint();
                    }
                    while (keepPlaying) {
                        response = reader.readLine();
                        System.out.println("DEBUG: --" + response + "--");
                        String[] parts = response.split(" ");
                        switch (parts[0]) {
                            case "END":
                                keepPlaying = false;
                            case "SCORE":
                                gameField.setScore(2, parts[1]);
                                break;
                            case "DISCONNECT":
                                disconnecting = true;
                                keepPlaying = false;
                                break;
                            default:
                                System.err.println("Unexpected game command: " + response + ". Ignoring.");
                        }
                        if (disconnecting) {
                            // We're disconnecting or they are. Acknowledge and quit.
                            writer.println("DISCONNECT");
                            return;
                        } else {
                            // If we're not disconnecting, reply with our current score
                            if (gameField.trees.size() > 0) {
                                writer.print("SCORE ");
                            } else {
                                keepPlaying = false;
                                writer.print("END ");
                            }
                            writer.println(gameField.getScore(1));
                        }
                    }
                    if (!disconnecting) {
                        // Check to see if they want to play again
                        response = reader.readLine();
                        if (response != null && response.equals("PLAY_AGAIN")) {
                            // Do we want to play again?
                            String message = gameField.getWinner() + " Would you like to play again?";
                            int myPlayAgain = JOptionPane.showConfirmDialog(gameField, message, "Play Again?", JOptionPane.YES_NO_OPTION);
                            if (myPlayAgain == JOptionPane.YES_OPTION) {
                                writer.println("YES");
                                startNewGame = true;
                            } else {
                                // Not playing again so disconnect.
                                disconnecting = true;
                                writer.println("DISCONNECT");
                            }
                        }
                    }
                }
            }
            catch (IOException e ) {
                System.err.println("Networking error. Closing down client.");
                e.printStackTrace();
            }
        }
    }
}
