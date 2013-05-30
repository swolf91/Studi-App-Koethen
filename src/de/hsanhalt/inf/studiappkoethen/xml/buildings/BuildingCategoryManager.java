package de.hsanhalt.inf.studiappkoethen.xml.buildings;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import android.util.Log;
import de.hsanhalt.inf.studiappkoethen.xml.parsing.IXmlParsing;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Diese Klasse verwaltet die Instanzen der BuildingCategory-Klasse.
 */
public final class BuildingCategoryManager implements IXmlParsing
{
    /**
     * beinhaltet die Instanz dieser Klasse
     */
    private static BuildingCategoryManager INSTANCE;
    /**
     * In dieser Map werden bzw sind alle Kategorien gespeichert
     */
    private Map<Byte, BuildingCategory> categoryMap;

    /**
     * Gibt eine Instanz dieser Klasse zurueck und sorgt dafuer, dass auch nur
     * eine Instanz erstellt wird! Ganz ala Singleton.
     */
    public static BuildingCategoryManager getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new BuildingCategoryManager();
        }
        return INSTANCE;
    }

    /**
     * Konstruktor erstellt eine leere Map, die später die Kategorien enthaelt.
     * Kann nicht von Aussen aufgerufen werden.
     */
    private BuildingCategoryManager()
    {
        this.categoryMap = new HashMap<Byte, BuildingCategory>();
    }

    /**
     * Gibt die Kategorie zurueck, die zur jeweiligen ID gehoert.
     * @param id
     * @return
     */
    public BuildingCategory getCategory(byte id)
    {
        return this.categoryMap.get(id);
    }

    /**
     * Gibt die Kategorie zurueck, die den jeweiligen Namen besitzt.
     * @param name
     * @return
     */
    public BuildingCategory getCategory(String name)
    {
//        TODO Diese Methode evtl umbauen (Levenstin Distance?)
        for (Entry<Byte, BuildingCategory> entry : this.categoryMap.entrySet())
        {
            BuildingCategory category = entry.getValue();
            if (category.getName().equals(name))
            {
                return category;
            }
        }
        return null;
    }

    @Override
    public void addNode(Node node)
    {
        if (!this.getStartTag().equals(node.getNodeName()))
        {
            return;
        }
        if (node.hasChildNodes())
        {
            NodeList nodes = node.getChildNodes();
            for (int i = 0; i < nodes.getLength(); i++)
            {
                Node subNode = nodes.item(i);
                if (subNode.getNodeName().equals("category"))
                {
                    this.addElement(subNode);
                }
            }
        }
    }

    @Override
    public String getStartTag()
    {
        return "buildingCategories";
    }

    /**
     * Fuegt das Node-Element zur Liste hinzu.
     * @param node
     * @return ob Element hinzugefuegt werden konnte oder nicht.
     */
    private boolean addElement(Node node)
    {
        if (!node.hasChildNodes())
        {
            return false;
        }

        byte id = -1;
        String name = null;
        String iconPath = null;

        NodeList nodeList = node.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++)
        {
            Node subNode = nodeList.item(i);
            if (subNode.getNodeName().equals("id"))
            {
                id = Byte.valueOf(subNode.getTextContent());
            }
            else if (subNode.getNodeName().equals("name"))
            {
                name = subNode.getTextContent();
            }
            else if(subNode.getNodeName().equals("setIcon"))
            {
                iconPath = "images/" + subNode.getTextContent();
            }
        }

        if (id == -1 || name == null)
        {
            return false;
        }

        BuildingCategory category = new BuildingCategory(id, name, iconPath);
        this.categoryMap.put(id, category);
        Log.d("BuildingCategoryManagerDebug", "Category was created: " + id + " " + name);

        return true;
    }

    /**
     * Gibt alle Instanzen der BuildingCategory-Klasse zurueck.
     * @return Kategorien
     */
    public BuildingCategory[] getBuildingCategories()
    {
        BuildingCategory[] buildingCategories = new BuildingCategory[this.categoryMap.size()];

        int i = 0;
        for(Entry<Byte, BuildingCategory> entry : this.categoryMap.entrySet())
        {
            buildingCategories[i++] = entry.getValue();
        }

        return buildingCategories;
    }
    /**
     * Holt die Anzahl der Kategorien.
     * @return Anzahl der Kategorieen
     */
    public int getSize()
    {
    	return this.categoryMap.size();
    }
    
}
