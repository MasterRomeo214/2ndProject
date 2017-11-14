package org.academiadecodigo.enuminatti.beerbattle.client.model;

import org.academiadecodigo.enuminatti.beerbattle.client.controller.Controller;

import java.util.Set;

/**
 * Created by codecadet on 10/11/17.
 */
public class Grid  {

    private Controller controller;
    private Set<Beer> beers;
    private int beersLeft;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Set<Beer> getBeersSet() {
        return beers;
    }

    public Beer getBeer(int x, int y) {
        Beer beer = null;
        for (Beer b:beers) {
            if (b.getX()==x && b.getY()==y){
                beer = b;
            }

        }
        return beer;
    }

    public void createBeer(BeerType beerType, int x, int y) {
        if (beersLeft>0){
        beers.add(new Beer(beerType, x, y));
        beersLeft--;
        return;
        }
        System.out.println("No more beers left");

    }

    public void deleteBeer(int x, int y){
        Beer beer;
        beer = getBeer(x,y);
        beers.remove(beer);
    }


}
