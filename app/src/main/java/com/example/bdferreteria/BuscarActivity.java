package com.example.bdferreteria;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class BuscarActivity extends BaseActivity {
 Spinner spinnerColumnas;
 TextView textViewTitulo;
 TextView textViewResultado;
 TextView textViewConsulta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);
        myTableName=getIntent().getStringExtra("myTable");
        spinnerColumnas = findViewById(R.id.spinnerColumnaBuscar);
        textViewTitulo = findViewById(R.id.textViewTituloBuscar);
        textViewResultado = findViewById(R.id.textViewResultadoBuscar);
        textViewConsulta = findViewById(R.id.editTextValorBuscar);

        textViewArrayList.add(textViewConsulta);
        textViewTitulo.setText("Buscar en Tabla "+ myTableName+ " por Campo");
        loadSpinnerColumn(spinnerColumnas,myTableName);
    }


    public void loadSpinnerColumn(Spinner mySpinner, String tableName) {

        if (searchTable(tableName)==null)
        {
            ShowToast("ERROR: No se encontró la tabla "+ tableName);
            return;
        }

        Table_SQLite myTable = searchTable(tableName);

        ArrayList<String> listaColumnas = myTable.getColumns_ArrayList();

        if (listaColumnas.isEmpty()) {
            ShowToast("Hay error en la creación de " + tableName);
            ArrayAdapter<String> emptyAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, new String[] {});
            mySpinner.setAdapter(emptyAdapter);
            return;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                listaColumnas.toArray(new String[0]));
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        mySpinner.setAdapter(adapter);
    }

    public void ConsultarBusqueda(View view) {
        if (emptyFields()) return;
        Table_SQLite myTable = searchTable(myTableName);
        String consulta = textViewConsulta.getText().toString();
        String columna = myTable.getColumns_ArrayList().get(spinnerColumnas.getSelectedItemPosition());
        ArrayList<Row_SQLite> resultRows = dbHandler.findMatching(myTable,columna,consulta);

        if (resultRows.isEmpty()) {
            ShowToast("En el momento, no hay registros en " + myTableName);
            textViewResultado.setText("Resultado: 0 Filas Coincidente en "+ myTableName);
            return;
        }
        String Resultado = "Resultado: " + resultRows.size() + " Filas Coincidente en "+ myTableName;
        // Obtener clave primaria y primer valor de filas, crear una lista de String con ello
        //ArrayList<String> valoresFilas = new ArrayList<>();
        for (Row_SQLite fila : resultRows) {
            Resultado+= "\n "+ fila.getID() ;
            for (String registro: fila.fields) {
                Resultado+=" | " + registro + " | ";
            }
        }

        textViewResultado.setText(Resultado);
    }





}