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

    private int X; //장소의 x 좌표
    private int Y; // 장소의 y 좌표
    private String loc_name; // 장소명
    private int loc_ground; // 장소-토지 가격
    private int loc_vila; // 장소-빌라 가격
    private int loc_building; // 장소-빌딩 가격
    private int loc_hotel; // 장소-호텔가격
    private int who; // 소유주
    private int[] value; // 소유주가 소유하고 있는 땅의 배열

    public int getLoc_ground() {
        return loc_ground;
    }

    public void setLoc_ground(int loc_ground) {
        this.loc_ground = loc_ground;
    }

    public int[] getValue() {
        return value;
    }

    public void setValue(int[] value) {
        this.value = value;
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
}
