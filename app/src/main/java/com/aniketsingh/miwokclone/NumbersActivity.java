package com.aniketsingh.miwokclone;

import android.content.Context;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

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

            //abandoning Audio Focus so that others app can use it
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
        words.add(new Words("lutti","one", R.drawable.number_one, R.raw.number_one));
        words.add(new Words("otiiko","two", R.drawable.number_two, R.raw.number_two));
        words.add(new Words("tolookosu","three", R.drawable.number_three, R.raw.number_three));
        words.add(new Words("oyyisa","four", R.drawable.number_four, R.raw.number_four));
        words.add(new Words("massokka","five", R.drawable.number_five, R.raw.number_five));
        words.add(new Words("temmokka","six", R.drawable.number_six, R.raw.number_six));
        words.add(new Words("kenekaku","seven", R.drawable.number_seven, R.raw.number_seven));
        words.add(new Words("kawinta","eight", R.drawable.number_eight, R.raw.number_eight));
        words.add(new Words("wo'e","nine", R.drawable.number_nine, R.raw.number_nine));
        words.add(new Words("na'aacha","ten", R.drawable.number_ten, R.raw.number_ten));

        WordAdapter itemsAdapter = new WordAdapter(this, words, R.color.numbersView);

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

                    mediaPlayer = MediaPlayer.create(NumbersActivity.this, word.getWordAudio());
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
