package com.hiwhuUI.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hiwhu.hiwhuclient.R;

import java.util.List;

public class McAdapter extends ArrayAdapter<comMessage> {
    private int resourceId;
    public McAdapter(Context context, int textViewResourceId,
                     List<comMessage> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        comMessage commessage = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        ImageView mcImage = (ImageView)view.findViewById(R.id.mc_image);
        TextView mcName = (TextView)view.findViewById(R.id.mc_name);
        mcImage.setImageResource(commessage.getImageId());
        mcName.setText(commessage.getName());
        return view;
    }
}
