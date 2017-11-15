package org.academiadecodigo.enuminatti.beerbattle.server;

import org.academiadecodigo.enuminatti.beerbattle.client.model.Beer;
import org.academiadecodigo.enuminatti.beerbattle.utils.Sound;

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
    //array of players
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

    private Sound ambientSound = new Sound("/resources/ambient.wav");


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

        ambientSound.play(true);

        System.out.println("Start");

        String messageP1 = "";
        String messageP2 = "";

        messageP1 = bufferedReaderP1.readLine();
        System.out.println(messageP1);

        String[] splitMessageP1 = messageP1.split(" ");
        String[] splitMessageP2 = messageP2.split(" ");

        createBeers(splitMessageP1, splitMessageP2);
        sendAttacks(splitMessageP1, splitMessageP2);
        readyPlayer(splitMessageP1, splitMessageP2);
        if (beersPlayerOne.size()==0){
           // ambientSound.stop();
        sendLoser(splitMessageP1, splitMessageP2);

        }


        messageP2 = bufferedReaderP2.readLine();
        System.out.println(messageP2);

        messageP1 = "";
        splitMessageP1 = messageP1.split(" ");
        splitMessageP2 = messageP2.split(" ");

        createBeers(splitMessageP1, splitMessageP2);
        sendAttacks(splitMessageP1, splitMessageP2);
        readyPlayer(splitMessageP1, splitMessageP2);
        if (beersPlayerTwo.size()==0){
           // ambientSound.stop();
            sendLoser(splitMessageP1, splitMessageP2);

        }



    }

    private void readyPlayer(String[] splitMessageP1, String[] splitMessageP2) {
        if (splitMessageP1[0].contains("READY")) {
            printWriterP2.println("READY");
            return;
        }
        if (splitMessageP2[0].contains("READY")) {
            printWriterP1.println("READY");
        }
    }

    private void createBeers(String[] splitMessageP1, String[] splitMessageP2) throws IOException {
        if (splitMessageP1[0].contains("PUT")) {
            int count = 1;

            while (count < 29) {

                int x = 0;
                int y = 0;

                if (count % 2 != 0) {
                    x = Integer.parseInt(splitMessageP1[count]);
                    count++;

                }
                y = Integer.parseInt(splitMessageP1[count]);
                count++;

                Beer beer = new Beer(x, y);
                System.out.println("p1 beer created at " + x + " " + y);
                beersPlayerOne.add(beer);
            }
            printWriterP1.println("BOATS READY");
            return;
        }

        if (splitMessageP2[0].contains("PUT")) {
            int count = 1;
            int x = 0;
            int y = 0;

            while (count < 29) {

                if (count % 2 != 0) {
                    x = Integer.parseInt(splitMessageP2[count]);
                    count++;

                }
                y = Integer.parseInt(splitMessageP2[count]);
                count++;

                Beer beer = new Beer(x, y);
                System.out.println("p2 beer created at " + x + " " + y);
                beersPlayerTwo.add(beer);
            }
            printWriterP2.println("BOATS READY");
        }


    }


    // Method to send attacks through command "ATK"
    // Checks if the attack is successful and answers to players

    private void sendAttacks(String[] splitMessageP1, String[] splitMessageP2) throws IOException {

        if (splitMessageP1[0].contains("ATK")) {

            int x = Integer.parseInt(splitMessageP1[1]);
            int y = Integer.parseInt(splitMessageP1[2]);
            if (checkIfIsHit(x, y, "p2")) {
                printWriterP1.println("HIT " + x + " " + y);
                printWriterP2.println("HITTED " + x + " " + y);
                return;
            }
            printWriterP1.println("MISS " + x + " " + y);
            printWriterP2.println("MISSED " + x + " " + y);
        }

        if (splitMessageP2[0].contains("ATK")) {

            int x = Integer.parseInt(splitMessageP2[1]);
            int y = Integer.parseInt(splitMessageP2[2]);
            if (checkIfIsHit(x, y, "p1")) {
                printWriterP2.println("HIT " + x + " " + y);
                printWriterP1.println("HITTED " + x + " " + y);
                return;
            }
            printWriterP2.println("MISS " + x + " " + y);
            printWriterP1.println("MISSED " + x + " " + y);
        }
    }


    // <3 method to confirm if beer has been hit by players

    private boolean checkIfIsHit(int x, int y, String player) {


        if (player.equals("p2")) {
            for (Beer b : beersPlayerTwo) {
                if (b.getX() == x && b.getY() == y) {
                    System.out.println(player + " was hit");
                    beersPlayerTwo.remove(b);
                    return true;
                }

            }
        }
        if (player.equals("p1")) {
            for (Beer b : beersPlayerOne) {
                if (b.getX() == x && b.getY() == y) {
                    System.out.println(player + " was hit");
                    beersPlayerOne.remove(b);
                    return true;
                }
            }
        }
        return false;
    }

    private void sendLoser(String[] splitMessageP1, String[] splitMessageP2) throws IOException {

        if (beersPlayerOne.size()==0) {
            endGame = true;
            printWriterP2.println("WON");
            return;
        }

        if (beersPlayerTwo.size()==0) {
            endGame = true;
            printWriterP1.println("WON");
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




