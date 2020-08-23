package com.laioffer.washerdrymanagement.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.laioffer.washerdrymanagement.R;

import java.util.List;
import java.util.Map;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> listTitle;
    private Map<String, List<String>> listItem;
    public ExpandableListAdapter(Context context, List<String> listTitle, Map<String, List<String>> listItem) {
        this.context = context;
        this.listItem = listItem;
        this.listTitle = listTitle;
    }

    @Override
    public int getGroupCount() {
        return listTitle.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listItem.get(listTitle.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listTitle.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listItem.get(listTitle.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String title = (String) getGroup(groupPosition);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.menu_list_group, null);
        }
        TextView txtTitle = (TextView)convertView.findViewById(R.id.expendable_group);
        txtTitle.setText(title);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String title = (String) getChild(groupPosition, childPosition);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.menu_list_item, null);
        }
        TextView txtChild = (TextView)convertView.findViewById(R.id.expendable_item);
        txtChild.setText(title);
        ImageView image = (ImageView)convertView.findViewById(R.id.child_image);
        switch (title){
            case "All":
                image.setImageResource(R.drawable.ic_fiber_manual_record_24px);
                break;
            case "Available":
                image.setImageResource(R.drawable.ic_available_logo);
                break;
            case "Reserved":
                image.setImageResource(R.drawable.ic_using_logo);
                break;
            case "Finished" :
                image.setImageResource(R.drawable.ic_finished_logo);
                break;
            case "Damaged":
                image.setImageResource(R.drawable.ic_assignment_late_black_24dp);
                break;
            default:
                break;
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
