package org.example;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/org/example/ui.fxml"));
        Logic logic = new Logic();
        Controller controller = new Controller(logic);
        fxmlLoader.setController(controller);
        Scene scene = new Scene(fxmlLoader.load(), 1080, 720);
        stage.setTitle("Caesar Decipher Application");
        stage.setScene(scene);
        stage.show();


    }

}

