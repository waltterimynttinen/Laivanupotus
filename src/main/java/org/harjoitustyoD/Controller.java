package org.harjoitustyoD;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;
import javafx.event.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;

public class Controller {
    String playerOne;
    String playerTwo;
    String lentotukialusAmount;
    String taistelulaivaAmount;
    String risteilijaAmount;
    String sukellusveneAmount;
    String havittajaAmount;
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
    protected Button readyButton;

    @FXML
    protected Button playerSwitchButton;

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
        String s = "" + boardSizeLabel.getText().charAt(0);
        int laudanKoko = Integer.parseInt(s);

        if(isValid(laudanKoko)) {
            playerTwo = playerTwoTextField.getText();
            playerOne = playerOneTextField.getText();
            lentotukialusAmount = (lentotukialusAmountTextField.getText());
            System.out.println(playerOne);
            System.out.println(playerTwo);

            if (laudanKoko == 1) {
                gl.createBoard1(10);
                gl.createBoard2(10);
                System.out.println("Laudan koko: " + boardSizeLabel.getText() + ", BSL: " + laudanKoko);
                gl.switchScene("--");
            } else {
                gl.createBoard1(laudanKoko);
                gl.createBoard2(laudanKoko);
                System.out.println("Laudan koko: " + boardSizeLabel.getText() + ", BSL: " + laudanKoko);
                gl.switchScene("--");
            }
        }else{
            System.out.println("ei kelpaa >:(");
        }
    }

    @FXML
    protected void onReadyButtonAction(ActionEvent event) throws IOException {
        gl.switchScene("switchPlayerScene.fxml");
    }

    @FXML
    protected void onPlayerSwitchButtonAction(ActionEvent event) throws IOException {
        gl.switchScene("boardP2.fxml");
    }

    public void initialize() {

        boardSizeList.getItems().add("5x5");
        boardSizeList.getItems().add("6x6");
        boardSizeList.getItems().add("7x7");
        boardSizeList.getItems().add("8x8");
        boardSizeList.getItems().add("9x9");
        boardSizeList.getItems().add("10x10");

        boardSizeList.setValue("5x5");

        boardSizeLabel.textProperty().bind(boardSizeList.valueProperty());


        playerOneTextField.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER){
                playerOne = playerOneTextField.getText();
                System.out.println(playerOne);
            }
        });

        playerTwoTextField.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER){
                playerTwo = playerTwoTextField.getText();
                System.out.println(playerTwo);
            }
        });



    }
    protected boolean isValid(int koko){
        if(koko == 1) {
            return gl.areShipsAllowed(10, 1, 2, 3, 4, 5);
        }
        else{
            return gl.areShipsAllowed(koko, 1, 2, 3, 4, 5);
        }
    }
}
