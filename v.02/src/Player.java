
import javax.swing.JLabel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author scw
 */
public class Player {

    private int X;
    private int Y;
    private int playerNum;
    private int referLoc;
    private int money;
    private int turn;
    // 움직임에 대한 처리

    public int getX() {
        return X;
    }

    public void setX(int X) {
        this.X = X;
    }

    public int getY() {
        return Y;
    }

    public void setY(int Y) {
        this.Y = Y;
    }

    public int getPlayerNum() {
        return playerNum;
    }

    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }

    public int getReferLoc() {
        return referLoc;
    }

    public void setReferLoc(int referLoc) {
        this.referLoc = referLoc;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public Player(int num, int x, int y) {
        this.money = 100; // 총 자산 초기화
        this.turn = 10; // 총 turn 초기화
        this.referLoc = 1;
        this.playerNum = num;
        this.X = x;
        this.Y = y;
    }

    public int movement(int diceResult, Location[] locationList) {
        this.referLoc += diceResult;
        if (this.referLoc > 24) {
            this.referLoc -= 24;
        }
        this.X = locationList[this.referLoc - 1].getX();
        this.Y = locationList[this.referLoc - 1].getY();
//        return locationList[this.referLoc].getWho();
        return this.X;
    }
}
