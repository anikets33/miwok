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

public class FamilyMembersActivity extends AppCompatActivity {

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
        words.add(new Words("әpә","father", R.drawable.family_father, R.raw.family_father));
        words.add(new Words("әṭa","mother", R.drawable.family_mother, R.raw.family_mother));
        words.add(new Words("angsi","son", R.drawable.family_son, R.raw.family_son));
        words.add(new Words("tune","daughter", R.drawable.family_daughter, R.raw.family_daughter));
        words.add(new Words("taachi","older brother", R.drawable.family_older_brother, R.raw.family_older_brother));
        words.add(new Words("chalitti","younger brother", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        words.add(new Words("teṭe","older sister", R.drawable.family_older_sister, R.raw.family_older_sister));
        words.add(new Words("kolliti","younger sister", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        words.add(new Words("ama","grandmother", R.drawable.family_grandmother, R.raw.family_grandmother));
        words.add(new Words("paapa","grandfather", R.drawable.family_grandfather, R.raw.family_grandfather));


        WordAdapter itemsAdapter = new WordAdapter(this, words, R.color.familyMembersView);

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

                    mediaPlayer = MediaPlayer.create(FamilyMembersActivity.this, word.getWordAudio());
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
