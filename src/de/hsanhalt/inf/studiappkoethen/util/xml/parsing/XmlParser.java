package de.hsanhalt.inf.studiappkoethen.util.xml.parsing;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import android.content.res.AssetManager;
import android.util.Log;
import de.hsanhalt.inf.studiappkoethen.util.StringUtils;
import de.hsanhalt.inf.studiappkoethen.util.xml.buildings.BuildingCategoryManager;
import de.hsanhalt.inf.studiappkoethen.util.xml.buildings.BuildingManager;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *  Diese Klasse uebernimmt das Hauptparsen der XML's und ordnet die Nodes den richtigen
 *  Klassen zu, die IXMLParsing implementiert haben muessen und in der XMLClasses registriert sind.
 */
public class XmlParser
{
    private static XmlParser INSTANCE;
    private AssetManager assets;

    /**
     * Gibt eine Instanz dieser Klasse zurueck
     * @return
     */
    public static XmlParser getInstance()
    {
        if(INSTANCE == null)
        {
            INSTANCE = new XmlParser();
        }
        return INSTANCE;
    }

    /**
     * prueft ob diese Klasse bereits erstellt wurde.
     * @return
     */
    public static boolean isCreated()
    {
        return INSTANCE != null;
    }

    private XmlParser()
    {}

    /**
     * parst den InputStream
     * @param stream
     */
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

    /**
     * Setzt den AssetManager
     * @param assets
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
        String[] fileList;
        List<String> folder = new ArrayList<String>(1);
        folder.add("xml");

        while(!folder.isEmpty())
        {
            Iterator<String> iter = folder.iterator();
            while(iter.hasNext())
            {
                String currentFolder = iter.next();
                iter.remove();
                try
                {
                    fileList = this.assets.list(currentFolder);
                }
                catch (IOException e)
                {
                    Log.e("XmlLoadError", "Can't list all files in " + currentFolder + " assets folder.");
                    continue;
                }
                for(String file : fileList)
                {
                    file = currentFolder + "/" + file;
                    String extension = StringUtils.getFileExtension(file);
                    if(extension.equals(""))
                    {
                        folder.add(file);
                    }
                    else if(extension.equals("xml"))
                    {
                        try
                        {
                            Log.d("XmlLoad", "Loads xml-file: " + file);
                            this.parse(this.assets.open(file));
                        }
                        catch (IOException e)
                        {
                            Log.e("XmlLoadError", "Can't load the file: " + file);
                            continue;
                        }
                    }
                }
            }
        }
    }

    /**
     * Aufzaehlung in der alle Klassen registriert werden muessen, die was aus der XML laden wollen
     */
    private enum XMLClasses
    {
        BUILDINGS(BuildingManager.getInstance()),
        CATEGORIES(BuildingCategoryManager.getInstance());

        private IXmlParsing instance;

        /**
         * @param instance - Instanz der Klasse, die IXmlParsing implementiert.
         */
        XMLClasses(IXmlParsing instance)
        {
            this.instance = instance;
        }

        public boolean matches(String tagName)
        {
            return this.instance.getStartTag().equals(tagName);
        }

        public void addNode(Node node)
        {
            this.instance.addNode(node);
        }
    }
}
