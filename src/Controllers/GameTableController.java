package Controllers;

import Poker.Objects.Card;
import Application.Context;
import Poker.Objects.Player;
import Poker.round;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.image.Image;


import java.io.IOException;
import java.util.Vector;

public class GameTableController implements Initializable{

    round currentRound;
    boolean show = false;
    @FXML
    private Button exitButton;
    @FXML
    void exit(ActionEvent event) {
        System.exit(1);
    }
    @FXML
    private Button restartButton;
    @FXML
    void restart(ActionEvent event) throws Exception
    {
        Context.getInstance().resetGame();
        Parent root = FXMLLoader.load(getClass().getResource("../resources/welcome.fxml"));
        Scene rootScene = new Scene(root);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(rootScene);
        window.show();
    }
    @FXML
    private Label roundName;
    @FXML
    private ImageView board0;
    @FXML
    private ImageView board1;
    @FXML
    private ImageView board2;
    @FXML
    private ImageView board3;
    @FXML
    private ImageView board4;

    @FXML
    private ImageView hand0;
    @FXML
    private ImageView hand1;

    @FXML
    private Label player1Cash;

    @FXML
    private Label player2Cash;

    @FXML
    private Label player3Cash;

    @FXML
    private Label player4Cash;

    @FXML
    private Label player5Cash;

    @FXML
    private Label player1Name;

    @FXML
    private Label player2Name;

    @FXML
    private Label player3Name;

    @FXML
    private Label player4Name;

    @FXML
    private Label player5Name;

    @FXML
    private Button checkButton;

    @FXML
    private Button raiseButton;

    @FXML
    private TextField raiseAmount;

    @FXML
    private Button foldButton;

    @FXML
    private Button allInButton;

    @FXML
    private Button toggleHandButton;

    @FXML
    private Button next;
    @FXML
    private Label errorMSG;

    @FXML
    private HBox hand;
    @FXML
    private AnchorPane player1;

    @FXML
    private AnchorPane player2;

    @FXML
    private AnchorPane player3;

    @FXML
    private AnchorPane player4;

    @FXML
    private Label bank;
    @FXML
    private AnchorPane player5;

    @FXML
    private Label player1Bet;

    @FXML
    private Label player2Bet;

    @FXML
    private Label player3Bet;

    @FXML
    private Label player4Bet;

    @FXML
    private Label player5Bet;

    @FXML
    void allIn(ActionEvent event) throws IOException
    {
        int button = Context.getInstance().currentGame().button;
        int cash = Context.getInstance().currentGame().ActivePlayers.elementAt(button % Context.getInstance().currentGame().ActivePlayers.size()).getCash();
        Context.getInstance().currentGame().ActivePlayers.elementAt(button % Context.getInstance().currentGame().ActivePlayers.size()).gamble(cash);        Context.getInstance().currentGame().pot += cash;
        if (Context.getInstance().currentGame().currentBet < cash);
            Context.getInstance().currentGame().currentBet = Context.getInstance().currentGame().ActivePlayers.elementAt(button % Context.getInstance().currentGame().ActivePlayers.size()).getBet();
        Context.getInstance().currentGame().ActivePlayers.elementAt(button % Context.getInstance().currentGame().ActivePlayers.size()).setBet(Context.getInstance().currentGame().currentBet);
        Context.getInstance().currentGame().setAllRespondedFalse();
        Context.getInstance().currentGame().ActivePlayers.elementAt(button % Context.getInstance().currentGame().ActivePlayers.size()).setRespondedTrue();
        Context.getInstance().currentGame().ActivePlayers.elementAt(button % Context.getInstance().currentGame().ActivePlayers.size()).setCash(0);
        Context.getInstance().currentGame().button++;
        refresh(event);
    }

