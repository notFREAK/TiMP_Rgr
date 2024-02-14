package Poker.Objects;

import Poker.Objects.Card;

import java.util.Vector;
import java.lang.String;

public class Player
{
    private String name = "";
    private int cash;
    public Vector<Card> cards = new Vector<Card>();
    private String status ="";
    private boolean responded;
    private boolean active;
    private int bet;

    public int score = 0;
    public Player()
    {
        cash = 40;
        status = "IN";
        responded = false;
        active = false;
        bet = 0;
    }
    public Player(int bank)
    {
        status = "IN";
        cash = bank;
        responded = false;
        active = false;
        bet = 0;
    }

    public boolean fold()
    {
        bet = 0;
        status = "FOLD";
        return true;
    }
    public boolean allIn()
    {
        status = "ALLIN";
        cash = 0;
        return true;
    }
    public void setStatusIN()
    {
        status = "IN";
    }
    public String getStatus()
    {
        if(cash == 0)
            status ="ALLIN";
        return status;
    }
    public boolean gamble(int amount)
    {
        if( amount-bet > cash)
            return false;
        else
        {
            System.out.println("gambled");
            cash -= (amount-bet);
            bet = amount;
            if(cash == 0)
                status = "ALLIN";
            //System.out.println("In gamble returning true");
            return true;
        }
    }
    public boolean checkBet(int amount)
    {
        if( amount >= cash)
        {
            return false;
        }
        else
            return true;
    }
    public int getCash()
    {
        return cash;
    }
    public int getBet()
    {
        return bet;
    }
    public void printHand()
    {
        //System.out.println("in print hand");
        System.out.println(cards.elementAt(0).toString());
        System.out.println(cards.elementAt(1).toString());
    }
    public void newHand() {
        cards.clear();
    }
    public void addCards(Card x)
    {
        cards.add(x);
    }
    public boolean isActive() {return active; }
    public void setActiveTrue() {active = true; }
    public boolean isResponded()
    {
        return responded;
    }
    public void setRespondedTrue()
    {
        responded = true;
    }
    public void setRespondedFalse()
    {
        if (status != "FOLD" || status != "ALLIN")
            responded = false;
        else
            responded = true;
    }
    public void addWinnings(int amount)
    {
        cash += amount;
    }
    public void removeCards()
    {
        cards.clear();
    }
    public String getName()
    {
        return name;
    }
    public void setName(String x)
    {
        name = x;
    }
    public void setCash(int amount){cash = amount;}
    public void setBet(int newBet) {bet = newBet;};
}
