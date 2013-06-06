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

public class ExpandableListAdapter extends BaseExpandableListAdapter
{
    private Context context;
    private List<ExpandableListEntry> entryList;

    public ExpandableListAdapter(Context context, List<ExpandableListEntry> entryList)
    {
        this.context = context;
        this.entryList = entryList;
    }

    @Override
    public int getGroupCount()
    {
        return this.entryList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition)
    {
        return this.entryList.get(groupPosition).getChilds().length;
    }

    @Override
    public Object getGroup(int groupPosition)
    {
        return this.entryList.get(groupPosition).getGroup();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition)
    {
        return this.entryList.get(groupPosition).getChilds()[childPosition];
    }

    @Override
    public long getGroupId(int groupPosition)
    {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return childPosition;
    }

    @Override
    public boolean hasStableIds()
    {
        return true;
    }

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

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return true;
    }
}
