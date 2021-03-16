package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader mainLoader=new FXMLLoader();
        mainLoader.setLocation(getClass().getResource("ProgramStartLayout.fxml"));
        Parent mainWindow=mainLoader.load();

        ProgramStartController controller=mainLoader.getController();
        controller.setProgramListView();

        primaryStage.setTitle("Selected Program");
        primaryStage.setScene(new Scene(mainWindow));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
