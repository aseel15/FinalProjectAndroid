package com.example.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;


import com.example.finalproject.model.ServiceItem;

import java.util.ArrayList;

public class ServiceAdapter extends ArrayAdapter {
    public ServiceAdapter(@NonNull Context context, ArrayList<ServiceItem>serviceItems) {
        super(context,0,
                serviceItems);
    }
    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent){
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.custom_layout_dropdown,parent,false);
        }
        ServiceItem serviceItem=(ServiceItem) getItem(position);
        ImageView icon=convertView.findViewById(R.id.imgService);
        TextView nameItem=convertView.findViewById(R.id.nameService);
        if(serviceItem!=null){
            nameItem.setText(serviceItem.getNameService());
            icon.setImageResource(serviceItem.getImgService());

        }
        return convertView;

    }
    @Override
    public View getDropDownView(int position, @NonNull View convertView, @NonNull ViewGroup parent){
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.custom_layout_dropdown,parent,false);
        }
        ServiceItem serviceItem=(ServiceItem) getItem(position);
        TextView nameItem=convertView.findViewById(R.id.nameService);
        ImageView icon=convertView.findViewById(R.id.imgService);

        if(serviceItem!=null){
            nameItem.setText(serviceItem.getNameService());
            icon.setImageResource(serviceItem.getImgService());

        }
        return convertView;

    }

}
