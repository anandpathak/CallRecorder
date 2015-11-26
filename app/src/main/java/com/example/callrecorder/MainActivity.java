package com.example.callrecorder;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    /*SharedPreferences settings;
    SharedPreferences.Editor editor;
    EditText username,passwd,ftp;*/
    ListView listview;
    MediaPlayer mediaPlayer = new MediaPlayer();
    boolean flag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listview = (ListView)findViewById(R.id.listview);
        final ArrayList<String> list= Filelist();
        final ArrayList<String> list2=new ArrayList<>();
        String s="";
        for(int j=0; j < list.size();j++) {
            s=list.get(j);
            list2.add(j,s.substring(s.lastIndexOf("/")+1));
  //          Log.d("data",s);
        }
        final StableArrayAdapter adapter = new StableArrayAdapter(this,android.R.layout.simple_list_item_1, list2);
        listview.setAdapter(adapter);
/*        Context context = getApplicationContext();
        settings =context.getSharedPreferences("AUDIO_SOURCE", 0);
        editor = settings.edit();
        EditText username = (EditText)findViewById(R.id.USERNAME);
        EditText passwd = (EditText)findViewById(R.id.PASSWORD);
        EditText fpt = (EditText)findViewById(R.id.FPT_HOST);
*/
/*        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                flag=!flag;
 //               final String item = (String) parent.getItemAtPosition(position);
                /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    view.animate().setDuration(2000).alpha(0)
                            .withEndAction(new Runnable() {

                                @Override
                                public void run() {
                                    list.remove(item);
                                    adapter.notifyDataSetChanged();
                                    view.setAlpha(1);
                                }
                            });
                } else {
                    view.animate().setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            list.remove(item);
                            adapter.notifyDataSetChanged();
                            view.setAlpha(1);
                        }
                    });
                }*/

                    Log.d("position", " " + position);
 //                   playmusic(list.get(position), flag);


            }

        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Log.d("action", "setting clicked");
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            Intent intent1= new Intent(this,SettingActivity.class);
            startActivity(intent1);

 //           String checked = settings.getString("AUDIO_SOURCE", "");
            /*Log.d("radio" , checked);
            RadioGroup r =(RadioGroup)findViewById(R.id.radiogroup);
            switch (checked){
                case "DEFAULT":
                      r.check(R.id.DEFAULT);
                    break;
                case "VOICE_CALL":
//                    b = (RadioButton) findViewById(R.id.VOICE_CALL);
//                    b.setChecked(true);
                    r.check(R.id.VOICE_CALL);
                    break;
                case "VOICE_COMMUNICATION":
//                    b = (RadioButton) findViewById(R.id.VOICE_COMMUNICATION);
//                    b.setChecked(true);
                    r.check(R.id.VOICE_COMMUNICATION);
                    break;
                default:
//                    b = (RadioButton) findViewById(R.id.VOICE_COMMUNICATION);
//                    b.setChecked(true);
                    r.check(R.id.VOICE_COMMUNICATION);
                    break;
            }*/
            return true;
        }
       else if(id== android.R.id.home)
        {
            Log.d("home", "clicked");
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
   /* public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        editor.remove("AUDIO_SOURCE");
        editor.commit();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.DEFAULT:
                if (checked)
                {
                    editor.putString("AUDIO_SOURCE", "DEFAULT");
                    editor.commit();
                }
                    break;
            case R.id.VOICE_COMMUNICATION:
                if (checked)
                {
                    editor.putString("AUDIO_SOURCE", "VOICE_COMMUNICATION");
                    editor.commit();
                }
                    break;
            case R.id.VOICE_CALL:
                if (checked)
                {
                    editor.putString("AUDIO_SOURCE", "VOICE_CALL");
                    editor.commit();
                }
                    break;
        }
    }*/


/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (REQUEST_CODE == requestCode) {
            Intent intent = new Intent(MainActivity.this, TService.class);
            startService(intent);
        }
    }
    */
// broadcast a custom intent.
    public void broadcastIntent(View view){
        Intent intent = new Intent();
        intent.setAction("com.example.callrecorder.Tservice");
        sendBroadcast(intent);
    }
    /*public void OnSaveButtonClicked(View view){

//        SharedPreferences.Editor editor = settings.edit();
//        usr.setText("xxx", TextView.BufferType.EDITABLE);

//        editor.putString("USERNAME",usr.getText().toString());
//        editor.putString("PASSWORD",passwd.getText().toString());
 //       editor.putString("FPT_HOST",fpt.getText().toString());
  //      editor.commit();
    }*/

    public ArrayList<String> Filelist(){
        ArrayList<String> list = new ArrayList<String>();
        File fileDirectory = new File(Environment.getExternalStorageDirectory()+"/TestRecordingDasa1");
        File[] dirFiles = fileDirectory.listFiles();
        if(dirFiles.length != 0) {
            // loops through the array of files, outputing the name to console
            for (int ii = 0; ii < dirFiles.length; ii++) {
                String fileOutput = dirFiles[ii].toString();
                Log.d("filelist" ,fileOutput);
                list.add(fileOutput);
            }
        }
        return list;
    }
/*    public void playmusic(String filepath , boolean flag){
        Uri myuri=Uri.fromFile(new File(filepath));
        if(flag) {

            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mediaPlayer.setDataSource(getApplicationContext(), myuri);
                mediaPlayer.prepare();
                mediaPlayer.start();
                Toast.makeText(this,"playing",Toast.LENGTH_LONG).show();
            }
            catch (Exception e){
                Log.d("can't play" , "no file found");
                Toast.makeText(this,"can't able to play",Toast.LENGTH_LONG).show();
            }
        }

    }*/
    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }
}
