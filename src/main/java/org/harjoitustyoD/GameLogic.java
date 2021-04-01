package org.harjoitustyoD;


import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.media.*;


public class GameLogic {
    private Board b1 = new Board();
    private Board b2 = new Board();
    private Board b3 = new Board();
    private Board b4 = new Board();
    private AnchorPane ap1 = new AnchorPane();
    private AnchorPane ap2 = new AnchorPane();
    private AnchorPane ap3 = new AnchorPane();
    private AnchorPane ap4 = new AnchorPane();
    private AnchorPane lauta1 = new AnchorPane();
    private AnchorPane lauta2 = new AnchorPane();
    private AnchorPane lauta3 = new AnchorPane();
    private AnchorPane lauta4 = new AnchorPane();
    protected ArrayList<Ship> playerOneShipContainer = new ArrayList<>();
    protected ArrayList<Ship> playerTwoShipContainer = new ArrayList<>();
    protected ArrayList<ImageView> pOneShipImages = new ArrayList<>();
    protected ArrayList<ImageView> pTwoShipImages = new ArrayList<>();
    private BorderPane bp = new BorderPane();
    private FlowPane fp1 = new FlowPane();
    private FlowPane fp2 = new FlowPane();
    private Button button1 = new Button();
    private Button button2 = new Button();
    private int boardNumber;
    private int playerNumber;
    private Stage stage;
    private Scene scene1;
    private Scene scene2;
    private Scene scene3;
    public int cordsX;
    public int cordsY;
    private ImageView selectedShip;
    private int counter = 1;
    protected AudioClip audioClip;

    protected String playerOneName;
    protected String playerTwoName;

    protected void setPlayerOneName(String s){
        playerOneName = s;
    }

    protected void setPlayerTwoName(String s){
        playerTwoName = s;
    }

