package org.harjoitustyoD;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;



public class Board {

    // This variable requires merely initialization here.
    // Value set to 10 for testing purposes.
    private int boardSize;
    public int cordsX;
    public int cordsY;
    GridPane grid = new GridPane();

    public Board(){

    }

    /**
     * Set the size for the board. Taken from the text field
     * from which user input is read when starting the game.
     *
     * @param boardSize
     */
    protected void setBoardSize(int boardSize){
        /*if(boardSize > 4 && boardSize < 11) {
            this.boardSize = boardSize;
        }else{
           //Tähän alarm-pop-up-ikkuna?
           System.out.println("Invalid size for the board!");
        }*/
        this.boardSize = boardSize;
    }

    /**
     * Builds the board for the battleships game
     * using the user-set boardSize which must
     * a number from 5 to 10.
     */

    protected AnchorPane buildBoard(){

        for (int i = 0; i < boardSize; i++) {
            ColumnConstraints c = new ColumnConstraints(50);
            grid.getColumnConstraints().add(c);
            grid.setOnMouseMoved((MouseEvent t) -> {
                double xx = (int) t.getX()*2;
                double yy = ( t.getY()*2);
                int y = (int) yy/100;
                int x = (int) xx/100;
                y++;
                x++;
                System.out.println(x + " x " + y);
                cordsX = x;
                cordsY = y;
                System.out.println("CordsX on: "+cordsX);
                System.out.println("CordsY on: "+cordsY);
            });
        }
        for (int i = 0; i < boardSize; i++) {
            RowConstraints c = new RowConstraints(50);
            grid.getRowConstraints().add(c);
            grid.setOnMouseMoved((MouseEvent t) -> {
                double xx = (int) t.getX()*2;
                double yy = ( t.getY()*2);
                int y = (int) yy/100;
                int x = (int) xx/100;
                y++;
                x++;
                System.out.println(x + " x " + y);
                cordsX = x;
                cordsY = y;
                System.out.println("CordsX on: "+cordsX);
                System.out.println("CordsY on: "+cordsY);
            });
        }
        // Make the lines visible for the board
        grid.setGridLinesVisible(true);

        AnchorPane aPane = new AnchorPane();
        aPane.getChildren().add(grid);
        AnchorPane.setLeftAnchor(grid, 10d);

        return aPane;

    }

    public int getCordsX(){
        return cordsX;
    }

    public int getCordsY(){
        return cordsY;
    }

    public GridPane getGrid(){
        return grid;
    }

    public int getBoardSize(){
        return boardSize;
    }

}

