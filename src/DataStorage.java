import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.io.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

public class DataStorage {

    Document dom;

    final String PLAYER_OUTPUT_FILENAME = "players_output.xml";

    final String PLAYER_TAG_NAME = "player";
    final String NAME_TAG_NAME = "name";
    final String DEADWOOD_TAG_NAME = "deadwoodScore";
    final String SCORE_TAG_NAME = "score";
    final String ID_TAG_NAME = "playerId";

    public DataStorage(String filename) {
        dom = createDOM(filename);
        saveDOMtoXML();
    }

    //Purpose: Create XML DOM object
    //Assumptions: filename is in project folder
    //Inputs filename: name of XML file
    //Post-conditions: Created an XML DOM object for filename
    private Document createDOM(String filename) {
        Document dom;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            dom = db.parse(filename);
        } catch (Exception e) {
            System.out.println(e);
            dom = null;
        }
        return dom;
    }


    //Purpose: Set the text content of the player tag
    //Assumptions: None
    //Inputs: id: id of the player
    //        name: name to be set in the DOM
    //Post-conditions: DOM is updated. Name tag has new text content
    public void setPlayerName(String id, String name) {
        Element docElement = dom.getDocumentElement();
        Element player = findPlayerElement(docElement, id);
        if (player == null) {
            System.out.println("Player id not in XML DOM.");
        } else {
            System.out.println("Found player id in XML DOM");
            player.getElementsByTagName(NAME_TAG_NAME).item(0).setTextContent(name);
            System.out.println("Name: " + player.getElementsByTagName(NAME_TAG_NAME).item(0).getTextContent());
        }
    }


    //Purpose: Set the deadwood of a player in the DOM
    //Assumptions: None
    //Inputs: id: id of the player
    //        deadwood: deadwood to be added to the DOM
    //Post-conditions: DOM is updated. deadwood tag has new text content
    public void setPlayerDeadwood(String id, int deadwood) {
        setScore(id, deadwood, DEADWOOD_TAG_NAME);
    }


    //Purpose: Set the score of a player in the DOM
    //Assumptions: None
    //Inputs: id: id of the player
    //        score: score to be added to the DOM
    //Post-conditions: DOM is updated. score tag has a new text content
    public void setPlayerScore(String id, int score) {
        setScore(id, score, SCORE_TAG_NAME);
    }


    //Purpose: Set the score of a player in the DOM (deadwood or score)
    //Assumptions: None
    //Inputs: id: id of the player
    //        score: score to be added to the DOM
    //        score_tag_name: either deadwood or score
    //Post-conditions: DOM is updated
    private void setScore(String id, int score, String score_tag_name) {
        Element docElement = dom.getDocumentElement();
        Element player = findPlayerElement(docElement, id);
        if (player == null) {
            System.out.println("Player id not in XML DOM.");
        } else {
            System.out.println("Found player id in XML DOM");
            player.getElementsByTagName(score_tag_name).item(0).setTextContent(Integer.toString(score));
            System.out.println(score_tag_name + ": " + player.getElementsByTagName(score_tag_name).item(0).getTextContent());
        }
    }



    //Purpose: Find a player node by looking for its id
    //Assumptions: None
    //Inputs: docElement: root node of the DOM
    //        playerID: text in playerid tag
    //Post-conditions: returns a contact node object if player is found, else null
    private Element findPlayerElement(Element docElement, String playerId) {
        NodeList nodes = docElement.getElementsByTagName(PLAYER_TAG_NAME);
        Element playerNode = findElement(nodes, ID_TAG_NAME, playerId);
        return playerNode;
    }


    //Purpose: Find an element in the DOM
    //Assumptions: None
    //Inputs: nodes: list of sibling nodes with same tag name
    //        tagName, tagValue: child tags within one of the sibling nodes
    //Post-conditions: Returns the sibling node object, null otherwise
    private Element findElement(NodeList nodes, String tagName, String tagValue)
    {
        Element element = null;
        Element siblingNode;
        Node childNode;
        if (nodes != null)
        {
            for (int idx=0; idx < nodes.getLength() && element == null; idx++)
            {
                siblingNode = (Element)nodes.item(idx);
                childNode = findChildElement(siblingNode, tagName, tagValue);
                if (childNode != null)
                    //Found the tagName/tagValue within this sibling node.
                    element = siblingNode;
            }
        }
        return element;
    }


    //Purpose: Find the child element within a parent node
    //Assumptions: None
    //Inputs: parent node: node that has child nodes
    //        tagName, tagValue: child tags within the parent node
    //Post-conditions: Returns child node matching the tag names and values, or null
    private Node findChildElement(Element parentNode, String tagName, String tagValue)
    {
        Node childNode = null;
        Node tempNode;
        if (parentNode.hasChildNodes())
        {
            tempNode = parentNode.getFirstChild();
            while (tempNode != null && childNode == null)
            {
                if (tempNode.getNodeType() == Node.ELEMENT_NODE &&
                        tagName.equalsIgnoreCase(tempNode.getNodeName()))
                {
                    if (tempNode.hasChildNodes())
                    {
                        tempNode = tempNode.getFirstChild();
                        if (tempNode.getNodeType() == Node.TEXT_NODE &&
                                tagValue.equalsIgnoreCase(tempNode.getNodeValue()))
                        {
                            childNode = tempNode;
                        }
                    }
                    //Done searching for the tagName.
                    tempNode = null;
                }
                else
                    //Continue searching for the tagName.
                    tempNode = tempNode.getNextSibling();
            }
        }
        return childNode;
    }


    //Purpose: Save current DOM to our specified XML file
    //Assumptions: XML file is in project directory
    //Inputs: None
    //Post-conditions: XML file contents have been updated
    public void saveDOMtoXML()
    {
        try
        {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(dom);
            StreamResult result = new StreamResult(new File(PLAYER_OUTPUT_FILENAME));
            transformer.transform(source, result);
        } catch (TransformerFactoryConfigurationError | TransformerException ignored)
        {
        }
    }



}
