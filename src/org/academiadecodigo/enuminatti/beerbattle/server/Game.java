package org.academiadecodigo.enuminatti.beerbattle.server;

import org.academiadecodigo.enuminatti.beerbattle.client.model.Boat;

import java.net.Socket;


/**
 * Created by codecadet on 07/11/17.
 */
public class Game {

    private Socket SocketOne;
    private Socket SocketTwo;

    private Boat[] boatsPlayerOne;
    private Boat[] BoatsPlayerTwo;

    public Game(Boat[] boatsPlayerOne, Boat[] boatsPlayerTwo) {
        this.SocketOne = new Socket();
        this.SocketTwo = new Socket();
        this.boatsPlayerOne = boatsPlayerOne;
        BoatsPlayerTwo = boatsPlayerTwo;
    }


    public void receiveSockets(Socket socket) {

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
        return BoatsPlayerTwo;
    }
}
