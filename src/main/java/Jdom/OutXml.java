package Jdom;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zeit on 22.11.2016.
 */
public class OutXml {
/*
    public static String fileName = "airpln.xml";


    public static void outMet() throws IOException {

            try {
                Document jdomDocument = OutXml.createJDOMusingSAXParser(OutXml.fileName);
                Element root = jdomDocument.getRootElement();
                // получаем список всех элементов AP
                List<Element> apList = root.getChildren("Humpbacked-Horse_938");
                // список объектов AP, в которых будем хранить
                // считанные данные по каждому элементу
                List<outAP> outavia = new ArrayList<>();
                for (Element aplist : apList) {
                    outAP ap = new outAP();
                    ap.setName(aplist.getChildText("name"));
                    ap.setSpeed(Integer.parseInt(aplist.getChildText("speed")));
                    ap.setRange(Integer.parseInt(aplist.getChildText("range")));


                    outavia.add(ap);
                }
                // печатаем полученный список объектов AP
                for (outAP ap : outavia) {
                    System.out.println(ap);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//Используем SAX
        public static Document createJDOMusingSAXParser (String fileName) throws JDOMException, IOException {
            SAXBuilder saxBuilder = new SAXBuilder();
            return saxBuilder.build(new File(fileName));
        }*/
       }
