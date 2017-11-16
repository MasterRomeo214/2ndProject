package org.academiadecodigo.enuminatti.beerbattle.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.academiadecodigo.enuminatti.beerbattle.client.model.Beer;
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

        for (Node n : mainGrid.getChildren()){
            Button b = (Button) n;
            b.setDisable(true);

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

    public void drawHitted(int x, int y) {
        Pane pane = new Pane();
        secondGrid.getChildren().get(1).setStyle("-fx-background-color: red");
        secondGrid.add(pane, x, y);
        pane.setStyle("-fx-background-image:url resources/beerCap");

        }


    public void drawHit(int x, int y) {
        String mockId = "b"+x+y;
        for (Node n: mainGrid.getChildren()) {
            if (n.getId() != null && n.getId().equals(mockId)) {
                n.setStyle("-fx-background-color: indianred");
            }
        }
    }

    public void drawMiss(int x, int y) {
        String mockId = "b"+x+y;
        for (Node n: mainGrid.getChildren()) {
            if (n.getId() != null && n.getId().equals(mockId)) {
                n.setStyle("-fx-background-color: aqua");
            }
        }
    }

    public void drawMissed(int x, int y) {
        Pane pane = new Pane();
        secondGrid.add(pane, x, y);
        pane.setStyle("-fx-background-image: resources/waterCap");
        String mockId = "p" + x + y;
        for (Node n : secondGrid.getChildren()) {
            if (n.getId() != null && n.getId().equals(mockId)) {
                n.setStyle("-fx-background-color: blue");
            }

        }
    }

    @FXML
    private TextArea textArea;

    @FXML
    private TextField textField;

    @FXML
    void teste(KeyEvent event) {

    }

    @FXML
    void textFieldPressed(ActionEvent event) {

        String text = textField.getCharacters().toString();

        textArea.nextWord();
        System.out.println(text);

    }

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
