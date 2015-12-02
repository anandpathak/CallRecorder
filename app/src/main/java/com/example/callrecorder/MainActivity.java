package com.example.callrecorder;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    /*SharedPreferences settings;
    SharedPreferences.Editor editor;
    EditText username,passwd,ftp;*/
    ListView listview;
    MediaPlayer mediaPlayer = new MediaPlayer();
    boolean flag=false;
    Integer imageId= R.drawable.play;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listview = (ListView)findViewById(R.id.listview);
        final ArrayList<String> list= Filelist();
        final ArrayList<String> list2=new ArrayList<>();
        if(list !=null) {
            String v = "";
            for (int j = 0; j < list.size(); j++) {
                v = list.get(j);
                list2.add(j, v.substring(v.lastIndexOf("/") + 1));
            }
            CustomList adapter = new CustomList(MainActivity.this, imageId, list, list2);
            listview.setAdapter(adapter);
        }
        else {
            Log.d("Problem","can't create file");
        }
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
    public ArrayList<String> Filelist() {
        ArrayList<String> list = new ArrayList<String>();
        File fileDirectory = new File(Environment.getExternalStorageDirectory() + "/TestRecordingDasa1");
        if (!fileDirectory.exists()) {
            fileDirectory.mkdirs();
            Log.d("folder modied on ", (new Date(fileDirectory.lastModified())).toString());
        }
        try {
                File[] dirFiles = fileDirectory.listFiles();
                if (dirFiles.length != 0) {
                    // loops through the array of files, outputing the name to console

                    Arrays.sort(dirFiles, new Comparator<File>() {
                        public int compare(File f1, File f2) {
                            return Long.valueOf(f2.lastModified()).compareTo(f2.lastModified());
                        }
                    });
                    for(int jj=0;jj < dirFiles.length;jj++)
                        Log.d("Sorted" ,dirFiles[jj].toString());

                    for (int ii = dirFiles.length-1; ii >=0; ii--) {
                        String fileOutput = dirFiles[ii].toString();
                        //                Log.d("filelist", fileOutput);
//                        Log.d("life ", (new Date(fileDirectory.lastModified())).toString());
                        list.add(fileOutput);
                    }
                }
        }

        catch (Exception e){
            Log.d("Problem","Not able to create file");
                return null;
            }
            return list;
        }
}
