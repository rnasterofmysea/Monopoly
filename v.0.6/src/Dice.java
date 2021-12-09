/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author scw
 */
import java.util.Random;

public class Dice {

    public int rollDice() {
        Random ran = new Random();
        int ranNum = ran.nextInt(6) + 1; 
        return ranNum;
    }
}
