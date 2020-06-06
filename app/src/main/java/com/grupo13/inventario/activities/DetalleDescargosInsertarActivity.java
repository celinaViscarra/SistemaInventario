package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.grupo13.inventario.ControlBD;
import com.grupo13.inventario.R;

public class DetalleDescargosInsertarActivity extends AppCompatActivity {
    ControlBD helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_descargos_insertar);
    }
}
