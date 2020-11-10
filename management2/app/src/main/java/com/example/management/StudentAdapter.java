package com.example.management;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class StudentAdapter extends ArrayAdapter<Student> {
    private int resourceId;

    public StudentAdapter(@NonNull Context context, int resource, @NonNull List<Student> objects) {
        super(context, resource, objects);
        resourceId=resource;
    }


    public View getView(int position, View convertView, ViewGroup parent)
    {
        Student student=getItem(position);
        View view;
        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        }else{
            view=convertView;
        }
        //TextView id=(TextView)view.findViewById(R.id.words_id);
        TextView date =(TextView)view.findViewById(R.id.date);
        TextView temperature=(TextView)view.findViewById(R.id.temperature);
        TextView address=(TextView)view.findViewById(R.id.address);
        TextView others=(TextView)view.findViewById(R.id.others);
        //id.setText(words.getId());
        date.setText(student.getDate());
        temperature.setText(student.getTemperature());
        address.setText(student.getAddress());
        others.setText(student.getOthers());
        return view;
    }
}