    @FXML
    void check(ActionEvent event) throws IOException
    {
        int button  = Context.getInstance().currentGame().button;
        int current = Context.getInstance().currentGame().currentBet;
        int bet = Context.getInstance().currentGame().ActivePlayers.elementAt(button % Context.getInstance().currentGame().ActivePlayers.size()).getBet();
        if(!Context.getInstance().currentGame().hasAllResponded())
        {
            System.out.println(current);
            if (!Context.getInstance().currentGame().ActivePlayers.elementAt(button % Context.getInstance().currentGame().ActivePlayers.size()).gamble(current))
            {
                errorMSG.setText("You do not have the funds for that");
                return;
            }
            else
            {
                Context.getInstance().currentGame().pot += current - bet;
                Context.getInstance().currentGame().ActivePlayers.elementAt(button % Context.getInstance().currentGame().ActivePlayers.size()).setRespondedTrue();
                Context.getInstance().currentGame().button++;
            }

            if (Context.getInstance().currentGame().hasAllResponded())
            {
                Context.getInstance().currentGame().NextRound();
            }
            refresh(event);
        }
    }

    @FXML
    void next(ActionEvent event) throws IOException{
        Context.getInstance().currentGame().button++;
        refresh(event);
    }

    @FXML
    void fold(ActionEvent event) throws IOException
    {
        int button = Context.getInstance().currentGame().button;
        Context.getInstance().currentGame().ActivePlayers.elementAt(button % Context.getInstance().currentGame().ActivePlayers.size()).fold();
        Context.getInstance().currentGame().ActivePlayers.elementAt(button % Context.getInstance().currentGame().ActivePlayers.size()).setRespondedTrue();
        Context.getInstance().currentGame().button++;
        refresh(event);
    }

    @FXML
    void raise(ActionEvent event) throws IOException
    {
        int button = Context.getInstance().currentGame().button;
        int current = Context.getInstance().currentGame().currentBet;
        int raise = Integer.parseInt(raiseAmount.getText());
        System.out.println(current + raise);

        if (!Context.getInstance().currentGame().ActivePlayers.elementAt(button % Context.getInstance().currentGame().ActivePlayers.size()).gamble(current + raise))
        {
            errorMSG.setText("You do not have the funds for that");
        }
        else
        {
            Context.getInstance().currentGame().pot += current + raise;
            Context.getInstance().currentGame().currentBet += raise;
            Context.getInstance().currentGame().setAllRespondedFalse();
            Context.getInstance().currentGame().ActivePlayers.elementAt(button % Context.getInstance().currentGame().ActivePlayers.size()).setRespondedTrue();
            Context.getInstance().currentGame().button++;
            refresh(event);
        }
    }




