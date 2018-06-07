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

public class PhrasesActivity extends AppCompatActivity {

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

       final  ArrayList<Word> words=new ArrayList<>();

        words.add(new Word("तुम कहाँ जा रहे हो?", "where are you going?",R.raw.whereareyougoing));
        words.add(new Word("तुम्हारा नाम क्या हे?", "what is your name?",R.raw.whatisyourname));
        words.add(new Word("मेरा नाम है...", "My name is...",R.raw.mynameis));
        words.add(new Word("आप कैसा महसूस कर रहे हैं?", "How are you feeling?",R.raw.howareyoufeeling));
        words.add(new Word("मैं अच्छा महसूस कर रहा हूँ", "I am feeling good",R.raw.iamfeelinggood));
        words.add(new Word("क्या तुम आ रहे हो", "Are you coming",R.raw.areyoucoming));
        words.add(new Word("हाँ मैं आ रहा हूँ", "Yes I am coming",R.raw.yesiamcoming));
        words.add(new Word("मैं आ रहा हूँ", "I am coming",R.raw.iamcoming));
        words.add(new Word("चलिए चलते हैं", "Lets go",R.raw.letsgo));
        words.add(new Word("यहाँ आओ", "Come here",R.raw.comehere));

        WordAdapter adapter=new WordAdapter(this,words,R.color.category_phrases);

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
                    eMediaPlayer = MediaPlayer.create(PhrasesActivity.this, word.getAudioResourceId());

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



