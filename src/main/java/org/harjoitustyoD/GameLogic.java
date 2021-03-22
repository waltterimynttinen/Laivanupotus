package org.harjoitustyoD;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;


public class GameLogic {
    private Board b1 = new Board();
    private Board b2 = new Board();
    private Pane ap = new Pane();
    protected ArrayList<Ship> playerOneShipContainer = new ArrayList<>();
    protected ArrayList<Ship> playerTwoShipContainer = new ArrayList<>();
    protected ArrayList<Rectangle> pOneRectangles = new ArrayList<>();
    protected ArrayList<Rectangle> pTwoRectangles = new ArrayList<>();


    FlowPane hb = new FlowPane();
    public int cordsX;
    public int cordsY;
    private Rectangle b = new Rectangle();
    private Rectangle c = new Rectangle();


    /**
     * A method used for switching scenes
     * so that it doesn't have to be done manually
     *
     * @param fxmlFile is used for retrieving fxml file, if 'vanilla javafx' is used,
     *                 this fxml filename is replaced with a String containing --
     * @throws IOException
     */
    public void switchScene(String fxmlFile) throws IOException {
        if (fxmlFile.equals("--")) {
            Stage stage = Main.getStage();
            stage.setScene(new Scene(ap, 1600, 900));
            stage.show();
        } else {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            Scene scene = new Scene(root);
            Stage stage = Main.getStage();
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Creates a new board utilizing the user input
     * from the TextField in the main menu. This
     * board belongs to the player 1
     *
     * @param size
     */
    public void createBoard1(int size) {
        b1.setBoardSize(size);
        ap = b1.buildBoard();
        hb.setHgap(30);
        hb.setVgap(10);
        ap.getChildren().add(hb);
        for(int i = 0; i < pOneRectangles.size(); i++){
            System.out.println("Rectanlesize = "+pOneRectangles.size());
            hb.getChildren().add(pOneRectangles.get(i));
            initializeMouseEvent(pOneRectangles.get(i));
        }



        AnchorPane.setRightAnchor(hb, 10d);



        /*
        initializeMouseEvent(b);
        initializeMouseEvent(c);
        b.setHeight(35);
        b.setWidth(150);
        c.setHeight(35);
        c.setWidth(100);
         */

    }

    /**
     * Creates a new board utilizing the user input
     * from the TextField in the main menu. This
     * board belongs to the player 2
     *
     * @param size
     */

    public void createBoard2(int size) {
        b2.setBoardSize(size);
        /*ap2 = b2.buildBoard();
        hb.setHgap(30);
        hb.setVgap(10);
        ap.getChildren().add(hb);
        initializeMouseEvent(b);
        initializeMouseEvent(c);
        b.setHeight(35);
        b.setWidth(150);
        c.setHeight(35);
        c.setWidth(100);
        AnchorPane.setRightAnchor(hb, 10d);*/
    }

    /**
     * Luodaan molempien pelaajien listoihin oikea määrä
     * laivoja, jotka haetaan tekstikentistä peliä aloitettaessa
     *
     * @param lta
     * @param tl
     * @param ris
     * @param sv
     * @param hv
     */

    public void createShips(int lta, int tl, int ris, int sv, int hv) {

        //Empty the old one if it exist
        if(!(playerOneShipContainer.size() == 0) && !(playerTwoShipContainer.size() == 0)) {
            playerOneShipContainer.clear();
            playerTwoShipContainer.clear();
        }

        // Lentotukialusten luonti
        int indeksi = 0;
        if(lta != 0) {
            for (int i = 0; i < lta; i++) {
                playerOneShipContainer.add(new Lentotukialus(i));
                pOneRectangles.add(new Rectangle());
                pOneRectangles.get(indeksi).setHeight(30);
                pOneRectangles.get(indeksi).setWidth(250);

                playerTwoShipContainer.add(new Lentotukialus(i));
                pTwoRectangles.add(new Rectangle());
                pTwoRectangles.get(indeksi).setHeight(30);
                pTwoRectangles.get(indeksi).setWidth(250);
                indeksi++;
            }
        }
        //Taistelulaivojen luonti
        if(tl != 0) {
            for (int i = 0; i < tl; i++) {
                playerOneShipContainer.add(new Taistelulaiva(i));
                pOneRectangles.add(new Rectangle());
                pOneRectangles.get(indeksi).setHeight(30);
                pOneRectangles.get(indeksi).setWidth(200);

                playerTwoShipContainer.add(new Taistelulaiva(i));
                pTwoRectangles.add(new Rectangle());
                pTwoRectangles.get(indeksi).setHeight(30);
                pTwoRectangles.get(indeksi).setWidth(200);
                indeksi++;
            }
        }

        //Risteilijöiden luonti
        if(ris != 0) {
            for (int i = 0; i < ris; i++) {
                playerOneShipContainer.add(new Risteilija(i));
                pOneRectangles.add(new Rectangle());
                pOneRectangles.get(indeksi).setHeight(30);
                pOneRectangles.get(indeksi).setWidth(150);

                playerTwoShipContainer.add(new Risteilija(i));
                pTwoRectangles.add(new Rectangle());
                pTwoRectangles.get(indeksi).setHeight(30);
                pTwoRectangles.get(indeksi).setWidth(150);
                indeksi++;
            }
        }

        //Sukeltajapaattien =) luonti
        if(sv != 0) {
            for (int i = 0; i < sv; i++) {
                playerOneShipContainer.add(new Sukellusvene(i));
                pOneRectangles.add(new Rectangle());
                pOneRectangles.get(indeksi).setHeight(30);
                pOneRectangles.get(indeksi).setWidth(150);

                playerTwoShipContainer.add(new Sukellusvene(i));
                pTwoRectangles.add(new Rectangle());
                pTwoRectangles.get(indeksi).setHeight(30);
                pTwoRectangles.get(indeksi).setWidth(150);
                indeksi++;
            }
        }

        //Hävittäjien luonti
        if(hv != 0) {
            for (int i = 0; i < hv; i++) {
                playerOneShipContainer.add(new Havittaja(i));
                pOneRectangles.add(new Rectangle());
                pOneRectangles.get(indeksi).setHeight(30);
                pOneRectangles.get(indeksi).setWidth(100);

                playerTwoShipContainer.add(new Havittaja(i));
                pTwoRectangles.add(new Rectangle());
                pTwoRectangles.get(indeksi).setHeight(30);
                pTwoRectangles.get(indeksi).setWidth(100);
                indeksi++;
            }
        }
    }//createShips()

    private void initializeMouseEvent(Rectangle b){

        b.setOnMousePressed(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)){
                hb.getChildren().remove(b);
                b1.grid.getChildren().remove(b);
                ap.getChildren().add(b);
            }
            mousePressed(event, b);
        });
        b.setOnMouseDragged(event -> dragged(event, b));
        b.setOnMouseReleased(event -> released(event, b));

