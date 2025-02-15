package utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class XmlUtil {

    // Define the file path here
    private static final String FILE_PATH = System.getProperty("user.dir") + "/allure-results/environment.xml";

    public static void addInfoToXML(String banca, String filiale) throws Exception {
        Document doc = loadXMLDocument(FILE_PATH);

        // Get the root element
        Element root = doc.getDocumentElement();

        // Add new parameters
        addParameter(doc, root, "Banca", banca);
        addParameter(doc, root, "Filiale", filiale);

        // Save the updated document
        saveXMLDocument(doc, FILE_PATH);
    }

    private static Document loadXMLDocument(String filePath) throws Exception {
        File file = new File(filePath);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        return documentBuilder.parse(file);
    }

    private static void addParameter(Document doc, Element root, String key, String value) {
        Element parameter = doc.createElement("parameter");

        Element keyElement = doc.createElement("key");
        keyElement.appendChild(doc.createTextNode(key));
        parameter.appendChild(keyElement);

        Element valueElement = doc.createElement("value");
        valueElement.appendChild(doc.createTextNode(value));
        parameter.appendChild(valueElement);

        root.appendChild(parameter);
    }

    private static void saveXMLDocument(Document doc, String filePath) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(filePath));
        transformer.transform(source, result);
    }

    public static void createEnvironmentXml(String banca, String filiale) {
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

            // Define root elements
            Document document = documentBuilder.newDocument();
            Element root = document.createElement("environment");
            document.appendChild(root);

            // Create the parameter elements
            root.appendChild(createParameterElement(document, "Banca", banca));
            root.appendChild(createParameterElement(document, "Filiale", filiale));

            // Create the XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File("environment.xml"));

            transformer.transform(domSource, streamResult);

            System.out.println("environment.xml file created successfully!");

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private static Element createParameterElement(Document document, String key, String value) {
        Element parameter = document.createElement("parameter");

        Element keyElement = document.createElement("key");
        keyElement.appendChild(document.createTextNode(key));
        parameter.appendChild(keyElement);

        Element valueElement = document.createElement("value");
        valueElement.appendChild(document.createTextNode(value));
        parameter.appendChild(valueElement);

        return parameter;
    }

    public static void mergeXmlFiles() {
        String mainFilePath = System.getProperty("user.dir") + "/allure-results/environment.xml";
        String secondaryFilePath = "environment.xml";
        String outputFilePath = System.getProperty("user.dir") + "/allure-results/environment.xml";

        try {
            // Load the main XML file
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document mainDoc = documentBuilder.parse(new File(mainFilePath));

            // Load the secondary XML file
            Document secondaryDoc = documentBuilder.parse(new File(secondaryFilePath));

            // Get the root element of the main document
            Element mainRoot = mainDoc.getDocumentElement();

            // Get the parameter nodes from the secondary document
            NodeList parameters = secondaryDoc.getDocumentElement().getElementsByTagName("parameter");

            // Import and append the parameter nodes to the main document
            for (int i = 0; i < parameters.getLength(); i++) {
                Node parameter = parameters.item(i);
                Node importedParameter = mainDoc.importNode(parameter, true);
                mainRoot.appendChild(importedParameter);
            }

            // Write the merged XML to the output file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(mainDoc);
            StreamResult streamResult = new StreamResult(new File(outputFilePath));

            transformer.transform(domSource, streamResult);

            System.out.println("Merged environment.xml file created successfully!");

        } catch (ParserConfigurationException | IOException | TransformerException | org.xml.sax.SAXException e) {
            e.printStackTrace();
        }
    }
}
