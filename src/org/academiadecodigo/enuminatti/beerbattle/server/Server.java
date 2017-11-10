package org.academiadecodigo.enuminatti.beerbattle.server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Created by codecadet on 07/11/17.
 */
public class Server {


    public static void main(String[] args) {

        Server server = null;

        try {
            server = new Server();

        } catch (IOException e) {
            e.printStackTrace();
        }
        server.init();
    }


    private ServerSocket serverSocket;
    public final static int PORTNUMBER = 8080;


    public Server() throws IOException {

        serverSocket = new ServerSocket(PORTNUMBER);
        System.out.println("Waiting player " + serverSocket.getLocalPort());
    }


    private void init() {

        int connectionCount = 0;


        while (true) {

            Socket socket = null;

            try {
                socket = serverSocket.accept();
                connectionCount++;
                System.out.println("Player " + connectionCount + " is connected!");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}





