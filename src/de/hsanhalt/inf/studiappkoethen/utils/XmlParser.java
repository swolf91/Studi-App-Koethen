package de.hsanhalt.inf.studiappkoethen.utils;

import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import android.util.Log;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlParser
{
    private static XmlParser INSTANCE;

    public static XmlParser getInstance()
    {
        if(INSTANCE == null)
        {
            INSTANCE = new XmlParser();
        }
        return INSTANCE;
    }

    public static boolean isCreated()
    {
        return INSTANCE != null;
    }

    private XmlParser()
    {}

    public void parse(InputStream stream)
    {
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(stream);

            NodeList list = document.getChildNodes();

            for(int i = 0; i < list.getLength(); i++)
            {
                Node node = list.item(i);
                String name = node.getNodeName();

                for(XMLClasses classes : XMLClasses.values())
                {
                    if(classes.matches(name))
                    {
                        classes.addNode(node);
                        break;
                    }
                }
            }
            stream.close();
        }
        catch (Exception e)
        {
            Log.e("XMLFileError", "Can't load the xml-file", e);
        }
    }

    private enum XMLClasses
    {
        CATEGORIES("categories", BuildingCategoryManager.getInstance());

        private String tagName;
        private IXmlParser instance;

        XMLClasses(String tagName, IXmlParser instance)
        {
            this.tagName = tagName;
            this.instance = instance;
        }

        public boolean matches(String tagName)
        {
            return this.tagName.equals(tagName);
        }

        public void addNode(Node node)
        {
            this.instance.addNode(node);
        }
    }
}
