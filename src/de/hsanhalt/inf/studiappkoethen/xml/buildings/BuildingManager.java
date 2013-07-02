package de.hsanhalt.inf.studiappkoethen.xml.buildings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.util.Log;
import de.hsanhalt.inf.studiappkoethen.xml.parsing.XmlParseException;
import de.hsanhalt.inf.studiappkoethen.xml.parsing.XmlParser;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParserException;

/**
 * Diese Klasse verwaltet die Instanzen der Building-Klasse.
 */
public class BuildingManager implements XmlParser
{
    /**
     * beinhaltet die Instanz dieser Klasse
     */
    private static BuildingManager INSTANCE;
    /**
     * In dieser Map werden bzw sind alle BuildingObjekte abgespeichert.
     */
    private List<Building> buildings;

    /**
     * Konstruktor erstellt eine leere Liste, welche spaeter
     * Building-Objekte enthaelt.
     */
    private BuildingManager()
    {
        this.buildings = new ArrayList<Building>();
    }

    /**
     * Gibt eine Instanz dieser Klasse zurueck und sorgt dafuer, dass auch nur
     * eine Instanz erstellt wird! Ganz ala Singleton.
     */
    public static BuildingManager getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new BuildingManager();
        }
        return INSTANCE;
    }

    /**
     * Diese Methode gibt das BuildingObjekt mit den jeweiligen ID's zurueck
     * @param category - ID der BuildingCategory des Objekts
     * @param id - ID des Objektes
     * @return
     */
    public Building getBuilding(BuildingCategory category, byte id)
    {
        for(Building building : this.buildings)
        {
            if(building.getBuildingCategory().equals(category) && building.getID() == id)
            {
                return building;
            }
        }
        return null;
    }

    /**
     * Gibt eine Liste zurueck, welche Building-Objekte beinhaltet.
     * Diese setzen sich so zusammen:
     * 1. Fall kein Parameter - Alle vorhandenen Building-Objekte werden zurueckgegeben
     * 2. Fall mit Parameter -  Alle Building-Objekte, die zu den angegebenen BuildingCategory-Objekten gehoeren
     *                          werden zurueckgegeben
     * @param categories
     * @return
     */
    public List<Building> getBuildingList(BuildingCategory... categories)
    {
        List<Building> buildingList;
        if (categories == null || categories.length == 0)
        {
            buildingList = new ArrayList<Building>(this.buildings);
        }
        else
        {
            buildingList = new ArrayList<Building>();
            List<BuildingCategory> categoryList = Arrays.asList(categories);

            for (Building building : this.buildings)
            {
                if (categoryList.contains(building.getBuildingCategory()))
                {
                    buildingList.add(building);
                }
            }
        }
        return buildingList;
    }

    /**
     * Fuegt alle Inhalte innerhalb der Node in die Klasse ein.
     *
     * @param node - node welches als Inhalt das Starttag hat.
     */
    @Override
    public void addNode(Node node) throws XmlParseException
    {
        if (!this.getStartTag().equals(node.getNodeName()))
        {
            throw new XmlParseException("Can parse buildings. Wrong node is given.");
        }
        if (node.hasChildNodes())
        {
            NodeList nodes = node.getChildNodes();
            BuildingCategory category = null;

            for (int i = 0; i < nodes.getLength(); i++)
            {
                Node subNode = nodes.item(i);
                if (subNode.getNodeName().equals("categoryID"))
                {
                    category = BuildingCategoryManager.getInstance().getCategory(Byte.valueOf(subNode.getTextContent()));
                }
                else if (subNode.getNodeName().equals("building"))
                {
                    try
                    {
                        this.addElement(category, subNode);
                    }
                    catch (XmlParseException e)
                    {
                        Log.e("BuildingManager", "XmlParseException", e);
                    }
                }
            }
        }
    }

    /**
     * Parst das Node-Element zum Building-Objekt und fuegt es zur Liste hinzu.
     * @param category
     * @param node
     */
    private void addElement(BuildingCategory category, Node node) throws XmlParseException
    {
        if (category == null)
        {
            throw new XmlParseException("Tried to match building without a category.");
        }

        byte id = -1;
        String name = null;
        String street = null;
        String houseNumber = null;
        String postalCode = null;
        String city = null;
        String description = null;
        Integer latitude = null;
        Integer longitude = null;
        String url = null;
        String phoneNumber = null;

        boolean collegeBuilding = false;
        Integer numberOfFaculty = null;
        Integer numberOfBuilding = null;

        List<String> images = new ArrayList<>();

        NodeList list = node.getChildNodes();
        for (int i = 0; i < list.getLength(); i++)
        {
            Node subNode = list.item(i);
            String nodeName = subNode.getNodeName();
            String content = subNode.getTextContent();

            if (nodeName.equals("name"))
            {
                name = content;
            }
            else if(nodeName.equals("id"))
            {
                id = Byte.valueOf(content);
            }
            else if (nodeName.equals("street"))
            {
                street = content;
            }
            else if (nodeName.equals("houseNumber"))
            {
                houseNumber = content;
            }
            else if (nodeName.equals("postalCode"))
            {
                postalCode = content;
            }
            else if (nodeName.equals("city"))
            {
                city = content;
            }
            else if (nodeName.equals("latitude"))
            {
                latitude = Integer.valueOf(content);
            }
            else if (nodeName.equals("longitude"))
            {
                longitude = Integer.valueOf(content);
            }
            else if (nodeName.equals("description"))
            {
                String[] descriptionLines = content.trim().split("\n");
                description = "";
                for(String line : descriptionLines)
                {
                    description += line.trim() + "\n";
                }

            }
            else if (nodeName.equals("url"))
            {
                url = content;
            }
            else if (nodeName.equals("phoneNumber"))
            {
                phoneNumber = content;
            }
            else if (nodeName.equals("collegeBuilding"))
            {
                collegeBuilding = true;
                NodeList collageList = subNode.getChildNodes();
                for (int j = 0; j < collageList.getLength(); j++)
                {
                    Node collageNode = collageList.item(j);
                    String collageNodeName = collageNode.getNodeName();
                    if (collageNodeName.equals("numberOfFaculty"))
                    {
                        numberOfFaculty = Integer.valueOf(collageNode.getTextContent());
                    }
                    else if (collageNodeName.equals("numberOfBuilding"))
                    {
                        numberOfBuilding = Integer.valueOf(collageNode.getTextContent());
                    }
                }
            }
            else if(nodeName.equals("registerImage"))
            {
                images.add("images/" + content);
            }
        }

        if(name == null)
        {
            throw new XmlParseException("Couldn't parse building from category " + category.getName() + ". Name is unspecified");
        }
        else if(id == -1)
        {
            throw new XmlParseException("Couldn't parse building " + name + " from category " + category.getName() + ". ID is unspecified");
        }

        Building building;
        if (collegeBuilding)
        {
            building = new CollegeBuilding(name, id, category, street, houseNumber, postalCode, city, phoneNumber, latitude, longitude, description, numberOfBuilding, numberOfFaculty, url, images.toArray(new String[images.size()]));
        }
        else
        {
            building = new Building(name, id, category, street, houseNumber, postalCode, city, phoneNumber, latitude, longitude, description, url, images.toArray(new String[images.size()]));
        }
        this.buildings.add(building);

        Log.d("BuildingManagerDebug ", "Created " + building.getClass().getSimpleName() + ": " + category.getName() + " - " + name);
    }

    /**
     * Gibt das Starttag zurueck
     */
    @Override
    public String getStartTag()
    {
        return "buildings";
    }
}
