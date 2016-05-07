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

        testData();
    }
    private void testData(){

    /*CollectionsCarList carList = new CollectionsCarList();
    DataBaseCarList dataBaseCarList = new DataBaseCarList();

    Automobile automobile = new Automobile();
    automobile.setBrand("Audi");
    automobile.setModel("A6Quttro");
    automobile.setGarNumb("2121");
    automobile.setRegNumb("333");
    automobile.setEngNumb("x43333");
    automobile.setEngVol("3.2");
    automobile.setVinCode("zxfc 12345678");
    automobile.setYearsOfRel("2014");
    automobile.setServInt("10000");
    automobile.setSpdVol("9455");
    automobile.setServLast("545");

    Automobile automobile2 = new Automobile();
    automobile2.setBrand("Toyota");
    automobile2.setModel("Camry");
    automobile2.setGarNumb("2122");
    automobile2.setRegNumb("444");
    automobile2.setEngNumb("x65555");
    automobile2.setEngVol("3.2");
    automobile2.setVinCode("xcc 444555");
    automobile2.setYearsOfRel("2014");
    automobile2.setServInt("10000");
    automobile2.setSpdVol("9200");
    automobile2.setServLast("800");

    carList.add(automobile);
    carList.add(automobile2);
    dataBaseCarList.add(automobile);
    dataBaseCarList.add(automobile2);

*/
    }

    public static void main(String[] args) {
        launch(args);


    }

}
