package people;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XPathTestAdvance {

    Document doc;
    XPath xpath;

    public static void main(String[] args) throws ParserConfigurationException, SAXException,
            IOException, XPathExpressionException {

        XPathTestAdvance test = new XPathTestAdvance();
        test.loadXML();

        test.printAllPeople();
        test.printHealthProfilebyID(5);
        test.printPeopleFilteredByWeight("90", ">");

    }

    public Node getWeightbyID(int personID) throws XPathExpressionException {
        if (personID > 9999) {
            System.err.println("personID not supported!");
            return null;
        }
        String number = "";
        if (personID < 10) {
            number = "000" + Integer.toString(personID);
        } else if (personID >= 10 && personID < 100) {
            number = "00" + Integer.toString(personID);
        } else if (personID >= 100 && personID < 1000) {
            number = "0" + Integer.toString(personID);
        } else {
            number = Integer.toString(personID);
        }

        XPathExpression expr = xpath.compile("/people/person[@id='" + number + "']/healthprofile/weight");
        Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);
        return node;
    }

    public Node getHeightbyID(int personID) throws XPathExpressionException {
        if (personID > 9999) {
            System.err.println("personID not supported!");
            return null;
        }
        String number = "";
        if (personID < 10) {
            number = "000" + Integer.toString(personID);
        } else if (personID >= 10 && personID < 100) {
            number = "00" + Integer.toString(personID);
        } else if (personID >= 100 && personID < 1000) {
            number = "0" + Integer.toString(personID);
        } else {
            number = Integer.toString(personID);
        }

        XPathExpression expr = xpath.compile("/people/person[@id='" + number + "']/healthprofile/height");
        Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);
        return node;
    }

    public void printAllPeople() throws XPathExpressionException {
        System.out.println("PRINTING ALL PEOPLE");
        XPathExpression expr = xpath.compile("//person");
        Object result = expr.evaluate(doc, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;
        for (int i = 0; i < nodes.getLength(); i++) {
            Node item = nodes.item(i);
            //System.out.println(item.getNodeName() + ": " + item.getTextContent());
            printNode(item);
        }
        System.out.println("ALL PEOPLE PRINTED\n");
    }

    public void printHealthProfilebyID(int personID) throws XPathExpressionException {
        if (personID > 9999) {
            System.err.println("personID not supported!");
            return;
        }
        String number = "";
        if (personID < 10) {
            number = "000" + Integer.toString(personID);
        } else if (personID >= 10 && personID < 100) {
            number = "00" + Integer.toString(personID);
        } else if (personID >= 100 && personID < 1000) {
            number = "0" + Integer.toString(personID);
        } else {
            number = Integer.toString(personID);
        }

        XPathExpression expr = xpath.compile("/people/person[@id='" + number + "']/healthprofile");
        Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);
        System.out.println("PRINTING HEALTHPROFILE OF PERSON WITH ID=" + personID);
        printNode(node);
        System.out.println("HEALTHPROFILE OF PERSON WITH ID=" + personID + " PRINTED\n");
    }

    public void loadXML() throws ParserConfigurationException, SAXException, IOException {

        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(true);
        DocumentBuilder builder = domFactory.newDocumentBuilder();
        doc = builder.parse("people.xml");

        //creating xpath object
        getXPathObj();
    }

    public XPath getXPathObj() {

        XPathFactory factory = XPathFactory.newInstance();
        xpath = factory.newXPath();
        return xpath;
    }

    public void printPeopleFilteredByWeight(String weight, String operator) throws XPathExpressionException {
        try {
            Float w = Float.parseFloat(weight);
        } catch (NumberFormatException e) {
            System.err.println("You should provide a valid weigth operand!");
            return;
        }
        switch (operator) {
            case "<":
                break;
            case ">":
                break;
            case "=":
                break;
            default:
                System.err.println("You should provide a valid comparator operand! (Supported are:  '=' '<' '>')");
                break;
        }
        System.out.println("PERSONS WHOSE WEIGHTS IS " + operator + " " + weight);
        XPathExpression expr = xpath.compile("/people/person/healthprofile[weight " + operator + " " + weight + "]/ancestor::person");
        NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); i++) {
            Node item = nodes.item(i);
            //System.out.println(item.getNodeName() + ": " + item.getTextContent());
            printNode(item);
        }
        System.out.println("END PRINTING PERSONS WHOSE WEIGHTS IS " + operator + " " + weight + "\n");
    }

    public void printNode(Node node) throws XPathExpressionException {
        System.out.println(node.getNodeName());
        NodeList childNodes = node.getChildNodes();
        XPathExpression expr = xpath.compile("/*");
        NodeList evaluate = (NodeList) expr.evaluate(node, XPathConstants.NODE);
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            if (!(item.getNodeName() == "#text")) {
                System.out.println(item.getNodeName() + ": " + item.getTextContent());
            }
        }
    }
}
