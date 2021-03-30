package org.harjoitustyoD;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;


public class GameLogic {
    private Board b1 = new Board();
    private Board b2 = new Board();
    private Board b3 = new Board();
    private Board b4 = new Board();
    private AnchorPane ap1 = new AnchorPane();
    private AnchorPane ap2 = new AnchorPane();
    private AnchorPane ap3 = new AnchorPane();
    private AnchorPane ap4 = new AnchorPane();
    protected ArrayList<Ship> playerOneShipContainer = new ArrayList<>();
    protected ArrayList<Ship> playerTwoShipContainer = new ArrayList<>();
    protected ArrayList<Rectangle> pOneRectangles = new ArrayList<>();
    protected ArrayList<Rectangle> pTwoRectangles = new ArrayList<>();

    private FlowPane fp1 = new FlowPane();
    private FlowPane fp2 = new FlowPane();
    private Button switchb2;
    private int boardNumber;
    private Stage stage;
    private Scene scene1;
    private Scene scene2;
    private Scene scene3;
    private Scene scene4;
    public int cordsX;
    public int cordsY;
    private Rectangle selectedShip;

    public void createScenes(){
        scene1 = new Scene(ap1, 1600,900);
        scene2 = new Scene(ap2, 1600, 900);
        scene3 = new Scene(ap3, 1600, 900);
        scene4 = new Scene(ap4, 1600, 900);

        // R-näppäimen käyttäminen laivan kääntämiseen
        scene1.addEventFilter(KeyEvent.KEY_TYPED, e -> {
            if(e.getCharacter().equalsIgnoreCase("r")){
                try{
                    rotateShip(selectedShip);

                }catch(Exception exception){
                    exception.printStackTrace();
                }
            }
        });
        scene2.addEventFilter(KeyEvent.KEY_TYPED, e -> {
            if(e.getCharacter().equalsIgnoreCase("r")){
                try{
                    rotateShip(selectedShip);

                }catch(Exception exception){
                    exception.printStackTrace();
                }
            }
        });

        stage = Main.getStage();
        stage.setScene(scene1);
        stage.show();
    }


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
            switch(boardNumber){
                case 1:
                    stage.setScene(scene1);
                    stage.show();
                    break;
                case 2:
                    stage.setScene(scene2);
                    stage.show();
                    break;
                case 3:
                    stage.setScene(scene3);
                    stage.show();
                    break;
                case 4:
                    stage.setScene(scene4);
                    stage.show();
                    break;
            }
        }
        else {
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
        //varattujen paikkojen merkitsemiseen
        b1.setBoardSize(size);
        ap1 = b1.buildBoard();
        fp1.setHgap(30);
        fp1.setVgap(10);
        Button switchb2 = new Button("Switch to board 2");
        switchb2.setOnAction(e->{
            try {
                setNumber(2);
                switchScene("--");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        ap1.getChildren().addAll(fp1, switchb2);
        for(int i = 0; i < pOneRectangles.size(); i++){
            System.out.println("Rectanlesize = "+pOneRectangles.size());
            fp1.getChildren().add(pOneRectangles.get(i));
            initializeMouseEvent(pOneRectangles.get(i), b1, ap1, fp1, playerOneShipContainer);
        }
        AnchorPane.setRightAnchor(fp1, 10d);
        AnchorPane.setBottomAnchor(switchb2, 10d);

    }


    /**
     * Creates a new board utilizing the user input
     * from the TextField in the main menu. This
     * board belongs to the player 2
     *
     * @param size
     */

    public void createBoard2(int size) {
        //varattujen paikkojen merkitsemiseen
        b2.setBoardSize(size);
        ap2 = b2.buildBoard();
        fp2.setHgap(30);
        fp2.setVgap(10);
        Button switchb3 = new Button("Ready");
        switchb3.setOnAction(e->{
            try {
                setNumber(3);
                switchScene("--");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        ap2.getChildren().addAll(fp2,switchb3);
        for(int i = 0; i < pTwoRectangles.size(); i++){
            System.out.println("Rectanlesize = "+pTwoRectangles.size());
            fp2.getChildren().add(pTwoRectangles.get(i));
            initializeMouseEvent(pTwoRectangles.get(i), b2, ap2, fp2, playerTwoShipContainer);
        }
        AnchorPane.setRightAnchor(fp2, 10d);
        AnchorPane.setBottomAnchor(switchb3, 10d);
    }


    public void createGuessBoard1(int size){
        b3.setBoardSize(size);
        ap3 = b3.buildBoard();
        Button switchb4 = new Button("Ready");
        switchb4.setOnAction(e->{
            try {
                setNumber(4);
                switchScene("--");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        ap3.getChildren().add(switchb4);
        b3.getGrid().setOnMouseClicked(e->{
            shoot(b3.getCordsX(), b3.getCordsY(), playerOneShipContainer);
        });
        AnchorPane.setBottomAnchor(switchb4, 10d);
    }
    public void createGuessBoard2(int size){
        b4.setBoardSize(size);
        ap4 = b4.buildBoard();
        b4.getGrid().setOnMouseClicked(e->{
            shoot(b4.getCordsX(), b4.getCordsY(), playerTwoShipContainer);
        });
    }


    /**
     * Luodaan molempien pelaajien listoihin oikea määrä
     * laivoja, jotka haetaan tekstikentistä peliä aloitettaessa
     *
     * @param lta = lentotukialukset
     * @param tl = taistelulaivat
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
                pOneRectangles.get(indeksi).setFill(Color.GREEN);

                playerTwoShipContainer.add(new Lentotukialus(i));
                pTwoRectangles.add(new Rectangle());
                pTwoRectangles.get(indeksi).setHeight(30);
                pTwoRectangles.get(indeksi).setWidth(250);
                pOneRectangles.get(indeksi).setFill(Color.GREEN);
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

        selectedShip = pOneRectangles.get(0);
    }//createShips()


    private void initializeMouseEvent(Rectangle b, Board board, AnchorPane ap, FlowPane fp,ArrayList<Ship> container){

        b.setOnMousePressed(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)){
                fp.getChildren().remove(b);
                board.grid.getChildren().remove(b);
                ap.getChildren().add(b);
            }
            mousePressed(event, board, b, fp);
        });
        b.setOnMouseDragged(event -> dragged(event, b));
        b.setOnMouseReleased(event -> released(event, board, b, ap, container, fp));

        //hb.getChildren().add(b);

    }//initializeMouseEvent()


    private void mousePressed(MouseEvent event, Board board, Rectangle b, FlowPane fp){
        if (event.getButton().equals(MouseButton.PRIMARY)){
            // settaa viimeisimmän laivan valituksi laivaksi, jotta rotate toimii r:stä
            selectedShip = b;
            return;
        }
        else if(event.getButton().equals(MouseButton.SECONDARY)){
            //EI TARVITA TÄTÄ OMINAISUUTTA!
            /*if(board.grid.getChildren().contains(b)){
                board.grid.getChildren().remove(b);
                fp.getChildren().add(b);
                return;
            }*/
        }

    }//mousePressed()


    private void dragged(MouseEvent event, Rectangle b){
        cordsX = (int) (b.getLayoutX() + event.getX());
        cordsY = (int) (b.getLayoutY() + event.getX());
        b.setLayoutX(event.getSceneX());
        b.setLayoutY(event.getSceneY());
        // settaa viimeisimmän laivan valituksi laivaksi, jotta rotate toimii r:stä
        selectedShip = b;
    }//dragged()


    private void released(MouseEvent event, Board board, Rectangle b, AnchorPane ap1, ArrayList<Ship> container, FlowPane fp){
        if (event.getButton().equals(MouseButton.PRIMARY)){
            int gridx = (int)b.getLayoutX()/ 50;
            int gridy = (int)b.getLayoutY()/ 50;
            cordsX = gridx;
            cordsY = gridy;
            // settaa viimeisimmän laivan valituksi laivaksi, jotta rotate toimii r:stä
            selectedShip = b;

            // kun päästetään irti mouse1:stä, setataan coordit jne
            int index = getShipIndex(b);
            if (boardNumber == 1) {
                playerOneShipContainer.get(index).setStartX(cordsX);
                playerOneShipContainer.get(index).setStartY(cordsY);
                playerOneShipContainer.get(index).setEndX(cordsX + playerOneShipContainer.get(index).getSize());
                playerOneShipContainer.get(index).setEndY(cordsY + playerOneShipContainer.get(index).getSize());
                System.out.println(playerOneShipContainer.get(index).getStartX() + " x " + playerOneShipContainer.get(index).getStartY());
            } else if (boardNumber == 2) {
                playerTwoShipContainer.get(index).setStartX(cordsX);
                playerTwoShipContainer.get(index).setStartY(cordsY);
                playerTwoShipContainer.get(index).setEndX(cordsX + playerTwoShipContainer.get(index).getSize());
                playerTwoShipContainer.get(index).setEndY(cordsY + playerTwoShipContainer.get(index).getSize());
            }

            // Tarkistetaan, onko asettaminen validia =)
            checkValidPlacement(board, b, container, fp, ap1);

            if(isSpotTaken(cordsX,cordsY,container, b)){
                //palautetaan takaisin alkuperäiseen paikkaan oikealle ja käännetään takaisin, jos käännetty
                ap1.getChildren().remove(b);
                board.getGrid().getChildren().remove(b);
                fp.getChildren().add(b);
                if(container.get(getShipIndex(b)).getIsHorizontal() == false){
                    rotateShip(b);
                }
                container.get(getShipIndex(b)).setStartX(-1);
                container.get(getShipIndex(b)).setStartY(-1);

            }else {
                // VARSINAINEN LAIVAN ASETUS RUUDUKKOON
                ap1.getChildren().remove(b);
                board.getGrid().add(b, cordsX, cordsY);
            }
        }
    }//released()


    /**
     * Checks whether the spot is occupied by another ship
     * when trying to place a ship to specific coordinates
     *
     * @param x is the placíng coordinate X
     * @param y is the placing coordinate Y
     * @param container is the ArrayList of Ships for the player in question
     * @param b is the rectangle referring to a certain ship in question
     * @return
     */

    protected boolean isSpotTaken(int x, int y, ArrayList<Ship> container, Rectangle b) {
        for(int i = 0; i < container.size(); i++){

            // tarkistettavana oleva
            Ship s = container.get(i);

            // rectangleen liitetty
            Ship ship = container.get(getShipIndex(b));

            if(getShipIndex(b) != i && s.getStartX() != -1) {
                for(int j = 0; j < ship.getSize(); j++) {
                    if (s.getIsHorizontal() == true && ship.getIsHorizontal() == true) {
                        if (x + j >= s.getStartX() && x + j <= s.getStartX() + s.getSize() - 1 && s.getStartY() == y) {
                            System.out.println("et voi bro");
                            return true;
                        }
                    }else if(s.getIsHorizontal() == false && ship.getIsHorizontal() == true){
                        if(x <= s.getStartX() && ship.getEndX() > s.getStartX() && y >= s.getStartY() && y < s.getEndY()){
                            System.out.println("et voi bro");
                            return true;
                        }
                    }else if(s.getIsHorizontal() == true && ship.getIsHorizontal() == false){
                        if(y <= s.getStartY() && ship.getEndY() > s.getStartY() && x >= s.getStartX() && x < s.getEndX()){
                            System.out.println("et voi bro");
                            return true;
                        }

                    }else if(s.getIsHorizontal() == false && ship.getIsHorizontal() == false){
                        if (y+j >= s.getStartY() && y+j <= s.getStartY() + s.getSize() - 1 && s.getStartX() == x) {
                            System.out.println("et voi bro");
                            return true;
                        }
                    }
                }
            }
        }
        System.out.println("laita siihen");
        return false;
    }//isSpotTaken()


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


    protected Button getSwitchb2(){
        return switchb2;
    }


    protected void setNumber(int number){
        this.boardNumber = number;
    }


    protected void rotateShip(Rectangle rectangle){

        Ship ship = playerOneShipContainer.get(getShipIndex(rectangle));
        Ship shap = playerTwoShipContainer.get(getShipIndex(rectangle));

        Rotate r = new Rotate();
        // 25 = gridin koko / 2
        r.setPivotX(25);
        // 15 = laivan leveys / 2
        r.setPivotY(15);

        if(boardNumber == 1 || boardNumber == 3) {
            if (ship.getIsHorizontal() && ship.getEndX() <= b1.getBoardSize() && ship.getStartY() <= b1.getBoardSize()-ship.getSize()) {
                r.setAngle(90);
                rectangle.getTransforms().addAll(r);
                ship.setIsHorizontal(false);
                ship.setEndX(ship.getStartX());
                ship.setEndY(ship.getStartY()+ship.getSize());
            } else if(!ship.getIsHorizontal() && ship.getEndY() <= b1.getBoardSize() && ship.getStartX() <= b1.getBoardSize()-ship.getSize()){
                r.setAngle(-90);
                rectangle.getTransforms().addAll(r);
                ship.setIsHorizontal(true);
                ship.setEndY(ship.getStartY());
                ship.setEndX(ship.getStartX()+ship.getSize());
            }
        }else if(boardNumber == 2 || boardNumber == 4){
            if (shap.getIsHorizontal() && shap.getEndX() <= b2.getBoardSize() && shap.getStartY() <= b2.getBoardSize()-shap.getSize()) {
                r.setAngle(90);
                rectangle.getTransforms().addAll(r);
                shap.setIsHorizontal(false);
                shap.setEndX(shap.getStartX());
                shap.setEndY(shap.getStartY()+shap.getSize());
            } else if(!shap.getIsHorizontal() && shap.getEndY() <= b2.getBoardSize() && shap.getStartX() <= b2.getBoardSize()-shap.getSize()) {
                r.setAngle(-90);
                rectangle.getTransforms().addAll(r);
                shap.setIsHorizontal(true);
                shap.setEndY(shap.getStartY());
                shap.setEndX(shap.getStartX()+shap.getSize());
            }
        }

    }//rotateShip()


    public boolean rotateIsValid(Rectangle b){

        return false;
    }



    protected int getShipIndex(Rectangle rectangle){
        int index;
        if(boardNumber == 1 || boardNumber == 3){
            index = pOneRectangles.indexOf(rectangle);
            return index;
        }
        if(boardNumber == 2 || boardNumber == 4){
            index = pTwoRectangles.indexOf(rectangle);
            return index;
        }
        return 0;
    }


    protected boolean shoot(int x, int y, ArrayList<Ship> container){
        for(int i = 0; i < container.size(); i++){
            if(container.get(i).getIsHorizontal() == true) {
                for (int j = 0; j < container.get(i).getSize(); j++) {
                    if ((container.get(i).getStartX() + j == x) && (container.get(i).getStartY() == y)) {
                        System.out.println("osuit vittu");
                        return true;
                    }
                }
            }
            else{
                for(int j = 0; j < container.get(i).getSize(); j++){
                    if((container.get(i).getStartY() + j == y) && (container.get(i).getStartX() == x)){
                        System.out.println("osuit käännettyyn vittu");
                        return true;
                    }
                }
            }
        }
        System.out.println("et osunu broh");
        return false;
    }


    /**
     * Method to check whether the place that the user is trying
     * to set the ship to is valid or not
     *
     * @param board
     * @param b
     * @param container
     */
    protected void checkValidPlacement(Board board, Rectangle b, ArrayList<Ship> container, FlowPane fp, AnchorPane ap){

        //Jos x tai y alle 0, sijoitetaan oikein
        if(cordsX < 0){
            cordsX = 0;
        }
        if(cordsY < 0){
            cordsY = 0;
        }

        if(container.get(getShipIndex(b)).getIsHorizontal()) {
            // tarkistetaan horisontaalisesti oikeanlainen placeaminen, JOS LAIVA ON HORISONTAALISESTI
            if (cordsX > board.getBoardSize() - container.get(getShipIndex(b)).getSize()) {
                //Ei voi placettaa ghost nodejen ulkopuolelle, setataan oikein ruudukon sisälle
                if (cordsX > board.getBoardSize() - 1) {
                    System.out.println("too far X: " + cordsX);
                    cordsX = board.getBoardSize() - (container.get(getShipIndex(b)).getSize());
                } else {
                    cordsX = board.getBoardSize() - (container.get(getShipIndex(b)).getSize());
                }

            }
            //Ei voi placettaa ghost nodejen ulkopuolelle, setataan oikein ruudukon sisälle
            if (cordsY > board.getBoardSize() - 1) {
                System.out.println("too far Y: " + cordsY);
                cordsY = board.getBoardSize() - 1;
            }
        }else {
            // Tarkistetaan vertikaalisesti oikeanlainen placeaminen, JOS LAIVA ON KÄÄNNETTY
            if (cordsY > board.getBoardSize() - container.get(getShipIndex(b)).getSize()) {
                //Ei voi placettaa ghost nodejen ulkopuolelle, setataan oikein ruudukon sisälle
                if (cordsY > board.getBoardSize() - 1) {
                    System.out.println("too far Y: " + cordsY);
                    cordsY = board.getBoardSize() - (container.get(getShipIndex(b)).getSize());
                } else {
                    cordsY = board.getBoardSize() - (container.get(getShipIndex(b)).getSize());
                }
            }

            if (cordsX > board.getBoardSize() - 1) {
                System.out.println("too far X: " + cordsX);
                cordsX = board.getBoardSize() - 1;
            }

        }

    }//checkValidPlacement()

}//class