    public void createScenes(){

        scene1 = new Scene(ap1, 1600,900);
        scene2 = new Scene(ap2, 1600, 900);
        scene3 = new Scene(ap3, 1600, 900);

        // R-näppäimen käyttäminen laivan kääntämiseen
        scene1.addEventFilter(KeyEvent.KEY_TYPED, e -> {
            if(e.getCharacter().equalsIgnoreCase("r") && counter == 1){
                try{
                    rotateShip(selectedShip);
                }catch(Exception exception){
                    exception.printStackTrace();
                }
            }
        });
        scene2.addEventFilter(KeyEvent.KEY_TYPED, e -> {
            if(e.getCharacter().equalsIgnoreCase("r") && counter == 2){
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
                case 1: //set p1 ships
                    stage.setScene(scene1);
                    stage.show();
                    break;
                case 2: //set p2 ships
                    stage.setScene(scene2);
                    stage.show();
                    break;
                case 3: //player1 screen
                    stage.setScene(scene3);
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
        Image ocean = new Image(getClass().getResourceAsStream("sand.png"));
        ImageView imageView = new ImageView(ocean);
        ap1.getChildren().add(imageView);
        b1.setBoardSize(size);
        b3.setBoardSize(size);
        lauta1 = b1.buildBoard(50);
        lauta3 = b3.buildBoard(30);
        fp1.setHgap(30);
        fp1.setVgap(10);
        for(int i = 0; i < pOneShipImages.size(); i++){
            System.out.println("Rectanlesize = "+ pOneShipImages.size());
            fp1.getChildren().add(pOneShipImages.get(i));
            initializeMouseEvent(pOneShipImages.get(i), b1, ap1, fp1, playerOneShipContainer);
        }
        System.out.println(fp1.getChildren().size());
        button1.setText("Switch to player 2");
        button1.setOnAction(e->{
            try {
                if(fp1.getChildren().size() == 0) {
                    lauta1.setDisable(true);
                    if (lauta1.isDisabled()) {
                        if (counter == 1) {
                            selectedShip = pTwoShipImages.get(0);
                            setBoardNumber(3);
                            switchScene("--");
                        } else {
                            startGuessing(3, playerNumber, b4, b1, playerOneShipContainer, button2);
                        }
                    } else {
                        setBoardNumber(3);
                        switchScene("--");
                    }
                    button1.setDisable(true);
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        ap1.getChildren().addAll(lauta1, lauta3, fp1, button1);
        AnchorPane.setTopAnchor(lauta1, 10d);
        AnchorPane.setBottomAnchor(lauta3, 50d);
        AnchorPane.setRightAnchor(fp1, 10d);
        AnchorPane.setBottomAnchor(button1, 10d);
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
        Image ocean = new Image(getClass().getResourceAsStream("sand.png"));
        ImageView imageView = new ImageView(ocean);
        ap2.getChildren().add(imageView);
        b2.setBoardSize(size);
        b4.setBoardSize(size);
        AnchorPane lauta2 = b2.buildBoard(50);
        AnchorPane lauta4 = b4.buildBoard(30);
        fp2.setHgap(30);
        fp2.setVgap(10);
        for(int i = 0; i < pTwoShipImages.size(); i++){
            System.out.println("Rectanlesize = "+ pTwoShipImages.size());
            fp2.getChildren().add(pTwoShipImages.get(i));
            initializeMouseEvent(pTwoShipImages.get(i), b2, ap2, fp2, playerTwoShipContainer);
        }
        button2.setText("Ready");
        button2.setOnAction(e->{
            try {
                if(fp2.getChildren().size() == 0) {
                    lauta2.setDisable(true);
                    startGuessing(3, playerNumber, b3, b2, playerTwoShipContainer, button1);
                    button2.setDisable(true);
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        ap2.getChildren().addAll(lauta2, lauta4, fp2, button2);
        AnchorPane.setTopAnchor(lauta2, 10d);
        AnchorPane.setBottomAnchor(lauta4, 50d);
        AnchorPane.setRightAnchor(fp2, 10d);
        AnchorPane.setBottomAnchor(button2, 10d);
    }

    public void createSwitchPLayerScene(){
        //switches from p1 create board to p2 create board
        HBox hb = new HBox();
        Label lb = new Label("Pelaaja 2: Siirry asettamaan laivasi laudalle");
        Button switchb5 = new Button("Letsgo");
        switchb5.setOnAction(e -> {
            try {
                if(counter == 1){
                    lb.setText("PELI ALKAKOON, pelaajan 1 vuoro");
                    setBoardNumber(2);
                    counter++;
                }
                else if(counter == 2){
                    lb.setText("Pelaajan 2 vuoro");
                    setBoardNumber(1);
                    counter++;
                }
                else if(counter > 2 && counter % 2 != 0){
                    lb.setText("Pelaajan 1 vuoro");
                    setBoardNumber(2);
                    setPlayerNumber(1);
                    counter++;
                }
                else if(counter > 2 && counter % 2 == 0){
                    lb.setText("Pelaajan 2 vuoro");
                    setBoardNumber(1);
                    setPlayerNumber(2);
                    counter++;
                }
                switchScene("--");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        hb.getChildren().addAll(switchb5, lb);
        hb.setSpacing(10);
        bp.setCenter(hb);
        ap3.getChildren().addAll(bp);
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

        //tätä voisi siistiä :)

        // Lentotukialusten luonti
        int indeksi = 0;
        if(lta != 0) {
            for (int i = 0; i < lta; i++) {
                playerOneShipContainer.add(new Lentotukialus(i));
                pOneShipImages.add(new ImageView(new Image(getClass().getResourceAsStream("lentotukialus.png"))));

                playerTwoShipContainer.add(new Lentotukialus(i));
                pTwoShipImages.add(new ImageView(new Image(getClass().getResourceAsStream("lentotukialus.png"))));

                indeksi++;
            }
        }
        //Taistelulaivojen luonti
        if(tl != 0) {
            for (int i = 0; i < tl; i++) {
                playerOneShipContainer.add(new Taistelulaiva(i));
                pOneShipImages.add(new ImageView(new Image(getClass().getResourceAsStream("taistelulaiva.png"))));

                playerTwoShipContainer.add(new Taistelulaiva(i));
                pTwoShipImages.add(new ImageView(new Image(getClass().getResourceAsStream("taistelulaiva.png"))));

                indeksi++;
            }
        }

        //Risteilijöiden luonti
        if(ris != 0) {
            for (int i = 0; i < ris; i++) {
                playerOneShipContainer.add(new Risteilija(i));
                pOneShipImages.add(new ImageView(new Image(getClass().getResourceAsStream("risteilija.png"))));


                playerTwoShipContainer.add(new Risteilija(i));
                pTwoShipImages.add(new ImageView(new Image(getClass().getResourceAsStream("risteilija.png"))));

                indeksi++;
            }
        }

        //Sukeltajapaattien =) luonti
        if(sv != 0) {
            for (int i = 0; i < sv; i++) {
                playerOneShipContainer.add(new Sukellusvene(i));
                pOneShipImages.add(new ImageView(new Image(getClass().getResourceAsStream("sukellusvene.png"))));

                playerTwoShipContainer.add(new Sukellusvene(i));
                pTwoShipImages.add(new ImageView(new Image(getClass().getResourceAsStream("sukellusvene.png"))));
                indeksi++;
            }
        }

        //Hävittäjien luonti
        if(hv != 0) {
            for (int i = 0; i < hv; i++) {
                playerOneShipContainer.add(new Havittaja(i));
                pOneShipImages.add(new ImageView(new Image(getClass().getResourceAsStream("havittaja.png"))));


                playerTwoShipContainer.add(new Havittaja(i));
                pTwoShipImages.add(new ImageView(new Image(getClass().getResourceAsStream("havittaja.png"))));

                indeksi++;
            }
        }

        selectedShip = pOneShipImages.get(0);
    }//createShips()


    private void initializeMouseEvent(ImageView b, Board board, AnchorPane ap, FlowPane fp,ArrayList<Ship> container){

        b.setOnMousePressed(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)){
                fp.getChildren().remove(b);
                System.out.println(fp.getChildren().size());
                board.grid.getChildren().remove(b);
                ap.getChildren().add(b);
            }
            mousePressed(event, board, b, fp);
        });
        b.setOnMouseDragged(event -> dragged(event, b));
        b.setOnMouseReleased(event -> released(event, board, b, ap, container, fp));

        //hb.getChildren().add(b);

    }//initializeMouseEvent()


    private void mousePressed(MouseEvent event, Board board, ImageView b, FlowPane fp){
        if (event.getButton().equals(MouseButton.PRIMARY)){
            // settaa viimeisimmän laivan valituksi laivaksi, jotta rotate toimii r:stä
            selectedShip = b;
            return;
        }
        else if(event.getButton().equals(MouseButton.SECONDARY)){
            if(board.grid.getChildren().contains(b)){
                board.grid.getChildren().remove(b);
                fp.getChildren().add(b);
                return;
            }
        }

    }//mousePressed()


    private void dragged(MouseEvent event, ImageView b){
        cordsX = (int) (b.getLayoutX() + event.getX());
        cordsY = (int) (b.getLayoutY() + event.getX());
        b.setLayoutX(event.getSceneX());
        b.setLayoutY(event.getSceneY());
        // settaa viimeisimmän laivan valituksi laivaksi, jotta rotate toimii r:stä
        selectedShip = b;
    }//dragged()


    private void released(MouseEvent event, Board board, ImageView b, AnchorPane ap1, ArrayList<Ship> container, FlowPane fp){
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
            checkShipValidPlacement(board, b, container);

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

    protected boolean isSpotTaken(int x, int y, ArrayList<Ship> container, ImageView b) {
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


    protected void setBoardNumber(int number){
        this.boardNumber = number;
    }
    protected int getBoardNumber(){
        return boardNumber;
    }
    protected void setPlayerNumber(int number){
        this.playerNumber = number;
    }
    protected int getPlayerNumber(){
        return playerNumber;
    }


    protected void rotateShip(ImageView rectangle){

        Ship ship = playerOneShipContainer.get(getShipIndex(rectangle));
        Ship shap = playerTwoShipContainer.get(getShipIndex(rectangle));

        Rotate r = new Rotate();
        // 25 = gridin koko / 2
        r.setPivotX(25);
        // 15 = laivan leveys / 2
        r.setPivotY(25);

        if(boardNumber == 1 || boardNumber == 3) {
            if (ship.getIsHorizontal() && ship.getEndX() <= b1.getBoardSize() && ship.getStartY() <= b1.getBoardSize()-ship.getSize()) {
                r.setAngle(90);
                rectangle.getTransforms().addAll(r);
                ship.setIsHorizontal(false);
                ship.setEndX(ship.getStartX());
                ship.setEndY(ship.getStartY()+ship.getSize());

                //jos rotate ei mahdollinen, cancellataan kääntäminen
                if(isSpotTaken(ship.getStartX(),ship.getStartY(),playerOneShipContainer,rectangle)){
                    r.setAngle(0);
                    rectangle.getTransforms().addAll(r);
                    ship.setIsHorizontal(true);
                    ship.setEndY(ship.getStartY());
                    ship.setEndX(ship.getStartX()+ship.getSize());
                    return;
                }

            } else if(!ship.getIsHorizontal() && ship.getEndY() <= b1.getBoardSize() && ship.getStartX() <= b1.getBoardSize()-ship.getSize()) {
                r.setAngle(-90);
                rectangle.getTransforms().addAll(r);
                ship.setIsHorizontal(true);
                ship.setEndY(ship.getStartY());
                ship.setEndX(ship.getStartX() + ship.getSize());

                //jos rotate ei mahdollinen, cancellataan kääntäminen
                if (isSpotTaken(ship.getStartX(), ship.getStartY(), playerOneShipContainer, rectangle)) {
                    r.setAngle(0);
                    rectangle.getTransforms().addAll(r);
                    ship.setIsHorizontal(false);
                    ship.setEndX(ship.getStartX());
                    ship.setEndY(ship.getStartY()+ship.getSize());
                    return;
                }
            }
        // TÄYTYY VIELÄ MUUTTAA
        }else if(boardNumber == 2 || boardNumber == 4){
            if (shap.getIsHorizontal() && shap.getEndX() <= b2.getBoardSize() && shap.getStartY() <= b2.getBoardSize()-shap.getSize()) {
                r.setAngle(90);
                rectangle.getTransforms().addAll(r);
                shap.setIsHorizontal(false);
                shap.setEndX(shap.getStartX());
                shap.setEndY(shap.getStartY()+shap.getSize());

                //jos rotate ei mahdollinen, cancellataan kääntäminen
                if (isSpotTaken(shap.getStartX(), shap.getStartY(), playerTwoShipContainer, rectangle)) {
                    r.setAngle(0);
                    rectangle.getTransforms().addAll(r);
                    shap.setIsHorizontal(true);
                    shap.setEndY(shap.getStartY());
                    shap.setEndX(shap.getStartX()+shap.getSize());
                    return;
                }

            } else if(!shap.getIsHorizontal() && shap.getEndY() <= b2.getBoardSize() && shap.getStartX() <= b2.getBoardSize()-shap.getSize()) {
                r.setAngle(-90);
                rectangle.getTransforms().addAll(r);
                shap.setIsHorizontal(true);
                shap.setEndY(shap.getStartY());
                shap.setEndX(shap.getStartX()+shap.getSize());

                if (isSpotTaken(shap.getStartX(), shap.getStartY(), playerTwoShipContainer, rectangle)) {
                    r.setAngle(0);
                    rectangle.getTransforms().addAll(r);
                    shap.setIsHorizontal(false);
                    shap.setEndX(shap.getStartX());
                    shap.setEndY(shap.getStartY()+shap.getSize());
                    return;
                }
            }
        } // uloin if-elseif

    }//rotateShip()


    protected int getShipIndex(ImageView rectangle){
        int index;
        if(boardNumber == 1 || boardNumber == 3){
            index = pOneShipImages.indexOf(rectangle);
            return index;
        }
        if(boardNumber == 2 || boardNumber == 4){
            index = pTwoShipImages.indexOf(rectangle);
            return index;
        }
        return 0;
    }


    protected boolean shoot(int x, int y, ArrayList<Ship> container){
        for(int i = 0; i < container.size(); i++){
            if(container.get(i).getIsHorizontal() == true) {
                for (int j = 0; j < container.get(i).getSize(); j++) {
                    if ((container.get(i).getStartX()+1 + j == x) && (container.get(i).getStartY()+1 == y)) {
                        container.get(i).hit();
                        return true;
                    }
                }
            }
            else{
                for(int j = 0; j < container.get(i).getSize(); j++){
                    if((container.get(i).getStartY()+1 + j == y) && (container.get(i).getStartX()+1 == x)){
                        container.get(i).hit();
                        return true;
                    }
                }
            }
        }
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
    protected void checkShipValidPlacement(Board board, ImageView b, ArrayList<Ship> container){

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
        System.out.println("lopulliset koordinatit: " + cordsX + " x " + cordsY);
        container.get(getShipIndex(b)).setStartX(cordsX);
        container.get(getShipIndex(b)).setStartY(cordsY);
    }//checkShipValidPlacement()

    /**
     * Method to check whether the place that the user is trying
     * to guess is valid or not
     *
     * @param board
     * @param container
     */
    protected boolean checkGuessValidPlacement(Board board, ArrayList<Ship> container){
        if(board.getCordsX() < 0){
            return false;
        }
        if(board.getCordsY() < 0){
            return false;
        }
        if(board.getCordsX() > board.getBoardSize()){
            return false;
        }
        if(board.getCordsY() > board.getBoardSize()){
            return false;
        }
        return true;
    }//checkGuessValidPlacement()


    protected void startGuessing(int boardNumber, int playerNumber, Board board, Board board2, ArrayList<Ship> container, Button button) throws IOException {
        board.getGrid().setDisable(false);
        Image image1 = new Image(getClass().getResourceAsStream("vihrearasti.png"));
        Image image2 = new Image(getClass().getResourceAsStream("punainenrasti.png"));
        ImageView imgGreen1 = new ImageView(image1);
        ImageView imgRed2 = new ImageView(image2);
        ImageView imgGreen3 = new ImageView(image1);

        int fezze = 5;
        imgGreen1.setFitHeight(30);
        imgRed2.setFitWidth(30);
        imgGreen1.setFitWidth(30);
        imgRed2.setFitHeight(25+fezze);

        setBoardNumber(boardNumber);
        switchScene("--");
        board.getGrid().setOnMouseClicked(f->{
            if(getNodeFromBoard(board, board.getCordsX()-1, board.getCordsY()-1) != null && getNodeFromBoard(board, board.getCordsX()-1, board.getCordsY()-1).isDisabled()){
                System.out.println("et voi arvata tätä ruutua");
                return;
            }
            if((shoot(board.getCordsX(), board.getCordsY(), container))){
                if(checkGuessValidPlacement(board, container)) {

                    board.getGrid().add(imgGreen1, board.getCordsX() - 1, board.getCordsY() - 1);
                    board2.getGrid().add(imgGreen3, board.getCordsX() - 1, board.getCordsY() - 1);
                    getNodeFromBoard(board, board.getCordsX() - 1, board.getCordsY() - 1).setDisable(true);
                    board.getGrid().setDisable(true);
                    button.setDisable(false);
                    playSound("valmiscrash.wav");
                    removeDeadShip(container, playerNumber);

                }
            }
            else if(!(shoot(board.getCordsX(), board.getCordsY(), container))){
                if(checkGuessValidPlacement(board, container)) {
                    System.out.println("Ammuit ohi!");
                    board.getGrid().add(imgRed2, board.getCordsX() - 1, board.getCordsY() - 1);
                    getNodeFromBoard(board, board.getCordsX() - 1, board.getCordsY() - 1).setDisable(true);
                    board.getGrid().setDisable(true);
                    button.setDisable(false);
                    playSound("valmissplash.wav");
                }
            }
        });
    }//startGuessing


    /**
     * A method for playing soundsclips
     * for example when you hit a ship
     * @param name is the name of the
     *             requested audioclip
     */
    protected void playSound(String name){
        AudioClip audioClip = new AudioClip(getClass().getResource(name).toString());
        audioClip.play();
    }


    // metodin avulla saadaan gridistä valittua haluttu solu
    protected Node getNodeFromBoard(Board board, int col, int row) {
        ObservableList<Node> children = board.getGrid().getChildren();
        for (Node node : children) {
            Integer columnIndex = GridPane.getColumnIndex(node);
            Integer rowIndex = GridPane.getRowIndex(node);

            if (columnIndex == null)
                columnIndex = 0;
            if (rowIndex == null)
                rowIndex = 0;

            if (columnIndex == col && rowIndex == row) {
                return node;
            }
        }
        return null;
    }

    //poistaa laivalistasta kuolleet laivat pois
    protected boolean removeDeadShip(ArrayList<Ship> container, int playerNumber){
        for(int i = 0; i<container.size(); i++){
            if(container.get(i).isDestroyed()){
                container.remove(i);
                System.out.println("kuollut laiva poistettu");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Upotit vastustajan laivan!");
                alert.setTitle("Laiva upotettu!");
                alert.showAndWait();
                if(container.isEmpty()){
                    winner(playerNumber);
                    return true;
                }
                else{
                    return false;
                }
            }
        }
        return false;
    }


    //valmis toteutus avaisi uuden ikkunan jossa voittaja julkistetaan, sekä myös vaihtoehdon aloittaa pelin alusta
    protected void winner(int playerNumber) {
        System.out.println("PELIN VOITTI: PELAAJA " + playerNumber);
        //väliaikainen, sulkee ohjelman
        Main.getStage().close();
        Stage stage = new Stage();

        // Napit
        Button newGame = new Button("Pelaa uudelleen");
        Button exitGame = new Button("Poistu pelistä");
        newGame.setAlignment(Pos.BOTTOM_CENTER);
        exitGame.setAlignment(Pos.BOTTOM_CENTER);

        exitGame.setOnAction(e -> {
            Platform.exit();
        });
        newGame.setOnAction(e -> {
            try {
                reset();
                stage.close();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });

        // Lopputekstit
        Label endingLabel = new Label("koipi");
        if(playerNumber == 1) {
            endingLabel.setText("Voittaja: " + playerOneName + "! \nPelataanko uudelleen?");
        }else if(playerNumber == 2){
            endingLabel.setText("Voittaja: " + playerTwoName+ "! \nPelataanko uudelleen?");
        }
        endingLabel.setTextFill(Color.SPRINGGREEN);
        endingLabel.setAlignment(Pos.CENTER);
        endingLabel.setScaleX(3);
        endingLabel.setScaleY(3);

        //Hbox nappien asettelulle
        HBox h = new HBox();
        h.getChildren().addAll(newGame,exitGame);
        h.setSpacing(10);
        h.setPadding(new Insets(0,0,15,0));
        h.setAlignment(Pos.CENTER);
        HBox.setHgrow(h, Priority.ALWAYS);

        // Taustakuva
        ImageView kuva = new ImageView(new Image(getClass().getResourceAsStream("battleships.png")));
        kuva.setX(-700);
        kuva.setY(-700);

        // Loput
        BorderPane pane = new BorderPane();
        pane.getChildren().add(kuva);
        pane.setCenter(endingLabel);
        pane.setBottom(h);
        Scene scene = new Scene(pane, 500, 300);
        stage.setTitle("Peli päättyi!");
        stage.setScene(scene);
        stage.show();
    }

    protected void reset() throws IOException {
        b1 = new Board();
        b2 = new Board();
        b3 = new Board();
        b4 = new Board();
        ap1 = new AnchorPane();
        ap2 = new AnchorPane();
        ap3 = new AnchorPane();
        ap4 = new AnchorPane();
        lauta1 = new AnchorPane();
        lauta2 = new AnchorPane();
        lauta3 = new AnchorPane();
        lauta4 = new AnchorPane();
        playerOneShipContainer = new ArrayList<>();
        playerTwoShipContainer = new ArrayList<>();
        pOneShipImages = new ArrayList<>();
        pTwoShipImages = new ArrayList<>();
        bp = new BorderPane();
        fp1 = new FlowPane();
        fp2 = new FlowPane();
        counter = 1;
        switchScene("mainMenu.fxml");
    }
}//class