package com.example.callrecorder;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewDebug;
import android.widget.EditText;
import android.widget.RadioButton;

/**
 * Created by DELLANAND on 22/11/2015.
 */
public class SettingActivity extends AppCompatActivity {

    SharedPreferences settings;
    SharedPreferences.Editor editor;
    EditText username,passwd,ftp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        Context context = getApplicationContext();
        settings =context.getSharedPreferences("AUDIO_SOURCE", 0);
        editor = settings.edit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_main, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            this.finish();
            return true;
        }
        return true;
    }

    public void onRadioButtonClicked(View view) {
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
                    Log.d("AUDIO_SOURCE " , "SET");
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
    public void OnSaveButtonClicked(View view){
        EditText username=(EditText)findViewById(R.id.USERNAME);
        EditText passwd=(EditText)findViewById(R.id.PASSWORD);
        EditText ftp=(EditText)findViewById(R.id.FPT_HOST);
        editor.putString("USERNAME",username.getText().toString());
        editor.putString("PASSWORD",passwd.getText().toString());
        editor.putString("FTP_HOST",ftp.getText().toString());
        editor.commit();
    }
}