package de.hsanhalt.inf.studiappkoethen.util.expandablelist;

public class ExpandableListEntry<G, C>
{
    private G group;
    private C[] childs;

    public ExpandableListEntry(G group, C... childs)
    {
        this.group = group;
        this.childs = childs;
    }

    public C[] getChilds()
    {
        return childs;
    }

    public G getGroup()
    {
        return this.group;
    }
}
