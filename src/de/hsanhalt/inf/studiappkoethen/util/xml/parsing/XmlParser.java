package de.hsanhalt.inf.studiappkoethen.util.xml.parsing;

import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import android.content.res.AssetManager;
import android.util.Log;
import de.hsanhalt.inf.studiappkoethen.util.xml.buildings.BuildingCategoryManager;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlParser
{
    private static XmlParser INSTANCE;
    private AssetManager assets;

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

    public void setAssets(AssetManager assets)
    {
        this.assets = assets;
    }

    public void install()
    {
        try
        {
            this.parse(this.assets.open("xml/categories.xml"));
        }
        catch (IOException e)
        {
            Log.e("FileException", "Can't parse file categories.xml from assets", e);
        }

//        try
//        {
//            for(String file : this.assets.list("xml"))
//            {
//                Log.d("Filename: ", file);
//            }
//        }
//        catch (Exception e)
//        {
//            Log.e("AssetsError", "Can't list asset files", e);
//        }

//        TODO alle xml-Dateien laden.
    }

    private enum XMLClasses
    {
        CATEGORIES("categories", BuildingCategoryManager.getInstance());

        private String tagName;
        private IXmlParsing instance;

        XMLClasses(String tagName, IXmlParsing instance)
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
