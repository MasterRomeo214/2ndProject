package org.academiadecodigo.enuminatti.beerbattle.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

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
    private int portNumber = 8080;
    private LinkedList<ServerWorker> clients;
    int connectionCount = 0;



    public Server() throws IOException {
        clients = new LinkedList<>();
        serverSocket = new ServerSocket(portNumber);
        System.out.println("waiting client " + serverSocket.getLocalPort());

    }


    private void init() {
        while (true) {


            try {
                Socket socket = serverSocket.accept();
                connectionCount++;
                System.out.println("Client Connected!");
                ServerWorker serverWorker = new ServerWorker(socket);
                clients.add(serverWorker);
                new Thread(serverWorker).start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void broadcast(String b, ServerWorker serverWorker) {

        for (ServerWorker client : clients) {
            if (client == serverWorker) {
                continue;
            }
            client.printMessage(b);
        }
    }

    public class ServerWorker implements Runnable {

        private BufferedReader bufferedReader;
        private PrintWriter printWriter;
        private final Socket socket;

        public ServerWorker(Socket socket) {
            this.socket = socket;
            bufferedReader = null;
            printWriter = null;
        }


        public String getMessage() {

            String b = null;

            try {
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                b = bufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return b;
        }

        public void printMessage(String b) {
            try {
                printWriter = new PrintWriter(socket.getOutputStream(), true);
                printWriter.println(b);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void disconnect() {

            try {
                bufferedReader.close();
                printWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        @Override
        public void run() {

            while (true) {
                String message = getMessage();
                broadcast(message, this);
            }
        }
    }







}
