package com.example.collectme;

import com.google.type.Date;

public class Collection {

    public String name, description;
    public int no_of_items;
    public Date creation_date;

    public Collection(String name, String description, int no_of_items, Date creation_date){
        this.name = name;
        this.description = description;
        this.no_of_items = no_of_items;
        this.creation_date = creation_date;
    }
}
