package org.harjoitustyoD;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
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
        this.boardSize = boardSize;
    }

    /**
     * Builds the board for the battleships game
     * using the user-set boardSize which must
     * a number from 5 to 10.
     */

    protected AnchorPane buildBoard(){
        int size = 50;
        for (int i = 0; i < boardSize; i++) {
            ColumnConstraints c = new ColumnConstraints(size);
            grid.getColumnConstraints().add(c);
            grid.setOnMouseMoved((MouseEvent t) -> {
                double xx = (int) t.getX()*2;
                double yy = ( t.getY()*2);
                int y = (int)yy/(size*2);
                int x = (int)xx/(size*2);
                y++;
                x++;
                //System.out.println(x + " x " + y);
                // TARVITAAN MAHDOLLISESTI MYÖHEMMIN
                cordsX = x;
                cordsY = y;
                /*
                System.out.println("CordsX on: "+cordsX);
                System.out.println("CordsY on: "+cordsY);*/
            });

        }//for1

        for (int i = 0; i < boardSize; i++) {
            RowConstraints c = new RowConstraints(size);
            grid.getRowConstraints().add(c);
            grid.setOnMouseMoved((MouseEvent t) -> {
                double xx = (int) t.getX()*2;
                double yy = ( t.getY()*2);
                int y = (int) yy/(size*2);
                int x = (int) xx/(size*2);
                y++;
                x++;
                //System.out.println(x + " x " + y);
                // TARVITAAN MAHDOLLISESTI MYÖHEMMIN
                cordsX = x;
                cordsY = y;
                /*
                System.out.println("CordsX on: "+cordsX);
                System.out.println("CordsY on: "+cordsY);*/
            });


        }//for2

        //adding images to the grid
        Image ocean = new Image(getClass().getResourceAsStream("ocean.png"));
        ImageView imageView = new ImageView(ocean);
        if(getBoardSize() % 2 == 0){
            //imageView.setRotate(imageView.getRotate() + 180);
            imageView.setFitHeight(getBoardSize()*50);
            imageView.setFitWidth(getBoardSize()*50);
            imageView.setLayoutY(imageView.getY());
            Pane p = new Pane();
            p.getChildren().add(imageView);
            grid.add(p, 0, 0);
        }
        else if(getBoardSize() % 2 == 1){
            imageView.setFitHeight(getBoardSize()*50);
            imageView.setFitWidth(getBoardSize()*50);
            grid.add(imageView,0, getBoardSize()/2);
        }
        //Image ocean = new Image(getClass().getResourceAsStream("ocean.png"), getBoardSize()*50, getBoardSize()*50, false, false);


        // Make the lines visible for the board
        grid.setGridLinesVisible(true);

        AnchorPane aPane = new AnchorPane();
        aPane.getChildren().add(grid);
        AnchorPane.setLeftAnchor(grid, 10d);


        return aPane;
    }// buildBoard()

    public GridPane getGrid(){
        return grid;
    }

    public int getBoardSize(){
        return boardSize;
    }
    public int getCordsX(){
        return cordsX;
    }
    public void setCordsX(int x){
        this.cordsX = x;
    }
    public void setCordsY(int y){
        this.cordsY = y;
    }
    public int getCordsY(){
        return cordsY;
    }

} // class Board


