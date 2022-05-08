import javax.xml.parsers.*;
import org.w3c.dom.*;

public class DataStorage {

    Document dom;

    final String PLAYER_TAG_NAME = "player";
    final String NAME_TAG_NAME = "name";
    final String DEADWOOD_TAG_NAME = "deadwoodScore";
    final String SCORE_TAG_NAME = "score";
    final String ID_TAG_NAME = "playerId";

    public DataStorage(String filename) {
        dom = createDOM(filename);
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

    public void setPlayerName(String id, String name) {
        Element docElement = dom.getDocumentElement();
        Element player = findPlayerElement(docElement, id);
        if (player == null) {
            System.out.println("Player id not in XML DOM.");
        } else {
            System.out.println("Found player id in XML DOM");
            System.out.println(player.getElementsByTagName(NAME_TAG_NAME).item(0));
            player.getElementsByTagName(NAME_TAG_NAME).item(0).setTextContent(name);
            System.out.println(player.getElementsByTagName(NAME_TAG_NAME).item(0).getTextContent());
        }
    }

    public void setPlayerDeadwood(String id, int deadwood) {
        setScore(id, deadwood, DEADWOOD_TAG_NAME);
    }

    public void setPlayerScore(String id, int score) {
        setScore(id, score, SCORE_TAG_NAME);
    }

    private void setScore(String id, int score, String score_tag_name) {
        Element docElement = dom.getDocumentElement();
        Element player = findPlayerElement(docElement, id);
        if (player == null) {
            System.out.println("Player id not in XML DOM.");
        } else {
            System.out.println("Found player id in XML DOM");
            System.out.println(player.getElementsByTagName(score_tag_name).item(0));
            player.getElementsByTagName(score_tag_name).item(0).setTextContent(Integer.toString(score));
            System.out.println(player.getElementsByTagName(score_tag_name).item(0).getTextContent());
        }
    }


    private Element findPlayerElement(Element docElement, String playerId) {
        NodeList nodes = docElement.getElementsByTagName(PLAYER_TAG_NAME);
        Element playerNode = findElement(nodes, ID_TAG_NAME, playerId);
        return playerNode;
    }

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



}
