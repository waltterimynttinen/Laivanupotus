package org.harjoitustyoD;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;
import javafx.event.*;

public class Controller {
    String playerOne;
    String playerTwo;
    GameLogic gl = new GameLogic();

    @FXML
    private TextField playerOneTextField;

    @FXML
    private TextField playerTwoTextField;

    @FXML
    private Label boardSizeLabel;

    @FXML
    private ChoiceBox<String> boardSizeList;

    @FXML
    protected Button startGameButton;

    @FXML
    private TextField lentotukialusAmountTextField;

    @FXML
    private TextField taistelulaivaAmountTextField;

    @FXML
    private TextField risteilijaAmountTextField;

    @FXML
    private TextField havittajaAmountTextField;

    @FXML
    private TextField sukellusveneAmountTextField;

    @FXML
    protected void onStartButtonAction(ActionEvent event) throws IOException {
        /*
        When this button action is triggered on the main menu, the game is
        started and all the needed values are fetched from their preassigned
        TextFields.
        */
        gl.playSound("buttonclick.wav");

        String s = "" + boardSizeLabel.getText().charAt(0);
        int laudanKoko = Integer.parseInt(s);
        playerTwo = playerTwoTextField.getText();
        playerOne = playerOneTextField.getText();
        gl.setPlayerOneName(playerOneTextField.getText());
        gl.setPlayerTwoName(playerTwoTextField.getText());

        //setataan epävalidit arvot, try-catchin sisällä ei voida alustaa muuttujia
        int lta = 100;
        int tl = 100;
        int ris = 100;
        int sv = 100;
        int hv = 100;

        // jos laitetaan esim. numeroiden sijaan kirjaimia
        try {
            lta = Integer.parseInt(lentotukialusAmountTextField.getText());
            tl = Integer.parseInt(taistelulaivaAmountTextField.getText());
            ris = Integer.parseInt(risteilijaAmountTextField.getText());
            sv = Integer.parseInt(sukellusveneAmountTextField.getText());
            hv = Integer.parseInt(havittajaAmountTextField.getText());
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Arvot väärin!");
            alert.setHeaderText("Tarkista, että asettamasi arvot ovat oikein!");
            gl.playSound("error.wav");
            alert.showAndWait();
            return;
        }

        if((areNamesValid(playerOne, playerTwo) && isBoardValid(laudanKoko))) {
            gl.playSound("buttonclick.wav");
            gl.createShips(lta, tl, ris, sv, hv);
            gl.setBoardNumber(1);
            if (laudanKoko == 1) {
                gl.createBoard1(10);
                gl.createSwitchPlayerScene();
                gl.createBoard2(10);
                gl.createScenes();
            } else {
                gl.createBoard1(laudanKoko);
                gl.createBoard2(laudanKoko);
                gl.createSwitchPlayerScene();
                gl.createScenes();
            }
            gl.playSong("start");

        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Arvot väärin!");
            alert.setHeaderText("Tarkista, että asettamasi arvot ovat oikein!");
            gl.playSound("error.wav");
            alert.showAndWait();

        }
    }

    /**
    A method for initializing values for the ChoiceBox and Labels
     */
    public void initialize() {
        for(int i = 5; i < 11; i++) {
            boardSizeList.getItems().add(i + "x" + i);
        }
        boardSizeList.setValue("5x5");

        //Don't touch this :)
        boardSizeLabel.textProperty().bind(boardSizeList.valueProperty());

    }


    /**
     * A method for checking whether the user-input
     * values are valid or not
     */

    protected boolean isBoardValid(int koko){
        int lta = Integer.parseInt(lentotukialusAmountTextField.getText());
        int tl = Integer.parseInt(taistelulaivaAmountTextField.getText());
        int ris = Integer.parseInt(risteilijaAmountTextField.getText());
        int sv = Integer.parseInt(sukellusveneAmountTextField.getText());
        int hv =  Integer.parseInt(havittajaAmountTextField.getText());

        int kok = lta+tl+ris+sv+hv;

        if(kok == 0){
            return false;
        }else{
            if(koko == 1) {
                return gl.areShipsAllowed(10, lta, tl, ris, sv, hv);
            }else{
                return gl.areShipsAllowed(koko, lta, tl, ris, sv, hv);
            }
        }
    }


    /**
     * Checks whether or not the names are
     * null that have been input by the user
     * @param p1
     * @param p2
     */

    protected boolean areNamesValid(String p1, String p2){
        if(p1.isEmpty() || p2.isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }
}
