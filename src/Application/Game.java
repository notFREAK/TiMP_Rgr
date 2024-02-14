package Application;

import Poker.*;
import Poker.Objects.Card;
import Poker.Objects.DeckofCards;
import Poker.Objects.Player;

import java.util.Vector;
import java.lang.String;

public class Game
{
    public DeckofCards deck = new DeckofCards();
    public Vector<Card> board = new Vector<Card>();
    public Player[] players = new Player[5];
    public Vector<Player> ActivePlayers = new Vector<Player>();
    public Rulebook rules = new Rulebook();
    public int pot = 0;
    public int button = 0;     // which player has the button
    public int dillerButton = 0;
    public int currentBet = rules.getSmallBet();
    private round currentRound = round.Nothing;

    public Game()
    {
        players[0] = new Player();
        players[1] = new Player();
        players[2] = new Player();
        players[3] = new Player();
        players[4] = new Player();
    }
    public boolean isDone()
    {
        if(players[button%2].getCash() < 2)
            return true;
        if(players[(button+1)%2].getCash() < 3)
            return true;

        return false;
    }
    public void reset()
    {
        setAllRespondedFalse();
        for(Player p: players)
        {
            p.setStatusIN();
            p.removeCards();
        }
        board.clear();
        button++;
        currentRound = round.PREFLOP;
    }
    public void setAllRespondedFalse()
    {
        for( Player player : ActivePlayers)
        {
            player.setRespondedFalse();
        }
    }

    public void setAllBetNull() {
        for( Player player : ActivePlayers)
        {
            player.setBet(0);
        }
    }
    public void setAllRespondedTrue()
    {
        for( Player player : ActivePlayers)
        {
            player.setRespondedTrue();
        }
    }
    public boolean hasAllResponded()
    {
        for( Player player : ActivePlayers)
        {
            if(player.isResponded() == false)
                return false;
        }
        return true;
    }

    public boolean hasWin()
    {
        int i = 0;
        for( Player player : ActivePlayers)
        {
            if(player.getCash() != 0)
                i++;
        }
        if (i > 1)
            return false;
        else
            return true;
    }

    public boolean hasAlone()
    {
        int i = 0;
        for( Player player : ActivePlayers)
        {
            if(player.getStatus() != "FOLD")
                i++;
        }
        if (i > 1)
            return false;
        else
            return true;
    }


    public void setCash(int amount)
    {
        for( Player player : players) {
            player.setCash(amount);
        }
    }

    public boolean bettingEnabled()
    {
        for(Player p: players)
        {
            if(p.getStatus().equals("FOLD") || p.getStatus().equals("ALLIN"))
                return false;
        }
        return true;
    }

    public void checkFolding() {
        if (ActivePlayers.elementAt(button%ActivePlayers.size()).getStatus() == "FOLD") {
            ActivePlayers.elementAt(button%ActivePlayers.size()).setRespondedTrue();
            button++;
            checkFolding();
        }
    }
    public void initPreFlop() {
        if(hasAllResponded())
        {
        dillerButton = (dillerButton+1)%ActivePlayers.size();
        pot = 0;
        setAllRespondedFalse();
        setAllBetNull();
        setAllStatusIN();
        currentBet = rules.getSmallBet();
        ActivePlayers.elementAt( (dillerButton + 1) % ActivePlayers.size()).gamble(rules.getSmallBlind());
        ActivePlayers.elementAt((dillerButton + 2) % ActivePlayers.size()).gamble(rules.getBigBlind());
        button = (dillerButton+3) % ActivePlayers.size();
        pot += rules.getSmallBlind() + rules.getBigBlind();
        deck.deal();//burn a card
        board.clear();
        for (Player player : ActivePlayers) {
            player.newHand();
            player.addCards(deck.deal());
            player.addCards(deck.deal());
        }
        currentRound = round.PREFLOP;
    }
    }
    public void initFlop()
    {
        if(hasAllResponded())
        {
            setAllRespondedFalse();
            setAllBetNull();
            deck.deal();
            currentBet = 0;
            button = (dillerButton+1) % ActivePlayers.size();
            board.add(deck.deal());
            board.add(deck.deal());
            board.add(deck.deal());
            currentRound = round.FLOP;;
        }
    }

    public void initTurn()
    {
        if(hasAllResponded())
        {
            deck.deal();
            currentBet = 0;
            button = (dillerButton+1) % ActivePlayers.size();
            board.add(deck.deal());
            setAllRespondedFalse();
            setAllBetNull();
            currentRound = round.TURN;
        }
    }
    public void initRiver()
    {
        if(hasAllResponded())
        {
            deck.deal();
            currentBet = 0;
            button = (dillerButton+1) % ActivePlayers.size();
            board.add(deck.deal());
            setAllRespondedFalse();
            setAllBetNull();
            currentRound = round.RIVER;
        }
    }
    public String getPlayer1Name()
    {
        return players[0].getName();
    }
    public String getPlayer2Name()
    {
        return players[1].getName();
    }

    public void setPlayerActive(int number)
    {
        ActivePlayers.add(players[number]);
        players[number].setActiveTrue();
    }


    public void setAllStatusIN() {
        for(Player p: players)
        {
            p.setStatusIN();
        }
    }
    public void setPlayerName(String x, int number)
    {
        players[number].setName(x);
    }

    public round isRound()
    {
        return currentRound;
    }

    public void NextRound() {
        currentRound = currentRound.next();
    }

    public void winRound() {
        if (hasAlone()) {
            currentRound = round.Nothing;
            board.add(deck.deal());
            board.add(deck.deal());
            board.add(deck.deal());
            board.add(deck.deal());
            board.add(deck.deal());
        }
    }

    public DeckofCards getDeck()
    {
        return deck;
    }

    public Vector<Card> getBoard()
    {
        return board;
    }

    public Player[] getPlayers()
    {
        return players;
    }

    public Vector<Player> getActivePlayers()
    {
        return ActivePlayers;
    }

    public Rulebook getRules()
    {
        return rules;
    }

    public int getPot()
    {
        return pot;
    }

    public int getButton()
    {
        return button;
    }
    public int getCurrentBet()
    {
        return currentBet;
    }

    public void setDeck(DeckofCards d)
    {
        deck = d;
    }

    public void setBoard(Vector<Card> b)
    {
        board = b;
    }

    public void setRules(Rulebook r)
    {
        rules = r;
    }

    public void setPot(int p)
    {
        pot = p;
    }

    public void setButton(int b)
    {
        button = b;
    }

    public void setCurrentBet(int c)
    {
        currentBet = c;
    }

}
