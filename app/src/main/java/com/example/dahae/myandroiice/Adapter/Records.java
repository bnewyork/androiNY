package com.example.dahae.myandroiice.Adapter;

public class Records {

    String time = null;
    String name = null;

    public Records(String time, String name) {
        super();
        this.time = time;
        this.name = name;
    }

    public String getTime(){
        return time;
    }
    public void setTime(String time){
        this.time = time;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}