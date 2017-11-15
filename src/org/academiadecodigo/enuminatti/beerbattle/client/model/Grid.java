package org.academiadecodigo.enuminatti.beerbattle.client.model;

import org.academiadecodigo.enuminatti.beerbattle.client.controller.Controller;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by codecadet on 10/11/17.
 */
public class Grid {

    private Controller controller;
    private Set<Beer> beers;
    private int beersLeft;

    public Grid() {
        beers = new HashSet<>();
        beersLeft = 14;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Set<Beer> getBeersSet() {
        return beers;
    }

    public int getBeersLeft() {
        return beersLeft;
    }

    public Beer getBeer(int x, int y) {
        Beer beer = null;
        for (Beer b : beers) {
            if (b.getX() == x && b.getY() == y) {
                beer = b;
            }
        }
        return beer;
    }

    public void createBeer( int x, int y) {
        if (beersLeft > 0) {
            beers.add(new Beer(x, y));
            beersLeft--;
            return;
        }
        System.out.println("No more beers left");
    }

    public void deleteBeer(int x, int y) {
        Beer beer;
        beer = getBeer(x, y);
        beers.remove(beer);
        beersLeft+=1;
    }
}
