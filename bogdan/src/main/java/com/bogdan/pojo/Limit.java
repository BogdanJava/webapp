package com.bogdan.pojo;

public class Limit {
    private int from;
    private int number;

    public Limit(int from, int number){
        if(from < 0) from = 1;
        this.from = from;
        if(number < 0) number = 1;
        this.number = number;
    }

    @Override
    public String toString() {
        return String.format("LIMIT %d, %d", from, number);
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
