package org.academiadecodigo.enuminatti.beerbattle.client.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by codecadet on 08/11/17.
 */
public class ServiceConnect {

    public static final String QUIT = "/quit";
    private Socket socket;
    private Scanner scanner;


    public ServiceConnect(String hostName, int portNumber) throws IOException {
        socket = new Socket(hostName, portNumber);
    }

    public static void main(String[] args) {

        ServiceConnect c = null;

        try {
            c = new ServiceConnect("localhost", 8080);
        } catch (IOException e) {
            e.printStackTrace();
        }

        c.init();
    }

    public void init() {

        scanner = new Scanner(System.in);

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

            new Thread(new ClientWorker(bufferedReader)).start();

            while (true) {
                String s = scanner.nextLine();

                printWriter.println(s);

                if (s.equals(QUIT)) {
                    break;
                }
            }
            disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {

        try {
            socket.close();
            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class ClientWorker implements Runnable {

        private BufferedReader bufferedReader;

        public ClientWorker(BufferedReader bufferedReader) {
            this.bufferedReader = bufferedReader;
        }

        @Override
        public void run() {

            String a;

            try {
                while (true) {
                    a = bufferedReader.readLine();
                    System.out.println(a);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
