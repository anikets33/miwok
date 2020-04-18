package com.aniketsingh.miwokclone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView messageNumbers = findViewById(R.id.numbers);
        TextView messageColors = findViewById(R.id.colors);
        TextView messageFamilyMembers = findViewById(R.id.familyMembers);
        TextView messagePhrases = findViewById(R.id.phrases);

        messageNumbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), NumbersActivity.class);
                startActivity(intent);
            }
        });

        messageColors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ColorsActivity.class);
                startActivity(intent);
            }
        });

        messageFamilyMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FamilyMembersActivity.class);
                startActivity(intent);
            }
        });

        messagePhrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PhrasesActivity.class);
                startActivity(intent);
            }
        });
    }
}