        //hb.getChildren().add(b);

    }//initializeMouseEvent()

    private void mousePressed(MouseEvent event, Rectangle b){
        if (event.getButton().equals(MouseButton.PRIMARY)){
            return;
        }
        else if(event.getButton().equals(MouseButton.SECONDARY)){
            b1.grid.getChildren().remove(b);
            hb.getChildren().add(b);
            return;
        }
        else if(event.getButton().equals(MouseButton.MIDDLE)){
            //todo
            System.out.println("rotate ship");
        }
    }//mousePressed()

    private void dragged(MouseEvent event, Rectangle b){
        cordsX = (int) (b.getLayoutX() + event.getX());
        cordsY = (int) (b.getLayoutY() + event.getX());
        b.setLayoutX(event.getSceneX());
        b.setLayoutY(event.getSceneY());
    }//dragged()

    private void released(MouseEvent event, Rectangle b){
        if (event.getButton().equals(MouseButton.PRIMARY)){
            int gridx = (int)b.getLayoutX()/ 50;
            int gridy = (int)b.getLayoutY()/ 50;
            cordsX = gridx;
            cordsY = gridy;
            ap.getChildren().remove(b);
            //if(cordsX = olemas, älä pleissaa)
            b1.grid.add(b, cordsX, cordsY);
            System.out.println("Placing coordinates: x = "+cordsX+" y = "+cordsY);
            return;
        }
        else if(event.getButton().equals(MouseButton.SECONDARY)){
            //do something
            return;
        }
        else if(event.getButton().equals(MouseButton.MIDDLE)){
            //do something
        }
    }//released()


    /**
     * Places the ship on the board
     */
    private void placeShip(Button button) {
        b1.grid.setOnMouseMoved(e -> {
            //System.out.println("asdasdsa"+b1.grid.getWidth());
        });
    }//placeShip



    public boolean areShipsAllowed(int area, int lta, int tl, int ris, int sv, int hv){
        int RA = area * area;
        int AA = 5*lta + 4*tl + 3*ris + 3*sv + 2*hv;
        if(RA >= 2*AA){
            System.out.println("kelpaa");
            return true;
        }
        else{
            System.out.println("ei kelpaa. vähennä alusten määrää =))=)))");
            return false;
        }
    }

    protected ArrayList<Ship> getPlayerOneShipContainer(){
        return playerOneShipContainer;
    }
    protected ArrayList<Ship> getPlayerTwoShipContainer(){
        return playerTwoShipContainer;
    }

}
