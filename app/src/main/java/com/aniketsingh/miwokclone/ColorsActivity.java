package com.aniketsingh.miwokclone;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer ;
    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;

            audioManager.abandonAudioFocus(l);
        }
    }

    private MediaPlayer.OnCompletionListener releaseMedia = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    AudioManager audioManager;

    int res ;
    AudioManager.OnAudioFocusChangeListener l = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        //onAudioFocusChange method takes care of the Audio Focus state in our app
        public void onAudioFocusChange(int focusChange) {
            // We lost Audio Focus to some other app temporarily
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            }
            //We lost Audio Focus permanently
            else if (focusChange == AudioManager.AUDIOFOCUS_LOSS){
                releaseMediaPlayer();
            }
            //We regained Audio Focus after losing it
            else if (focusChange == AudioManager.AUDIOFOCUS_GAIN_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_GAIN){
                mediaPlayer.start();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Words> words = new ArrayList<Words>();
        words.add(new Words("weṭeṭṭi","red", R.drawable.color_red, R.raw.color_red));
        words.add(new Words("chokokki","green", R.drawable.color_green, R.raw.color_green));
        words.add(new Words("ṭakaakki","brown", R.drawable.color_brown, R.raw.color_brown));
        words.add(new Words("ṭopoppi","gray", R.drawable.color_gray, R.raw.color_gray));
        words.add(new Words("kululli","black", R.drawable.color_black, R.raw.color_black));
        words.add(new Words("kelelli","white", R.drawable.color_white, R.raw.color_white));
        words.add(new Words("ṭopiisә","dusty yellow", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        words.add(new Words("chiwiiṭә","mustard yellow", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));

        WordAdapter itemsAdapter = new WordAdapter(this, words, R.color.colorsView);

        ListView listView = findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Words word = words.get(position);

                releaseMediaPlayer();

                res = audioManager.requestAudioFocus(l , AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(res == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    //Requesting for Audio Focus only when item is clicked otherwise it is not necessary
                    //Below three lines will execute only if request is granted

                    mediaPlayer = MediaPlayer.create(ColorsActivity.this, word.getWordAudio());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(releaseMedia);
                }

            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        //Release the media player or audio file when the app is stopped
        releaseMediaPlayer();
    }
}
