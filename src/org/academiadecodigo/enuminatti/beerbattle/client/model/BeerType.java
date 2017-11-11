package org.academiadecodigo.enuminatti.beerbattle.client.model;

/**
 * Created by codecadet on 10/11/17.
 */
public enum BeerType {

    MINI(1),
    MEDIA(2),
    LITROZA(3),
    BARRIL(4);

    private int hp;

    BeerType(int hp) {
        this.hp = hp;
    }

    public void hit(){
        hp--;
    }

    //while hp is greater than 0 returns true, return false when hp is 0 or less
    public boolean isAlive(){
        return hp>0;
    }
}
