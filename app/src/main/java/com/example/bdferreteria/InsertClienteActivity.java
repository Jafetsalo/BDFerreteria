package com.example.bdferreteria;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class InsertClienteActivity extends BaseActivity {

    TextView cedulaCliente;
    TextView nombreCliente;
    TextView direccionCliente;
    TextView telefonoCliente;
    Spinner spinnerCliente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_cliente);
        myTableName = "clientes";
        cedulaCliente = findViewById(R.id.editTextCedulaCliente);
        nombreCliente = findViewById(R.id.editTextNombresCliente);
        direccionCliente = findViewById(R.id.editTextDireccionCliente);
        telefonoCliente = findViewById(R.id.editTextTelefonoCliente);
        spinnerCliente = findViewById(R.id.spinnerClientes);

        textViewArrayList.add(cedulaCliente);
        textViewArrayList.add(nombreCliente);
        textViewArrayList.add(direccionCliente);
        textViewArrayList.add(telefonoCliente);
        spinnerCliente.setOnItemSelectedListener(selectedElementListener);
        loadSpinner(spinnerCliente,myTableName);
    }


    //Crear Insert
    //Obtener variable Tabla


    public void InsertarCliente(View view) {
        if (emptyFields()) return;
        InsertValue(myTableName);
        RefreshForm(spinnerCliente);
    }
    
    public void ActualizarCliente(View view) {
        if(emptyFields()) return;
        UpdateValue(myTableName, spinnerCliente.getSelectedItemPosition());
        RefreshForm(spinnerCliente);
    }

    public void EliminarCliente(View view) {
        if(emptyFields()) return;
        DeleteValue(myTableName, spinnerCliente.getSelectedItemPosition());
        RefreshForm(spinnerCliente);
    }



    //Con uno seleccionado, y toda la info llena, se actualiza






}