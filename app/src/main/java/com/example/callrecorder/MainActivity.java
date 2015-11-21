package com.example.callrecorder;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    SharedPreferences settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Context context = getApplicationContext();
        settings =context.getSharedPreferences("AUDIO_SOURCE", 0);
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
            LayoutInflater inflater = (LayoutInflater)MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//Here x is the name of the xml which contains the popup components
            PopupWindow pw = new PopupWindow(inflater.inflate(R.layout.setting,null, false),findViewById(R.id.relavitelayout).getWidth()-50,findViewById(R.id.relavitelayout).getHeight()-50,true);
            pw.showAtLocation(findViewById(R.id.relavitelayout), Gravity.CENTER, 0, 0);

            String checked = settings.getString("AUDIO_SOURCE", "");
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

        return super.onOptionsItemSelected(item);
    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
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

}
