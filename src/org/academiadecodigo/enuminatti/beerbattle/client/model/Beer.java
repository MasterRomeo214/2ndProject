package org.academiadecodigo.enuminatti.beerbattle.client.model;

/**
 * Created by codecadet on 10/11/17.
 */
public class Beer {

    private String name;
    private BeerType beerType;
    private int x;
    private int y;

    public Beer(BeerType beerType, int x, int y) {
        this.name = beerType.toString();
        this.beerType = beerType;
        this.x = x;
        this.y = y;

    }

    public String getName() {
        return name;
    }


    public BeerType getBeerType() {
        return beerType;
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

