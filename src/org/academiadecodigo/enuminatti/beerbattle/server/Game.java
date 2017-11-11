package org.academiadecodigo.enuminatti.beerbattle.server;

import org.academiadecodigo.enuminatti.beerbattle.client.model.Boat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by codecadet on 07/11/17.
 */
public class Game {

    private Socket socketP1;
    private Socket socketP2;
    private LinkedList<Boat> boatsPlayerOne;
    private LinkedList<Boat> boatsPlayerTwo;
    private Server server;
    private BufferedReader bufferedReaderP1;
    private BufferedReader bufferedReaderP2;
    private PrintWriter printWriterP1;
    private PrintWriter printWriterP2;


    public Game(Server server) {
        boatsPlayerOne = new LinkedList<>();
        boatsPlayerTwo = new LinkedList<>();
        this.server = server;
        this.boatsPlayerOne = boatsPlayerOne;
        this.boatsPlayerTwo = boatsPlayerTwo;
        socketP1 = null;
        socketP2 = null;
        bufferedReaderP1 = null;
        bufferedReaderP2 = null;
        printWriterP1 = null;
        printWriterP2 = null;

    }

    //this method will receive the sockets and prepare the readers/writers
    public void receiveSockets(Socket socket) throws IOException {

        if (socketP1 == null) {
            socketP1 = socket;
            bufferedReaderP1 = new BufferedReader(new InputStreamReader(socketP1.getInputStream()));
            printWriterP1 = new PrintWriter(socketP1.getOutputStream(), true);
            return;
        }
        socketP2 = socket;
        bufferedReaderP2 = new BufferedReader(new InputStreamReader(socketP2.getInputStream()));
        printWriterP2 = new PrintWriter(socketP2.getOutputStream(), true);

    }

    public void interpretMessage() throws IOException {

        String messageP1 = bufferedReaderP1.readLine();
        String[] splitMessageP1 = messageP1.split(" ");

        if (splitMessageP1[0].contains("PUTBOAT")) {
            boatsPlayerOne.add(new Boat());
        }

    }

    public void receiveAttacks() {

    }

    public void respondAttacks() {

    }


    public void receiveLoser() {

    }


    public Boat[] getBoatsPlayerOne() {
        return boatsPlayerOne;
    }

    public Boat[] getBoatsPlayerTwo() {
        return boatsPlayerTwo;
    }
}










/*



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
            socket.close();
            System.out.println("Client disconnected");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    }

}*/



