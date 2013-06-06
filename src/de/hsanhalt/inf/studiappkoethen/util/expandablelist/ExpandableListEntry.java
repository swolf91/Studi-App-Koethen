package de.hsanhalt.inf.studiappkoethen.util.expandablelist;

public class ExpandableListEntry
{
    private Object group;
    private Object[] childs;

    public ExpandableListEntry(Object group, Object... childs)
    {
        this.group = group;
        this.childs = childs;
    }

    public Object[] getChilds()
    {
        return childs;
    }

    public Object getGroup()
    {
        return this.group;
    }
}
