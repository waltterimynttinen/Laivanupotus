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

        String s = "" + boardSizeLabel.getText().charAt(0);
        int laudanKoko = Integer.parseInt(s);
        playerTwo = playerTwoTextField.getText();
        playerOne = playerOneTextField.getText();
        // ::DD
        gl.setPlayerOneName(playerOneTextField.getText());
        gl.setPlayerTwoName(playerTwoTextField.getText());





        int lta = Integer.parseInt(lentotukialusAmountTextField.getText());
        int tl = Integer.parseInt(taistelulaivaAmountTextField.getText());
        int ris = Integer.parseInt(risteilijaAmountTextField.getText());
        int sv = Integer.parseInt(sukellusveneAmountTextField.getText());
        int hv =  Integer.parseInt(havittajaAmountTextField.getText());

        if((areNamesValid(playerOne, playerTwo) && isBoardValid(laudanKoko))) {
            gl.createShips(lta, tl, ris, sv, hv);
            gl.setBoardNumber(1);
            if (laudanKoko == 1) {
                gl.createBoard1(10);
                gl.createSwitchPLayerScene();
                gl.createBoard2(10);
                System.out.println("Laudan koko: " + boardSizeLabel.getText() + ", BSL: " + laudanKoko);
                gl.createScenes();
                System.out.println(gl.getPlayerOneShipContainer());
                System.out.println(gl.getPlayerTwoShipContainer());
            } else {
                gl.createBoard1(laudanKoko);
                gl.createBoard2(laudanKoko);
                gl.createSwitchPLayerScene();
                System.out.println("Laudan koko: " + boardSizeLabel.getText() + ", BSL: " + laudanKoko);
                gl.createScenes();
                System.out.println(gl.getPlayerOneShipContainer());
                System.out.println(gl.getPlayerTwoShipContainer());
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Arvot väärin!");
            alert.setHeaderText("Tarkista, että asettamasi arvot ovat oikein!");
            alert.showAndWait();
        }
    }


    public String palautaNimi1(){
        return playerOne;
    }
    public String palautaNimi2(){
        return playerTwo;
    }

    public void initialize() {

        for(int i = 5; i < 11; i++) {
            boardSizeList.getItems().add(i + "x" + i);
        }
        boardSizeList.setValue("5x5");

        //Don't touch this :)
        boardSizeLabel.textProperty().bind(boardSizeList.valueProperty());

    }
    protected boolean isBoardValid(int koko){
        int lta = Integer.parseInt(lentotukialusAmountTextField.getText());
        int tl = Integer.parseInt(taistelulaivaAmountTextField.getText());
        int ris = Integer.parseInt(risteilijaAmountTextField.getText());
        int sv = Integer.parseInt(sukellusveneAmountTextField.getText());
        int hv =  Integer.parseInt(havittajaAmountTextField.getText());


        System.out.println(lta);

        int kok = lta+tl+ris+sv+hv;

        if(kok == 0){
            System.out.println("Väärä määrä laivoja");
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
            System.out.println("seis");
            return false;
        }
        else{
            return true;
        }

    }
}
