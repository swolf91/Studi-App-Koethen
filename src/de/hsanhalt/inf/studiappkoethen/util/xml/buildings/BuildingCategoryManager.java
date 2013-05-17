package de.hsanhalt.inf.studiappkoethen.util.xml.buildings;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import android.util.Log;
import de.hsanhalt.inf.studiappkoethen.util.xml.parsing.IXmlParsing;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class BuildingCategoryManager implements IXmlParsing
{
    private static BuildingCategoryManager INSTANCE;
    private Map<Byte, BuildingCategory> categoryMap;

    public static BuildingCategoryManager getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new BuildingCategoryManager();
        }
        return INSTANCE;
    }

    private BuildingCategoryManager()
    {
        this.categoryMap = new HashMap<Byte, BuildingCategory>();
    }

    public boolean hasContent()
    {
        return this.categoryMap != null && !this.categoryMap.isEmpty();
    }

    public BuildingCategory getCategory(byte id)
    {
        return this.categoryMap.get(id);
    }

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

    private boolean addElement(Node node)
    {
        if (!node.hasChildNodes())
        {
            return false;
        }

        byte id = -1;
        String name = null;

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
        }

        if (id == -1 || name == null)
        {
            return false;
        }

        BuildingCategory category = new BuildingCategory(id, name);
        this.categoryMap.put(id, category);
        Log.d("Building category created", "id: " + id + " name: " + name);

        return true;
    }
}
