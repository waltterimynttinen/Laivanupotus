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
import javafx.stage.Stage;

import java.io.IOException;


public class GameLogic {
    private Board b1 = new Board();
    private Board b2 = new Board();
    private Pane ap = new Pane();
    Button buttonShip3 = new Button("Lentotukialus");
    Button buttonShip2 = new Button("Destroyer");
    Button buttonShip1 = new Button("Tykkivene");
    Button button = new Button("liitävä lautane");
    Label shipSize3LeftAmountLabel = new Label();
    Label shipSize2LeftAmountLabel = new Label();
    Label shipSize1LeftAmountLabel = new Label();
    boolean shipSize3isSelected = false;
    boolean shipSize2isSelected = false;
    boolean shipSize1isSelected = false;
    FlowPane hb = new FlowPane();
    public int cordsX2;
    public int cordsY2;

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

    public void createBoard1(int size) {
        b1.setBoardSize(size);
        ap = b1.buildBoard();
        shipSize3LeftAmountLabel.setText("Jäljellä: 1");
        shipSize2LeftAmountLabel.setText("Jäljellä: 1");
        shipSize1LeftAmountLabel.setText("Jäljellä: 1");
        placeShip(button);
        hb.getChildren().add(button);
        hb.getChildren().add(buttonShip3);
        hb.getChildren().add(shipSize3LeftAmountLabel);
        hb.getChildren().add(buttonShip2);
        hb.getChildren().add(shipSize2LeftAmountLabel);
        hb.getChildren().add(buttonShip1);
        hb.getChildren().add(shipSize1LeftAmountLabel);
        hb.setHgap(30);
        hb.setVgap(10);
        ap.getChildren().add(hb);

        AnchorPane.setRightAnchor(hb, 10d);

    }

    public void createBoard2(int size) {
        b2.setBoardSize(size);
        b2.buildBoard();
    }

    public void createShip(int size) {

    }

    public void placeShip(Button button) {
        button.setOnMouseClicked(e -> {

            if (e.getButton() == MouseButton.PRIMARY) {
                hb.getChildren().remove(button);
                ap.getChildren().remove(button);
                ap.getChildren().add(button);
                return;
            } else if (e.getButton() == MouseButton.SECONDARY) {
                System.out.println("place here");
                System.out.println(b1.getCordsX());
                hb.getChildren().remove(button);
                ap.getChildren().remove(button);
                //b1.grid.getChildren().add(button);
                b1.grid.add(button, b1.getCordsX(), b1.getCordsY());
            }
        });

        /*button.setOnMouseReleased(e->{
            System.out.println("place here");
            System.out.println(b1.getCordsX());
            hb.getChildren().remove(button);
            ap.getChildren().remove(button);
            //b1.grid.getChildren().add(button);
            b1.grid.add(button, b1.getCordsX(), b1.getCordsY());

        });*/

        button.setOnMouseDragged(e -> {
            button.setLayoutX(e.getSceneX());
            button.setLayoutY(e.getSceneY());
            System.out.println("dragging button");
            for (int i = 0; i < b1.getBoardSize(); i++) {
                ColumnConstraints c = new ColumnConstraints(50);
                //b1.grid.getColumnConstraints().add(c);

                double xx = (int) e.getX() * 2;
                double yy = (e.getY() * 2);
                int y = (int) yy / 100;
                int x = (int) xx / 100;
                y++;
                x++;
                System.out.println(x + " x " + y);
                cordsX2 = x;
                cordsY2 = y;
                System.out.println("column");
                System.out.println("CordsX on: " + cordsX2);
                System.out.println("CordsY on: " + cordsY2);

            }
            for (int i = 0; i < b1.getBoardSize(); i++) {
                RowConstraints c = new RowConstraints(50);
                //b1.grid.getRowConstraints().add(c);

                double xx = (int) e.getX() * 2;
                double yy = (e.getY() * 2);
                int y = (int) yy / 100;
                int x = (int) xx / 100;
                y++;
                x++;
                System.out.println(x + " x " + y);
                cordsX2 = x;
                cordsY2 = y;
                System.out.println("row");
                System.out.println("CordsX on: " + cordsX2);
                System.out.println("CordsY on: " + cordsY2);

            }
        });
    }
    public boolean areShipsAllowed(int area, int lta, int tl, int ris, int sv, int hv){
        int RA = area * area;
        int AA = 5*lta + 4*tl + 3*ris + 3*sv + 2*hv;
        if(RA >= 2*AA){
            System.out.println("kelpaa");
            return true;
        }
        else{
            System.out.println("ei kelpaa");
            return false;
        }
    }
}
