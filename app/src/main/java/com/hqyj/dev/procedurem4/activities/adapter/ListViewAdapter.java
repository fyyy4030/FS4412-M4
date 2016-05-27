package com.hqyj.dev.procedurem4.activities.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hqyj.dev.procedurem4.R;
import com.hqyj.dev.procedurem4.modules.Module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import view.DrawListViewItem;

/**
 *
 * Created by jiyangkang on 2016/5/5 0005.
 */
public class ListViewAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    private List<Module> modulesList = new ArrayList<>();

    public ListViewAdapter(Context context, List<Module> modules) {
        modulesList = modules;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return modulesList.size();
    }

    public class OC{
        public DrawListViewItem drawListViewItem;
    }

    @Override
    public Object getItem(int position) {
        return (modulesList == null) ? null : modulesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        OC oc = null;
        if (convertView == null){
            oc = new OC();
            convertView = mLayoutInflater.inflate(R.layout.listview_item, null);
            oc.drawListViewItem = (DrawListViewItem) convertView.findViewById(R.id.item_list);
            Module module = modulesList.get(position);
            oc.drawListViewItem.setName(module.getName());
            oc.drawListViewItem.setIsChoose(false);
            convertView.setTag(oc);
        }else {
            oc = (OC) convertView.getTag();
        }
        Module module = modulesList.get(position);
        oc.drawListViewItem.setName(module.getName());
        oc.drawListViewItem.setIsChoose(false);
        oc.drawListViewItem.invalidate();
        return convertView;
    }


}
