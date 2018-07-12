package com.hiwhuUI.Activity.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hiwhu.hiwhuclient.R;

import java.util.List;

public class ResultAdapter extends ArrayAdapter<comResult>{
    private int resourceId;

    public ResultAdapter(Context context, int textViewRecourceId, List<comResult> objects){
        super(context,textViewRecourceId,objects);
        resourceId = textViewRecourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        comResult comresult = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        ImageView backImage = (ImageView) view.findViewById(R.id.result_back);
        TextView activityName = (TextView)view.findViewById(R.id.result_name);
        TextView resultState = (TextView)view.findViewById(R.id.result_state);
        backImage.setImageResource(comresult.getBackId());
        activityName.setText(comresult.getActivityName());
        resultState.setText(comresult.getState());
        return  view;
    }
}
