package utilities;

import board.Board;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import utilities.exception.NoXMLResponseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class XMLReader {
    private String filePath = "data/library.xml";
    private File libraryFile;
    private NodeList languages;
    private static Logger logger;
    private String languageName;


    public XMLReader(){
        logger = LogManager.getLogger(XMLReader.class.getName());   //Trace level information, separately is to call you when you started in a method or program logic, and logger.trace ("entry") basic a meaning
        PropertiesReader properties = new PropertiesReader();
        languageName = properties.getLanguage();
        try{
            this.libraryFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(this.libraryFile);
            doc.getDocumentElement().normalize();
            languages = doc.getElementsByTagName("language");
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public String getStart(String language) throws NoXMLResponseException {
        for (int temp = 0; temp < languages.getLength(); temp++) {
            Node nNode = languages.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                if(eElement.getAttribute("name").equals(language)){
                    logWord(eElement.getElementsByTagName("start").item(0).getTextContent());

                    return eElement.getElementsByTagName("start").item(0).getTextContent();
                }

            }
        }
        throw new NoXMLResponseException();
    }
    public String getExit(String language){
        for (int temp = 0; temp < languages.getLength(); temp++) {
            Node nNode = languages.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                if(eElement.getAttribute("name").equals(language)){
                    logWord(eElement.getElementsByTagName("exit").item(0).getTextContent());

                    return eElement.getElementsByTagName("exit").item(0).getTextContent();
                }

            }
        }
        return "languageERROR";
    }
    public String getWord(String word){
        for (int temp = 0; temp < languages.getLength(); temp++) {
            Node nNode = languages.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                if(eElement.getAttribute("name").equals(languageName)){
                    logWord(eElement.getElementsByTagName(word).item(0).getTextContent());

                    return eElement.getElementsByTagName(word).item(0).getTextContent();
                }

            }
        }
        return "languageERROR";
    }

    public void logWord(String word){
        logger.info("Selected word in correct language from library: " + word);

    }




//    try {
//        File inputFile = new File("input.txt");
//        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//        Document doc = dBuilder.parse(inputFile);
//        doc.getDocumentElement().normalize();
//        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
//        NodeList nList = doc.getElementsByTagName("student");
//        System.out.println("----------------------------");
//
//        for (int temp = 0; temp < nList.getLength(); temp++) {
//            Node nNode = nList.item(temp);
//            System.out.println("\nCurrent Element :" + nNode.getNodeName());
//
//            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
//                Element eElement = (Element) nNode;
//                System.out.println("Student roll no : "
//                        + eElement.getAttribute("rollno"));
//                System.out.println("First Name : "
//                        + eElement
//                        .getElementsByTagName("firstname")
//                        .item(0)
//                        .getTextContent());
//                System.out.println("Last Name : "
//                        + eElement
//                        .getElementsByTagName("lastname")
//                        .item(0)
//                        .getTextContent());
//                System.out.println("Nick Name : "
//                        + eElement
//                        .getElementsByTagName("nickname")
//                        .item(0)
//                        .getTextContent());
//                System.out.println("Marks : "
//                        + eElement
//                        .getElementsByTagName("marks")
//                        .item(0)
//                        .getTextContent());
//            }
//        }
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
}
