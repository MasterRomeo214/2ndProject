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

    public void setPrimaryGrid(Grid primaryGrid) {
        this.primaryGrid = primaryGrid;
    }

    @FXML
    void buttonPressed(ActionEvent event) {

        Button bClicked = (Button) event.getSource();

        x = getPositionX(bClicked);
        y = getPositionY(bClicked);
        System.out.println(getPositionX(bClicked) + " " + getPositionY(bClicked));
        System.out.println(primaryGrid.getBeersLeft());

        if (!bClicked.isDefaultButton()&&primaryGrid.getBeersLeft()!=1) {
            bClicked.setDefaultButton(true);
            primaryGrid.createBeer(x, y);
            return;
        }

        if (bClicked.isDefaultButton()) {
            bClicked.setDefaultButton(false);
            primaryGrid.deleteBeer(x,y);
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
            //cleanGrid();
            return;
        }
        if (startButton.getText().contains("Attack")) {
            startButton.setDisable(true);

        }

        System.out.println("oi");
        comunicationService.sendAttack(x, y);
        comunicationService.sendReady();
    }

    public void cleanGrid() {
        while(true) {
            System.out.println("xico");
            for (Node n: mainGrid.getChildren()) {
                
            }
            //bClicked.setDefaultButton(false);
        }
    }

    public void play() {

    }

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
}
