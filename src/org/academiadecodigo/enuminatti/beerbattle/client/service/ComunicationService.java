package org.academiadecodigo.enuminatti.beerbattle.client.service;

import org.academiadecodigo.enuminatti.beerbattle.client.controller.Controller;
import org.academiadecodigo.enuminatti.beerbattle.client.model.Beer;
import org.academiadecodigo.enuminatti.beerbattle.client.model.Grid;

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

    public ComunicationService(int portNumber, Grid grid) throws IOException {
        this.grid = grid;
        serverSocket = new Socket("localhost", portNumber);
        bufferedReader = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
        printWriter = new PrintWriter(serverSocket.getOutputStream(), true);
        Thread thread = new Thread(this);
        thread.start();
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void sendBeers() {

        Set<Beer> beers = grid.getBeersSet();

        String messageBitch = "PUT ";

        for (Beer b : beers) {
            int x = b.getX();
            int y = b.getY();

            messageBitch += "" + x + " ";
            messageBitch += "" + y + " ";
        }

        printWriter.println(messageBitch);
        System.out.println(messageBitch);
    }

    public void sendAttack(int x, int y) {

        printWriter.println("ATK " + x + " " + y);
        System.out.println("Attack sent to " + x + " " + y);
    }

    public void sendLoser() {

        printWriter.println("LOSER");
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
                //showHitOnMainGrid(x, y);
                break;

            case ("MISS"):
                x = Integer.valueOf(splitMessage[1]);
                y = Integer.valueOf(splitMessage[2]);
                //showMissOnMainGrid(x, y);
                break;

            case ("HITTED"):
                x = Integer.valueOf(splitMessage[1]);
                y = Integer.valueOf(splitMessage[2]);
                controller.releaseStartButton();

                //controller.drawHitted(x,y);
                break;

            case ("MISSED"):
                x = Integer.valueOf(splitMessage[1]);
                y = Integer.valueOf(splitMessage[2]);
                controller.releaseStartButton();
                //controller.drawMissed(x,y);
                break;

            case ("WON"):
                //game ends and view updates with winner message
                System.out.println("ganhaste!!!!!!!!!!");
                //disconnect();
                break;

            case ("BOATS READY"):
                System.out.println("boats rdy");
                break;

            default:
                System.out.println("This wasn't supposed to happen ;)");
                break;

        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                receiveMessage();

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }
}
