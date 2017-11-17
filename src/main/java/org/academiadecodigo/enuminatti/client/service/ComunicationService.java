package org.academiadecodigo.enuminatti.client.service;

import org.academiadecodigo.enuminatti.client.controller.Controller;
import org.academiadecodigo.enuminatti.client.model.Beer;
import org.academiadecodigo.enuminatti.client.model.Grid;
import org.academiadecodigo.enuminatti.utils.Sound;

import java.io.*;
import java.net.Socket;
import java.util.Set;

/**
 * Created by codecadet on 10/11/17.
 */
public class ComunicationService implements Service, Runnable {

    private Socket serverSocket;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;
    private Controller controller;
    private Grid grid;
    private int player;
    private boolean endGame = false;

    //HIT SOUND
    private Sound carica = new Sound("/sounds/carica.wav");
    private Sound arrotoJP = new Sound("/sounds/arrotoJP.wav");
    private Sound arrotoR = new Sound("/sounds/arrotoR.wav");
    private Sound gluglu = new Sound("/sounds/gluglu.wav");

    //MISS SOUND
    private Sound naoTiraSede = new Sound("/sounds/naoTiraSede.wav");
    private Sound trazCerveja = new Sound("/sounds/trazCerveja.wav");
    private Sound eh = new Sound("/sounds/eh.wav");

    //HITTED SOUND
    private Sound ahCara = new Sound("/sounds/ahCara.wav");
    private Sound ohMinha = new Sound("/sounds/ohMinha.wav");
    private Sound olha = new Sound("/sounds/olha.wav");

    //MISSED SOUND
    private Sound agua = new Sound("/sounds/agua.wav");
    private Sound naoSabias = new Sound("/sounds/naoSabias.wav");
    private Sound torto = new Sound("/sounds/torto.wav");

    //END SOUND
    private Sound loser = new Sound("/sounds/loser.wav");
    private Sound winner = new Sound("/sounds/winner.wav");

    Thread thread;


    public ComunicationService(int portNumber, Grid grid) throws IOException {
        this.grid = grid;
        serverSocket = new Socket("192.168.1.12", portNumber);
        bufferedReader = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
        printWriter = new PrintWriter(serverSocket.getOutputStream(), true);
        thread = new Thread(this);
        thread.start();
        player = 0;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getPlayer() {

        return player;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void sendBeers() {

        Set<Beer> beers = grid.getBeersSet();

        String message = "PUT ";

        for (Beer b : beers) {
            int x = b.getX();
            int y = b.getY();

            message += "" + x + " ";
            message += "" + y + " ";
        }

        printWriter.println(message);
        System.out.println(message);
    }

    public void sendAttack(int x, int y) {

        printWriter.println("ATK " + x + " " + y);
        System.out.println("Attack sent to " + x + " " + y);
    }

    public void disconnect() throws IOException {

        bufferedReader.close();
        printWriter.close();
        serverSocket.close();
    }

    public void receiveMessage() throws IOException {

        int x = 0;
        int y = 0;
        String messageFromServer = "";

        messageFromServer = bufferedReader.readLine();

        String[] splitMessage = messageFromServer.split(" ");

        switch (splitMessage[0]) {

            //HIT means that the player was successful on the target
            case ("HIT"):
                x = Integer.valueOf(splitMessage[1]);
                y = Integer.valueOf(splitMessage[2]);
                controller.drawHit(x, y);
                playHitSound();
                break;

            case ("MISS"):
                x = Integer.valueOf(splitMessage[1]);
                y = Integer.valueOf(splitMessage[2]);
                controller.drawMiss(x, y);
                playMissSound();
                break;

            case ("HITTED"):
                x = Integer.valueOf(splitMessage[1]);
                y = Integer.valueOf(splitMessage[2]);
                controller.releaseStartButton();
                controller.drawHitted(x, y);
                playHittedSound();
                break;

            case ("MISSED"):
                x = Integer.valueOf(splitMessage[1]);
                y = Integer.valueOf(splitMessage[2]);
                controller.releaseStartButton();
                controller.drawMissed(x, y);
                playMissedSound();
                break;

            case ("PLAYER1"):
                player = 1;
                break;

            case ("WON"):
                controller.changeImageToWinner();
                controller.stopSound();
                winner.play(true);
                endGame = true;
                disconnect();
                break;

            case ("LOSER"):
                controller.changeImageToLoser();
                controller.stopSound();
                loser.play(true);
                endGame = true;
                disconnect();
                break;

            default:
                System.out.println("This wasn't supposed to happen ;)");
                break;
        }
    }

    private void playHitSound() {
        int randomNumber = 1;

        switch (randomNumber = (int) (Math.random() * 6)){
            case 1:
                gluglu.play(true);
                break;
            case 2:
                arrotoJP.play(true);
                break;
            case 3:
                break;
            case 4:
                arrotoR.play(true);
                break;
            case 5:
                break;
            case 6:
                carica.play(true);
                break;
        }
    }

    private void playMissSound() {
        int randomNumber = 1;

        switch (randomNumber = (int) (Math.random() * 6)){
            case 1:
                naoTiraSede.play(true);
                break;
            case 2:
                trazCerveja.play(true);
                break;
            case 3:
                break;
            case 4:
                eh.play(true);
                break;
            case 5:
                break;
        }
    }

    private void playHittedSound() {
        int randomNumber = 1;

        switch (randomNumber = (int) (Math.random() * 6)){
            case 1:
                ahCara.play(true);
                break;
            case 2:
                break;
            case 3:
                ohMinha.play(true);
                break;
            case 4:
                olha.play(true);
                break;
            case 5:
                break;
        }
    }

    private void playMissedSound() {
        int randomNumber = 1;

        switch (randomNumber = (int) (Math.random() * 6)){
            case 1:
                break;
            case 2:
                agua.play(true);
                break;
            case 3:
                naoSabias.play(true);
                break;
            case 4:
                torto.play(true);
                break;
            case 5:
                break;
        }
    }




    @Override
    public void run() {
        while (!endGame) {
            try {
                receiveMessage();

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
