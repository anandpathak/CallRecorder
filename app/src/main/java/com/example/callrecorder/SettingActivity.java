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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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

        username=(EditText)findViewById(R.id.USERNAME);
        passwd=(EditText)findViewById(R.id.PASSWORD);
        ftp=(EditText)findViewById(R.id.FPT_HOST);
        Context context = getApplicationContext();
        settings =context.getSharedPreferences("AUDIO_SOURCE", 0);
        editor = settings.edit();
        RadioGroup rg=(RadioGroup)findViewById(R.id.radiogroup);

        //rg.check(R.id.DEFAULT);
        //Set variables
        String choice = settings.getString("AUDIO_SOURCE","");
        switch (choice){
            case "DEFAULT":
                rg.check(R.id.DEFAULT);
                break;
            case  "VOICE_COMMUNICATION" :
                rg.check(R.id.VOICE_COMMUNICATION);
                break;
            case "VOICE_CALL" :
                rg.check(R.id.VOICE_CALL);
                break;
            default:
                rg.check(R.id.VOICE_CALL);
        }
        Log.d("value" , settings.getString("USERNAME","") );
        username.setText(settings.getString("USERNAME",""), null);
        passwd.setText(settings.getString("PASSWORD",""));
        ftp.setText(settings.getString("FTP_HOST",""));
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

        editor.putString("USERNAME",username.getText().toString());
        editor.putString("PASSWORD",passwd.getText().toString());
        editor.putString("FTP_HOST", ftp.getText().toString());
        editor.commit();
        Toast.makeText(getApplicationContext(),"Saved !" , Toast.LENGTH_LONG).show();
    }
}