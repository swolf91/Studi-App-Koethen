package de.hsanhalt.inf.studiappkoethen.xml.persons;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import android.util.Log;
import de.hsanhalt.inf.studiappkoethen.xml.parsing.XmlParseException;
import de.hsanhalt.inf.studiappkoethen.xml.parsing.XmlParser;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PersonCategoryManager implements XmlParser
{
    private static PersonCategoryManager INSTANCE;
    private Map<Byte, PersonCategory> categoryMap;

    public static PersonCategoryManager getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new PersonCategoryManager();
        }
        return INSTANCE;
    }

    private PersonCategoryManager()
    {
        this.categoryMap = new HashMap<>();
    }

    public PersonCategory getCategory(byte id)
    {
        return this.categoryMap.get(id);
    }

    @Override
    public void addNode(Node node) throws XmlParseException
    {
        if (!this.getStartTag().equals(node.getNodeName()))
        {
            throw new XmlParseException("Could not parse person categories. Wrong node is given!");
        }
        if (node.hasChildNodes())
        {
            NodeList nodes = node.getChildNodes();
            for (int i = 0; i < nodes.getLength(); i++)
            {
                Node subNode = nodes.item(i);
                if (subNode.getNodeName().equals("category"))
                {
                    try
                    {
                        this.addElement(subNode);
                    }
                    catch (XmlParseException e)
                    {
                        Log.e("PersonCategoryManager", "XmlParseException", e);
                    }
                }
            }
        }
    }

    @Override
    public String getStartTag()
    {
        return "personCategories";
    }

    private boolean addElement(Node node) throws XmlParseException
    {
        if (!node.hasChildNodes())
        {
            throw new XmlParseException("Couldn't parse person categorie. Child nodes are missing!");
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

        if(name == null)
        {
            throw new XmlParseException("Could not parse person category. Name is unspecified!");
        }
        if(id == -1)
        {
            throw new XmlParseException("Could not parse person category " + name + ". ID is unspecified!");
        }

        PersonCategory category = new PersonCategory(id, name);
        this.categoryMap.put(id, category);
        Log.d("Person category created", "id: " + id + " name: " + name);

        return true;
    }

    public PersonCategory[] getPersonCategories()
    {
        PersonCategory[] personCategories = new PersonCategory[this.categoryMap.size()];

        int i = 0;
        for(Entry<Byte, PersonCategory> entry : this.categoryMap.entrySet())
        {
            personCategories[i++] = entry.getValue();
        }
        return personCategories;
    }
    
    public int getSize()
    {
    	return this.categoryMap.size();
    }
}
