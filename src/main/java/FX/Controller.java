package FX;

import CreatAir.AviaPort;
import DB.DBconnect;
import Jdom.outAP;
import Main.MainGo;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.*;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static CreatAir.AviaPort.all;
import static Jdom.Jdom.JDOMtest;
import static Jdom.Jdom.JDOMtest2;
import static Jdom.outAP.currentjet;
import static Main.MainGo.newAAA;

public class Controller {

    private static final String user = "root";
    private static final String pass = "root";
    private static final String url = "jdbc:mysql://localhost:3306/mysql?useSSL=false";
    private DBconnect db = new DBconnect();
    private Connection cnt = db.conn(url, user, pass);
    private Statement stat = cnt.createStatement();


    public int num;
    public int funct;
    public static int bort_index;
    public int babki;
    private String funct_name;


    @FXML
    Label cash;
    @FXML
    Label fcost;
    @FXML
    Button button;
    @FXML
    TextArea info;
    @FXML
    ComboBox planeid;
    @FXML
    ComboBox func;
    @FXML
    Label pewpew;
    @FXML
    Button flyaway;
    @FXML
    Button out;
    @FXML
    Button allxml;
    @FXML
    Button onexml;
    @FXML
    Button adplan;

    public Controller() throws SQLException {
    }

    // Заполняем info
    public void getInfo(int bort_index) throws SQLException {
        info.setText("Bort name: " + (currentjet.get(bort_index).name) + "\n" +
                "Bort number: " + currentjet.get(bort_index).number + "\n" +
                "Bort speed: " + currentjet.get(bort_index).speed + "\n" +
                "Bort range: " + currentjet.get(bort_index).range + "\n" +
                "Bort weight: " + currentjet.get(bort_index).weight + "\n" +
                "Bort fuel: " + currentjet.get(bort_index).fuel + "\n" +
                "Bort tank : " + currentjet.get(bort_index).tank + "\n" +
                "Bort passenger : " + currentjet.get(bort_index).passenger + "\n" +
                "Bort passenger seat : " + currentjet.get(bort_index).passenger_seat + "\n" +
                "Bort flyCost: " + currentjet.get(bort_index).flyCost);
    }

    // Добавляем в HashMap выбранный самолёт
    public void setMas(int bort_index) throws SQLException {
        ResultSet resset = stat.executeQuery("select  * from Airport_MSQ.Jets where id = " + bort_index);
        while (resset.next()) {
            currentjet.clear();
            currentjet.put(bort_index, new outAP
                    (resset.getString("jet_name"),
                            resset.getInt("jet_ID"),
                            resset.getInt("jet_speed"),
                            resset.getInt("jet_range"),
                            resset.getInt("jet_weight"),
                            resset.getInt("jet_fuel"),
                            resset.getInt("jet_tank"),
                            resset.getInt("jet_passenger"),
                            resset.getInt("jet_passengerseat"),
                            resset.getInt("jet_FlyCost")));

        /*    for (Map.Entry<Integer, outAP> item : currentjet.entrySet()) {
                System.out.printf("Ключ: %s  Значение: %s \n", item.getKey(), item.getValue().getNumber());
            }*/
        }
    }

    //добавляем в базу данные
    public void addAir() throws SQLException {

        String adddb = "insert into Airport_MSQ.Jets(jet_name, jet_ID, jet_speed, jet_range, jet_weight, jet_fuel, jet_tank,  jet_passenger, jet_passengerseat,  jet_Flycost) values (?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement prep = cnt.prepareStatement(adddb);
        AviaPort ap = null;
        ap = new AviaPort();
        all.add(ap);
        prep.setString(1, ap.getName());
        prep.setInt(2, ap.getNumber());
        prep.setInt(3, ap.getSpeed());
        prep.setInt(4, ap.getRange());
        prep.setInt(5, ap.getWeight());
        prep.setInt(6, ap.getFuel());
        prep.setInt(7, ap.getTank());
        prep.setInt(8, ap.getPassenger());
        prep.setInt(9, ap.getPassenger_seat());
        prep.setInt(10, ap.getFlyCost());
        prep.execute();
    }

        //Добавление в ComboBox
    public void fill() throws SQLException {
        ResultSet resset = stat.executeQuery("select  * from Airport_MSQ.Jets");
        planeid.getItems().clear();
        while (resset.next()) {
            planeid.getItems().addAll(resset.getInt("id") + " " + resset.getString("jet_name") + " " + resset.getInt("jet_ID"));
        }
    }

