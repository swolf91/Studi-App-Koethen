package de.hsanhalt.inf.studiappkoethen.xml.persons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.util.Log;
import de.hsanhalt.inf.studiappkoethen.xml.parsing.XmlParseException;
import de.hsanhalt.inf.studiappkoethen.xml.parsing.XmlParser;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
 * Bekommt Daten aus der XML und fuegt diese zu einem Personen-Objekt zusammen.
 *
 */
public class PersonManager implements XmlParser
{

    private static PersonManager INSTANCE;
    private List<Person> person;


    /**
     * Standartkonstruktor. Laedt alle in als XML-Daten vorhandenen Personen in den RAM.
     */
    private PersonManager()
    {
        this.person = new ArrayList<>();
               
    }


    /**
     * Gibt eine Instanz dieser Klasse zurueck und sorgt dafuer, dass auch nur
     * eine Instanz erstellt wird! Ganz ala Singleton.
     *
     * @return eine Instanz!
     */
    public static PersonManager getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new PersonManager();
        }
        return INSTANCE;
    }


    public Person getPerson(PersonCategory category, byte id)
    {
    	for(Person person : this.person)
    	{
    		if(person.getPersonCategory().equals(category) && person.getID() == id)
    		{
    			return person;
    		}
    	}
		return null;
    
    }
    
    
    /**
     * Gibt fuer 0..n Kategorien eine Personenliste zurueck.
     *
     * @param categories 0..n Moeglichkeiten
     *
     * @return Alle Personen der angeforderten Kategorieen
     */
    public List<Person> getPersonList(PersonCategory... categories)
    {
        List<Person> personList;
        if (categories == null || categories.length == 0)
        {
            personList = new ArrayList<>(this.person);
        }
        else
        {
            personList = new ArrayList<>();
            List<PersonCategory> categoryList = Arrays.asList(categories);

            for (Person person : this.person)
            {
                if (categoryList.contains(person.getPersonCategory()))
                {
                    personList.add(person);
                }
            }
        }
        return personList;
    }
    
    /**
     * Wertet ein XML Knote aus und teilt jedem neuen Personenobjekt eine Kategorie zu.
     */
    @Override
    public void addNode(Node node) throws XmlParseException
    {
        if (!this.getStartTag().equals(node.getNodeName()))
        {
            throw new XmlParseException("Couldn't parse persons. Wrong node is given!");
        }
        if (node.hasChildNodes())
        {
            NodeList nodes = node.getChildNodes();
            PersonCategory category = null;

            for (int i = 0; i < nodes.getLength(); i++)
            {
                Node subNode = nodes.item(i);
                if (subNode.getNodeName().equals("categoryID"))
                {
                    category = PersonCategoryManager.getInstance().getCategory(Byte.valueOf(subNode.getTextContent()));
                }
                else if (subNode.getNodeName().equals("person"))
                {
                    try
                    {
                        this.addElement(category, subNode);
                    }
                    catch (XmlParseException e)
                    {
                        Log.e("PersonManager", "XmlParseException", e);
                    }
                }
            }
        }
    }

    /**
     * Elemente werden aus der XML ausgelesen und als Personenobjekte angelegt.
     *
     * @param category wird benoetigt um schneller auf die Person zu schliessen.
     * @param node     aktueller XML Knoten
     */
    private void addElement(PersonCategory category, Node node) throws XmlParseException
    {
        if (category == null)
        {
            throw new XmlParseException("Tried to match person without a category.");
        }

        byte id = -1;
        String name = null;
        String surname = null;
        String state = null;
        String specialField = null;
        String street = null;
        String houseNumber = null;
        String postalCode = null;
        String city = null;
        String buildings = null;
        String room = null;
        String description = null;
        String profession = null;
        String talkTime = null;
        String phone = null;
        String email = null;
        String url = null;

        NodeList list = node.getChildNodes();
        
        ArrayList<String> modules = new ArrayList<>();       //zum Fuellen der Arrays nutzen wir Arraylisten um leere Elemente zu vermeiden.
        ArrayList<String> responsibilities = new ArrayList<>();
        
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
            else if (nodeName.equals("surname"))
            {
                surname = content;
            }
            else if (nodeName.equals("state"))
            {
                state = content;
            }
            else if (nodeName.equals("specialField"))
            {
                specialField = content;
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
            else if (nodeName.equals("buildings"))
            {
                buildings = content;
            }
            else if (nodeName.equals("room"))
            {
                room = content;
            }
            else if (nodeName.equals("description"))
            {
                description = content;
            }
            else if (nodeName.equals("profession"))
            {
                profession = content;
            }
            else if (nodeName.equals("street"))
            {
                street = content;
            }
            else if (nodeName.equals("modules"))
            {
                NodeList childList = subNode.getChildNodes();

                for (int k = 0; k < childList.getLength(); k++)
                {
                	subNode = childList.item(k);
                    if(subNode.getNodeName().equals("module"))
                    {
                        modules.add(subNode.getTextContent());
                    }
                }
            }
            else if (nodeName.equals("responsibilities"))
            {
                NodeList childList = subNode.getChildNodes();

                for (int k = 0; k < childList.getLength(); k++)
                {
                    subNode = childList.item(k);
                    if(subNode.getNodeName().equals("responsibility"))
                    {
                        responsibilities.add(subNode.getTextContent());
                    }
                }
            }
            else if (nodeName.equals("talkTime"))
            {
                talkTime = content;
            }
            else if (nodeName.equals("phone"))
            {
                phone = content;
            }
            else if (nodeName.equals("email"))
            {
                email = content;
            }
            else if (nodeName.equals("url"))
            {
                url = content;
            }
        }

        if(name == null)
        {
            throw new XmlParseException("Could not parse person. Forename (<name>) is unspecified!");
        }
        if(surname == null)
        {
            throw new XmlParseException("Could not parse person. Surname is unspecified!");
        }
        if(id == -1)
        {
            throw new XmlParseException("Could not parse person " + surname + "! Id is unspecified!");
        }

        Person newPerson = new Person(category,id, name, surname, state, specialField, street, houseNumber, postalCode, city, buildings, room, description, profession, modules.toArray(new String[modules.size()]), responsibilities.toArray(new String[responsibilities.size()]), talkTime, phone, email, url);
        this.person.add(newPerson);

        Log.d("PersonManagerDebug", "Created Person " + category.getName() + " - " + newPerson.getSurname());
    }

    /**
     * Zur erkennung des Anfangs der Personen-XML. 
     *
     */
    @Override
    public String getStartTag()
    {
        return "persons";
    }
}
