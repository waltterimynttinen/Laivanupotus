package org.harjoitustyoD;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Main extends Application {
    private static Stage stage;

    protected static Stage getStage(){
        return Main.stage;
    }

    protected void setStage(Stage stage){
        Main.stage = stage;
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        setStage(primaryStage);
        Parent root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        stage.setTitle("Laivanupotus");
        stage.setScene(new Scene(root));
        stage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }



}
