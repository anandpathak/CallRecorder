package com.example.callrecorder;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.media.MediaPlayer;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class CustomList extends ArrayAdapter<String>{

    private final Activity context;
    private final Integer imageId;
    private final ArrayList fileList , mainlist;
    MediaPlayer mediaPlayer = new MediaPlayer();
    boolean flag=false;
    ImageView previousImageView=null;
    public CustomList(Activity context,
                       Integer imageId ,ArrayList<String> fileList ,ArrayList<String> mainlist) {
        super(context, R.layout.list_single, fileList);
        this.context = context;

        this.imageId = imageId;
        this.fileList=fileList;
        this.mainlist=mainlist;
    }
    @Override
    public View getView(final int position, final View view, final ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
       final View rowView= inflater.inflate(R.layout.list_single, null, true);
//            View rowView = super.getView(position,view,parent);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

        final ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(mainlist.get(position).toString());

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
                Log.d("Here it goes", "me too" + position);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Choose option..");
                final CharSequence[] items = {
                        "Delete", "Upload"
                };
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {

                        // Do something with the selection
                        if(item == 0)
                        {

                            File f= new File(fileList.get(position).toString());
                            try {
                                f.delete();
                                Log.d("file", "deleted");
                                fileList.remove(position);
                                mainlist.remove(position);
                                notifyDataSetChanged();
                            }
                            catch (Exception e){
                                Toast.makeText(context,"File not exist !" ,Toast.LENGTH_LONG).show();
                            }
                        }
                        //upload
                        if (item== 1)
                        {

                        }
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });

        imageView.setImageResource(imageId);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                previousImageView.setImageResource(R.drawable.play);
                Log.d("completed", "playing");

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

