package Jdom;

import CreatAir.AviaPort;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static FX.Controller.bort_index;
import static Main.MainGo.newAAA;

/**
 * Created by Zeit on 21.11.2016.
 */
public class Jdom {


    public static void JDOMtest(List<AviaPort> airr, String fileName) throws IOException {
        Document doc = new Document();
        // создаем корневой элемент с пространством имен
        doc.setRootElement(new Element("AirPlaner"));
        // формируем JDOM документ из объектов AirPlane
        for (AviaPort airpla : airr) {

            Element airplnElement = (new Element(airpla.getName() + "_" + airpla.getNumber()));
            // Element airplnElement = new Element("AirPlane", Namespace.getNamespace("Что происходит? "));
            //  airplnElement.addContent(new Element("name").setText("" + airpla.getName()));
            airplnElement.addContent(new Element("speed").setText("" + airpla.getSpeed()));
            //   airplnElement.addContent(new Element("name",
            //        Namespace.getNamespace("Что происходит? ")).setText(airpla.getName()));
            //    airplnElement.addContent(new Element ("Weight =" + airpla.getWeight() + " Range =" + airpla.getRange()));
            airplnElement.addContent(new Element("weight").setText(" Weight =" + airpla.getWeight() + " Range =" + airpla.getRange()));
            airplnElement.addContent(new Element("fuel").setText("" + airpla.getFuel()));
            airplnElement.addContent(new Element("tank").setText("" + airpla.getTank()));
            airplnElement.addContent(new Element("passenger").setText("" + airpla.getPassenger()));
            airplnElement.addContent(new Element("passenger_seat").setText("" + airpla.getPassenger_seat()));
            airplnElement.addContent(new Element("range").setText("" + airpla.getRange()));
            doc.getRootElement().addContent(airplnElement);
        }
        // Документ JDOM сформирован и готов к записи в файл
        XMLOutputter xmlWriter = new XMLOutputter(Format.getPrettyFormat());
        // сохнаряем в файл
        xmlWriter.output(doc, new FileOutputStream(fileName));

    }
    //записываем определенный объект

    public static void JDOMtest2(HashMap<Integer, outAP> currentjet, String fileName) throws IOException {
        Document doc = new Document();
        doc.setRootElement(new Element("AirPlaner"));
        Element airplnElement = new Element(currentjet.get(bort_index).name);

        airplnElement.addContent(new Element("number").setText("" + currentjet.get(bort_index).number));
        airplnElement.addContent(new Element("speed").setText("" + currentjet.get(bort_index).speed));
        airplnElement.addContent(new Element("range").setText("" + currentjet.get(bort_index).range));
        airplnElement.addContent(new Element("weight").setText("" + currentjet.get(bort_index).weight));
        airplnElement.addContent(new Element("fuel").setText("" + currentjet.get(bort_index).fuel));
        airplnElement.addContent(new Element("tank").setText("" + currentjet.get(bort_index).tank));
        airplnElement.addContent(new Element("passenger").setText("" + currentjet.get(bort_index).passenger));
        airplnElement.addContent(new Element("passenger_seat").setText("" + currentjet.get(bort_index).passenger_seat));
        airplnElement.addContent(new Element("FlyCost").setText("" + currentjet.get(bort_index).flyCost));

        doc.getRootElement().addContent(airplnElement);

        XMLOutputter xmlWriter = new XMLOutputter(Format.getPrettyFormat());
        xmlWriter.output(doc, new FileOutputStream(fileName));
    }

}
