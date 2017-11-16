package org.academiadecodigo.enuminatti.beerbattle.client.model;

/**
 * Created by codecadet on 10/11/17.
 */
public class Beer {

    private int x;
    private int y;

    public Beer(int x, int y) {
        this.x = x;
        this.y = y;

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}

