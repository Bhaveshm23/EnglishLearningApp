package com.example.android.englishlearning;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by BHAVESH MOTIRAMANI on 17-12-2017.
 */

public class WordAdapter extends ArrayAdapter<Word>
{
    private int hColorResourceId;
    WordAdapter(Activity context, ArrayList<Word> words,int colorResourceId) {
        super(context, 0, words);
        hColorResourceId=colorResourceId;
    }

    WordAdapter(Activity context, int resource,ArrayList<Word> words) {
        super(context,0, words);
    }

    @Override

    public View getView(int position, View convertView, ViewGroup parent)
    {
        View listItemView=convertView;

        if(listItemView==null)
        {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Word currentWord=getItem(position);


        TextView hindiTextView=(TextView)listItemView.findViewById(R.id.hindi_text_view);
        hindiTextView.setText(currentWord.getHindiTranslation());

        TextView englishTextView=(TextView)listItemView.findViewById(R.id.english_text_view);
        englishTextView.setText(currentWord.getEnglishTranslation());


        ImageView imageView=(ImageView) listItemView.findViewById(R.id.image);

        if(currentWord.hasImage())
        {
imageView.setImageResource(currentWord.getImageResourceId());
        }

        else
        {
            imageView.setVisibility(View.GONE);
        }


        //Set the theme color
        View textContainer=listItemView.findViewById(R.id.text_container);
        //Find the color that resource id maps
        int color= ContextCompat.getColor(getContext(),hColorResourceId);

        //Set the background color of the text container view
        textContainer.setBackgroundColor(color);

        return listItemView;


    }
}
