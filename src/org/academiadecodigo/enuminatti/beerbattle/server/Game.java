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
    private boolean endGame;


    public Game(Server server) {
        beersPlayerOne = new LinkedList<>();
        beersPlayerTwo = new LinkedList<>();
        this.server = server;
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

        String messageP2 = bufferedReaderP2.readLine();
        String[] splitMessageP2 = messageP2.split(" ");

        createBeers(splitMessageP1, splitMessageP2);
        sendAttacks(splitMessageP1, splitMessageP2);
        receiveLoser(splitMessageP1, splitMessageP2);

    }

    private void createBeers(String[] splitMessageP1, String[] splitMessageP2) throws IOException {

        if (splitMessageP1[0].contains("PUT")) {

            String typeOfBeer = splitMessageP1[1];
            BeerType beerType = BeerType.valueOf(typeOfBeer);
            int x = Integer.parseInt(splitMessageP1[2]);
            int y = Integer.parseInt(splitMessageP1[3]);

            beersPlayerOne.add(new Beer(beerType, x, y));
        }

        if (splitMessageP2[0].contains("PUT")) {

            String typeOfBeer = splitMessageP2[1];
            BeerType beerType = BeerType.valueOf(typeOfBeer);
            int x = Integer.parseInt(splitMessageP2[2]);
            int y = Integer.parseInt(splitMessageP2[3]);

            beersPlayerTwo.add(new Beer(beerType, x, y));
        }

    }

    // Method to send attacks through command "ATK"
    // Checks if the attack is successful and answers to players

    private void sendAttacks(String[] splitMessageP1, String[] splitMessageP2) throws IOException {

        if (splitMessageP1[0].contains("ATK")) {

            int x = Integer.parseInt(splitMessageP1[1]);
            int y = Integer.parseInt(splitMessageP1[2]);
            if (checkIfIsHit(x, y, "p2")) {
                printWriterP1.write("HIT " + x + " " + y);
                printWriterP2.write("HITTED " + x + " " + y);
                return;
            }
            printWriterP1.write("MISS " + x + " " + y);
            printWriterP2.write("MISSED " + x + " " + y);
        }

        if (splitMessageP2[0].contains("ATK")) {

            int x = Integer.parseInt(splitMessageP2[1]);
            int y = Integer.parseInt(splitMessageP2[2]);
            if (checkIfIsHit(x, y, "p1")) {
                printWriterP2.write("HIT " + x + " " + y);
                printWriterP1.write("HITTED " + x + " " + y);
                return;
            }
            printWriterP2.write("MISS " + x + " " + y);
            printWriterP1.write("MISSED " + x + " " + y);
        }
    }


    // <3 method to confirm if beer has been hit by players

    private boolean checkIfIsHit(int x, int y, String player) {


        if (player.equals("p2")) {
            for (Beer b : beersPlayerTwo) {
                if (b.getX() == x && b.getY() == y) {
                    System.out.println(player + " was hit");
                    return true;
                }

            }
        } else if (player.equals("p1")) {
            for (Beer b : beersPlayerOne) {
                if (b.getX() == x && b.getY() == y) {
                    System.out.println(player + " was hit");
                    return true;
                }
            }
        }
        return false;
    }

    private void receiveLoser(String[] splitMessageP1, String[] splitMessageP2) throws IOException {

        if (splitMessageP1[0].contains("LOSER")) {
            endGame = true;
            printWriterP2.write("WON");
            return;
        }

        if (splitMessageP2[0].contains("LOSER")) {
            endGame = true;
            printWriterP1.write("WON");
            return;
        }
    }

    public void disconnect() {

        try {
            bufferedReaderP1.close();
            bufferedReaderP2.close();
            printWriterP1.close();
            printWriterP2.close();
            socketP1.close();
            socketP2.close();
            System.out.println("Players disconnected");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




