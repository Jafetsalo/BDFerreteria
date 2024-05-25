package com.example.bdferreteria;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

public class InsertProductoActivity extends BaseActivity {

    TextView descripcionProducto;
    TextView valorProducto;
    Spinner spinnerProducto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_producto);
        myTableName="producto";
        descripcionProducto = findViewById(R.id.editTextDescripcionProducto);
        valorProducto = findViewById(R.id.editTextPrecioProducto);
        spinnerProducto = findViewById(R.id.spinnerProductos);

        textViewArrayList.add(descripcionProducto);
        textViewArrayList.add(valorProducto);
        spinnerProducto.setOnItemSelectedListener(selectedElementListener);
        loadSpinner(spinnerProducto,myTableName);

    }


    public void InsertarProducto(View view) {
        if (emptyFields()) return;
        InsertValue(myTableName);
        RefreshForm(spinnerProducto);
    }

    public void ActualizarProducto(View view) {
        if(emptyFields()) return;

        UpdateValue(myTableName, spinnerProducto.getSelectedItemPosition());
        //Tenemos que actualizar myRow
        RefreshForm(spinnerProducto);
    }

    public void EliminarProducto(View view) {
        if(emptyFields()) return;
        DeleteValue(myTableName, spinnerProducto.getSelectedItemPosition());
        RefreshForm(spinnerProducto);
    }

}