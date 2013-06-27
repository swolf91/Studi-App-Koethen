package de.hsanhalt.inf.studiappkoethen.util.expandablelist;
/**
 * Objekt fuer eine Eintrag in die ExpandableList. Ein Eintrag enthaelt eine Kategorie und beliebig viele Unterelemente.
 *
 * @param <G> Liste von Kategorieeintraegen
 * @param <C> Kategorie
 */
public class ExpandableListEntry<G, C>
{
    /**
     * Der Oberpunkt von diesem Eintrag
     */
    private G group;
    /**
     * Die Unterpunkte, die zu diesem Eintrag gehoeren.
     */
    private C[] childs;
    /**
     * Konstruktor fuer die benoetigten Items der Liste.
     * @param group - Oberpunkt
     * @param childs - Unterpunkte
     */
    public ExpandableListEntry(G group, C... childs)
    {
        this.group = group;
        this.childs = childs;
    }
    /**
     * 
     * Gibt die Unterelemente des aktuellen Objekts zurueck.
     */
    public C[] getChilds()
    {
        return childs;
    }
    /**
     * Gibt die Kategorien des aktuellen Objekts zurueck.
     * 
     */
    public G getGroup()
    {
        return this.group;
    }
}
