package org.academiadecodigo.enuminatti.beerbattle.client.model;

import org.academiadecodigo.enuminatti.beerbattle.client.controller.Controller;

import java.util.Set;

/**
 * Created by codecadet on 10/11/17.
 */
public class Grid {

    private Controller controller;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    private Set<Boat> boats;


    public Set<Boat> getBoat(){
        return boats;
    }

    public void createBoat(Position position, BoatType boatType){
        boats.add(new Boat(position,boatType));

    }



}
