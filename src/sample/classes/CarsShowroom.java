package sample.classes;

import javafx.application.Application;
import javafx.stage.Stage;
import sample.controllers.Enter;

public class CarsShowroom extends Application {
    public static Stage stage;
    @Override
    public void start(Stage primaryStage) throws Exception{
       Enter enter = new Enter();
       enter.mainStage(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
