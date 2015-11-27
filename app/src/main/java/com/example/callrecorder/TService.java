package com.example.callrecorder;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

/**
 * Created by DELLANAND on 20/11/2015.
 */
public class TService extends BroadcastReceiver  {
    static  MediaRecorder recorder = new MediaRecorder();
    File audiofile;
    String name, phonenumber;
    String audio_format;
    public String Audio_Type;
    int audioSource;
    Context context;
    private Handler handler;
    Timer timer;
    Boolean offHook = false, ringing = false;
    Toast toast;
    Boolean isOffHook = false;
    static private boolean recordstarted = false;
    private static final String ACTION_IN = "android.intent.action.PHONE_STATE";
    private static final String ACTION_OUT = "android.intent.action.NEW_OUTGOING_CALL";

    public SaveFile SaveRecording= new SaveFile();

        Bundle bundle;
        String state;
        String inCall, outCall;
        public static boolean wasRinging = false;
        public static boolean setUpRecording = false;

        @Override
        public void onReceive(Context context, Intent intent) {
            SharedPreferences settings =context.getSharedPreferences("AUDIO_SOURCE", 0);
            if (intent.getAction().equals(ACTION_IN)) {
                if ((bundle = intent.getExtras()) != null) {
                    state = bundle.getString(TelephonyManager.EXTRA_STATE);
//                    Toast.makeText(context, "state changed : " + state, Toast.LENGTH_LONG).show();
                        Log.d("State " ,state);
                    if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                        inCall = bundle.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                        wasRinging = true;
                        setUpRecording = true;
                        //Toast.makeText(context, "INcoming : " + inCall, Toast.LENGTH_LONG).show();
                        Log.d("Incoming Call" ," from : "+ inCall + "wasRinging : "+wasRinging);
                    } else if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {

                        Log.d("message" , " Oh ! he picked the call");
                        if (wasRinging == true) {

                            Toast.makeText(context, "ANSWERED", Toast.LENGTH_LONG).show();
                            Log.d("Answered the call" , "from :" + inCall);
                            if (setUpRecording) {
                                recordstarted = true;
                                setUpRecording = false;
                    //            SaveRecording.startRecording();
                                try {
                                    String method = settings.getString("AUDIO_SOURCE", "");
                                    Log.d("message", method);
                                    SaveRecording.startRecording(method,inCall,"-In");
                                }
                                catch (Exception e){
                                    Log.d("Recording Exception1 : ",e.toString());
                                }

                            }
                        }

                    } else if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                        wasRinging = false;
                        Toast.makeText(context, "REJECT || DISCO", Toast.LENGTH_LONG).show();
                        if (recordstarted) {
//                            recorder.stop();
                                Log.d("Message" , "Stopping recording"  );
                            SaveRecording.stopRecording();
//                            saverecordings.stopRecording();
                            //SaveFile.recorder.stop();
                            recordstarted = false;
                        }

                    }

                }
            } else if (intent.getAction().equals(ACTION_OUT)) {
                if ((bundle = intent.getExtras()) != null) {
                    outCall = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
                    Toast.makeText(context, "OUT : " + outCall, Toast.LENGTH_LONG).show();
                    //                   saverecordings.startRecording();
                    try {
                        String method = settings.getString("AUDIO_SOURCE", "");
                        Log.d("message", method);
                        SaveRecording.startRecording(method,outCall,"-Out");
                    }
                    catch (Exception e){
                        Log.d("Recording Exception2", e.toString());
                    }
                    recordstarted = true;
                    setUpRecording = false;

                }
            }
        }

    }
