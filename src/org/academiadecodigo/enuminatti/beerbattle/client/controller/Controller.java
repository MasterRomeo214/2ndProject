package org.academiadecodigo.enuminatti.beerbattle.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.academiadecodigo.enuminatti.beerbattle.client.model.Beer;
import org.academiadecodigo.enuminatti.beerbattle.client.model.Grid;
import org.academiadecodigo.enuminatti.beerbattle.client.service.ComunicationService;

import java.io.IOException;

public class Controller {

    boolean test = false;
    private Integer x;
    private Integer y;
    private ComunicationService comunicationService;
    private Grid primaryGrid;
    private boolean booleanoDoRomeu;


    @FXML
    private GridPane secondGrid;

    @FXML
    private Button startButton;

    @FXML
    private Pane paneo;

    @FXML
    private Pane panet;


    public Controller() {

        booleanoDoRomeu = true;
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

        if (startButton.getText().contains("Send")) {
            if (test) {
                bClicked.setStyle("-fx-fill: cyan");
                primaryGrid.createBeer(x, y);
                test = false;
                return;
            }
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

    @FXML
    void startButtonPressed(MouseEvent event) {
        //depois de cada ataque ser lançado as posiçoes tem que voltar a ser null
        if (startButton.getText().contains("Send")) {

            //comunicationService.sendBeers();
            //return;
        }
        if (startButton.getText().contains("Attack")) {
            startButton.setDisable(true);
        }

        startButton.setText("Attack");
        comunicationService.sendReady();
        comunicationService.sendAttack(x, y);
    }

    public void draw() {


        paneo.setStyle("-fx-background-color: red");
        panet.setStyle("-fx-background-color: red");
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
