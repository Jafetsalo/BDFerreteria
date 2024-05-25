package com.example.bdferreteria;

import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

public class InsertPedidoActivity extends BaseActivity {

    TextView descripcionPedido;
    TextView fechaPedido;
    TextView cantidadPedido;
    Spinner spinnerPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_pedido);
        myTableName="pedido";
        descripcionPedido = findViewById(R.id.editTextDescripcionPedido);
        fechaPedido = findViewById(R.id.editTextFechaPedido);
        cantidadPedido = findViewById(R.id.editTextCantidadPedido);
        spinnerPedido = findViewById(R.id.spinnerPedidos);

        textViewArrayList.add(descripcionPedido);
        textViewArrayList.add(fechaPedido);
        textViewArrayList.add(cantidadPedido);
        spinnerPedido.setOnItemSelectedListener(selectedElementListener);
        loadSpinner(spinnerPedido,myTableName);
    }


    public void InsertarPedido(View view) {
        if (emptyFields()) return;
        InsertValue(myTableName);
        RefreshForm(spinnerPedido);
    }

    public void ActualizarPedido(View view) {
        if(emptyFields()) return;

        UpdateValue(myTableName, spinnerPedido.getSelectedItemPosition());
        //Tenemos que actualizar myRow
        RefreshForm(spinnerPedido);
    }

    public void EliminarPedido(View view) {
        if(emptyFields()) return;
        DeleteValue(myTableName, spinnerPedido.getSelectedItemPosition());
        RefreshForm(spinnerPedido);
    }






}