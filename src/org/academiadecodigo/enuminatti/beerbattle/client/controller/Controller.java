package org.academiadecodigo.enuminatti.beerbattle.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import org.academiadecodigo.enuminatti.beerbattle.client.service.ComunicationService;

public class Controller {

    private ComunicationService comunicationService;

    public void setComunicationService(ComunicationService comunicationService) {
        this.comunicationService = comunicationService;
    }

    @FXML
    void buttonPressed(ActionEvent event) {

        Button bClicked = (Button) event.getSource();

        System.out.println(bClicked);
        System.out.println(getPositionX(bClicked));
        System.out.println(getPositionY(bClicked));

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
}
