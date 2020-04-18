package com.aniketsingh.miwokclone;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

//Inheriting ArrayAdapter
//WordAdapter is customAdapter
public class WordAdapter extends ArrayAdapter<Words> {

    private int colorResourceId;

    //Constructor with two inputs
    public WordAdapter(Activity context, ArrayList<Words> word, int resourceID) {

        //Calling super constructor i.e. ArrayAdapter constructor and initialising resource with zero
        super(context,0, word);
        colorResourceId = resourceID;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;

        /*This block of code gets executed when scrap pile is empty or
          when filling the layout for first time
        */
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        View listContainer =  listItemView.findViewById(R.id.list_background);
        int color = ContextCompat.getColor(getContext(), colorResourceId);
        listContainer.setBackgroundColor(color);

        //getItem(position) returns the element present at the particular position of respective type
        Words currentWord = getItem(position);
//
//        View textContainer =  listItemView.findViewById(R.id.linear_layout);
//
//        final MediaPlayer wordAudio = MediaPlayer.create(getContext(), currentWord.getWordAudio());
//        textContainer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                wordAudio.start();
//            }
//        });

        //Making a textView object , finding it by id
        TextView t1 = listItemView.findViewById(R.id.miwok_text);
        //Setting miwok word in textView
        t1.setText(currentWord.getMiwokWord());


        //Creating a textView object , finding it by id
        TextView t2 = listItemView.findViewById(R.id.eng_text);
        //Setting english word in textView
        t2.setText(currentWord.getDefaultWord());

        //Creating a imageView object , finding it by id
        ImageView i1 = listItemView.findViewById(R.id.img);

        //This block gets executed if there is an image associated with the object
        if(currentWord.hasImage()) {

            //setting image in imageView
            i1.setImageResource(currentWord.getImageResourceId());
            //To make image visible if it is previously invisible
            i1.setVisibility(View.VISIBLE);
        }
        else {
            //To make image invisible without taking any space i.e. GONE
            i1.setVisibility(View.GONE);
        }
        return listItemView;
    }
}
