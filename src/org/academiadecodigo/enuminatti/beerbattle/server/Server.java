package org.academiadecodigo.enuminatti.beerbattle.server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Created by codecadet on 07/11/17.
 */
public class Server {

    private ServerSocket serverSocket;
    public final static int PORTNUMBER = 8080;
    Game game = null;


    public Server() throws IOException {

        serverSocket = new ServerSocket(PORTNUMBER);
        System.out.println("Waiting player " + serverSocket.getLocalPort());
    }


    private void init() {

        int connectionCount = 0;
        game = new Game(this);


        while (connectionCount < 3) {

            Socket socket = null;

            try {
                socket = serverSocket.accept();
                game.receiveSockets(socket);

                connectionCount++;
                System.out.println("Player " + connectionCount + " is connected!");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        Server server = null;

        try {
            server = new Server();
            server.init();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}





