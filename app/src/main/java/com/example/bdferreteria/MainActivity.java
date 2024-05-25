package com.example.bdferreteria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {


    private Spinner spinnerTablas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        spinnerTablas = findViewById(R.id.spinnerTablas);
        cargarSpinnerTablas(dbHandler.tables_ArrayList);
    }

    public void cargarSpinnerTablas(ArrayList<Table_SQLite> listaTablas) {
        if (listaTablas.isEmpty()) {
            ShowToast("No es posible cargar Tablas. No hay ninguna registrada");
            ArrayAdapter<String> emptyAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, new String[] {});
            spinnerTablas.setAdapter(emptyAdapter);
            return;
        }

        // Obtener nombres de las tablas
        ArrayList<String> nombreTablas = new ArrayList<>();
        for (Table_SQLite tabla : listaTablas) {
            nombreTablas.add(tabla.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                nombreTablas.toArray(new String[0]));
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinnerTablas.setAdapter(adapter);
    }

    public void CargarCRUDActivity(View view) {
        if (spinnerTablas == null || dbHandler == null || dbHandler.tables_ArrayList == null) {
            ShowToast("Spinner or DBHandler is not initialized properly.");
            return;
        }

        if (spinnerTablas.getSelectedItemPosition() == AdapterView.INVALID_POSITION) {
            ShowToast("Tabla no seleccionada");
            return;
        }

        String selectedTableName = (String) spinnerTablas.getSelectedItem();
        if (selectedTableName != null) {
            for (Table_SQLite myTable : dbHandler.tables_ArrayList) {
                if (selectedTableName.equals(myTable.getName())) {
                    SortActivity(myTable.getName());
                    break;
                }
            }
        } else {
            ShowToast("Selected table name is null.");
        }
    }

    public void CargarBusquedaActivity(View view) {
        if (spinnerTablas == null || dbHandler == null || dbHandler.tables_ArrayList == null) {
            ShowToast("Spinner or DBHandler is not initialized properly.");
            return;
        }

        if (spinnerTablas.getSelectedItemPosition() == AdapterView.INVALID_POSITION) {
            ShowToast("Tabla no seleccionada");
            return;
        }

        String selectedTableName = (String) spinnerTablas.getSelectedItem();
        if (selectedTableName != null) {
            for (Table_SQLite myTable : dbHandler.tables_ArrayList) {
                if (selectedTableName.equals(myTable.getName())) {

                    //Ir a Activity con nombre de tabla
                    Intent intentBuscar = new Intent(this, BuscarActivity.class);
                    intentBuscar.putExtra("myTable",myTable.getName());
                    startActivity(intentBuscar);
                    break;
                }
            }
        } else {
            ShowToast("Selected table name is null.");
        }


    }

}
