package com.example.callrecorder;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.media.MediaPlayer;

import java.util.ArrayList;

public class CustomList extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] web;
    private final Integer imageId;
    private final ArrayList fileList;
    MediaPlayer mediaPlayer = new MediaPlayer();
    boolean flag=false;
    ImageView previousImageView=null;
    public CustomList(Activity context,
                      String[] web, Integer imageId ,ArrayList<String> fileList) {
        super(context, R.layout.list_single, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;
        this.fileList=fileList;
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

                if (previousImageView == null) {
                    previousImageView = imageView;
                    imageView.setImageResource(R.drawable.pause);
                    playmusic(fileList.get(position).toString());

                } else {
                    if (previousImageView == imageView) {
                        if (flag) {
                            imageView.setImageResource(R.drawable.play);
                            stopmusic();

                            flag = false;
                        } else {
                            imageView.setImageResource(R.drawable.pause);
                            stopmusic();
                            playmusic(fileList.get(position).toString());

                            flag = true;
                        }
                    } else {
                        previousImageView.setImageResource(R.drawable.play);
                        imageView.setImageResource(R.drawable.pause);
                        previousImageView = imageView;
                        stopmusic();
                        playmusic(fileList.get(position).toString());
                    }
                }
            }
        });
        txtTitle.setOnClickListener(new TextView.OnClickListener() {

            @Override
            public void onClick(View v){

                Log.d("I m " , fileList.get(position).toString());
                Log.d("hhere it goes","me too"+position);
            }
        });

        imageView.setImageResource(imageId);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                previousImageView.setImageResource(R.drawable.play);
                Log.d("completed","playing");
            }
        });

        return rowView;
    }
    public void playmusic(String filepath ){
            try {
                mediaPlayer.setDataSource(filepath);
                mediaPlayer.prepare();
                mediaPlayer.start();
            }
            catch (Exception e){
                Log.d("can't play" , "no file found");
            }
        }
    public void stopmusic(){
        try{
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
        catch (Exception e){
            Log.d("Error" ,"cant stop music" +e);
        }
    }

}