package org.academiadecodigo.enuminatti.beerbattle.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.academiadecodigo.enuminatti.beerbattle.client.service.ComunicationService;

import java.io.IOException;

public class Controller {

    private int x;
    private int y;
    private ComunicationService comunicationService;

    @FXML
    private GridPane secondGrid;

    @FXML
    private Button startButton;

    @FXML
    private Pane paneo;

    @FXML
    private Pane panet;


    public Controller() {
        this.x = 0;
        this.y = 0;
        try {
            comunicationService = new ComunicationService(8080);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void buttonPressed(ActionEvent event) {

        Button bClicked = (Button) event.getSource();

        x = getPositionX(bClicked);
        y = getPositionY(bClicked);







        System.out.println(getPositionX(bClicked) + " " + getPositionY(bClicked));

    }

    public void play() {

    }

    public void events(){

    }

    public Integer getPositionX(Button button) {

        Integer x = GridPane.getColumnIndex(button);
        return (x == null) ? 0 : x;

    }

    public Integer getPositionY(Button button) {

        Integer y = GridPane.getRowIndex(button);
        return (y == null) ? 0 : y;

    }

    public void gameLost(){

    }

    @FXML
    void startButtonPressed(MouseEvent event) {

        comunicationService.sendAttack(x,y);
        secondGrid.setVisible(true);
        startButton.setText("Attack");


    }
    public void draw(){


        paneo.setStyle("-fx-background-color: red");
        panet.setStyle("-fx-background-color: red");
    }



}
