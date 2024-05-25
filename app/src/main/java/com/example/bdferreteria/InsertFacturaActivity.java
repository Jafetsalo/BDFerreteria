package com.example.bdferreteria;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

public class InsertFacturaActivity extends BaseActivity {

    TextView fechaFactura;
    TextView totalFactura;
    Spinner spinnerFactura;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_factura);
        myTableName="factura";
        fechaFactura = findViewById(R.id.editTextFechaFactura);
        totalFactura = findViewById(R.id.editTextTotalFactura);
        spinnerFactura = findViewById(R.id.spinnerFactura);

        textViewArrayList.add(fechaFactura);
        textViewArrayList.add(totalFactura);
        spinnerFactura.setOnItemSelectedListener(selectedElementListener);
        loadSpinner(spinnerFactura,myTableName);
    }

    public void InsertarFactura(View view) {
        if (emptyFields()) return;
        InsertValue(myTableName);
        RefreshForm(spinnerFactura);
    }

    public void ActualizarFactura(View view) {
        if(emptyFields()) return;

        UpdateValue(myTableName, spinnerFactura.getSelectedItemPosition());
        //Tenemos que actualizar myRow
        RefreshForm(spinnerFactura);
    }

    public void EliminarFactura(View view) {
        if(emptyFields()) return;
        DeleteValue(myTableName, spinnerFactura.getSelectedItemPosition());
        RefreshForm(spinnerFactura);
    }




}