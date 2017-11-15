package org.academiadecodigo.enuminatti.beerbattle.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.academiadecodigo.enuminatti.beerbattle.client.model.Beer;
import org.academiadecodigo.enuminatti.beerbattle.client.model.BeerType;
import org.academiadecodigo.enuminatti.beerbattle.client.model.Grid;
import org.academiadecodigo.enuminatti.beerbattle.client.service.ComunicationService;

import java.io.IOException;

public class Controller {

    private Integer x;
    private Integer y;
    private ComunicationService comunicationService;
    private Grid primaryGrid;

    @FXML
    private GridPane secondGrid;

    @FXML
    private GridPane mainGrid;

    @FXML
    private Button startButton;

    public Controller() {

        this.x = 0;
        this.y = 0;
        try {
            primaryGrid = new Grid();
            comunicationService = new ComunicationService(8080, primaryGrid);
            comunicationService.setController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void buttonPressed(ActionEvent event) {

        Button bClicked = (Button) event.getSource();

        System.out.println(bClicked.getStyle());
        x = getPositionX(bClicked);
        y = getPositionY(bClicked);
        System.out.println(getPositionX(bClicked) + " " + getPositionY(bClicked));

        if (!bClicked.isDefaultButton() && primaryGrid.getBeersLeft() != 0) {
            bClicked.setDefaultButton(true);
            primaryGrid.createBeer(x, y);
            return;
        }

        if (bClicked.isDefaultButton()) {
            bClicked.setDefaultButton(false);
            primaryGrid.deleteBeer(x, y);
        }
    }

    @FXML
    void startButtonPressed(MouseEvent event) {
        //depois de cada ataque ser lançado as posiçoes tem que voltar a ser null
        if (startButton.getText().contains("Send")) {

            comunicationService.sendBeers();
            secondGrid.setVisible(true);
            drawBeers();
            startButton.setText("Attack");
            cleanGrid();
            return;
        }
        if (startButton.getText().contains("Attack")) {


            startButton.setDisable(true);

        }

        comunicationService.sendAttack(x, y);
        comunicationService.sendReady();
        lockButton();
    }

    public void cleanGrid() {


        for (Node n : mainGrid.getChildren()){
            Button b = (Button) n;
            b.setDefaultButton(false);

        }

    }

    public void play() {

    }
    // WORK IN PROGRESS!!!
    public void lockButton() {

        for (Node n : mainGrid.getChildren()){
            Button b = (Button) n;
            b.setDisable(true);

        }
    }
    //
    public void events() {

    }

    public Integer getPositionX(Button button) {

        Integer x = GridPane.getColumnIndex(button);
        return (x == null) ? 0 : x;

    }

    public Integer getPositionY(Button button) {

        Integer y = GridPane.getRowIndex(button);
        return (y == null) ? 0 : y;
    }

    public void gameLost() {

    }

    public void releaseStartButton() {
        startButton.setDisable(false);
    }

    private void drawBeers() {

        for (Beer b : primaryGrid.getBeersSet()) {

            int x = b.getX();
            int y = b.getY();
            Pane pane = new Pane();
            secondGrid.add(pane, x, y);
            pane.setStyle("-fx-background-color: green");
        }
    }

    public void drawHitted(int x, int y) {
        Pane pane = new Pane();
        secondGrid.getChildren().get(1).setStyle("-fx-background-color: red");
        secondGrid.add(pane, x, y);
        pane.setStyle("-fx-background-color: crimson");

    }

    public void drawMissed(int x, int y) {
        Pane pane = new Pane();
        secondGrid.add(pane, x, y);
        pane.setStyle("-fx-background-color: blue");
    }
}
