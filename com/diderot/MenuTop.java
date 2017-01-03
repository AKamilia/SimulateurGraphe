package com.diderot;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.diderot.Main.canvas;

class MenuTop extends MenuBar {
	static int choice;

	
	MenuTop(){
		Menu File = new Menu("Fichier");
		Menu algo = new Menu("Algorithmes");
		Menu exemples = new Menu ("Exemples Graphes");
		
		getMenus().addAll(File,algo, exemples);
				    
		MenuItem ex1 = new MenuItem ("Graphe 1");
		MenuItem ex2 = new MenuItem ("Graphe 2");
		MenuItem ex3 = new MenuItem ("Graphe 3");
		
		MenuItem longestWay = new MenuItem("LongestWay");
		MenuItem longestWayWeight = new MenuItem("LongestWayWeight");
		MenuItem minimumWeight = new MenuItem("MinimumWeight");
		
		MenuItem new_item = new MenuItem("Sauvegarder");
        MenuItem open_item = new MenuItem("Ouvrir");
        MenuItem clear_item = new MenuItem("Effacer");
		MenuItem quit_item = new MenuItem("Quitter");
		
		algo.getItems().addAll(longestWay, longestWayWeight, minimumWeight);
		exemples.getItems().addAll(ex1, ex2, ex3);
		File.getItems().addAll(new_item, open_item,clear_item, quit_item);

        new_item.setOnAction(event -> {
            List<Shape> choices = new ArrayList<>();

            for (int i = 0; i < canvas.getChildren().size(); i++) {
                if (canvas.getChildren().get(i) instanceof GLine) continue;
                if (canvas.getChildren().get(i) instanceof GLabel) continue;
                choices.add((Shape) canvas.getChildren().get(i));
            }

            try {
                writeXml(choices);
            } catch (ParserConfigurationException | TransformerException e) {
                e.printStackTrace();
            }
        });

        clear_item.setOnAction(event -> { canvas.getChildren().clear();});
        quit_item.setOnAction(event -> {System.exit(0);});

        longestWayWeight.setOnAction(t -> choice=0);
        longestWay.setOnAction(t -> choice=1);
        minimumWeight.setOnAction(t -> choice=2);
        
        
        open_item.setOnAction(event -> {
            importXML("exportation.xml");
        });

        ex1.setOnAction(event -> {
            importXML("exportation1.xml");
        });
        
        ex2.setOnAction(event -> {
            importXML("exportation2.xml");
        });
        
        ex3.setOnAction(event -> {
            importXML("exportation3.xml");
        });
	}

