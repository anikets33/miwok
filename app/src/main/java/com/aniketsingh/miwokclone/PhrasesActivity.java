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

public class PhrasesActivity extends AppCompatActivity {

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
        words.add(new Words("minto wuksus","Where are you going?", R.raw.phrase_where_are_you_going));
        words.add(new Words("tinnә oyaase'nә","What is your name?", R.raw.phrase_what_is_your_name));
        words.add(new Words("oyaaset...","My name is...", R.raw.phrase_my_name_is));
        words.add(new Words("michәksәs?","How are you feeling?", R.raw.phrase_how_are_you_feeling));
        words.add(new Words("kuchi achit","I’m feeling good.", R.raw.phrase_im_feeling_good));
        words.add(new Words("әәnәs'aa?","Are you coming?", R.raw.phrase_are_you_coming));
        words.add(new Words("hәә’ әәnәm","Yes, I’m coming.", R.raw.phrase_yes_im_coming));
        words.add(new Words("әәnәm","I’m coming.", R.raw.phrase_im_coming));
        words.add(new Words("yoowutis","Let’s go.", R.raw.phrase_lets_go));
        words.add(new Words("әnni'nem","Come here.", R.raw.phrase_come_here));

        WordAdapter itemsAdapter = new WordAdapter(this, words, R.color.phrasesView);

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

                    mediaPlayer = MediaPlayer.create(PhrasesActivity.this, word.getWordAudio());
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
