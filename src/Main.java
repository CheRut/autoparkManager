import Interfaces.impls.CollectionsCarList;
//import Interfaces.impls.DataBaseCarList;
import Objects.Automobile;

import controllers.MainController;
import controllers.MainWindowController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application {
    Connection conn = null;
    private Stage primaryStage;
    private static final String iconImageLoc =
            //"http://icons.iconarchive.com/icons/scafer31000/bubble-circle-3/16/GameCenter-icon.png";
            "file:dir\\addAuto.png";
    private Timer notificationTimer = new Timer();

    @Override
    public void start(Stage primaryStage) throws Exception{
        System.out.println(System.getProperty("user.dir"));
        FXMLLoader fxmlLoader=new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/main.fxml"));
        Parent root = fxmlLoader.load();
        MainController mainController = fxmlLoader.getController();
        mainController.setMainStage(primaryStage);

        primaryStage.setTitle("Учет проведения технического обслуживания автомобилей");
        primaryStage.setScene(new Scene(root, 800, 600));

        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.show();

        
    }
    

    

    public static void main(String[] args) {
        launch(args);


    }

}
