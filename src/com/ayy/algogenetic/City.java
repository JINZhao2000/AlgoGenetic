package com.ayy.algogenetic;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 23/02/2021
 * @ Version 1.0
 */
public class City {
    private int num;
    private double x;
    private double y;

    public City(int num, double x, double y) {
        this.num = num;
        this.x = x;
        this.y = y;
    }

    public int getNum() {
        return num;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "City["+num+"]";
    }
}
