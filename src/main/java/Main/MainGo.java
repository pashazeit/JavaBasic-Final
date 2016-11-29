package Main;

import CreatAir.AviaPort;
import DB.CreateTable;
import DB.DBconnect;
import InterFace.Bank;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;


import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static CreatAir.AviaPort.all;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainGo extends Application implements Bank {

    public static ArrayList<AviaPort> newAAA;
    public static int Money = money;

    private final String user = "root";
    private final String pass = "root";
    private final String url = "jdbc:mysql://localhost:3306/mysql?useSSL=false";
    private DBconnect db = new DBconnect();
    private Connection cnt = db.conn(url, user, pass);

    public MainGo() throws SQLException {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
        primaryStage.setTitle("AirPort");
        primaryStage.setScene(new Scene(root, 500, 600));
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> {
            try {
                // закрываем соединение с базой данных
                cnt.close();
            } catch (SQLException e) {
                System.out.println("aaaaaaa");
            }
            event.consume();
        });
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {

        CreateTable.CreateDB();
        // создаём массив самолётов

        for (int i = 0; i < 10; i++) {
            {
                all.add(new AviaPort());
            }
        }
        //  запись в файл

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Test.txt"));
        oos.writeObject(all);
        all.clear();

        //чтение из файла

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Test.txt"));
        newAAA = (ArrayList<AviaPort>) ois.readObject();

        for (AviaPort p : newAAA) {
            System.out.printf("Имя: %s \t номер: %d \t пасс мест: %d \n", p.name, p.number, p.passenger_seat);
        }

        //запуск FXML
        launch(args);
    }
    // работа с XML
    //   OutXml.outMet();
}







