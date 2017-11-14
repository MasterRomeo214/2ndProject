package org.academiadecodigo.enuminatti.beerbattle.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.academiadecodigo.enuminatti.beerbattle.client.model.Position;
import org.academiadecodigo.enuminatti.beerbattle.client.model.Grid;
import org.academiadecodigo.enuminatti.beerbattle.client.service.ComunicationService;

import java.io.IOException;
import java.util.LinkedList;

public class Controller {

    LinkedList<Position> initialPosition;
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
        //  this.x = 0;
        //  this.y = 0;
        initialPosition = new LinkedList<>();
        booleanoDoRomeu = true;
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

    boolean test = false;

    @FXML
    void buttonPressed(ActionEvent event) {

        Button bClicked = (Button) event.getSource();

        x = getPositionX(bClicked);
        y = getPositionY(bClicked);

        System.out.println(getPositionX(bClicked) + " " + getPositionY(bClicked));
        if (booleanoDoRomeu) {
            if (test) {
                bClicked.setStyle("-fx-fill: cyan");
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
        if (x == null || y == null) {
            return;
        }


        comunicationService.sendAttack(x, y);
        secondGrid.add(new Label("X"), x, y);
        //get(x + y).setStyle("-fx-background-color: rebeccapurple");

        secondGrid.setVisible(true);
        startButton.setText("Attack");

        booleanoDoRomeu = false;
        comunicationService.sendReady();
        comunicationService.sendAttack(x, y);
        startButton.setDisable(true);

    }

    public void draw() {


        paneo.setStyle("-fx-background-color: red");
        panet.setStyle("-fx-background-color: red");
    }


}
