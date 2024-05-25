package com.example.bdferreteria;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Table_SQLite implements Serializable {

    public ArrayList<String> getColumns_ArrayList() {
        return columns_ArrayList;
    }

    public String getName() {
        return name;
    }

    String name;
    //String Description;
    public ArrayList<String> columns_ArrayList = new ArrayList<>();
    public ArrayList<Row_SQLite> rows_ArrayList = new ArrayList<>();

    public Table_SQLite(String name, String... headings)
    {
        this.name = name;
        this.columns_ArrayList = new ArrayList<>(Arrays.asList(headings));
    }

    public void setRows_ArrayList(ArrayList<Row_SQLite> rows_ArrayList) {
        this.rows_ArrayList = rows_ArrayList;
    }

    public ArrayList<Row_SQLite> getRows_ArrayList() {
        return rows_ArrayList;
    }

    //Rows 1. Create Row_SQLite Class with one arraylist of strings and one int
    //2. Add arraylist of Rows, like a collection of rows in Table_SQLite
    //if i make an arraylist of arraylists it's awful, and unreadable, hence we build a Row_SQLite class

}
