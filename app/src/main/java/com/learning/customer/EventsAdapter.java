package com.learning.customer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class EventsAdapter extends ArrayAdapter<Events> {

    public static final String Event_ID="";
    private Activity context;
    private List<Events> list;

    public EventsAdapter(Activity context,List<Events> list) {
        super(context,R.layout.single_info_event_layout,list);
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=context.getLayoutInflater();
        View listViewItem = layoutInflater.inflate(R.layout.single_info_event_layout,null,true);

        TextView nameD = listViewItem.findViewById(R.id.textViewNameD);
        TextView organiserD = listViewItem.findViewById(R.id.textViewOrganiserD);

        final Events event = list.get(position);

        nameD.setText(event.getName());
        organiserD.setText(event.getOrganiser());

        Animation aniFade = AnimationUtils.loadAnimation(context,R.anim.fade_in_listview);
        listViewItem.startAnimation(aniFade);

        return listViewItem;
    }
}
