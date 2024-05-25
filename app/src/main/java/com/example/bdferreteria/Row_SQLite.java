package com.example.bdferreteria;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Row_SQLite implements Serializable {

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int ID = 0;
   public ArrayList<String> fields = new ArrayList<>();

    public Row_SQLite(int id, String... values) {
        this.ID = id;
        this.fields = new ArrayList<>(Arrays.asList(values));
    }

    public String getValueAtColumn(int columnID){

        if(columnID >= 0 && columnID < fields.size()){
            return this.fields.get(columnID);
        }
        else {return "";}
    }
}
