package com.example.android.englishlearning;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by BHAVESH MOTIRAMANI on 17-12-2017.
 */

public class FamilyActivity extends AppCompatActivity {

    // handles the playback sound files
    private MediaPlayer eMediaPlayer;

    // handles audio focus when playing a sound file

    private AudioManager eAudioManager;

    private MediaPlayer.OnCompletionListener eCompletionListener =new MediaPlayer.OnCompletionListener(){
        @Override

        public void onCompletion(MediaPlayer mediaPlayer)
        {
            releaseMediaPlayer();
        }
    };


    private AudioManager.OnAudioFocusChangeListener eOnAudioFocusChangeListener=new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {

            if(focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK)
            {
                eMediaPlayer.pause();
                eMediaPlayer.seekTo(0);
            }
            else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                eMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);


        //Create and setup the request to AudioFocus

        eAudioManager=(AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words=new ArrayList<>();

        words.add(new Word("पिता", "father",R.drawable.family_father,R.raw.father));
        words.add(new Word("मां", "mother",R.drawable.family_mother,R.raw.mother));
        words.add(new Word("बेटा", "son",R.drawable.family_son,R.raw.son));
        words.add(new Word("बेटी", "daughter",R.drawable.family_daughter,R.raw.daughter));
        words.add(new Word("बड़े बेटे", "older brother",R.drawable.family_older_brother,R.raw.older_brother));
        words.add(new Word("छोटा बेटा", "younger brother",R.drawable.family_younger_brother,R.raw.younger_brother));
        words.add(new Word("बड़ी बेटी", "older sister",R.drawable.family_older_sister,R.raw.older_sister));
        words.add(new Word("छोटी बेटी", "younger sister",R.drawable.family_younger_sister,R.raw.younger_sister));
        words.add(new Word("दादी मा", "grandmother",R.drawable.family_grandmother,R.raw.grandmother));
        words.add(new Word("दादा", "grandfather",R.drawable.family_grandfather,R.raw.grandfather));
        words.add(new Word("भांजी", "niece",R.drawable.family_daughter,R.raw.niece));
        words.add(new Word("भतीजा", "nephew",R.drawable.family_son,R.raw.nephew));
        words.add(new Word("एक माँ की संताने", "siblings",R.drawable.family_younger_sister,R.raw.siblings));

        WordAdapter adapter=new WordAdapter(this,words,R.color.category_family);

        ListView listView=(ListView)findViewById(R.id.list);

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Release the media player if it currently exists because we are about to
                // play a different sound file
                releaseMediaPlayer();

                // Get the {@link Word} object at the given position the user clicked on
                Word word = words.get(position);

                // Request audio focus so in order to play the audio file. The app needs to play a
                // short audio file, so we will request audio focus with a short amount of time
                // with AUDIOFOCUS_GAIN_TRANSIENT.
                int result = eAudioManager.requestAudioFocus(eOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // We have audio focus now.

                    // Create and setup the {@link MediaPlayer} for the audio resource associated
                    // with the current word
                    eMediaPlayer = MediaPlayer.create(FamilyActivity.this, word.getAudioResourceId());

                    // Start the audio file
                    eMediaPlayer.start();

                    // Setup a listener on the media player, so that we can stop and release the
                    // media player once the sound has finished playing.
                    eMediaPlayer.setOnCompletionListener(eCompletionListener);
                }
            }
        });



    }

    @Override

    protected void onStop()
    {
        super.onStop();
        releaseMediaPlayer();
    }


    private void releaseMediaPlayer()
    {
        if(eMediaPlayer!=null)
        {
            eMediaPlayer.release();
            eMediaPlayer=null;

            eAudioManager.abandonAudioFocus(eOnAudioFocusChangeListener);
        }
    }


  }
