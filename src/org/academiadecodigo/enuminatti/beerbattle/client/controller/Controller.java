package org.academiadecodigo.enuminatti.beerbattle.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import org.academiadecodigo.enuminatti.beerbattle.client.model.BeerType;
import org.academiadecodigo.enuminatti.beerbattle.client.model.Grid;
import org.academiadecodigo.enuminatti.beerbattle.client.service.ComunicationService;

import java.io.IOException;

public class Controller {

    private int x;
    private int y;
    private ComunicationService comunicationService;
    private Grid primaryGrid;
    private boolean booleanoDoRomeu;

    public Controller() {
        booleanoDoRomeu=false;
        this.x = 0;
        this.y = 0;
        try {
            comunicationService = new ComunicationService(8080);
            primaryGrid = new Grid();
        } catch (IOException e) {
            e.printStackTrace();
        }
        comunicationService.setController(this);
        primaryGrid.setController(this);
    }

    @FXML
    void buttonPressed(ActionEvent event) {
        Button bClicked = (Button) event.getSource();
        if (bClicked.isDisabled()){
            bClicked.setDisable(false);
        }

        bClicked.setDisable(true);

        x = getPositionX(bClicked);
        y = getPositionY(bClicked);

        System.out.println(getPositionX(bClicked) + " " + getPositionY(bClicked));
        if (booleanoDoRomeu){
            primaryGrid.createBeer(BeerType.MINI,x,y);
        }
    }

    public void play() {

    }

    public void events(){

    }

    public Integer getPositionX(Button button6) {

        Integer x = GridPane.getColumnIndex(button6);
        return (x == null) ? 0 : x;

    }

    public Integer getPositionY(Button button6) {

        Integer y = GridPane.getRowIndex(button6);
        return (y == null) ? 0 : y;

    }

    public void gameLost(){

    }

    @FXML
    private Button startButton;

    public void releaseStartButton(){
         startButton.setDisable(false);
    }

    @FXML
    void startButtonPressed(MouseEvent event) {

        booleanoDoRomeu = false;
        comunicationService.sendReady();
        comunicationService.sendAttack(x,y);
        startButton.setDisable(true);

    }
}
