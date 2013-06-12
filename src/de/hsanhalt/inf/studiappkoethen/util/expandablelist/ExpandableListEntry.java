package de.hsanhalt.inf.studiappkoethen.util.expandablelist;

public class ExpandableListEntry<G, C>
{
    private G group;
    private C[] childs;
    /**
     * Baut ein Objekt zum Eintrag in die ExpandableList. Ein Eintrag enthaelt eine Gruppe und beliebig viele Unterelemente.
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
     * @return
     */
    public G getGroup()
    {
        return this.group;
    }
}
