package com.aniketsingh.miwokclone;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.view.View;

public class Words {

    //states
    private String miwokWord;
    private String defaultWord;
    private int image = NO_IMAGE_RESOURCE;
    private int wordAudio;


    private static final int NO_IMAGE_RESOURCE = -1;

    //constructor
    public Words(String miwokWordTranslation, String defaultWordTranslation, int imageTranslation, int audioTranslation) {
        miwokWord = miwokWordTranslation;
        defaultWord = defaultWordTranslation;
        image = imageTranslation;
        wordAudio = audioTranslation;
    }

    public Words(String miwokWordTranslation, String defaultWordTranslation, int audioTranslation) {
        miwokWord = miwokWordTranslation;
        defaultWord = defaultWordTranslation;
        wordAudio = audioTranslation;
    }

    //methods
    public String getMiwokWord() {
        return this.miwokWord;
    }

    public String getDefaultWord() {
        return this.defaultWord;
    }

    public int getImageResourceId() {
        return this.image;
    }

    public int getWordAudio() {
        return this.wordAudio;
    }

    public boolean hasImage() {
        return image != NO_IMAGE_RESOURCE;
    }
}
