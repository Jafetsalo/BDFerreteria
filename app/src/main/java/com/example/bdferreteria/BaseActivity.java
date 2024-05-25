package com.example.bdferreteria;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class BaseActivity extends AppCompatActivity {

    String myTableName = "";
    ArrayList<TextView> textViewArrayList = new ArrayList<>();
    protected DBHandler dbHandler;
    Row_SQLite myRow = new Row_SQLite(-1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHandler = new DBHandler(BaseActivity.this);
        dbHandler.loadTables();

    }

    public void ShowToast(String message) {
        Toast warningToast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        warningToast.setGravity(Gravity.BOTTOM, 0, 0);
        warningToast.show();
    }

    public void SortActivity(String tableName) {
        if (tableName.equals("clientes")) {
            GoToActivity(InsertClienteActivity.class);
        } else if (tableName.equals("factura")) {
            GoToActivity(InsertFacturaActivity.class);
        } else if (tableName.equals("producto")) {
            GoToActivity(InsertProductoActivity.class);
        } else if (tableName.equals("pedido")) {
            GoToActivity(InsertPedidoActivity.class);
        }
        else
        {
            GoToActivity(MainActivity.class);
        }
    }

    public void GoToActivity(Class<?> cls) {
        Intent intentCRUD = new Intent(this, cls);
        startActivity(intentCRUD);
    }

    public Table_SQLite searchTable(String tableName){
        if ( dbHandler == null || dbHandler.tables_ArrayList == null) {
            ShowToast("Spinner or DBHandler is not initialized properly.");
            return null;
        }

        for (Table_SQLite tabla : dbHandler.tables_ArrayList) {
            if(tabla.getName().equals(tableName)){return tabla;}
        }

        return null;
    }

    public void InsertValue(String tableName)
    {

        Table_SQLite myTable = searchTable(tableName);
        if (myTable == null)
        {
            ShowToast("ERROR: No se encontró la tabla "+ tableName);
            return;
        }
        ArrayList<String> values = new ArrayList<>();

        for (TextView myTextView: textViewArrayList) {
            values.add(myTextView.getText().toString());
        }

        dbHandler.insertElement(myTable, values.toArray(new String[0]));
        ShowToast("Inserción exitosa en "+ tableName);

    }

    public void UpdateValue(String tableName, int index )
    {
        Table_SQLite myTable = searchTable(tableName);
        if (myTable == null)
        {
            ShowToast("ERROR: No se encontró la tabla "+ tableName);
            return;
        }
        if (myRow.getID() <0){
            ShowToast("ERROR: No se encontró el registro número "+ index);
            return;
        }
        if((myRow == null)||(myTable.rows_ArrayList.get(index)==null))
        {
            ShowToast("ERROR: No se encontró el registro número "+ index);
            return;
        }

        int indexRow = 0;
        for (TextView myTextView: textViewArrayList) {
            myRow.fields.set(indexRow,myTextView.getText().toString());
            indexRow++;
        }
        dbHandler.updateElement(myTable,myRow);
        ShowToast("Actualización exitosa en "+ tableName);
    }

    public void DeleteValue(String tableName, int index)
    {
        Table_SQLite myTable = searchTable(tableName);
        if (myTable == null)
        {
            ShowToast("ERROR: No se encontró la tabla "+ tableName);
            return;
        }
        if (myRow.getID() <0){
            ShowToast("ERROR: No se encontró el registro número "+ index);
            return;
        }
        if((myRow == null)||(myTable.rows_ArrayList.get(index)==null))
        {
            ShowToast("ERROR: No se encontró el registro número "+ index);
            return;
        }

        dbHandler.deleteElement(myTable,myRow);
        ShowToast("Eliminación exitosa en "+ tableName);
    }


    public void loadSpinner(Spinner mySpinner, String tableName) {

        if (searchTable(tableName)==null)
        {
            ShowToast("ERROR: No se encontró la tabla "+ tableName);
            return;
        }
        ArrayList<Row_SQLite> listaFilas = dbHandler.readElements(searchTable(tableName));
        ///Esta es la línea ganadora, que me actualiza las rows al llamar loadSpinner

        if (listaFilas.isEmpty()) {
            ShowToast("En el momento, no hay registros en " + tableName);
            ArrayAdapter<String> emptyAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, new String[] {});
            mySpinner.setAdapter(emptyAdapter);
            return;
        }

        // Obtener clave primaria y primer valor de filas, crear una lista de String con ello
        ArrayList<String> valoresFilas = new ArrayList<>();
        for (Row_SQLite fila : listaFilas) {
            valoresFilas.add(
                    fila.getID() + " " + fila.getValueAtColumn(1));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                valoresFilas.toArray(new String[0]));
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        mySpinner.setAdapter(adapter);
    }

    AdapterView.OnItemSelectedListener selectedElementListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            if (position == AdapterView.INVALID_POSITION) {
                ShowToast("No es posible EDITAR. No hay ningún "+ myTableName +" registrado");
                return;
            }

            //Escribir qué debe pasar
            //Con position sacamos el índice de un ArrayList con Rows
            //cargamos el row temporal, Llenamos los datos del formulario y listo
            Table_SQLite myTable = searchTable(myTableName);
            myRow = myTable.rows_ArrayList.get(position);

            int index = 0;
            for (String myValue: myRow.fields) {
                textViewArrayList.get(index).setText(myValue);
                index++;
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    protected boolean emptyFields() {
        for (TextView myTextView: textViewArrayList) {
            if(myTextView.getText().toString().isEmpty()) {
                ShowToast("Faltan valores a ingresar");
                return true;
            }
        }
        return false;
    }

    protected void RefreshForm(Spinner mySpinner)
    {
        loadSpinner(mySpinner,myTableName);
        for (TextView myTextView: textViewArrayList) {
            myTextView.setText("");
        }
    }


}
