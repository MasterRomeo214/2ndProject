package org.academiadecodigo.enuminatti.beerbattle.client.service;

import java.io.*;
import java.net.Socket;

/**
 * Created by codecadet on 10/11/17.
 */
public class ComunicationService {

    private Socket serverSocket;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;

    public ComunicationService(int portNumber) throws IOException {
        serverSocket = new Socket("Player", portNumber);
        bufferedReader = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
        printWriter = new PrintWriter(serverSocket.getOutputStream(), true);
    }


    public void sendBoats(int x, int y) {

        printWriter.write("PUT " + "MINI " + x + " " + y);

    }

    public void sendAttack(int x, int y) {

        printWriter.write("ATK " + x + " " + y);

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

        String messageFromServer = null;
        String[] splitMessage;
        messageFromServer = bufferedReader.readLine();
        splitMessage = messageFromServer.split(" ");

        switch (splitMessage[0]) {

            int x = 0;
            int y = 0;
            //HIT means that the player was successful on the target
            case ("HIT"):
                x = Integer.valueOf(splitMessage[1]);
                y = Integer.valueOf(splitMessage[2]);
                showHitOnMainGrid(x, y);
                break;

            case ("MISS"):
                x = Integer.valueOf(splitMessage[1]);
                y = Integer.valueOf(splitMessage[2]);
                showMissOnMainGrid(x, y);
                break;

            case ("HITTED"):
                x = Integer.valueOf(splitMessage[1]);
                y = Integer.valueOf(splitMessage[2]);
                showDamageOnSecundaryGrid(x, y);
                break;

            case ("MISSED"):
                x = Integer.valueOf(splitMessage[1]);
                y = Integer.valueOf(splitMessage[2]);
                showWaterOnSecundaryGrid(x, y);
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
