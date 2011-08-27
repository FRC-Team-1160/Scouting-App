package com.team1160.scouting.xml;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * This class parses the config.xml file and returns elements which are nodes of the xml tree.
 * @author Saketh Kasibatla
 */
public class XMLParser {
    /**
     * the name of the document to be parsed.
     */
    String documentName;

    /**
     * the file at location documentName
     */
    File documentFile;

    /**
     * the document which is parsed by the java parsers.
     */
    Document document;

    /**
     * creates a new XMLParser from the document name
     * @param documentName the name of the document to be read
     */
    public XMLParser(String documentName) throws ParserConfigurationException, SAXException, IOException {
            this.documentName = documentName;
            documentFile = new File(documentName);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            document=db.parse(documentFile);
            document.getDocumentElement().normalize();
    }

    /**
     * gets the match node of the xml document.
     * @return the match subnode in the xml document
     */
    public Element getMatch() {
        NodeList nodes =  document.getDocumentElement().getChildNodes();
        for(int i=0;i<nodes.getLength();i++){
            if(nodes.item(i).getNodeType()==Node.ELEMENT_NODE){
                Element e=(Element) nodes.item(i);
                if(e.getTagName().equals("match")){
                    return e;
                }
            }
        }

        throw new NullPointerException("no tag named match");
    }

    /**
     *
     * @return the pit subnode in the xml document
     */
    public Element getPit() {
        NodeList nodes =  document.getDocumentElement().getChildNodes();
        for(int i=0;i<nodes.getLength();i++){
            if(nodes.item(i).getNodeType()==Node.ELEMENT_NODE){
                Element e=(Element) nodes.item(i);
                if(e.getTagName().equals("pit")){
                    return e;
                }
            }
        }

        throw new NullPointerException("no tag named pit");
    }

    


}
