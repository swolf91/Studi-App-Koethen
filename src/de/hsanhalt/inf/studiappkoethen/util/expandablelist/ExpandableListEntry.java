package de.hsanhalt.inf.studiappkoethen.util.expandablelist;
/**
 * Objekt fuer eine Eintrag in die ExpandableList. Ein Eintrag enthaelt eine Kategorie und beliebig viele Unterelemente.
 *
 * @param <G> Liste von Kategorieeintraegen
 * @param <C> Kategorie
 */
public class ExpandableListEntry<G, C>
{
    private G group;
    private C[] childs;
    /**
     * Konstruktor fuer die benoetigten Items der Liste.
     * @param Kategorie und Kategorieeintraege
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