    private void importXML(String s) {
        File fXmlFile = new File(s);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;

        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        try {
            assert dBuilder != null;
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            drawNodes(doc);
            drawLinks(doc);

        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    private void drawLinks(Document doc) {
        NodeList nList = doc.getElementsByTagName("link");
        for (int i = 0; i < nList.getLength(); i++) { extractLink((Element) nList.item(i)); }
    }

    private void drawNodes(Document doc) {
        NodeList nList = doc.getElementsByTagName("node");
        for (int i = 0; i < nList.getLength(); i++) { extractNode((Element) nList.item(i)); }
    }

    private void extractNode(Element element) {
        String id, type;
        double x, y, r, g, b, brightness;
        int weight;

        id = element.getAttribute("id");
        type = element.getAttribute("type");

        x = Double.parseDouble(element.getAttribute("x"));
        y = Double.parseDouble(element.getAttribute("y"));

        r = Double.parseDouble(element.getAttribute("r"));
        g = Double.parseDouble(element.getAttribute("g"));
        b = Double.parseDouble(element.getAttribute("b"));

        weight = Integer.parseInt(element.getAttribute("weight"));
        brightness = 1.0;

        if (type.equals("class com.diderot.GCircle")) {
            createAndDrawCircle(id, x, y, r, g, b, brightness, weight);
        }

        if (type.equals("class com.diderot.GRectangle")) {
            createAndDrawRectangle(id, x, y, r, g, b, brightness, weight);
        }
    }

    private void extractLink(Element element) {
        String attr_1, attr_2, attr_3, attr_4, attr_5;

        attr_1 = element.getAttribute("from");
        attr_2 = element.getAttribute("fromType");
        attr_3 = element.getAttribute("to");
        attr_4 = element.getAttribute("toType");
        attr_5 = element.getAttribute("weight");

        Shape shape_1 = findShapeFromLink(attr_1, attr_2);
        Shape shape_2 = findShapeFromLink(attr_3, attr_4);

        GLine line = new GLine(shape_1, shape_2, Integer.parseInt(attr_5));
        canvas.getChildren().add(line);
    }

    private void createAndDrawRectangle(String id, double x, double y, double r, double g, double b, double brightness, int weight) {
        Graphable currentShape = new GRectangle(x, y, weight);
        currentShape.setCurrentNumber(Integer.parseInt(id));
        ((Colorable) currentShape).setColor(new Color(r, g, b, brightness));
        canvas.getChildren().add((javafx.scene.Node) currentShape);
    }

    private void createAndDrawCircle(String id, double x, double y, double r, double g, double b, double brightness, int weight) {
        Graphable currentShape = new GCircle(x, y, weight);
        currentShape.setCurrentNumber(Integer.parseInt(id));
        ((Colorable) currentShape).setColor(new Color(r, g, b, brightness));
        canvas.getChildren().add((javafx.scene.Node) currentShape);
    }

    private Shape findShapeFromLink(String id, String type) {
        Graphable currentShape;
        boolean condition_1, condition_2;

        for (int i = 0; i < canvas.getChildren().size(); i++) {
            if (canvas.getChildren().get(i) instanceof GLabel) continue;
            if (canvas.getChildren().get(i) instanceof GLine)  continue;

            currentShape = (Graphable) canvas.getChildren().get(i);

            condition_1 = currentShape.getClass().toString().equals(type);
            condition_2 = currentShape.getCurrentNumber() == Integer.parseInt(id);

            if (condition_1 && condition_2) {
                return (Shape) currentShape;
            }
        }

        return null;
    }

    private void writeXml(List<Shape> choices) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document doc = docBuilder.newDocument();

        Element rootElement = doc.createElement("root");
        Element nodesElement = doc.createElement("nodes");
        Element linksElement = doc.createElement("links");

        doc.appendChild(rootElement);

        for (Shape choice : choices) {
            nodesElement.appendChild(createNodeWith(choice, doc));
        }

        for (GLine line : getLines()) {
            linksElement.appendChild(CreateLinkWith(line, doc));
        }

        rootElement.appendChild(nodesElement);
        rootElement.appendChild(linksElement);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;

        try {
            transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("exportation.xml"));

        assert transformer != null;
        transformer.transform(source, result);
        System.out.println("Fichier sauvegardé !");
    }

    private Node CreateLinkWith(GLine line, Document doc) {
        Element node = doc.createElement("link");

        Attr sub_attr_0 = doc.createAttribute("from");
        Attr sub_attr_1 = doc.createAttribute("fromType");
        Attr sub_attr_2 = doc.createAttribute("to");
        Attr sub_attr_3 = doc.createAttribute("toType");
        Attr sub_attr_4 = doc.createAttribute("weight");

        sub_attr_0.setValue(String.valueOf(((Graphable) line.getShape_1()).getCurrentNumber()));
        sub_attr_1.setValue(line.getShape_1().getClass().toString());
        sub_attr_2.setValue(String.valueOf(((Graphable) line.getShape_2()).getCurrentNumber()));
        sub_attr_3.setValue(line.getShape_2().getClass().toString());
        sub_attr_4.setValue(String.valueOf(line.getWeight()));

        node.setAttributeNode(sub_attr_0);
        node.setAttributeNode(sub_attr_1);
        node.setAttributeNode(sub_attr_2);
        node.setAttributeNode(sub_attr_3);
        node.setAttributeNode(sub_attr_4);

        return node;
    }

    private Node createNodeWith(Shape choice, Document doc) {
        Element node = doc.createElement("node");

        Attr attr_0 = doc.createAttribute("type");
        Attr attr_1 = doc.createAttribute("id");
        Attr attr_2 = doc.createAttribute("x");
        Attr attr_3 = doc.createAttribute("y");
        Attr attr_4 = doc.createAttribute("r");
        Attr attr_5 = doc.createAttribute("g");
        Attr attr_6 = doc.createAttribute("b");
        Attr attr_7 = doc.createAttribute("brightness");
        Attr attr_8 = doc.createAttribute("weight");

        attr_0.setValue(choice.getClass().toString());
        attr_1.setValue(String.valueOf(((Graphable) choice).getCurrentNumber()));
        attr_2.setValue(String.valueOf(((Graphable) choice).getCentreX()));
        attr_3.setValue(String.valueOf(((Graphable) choice).getCentreY()));

        attr_4.setValue(String.valueOf(((Colorable) choice).getColor().getRed()));
        attr_5.setValue(String.valueOf(((Colorable) choice).getColor().getGreen()));
        attr_6.setValue(String.valueOf(((Colorable) choice).getColor().getBlue()));
        attr_7.setValue(String.valueOf(1));
        attr_8.setValue(String.valueOf(((Graphable) choice).getWeight()));

        node.setAttributeNode(attr_0);
        node.setAttributeNode(attr_1);
        node.setAttributeNode(attr_2);
        node.setAttributeNode(attr_3);
        node.setAttributeNode(attr_4);
        node.setAttributeNode(attr_5);
        node.setAttributeNode(attr_6);
        node.setAttributeNode(attr_7);
        node.setAttributeNode(attr_8);

        return node;
    }

    private List<GLine> getLines() {
        List <GLine> list = new ArrayList<>();
        for (int i = 0; i < canvas.getChildren().size(); i++) {
            if (!(canvas.getChildren().get(i) instanceof GLine)) continue;
            list.add((GLine) canvas.getChildren().get(i));
            System.out.println("line");
        }

        return list;
    }
}

