package com.sourav.cardgame;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ASUS on 14/08/2015.
 */
public class Deck {
    private ArrayList<Cards> cards;

    public Deck()
    {
        cards=new ArrayList<Cards>();
    }
    public Deck(ArrayList<Cards> cardsList)
    {
        cards=cardsList;
    }
    public void shuffleDeck()
    {
        Random r=new Random();
        int shuffleWith;
        for(int i=0;i<cards.size();i++)
        {
            shuffleWith=r.nextInt(cards.size());
            Cards c=cards.get(i);
            cards.set(i, cards.get(shuffleWith));
            cards.set(shuffleWith, c);

        }
    }
    public Cards get(int i)
    {
        return cards.get(i);
    }
    public int size()
    {
        return cards.size();
    }

    public void createDeck()
    {
        Cards queenH=new Cards(Cards.QH);
        cards.add(queenH);
        Random r=new Random();
        int k;
        for(int i=0;i<8;i++)
        {
            k= r.nextInt(Cards.others.length);
            cards.add(new Cards(Cards.others[k]));

        }

    }


}
