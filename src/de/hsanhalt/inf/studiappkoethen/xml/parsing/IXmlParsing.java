package de.hsanhalt.inf.studiappkoethen.xml.parsing;

import org.w3c.dom.Node;

/**
 * Interface, welches von Klassen implementiert werden muss, die XML-Dateien einlesen sollen
 */
public interface IXmlParsing
{
    /**
     * Fuegt alle Inhalte innerhalb der Node in die Klasse ein.
     *
     * @param node - node welches als Inhalt das Starttag hat.
     */
    public void addNode(Node node);

    /**
     * Gibt das Starttag zurueck
     */
    public String getStartTag();
}
