package com.example.callrecorder;

import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

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
    public void startRecording(){
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

          //                       recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);

        TService.recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION);
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
