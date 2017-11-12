package org.academiadecodigo.enuminatti.beerbattle.client.service;

import org.academiadecodigo.enuminatti.beerbattle.client.controller.Controller;

import java.io.*;
import java.net.Socket;

/**
 * Created by codecadet on 10/11/17.
 */
public class ComunicationService implements Service {

    private Socket serverSocket;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;
    private Controller controller;

    public ComunicationService(int portNumber) throws IOException {

        serverSocket = new Socket("localhost", portNumber);
        bufferedReader = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
        printWriter = new PrintWriter(serverSocket.getOutputStream(), true);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void sendBoats(int x, int y) {

        printWriter.write("PUT " + "MINI " + x + " " + y);
        System.out.println("Boat created at " + x + " " + y);

    }

    public void sendAttack(int x, int y) {

        printWriter.write("ATK " + x + " " + y);
        System.out.println("Attack sent to " + x + " " + y);

    }

    public void sendLoser() {

        printWriter.write("LOSER");
    }

    public void disconnect() throws IOException {

        bufferedReader.close();
        printWriter.close();
        serverSocket.close();
    }

    public void receiveMessage() throws IOException {

        int x = 0;
        int y = 0;
        String messageFromServer = null;
        String[] splitMessage;
        messageFromServer = bufferedReader.readLine();
        splitMessage = messageFromServer.split(" ");

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
                //showDamageOnSecundaryGrid(x, y);
                break;

            case ("MISSED"):
                x = Integer.valueOf(splitMessage[1]);
                y = Integer.valueOf(splitMessage[2]);
                //showWaterOnSecundaryGrid(x, y);
                break;

            case ("WON"):
                //game ends and view updates with winner message
                disconnect();
                break;


            default:
                System.out.println("This wasn't supposed to happen ;)");
                break;

        }
    }
}
