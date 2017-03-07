package sample.classes;

import javafx.stage.Stage;

/**
 * Created by Rynkovoy on 26.09.2016.
 */
public class MyStage extends Stage{
    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private static Stage stage = new Stage();
}
