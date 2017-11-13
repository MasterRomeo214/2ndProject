package org.academiadecodigo.enuminatti.beerbattle.client.model;

import org.academiadecodigo.enuminatti.beerbattle.client.controller.Controller;

import java.util.Set;

/**
 * Created by codecadet on 10/11/17.
 */
public class Grid  {

    private Controller controller;


    public void setController(Controller controller) {
        this.controller = controller;
    }

    private Set<Beer> beers;


    public Set<Beer> getBoat() {
        return beers;
    }

    public void createBoat(BeerType beerType, int x, int y) {
        beers.add(new Beer(beerType, x, y));

    }


}
