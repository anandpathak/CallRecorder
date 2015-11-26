package com.example.callrecorder;

import android.app.Activity;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomList extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] web;
    private final Integer imageId;
    boolean flag=true;
    public CustomList(Activity context,
                      String[] web, Integer imageId) {
        super(context, R.layout.list_single, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;

    }
    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
       View rowView= inflater.inflate(R.layout.list_single, null, true);
//            View rowView = super.getView(position,view,parent);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

        final ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(web[position]);

        imageView.setOnClickListener(new ImageView.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("mContext", "ImageView clicked for the row = " + position);
                if(flag) {
                    imageView.setImageResource(R.drawable.pause);
                    flag = !flag;
                }
                else {
                    imageView.setImageResource(R.drawable.play);
                    flag=!flag;
                }
            }
        });
        txtTitle.setOnClickListener(new TextView.OnClickListener() {

            @Override
            public void onClick(View v){
                Log.d("hhere it goes","me too"+position);
            }
        });

        imageView.setImageResource(imageId);


        return rowView;
    }

}