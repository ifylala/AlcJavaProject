package com.example.android.androiddevproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SEAMFIX PC on 15/09/2017.
 */

public class UserRecordsAdapter extends ArrayAdapter<UserRecords> {


    ArrayList<UserRecords> userClass;
    Context context;
    int resource;

    public UserRecordsAdapter(Context context, int resource, ArrayList<UserRecords> userClass) {
        super(context, resource, userClass);
        this.userClass=userClass;
        this.context=context;
        this.resource=resource;

            }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.users_list_items, parent, false);
        }

        UserRecords users=getItem(position);

        // Users Records that populates the list view
        ImageView imageView=(ImageView) convertView.findViewById(R.id.image1);;
        TextView textView = (TextView) convertView.findViewById(R.id.text1);
        TextView textView1 = (TextView) convertView.findViewById(R.id.text2);
        TextView textView2 = (TextView) convertView.findViewById(R.id.text3);

        UserRecords imageRecord = getItem(position);

        Picasso.with(context).load(users.getAvatarUrl()).placeholder(R.drawable.images).resize(80,80).into(imageView);
        textView.setText(imageRecord.getLogin());
        textView1.setText(imageRecord.getHtmlUrl());
        textView2.setText(imageRecord.getAvatarUrl());


        return convertView;
    }

    public void swapImageRecords(List<UserRecords> objects) {
        clear();

        for(UserRecords object : objects) {
            add(object);
        }

        notifyDataSetChanged();
    }
}
