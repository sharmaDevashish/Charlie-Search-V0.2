package com.example.devashishsharma.charliesearchv11.Data;

/**
 * Created by devashish.sharma on 1/8/2016.
 */
public class Query {

    public int id;
    public String query;
    public String time;

    //Empty Constructor
    public Query(){}

    //constructor
    public Query(int id, String query, String time){
        this.id = id;
        this.query = query;
        this.time = time;
    }

    //constructor
    public Query(String query,String time){
        this.query = query;
        this.time = time;
    }

    //getter for Id
    public int getId(){
        return this.id;
    }

    //setter for Id
    public void setId(int id){
        this.id = id;
    }

    //getter for Query
    public String getQuery(){
        return this.query;
    }

    //setter for Query
    public void setQuery(String query){
        this.query = query;
    }

    //getter for time
    public String getTime() {
        return this.time;
    }

    //setter for time
    public void setTime(String time){
        this.time = time;
    }
}
