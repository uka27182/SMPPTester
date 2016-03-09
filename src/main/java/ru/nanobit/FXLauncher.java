package ru.nanobit;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FXLauncher extends Application {
    final Logger logger = LoggerFactory.getLogger(FXLauncher.class);

    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        logger.debug("start");

        Text text = new Text("Wow");
        VBox root = new VBox();
        root.getChildren().add(text);
        Scene scene = new Scene(root, 200, 100);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Hello FX");
        primaryStage.show();
    }
}
