package de.hsanhalt.inf.studiappkoethen.util.xml.persons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.util.Log;
import de.hsanhalt.inf.studiappkoethen.util.xml.parsing.IXmlParsing;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PersonManager implements IXmlParsing
{

    private static PersonManager INSTANCE;
    private List<Person> person;


    /**
     * Standartkonstruktor. Laedt alle in als XML-Daten vorhandenen Personen in den RAM.
     */
    private PersonManager()
    {
        this.person = new ArrayList<Person>();
               
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
            personList = new ArrayList<Person>(this.person);
        }
        else
        {
            personList = new ArrayList<Person>();
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
                    this.addElement(category, subNode);
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
    private void addElement(PersonCategory category, Node node)
    {
        if (category == null)
        {
            Log.e("MatchPersonError", "Tried to match person without category.");
            return;
        }

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
        String[] modules = null;
        String[] responsibilities = null;
        String talkTime = null;
        String phone = null;
        String email = null;
        String url = null;

        NodeList list = node.getChildNodes();
        
        ArrayList<String> module = new ArrayList<String>();       //zum Füllen der Arrays nutzen wir Arraylisten um leere Elemente zu vermeiden. 
        ArrayList<String> responsibility = new ArrayList<String>();
        
        for (int i = 0; i < list.getLength(); i++)
        {
            Node subNode = list.item(i);
            String nodeName = subNode.getNodeName();
            String content = subNode.getTextContent();

            if (nodeName.equals("name"))
            {
                name = content;
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
                                                    //TODO xml an der Stelle ueberarbeiten & Implementierung ueberdenken
                                                    // Grund dafuer ist, dass getChildNodes mehr childs zurueck gibt, als vorhanden sind!
            										
                NodeList childList = subNode.getChildNodes();
              

                for (int k = 0; k < childList.getLength(); k++)
                {
                	Node n =childList.item(k); 
                	if(n.getNodeType()==1){ // 1 -> ELEMENT_NODE 
                		module.add(childList.item(k).getNodeValue());  
                	}
                }
            }
            else if (nodeName.equals("responsibilities"))
            {
                                                
                NodeList childList = subNode.getChildNodes();
                
 
                for (int k = 0; k < childList.getLength(); k++)
                {
                	Node n =childList.item(k);
                	if(n.getNodeType()==1){ // 1 -> ELEMENT_NODE
                		responsibility.add(childList.item(k).getNodeValue());
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

        Person newPerson;

        newPerson = new Person(category, name, surname, state, specialField, street, houseNumber, postalCode, city, buildings, room, description, profession, module.toArray(new String[module.size()]), responsibility.toArray(new String[responsibility.size()]), talkTime, phone, email, url);

        this.person.add(newPerson);

        Log.d("Created " + person.getClass().getSimpleName(), category.getName() + " - " + name);
    }

    /**
     * Sucht den Anfang der Person XML.
     *
     * @return String
     */
    @Override
    public String getStartTag()
    {
        return "persons";
    }
}