    @FXML
    void toggleHand(ActionEvent event)
    {
        int button = Context.getInstance().currentGame().button;
        show = !show;
        if(show)
        {
            hand.setVisible(true);
            String playercard1 = Context.getInstance().currentGame().ActivePlayers.elementAt(button % Context.getInstance().currentGame().ActivePlayers.size()).cards.elementAt(0).toString();
            String playercard2 = Context.getInstance().currentGame().ActivePlayers.elementAt(button % Context.getInstance().currentGame().ActivePlayers.size()).cards.elementAt(1).toString();
            Image card1 = new Image(getClass().getResourceAsStream(playercard1));
            Image card2 = new Image(getClass().getResourceAsStream(playercard2));
            hand0.setImage(card1);
            hand1.setImage(card2);
        }
        else
        {
            hand.setVisible(false);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        Context.getInstance().currentGame().winRound();
        Context.getInstance().currentGame().checkFolding();
        int button = Context.getInstance().currentGame().button;
        currentRound = Context.getInstance().currentGame().isRound();
        Vector<Label> ActivePlayers = new Vector<Label>();
        Vector<Label> Cash = new Vector<Label>();
        Image card1, card2, card3, card4, card5;
        switch (currentRound) {
            case Nothing:
                Context.getInstance().currentGame().setAllRespondedTrue();
                if (Context.getInstance().currentGame().pot > 0) {
                    Player win = new Player();
                    int maxscore = 0;
                    roundName.textProperty().setValue("Poker");
                    card1 = new Image(getClass().getResourceAsStream(Context.getInstance().currentGame().board.elementAt(0).toString()));
                    card2 = new Image(getClass().getResourceAsStream(Context.getInstance().currentGame().board.elementAt(1).toString()));
                    card3 = new Image(getClass().getResourceAsStream(Context.getInstance().currentGame().board.elementAt(2).toString()));
                    card4 = new Image(getClass().getResourceAsStream(Context.getInstance().currentGame().board.elementAt(3).toString()));
                    card5 = new Image(getClass().getResourceAsStream(Context.getInstance().currentGame().board.elementAt(4).toString()));
                    board0.setImage(card1);
                    board1.setImage(card2);
                    board2.setImage(card3);
                    board3.setImage(card4);
                    board4.setImage(card5);
                    for (Player player : Context.getInstance().currentGame().ActivePlayers) {
                        if(player.getStatus() != "FOLD") {
                            Card[] phand = {player.cards.elementAt(0), player.cards.elementAt(1), Context.getInstance().currentGame().board.elementAt(0), Context.getInstance().currentGame().board.elementAt(1), Context.getInstance().currentGame().board.elementAt(2), Context.getInstance().currentGame().board.elementAt(3), Context.getInstance().currentGame().board.elementAt(4)};
                            player.score = Context.getInstance().currentGame().rules.score(phand);
                            if (maxscore <= player.score) {
                                maxscore = player.score;
                                win = player;
                            }
                        }
                    }
                    bank.textProperty().bind(new SimpleStringProperty(win.getName() +" победитель"));
                    win.addWinnings(Context.getInstance().currentGame().pot);
                    Context.getInstance().currentGame().pot = 0;
                    if (!Context.getInstance().currentGame().hasWin())
                        next.setVisible(true);
                    toggleHandButton.setVisible(false);
                    checkButton.setVisible(false);
                    raiseButton.setVisible(false);
                    raiseAmount.setVisible(false);
                    allInButton.setVisible(false);
                    foldButton.setVisible(false);
                    break;
                }
            case PREFLOP:
                Context.getInstance().currentGame().initPreFlop();
                roundName.textProperty().setValue("PreFlop");
                break;
            case FLOP:
                Context.getInstance().currentGame().initFlop();
                roundName.textProperty().setValue("Flop");
                card1 = new Image(getClass().getResourceAsStream(Context.getInstance().currentGame().board.elementAt(0).toString()));
                card2 = new Image(getClass().getResourceAsStream(Context.getInstance().currentGame().board.elementAt(1).toString()));
                card3 = new Image(getClass().getResourceAsStream(Context.getInstance().currentGame().board.elementAt(2).toString()));
                board0.setImage(card1);
                board1.setImage(card2);
                board2.setImage(card3);
                break;
            case TURN:
                Context.getInstance().currentGame().initTurn();
                roundName.textProperty().setValue("Turn");
                card1 = new Image(getClass().getResourceAsStream(Context.getInstance().currentGame().board.elementAt(0).toString()));
                card2 = new Image(getClass().getResourceAsStream(Context.getInstance().currentGame().board.elementAt(1).toString()));
                card3 = new Image(getClass().getResourceAsStream(Context.getInstance().currentGame().board.elementAt(2).toString()));
                card4 = new Image(getClass().getResourceAsStream(Context.getInstance().currentGame().board.elementAt(3).toString()));
                board0.setImage(card1);
                board1.setImage(card2);
                board2.setImage(card3);
                board3.setImage(card4);
                break;
            case RIVER:
                Context.getInstance().currentGame().initRiver();
                roundName.textProperty().setValue("River");
                card1 = new Image(getClass().getResourceAsStream(Context.getInstance().currentGame().board.elementAt(0).toString()));
                card2 = new Image(getClass().getResourceAsStream(Context.getInstance().currentGame().board.elementAt(1).toString()));
                card3 = new Image(getClass().getResourceAsStream(Context.getInstance().currentGame().board.elementAt(2).toString()));
                card4 = new Image(getClass().getResourceAsStream(Context.getInstance().currentGame().board.elementAt(3).toString()));
                card5 = new Image(getClass().getResourceAsStream(Context.getInstance().currentGame().board.elementAt(4).toString()));
                board0.setImage(card1);
                board1.setImage(card2);
                board2.setImage(card3);
                board3.setImage(card4);
                board4.setImage(card5);
                break;
        }
        player1.setVisible(Context.getInstance().currentGame().players[0].isActive());
        player2.setVisible(Context.getInstance().currentGame().players[1].isActive());
        player3.setVisible(Context.getInstance().currentGame().players[2].isActive());
        player4.setVisible(Context.getInstance().currentGame().players[3].isActive());
        player5.setVisible(Context.getInstance().currentGame().players[4].isActive());
        String[] names = new String[5];
        String[] cash = new String[5];
        String[] bet = new String[5];
        for (int i = 0; i < 5; i++) {
            if (Context.getInstance().currentGame().players[i].isActive()) {
                names[i] = Context.getInstance().currentGame().players[i].getName();
                cash[i] = Integer.toString(Context.getInstance().currentGame().players[i].getCash());
                switch (i) {
                    case 0:
                        ActivePlayers.add(player1Name);
                        Cash.add(player1Cash);
                        break;
                    case 1:
                        ActivePlayers.add(player2Name);
                        Cash.add(player2Cash);
                        break;
                    case 2:
                        ActivePlayers.add(player3Name);
                        Cash.add(player3Cash);
                        break;
                    case 3:
                        ActivePlayers.add(player4Name);
                        Cash.add(player4Cash);
                        break;
                    case 4:
                        ActivePlayers.add(player5Name);
                        Cash.add(player5Cash);
                        break;
                }
                if (Context.getInstance().currentGame().players[i].getBet() != 0) {
                    bet[i] = Integer.toString(Context.getInstance().currentGame().players[i].getBet());
                    bet[i] += "$";
                }
                else {
                    bet[i] = "";
                }
            } else {
                names[i] = "";
                cash[i] = "";
                bet[i] = "";
            }
        }
        player1Name.textProperty().setValue(names[0]);
        player2Name.textProperty().setValue(names[1]);
        player3Name.textProperty().setValue(names[2]);
        player4Name.textProperty().setValue(names[3]);
        player5Name.textProperty().setValue(names[4]);
        player1Cash.textProperty().setValue(cash[0] + "$");
        player2Cash.textProperty().setValue(cash[1] + "$");
        player3Cash.textProperty().setValue(cash[2] + "$");
        player4Cash.textProperty().setValue(cash[3] + "$");
        player5Cash.textProperty().setValue(cash[4] + "$");
        player1Bet.textProperty().setValue(bet[0]);
        player2Bet.textProperty().setValue(bet[1]);
        player3Bet.textProperty().setValue(bet[2]);
        player4Bet.textProperty().setValue(bet[3]);
        player5Bet.textProperty().setValue(bet[4]);
        if (currentRound != round.Nothing) {
            bank.textProperty().setValue("");
            if (Context.getInstance().currentGame().pot > 0) {
                bank.textProperty().setValue("Bank: " + Integer.toString(Context.getInstance().currentGame().pot) + "$");
            }
        }
        ActivePlayers.elementAt((button = Context.getInstance().currentGame().button) % Context.getInstance().currentGame().ActivePlayers.size()).setTextFill(Color.DEEPSKYBLUE);
        Cash.elementAt((button = Context.getInstance().currentGame().button) % Context.getInstance().currentGame().ActivePlayers.size()).setTextFill(Color.DEEPSKYBLUE);
    }

    void refresh(ActionEvent event) throws IOException {
        Parent gameParent = FXMLLoader.load(getClass().getResource("../resources/gameTable.fxml"));
        Scene gameScene = new Scene(gameParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(gameScene);
        window.show();
    }


}
