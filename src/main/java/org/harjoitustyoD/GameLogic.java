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
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
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
    private BorderPane bp = new BorderPane();
    private AnchorPane lauta1 = new AnchorPane();
    private AnchorPane lauta2 = new AnchorPane();
    private AnchorPane lauta3 = new AnchorPane();
    private AnchorPane lauta4 = new AnchorPane();
    protected ArrayList<Ship> playerOneShipContainer = new ArrayList<>();
    protected ArrayList<Ship> playerTwoShipContainer = new ArrayList<>();
    protected ArrayList<ImageView> pOneShipImages = new ArrayList<>();
    protected ArrayList<ImageView> pTwoShipImages = new ArrayList<>();
    private FlowPane fp1 = new FlowPane();
    private FlowPane fp2 = new FlowPane();
    private Button button1 = new Button();
    private Button button2 = new Button();
    private Button exitToMainMenu1 = new Button();
    private Button exitToMainMenu2 = new Button();
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
    protected AudioClip backgroundMusic = new AudioClip(getClass().getResource("rampytysbiisi.wav").toString());
    protected int placeCorrect = 0;
    protected String playerOneName;
    protected String playerTwoName;

    protected void setPlayerOneName(String s){
        playerOneName = s;
    }

    protected void setPlayerTwoName(String s){
        playerTwoName = s;
    }


    /**
     * Creates the scenes used for the game.
     */
    public void createScenes(){

        scene1 = new Scene(ap1, 1600,900);
        scene2 = new Scene(ap2, 1600, 900);
        scene3 = new Scene(bp, 1600, 900);

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
                case 3: //switch between p1 and p2
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
        lauta1 = b1.buildBoard(50, getClass().getResourceAsStream("ocean.png"));
        lauta3 = b3.buildBoard(50, getClass().getResourceAsStream("mistocean.png"));
        fp1.setHgap(30);
        fp1.setVgap(10);

        for(int i = 0; i < pOneShipImages.size(); i++){
            fp1.getChildren().add(pOneShipImages.get(i));
            initializeMouseEvent(pOneShipImages.get(i), b1, ap1, fp1, playerOneShipContainer);
        }
        button1.setText("Vaihda pelaajaa");
        button1.setOnAction(e->{
            try {
                if(fp1.getChildren().size() == 0) {
                    playSound("buttonclick.wav");
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
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Lauta ei valmis");
                    alert.setHeaderText("Aseta kaikki laivasi!");
                    playSound("error.wav");
                    alert.showAndWait();

                }

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        button1.setLayoutX(800);
        button1.setLayoutY(450);
        button1.setScaleX(2);
        button1.setScaleY(2);

        exitToMainMenu1.setText("Poistu päävalikkoon");
        exitToMainMenu1.setOnAction(e -> {
            try {
                reset();
                playSong("stop");
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });

        HBox g = new HBox();

        // Labelit ruudukoille!
        Label pelausRuutu = new Label("Sijoita laivasi tähän ruutuun");
        pelausRuutu.setScaleY(2);
        pelausRuutu.setScaleX(2);
        //pelausRuutu.setAlignment(Pos.BOTTOM_RIGHT);
        pelausRuutu.setLayoutX(385 - size*5);
        // Setattu oikein, siirretään gridin koon mukaan
        pelausRuutu.setLayoutY(395/size * 2);

        Label arvausRuutu = new Label("Arvaa tähän ruutuun");
        arvausRuutu.setScaleX(2);
        arvausRuutu.setScaleY(2);
        // Setattu oikein, siirretään gridin koon mukaan
        arvausRuutu.setLayoutY(1100/size * 2.5);
        arvausRuutu.setLayoutX(1375 - size*10);
        ap1.getChildren().addAll(pelausRuutu,arvausRuutu);

        //tässä setataan placeCorrect, joka vaihtaa nodea johon laiva siirretään
        //f on näkymättömän gridin siirtäminen
        int f = 0;
        if(b1.getBoardSize() == 5){
            f = 300;
            placeCorrect = -6;
        }
        else if(b1.getBoardSize() == 6){
            placeCorrect = -5;
            f = 250;
        }
        else if(b1.getBoardSize() == 7){
            placeCorrect = -5;
            f = 250;
        }
        else if(b1.getBoardSize() == 8){
            placeCorrect = -4;
            f = 200;
        }
        else if(b1.getBoardSize() == 9){
            placeCorrect = -4;
            f = 200;
        }
        else if(b1.getBoardSize() == 10){
            placeCorrect = -4;
            f = 195;
        }

        g.setPadding(new Insets(f, f, f, f));
        g.getChildren().add(lauta1);
        ap1.getChildren().addAll(g, lauta3, fp1, button1, exitToMainMenu1);
        AnchorPane.setRightAnchor(lauta3, 50d);
        AnchorPane.setBottomAnchor(lauta3, 50d);
        AnchorPane.setRightAnchor(fp1, 10d);
        AnchorPane.setLeftAnchor(exitToMainMenu1, 0d);

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
        AnchorPane lauta2 = b2.buildBoard(50, getClass().getResourceAsStream("ocean.png"));
        AnchorPane lauta4 = b4.buildBoard(50, getClass().getResourceAsStream("mistocean.png"));
        fp2.setHgap(30);
        fp2.setVgap(10);

        for(int i = 0; i < pTwoShipImages.size(); i++){
            fp2.getChildren().add(pTwoShipImages.get(i));
            initializeMouseEvent(pTwoShipImages.get(i), b2, ap2, fp2, playerTwoShipContainer);
        }
        button2.setText("Vaihda pelaajaa");
        button2.setOnAction(e->{
            try {
                if(fp2.getChildren().size() == 0) {
                    playSound("buttonclick.wav");

                    lauta2.setDisable(true);
                    startGuessing(3, playerNumber, b3, b2, playerTwoShipContainer, button1);
                    button2.setDisable(true);
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Lauta ei valmis!");
                    alert.setHeaderText("Aseta kaikki laivasi!");
                    playSound("error.wav");
                    alert.showAndWait();
                }

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        button2.setLayoutX(800);
        button2.setLayoutY(450);
        button2.setScaleX(2);
        button2.setScaleY(2);

        exitToMainMenu2.setText("Poistu päävalikkoon");
        exitToMainMenu2.setOnAction(e -> {
            try {
                reset();
                playSong("stop");
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });

        // Labelit ruudukoille!
        Label pelausRuutu = new Label("Sijoita laivasi tähän ruutuun");
        pelausRuutu.setScaleY(2);
        pelausRuutu.setScaleX(2);
        //pelausRuutu.setAlignment(Pos.BOTTOM_RIGHT);
        pelausRuutu.setLayoutX(385 - size*5);
        // Setattu oikein, siirretään gridin koon mukaan
        pelausRuutu.setLayoutY(395/size * 2);

        Label arvausRuutu = new Label("Arvaa tähän ruutuun");
        arvausRuutu.setScaleX(2);
        arvausRuutu.setScaleY(2);
        // Setattu oikein, siirretään gridin koon mukaan
        arvausRuutu.setLayoutY(1100/size * 2.5);
        arvausRuutu.setLayoutX(1375 - size*10);
        ap2.getChildren().addAll(pelausRuutu,arvausRuutu);


        //tässä setataan placeCorrect, joka vaihtaa nodea johon laiva siirretään
        //f on näkymättömän gridin siirtäminen
        int f = 0;
        if(b1.getBoardSize() == 5){
            f = 300;
            placeCorrect = -6;
        }
        else if(b1.getBoardSize() == 6){
            placeCorrect = -5;
            f = 250;
        }
        else if(b1.getBoardSize() == 7){
            placeCorrect = -5;
            f = 250;
        }
        else if(b1.getBoardSize() == 8){
            placeCorrect = -4;
            f = 200;
        }
        else if(b1.getBoardSize() == 9){
            placeCorrect = -4;
            f = 200;
        }
        else if(b1.getBoardSize() == 10){
            placeCorrect = -4;
            f = 195;
        }

        HBox d = new HBox();

        d.setPadding(new Insets(f, f, f, f));
        d.getChildren().add(lauta2);
        ap2.getChildren().addAll(d, lauta4, fp2, button2, exitToMainMenu2);
        AnchorPane.setRightAnchor(lauta4, 50d);
        AnchorPane.setBottomAnchor(lauta4, 50d);
        AnchorPane.setRightAnchor(fp2, 10d);
        AnchorPane.setLeftAnchor(exitToMainMenu2, 0d);
    }


    /**
     * Creates a scene for switching players.
     */

    public void createSwitchPlayerScene(){
        //switches from p1 create board to p2 create board
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("sand.png")));
        HBox hb = new HBox();
        Label lb = new Label("Pelaaja 2: Siirry asettamaan laivasi laudalle!");
        lb.setFont(Font.font(50));
        Button switchb5 = new Button("Seuraava");
        switchb5.setOnAction(e -> {
            try {
                playSound("buttonclick.wav");
                if(counter == 1){
                    lb.setText("PELI ALKAKOON, " + playerOneName +"n vuoro");
                    setBoardNumber(2);
                    setPlayerNumber(1);
                    counter++;
                }
                else if(counter == 2){
                    lb.setText("Pelaajan "+ playerTwoName + " vuoro");
                    setBoardNumber(1);
                    setPlayerNumber(2);
                    counter++;
                }
                else if(counter > 2 && counter % 2 != 0){
                    lb.setText("Pelaajan "+ playerOneName + " vuoro");
                    setBoardNumber(2);
                    setPlayerNumber(1);
                    counter++;
                }
                else if(counter > 2 && counter % 2 == 0){
                    lb.setText("Pelaajan "+ playerTwoName + " vuoro");
                    setBoardNumber(1);
                    setPlayerNumber(2);
                    counter++;
                }
                switchScene("--");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        hb.getChildren().addAll(lb, switchb5);
        hb.setSpacing(20);
        hb.setAlignment(Pos.CENTER);
        bp.getChildren().addAll(imageView);
        bp.setCenter(hb);

    }


    /**
     * Creates the right amount of boats and inserts them to their correct containers.
     * Gets its parameters from the text fields in the main menu.
     *
     * @param lta = lentotukialukset
     * @param tl = taistelulaivat
     * @param ris = risteilijät
     * @param sv = sukellusveneet
     * @param hv = hävittäjät
     */

    public void createShips(int lta, int tl, int ris, int sv, int hv) {

        //Empty the old one if it exist, kind of a redundant feature
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

    /**
     * Initializes the mouse events used for moving and placing the ships.
     *
     * @param b
     * @param board
     * @param ap
     * @param fp
     * @param container
     */

    private void initializeMouseEvent(ImageView b, Board board, AnchorPane ap, FlowPane fp,ArrayList<Ship> container){

        b.setOnMousePressed(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)){
                fp.getChildren().remove(b);
                board.getGrid().getChildren().remove(b);
                ap.getChildren().add(b);
            }
            mousePressed(event, board, b, fp);
        });
        b.setOnMouseDragged(event -> dragged(event, b));
        b.setOnMouseReleased(event -> released(event, board, b, ap, container, fp));

    }//initializeMouseEvent()

    /**
     * Left-click sets the clicked ship as selected, which enables rotating for the ships.
     * Right-click removes the ship from the grid, placing it back to it's original place.
     *
     * @param event
     * @param board
     * @param b
     * @param fp
     */

    private void mousePressed(MouseEvent event, Board board, ImageView b, FlowPane fp){
        if (event.getButton().equals(MouseButton.PRIMARY)){
            // settaa viimeisimmän laivan valituksi laivaksi, jotta rotate toimii r:stä
            selectedShip = b;
            return;
        }
        else if(event.getButton().equals(MouseButton.SECONDARY)){
            if(board.getGrid().getChildren().contains(b)){
                board.getGrid().getChildren().remove(b);
                fp.getChildren().add(b);
                return;
            }
        }

    }//mousePressed()

    /**
     * Saves the coordinates for the dragged ship and makes the dragged ship selected.
     * @param event
     * @param b
     */

    private void dragged(MouseEvent event, ImageView b){
        cordsX = (int) (b.getLayoutX() + event.getX());
        cordsY = (int) (b.getLayoutY() + event.getX());
        b.setLayoutX(event.getSceneX()-15);
        b.setLayoutY(event.getSceneY()-15);
        // settaa viimeisimmän laivan valituksi laivaksi, jotta rotate toimii r:stä
        selectedShip = b;
    }//dragged()

    /**
     * Places the ship to the grid when releasing the mouse. Also checks that the place is valid, busing checkValidShipPlacement();
     * Also checks that the node in the grid isn't already occupied, by using isSpotTaken();
     * @param event
     * @param board
     * @param b
     * @param ap1
     * @param container
     * @param fp
     */

    private void released(MouseEvent event, Board board, ImageView b, AnchorPane ap1, ArrayList<Ship> container, FlowPane fp){
        if (event.getButton().equals(MouseButton.PRIMARY)){

            //place correct siirtää nodea sen mukaan minkä kokoinen lauta on
            // ÄLÄ KOSKE !! :D KATSOTTU ETTÄ TOIMII
            int gridx = (int) (b.getLayoutX())/50 + placeCorrect;
            int gridy = (int) (b.getLayoutY()+15)/50 + placeCorrect;

            cordsX = gridx;
            cordsY = gridy;
            // settaa viimeisimmän laivan valituksi laivaksi, jotta rotate toimii r:stä
            selectedShip = b;

            // kun päästetään irti mouse1:stä, setataan coordit
            int index = getShipIndex(b);
            if (boardNumber == 1) {
                playerOneShipContainer.get(index).setStartX(cordsX);
                playerOneShipContainer.get(index).setStartY(cordsY);
                playerOneShipContainer.get(index).setEndX(cordsX + playerOneShipContainer.get(index).getSize());
                playerOneShipContainer.get(index).setEndY(cordsY + playerOneShipContainer.get(index).getSize());
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
                            return true;
                        }
                    }else if(s.getIsHorizontal() == false && ship.getIsHorizontal() == true){
                        if(x <= s.getStartX() && ship.getEndX() > s.getStartX() && y >= s.getStartY() && y < s.getEndY()){
                            return true;
                        }
                    }else if(s.getIsHorizontal() == true && ship.getIsHorizontal() == false){
                        if(y <= s.getStartY() && ship.getEndY() > s.getStartY() && x >= s.getStartX() && x < s.getEndX()){
                            return true;
                        }

                    }else if(s.getIsHorizontal() == false && ship.getIsHorizontal() == false){
                        if (y+j >= s.getStartY() && y+j <= s.getStartY() + s.getSize() - 1 && s.getStartX() == x) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }//isSpotTaken()


    /**
     * Calculates the area of ships that are allowed on a specific board size and checks if it's valid.
     *
     * @param area
     * @param lta
     * @param tl
     * @param ris
     * @param sv
     * @param hv
     * @return
     */

    public boolean areShipsAllowed(int area, int lta, int tl, int ris, int sv, int hv){
        int RA = area * area;
        int AA = 5*lta + 4*tl + 3*ris + 3*sv + 2*hv;
        if(RA >= 2*AA){
            return true;
        }
        else{
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

    protected void setPlayerNumber(int number){
        this.playerNumber = number;
    }


    /**
     * A method used for rotating the ship that has been clicked last
     * @param rectangle is an old name but means the ship image
     */

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


    /**
     * Easier way of getting the index of the corresponding ship
     * in their lists
     * @param rectangle
     * @return
     */
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


    /**
     * This method checks if a ship has any of the values given to it. If a match is found,
     * the ship is hit.
     *
     * @param x
     * @param y
     * @param container
     * @return
     */

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
                    cordsX = board.getBoardSize() - (container.get(getShipIndex(b)).getSize());
                } else {
                    cordsX = board.getBoardSize() - (container.get(getShipIndex(b)).getSize());
                }
            }
            //Ei voi placettaa ghost nodejen ulkopuolelle, setataan oikein ruudukon sisälle
            if (cordsY > board.getBoardSize() - 1) {
                cordsY = board.getBoardSize() - 1;
            }
        }else {
            // Tarkistetaan vertikaalisesti oikeanlainen placeaminen, JOS LAIVA ON KÄÄNNETTY
            if (cordsY > board.getBoardSize() - container.get(getShipIndex(b)).getSize()) {
                //Ei voi placettaa ghost nodejen ulkopuolelle, setataan oikein ruudukon sisälle
                if (cordsY > board.getBoardSize() - 1) {
                    cordsY = board.getBoardSize() - (container.get(getShipIndex(b)).getSize());
                } else {
                    cordsY = board.getBoardSize() - (container.get(getShipIndex(b)).getSize());
                }
            }
            if (cordsX > board.getBoardSize() - 1) {
                cordsX = board.getBoardSize() - 1;
            }
        }
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


    /**
     * Starts the game, after the ships have been placed by both players.
     *
     * @param boardNumber
     * @param playerNumber
     * @param board
     * @param board2
     * @param container
     * @param button
     * @throws IOException
     */

    protected void startGuessing(int boardNumber, int playerNumber, Board board, Board board2, ArrayList<Ship> container, Button button) throws IOException {
        board.getGrid().setDisable(false);
        Image image1 = new Image(getClass().getResourceAsStream("vihrearasti.png"));
        Image image2 = new Image(getClass().getResourceAsStream("punainenrasti.png"));
        ImageView imgGreen1 = new ImageView(image1);
        ImageView imgRed2 = new ImageView(image2);

        imgGreen1.setFitHeight(50);
        imgRed2.setFitWidth(50);
        imgGreen1.setFitWidth(50);
        imgRed2.setFitHeight(50);

        setBoardNumber(boardNumber);
        switchScene("--");
        board.getGrid().setOnMouseClicked(f->{
            if(getNodeFromBoard(board, board.getCordsX()-1, board.getCordsY()-1) != null && getNodeFromBoard(board, board.getCordsX()-1, board.getCordsY()-1).isDisabled()){
                return;
            }
            if((shoot(board.getCordsX(), board.getCordsY(), container))){
                if(checkGuessValidPlacement(board, container)) {
                    ImageView temp = new ImageView(image1);
                    temp.setFitHeight(50);
                    temp.setFitWidth(50);
                    board.getGrid().add(temp, board.getCordsX() - 1, board.getCordsY() - 1);
                    board2.getGrid().add(new ImageView(image1), board.getCordsX() - 1, board.getCordsY() - 1);
                    getNodeFromBoard(board, board.getCordsX() - 1, board.getCordsY() - 1).setDisable(true);

                    button.setDisable(true);

                    playSound("valmiscrash.wav");
                    removeDeadShip(container, playerNumber);

                }
            }
            else if(!(shoot(board.getCordsX(), board.getCordsY(), container))){
                if(checkGuessValidPlacement(board, container)) {
                    button.setDisable(false);
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
     * A method for playing soundclips,
     * for example when you hit a ship,
     * a hitting sound should be played
     * @param name is the name of the
     *             requested audioclip
     */
    protected void playSound(String name){
        AudioClip audio = new AudioClip(getClass().getResource(name).toString());
        audio.play();
    }


    /**
     * A method for playing background
     * music, started from Controller
     * @param state Whether you want to start or stop
     */

    protected void playSong(String state){
        if(state.equalsIgnoreCase("start")){
            backgroundMusic.setVolume(0.15);
            backgroundMusic.setCycleCount(20);
            backgroundMusic.play();
        }else if(state.equalsIgnoreCase("stop")){
            backgroundMusic.stop();
        }
    }


     /**
     * Returns a specific node from the grid.
     *
     * @param board
     * @param col
     * @param row
     * @return
     */
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


    /**
     * Removes ships from the container, if they've lost all of their HP.
     * Also checks the container for remaining ships. If none are left, the player with ships left is
     * announced as winner.
     *
     * @param container
     * @param playerNumber
     * @return
     */

    protected boolean removeDeadShip(ArrayList<Ship> container, int playerNumber){
        for(int i = 0; i<container.size(); i++){
            if(container.get(i).isDestroyed()){
                container.remove(i);
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


    /**
     * Opens up a new window that announces the winner and closes
     * the actual game window.
     *
     * Also gives an option to the players to restart the game or
     * leave the game and close the application.
     * @param playerNumber
     */

    protected void winner(int playerNumber) {
        Main.getStage().close();
        Stage stage = new Stage();

        // Napit
        Button newGame = new Button("Pelaa uudelleen");
        Button exitGame = new Button("Poistu pelistä");
        newGame.setAlignment(Pos.BOTTOM_CENTER);
        exitGame.setAlignment(Pos.BOTTOM_CENTER);

        exitGame.setOnAction(e -> {
            playSound("buttonclick.wav");
            Platform.exit();
        });
        newGame.setOnAction(e -> {
            try {
                playSound("buttonclick.wav");
                reset();
                stage.close();

            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });

        // Lopputekstit
        Label endingLabel = new Label();
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
        stage.setResizable(false);
        stage.show();

        //stop the song
        playSong("stop");
    }


    /**
     * Resets the game and switches the scene back to the main menu.
     *
     * @throws IOException
     */

    protected void reset() throws IOException {
        b1 = new Board();
        b2 = new Board();
        b3 = new Board();
        b4 = new Board();
        ap1 = new AnchorPane();
        ap2 = new AnchorPane();
        bp = new BorderPane();
        lauta1 = new AnchorPane();
        lauta2 = new AnchorPane();
        lauta3 = new AnchorPane();
        lauta4 = new AnchorPane();
        playerOneShipContainer = new ArrayList<>();
        playerTwoShipContainer = new ArrayList<>();
        pOneShipImages = new ArrayList<>();
        pTwoShipImages = new ArrayList<>();
        fp1 = new FlowPane();
        fp2 = new FlowPane();
        counter = 1;
        switchScene("mainMenu.fxml");
    }
}//class