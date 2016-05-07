package connectionDB;


import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;


/**
 * Created by dimsan on 07.11.2015.
 */
public class DbConnection {
    Connection  con= null;
    public static Connection ConnectDb(){

        try{

            Class.forName("org.sqlite.JDBC");
            String dir = System.getProperty("user.dir");
            String dbUrl="jdbc:sqlite:dir\\ProjectListOfCars.sqlite";
            Connection con = DriverManager.getConnection(dbUrl);
            //JOptionPane.showMessageDialog(null,"Соединение с базой установлено");
            return con;

        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Ошибка соединения с базой");
            return null;
        }

    }
    public static Connection ConnectDb(String name){

        try{
            Class.forName("org.sqlite.JDBC");
            String dir = System.getProperty("user.dir");
            String dbUrl="jdbc:sqlite:dir\\"+name+".sqlite";
            Connection con = DriverManager.getConnection(dbUrl);
            //JOptionPane.showMessageDialog(null,"Соединение с базой установлено");
            return con;

        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Ошибка, база данных не найдена");
            return null;
        }

    }

}