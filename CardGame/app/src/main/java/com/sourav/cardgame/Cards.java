package com.sourav.cardgame;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;

import java.net.URL;

/**
 * Created by ASUS on 14/08/2015.
 */
public class Cards {
    public static final String AC="ace_of_clubs";
    public static final String AD="ace_of_diamonds";
    public static final String AH="ace_of_hearts";
    public static final String AS="ace_of_spades";
    public static final String JC="jack_of_clubs2";
    public static final String JD="jack_of_diamonds2";
    public static final String JH="jack_of_hearts2";
    public static final String JS="jack_of_spades2";
    public static final String KC="king_of_clubs2";
    public static final String KD="king_of_diamonds2";
    public static final String KH="king_of_hearts2";
    public static final String KS="king_of_spades2";
    public static final String QC="queen_of_clubs2";
    public static final String QD="queen_of_diamonds2";
    public static final String QH="queen_of_hearts2";
    public static final String QS="queen_of_spades2";

    public static final String[] others={AC,AD,AH,AS,JC,JD,JH,JS, KC,KD,KH, KS, QC,QD,QS};


    private String cardname;
    private boolean flipped;


    public Cards(String name)
    {
        cardname=name;

        flipped=false;
    }

    public void flipCard()
    {
        flipped=true;
    }
    public String getCardname()
    {
        return cardname;
    }
    public boolean isFlipped()
    {
        return flipped;
    }

    public String getBackImageResourceString()
    {
        String imagename="backimage";
        return imagename;
    }
}
