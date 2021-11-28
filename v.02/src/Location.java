/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author scw
 */
public class Location {

    private int X;
    private int Y;
    private String loc_name;
    private int loc_land;
    private int loc_vila;
    private int loc_building;
    private int loc_hotel;
    private int who;
    private int[] value;

    public int getLoc_ground() {
        return loc_land;
    }

    public void setLoc_ground(int loc_ground) {
        this.loc_land = loc_ground;
    }

    public int getWho() {
        return who;
    }

    public void setWho(int who) {
        this.who = who;
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        this.X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        this.Y = y;
    }

    public String getLoc_name() {
        return loc_name;
    }

    public void setLoc_name(String loc_name) {
        this.loc_name = loc_name;
    }

    public int getLoc_vila() {
        return loc_vila;
    }

    public void setLoc_vila(int loc_vila) {
        this.loc_vila = loc_vila;
    }

    public int getLoc_building() {
        return loc_building;
    }

    public void setLoc_building(int loc_building) {
        this.loc_building = loc_building;
    }

    public int getLoc_hotel() {
        return loc_hotel;
    }

    public void setLoc_hotel(int loc_hotel) {
        this.loc_hotel = loc_hotel;
    }

    public Location(int x, int y) {
        // 생성자
        int[] idx = {0, 0, 0, 0};
        this.X = x;
        this.Y = y;
        this.who = 0;
        this.value = idx;
    }
    
        public Location(int x, int y, String tst) {
        // 생성자
        int[] idx = {0, 0, 0, 0};
        this.X = x;
        this.Y = y;
        this.who = 0;
        this.value = idx;
        this.loc_name = tst;    }


}
