package com.example.android.englishlearning;


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


        //When Family Members is clicked


        TextView family=(TextView)findViewById(R.id.family);

        family.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent familyIntent =new Intent(MainActivity.this,FamilyActivity.class);
                startActivity(familyIntent);
            }
        });

        //When Phrases id clicked

        TextView phrases=(TextView)findViewById(R.id.phrases);

        phrases.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent phrasesIntent =new Intent(MainActivity.this,PhrasesActivity.class);
                startActivity(phrasesIntent);
            }
        });

    }
}
