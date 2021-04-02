package org.harjoitustyoD;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import java.io.InputStream;

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

    protected AnchorPane buildBoard(int size, InputStream name){
        //AWFUL IMPLEMENTATION WHICH WORKS ALL THE SAME, HENCE NOT CLEANED UP
        for (int i = 0; i < boardSize; i++) {
            ColumnConstraints c = new ColumnConstraints(size);
            grid.getColumnConstraints().add(c);
            grid.setOnMouseReleased((MouseEvent t) -> {
                double xx = (int) t.getX()*2;
                double yy = ( t.getY()*2);
                int y = (int)yy/(size*2);
                int x = (int)xx/(size*2);
                y++;
                x++;

                cordsX = x;
                cordsY = y;

                System.out.println("CordsX on: "+cordsX);
                System.out.println("CordsY on: "+cordsY);
            });

        }//for1

        for (int i = 0; i < boardSize; i++) {
            RowConstraints c = new RowConstraints(size);
            grid.getRowConstraints().add(c);
            grid.setOnMouseReleased((MouseEvent t) -> {
                double xx = (int) t.getX()*2;
                double yy = ( t.getY()*2);
                int y = (int) yy/(size*2);
                int x = (int) xx/(size*2);
                y++;
                x++;

                cordsX = x;
                cordsY = y;

            });


        }//for2

        //adding images to the grid
        Image ocean = new Image(name);
        ImageView imageView = new ImageView(ocean);
        if(getBoardSize() % 2 == 0){
            imageView.setFitHeight(getBoardSize()*size);
            imageView.setFitWidth(getBoardSize()*size);
            imageView.setLayoutY(imageView.getY());
            Pane p = new Pane();
            p.getChildren().add(imageView);
            grid.add(p, 0, 0);
        }
        else if(getBoardSize() % 2 == 1){
            imageView.setFitHeight(getBoardSize()*size);
            imageView.setFitWidth(getBoardSize()*size);
            grid.add(imageView,0, getBoardSize()/2);
        }

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
    public int getCordsY(){
        return cordsY;
    }

} // class Board


