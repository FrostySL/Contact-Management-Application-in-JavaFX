package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class of the application.
 * Launches the main view of the contact management.
 * 
 * @version 1.00 (20 May 2024)
 * @author Lukas Sinning
 */
public class MainApp extends Application {

    /* Primary window of the application */
    private Stage primaryStage;
    
    /**
     * Starts the application and shows the main view.
     * 
     * @param primaryStage the primary window of the application
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        
        // Shows the main view
        showMainView();
        
    }
    
    /**
     * Shows the main view of the application.
     */
    private void showMainView() {
        
        MainView mainView = new MainView();
        Scene scene = new Scene(mainView.getView(), 800, 600);
        primaryStage.setTitle("ContactApp");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    /**
     * Main method to launch the application.
     * @param args arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }


}
