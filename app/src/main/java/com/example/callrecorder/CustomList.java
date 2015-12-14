package com.example.callrecorder;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.media.MediaPlayer;
import android.widget.Toast;
import java.io.File;
import java.util.ArrayList;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferListener;

public class CustomList  extends ArrayAdapter<String>{

    private final Activity context;
    private final Integer imageId;
    private final ArrayList fileList, mainlist;
    MediaPlayer mediaPlayer = new MediaPlayer();
    boolean flag = true;
    ImageView previousImageView = null;
    boolean flag1=true;
   // final SeekBar seek = new SeekBar(getContext());
     SeekBar seek =null;
    Handler updateHandler = new Handler();
    //AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
    //AlertDialog alert1=null;
    public CustomList(Activity context,Integer imageId, ArrayList<String> fileList, ArrayList<String> mainlist) {
        super(context, R.layout.list_single, fileList);
        this.context = context;
        this.imageId = imageId;
        this.fileList = fileList;
        this.mainlist = mainlist;
    }

    @Override
    public View getView(final int position, final View view, final ViewGroup parent) {
        final LayoutInflater inflater = context.getLayoutInflater();
        final View rowView = inflater.inflate(R.layout.list_single, null, true);
//            View rowView = super.getView(position,view,parent);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        final ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(mainlist.get(position).toString());

        Log.d("create create", " iii");
        imageView.setImageResource(R.drawable.play);
 //       AlertDialog.Builder builder = new AlertDialog.Builder(getContext());


        //Alert();
        imageView.setOnClickListener(new ImageView.OnClickListener() {


            @Override
            public void onClick(View v) {
                Log.d("mContext", "ImageView clicked for the row = " + position);

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
// ...Irrelevant code for customizing the buttons and title
                final View dialogView = inflater.inflate(R.layout.seekbar, null);
                dialogBuilder.setView(dialogView);
                seek = (SeekBar)dialogView .findViewById(R.id.seekBar);
                seek.setMax(100);
              final  TextView title = (TextView)dialogView .findViewById(R.id.title);
              final  AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();
                updateHandler.postDelayed(timerRunnable, 1000);
                playmusic(fileList.get(position).toString());
     //           seek.setMax(musiclength());
                seek.setProgress(0);
                try {
            //        alert1.show();
                }
                catch (Exception e){
                    Log.d("again same" , "jsd;lfs"+e);
                }
                seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        Log.d("Stop", "Seeking");
                        //updateHandler.removeCallbacks(timerRunnable);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        Log.d("start", "Seeking");
                        updateHandler.postDelayed(timerRunnable,200);
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                          //  updateHandler.postDelayed(timerRunnable,500);
                            int duration = mediaPlayer.getDuration();
                            int moveto =  (progress*duration)/seek.getMax() -2;
                            Log.d("seekto","total-music "+duration +" moved to " + moveto + " seek to " + seek.getMax() + " progress " + progress );
                            mediaPlayer.seekTo(moveto);
                            Log.d("seek", "changed to " + moveto);
                        }
                    }
                });
                alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

