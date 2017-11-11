package org.academiadecodigo.enuminatti.beerbattle.server;

import javafx.fxml.Initializable;
import org.academiadecodigo.enuminatti.beerbattle.client.model.Beer;
import org.academiadecodigo.enuminatti.beerbattle.client.model.BeerType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;


/**
 * Created by codecadet on 07/11/17.
 */
public class Game {

    private Socket socketP1;
    private Socket socketP2;
    private LinkedList<Beer> beersPlayerOne;
    private LinkedList<Beer> beersPlayerTwo;
    private Server server;
    private BufferedReader bufferedReaderP1;
    private BufferedReader bufferedReaderP2;
    private PrintWriter printWriterP1;
    private PrintWriter printWriterP2;


    public Game(Server server) {
        beersPlayerOne = new LinkedList<>();
        beersPlayerTwo = new LinkedList<>();
        this.server = server;
        this.beersPlayerOne = beersPlayerOne;
        this.beersPlayerTwo = beersPlayerTwo;
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

    public void createBeer() throws IOException {

        String messageP1 = bufferedReaderP1.readLine();
        String[] splitMessageP1 = messageP1.split(" ");

        if (splitMessageP1[0].contains("PUTBEER")) {
            String typeOfBeer = splitMessageP1[1];
            BeerType beerType = BeerType.valueOf(typeOfBeer);
            int x = Integer.parseInt(splitMessageP1[2]);
            int y = Integer.parseInt(splitMessageP1[3]);

            beersPlayerOne.add(new Beer(beerType, x, y));
        }
        
    }

    public void receiveAttacks() {

    }

    public void respondAttacks() {

    }


    public void receiveLoser() {

    }


    public Beer[] getBoatsPlayerOne() {
        return beersPlayerOne;
    }

    public Beer[] getBeersPlayerTwo() {
        return beersPlayerTwo;
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



