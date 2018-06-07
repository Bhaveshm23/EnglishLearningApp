package com.example.android.englishlearning;

import static android.icu.text.Normalizer.NO;

/**
 * Created by BHAVESH MOTIRAMANI on 17-12-2017.
 */

public class Word {

    //Hindi Translation

    private String eHindiTranslation;

    //English Translation

    private String eEnglishTranslation;

    private static final int NO_IMAGE_PROVIDED=-1;

    //for audio

    private int eAudioResourceId;

    private int eImageResourceId=NO_IMAGE_PROVIDED;

    public Word(String hindiTranslation,String englishTranslation,int audioResourceId)
    {
        eEnglishTranslation=englishTranslation;
        eHindiTranslation=hindiTranslation;
        eAudioResourceId=audioResourceId;
    }

    //Constructor for Image to be added
    public Word(String hindiTranslation ,String englishTranslation,int imageResourceId,int audioResourceId)
    {
        eEnglishTranslation=englishTranslation;
        eHindiTranslation=hindiTranslation;
        eImageResourceId=imageResourceId;
        eAudioResourceId=audioResourceId;

    }

    //for Audio constructor





    public String getHindiTranslation()
    {
        return eHindiTranslation;
    }

    public String getEnglishTranslation()
    {
        return eEnglishTranslation;
    }

    public int getImageResourceId()
    {
        return eImageResourceId;
    }

    public int getAudioResourceId(){
        return eAudioResourceId;
    }

public boolean hasImage()
{
    return eImageResourceId!=NO_IMAGE_PROVIDED;
}

}
