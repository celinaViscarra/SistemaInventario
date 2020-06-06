package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.grupo13.inventario.R;

import butterknife.ButterKnife;

public class DetalleAutorEliminarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_autor_eliminar);

        //USO DE BUTTERKNIFE, SI NO PONEN ESTA LINEA NO SIRVE
        ButterKnife.bind(this);
    }
}