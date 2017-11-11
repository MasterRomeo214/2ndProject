package org.academiadecodigo.enuminatti.beerbattle.client.model;

/**
 * Created by codecadet on 10/11/17.
 */
public class Boat {

    private String name;
    private Position boatPosition;
    private BoatType boatType;

    public Boat(Position boatPosition, BoatType boatType) {
        this.name = boatType.toString();
        this.boatPosition = boatPosition;
        this.boatType = boatType;
    }

    public String getName() {
        return name;
    }

    public Position getBoatPosition() {
        return boatPosition;
    }

    public BoatType getBoatType() {
        return boatType;
    }

    public void setBoatPosition(Position boatPosition) {
        this.boatPosition = boatPosition;
    }
}