                    @Override
                    public void onDismiss(DialogInterface builder1) {
                        Log.d("alert", "dismissed");
                        stopmusic();
                        //builder1.cancel();
                        updateHandler.removeCallbacks(timerRunnable);
                        alertDialog.dismiss();
                    }
                });

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        try
                        {

                            stopmusic();
                            Log.d("successfully", "closed alert");
                            updateHandler.removeCallbacks(timerRunnable);
                            alertDialog.dismiss();

                        }
                        catch (Exception e){
                            Log.d("mediaPlayer",""+e);
                        }
                    }
                });


                /*if (previousImageView == null) {
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
                }*/

            }
        });
        txtTitle.setOnClickListener(new TextView.OnClickListener() {

            @Override
            public void onClick(View v) {

                Log.d("I m ", fileList.get(position).toString());
                Log.d("Here it goes", "me too" + position);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Choose option..");
                final CharSequence[] items = {
                        "Delete",
                };
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {

                        // Do something with the selection
                        if (item == 0) {

                            File f = new File(fileList.get(position).toString());
                            try {
                                f.delete();
                                Log.d("file", "deleted");
                                fileList.remove(position);
                                mainlist.remove(position);
                                notifyDataSetChanged();
                            } catch (Exception e) {
                                Toast.makeText(context, "File not exist !", Toast.LENGTH_LONG).show();
                            }
                        }
                        //upload
                        if (item == 1) {
                            uploadFile(new File(fileList.get(position).toString()));
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

    /*public void Alert(){
        if(flag1){
            flag1=false;
            //builder.setTitle("playing..");
       //     seek.setMax(100);
       //     builder.setView(seek);
            alert1=builder.create();
        }

    }*/
    Runnable timerRunnable = new Runnable() {

        public void run() {
            // Get mediaplayer time and set the value
            seek.setProgress(mediaPlayer.getCurrentPosition()*seek.getMax()/mediaPlayer.getDuration());
            // This will trigger itself every one second.
            updateHandler.postDelayed(this, 500);
        }
    };


    public void playmusic(String filepath) {
        try {
            mediaPlayer.setDataSource(filepath);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            Log.d("can't play", "no file found");
        }
    }
    public int  musiclength(){
        try {
            return mediaPlayer.getDuration();
        }
        catch (Exception e){
            Log.d("Error" , "Can't get length" +e);
                    return 100;
        }
    }

    public void stopmusic() {
        try {
            mediaPlayer.stop();
            mediaPlayer.reset();
        } catch (Exception e) {
            Log.d("Error", "cant stop music" + e);
        }
    }


    public void uploadFile(File fileName) {
        FTPClient client = new FTPClient();
        SharedPreferences userShare = context.getSharedPreferences("AUDIO_SOURCE", 0);
        String USERNAME = userShare.getString("USERNAME", "");
        String PASSWORD = userShare.getString("PASSWORD", "");
        String FTP_HOST = userShare.getString("FTP_HOST", "");
        Log.d("data" ,USERNAME+" "+PASSWORD+" "+FTP_HOST);
        try {

            client.connect(FTP_HOST);
        //    client.login(USERNAME, PASSWORD);
        //    client.setType(FTPClient.TYPE_BINARY);
         //   client.changeDirectory("/upload/");

           // client.upload(fileName, new MyTransferListener());

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context,"client not connected !",Toast.LENGTH_LONG).show();
            try {
                client.disconnect(true);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }


    /*@Override
    public void run() {
            int currentPosition = 0;
            int total = mediaPlayer.getDuration();
            seek.setMax(total);
            Log.d("runnable" , "value" + mediaPlayer.getCurrentPosition() );
            while (mediaPlayer != null && currentPosition < total) {
                try {
                    Thread.sleep(100);
                    currentPosition = mediaPlayer.getCurrentPosition();
                } catch (InterruptedException e) {
                    return;
                } catch (Exception e) {
                    return;
                }
                seek.setProgress(currentPosition);
            }

    }*/


    /*******
     * Used to file upload and show progress
     **********/

    private class MyTransferListener implements FTPDataTransferListener {

        public void started() {

            //btn.setVisibility(View.GONE);
            // Transfer started
                 Toast.makeText(context, " Upload Started ...", Toast.LENGTH_SHORT).show();
            Log.d("upload", " started");
            //System.out.println(" Upload Started ...");
        }

        public void transferred(int length) {

            // Yet other length bytes has been transferred since the last time this
            // method was called
                Toast.makeText(context, " transferred ..." + length, Toast.LENGTH_SHORT).show();
            Log.d("upload", " transferred");
            //System.out.println(" transferred ..." + length);
        }

        public void completed() {

            //btn.setVisibility(View.VISIBLE);
            // Transfer completed

               Toast.makeText(context, " completed ...", Toast.LENGTH_SHORT).show();
            Log.d("upload", " completed");
            //System.out.println(" completed ..." );
        }

        public void aborted() {

            //btn.setVisibility(View.VISIBLE);
            // Transfer aborted
            Toast.makeText(context," transfer aborted ,please try again...", Toast.LENGTH_SHORT).show();
            //System.out.println(" aborted ..." );
            Log.d("upload", " transfer aborted");
        }

        public void failed() {

            //btn.setVisibility(View.VISIBLE);
            // Transfer failed
            //  System.out.println(" failed ..." );
            Log.d("upload", " failed");
        }

    }

}