package com.example.bdferreteria;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class DBHandler extends SQLiteOpenHelper implements Serializable {
    //Constructor, OnCreate
    //Array List for each table
    //Four tables:
    //1. Clientes (Cedula, nombre, direccion, telefono)
    //2. Pedido (codigo, descripcion, fecha, cantidad)
    //3. Producto (Codigo, descripcion, valor)
    //4. Factura (Numero, fecha, total)
    private static final String DB_Name = "fierrosdb";
    private static final int DB_Version = 1;
    ArrayList<Table_SQLite> tables_ArrayList = new ArrayList<>();

    public DBHandler(Context context){
        super(context, DB_Name,null, DB_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        tables_ArrayList.add(new Table_SQLite("clientes", "codigo","cedula", "nombre","direccion", "telefono"));
        tables_ArrayList.add(new Table_SQLite("pedido", "codigo","descripcion", "fecha", "cantidad"));
        tables_ArrayList.add(new Table_SQLite("producto", "codigo","descripcion", "valor"));
        tables_ArrayList.add(new Table_SQLite("factura", "numero","fecha", "total"));


        for (Table_SQLite myTableSQLite :tables_ArrayList) {
            String query = "CREATE TABLE " + myTableSQLite.getName() + " (";

            for (String column: myTableSQLite.getColumns_ArrayList()
                 ) {int index = myTableSQLite.getColumns_ArrayList().indexOf(column);
                if(index == 0){ query += column + " INTEGER PRIMARY KEY AUTOINCREMENT"; }
                else { query += column + " TEXT";}
                if(index < myTableSQLite.getColumns_ArrayList().size()-1){query+= ", ";}


            }
            query += ")";
            db.execSQL(query);
        }
        //FULLY WORKING!!
    }

    public void loadTables() {
        tables_ArrayList.clear();
        tables_ArrayList.add(new Table_SQLite("clientes", "codigo","cedula", "nombre","direccion", "telefono"));
        tables_ArrayList.add(new Table_SQLite("pedido", "codigo","descripcion", "fecha", "cantidad"));
        tables_ArrayList.add(new Table_SQLite("producto", "codigo","descripcion", "valor"));
        tables_ArrayList.add(new Table_SQLite("factura", "numero","fecha", "total"));

        for (Table_SQLite myTable: tables_ArrayList) {
            readElements(myTable);
        }

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (Table_SQLite myTableSQLite :tables_ArrayList
             ) {
            db.execSQL("DROP TABLE IF EXISTS " + myTableSQLite.getName());
        }

    }


    //Insert element to a table Works like a charm!!
    public boolean insertElement(Table_SQLite myTableSQLite, String... Fields)
    {
        //if (myTableSQLite.getColumns_ArrayList().size() != Fields.length){ return;}
        if (myTableSQLite.getColumns_ArrayList().size()-1 != Fields.length){ Log.e("DBHandler", "Invalid number of fields provided for insertion.");
            return false;}
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        int index = 1;
        for (String field: Fields
             ) {
            values.put(myTableSQLite.getColumns_ArrayList().get(index),field);
            index++;
        }

       long result = db.insert(myTableSQLite.getName(), null, values);

        db.close();

        return (result != 1);
    }

    //Read element from a table Works like a charm!!!!! It was super hard and I was worried
    public ArrayList<Row_SQLite> readElements(Table_SQLite myTableSQLite){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorTable = db.rawQuery("SELECT * FROM "+ myTableSQLite.getName(), null);

        ArrayList<Row_SQLite> row_sqLites = new ArrayList<>();


        if (cursorTable.moveToFirst()) {
            do {
                int index = 0;

                ArrayList<String> field_sqLite = new ArrayList<>();
                int field_PrimaryKey = 0;
                //SQLite may organize columns differently, so we search column by name instead
                for (String column: myTableSQLite.getColumns_ArrayList()
                     ) {
                    if(index == 0) {field_PrimaryKey = cursorTable.getInt(cursorTable.getColumnIndexOrThrow(myTableSQLite.getColumns_ArrayList().get(0)));}
                    // if index 0, then we're looking for primary key, so we search the index in cursor
                    // that matches by name the primary key column in array, then retrieve the value

                    else {field_sqLite.add(cursorTable.getString(cursorTable.getColumnIndexOrThrow(myTableSQLite.getColumns_ArrayList().get(index))));}
                    index++;
                }
                row_sqLites.add(new Row_SQLite(field_PrimaryKey, field_sqLite.toArray(new String[0])));

            } while (cursorTable.moveToNext());
        }
        cursorTable.close();
        db.close();

        myTableSQLite.setRows_ArrayList(row_sqLites);
        return row_sqLites;
        //Return arraylist of found rows, and update Table rows with the most recent data
    }

    //Update element. My beautiful code it's all working


    public void updateElement(Table_SQLite myTableSQLite, Row_SQLite myRowSQLite) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // Assuming the first field in myRowSQLite.fields corresponds to the second column in the table
        for (int i = 1; i < myTableSQLite.getColumns_ArrayList().size(); i++) {
            values.put(myTableSQLite.getColumns_ArrayList().get(i), myRowSQLite.fields.get(i - 1));
        }
        String whereClause = myTableSQLite.getColumns_ArrayList().get(0) + "=?";
        String[] whereArgs = new String[]{String.valueOf(myRowSQLite.getID())};
        db.update(myTableSQLite.getName(), values, whereClause, whereArgs);
        db.close();
    }

    public void deleteElement(Table_SQLite myTableSQLite, Row_SQLite myRowSQLite)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String whereClause = myTableSQLite.getColumns_ArrayList().get(0) + "=?";
        String[] whereArgs = new String[]{String.valueOf(myRowSQLite.getID())};
        db.delete(myTableSQLite.getName(),whereClause,whereArgs);
        db.close();
    }

    public ArrayList<Row_SQLite> findMatching(Table_SQLite myTableSQLite, String myColumn, String mySearch){
        SQLiteDatabase db = this.getReadableDatabase();
        String myQuery = new String("SELECT * FROM "+ myTableSQLite.getName() + " WHERE "
                + myColumn +" = " +"'" + mySearch + "'");
        Cursor cursorTable = db.rawQuery(myQuery,null);

        ArrayList<Row_SQLite> row_sqLites = new ArrayList<>();
        if (cursorTable.moveToFirst()) {
            do {
                int index = 0;

                ArrayList<String> field_sqLite = new ArrayList<>();
                int field_PrimaryKey = 0;
                //SQLite may organize columns differently, so we search column by name instead
                for (String column: myTableSQLite.getColumns_ArrayList()
                ) {                   if(index == 0) {field_PrimaryKey = cursorTable.getInt(cursorTable.getColumnIndexOrThrow(myTableSQLite.getColumns_ArrayList().get(0)));}
                    // if index 0, then we're looking for primary key, so we search the index in cursor
                    // that matches by name the primary key column in array, then retrieve the value

                    else {field_sqLite.add(cursorTable.getString(cursorTable.getColumnIndexOrThrow(myTableSQLite.getColumns_ArrayList().get(index))));}
                    index++;
                }
                row_sqLites.add(new Row_SQLite(field_PrimaryKey, field_sqLite.toArray(new String[0])));

            } while (cursorTable.moveToNext());
        }

        cursorTable.close();
        db.close();
        return row_sqLites;
    }


}
