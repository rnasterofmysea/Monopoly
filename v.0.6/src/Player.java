
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
    private int money; // 현금 자산
    private int asset; // 총자산
    private int turn;
    private int island;
    // 움직임에 대한 처리

    public int getAsset() {
        return asset;
    }

    public void setAsset(int asset) {
        this.asset = asset;
    }

    public int getIsland() {
        return island;
    }

    public void setIsland(int island) {
        this.island = island;
    }

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
        this.money = 100000;// 초기화
        this.asset = 100000;// 초기화
        this.turn = 10; // 총 turn 초기화
        this.referLoc = 0; // 시작 위치
        this.playerNum = num;
        this.X = x;
        this.Y = y;
        this.island = 0;
    }

    public int movement(int diceResult, Location[] locationList, JLabel[] playerMoneylblList, JLabel[] playerAssetlblList) {
        this.referLoc += diceResult;
        if (this.referLoc > 24) {
            this.referLoc -= 24;
            this.money += 20000;
            this.asset += 20000;
            playerMoneylblList[this.playerNum].setText(Integer.toString(this.money));
            playerAssetlblList[this.playerNum].setText(Integer.toString(this.asset));
        }
        this.X = locationList[this.referLoc].getX();
        this.Y = locationList[this.referLoc].getY();

        if (this.referLoc == 6) {
            return 6;
        } else if (this.referLoc == 12) {
            return 12;
        } else if (this.referLoc == 18) {
            return 18;
        } else {
            return locationList[this.referLoc].getWho();
        }
    }
}
