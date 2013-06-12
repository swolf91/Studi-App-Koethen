package de.hsanhalt.inf.studiappkoethen.util.expandablelist;

public class ExpandableListEntry<G, C>
{
    private G group;
    private C[] childs;
    /**
     * Dieses Objekt wird verwendet um DIe ExpandableList zu fuellen (Personen- oder Gebaude-Objekte).
     * @param group Die jeweilige Kategorie.
     * @param childs Das jeweilige Objekt das zur Kategorie gehört.
     */
    public ExpandableListEntry(G group, C... childs)
    {
        this.group = group;
        this.childs = childs;
    }
    /**
     *  Gibt untergeordnete Elemente zurueck.
     */
    public C[] getChilds()
    {
        return childs;
    }
    /**
     * Gibt die Kategorie zurueck.
     * 
     */
    public G getGroup()
    {
        return this.group;
    }
}
