package com.example.callrecorder;


import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaRecorder;
import android.os.Environment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Switch;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by DELLANAND on 21/11/2015.
 */
public class SaveFile {

 //   MediaRecorder recorder = TService.recorder;
    File audiofile;
    String audio_format;
    public String Audio_Type;
    int audioSource;

   /* public SaveFile(MediaRecorder recorder)
    {
        this.recorder=recorder;
    }*/
    public void startRecording(String method) {
        Log.d("recording Started" , "Yo I have been called" );
        String out = new SimpleDateFormat("dd-MM-yyyy hh-mm-ss").format(new Date());
        File sampleDir = new File(Environment.getExternalStorageDirectory(), "/TestRecordingDasa1");
        if (!sampleDir.exists()) {
            sampleDir.mkdirs();
        }
        String file_name = "Record";
        try {
            audiofile = File.createTempFile(file_name, ".amr", sampleDir);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("save exception", "error" + e);
        }
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();

//                                 recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);
/*
        try {
            MediaRecorder r = new MediaRecorder();
            r.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);
            r.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            r.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            r.setOutputFile(audiofile.getAbsolutePath());
            r.setAudioSamplingRate(96000);
            r.start();
            r.stop();
            r.reset();
            r.release();
            r = null;
            TService.recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);
        }
        catch (Exception e)
        {
            Log.d("State" , "Your phone does not support VOICE_CALL");
            MediaRecorder r = new MediaRecorder();
            r.setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION);
            r.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
            r.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            r.setOutputFile(audiofile.getAbsolutePath());
            try {
                r.prepare();
            } catch (IllegalStateException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();

            }
            r.start();
            r.stop();
            r.reset();
            r = null;
            TService.recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION);
        }

*/

       Log.d("message",method);
        switch(method){
            case "DEFAULT":
                TService.recorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
                break;
            case "VOICE_COMMUNICATION":
                TService.recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION);
                break;
            case  "VOICE_CALL":
                TService.recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);
                break;
            default:
                TService.recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION);
                break;
        }
//        TService.recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION);
        TService.recorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
        TService.recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        TService.recorder.setOutputFile(audiofile.getAbsolutePath());
        try {
            TService.recorder.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            Log.d("Problem" , "recoder.prepare has problem" );
        } catch (IOException e) {
            e.printStackTrace();
        }
        TService.recorder.start();
    }
    public void stopRecording(){
       TService.recorder.stop();
    }
}
