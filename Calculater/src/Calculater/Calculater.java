package Calculater;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Calculater extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Calculater.fxml"));
            primaryStage.setTitle("计算器");
            primaryStage.setScene(new Scene(root, 631, 735));
            primaryStage.show();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }


    }


    public static void main(String[] args) {
        launch(args);
    }
}
