package com.example.mewk;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ItemsAdapter extends ArrayAdapter<ItemData> {
    public ItemsAdapter(Context context, ArrayList<ItemData> items) {
       super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // Get the data item for this position
       ItemData item = getItem(position);    
       // Check if an existing view is being reused, otherwise inflate the view
       if (convertView == null) {
          convertView = LayoutInflater.from(getContext()).inflate(R.layout.listitem_row, parent, false);
       }
       // Lookup view for data population
       TextView tvItem = (TextView) convertView.findViewById(R.id.itemname);
       TextView tvTime = (TextView) convertView.findViewById(R.id.timeleft);
       // Populate the data into the template view using the data object
       tvItem.setText(item.itemname);
       tvTime.setText(item.deadline);
       // Return the completed view to render on screen
       return convertView;
   }
}
