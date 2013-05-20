package de.hsanhalt.inf.studiappkoethen.util.xml.buildings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.util.Log;
import de.hsanhalt.inf.studiappkoethen.util.xml.parsing.IXmlParsing;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class BuildingManager implements IXmlParsing
{
    private static BuildingManager INSTANCE;
    private List<Building> buildings;

    /**
     *
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
    public void addNode(Node node)
    {
        if (!this.getStartTag().equals(node.getNodeName()))
        {
            return;
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
                    category = BuildingCategoryManager.getInstance()
                                                      .getCategory(Byte.valueOf(subNode.getTextContent()));
                }
                else if (subNode.getNodeName().equals("building"))
                {
                    this.addElement(category, subNode);
                }
            }
        }
    }

    private void addElement(BuildingCategory category, Node node)
    {
        if (category == null)
        {
            Log.e("MatchBuildingError", "Tried to match building without category.");
            return;
        }

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

        List<String> images = new ArrayList<String>();

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
                description = content;
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

        Building building;

        if (collegeBuilding)
        {
            building = new CollegeBuilding(name, category, street, houseNumber, postalCode, city, phoneNumber, latitude, longitude, description, numberOfBuilding, numberOfFaculty, url, images.toArray(new String[images.size()]));
        }
        else
        {
            building = new Building(name, category, street, houseNumber, postalCode, city, phoneNumber, latitude, longitude, description, url, images.toArray(new String[images.size()]));
        }
        this.buildings.add(building);

        Log.d("Created " + building.getClass().getSimpleName(), category.getName() + " - " + name);
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
