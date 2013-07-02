package de.hsanhalt.inf.studiappkoethen.xml.parsing;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import android.content.res.AssetManager;
import android.util.Log;
import de.hsanhalt.inf.studiappkoethen.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Diese Klasse uebernimmt das Hauptparsen der XML's und ordnet die Nodes den richtigen
 * Klassen zu. Die Klassen muessen XmlParser implementiert und sich hier registriert haben.
 */
public class DefaultXmlParser
{
    private static DefaultXmlParser INSTANCE;

    private Set<XmlParser> xmlParserSet;
    private AssetManager assets;

    /**
     * Gibt eine Instanz dieser Klasse zurueck
     */
    public static DefaultXmlParser getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new DefaultXmlParser();
        }
        return INSTANCE;
    }

    /**
     * prueft ob diese Klasse bereits erstellt wurde.
     */
    public static boolean isCreated()
    {
        return INSTANCE != null;
    }

    private DefaultXmlParser()
    {
        xmlParserSet = new HashSet<>(5);
    }

    /**
     * registriert Klasse.
     * @param parser
     */
    public void registerXmlParser(XmlParser parser)
    {
        this.xmlParserSet.add(parser);
    }

    /**
     * Setzt den AssetManager
     */
    public void setAssets(AssetManager assets)
    {
        this.assets = assets;
    }

    /**
     * "Installiert" die xml-Dateien aus dem xml Ordner in den Assets.
     */
    public void install()
    {
        PriorityQueue<Node> nodePriorityQueue = new PriorityQueue<>(5, new NodeComparator());

        String[] fileList;
        List<String> folder = new ArrayList<>(1);
        folder.add("xml");

        while (!folder.isEmpty())
        {
            Iterator<String> iter = folder.iterator();
            while (iter.hasNext())
            {
                String currentFolder = iter.next();
                iter.remove();
                try
                {
                    fileList = this.assets.list(currentFolder);
                }
                catch (IOException e)
                {
                    Log.e("XmlParserError", "Can't list all files in " + currentFolder + " assets folder.");
                    continue;
                }
                for (String file : fileList)
                {
                    file = currentFolder + "/" + file;
                    String extension = StringUtils.getFileExtension(file);
                    if (extension.equals(""))
                    {
                        folder.add(file);
                    }
                    else if (extension.equals("xml"))
                    {
                        try
                        {
                            Log.d("XmlParserDebug", "Loads xml-file: " + file);
                            nodePriorityQueue.add(this.getRootNode(this.assets.open(file)));
                        }
                        catch (IOException e)
                        {
                            Log.e("XmlParserError", "Can't load the file: " + file);
                            continue;
                        }
                    }
                }
            }
        }

        while(!nodePriorityQueue.isEmpty())
        {
            this.parseNode(nodePriorityQueue.poll());
        }
    }

    private void parseNode(Node node)
    {
        for(XmlParser xmlParser : this.xmlParserSet)
        {
            if(xmlParser.getStartTag().equals(node.getNodeName()))
            {
                try
                {
                    Log.d("XmlParserDebug", "trying to parse " + node.getNodeName());
                    xmlParser.addNode(node);
                    Log.d("XmlParserDebug", "successfully parsed " + node.getNodeName());
                }
                catch (XmlParseException e)
                {
                    Log.e("XmlParserError", "XmlParseException", e);
                }
            }
        }
    }

    private Node getRootNode(InputStream stream)
    {
        Node node = null;
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(stream);

            NodeList list = document.getChildNodes();

            for (int i = 0; i < list.getLength(); i++)
            {
                node = list.item(i);
            }
            stream.close();
        }
        catch (Exception e)
        {
            Log.e("XMLFileError", "Can't load the xml-file", e);
        }
        return node;
    }

    private class NodeComparator implements Comparator<Node>
    {
        @Override
        public int compare(Node firstNode, Node secondNode)
        {
            int firstPriority = 0;
            int secondPriority = 0;

            NamedNodeMap attributes = firstNode.getAttributes();
            Node priorityNode = attributes.getNamedItem("priority");
            if(priorityNode != null)
            {
                firstPriority = Integer.valueOf(priorityNode.getTextContent());
            }

            attributes = secondNode.getAttributes();
            priorityNode = attributes.getNamedItem("priority");
            if(priorityNode != null)
            {
                secondPriority = Integer.valueOf(priorityNode.getTextContent());
            }

            return Integer.valueOf(secondPriority).compareTo(firstPriority);
        }
    }
}
