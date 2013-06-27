package de.hsanhalt.inf.studiappkoethen.util.expandablelist;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import de.hsanhalt.inf.studiappkoethen.R.id;
import de.hsanhalt.inf.studiappkoethen.R.layout;
/**
 * Diese Klasse ist zur Laufzeit da,
 * um die Werte fuer Personen und Gebaeude zu kombinieren, die der ExpandableListAdapter weiterverarbeitet.
 *
 * @param <G> Liste der Gebaeude oder Personen
 * @param <C> zugehoerige Kategorie
 */
public class ExpandableListAdapter<G, C> extends BaseExpandableListAdapter
{
    private Context context;
    private List<ExpandableListEntry<G, C>> entryList;
    /**
     * Erzeugt den Adapter der ExpandableList
     * @param context 
     * @param entryList Eintraege der ExpandableList
     */
    public ExpandableListAdapter(Context context, List<ExpandableListEntry<G, C>> entryList)
    {
        this.context = context;
        this.entryList = entryList;
    }
    /**
     * Gibt die Anzahl der Kategorien zurueck.
     */
    @Override
    public int getGroupCount()
    {
        return this.entryList.size();
    }
    /**
     * Gibt die Anzahl Elemente der Kategorie zurueck. 
     */
    @Override
    public int getChildrenCount(int groupPosition)
    {
        return this.entryList.get(groupPosition).getChilds().length;
    }
    /**
     * Gibt die jeweilige Kategorie passend zur Position zurueck.
     */
    @Override
    public G getGroup(int groupPosition)
    {
        return this.entryList.get(groupPosition).getGroup();
    }
    /**
     * Gibt das jeweilige untere Element der Kategorie zurueck
     */
    @Override
    public C getChild(int groupPosition, int childPosition)
    {
        return this.entryList.get(groupPosition).getChilds()[childPosition];
    }
    /**
     * Gibt eine eindeutige ID der Kategorie zurueck.
     */
    @Override
    public long getGroupId(int groupPosition)
    {
        return groupPosition;
    }
    /**
     * Gibt eine eindeutige ID des unteren Elements einer Kategorie zurueck.
     */
    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return childPosition;
    }
    /**
     * Gibt "true" zurueck wenn die IDs sich nicht aendern.
     */
    @Override
    public boolean hasStableIds()
    {
        return true;
    }
    /**
     * Gibt ein View-Objekt mit Text und Layout zum anzeigen zurueck.
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout.activity_expandablelist_group_heading, null);
        }
        TextView textView = (TextView) convertView.findViewById(id.expandablelist_textview_headline);
        textView.setText(this.getGroup(groupPosition).toString());

        return convertView;
    }
    /**
     * Gibt ein View-Objekt, des Unterelementes mit Text und Layout, zum anzeigen zurueck.
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {
        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout.activity_expandablelist_child_row, null);
        }
        TextView textView = (TextView) convertView.findViewById(id.expandablelist_textview_childItem);
        textView.setText(this.getChild(groupPosition, childPosition).toString());
        return convertView;
    }
    /**
     * Ist "true" wenn die Unterelemente der Kategorie selektierbar sein duerfen.
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return true;
    }
}
