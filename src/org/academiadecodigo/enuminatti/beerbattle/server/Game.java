package org.academiadecodigo.enuminatti.beerbattle.server;

import java.net.Socket;


/**
 * Created by codecadet on 07/11/17.
 */
public class Game {

    private Socket SocketOne;
    private Socket SocketTwo;

    private Boats[] boatsPlayerOne;
    private Boats[] BoatsPlayerTwo;

    public Game(Boats[] boatsPlayerOne, Boats[] boatsPlayerTwo) {
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


    public Boats[] getBoatsPlayerOne() {
        return boatsPlayerOne;
    }

    public Boats[] getBoatsPlayerTwo() {
        return BoatsPlayerTwo;
    }
}
