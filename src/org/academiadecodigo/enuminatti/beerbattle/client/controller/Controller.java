package org.academiadecodigo.enuminatti.beerbattle.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.academiadecodigo.enuminatti.beerbattle.client.model.Beer;
import org.academiadecodigo.enuminatti.beerbattle.client.model.Grid;
import org.academiadecodigo.enuminatti.beerbattle.client.service.ComunicationService;
import org.academiadecodigo.enuminatti.beerbattle.utils.Sound;

import java.io.IOException;

public class Controller {

    private Integer x;
    private Integer y;
    private ComunicationService comunicationService;
    private Grid primaryGrid;
    private boolean xico;

    private Sound ambientSound = new Sound("/resources/ambient.wav");
    private Sound introF = new Sound("/resources/introF.wav");

    public Controller() {
        this.xico = false;
        this.x = 0;
        this.y = 0;
        try {
            primaryGrid = new Grid();
            comunicationService = new ComunicationService(8080, primaryGrid);
            comunicationService.setController(this);

            ambientSound.play(true);
            ambientSound.loopIndef();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void buttonPressed(ActionEvent event) {

        Button bClicked = (Button) event.getSource();
        x = getPositionX(bClicked);
        y = getPositionY(bClicked);

        //reference to the clicked position
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

        if (startButton.getText().contains("Send")&&primaryGrid.getBeersLeft()==0){
            startButton.setDisable(false);
        }
    }

    public void checkoIfBeerRemain(){
        while (primaryGrid.getBeersLeft() > 0){
            startButton.setDisable(true);
        }
        startButton.setDisable(false);

    }

    @FXML
    void startButtonPressed(MouseEvent event) {


        if (startButton.getText().contains("Send")) {

            comunicationService.sendBeers();
            secondGrid.setVisible(true);
            drawBeers();
            introF.play(true);
            startButton.setText("Attack");
            cleanGrid();
            label.setText("jhfjhga");


            if (comunicationService.getPlayer() == 0) {
                startButton.setDisable(true);
                comunicationService.setPlayer(1);
                //return;
            }
        }

        if (startButton.getText().contains("Attack")) {
            startButton.setDisable(true);
            comunicationService.sendAttack(x, y);
        }
    }

    public void cleanGrid() {

        for (Node n : mainGrid.getChildren()) {
            Button b = (Button) n;
            b.setDefaultButton(false);

        }
    }

    // WORK IN PROGRESS!!!
    public void lockButton() {
        int count = 0;

        for (Node n : secondGrid.getChildren()) {
            Button b = (Button) n;
            b.setId("" + count);
            count++;

        }
    }

    public Integer getPositionX(Button button) {

        Integer x = GridPane.getColumnIndex(button);
        return (x == null) ? 0 : x;

    }

    public Integer getPositionY(Button button) {

        Integer y = GridPane.getRowIndex(button);
        return (y == null) ? 0 : y;
    }


    public void stopSound() {
        ambientSound.stop();
    }

    public void releaseStartButton() {
        startButton.setDisable(false);
    }

    private void drawBeers() {

        for (Beer b : primaryGrid.getBeersSet()) {

            int x = b.getX();
            int y = b.getY();

            String beerId = "p" + x + y;

            for (Node n : secondGrid.getChildren()) {
                if (n.getId() != null && n.getId().equals(beerId)) {
                    n.setStyle("-fx-background-color: yellow");

                }
            }
        }
    }


    public void drawHit(int x, int y) {
        String mockId = "b" + x + y;
        for (Node n : mainGrid.getChildren()) {
            if (n.getId() != null && n.getId().equals(mockId)) {
                n.setStyle("-fx-background-image: beer");
            }
        }
    }

    public void drawMiss(int x, int y) {
        String mockId = "b" + x + y;
        for (Node n : mainGrid.getChildren()) {
            if (n.getId() != null && n.getId().equals(mockId)) {
                n.setStyle("-fx-background-image: Water");
            }
        }
    }

    public void drawHitted(int x, int y) {
        String mockId = "p" + x + y;
        for (Node n : secondGrid.getChildren()) {
            if (n.getId() != null && n.getId().equals(mockId)) {
                n.setStyle("-fx-background-image: url('beerCap.jpg')");
            }

        }
    }

    public void drawMissed(int x, int y) {

        String mockId = "p" + x + y;
        for (Node n : secondGrid.getChildren()) {
            if (n.getId() != null && n.getId().equals(mockId)) {
                n.setStyle("-fx-background-image: waterCap");
            }

        }
    }

    @FXML
    private Label label;
    @FXML
    private GridPane secondGrid;
    @FXML
    private GridPane mainGrid;
    @FXML
    private Button startButton;
    @FXML
    private Pane p00;
    @FXML
    private Pane p10;
    @FXML
    private Pane p20;
    @FXML
    private Pane p30;
    @FXML
    private Pane p40;
    @FXML
    private Pane p50;
    @FXML
    private Pane p01;
    @FXML
    private Pane p11;
    @FXML
    private Pane p21;
    @FXML
    private Pane p31;
    @FXML
    private Pane p41;
    @FXML
    private Pane p51;
    @FXML
    private Pane p02;
    @FXML
    private Pane p12;
    @FXML
    private Pane p22;
    @FXML
    private Pane p32;
    @FXML
    private Pane p42;
    @FXML
    private Pane p52;
    @FXML
    private Pane p03;
    @FXML
    private Pane p13;
    @FXML
    private Pane p23;
    @FXML
    private Pane p33;
    @FXML
    private Pane p43;
    @FXML
    private Pane p53;
    @FXML
    private Pane p04;
    @FXML
    private Pane p14;
    @FXML
    private Pane p24;
    @FXML
    private Pane p34;
    @FXML
    private Pane p44;
    @FXML
    private Pane p54;
    @FXML
    private Pane p05;
    @FXML
    private Pane p15;
    @FXML
    private Pane p25;
    @FXML
    private Pane p35;
    @FXML
    private Pane p45;
    @FXML
    private Pane p55;
    @FXML
    private Button b00;
    @FXML
    private Button b10;
    @FXML
    private Button b20;
    @FXML
    private Button b30;
    @FXML
    private Button b40;
    @FXML
    private Button b50;
    @FXML
    private Button b01;
    @FXML
    private Button b11;
    @FXML
    private Button b21;
    @FXML
    private Button b31;
    @FXML
    private Button b41;
    @FXML
    private Button b51;
    @FXML
    private Button b02;
    @FXML
    private Button b12;
    @FXML
    private Button b22;
    @FXML
    private Button b32;
    @FXML
    private Button b42;
    @FXML
    private Button b52;
    @FXML
    private Button b03;
    @FXML
    private Button b13;
    @FXML
    private Button b23;
    @FXML
    private Button b33;
    @FXML
    private Button b43;
    @FXML
    private Button b53;
    @FXML
    private Button b04;
    @FXML
    private Button b14;
    @FXML
    private Button b24;
    @FXML
    private Button b34;
    @FXML
    private Button b44;
    @FXML
    private Button b54;
    @FXML
    private Button b05;
    @FXML
    private Button b15;
    @FXML
    private Button b25;
    @FXML
    private Button b35;
    @FXML
    private Button b45;
    @FXML
    private Button b55;
}