    public void initialize() throws IOException, SQLException {
        babki = MainGo.Money;
        cash.setText(babki + "");
        func.getItems().addAll("Заправить", "Слить", "Посадить пассажиров", "Снять пассажиров");
        button.setDisable(true);
        onexml.setDisable(true);
        //добавляем самолеты в ComboBox


        fill();
        planeid.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if ((int) newValue >= 0) {
                onexml.setDisable(false);
                bort_index = (int) newValue;
                String aa = (String) planeid.getValue();

                // берём ID с базы данных
                Pattern pattern;
                Matcher matcher;
                pattern = Pattern.compile("(^[0-9]+)");
                matcher = pattern.matcher(aa);
                if (matcher.find()) {
                    bort_index = Integer.parseInt(matcher.group(1));
                }
                System.out.println(bort_index);

                try {
                    setMas(bort_index);
                    getInfo(bort_index);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                num = currentjet.get(bort_index).number;
                pewpew.setText("Выберите что будем делать с #" + num);
                fcost.setText("Выручим с полета: " + currentjet.get(bort_index).flyCost);
            }
        });

        func.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                funct_name = newValue + "";

            }
        });

        func.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                funct = (int) newValue;
                pewpew.setText("Для функции " + funct_name + " \"жмякните Жмяк\"");
                button.setDisable(false);

            }
        });

        button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String txt = "";
                if ((num > 0) && (funct >= 0)) {

                    switch (funct) {
                        case 0:
                            currentjet.get(bort_index).inFuel();
                            try {
                                getInfo(bort_index);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            cash.setText(MainGo.Money + "");
                            txt = "Bort fuel: " + currentjet.get(bort_index).fuel + "";
                            break;
                        case 1:
                            currentjet.get(bort_index).outFuel();
                            try {
                                getInfo(bort_index);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            cash.setText(MainGo.Money + "");
                            txt = "Bort fuel: " + currentjet.get(bort_index).fuel + "";
                            break;
                        case 2:
                            currentjet.get(bort_index).inPassenger();
                            try {
                                getInfo(bort_index);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            fcost.setText("Выручим с полета: " + currentjet.get(bort_index).flyCost);
                            txt = "Bort passengers: " + currentjet.get(bort_index).passenger + "";
                            break;
                        case 3:
                            currentjet.get(bort_index).outPassenger();
                            try {
                                getInfo(bort_index);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            fcost.setText("Выручим с полета: " + currentjet.get(bort_index).flyCost);
                            txt = "Bort passengers: " + currentjet.get(bort_index).passenger + "";
                            break;
                    }
                    }
                    pewpew.setText("well done, my son!\n" + txt + "\n");
                    button.setText("Thanks!");
                    try {
                        stat.executeUpdate("update Airport_MSQ.Jets SET " +
                                //"jet_name =\"" + currentjet.get(bort_index).name + "\", " +
                                //"jet_id= " + currentjet.get(bort_index).number + ", " +
                                //"jet_speed= " + currentjet.get(bort_index).speed+ ", " +
                                //"jet_range= " + currentjet.get(bort_index).range+ ", " +
                                //"jet_weight= " + currentjet.get(bort_index).weight+ ", " +
                                "jet_fuel= " + currentjet.get(bort_index).fuel + ", " +
                                //"jet_tank= " + currentjet.get(bort_index).tank+ ", " +
                                "jet_passenger= " + currentjet.get(bort_index).passenger + ", " +
                                //"jet_passengerseat= " + currentjet.get(bort_index).passenger_seat+ ", " +
                                "jet_FlyCost= " + currentjet.get(bort_index).flyCost + " " +
                                "where id = " + bort_index);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }


        });

        flyaway.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String txt = "";
                if ((num > 0) && (funct >= 0)) {

                    planeid.getItems().removeAll();

                    planeid.getItems().clear();
                    currentjet.remove(bort_index);
                    currentjet.clear();

                    pewpew.setText("well done, my son!\n" + txt + "\n");
                    info.setText("");
                    flyaway.setText("Thanks!");
                    button.setDisable(true);
                    onexml.setDisable(true);
                    try {
                        stat.executeUpdate("DELETE from Airport_MSQ.Jets WHERE id=" + bort_index);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    fill();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        out.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Test.txt"))) {
                oos.writeObject(newAAA);

            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) out.getScene().getWindow();
            stage.close();
        });

        allxml.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            String fileName = "airpln.xml";

            try {
                JDOMtest(newAAA, fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
            allxml.setText("Done");
        });

        onexml.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if ((num > 0) && (funct >= 0)) {
                String fileName = "airpln2.xml";

                try {
                    JDOMtest2(currentjet, fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                onexml.setText("Done");
            } else
                System.out.println(" не выбрано");
        });

        adplan.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {

            try {
                addAir();
                fill();
                button.setDisable(true);
                pewpew.setText("нешта дабавиу");
            } catch (SQLException e) {
                System.out.println("NEIN!!!");
            }
        });
    }
}